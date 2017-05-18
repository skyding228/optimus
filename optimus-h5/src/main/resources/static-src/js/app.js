mui("body").on("tap", "a", function (e) {
    var t = this;
    if (t.href && t.href.indexOf(".html") > 0) {
        var href = t.getAttribute('href');
        mui.openWindow({
            url: href,
            id: href
        })
        mui.preventDefault(e);
    }
});
// app 根路径，包含app名称
window.APPROOT = (function () {
    var i = window.location.pathname.indexOf('static');
    i = i == -1 ? window.location.pathname.indexOf('index') : i;
    return window.location.pathname.substring(0, i - 1) || "";
})();
/**
 *  去个人中心
 */
function GOME() {
    window.location.href = APPROOT + "/static/me.html";
}

function _error(xhr, errorType, err) {
    var resp = JSON.parse(xhr.responseText);
    if (resp.errmsg === "NEED_LOGIN") {
        window.location.href = resp.reason;
    }
}
// 自定义ajax请求方法
mui.$get = function (url, data, success, complete, error) {
    url = APPROOT + url;
    $.ajax({
        type: "get",
        url: url,
        async: true,
        data: data,
        dataType: 'json',
        cache: false,
        success: function (data, status, xhr) {
            success && success.apply(window, arguments);
        },
        complete: function (xhr, status) {
            complete && complete.apply(window, arguments);
        },
        error: function (xhr, errorType, err) {
            _error(xhr);
            error ? error.apply(window, arguments) : mui.toast("出了点小问题,请稍候重试!");
        }
    });
};

mui.$post = function (url, data, success, complete, error) {
    url = APPROOT + url;
    $.ajax({
        type: "post",
        url: url,
        async: true,
        data: JSON.stringify(data),
        dataType: 'json',
        headers: {
            "content-type": "application/json;charset=UTF-8",
            "accept": "application/json"
        },
        success: function (data, status, xhr) {
            success && success.apply(window, arguments);
        },
        complete: function (xhr, status) {
            complete && complete.apply(window, arguments);
        },
        error: function (xhr, errorType, err) {
            _error(xhr);
            error ? error.apply(window, arguments) : mui.toast("出了点小问题,请稍候重试!");
        }
    });
};
/**
 * 自定义 模板方法
 *
 * @param {String}
 *            tmpl
 * @return {Function}
 */
mui.$template = function (tmpl) {
    var express = [];
    var reg = /\{.*?\}/g; // 正则匹配
    express = tmpl.match(reg); // 找出所有的表达式

    var strs = tmpl.split(reg); // 用表达式分隔开字符串
    return function (data) {
        var value = [];
        var i = 0;
        if (express && express.length) {
            for (; i < express.length; i++) {
                value.push(strs[i]);
                value.push(mui.$getProp(data, express[i]));
            }
        }
        value.push(strs[i]);
        return value.join("");
    }
}

/**
 * 获取data 中表达式为expr的值
 *
 * @param {Object}
 *            data
 * @param {Object}
 *            expr 大括号括起来的 { 点分隔的表达式 }
 */
mui.$getProp = function (data, expr) {
    // 去掉大括号
    expr = expr.replace(/[\{\}]/g, "");
    var express = expr.split(".");
    var val = data;
    for (var i = 0; i < express.length; i++) {
        val = val[express[i].trim()];
        val = val === undefined ? "" : val;
    }
    return val;
}

/**
 * 从url中获取请求参数
 *
 * @param {Object}
 *            name
 */
mui.$getParam = function (name) {
    var search = window.location.search;
    var reg = /\w*=\w*/g;
    var params = search.match(reg);
    for (var i = 0; i < params.length; i++) {
        var arr = params[i].split("=");
        if (arr[0] === name) {
            return arr[1];
        }
    }
    return null;
}
var _colors = ['#FCE71E', '#5D8BF9', '#FF5B5A', '#F8B21E', '#30B2CF', '#8a6de9', '#C75312', '#E5B053', '#5F0D95'];
mui.$resetListStyle = function () {
    var random = Math.floor(Math.random() * 10000);
    $('.i-list-style').each(function () {
        var color = _colors[random++ % _colors.length];
        $(this).css("background-color", color)
    });
    var random = Math.floor(Math.random() * 10000);
    $(".i-color-random").each(function () {
        var color = _colors[random++ % _colors.length];
        $(this).css("color", color);
    });
}
setTimeout(function () {
    $('a').each(function () {
        if ($(this).attr("href") === '#') {
            $(this).attr("href", "javascript:void(0);")
        }
    });

}, 1000);