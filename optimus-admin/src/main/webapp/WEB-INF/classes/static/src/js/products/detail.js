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
require('app').register.controller('prdDetailController', function ($scope, $location, $timeout, $myhttp) {
    var id = $location.search().id;

    if (!id) {
        angular.alert("缺少产品编号!", function () {
            window.history.back(); //回退
        });
    }

    //基本信息
    var baseInfo = $scope.baseInfo = {};
    //投资记录
    var investRecords = $scope.investRecords = [];
    //还款记录
    var repaymentRecords = $scope.repaymentRecords = [];
    //分页参数
    var investPagination = $scope.investPagination = {currentPage: 1, totalPage: 0};
    var repaymentPagination = $scope.repaymentPagination = {currentPage: 1, totalPage: 0};

    $scope.repaymentInfo = {
        totalPrincipalAndInterest: 0 //还款总额
        , totalInterest: 0 //总利息
    }; //还款信息

    $scope.product = {};
    $scope.noTheProduct = false;

    $scope.loading = {
        baseInfo: false,
        invest: false,
        repayment: false
    };
    // 查询基本信息
    $scope.queryBaseInfo = function () {
        $scope.loading.baseInfo = true;
        var param = {subjectNo: id};
        console.log("基本信息请求参数", param);
        $myhttp('loading.baseInfo', $scope).get('/product/list', param, angular.ajaxIsSuccess(function (result) {
            if (result.result && result.result.length) {
                $scope.product = result.result[0];
            } else {
                $scope.noTheProduct = true;
            }
        }));
    };


    //申购订单状态
    /**
     * INIT("INIT", "初始状态"), PAY_SUCCESS("PAY_SUCCESS", "支付成功"), REFUNDING(
     "REFUNDING", "退款中"), REFUND_SUCCESS("REFUND_SUCCESS", "退款成功"), BID_SUCCESS(
     "BID_SUCCESS", "投资成功"), BID_FAIL("BID_FAIL", "投资失败"), ;
     */
    var investStatuses = $scope.investStatuses = [{
        text: '全部', value: 'REFUNDING,BID_FAIL,INIT,PAY_SUCCESS,BID_SUCCESS,REFUND_SUCCESS'
    }, {
        text: '待支付', value: 'INIT'
    }, {
        text: '支付成功', value: 'PAY_SUCCESS'
    }, {
        text: '确认成功', value: 'BID_SUCCESS'
    }, {
        text: '已退款', value: 'REFUND_SUCCESS'
    }];
    $scope.orderStatus = investStatuses[0].value; //默认选择全部
    $scope.startDate = new Date(new Date().getTime() - 30 * 24 * 3600000);
    $scope.endDate = new Date();

    var moment = require('moment');
    //查询申购记录
    $scope.queryInvestRecords = function (page) {
        var param = {};
        $scope.loading.invest = true;

        param.pageNum = page || 1;
        param.pageSize = 10;

        param.subjectNo = id;
        param.statusList = $scope.orderStatus;

        param.startTime = moment($scope.startDate).format('x');
        param.endTime = moment($scope.endDate).format('x');
        console.info("申购记录请求参数", param);
        $myhttp('loading.invest', $scope).get('/product/investRecords', param, angular.ajaxIsSuccess(function (result) {
            //分页信息
            investPagination.currentPage = result.pageIndex;
            investPagination.totalPage = result.pageCount;

            $scope.investRecords = result.result;
        }));

    };


    //查询还款记录
    $scope.queryRepaymentRecords = function (page) {
        var param = {};
        $scope.loading.repayment = true;

        param.pageNum = page || 1;
        param.pageSize = 10;

        param.subjectNo = id;

        console.info("还款记录请求参数", param);
        $myhttp('loading.repayment', $scope).get('/product/repaymentRecords', param, angular.ajaxIsSuccess(function (result) {
            //分页信息
            repaymentPagination.currentPage = result.pageIndex;
            repaymentPagination.totalPage = result.pageCount;

            $scope.repaymentRecords = result.result;
            $scope.repaymentInfo = result;
        }));

    };

    $timeout(function () {
        $scope.queryBaseInfo();
        $scope.queryInvestRecords();
        $scope.queryRepaymentRecords();
    }, 300);
});