
function doDeposit() {
	var name = $('#depositMoney').val();
	
	mui.$post("/login", {
		memberId : name
	}, function(data) {
		if (data.url) {
			window.location.href = data.url;
		} else {
			mui.toast("登录失败!");
		}
	});
}

// ------------------------------------
mui.init({
	swipeBack : true
// 启用右滑关闭功能
});
$("#depositBtn").on("tap", doDeposit);