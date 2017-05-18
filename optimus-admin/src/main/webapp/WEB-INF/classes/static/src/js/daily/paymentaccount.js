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
require('app').register.controller('paymentAccountController', function ($scope, $myhttp, $timeout, $location) {
    var param = $scope.param = $location.search();

    $scope.orders = []; //订单编号

    $scope.loading = false;

    $scope.overview = {};


    var pagination = $scope.pagination = {
        currentPage: 1, totalPage: 0
    };


    $scope.query = function (page) {
        $scope.loading = true;
        var p = {};
        p.pageNum = page || 1;
        p.pageSize = 10;
        p.date = $scope.param.date;
        p.type = $scope.param.type;
        $myhttp('loading', $scope).get("/account/trade", p, function (result) {
            pagination.totalPage = result.pageCount;
            pagination.currentPage = result.pageIndex;
            $scope.orders = result.result;
        });
    };


    function refresh() {
        $scope.query();
//        $scope.loading = true;
//        var p = {};
//        p.date = $scope.param.date;
//        $myhttp('loading', $scope).get('/account/tradeoverview', p, function (result) {
//            $scope.overview = result;
//        });
    }

    $timeout(refresh, 300);

});