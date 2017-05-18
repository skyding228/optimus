//产品列表
var template = mui.$template($("#producTmpl").html());
var PageNum = 1;
var HasMore = true;
/**
 * 查询一页投资标的
 * @param {Object} page
 */
function querySubjects(pageNum, pageSize, success) {
	pageNum = pageNum || PageNum;
	pageSize = pageSize || 10;
	if (pageNum === 1) {
		$('#loading').show();
	}
	mui.$get("/invest/subjects", {
		pageSize: pageSize,
		pageNum: pageNum
	}, function(data) {
		console.log(data);
		HasMore = hasMore(data);
		insertToDom(data.result);
		success && success.call(window, data);
		PageNum = data.pageIndex + 1; //记录当前页
		mui.$resetListStyle();
	}, function() {
		$('#loading').hide();
	}, function() {
		mui.toast('网络异常,请再次刷新!');
		console.dir(arguments);
	});
}
/**
 *判断是否还有更多数据 
 * @param {Object} data 
 {"itemCount": 1,
  "pageCount": 1,
  "pageSize": 10,
  "pageIndex": 1
 }
 * 
 * @return {boolean}
 */
function hasMore(data) {
	if (data.pageIndex * data.pageSize < data.itemCount) {
		return true;
	}
	return false;
}
/**
 * 将查询到的产品列表插入到DOM中 
 * @param {Object} list
 */
function insertToDom(list) {
	if (list && list.length) {
		for (var i = 0; i < list.length; i++) {
			$('#uploadDiv div.mui-pull-bottom-pocket').before(template(list[i]));
		}
	}
}


//------------------------------------------------------------
mui.init({
	pullRefresh: {
		container: '#uploadDiv', //待刷新区域标识，querySelector能定位的css选择器均可，比如：id、.class等
		up: {
			contentrefresh: "正在加载...", //可选，正在加载状态时，上拉加载控件上显示的标题内容
			contentnomore: '没有更多数据了', //可选，请求完毕若没有更多数据时显示的提醒内容；
			callback: function() { //必选，刷新函数，根据具体业务来编写，比如通过ajax从服务器获取新数据；
				var t = this;
				if (HasMore) {
					querySubjects(null, 5, function(data) {
						t.endPullupToRefresh(!HasMore);
					});
				} else {
					t.endPullupToRefresh(!HasMore);
				}

			}
		}
	}
});
querySubjects();
