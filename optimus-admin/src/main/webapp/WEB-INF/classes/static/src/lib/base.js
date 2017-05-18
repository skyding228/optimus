/**
 * Created by weichunhe on 2015/12/29.
 */
// app 根路径，包含app名称
window.APPROOT = (function () {
    var i = window.location.pathname.indexOf('index');
    i = i == -1 ? window.location.pathname.indexOf('static') : i;
    return window.location.pathname.substring(0, i - 1) || "";
})();
/**
 * JQUERY 默认ajax设置
 */
$.ajaxSetup({
    headers: {'Content-type': 'application/json;charset=UTF-8', 'accept': 'application/json'},
    beforeSend: function (xhr, setting) {
        setting.url = APPROOT + setting.url;
    }
});
//新增扩展
/**
 * 判断ajax请求是否成功了
 * @param result 响应数据
 * @param callback 成功回调
 */
angular.ajaxIsSuccess = function (callback) {
    return function (result) {
        if (result.returnCode !== 'S0001') {
            angular.info(result.returnMessage);
        } else {
            callback(result);
        }
    }
};

angular.formatNum = function (num) {
    num = num.toFixed(2);
    var splits = num.split(".");
    var str = splits[0] + "";
    var arr = [];
    for (var i = str.length; i > 0; i = i - 3) {
        arr.push(str.substring(Math.max(i - 3, 0), i));
    }
    return arr.reverse().join(",") + "." + splits[1];
};
/**
 *
 * @param msg
 * @param callback
 */
angular.alert = function (msg, callback) {
    alert(msg);
    callback && callback();
};

/**
 * 显示一些提示性信息
 * @param msg 要显示的提示信息
 * @param timeout 超时自动关闭时间，默认2000,0表示不进行自动关闭
 */
angular.info = function (msg, timeout) {
    timeout = timeout === undefined ? 2000 : timeout;
    if (!infodlg) {
        infodlg = $('#infodialog');
        infomsg = infodlg.find('#infomsg');
    }
    infomsg.text(msg);
    infodlg.show().find('.box').hide().slideDown(600);

    if (timeout > 0) {
        setTimeout(function () {
            angular.info.hide();
        }, timeout);
    }
};
var infodlg = null, infomsg = null;
//window.alert = app.info; //重写alert
window.infoDlgHide = angular.info.hide = function () {
    infodlg.find('.box').slideUp(600, function () {
        infodlg.hide();
    });
};