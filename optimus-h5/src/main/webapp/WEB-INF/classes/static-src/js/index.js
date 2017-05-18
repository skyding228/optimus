/**
 * 查询一页投资标的
 * @param {Object} page
 */
function querySubjects(pageNum, pageSize, success) {
	var pageNum = pageNum || 1;
	var pageSize = pageSize || 2;
	if (pageNum === 1) {
		$('#loading').show();
	}
	mui.$get("/invest/subjects", {
		pageSize: pageSize,
		pageNum: pageNum
	}, function(data) {
		console.log(data);
		insertToDom(data.result);
		mui.$resetListStyle();
	}, function() {
		$('#loading').hide();
	}, function() {
		console.dir(arguments);
	});
}

function queryFund() {
	mui.$get("/fund/info", {}, function(data) {
		if (data.canBuy == '1') {
			$("#fundContainer").html(fundTmpl(data));
		}
	});
}
/**
 * 将查询到的产品列表插入到DOM中 
 * @param {Object} list
 */
function insertToDom(list) {
	if (list) {
		//		var data = list[0];
		$('#productContainer').html("");
		for (var i = 0; i < list.length; i++) {
			//			if (data.rewardRate < list[i].rewardRate) {
			//				data = list[i];
			//			}
			$('#productContainer').append(template(list[i]));
		}
	}
}

//------------------------------------------------------------
var template = mui.$template($("#producTmpl").html());
var fundTmpl = mui.$template($("#fundTmpl").html());
mui.init({
	swipeBack: true //启用右滑关闭功能
});
var slider = mui("#slider");
slider.slider({
	interval: 3000
});
querySubjects();
queryFund();
