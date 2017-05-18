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
	if (amount < 100) {
		mui.toast("小于起购金额!");
		return;
	}

	return true;
}

/**
 * 进行投资
 */
function doInvest() {
	// 进行合法性校验
	var amount = $("#investMoney").val();
	if (!beforeInvest(amount)) {
		mask.close();
		return;
	}

	mui.$post("/fund/apply", {
		amount: amount
	}, function(data) {
		// 余额不足
		if (data.code === 1) {
			mui.confirm('现在就去充值?', '余额不足', ['充值', '不买了'], function(e) {
				if (e.index == 0) {
					window.location.href = APPROOT + "/static/medeposit.html";
				} else {
					// 不去充值
				}
			});
			return;
		}
		console.log(data);
		if (data.url) {
			window.location.href = data.url;
		} else {
			mui.toast("申购失败!")
		}

	}, function() {
		mask.close();
	});

}
//-------------------------------------------
mui.init({
	swipeBack: true //启用右滑关闭功能
});
var mask = mui.createMask();
$("#investMoney").on("focus", function() {
	mask.show();
});
$("#doInvestBtn").on("tap", function() {
	setTimeout(doInvest, 500);
});