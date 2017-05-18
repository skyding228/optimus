/**
 *
 * 作者：weich
 * 邮箱：1329555958@qq.com
 * 日期：2016/3/25
 *
 * 未经作者本人同意，不允许将此文件用作其他用途。违者必究。
 *
 * @ngdoc
 * @author          weich
 * @name            Role
 * @description
 */
require('app').register.controller('memberAccountFundViewController', function ($scope, $timeout, $myhttp, $location) {

    $scope.view = {};

    $scope.userId = $location.search().userId;

    $scope.loading = false;

    $scope.query = function (page) {
        $scope.loading = true;

        var param = {};
        param.userId = $scope.userId;

        console.log(param);
        $myhttp('loading', $scope).get('/account/overview/subject', param, function (result) {
            $scope.view = result;
            console.info(result);
        });
    };

    $timeout($scope.query, 300);
});