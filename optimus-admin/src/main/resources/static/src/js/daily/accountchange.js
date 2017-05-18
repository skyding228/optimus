/**
 *
 * 作者：weich
 * 邮箱：1329555958@qq.com
 * 日期：2016/3/24
 *
 * 未经作者本人同意，不允许将此文件用作其他用途。违者必究。
 *
 * @ngdoc
 * @author          weich
 * @name            Role
 * @description
 */
require('app').register.controller('accountChangeController', function ($scope, $location, $timeout, $myhttp) {

    var param = $scope.param = {};

    var MANUAL_OUT = 'chan_manual_out', MANUAL_IN = 'chan_manual_in';
    $scope.orders = []; //待执行记录
    $scope.waitOrders = []; //待对方审核记录

    $scope.reset = function () {
        param.orderType = MANUAL_OUT;
        param.amount = 0;
        param.outerOrderId = '';
        param.memo = '';
        param.orderId = '';
        $scope.disableInput = false;
    };

    $scope.hasError = false;
    $scope.errorText = '';

    $scope.loading = false;
    $scope.execLoading = false;
    $scope.auditLoading = false;

    $scope.action = function () {
        $scope.hasError = false;
        if (param.amount - 0 <= 0) {
            $scope.hasError = true;
            $scope.errorText = '金额需大于0';
        }
        if (!param.outerOrderId) {
            $scope.hasError = true;
            $scope.errorText = '请输入凭证号';
        }

        if ($scope.hasError) {
            return;
        }
        $scope.loading = true;
        console.log(param);
        $myhttp('loading', $scope).post("/account/payment", JSON.stringify(param), function () {
            angular.info('操作成功!');
            $scope.reset();
            refresh();
        });
    };

    $scope.queryWaitExecs = function () {
        $scope.execLoading = true;
        //查询待执行的出入款记录结果
        $myhttp('execLoading', $scope).get("/payment/waitexecs", null, function (result) {
            //待执行的出入款记录
            $scope.orders = result || [];
        });
    };

    $scope.queryWaitAudits = function () {
        $scope.auditLoading = true;
        //查询待对方审核记录
        $myhttp('auditLoading', $scope).get("/payment/waitOppositeAudits", null, function (result) {
            $scope.waitOrders = result || [];
        });
    };

    $scope.selectOrder = function (o) {
        param.orderType = o.orderType;
        param.amount = o.amount;
        param.orderId = o.orderId;
        param.memo = o.memo;
        $scope.disableInput = true;
    };

    $scope.reset();

    function refresh() {
        $scope.queryWaitExecs();
        $scope.queryWaitAudits();
    }

    $timeout(refresh, 300);


});