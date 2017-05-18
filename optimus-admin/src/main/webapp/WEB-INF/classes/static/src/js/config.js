/**
 * Created by weichunhe on 2015/12/29.
 */
define('config', function () {
    var config = {};

    config.views_base_path = APPROOT + 'static/asset/views'; // 视图根目录
    config.view_suffix = '.html'; // 视图后缀
    config.load_interface_url = '/data/interface.json'; // 加载接口数据的url

    /*事件*/
    var events = config.EVENTS = {};
    events.ROUTER_CHANGE_SUCCESS = "routerChangeSuccessEvent";

    return config;
});