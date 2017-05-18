package com.netfinworks.optimus.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jodd.io.FileUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netfinworks.optimus.entity.AccountCheckEntity;
import com.netfinworks.optimus.service.auth.AuthService;
import com.netfinworks.optimus.utils.FormatUtil;

/**
 * 对账服务
 * 
 * @author weichunhe create at 2016年5月15日
 */
@Service
public class CheckingService {

	@Resource
	private AuthService authService;

	@Resource
	private MemberService memberService;

	@Resource
	private ConfigService configService;

	@Resource
	private AccountService accountService;

	private RestTemplate restTmpl = new RestTemplate();

	private static Logger log = LoggerFactory.getLogger(CheckingService.class);

	/**
	 * 获取平台对账文件根路径
	 * 
	 * @param plat
	 * @param isPlatDir
	 *            平台方的对账文件目录
	 * @return
	 */
	private File getRootDir(String plat, boolean isPlatDir) {
		String path = configService.getValue(ConfigService.CHECKING_FILE_ROOT);
		String[] dirs = path.split("/");
		String root = Paths.get("/").toAbsolutePath().toString();
		String dir = Paths.get(root, dirs).toString();
		log.info("对账文件根路径:{}", dir);

		File rootDir = null;
		if (isPlatDir) {
			rootDir = Paths.get(dir, plat, "plat").toFile();
		} else {
			rootDir = Paths.get(dir, plat).toFile();
		}
		if (!rootDir.exists()) {
			try {
				FileUtil.mkdirs(rootDir);
			} catch (IOException e) {
				log.error("创建对账文件根目录时失败:{}", rootDir.toString(), e);
			}
		}
		return rootDir;
	}

	/**
	 * 获取对账文件，如果不存在就直接生成
	 * 
	 * @param plat
	 * @param date
	 * @return
	 */
	public File getCheckingFile(String plat, String date) {
		File path = getRootDir(plat, false);

		String fileName = date + ".txt";
		File file = new File(path, fileName);
		if (file.exists()) {
			return file;
		}
		int pageSize = 100, pageNum = 0;
		// 分批写入文件
		while (true) {
			List<AccountCheckEntity> list = accountService
					.makeAccountCheckingFile(plat, date, ++pageNum, pageSize);
			StringBuilder content = new StringBuilder();
			String endLine = System.getProperty("line.separator", "\n");
			if (list == null || list.size() == 0) {
				content.append("");
			} else {
				list.forEach(x -> {
					content.append(x.toRow());
					content.append(endLine);
				});
			}

			// 写入文件
			try {
				FileUtil.appendString(file, content.toString(), "UTF-8");
			} catch (IOException e) {
				log.error("写入对账文件出错了:{}", file.toString(), e);
				file.delete();
			}

			if (list == null || list.size() == 0) {
				break;
			}

		}

		return file;
	}

	/**
	 * 获取平台方的对账文件，如果不存在就直接生成；用于对账
	 * 
	 * @param plat
	 * @param date
	 * @return
	 */
	public File getPlatCheckingFile(String plat, String date) {
		File path = getRootDir(plat, true);
		String fileName = date + "-"
				+ FormatUtil.formatDateTime(new Date()).replaceAll("\\W", "")
				+ ".txt";
		File file = new File(path, fileName);
		if (file.exists()) {
			return file;
		}
		// 下载平台方的对账文件
		Map<String, String> param = new HashMap<String, String>();
		param.put("date", date);

		HttpEntity<String> formEntity = AuthService.restFormEntity(plat, param);
		String url = configService.getPlatValue(plat,
				ConfigService.API_URL_BASE) + ConfigService.PLAT_CHECKING_URL;
		// 写入文件
		try {
			String resp = restTmpl.postForObject(url, formEntity, String.class);
			log.info("{}-平台方对账文件:{}", date, resp);
			if (StringUtils.isEmpty(resp) || !resp.contains("|")) {
				resp = "";
			}
			FileUtil.appendString(file, resp, "UTF-8");
		} catch (IOException e) {
			log.error("写入对账文件出错了:{}", file.toString(), e);
		}

		return file;
	}
}
