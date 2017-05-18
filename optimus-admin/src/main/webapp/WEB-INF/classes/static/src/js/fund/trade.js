/**
 *
 * 作者：weich
 * 邮箱：1329555958@qq.com
 * 日期：2016/3/21
 *
 * 未经作者本人同意，不允许将此文件用作其他用途。违者必究。
 * 产品列表
 * @ngdoc
 * @author          weich
 * @name            Role
 * @description
 */

require('app').register.controller('fundTradeController', function ($scope, $myhttp, $timeout) {
    //分页信息
    var pagination = $scope.pagination = {currentPage: 1, totalPage: 0};

    $scope.type = '';
    $scope.memberId = '';

    var types = $scope.types = [{
        text: '全部', value: ''
    }, {
        text: '申购', value: 'INVEST'
    }, {
        text: '赎回', value: 'REDEEM'
    }];

    $scope.loading = false;

    $scope.orders = [];

    $scope.startDate = new Date(new Date().getTime() - 24 * 3600000);
    $scope.endDate = new Date();

    var moment = require('moment');

    $scope.query = function (page) {
        var param = {};
        $scope.loading = true;

        param.pageNum = page || 1;
        param.pageSize = 10;
        param.orderType = $scope.type;

        param.startTime = moment($scope.startDate).format('x');
        param.endTime = moment($scope.endDate).format('x');
        param.memberId = $scope.memberId;

        console.info("请求参数", param);
        $myhttp('loading', $scope).get('/fund/orders', param, function (result) {
            //分页信息
            pagination.currentPage = result.pageIndex;
            pagination.totalPage = result.pageCount;

            $scope.orders = result.result;
        });
    };


    //初始加载
    $timeout($scope.query, 1000);
});