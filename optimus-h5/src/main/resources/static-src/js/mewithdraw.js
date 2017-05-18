var balance = -1; //账户余额

/**
 * 提现之前进行数据校验
 */
function before(amount, pwd) {
	if (amount - 0 != amount || amount - 0 < 0) {
		mui.toast("请输入合法的提现金额");
		return;
	}
	if (amount <= 0) {
		mui.toast("请输入大于0的金额");
		return;
	}
	if (!pwd) {
		mui.toast("请输入支付密码!");
		return;
	}
	if(balance !== -1 && balance < amount){
		mui.toast('余额不足!');
		return;
	}
	return true;
}

function queryBalance() {
	mui.$get('/account/overview', null, function(data) {
		balance = data.balance || 0;
		$("#balanceMoney").text(data.vm.balanceV);
		$('#infoDlg').show();
	});
}

function withdrawAll() {
	$('#depositMoney').val(balance);
}

function withdraw() {
	if (loading) {
		return;
	}
	var amount = $('#depositMoney').val();
	var pwd = $("#depositPwd").val();
	if (!before(amount, pwd)) {
		return;
	}
	//对密码进行加密
	pwd = hex_sha1(pwd);
	amount = amount - 0;
	loading = true;
	$("#loading").show();
	mui.$post("/account/withdraw", {
		amount: amount,
		memo: pwd
	}, function(data) {
		if (data.url) {
			window.location.href = data.url;
		} else if (data.code && data.code != 0) {
			mui.toast(data.msg);
		} else {
			mui.toast("提现失败!");
		}
	}, function() {
		loading = false;
		$("#loading").hide();
	});
}

// ------------------------------------
var loading = false;
mui.init({
	swipeBack: true
		// 启用右滑关闭功能
});
$("#withdrawBtn").on("tap", withdraw);
//$("#withdrawAllBtn").on('tap',withdrawAll);
queryBalance();