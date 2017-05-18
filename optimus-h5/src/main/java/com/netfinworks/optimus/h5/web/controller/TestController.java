package com.netfinworks.optimus.h5.web.controller;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netfinworks.common.util.money.Money;
import com.netfinworks.enums.OrderType;
import com.netfinworks.invest.request.BidRequest;
import com.netfinworks.invest.response.BidResponse;
import com.netfinworks.optimus.domain.TokenBean;
import com.netfinworks.optimus.entity.AccountEntity;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.h5.web.domain.RestResult;
import com.netfinworks.optimus.service.AccountService;
import com.netfinworks.optimus.service.ConfigService;
import com.netfinworks.optimus.service.MemberService;
import com.netfinworks.optimus.service.fund.ApplyService;
import com.netfinworks.optimus.service.fund.FundConstants;
import com.netfinworks.optimus.service.fund.RedeemService;
import com.netfinworks.optimus.service.integration.invest.impl.InvestServiceImpl;
import com.netfinworks.optimus.service.integration.mef.MefClientImpl;
import com.netfinworks.optimus.utils.BeanUtil;
import com.netfinworks.optimus.utils.FormatUtil;
import com.netfinworks.util.JSONUtil;
import com.vf.mef.vo.InvestRedeemRequest;
import com.vf.mef.vo.InvestRedeemResponse;

@RequestMapping("/test")
@RestController
public class TestController {

	private static Logger log = LoggerFactory.getLogger(TestController.class);
	// 日志前缀
	private static final String LOG_PREFIX = "压力测试";

	@Resource(name = "investService")
	private InvestServiceImpl investService;

	@Resource
	private ApplyService applyService;
	@Resource
	private RedeemService redeemService;

	@Resource
	private AccountService accountService;

	@Resource
	private MemberService memberService;

	@Resource
	private ConfigService configService;

	@Resource(name = "mefClient")
	private MefClientImpl mefclient;

	@RequestMapping(value = "/subject/{subjectNo}")
	public Object apply(@PathVariable String subjectNo,
			@RequestBody OrderEntity order, HttpServletRequest request) {
		BigDecimal amount = order.getAmount();
		Assert.notNull(amount, "请输入购买金额!");
		Assert.isTrue(amount.compareTo(BigDecimal.ZERO) > 0, "购买金额必须大于0!");

		MemberEntity member = new MemberEntity(); // ControllerUtil.getMember(request,
													// memberService);

		member.setAccountId("200112650002");
		member.setMemberId(member.getAccountId());

		AccountEntity account = accountService
				.getAccount(member.getAccountId());

		if (account.getBalance().compareTo(amount) < 0) {
			RestResult result = new RestResult();
			result.setCode(1);
			result.setMsg("余额不足!");
			return result;
		}

		BidRequest req = new BidRequest();
		req.setAmount(new Money(amount.toString()));
		req.setExtension("");
		req.setMemberId(member.getMemberId());
		req.setRemark("购买产品");
		req.setSubjectNo(subjectNo);
		req.setSubmitType("1"); // 手动购买

		log.info("{}-投资购买-请求参数:{}", LOG_PREFIX, JSONUtil.toJson(req));

		BidResponse resp = investService.apply(req);

		log.info("{}-投资购买-响应信息:{}", LOG_PREFIX, JSONUtil.toJson(resp));

		return resp;
	}

