
/**
 * 充值之前进行数据校验
 */
function beforeDeposit(amount, pwd) {
	if (amount - 0 != amount || amount - 0 < 0) {
		mui.toast("请输入合法的充值金额");
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
	return true;
}

function doDeposit() {
	if(loading){
		return;
	}
	var amount = $('#depositMoney').val();
	var pwd = $("#depositPwd").val();
	if (!beforeDeposit(amount, pwd)) {
		return;
	}
	//对密码进行加密
	pwd = hex_sha1(pwd);
	amount = amount - 0;
	loading = true;
	$("#loading").show();
	mui.$post("/account/deposit", {
		amount: amount,
		memo: pwd
	}, function(data) {
		if (data.url) {
			window.location.href = data.url;
		} else if (data.code && data.code != 0) {
			mui.toast(data.msg);
		} else {
			mui.toast("充值失败!");
		}
	},function(){
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
$("#depositBtn").on("tap", doDeposit);