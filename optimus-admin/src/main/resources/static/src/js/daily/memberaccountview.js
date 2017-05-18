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
require('app').register.controller('memberAccountViewController', function ($scope, $timeout, $myhttp, $location) {

    $scope.orders = [];

    $scope.statuses = [{
        text: '全部', value: 'S,F'
    }, {
        text: '成功', value: 'S'
    }, {
        text: '失败', value: 'F'
    }];
    $scope.types = [{
        text: '全部', value: ''
    }, {
        text: '充值', value: 'deposit'
    }, {
        text: '提现', value: 'withdraw'
    }, {
        text: '投资', value: 'buy'
    }, {
        text: '还款', value: 'recovery'
    }, {
        text: '退款', value: 'refund'
    }];
    $scope.orderType = $scope.types[0].value;
    $scope.orderStatus = $scope.statuses[0].value;
    $scope.date = new Date(new Date().getTime()); //默认今天
    $scope.userId = $location.search().userId;

    $scope.loading = false;

    $scope.pagination = {currentPage: 1, totalPage: 0};

    var moment = require('moment');
    $scope.query = function (page) {
        $scope.loading = true;

        var param = {};
        param.date = $scope.date && moment($scope.date).format('YYYYMMDD');
        param.type = $scope.orderType;
        param.orderStatus = $scope.orderStatus;
        param.userId = $scope.userId;

        param.pageNum = page || 1;
        console.log(param);
        $myhttp('loading', $scope).get('/account/trade', param, function (result) {
            $scope.pagination.currentPage = result.pageIndex;
            $scope.pagination.totalPage = result.pageCount;

            $scope.orders = result.result;
        });
    };

    $timeout($scope.query, 300);
});