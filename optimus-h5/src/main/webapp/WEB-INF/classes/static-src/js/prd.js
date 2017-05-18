var prdTmplFunc = mui.$template($('#prdTmpl').html());
var headerTmplFunc = mui.$template($("#headerTmpl").html());
var investOrderTmplFunc = mui.$template($("#investOrderTmpl").html());
var biddableAmount = 0; // 剩余可购买金额
var bidMinAmount = 0; // 最小购买金额
var bidUnit = 0; // 投资步长
var rewardRate = 0; // 收益率
var subjectNo = ""; // 产品编号
var loanTermDay = 0; // 产品期限,天

var LoanTerm = {};
for (var i = 1; i < 50; i++) {
	LoanTerm[i + '月'] = i * 30;
	// LoanTerm[i + '年'] = i * 360;
}
/**
 * 查询详情
 */
function queryDetail() {
	var subjectNo = mui.$getParam("subjectNo");
	if (!subjectNo) {
		mui.toast("获取不到产品编号!");
		return;
	}
	mui.$get("/invest/subject/" + subjectNo, null, function(data) {
		insertToDom(data);
	});
}

/**
 * 将查询到的数据构造成DOM
 * 
 * @param {Object}
 *            data
 */
function insertToDom(data) {
	// 修改标题头
	$('.mui-title').text(data.subjectName);
	// 详情
	$('.mui-content').html(prdTmplFunc(data));
	$("header").append(headerTmplFunc(data));
	// 投资记录
	if (data.bidOrders) {
		var html = [];
		for (var i = 0; i < data.bidOrders.length; i++) {
			data.bidOrders[i].index = (i < 9 ? '0' : '') + (i + 1);
			html.push(investOrderTmplFunc(data.bidOrders[i]));
		}
		$('#investOrdersContainer').append(html.join(""));
	}
	// 判断是否可投资
	biddableAmount = data.biddableAmount.amount;
	bidMinAmount = data.bidMinAmount.amount;
	bidUnit = data.bidUnit.amount;
	rewardRate = data.rewardRate;
	subjectNo = data.subjectNo;
	loanTermDay = LoanTerm[data.loanTerm];
	var now = new Date().getTime();
	if (biddableAmount <= 0) {
		$("#doInvestBtn").text("已售罄");
		return;
	}
	if (data.validDate <= now) {
		$("#doInvestBtn").text("已截止");
		return;
	}

	if (biddableAmount > 0) {
		$("#doInvestBtn").removeAttr("disabled");
		$("#investMoney").removeAttr("disabled");
	}
}
/**
 * 进行投资前校验
 * 
 * @param {Object}
 *            amount
 */
function beforeInvest(amount) {
	if (amount - 0 != amount) {
		mui.toast("请输入合法的金额!");
		return;
	}
	amount = amount - 0;
	if (amount > biddableAmount) {
		mui.toast("超过剩余可购买金额!");
		return;
	}
	if (biddableAmount < bidMinAmount && amount == biddableAmount) {
		return true;
	}
	if (biddableAmount >= bidMinAmount && amount < bidMinAmount) {
		mui.toast("至少购买" + bidMinAmount);
		return;
	}

	if (biddableAmount < bidMinAmount && amount != biddableAmount) {
		mui.toast("当前剩余不足起购金额,需一次性全部购买!");
		return;
	}
	// 判断投资步长
	if (bidUnit > 0) {
		if (!isMod0(amount, bidUnit)) {
			mui.toast("金额需是投资步长的整数倍!");
			return;
		}
	}
	return true;
}
// 是否整除
function isMod0(l, r) {
	// 把小数转换为整数
	var e10 = 0;
	var str = ('' + r);
	if (str.indexOf('.') !== -1) {
		e10 = str.length - str.indexOf('.') - 1;
	}
	if (l * Math.pow(10, e10) % (r * Math.pow(10, e10)) !== 0) {
		return false;
	}
	return true;
}
/**
 * 进行投资
 */
function doInvest() {
	if (loading) {
		return;
	}
	// 进行合法性校验
	var amount = $("#investMoney").val();
	if (!beforeInvest(amount)) {
		mask.close();
		return;
	}
	loading = true;
	$("#loading").show();
	mui.$post("/invest/subject/" + subjectNo, {
		amount : amount
	}, function(data) {
		// 余额不足
		if (data.code === 1) {
			mui.confirm('现在就去充值?', '余额不足', [ '充值', '不买了' ], function(e) {
				if (e.index == 0) {
					window.location.href = APPROOT + "/deposit";
				} else {
					// 不去充值
				}
			});
			return;
		}
		if (data.url) {
			window.location.href = data.url;
		} else {
			mui.toast("投资失败!");
		}
	}, function() {
		mask.close();
		loading = false;
		$("#loading").hide();
	});

}

// --------------------------------------------------
var loading = false;
mui.init({
	swipeBack : true
// 启用右滑关闭功能
});
// $('#text').text(navigator.userAgent);
var mask = null;
$("#investMoney").on("focus", function() {
	setTimeout(function() {
		if (!mask) {
			mask = mui.createMask();
		}
		mask.show();
		if (/ipad|iphone|mac/gi.test(navigator.userAgent)) {
			$('.mui-bar-tab').css("position", 'absolute');
			$('.mui-backdrop').css("position", 'absolute');
		}
	}, 300);
});
$("#investMoney").on("focusout", function() {
	setTimeout(function() {
		$('.mui-bar-tab').css("position", 'fixed');
	}, 300);

});
$("#investMoney").on("input", function() {
	var val = $("#investMoney").val();
	if (!val) {
		$("#profitMoney").text('0');
		$("#investMoney").val('');
		return;
	}
	var money = parseInt(val) || 0;
	if (money > biddableAmount) {
		money = biddableAmount;
	}
	$("#investMoney").val(money);
	var profit = money * loanTermDay * rewardRate / 360 / 100;
	profit = profit.toFixed(5) + "";
	$("#profitMoney").text(profit.substring(0, profit.indexOf(".") + 3));
});
$("#doInvestBtn").on("tap", function() {
	setTimeout(doInvest, 500);
});
queryDetail();