	@RequestMapping(value = "/download")
	public ResponseEntity<byte[]> accountCheck(HttpServletRequest request) {
		String root = System.getProperty("h5.root");
		File file = new File(root + "doc.pdf");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "doc.pdf");
		byte[] array = new byte[0];
		try {
			array = FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			log.error("读取对账文件失败!", e);
		}
		return new ResponseEntity<byte[]>(array, headers, HttpStatus.CREATED);
	}

	/**
	 * 充值 memberId,amount,chanUserId代表token
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deposit", method = RequestMethod.POST)
	public Object deposit(@RequestBody OrderEntity order,
			HttpServletRequest request) {
		BigDecimal amount = order.getAmount();
		Assert.isTrue(BigDecimal.ZERO.compareTo(amount) < 0, "充值金额必须大于0!");
		Assert.notNull(order.getMemo(), "缺少密码!"); // 暂用作密码字段

		MemberEntity member = memberService
				.getMemberEntity(order.getMemberId());
		TokenBean bean = new TokenBean(order.getChanUserId(),
				member.getChanId());
		bean.setMember(member);
		bean.setToken(order.getChanUserId());

		log.info("账户充值-金额:{}-{}", member.getMemberId(), amount);

		String pwd = order.getMemo(); // CipherUtil.encrypt(order.getMemo());

		AccountEntity account = accountService.deposit(amount, pwd,// "37b8acd28f207ce2",
				configService.getValue(ConfigService.API_URL_BASE)
						+ "/callback/account/deposit", bean);
		// 如果accountId 不为空就说明充值操作成功
		Assert.notNull(account.getAccountId(),
				"充值失败:" + account.getAccountName());

		Map<String, Object> result = BeanUtil.pick(account, "balance");

		return result;
	}

	/**
	 * 提现
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
	public Object withdraw(@RequestBody OrderEntity order,
			HttpServletRequest request) {
		BigDecimal amount = order.getAmount();

		Assert.isTrue(BigDecimal.ZERO.compareTo(amount) < 0, "提现金额必须大于0!");
		Assert.notNull(order.getMemo(), "缺少密码!"); // 暂用作密码字段
		MemberEntity member = memberService
				.getMemberEntity(order.getMemberId());
		TokenBean bean = new TokenBean(order.getChanUserId(),
				member.getChanId());
		bean.setMember(member);
		bean.setToken(order.getChanUserId());

		log.info("账户提现-金额:{}-{}", member.getMemberId(), amount);

		String pwd = order.getMemo(); // CipherUtil.encrypt(order.getMemo());
		AccountEntity account = accountService.withdraw(amount, pwd,
				configService.getValue(ConfigService.API_URL_BASE)
						+ "/callback/account/redeem", bean);

		Assert.notNull(account.getAccountId(),
				"充值失败:" + account.getAccountName());

		Map<String, Object> result = BeanUtil.pick(account, "balance");

		return result;
	}

	@RequestMapping("/fund/apply")
	public Object apply(@RequestBody OrderEntity order,
			HttpServletRequest request) {
		BigDecimal amount = order.getAmount();
		Assert.notNull(amount, "缺少申购金额参数!");
		Assert.isTrue(BigDecimal.ZERO.compareTo(amount) < 0, "申购金额必须大于0!");

		MemberEntity member = memberService
				.getMemberEntity(order.getMemberId());

		InvestRedeemRequest req = new InvestRedeemRequest();
		req.setChannelNo(order.getChanId() == null ? "mef_platform_id" : order
				.getChanId());
		req.setAmount(amount.toString());
		req.setExtension("");
		req.setMemberId(member.getMemberId());
		req.setMemo(order.getMemo() == null ? "申购基金" : order.getMemo());
		req.setProductNo(order.getProductId());
		req.setRequestNo(UUID.randomUUID().toString().replaceAll("-", ""));
		req.setRequestTime(FormatUtil.formatDateTime(new Date()));
		req.setTradeType(OrderType.INVEST.name());
		if (StringUtils.isNotEmpty(order.getOrderType())) {
			req.setTradeType(order.getOrderType());
		}

		InvestRedeemResponse response = mefclient.investRedeemTrade(req);
		log.info("申购基金:{}->{}", JSONUtil.toJson(req), JSONUtil.toJson(response));

		return response;
	}

	public static void main2(String[] args) throws Exception {
		// System.out.println("start");
		// String root = System.getProperty("h5.root");
		// HtmlImageGenerator generator = new HtmlImageGenerator();
		// Dimension dimension = new Dimension();
		// // dimension.
		// // generator.setSize(arg0);
		// dimension = generator.getSize();
		// dimension.setSize(1024, dimension.getHeight());
		// System.out.println(dimension);
		// // generator.
		// generator.loadHtml(FileUtils.readFileToString(new File(
		// "E:/optimus/optimus/optimus-h5/src/main/webapp/index.htm")));
		// System.out.println(FileUtils.readFileToString(new File(
		// "E:/optimus/optimus/optimus-h5/src/main/webapp/index.htm")));
		// Thread.sleep(3000);
		// System.out.println(generator.getSize());
		// generator
		// .saveAsImage("E:/optimus/optimus/optimus-h5/src/main/webapp/002.png");
		// System.out.println(dimension);
		// System.out.println("end");
	}

	public static void main3(String[] args) throws Exception {
		JEditorPane ed = new JEditorPane();
		System.out.println("10");
		ed.setAutoscrolls(false);
		// ed.set
		ed.setSize(1024, ed.getPreferredSize().height);
		// ed.setLayout(mgr);
		ed.setPage(new URL(
				"http://localhost:8080/optimus-admin/docs/manual/hetong.html"));

		Thread.sleep(20000);

		// create a new image
		BufferedImage image = new BufferedImage(ed.getWidth(),
				ed.getPreferredSize().height, BufferedImage.TYPE_INT_ARGB);

		// paint the editor onto the image
		SwingUtilities.paintComponent(image.createGraphics(), ed, new JPanel(),
				0, 0, image.getWidth(), image.getHeight());
		// save the image to file
		ImageIO.write((RenderedImage) image, "png", new File(
				"E:/optimus/optimus/optimus-h5/src/main/webapp/1024.png"));
		System.out.println("ok");

	}

	public static void main(String[] args) {
		try {
			main2(args);
			// main3(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
