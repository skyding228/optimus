

//--------------------------------
var productId = mui.$getParam("id");


//TODO 查询 产品详情 判断产品类型 进行针对性数据显示

if(productId == 1){ //基金
	$('nav').show();
	$("#orderDetailContainer").html($('#fundTmpl').html());
}else{ //定期
	$('nav').hide();
	$("#orderDetailContainer").html($('#productTmpl').html());
}
