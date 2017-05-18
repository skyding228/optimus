/**
 *
 * 作者：weich
 * 邮箱：1329555958@qq.com
 * 日期：2016/3/21
 *
 * 未经作者本人同意，不允许将此文件用作其他用途。违者必究。
 *
 * @ngdoc
 * @author          weich
 * @name            Role
 * @description
 */
define(['app', 'config'], function (app, config) {
    app.controller('conditionController', function ($scope, $stateParams) {

        $scope.$on(config.EVENTS.ROUTER_CHANGE_SUCCESS, function () {
            var url = $stateParams.url;
            setActiveMenu(url);
        });
        /**
         * 根据url 高亮菜单
         * @param url
         */
        function setActiveMenu(url) {
            var $active = null;
            $('.sidebar-menu li a').each(function () {
                var $a = $(this);
                if (_.startsWith('#' + url, $a.attr('href'))) {
                    $active = $a;
                }
            });

            if ($active) {
                $('.sidebar-menu li').removeClass("active");
                $active.parent('li').addClass('active');
            }

        }

    });
});