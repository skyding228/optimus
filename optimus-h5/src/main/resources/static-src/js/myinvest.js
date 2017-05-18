/**
 * 查询账户总览信息 
 */
function queryOverview() {
	mui.$get("/invest/mysubjects", null, function(data) {
		$('#loading').hide();
		console.log(data);
		$("#notConfirmMoney").text(data.subjectNotConfirmV);
		if (data.subjectNotConfirmList) {
			$.each(data.subjectNotConfirmList, function(i, d) {
				$("#notConfirmListContainer").append(notConfirmTmplFunc(d));
			});
		}

		$('#confirmMoney').text(data.subjectConfirmV);
		if (data.subjectConfirmList) {
			$.each(data.subjectConfirmList, function(i, d) {
				$("#confirmListContainer").append(confirmTmplFunc(d));
			});
		}

		$('#finishMoney').text(data.subjectFinishV);
		if (data.subjectFinishList) {
			$.each(data.subjectFinishList, function(i, d) {
				$("#finishListContainer").append(finishTmplFunc(d));
			});
		}
		mui.$resetListStyle();
	});
}

function openContract(subjectNo) {
	mui.openWindow({
		url: APPROOT + "/action/contract/" + subjectNo + '?p=' + subjectNo,
		id: 'contractWindow'
	});
}

//---------------------------------------------
var notConfirmTmplFunc = mui.$template($("#notConfirmTmpl").html());
var confirmTmplFunc = mui.$template($("#confirmTmpl").html());
var finishTmplFunc = mui.$template($("#finishTmpl").html());
mui.init({
	swipeBack: true //启用右滑关闭功能
});

queryOverview();

mui.$resetListStyle();