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

require('app').register.controller('fundProfitController', function ($scope, $myhttp, $timeout) {
    //分页信息
    var pagination = $scope.pagination = {currentPage: 1, totalPage: 0};

    $scope.status = '';
    $scope.memberId = '';

    var types = $scope.statuses = [{
        text: '全部', value: ''
    },
        //    {
        //    text: '初始中', value: 'INIT'
        //}, {
        //    text: '处理中', value: 'IN_PROCESS'
        //}, {
        //    text: '待分发', value: 'WAIT_DIVI'
        //}, {
        //    text: '已分发', value: 'DIVIDED'
        //},
        {
            text: '发放成功', value: 'SUCCESS'
        }, {
            text: '发放失败', value: 'FAILURE'
        }];

    $scope.loading = false;

    $scope.orders = [];

    $scope.startDate = '';

    var moment = require('moment');

    $scope.query = function (page) {
        var param = {};
        $scope.loading = true;

        param.pageNum = page || 1;
        param.pageSize = 10;

        param.time = $scope.startDate && moment($scope.startDate).format('x');
        param.memberId = $scope.memberId;

        param.orderStatus = $scope.status;

        console.info("请求参数", param);
        $myhttp('loading', $scope).get('/fund/profit', param, function (result) {
            //分页信息
            pagination.currentPage = result.pageIndex;
            pagination.totalPage = result.pageCount;

            $scope.orders = result.result;
        });
    };


    //初始加载
    $timeout($scope.query, 1000);
});