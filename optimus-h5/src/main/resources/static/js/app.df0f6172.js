/*! finlog 2016-05-18 09 */
function GOME(){window.location.href=APPROOT+"/static/me.html"}function _error(xhr,errorType,err){var resp=JSON.parse(xhr.responseText);"NEED_LOGIN"===resp.status&&(window.location.href=resp.reason)}mui("body").on("tap","a",function(e){var t=this;if(t.href&&t.href.indexOf(".html")>0){var href=t.getAttribute("href");mui.openWindow({url:href,id:href}),mui.preventDefault(e)}}),window.APPROOT=function(){var i=window.location.pathname.indexOf("static");return i=-1==i?window.location.pathname.indexOf("index"):i,window.location.pathname.substring(0,i-1)||""}(),mui.$get=function(url,data,success,complete,error){url=APPROOT+url,$.ajax({type:"get",url:url,async:!0,data:data,dataType:"json",cache:!1,success:function(data,status,xhr){success&&success.apply(window,arguments)},complete:function(xhr,status){complete&&complete.apply(window,arguments)},error:function(xhr,errorType,err){_error(xhr),error?error.apply(window,arguments):mui.toast("出了点小问题,请稍候重试!")}})},mui.$post=function(url,data,success,complete,error){url=APPROOT+url,$.ajax({type:"post",url:url,async:!0,data:JSON.stringify(data),dataType:"json",headers:{"content-type":"application/json;charset=UTF-8",accept:"application/json"},success:function(data,status,xhr){success&&success.apply(window,arguments)},complete:function(xhr,status){complete&&complete.apply(window,arguments)},error:function(xhr,errorType,err){_error(xhr),error?error.apply(window,arguments):mui.toast("出了点小问题,请稍候重试!")}})},mui.$template=function(tmpl){var express=[],reg=/\{.*?\}/g;express=tmpl.match(reg);var strs=tmpl.split(reg);return function(data){var value=[],i=0;if(express&&express.length)for(;i<express.length;i++)value.push(strs[i]),value.push(mui.$getProp(data,express[i]));return value.push(strs[i]),value.join("")}},mui.$getProp=function(data,expr){expr=expr.replace(/[\{\}]/g,"");for(var express=expr.split("."),val=data,i=0;i<express.length;i++)val=val[express[i].trim()],val=void 0===val?"":val;return val},mui.$getParam=function(name){for(var search=window.location.search,reg=/\w*=\w*/g,params=search.match(reg),i=0;i<params.length;i++){var arr=params[i].split("=");if(arr[0]===name)return arr[1]}return null};var _colors=["#FCE71E","#5D8BF9","#FF5B5A","#F8B21E","#30B2CF","#8a6de9","#C75312","#E5B053","#5F0D95"];mui.$resetListStyle=function(){var random=Math.floor(1e4*Math.random());$(".i-list-style").each(function(){var color=_colors[random++%_colors.length];$(this).css("background-color",color)});var random=Math.floor(1e4*Math.random());$(".i-color-random").each(function(){var color=_colors[random++%_colors.length];$(this).css("color",color)})},setTimeout(function(){$("a").each(function(){"#"===$(this).attr("href")&&$(this).attr("href","javascript:void(0);")})},1e3);