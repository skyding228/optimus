package com.netfinworks.optimus.h5.web.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.netfinworks.optimus.domain.AccountInfo;
import com.netfinworks.optimus.domain.OrderResponse;
import com.netfinworks.optimus.domain.TokenBean;
import com.netfinworks.optimus.domain.UserInfo;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.service.AccountService;
import com.netfinworks.optimus.service.CheckingService;
import com.netfinworks.optimus.service.ConfigService;
import com.netfinworks.optimus.service.MemberService;
import com.netfinworks.optimus.service.auth.AuthService;
import com.netfinworks.optimus.utils.BeanUtil;
import com.netfinworks.optimus.utils.FormatUtil;
import com.netfinworks.util.JSONUtil;

@Controller
@RequestMapping("/api")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class APIController {
	private static Logger log = LoggerFactory.getLogger(APIController.class);
	private static final String LogProfix = "外部API";

	@Resource
	private AuthService authService;

	@Resource
	private MemberService memberService;

	@Resource
	private ConfigService configService;

	@Resource
	private AccountService accountService;

	@Resource
	private CheckingService checkingService;

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	@ResponseBody
	public Object test(HttpServletRequest request) {
		Map map = (Map) request.getAttribute("postParam");
		log.info("{}-测试 -请求参数:{}", LogProfix, JSONUtil.toJson(map));

		return map;
	}

	@RequestMapping("/health")
	@ResponseBody
	public Object Health(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("health", "true");
		return map;
	}

	/**
	 * 首页跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public ModelAndView index(HttpServletRequest request) {
		return makeTokenKey(request, "首页", "/to/index");
	}

	/**
	 * 个人中心跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/account/me", method = RequestMethod.POST)
	public ModelAndView me(HttpServletRequest request) {
		return makeTokenKey(request, "个人中心", "/to/account/me");
	}

	/**
	 * 充值跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/account/deposit", method = RequestMethod.POST)
	public ModelAndView deposit(HttpServletRequest request) {
		return makeTokenKey(request, "充值", "/to/account/deposit");
	}

	/**
	 * 对账文件下载
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/account/checking", method = RequestMethod.POST)
	public ResponseEntity<byte[]> accountCheck(HttpServletRequest request) {
		Map<String, Object> map = (Map<String, Object>) request
				.getAttribute("postParam");

		String authId = MapUtils.getString(map, "auth_id");
		String platNo = authService.getPlatNo(authId);
		String date = MapUtils.getString(map, "date");
		Assert.notNull(date, "缺少对账日期参数!");
		Date day = null;
		try {
			day = FormatUtil.dateFormat.parse(date);
		} catch (ParseException e) {
			log.error("下载对账文件日期错误!{}", date);
			Assert.isTrue(false, "下载对账文件日期错误!" + date);
		}
		Date start = new Date(System.currentTimeMillis() - 31L * 24 * 3600000L);
		Date end = new Date(System.currentTimeMillis() - 24 * 3600000L);
		Assert.isTrue(day.before(end) && day.after(start), "只允许下载最近30天的对账文件!");
		File file = checkingService.getCheckingFile(platNo, date);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", date + ".txt");
		byte[] array = new byte[0];
		try {
			array = FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			log.error("{}-读取对账文件失败!", LogProfix, e);
		}
		return new ResponseEntity<byte[]>(array, headers, HttpStatus.CREATED);
	}

	/**
	 * 账户资产
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/account/overview", method = RequestMethod.POST)
	@ResponseBody
	public Object overview(HttpServletRequest request) {
		Map<String, Object> map = (Map<String, Object>) request
				.getAttribute("postParam");
		String token = MapUtils.getString(map, "token");
		String authId = MapUtils.getString(map, "auth_id");
		Assert.notNull(token, "缺少token参数!");

		String platNo = authService.getPlatNo(authId);
		// 验证token的地址
		String tokenUrl = configService.getPlatValue(platNo,
				ConfigService.API_URL_BASE) + ConfigService.PLAT_TOKEN_URL;
		TokenBean tokenBean = new TokenBean(token, platNo);
		tokenBean.setUrl(tokenUrl);

		log.info("{}-查询账户总览-请求参数:{}", LogProfix, JSONUtil.toJson(tokenBean));
		UserInfo user = AuthService.validateToken(tokenBean);
		Assert.notNull(user, "用户身份无效:token=" + token);
		MemberEntity member = memberService.getMemberEntityByChanUserId(platNo,
				user.getMemberId());
		if (member == null) {
			log.info("{}-查询账户总览-未查询到相关人员", LogProfix);
			return new HashMap<String, String>();
		}
		AccountInfo info = accountService.calcOverview(member);

		Map<String, Object> overviewMap = BeanUtil.pick(info, "totalAsset",
				"balance");
		log.info("{}-查询账户总览-响应信息:{}->{}", LogProfix, JSONUtil.toJson(info),
				JSONUtil.toJson(overviewMap));
		return overviewMap;
	}

	/**
	 * 充值回调
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/callback/account/deposit", method = RequestMethod.POST)
	@ResponseBody
	public Object depositCallback(HttpServletRequest request) {
		Map<String, Object> map = (Map<String, Object>) request
				.getAttribute("postParam");

		OrderResponse response = new OrderResponse();
		try {
			BeanUtils.populate(response, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		accountService.afterAccountChange(response);
		return null;
	}

	/**
	 * 提现回调
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/callback/account/redeem", method = RequestMethod.POST)
	@ResponseBody
	public Object withdrawCallback(HttpServletRequest request) {
		return depositCallback(request);
	}

	/**
	 * 管理 跳转token
	 * 
	 * @param request
	 * @param logInfo
	 * @return
	 */
	private ModelAndView makeTokenKey(HttpServletRequest request,
			String logInfo, String url) {
		Map<String, Object> map = (Map<String, Object>) request
				.getAttribute("postParam");
		log.info("{}-{}跳转-请求参数:{}", LogProfix, logInfo, JSONUtil.toJson(map));
		String token = MapUtils.getString(map, "token");
		String authId = MapUtils.getString(map, "auth_id");
		Assert.notNull(token, "缺少token参数!");

		String platNo = authService.getPlatNo(authId);
		// 验证token的地址
		String tokenUrl = configService.getPlatValue(platNo,
				ConfigService.API_URL_BASE) + ConfigService.PLAT_TOKEN_URL;

		TokenBean validate = new TokenBean(token, platNo);
		validate.setUrl(tokenUrl);
		UserInfo user = AuthService.validateToken(validate);

		log.info("{}- token 所属用户:{}", LogProfix, JSONUtil.toJson(user));
		Assert.notNull(user, "token is invalid");
		Assert.isTrue(!StringUtils.isEmpty(user.getMemberName()),
				"member name is needed!");
		Assert.isTrue(!StringUtils.isEmpty(user.getIdNumber()),
				"id number is needed!");
		Assert.isTrue(!StringUtils.isEmpty(user.getRealName()),
				"real name is needed!");
		// 判断此用户是否在本平台已经开户,如果没有就进行直接开户
		MemberEntity member = memberService.getOrCreateMember(platNo,
				user.getMemberId(), user.getMemberName(), user.getTelphone());
		// 更新用户信息
		member.setChanUserIdNumber(user.getIdNumber());
		member.setChanUserRealname(user.getRealName());
		member.setChanUserName(user.getMemberName());
		member.setMobile(user.getTelphone());
		memberService.updateEntity(member);

		TokenBean bean = APITokenMgt.saveToken(token, platNo);
		bean.setMember(member);
		bean.setUser(user);
		bean.setUrl(tokenUrl);

		String base = configService.getValue(ConfigService.API_URL_BASE);
		base = base.substring(0, base.lastIndexOf("/"));

		Map<String, String> model = new HashMap<String, String>();
		model.put("url", base + url + "/" + bean.getKey());

		ModelAndView mv = new ModelAndView("redirect", model);

		return mv;
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(FormatUtil.formatDateTime(new Date()).replaceAll(
				"\\W", ""));
	}

	public static void main3(String[] args) {
		String path = "/opt/files/checking/";
		String[] ps = path.split("/");
		System.out.println(ps);
		Path p = Paths.get(Paths.get("/").toAbsolutePath().toString(), ps);
		System.out.println(p.toAbsolutePath());
		System.out.println(p.getRoot());
		System.out.println(p.toFile().exists());
	}

}
