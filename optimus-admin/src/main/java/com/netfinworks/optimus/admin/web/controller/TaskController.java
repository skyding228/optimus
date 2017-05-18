package com.netfinworks.optimus.admin.web.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netfinworks.optimus.cron.PaymentTask;
import com.netfinworks.optimus.cron.SubjectTask;
import com.netfinworks.optimus.utils.SelfSufficient;

/**
 * 定时任务
 * 
 * @author weichunhe create at 2016年5月10日
 */
@RestController
@RequestMapping("/task")
public class TaskController {
	private static Logger log = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	private PaymentTask paymentTask;

	@Autowired
	private SubjectTask subjectTask;

	@RequestMapping("/execute")
	public Object execute(String param) {
		// 必须随意传递一个参数名称为param 的参数，防止爬虫等机器行为执行任务
		if (StringUtils.isEmpty(param)) {
			return null;
		}
		executeWithCatch(subjectTask::applyOverdueSubject);
		executeWithCatch(subjectTask::repaymentProvision);
		executeWithCatch(paymentTask::scheduleUpdatePaymentRecords);
		executeWithCatch(paymentTask::genCheckingFiles);
		executeWithCatch(paymentTask::statisticsChanView);
		return "SUCCESS";
	}

	private void executeWithCatch(SelfSufficient inf) {
		try {
			inf.execute();
		} catch (Exception e) {
			log.error("定时任务执行失败!", e);
		}

	}

}
