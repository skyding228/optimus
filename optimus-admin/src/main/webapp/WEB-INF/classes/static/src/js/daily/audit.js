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
require('app').register.controller('auditController', function ($scope, $myhttp, $timeout) {

    $scope.loading = {
        refresh: false,
        S: false, //通过
        F: false //驳回
    };

    $scope.orders = [];

    $scope.checkboxs = {};

    $scope.allIsCheck = false;
    /**
     * 全部选中
     * @param onlyValid 只是进行校验并不实际执行
     */
    $scope.checkAll = function (onlyValid) {
        //如果有没选中的
        onlyValid && ($scope.allIsCheck = !_.find($scope.orders, function (o) {
            return !$scope.checkboxs[o.orderId];
        }));

        onlyValid || _.each($scope.orders, function (o) {
            $scope.checkboxs[o.orderId] = $scope.allIsCheck;
        });

    };

    $scope.check = function (orderId) {
        //$scope.checkboxs[orderId] = !$scope.checkboxs[orderId];
        $scope.checkAll(true);
    };

    /**
     * 进行审核
     * @param type  S or F 通过或者驳回
     */
    $scope.audit = function (type) {
        var param = {};
        param.orders = getCheckedOrderIds();
        if (!param.orders) {
            angular.info("请选择要审核的记录!");
            return;
        }
        param.status = type;
        $scope.loading[type] = true;

        $myhttp('loading.' + type, $scope).get('/account/doaudit', param, function () {
            console.info('审核成功!');
            $scope.refresh();
        });
    };

    $scope.refresh = function () {
        $scope.checkboxs = {};
        $scope.allIsCheck = false;
        query();
    };

    /**
     * 查询待审核记录
     */
    function query() {
        $scope.loading.refresh = true;
        $myhttp('loading.refresh', $scope).get("/payment/waitaudits", null, function (result) {
            $scope.orders = result || [];
        });
    }

    /**
     * 获取选中的订单id
     */
    function getCheckedOrderIds() {
        var ids = [];
        _.each($scope.checkboxs, function (val, key) {
            val && ids.push(key);
        });
        return ids.join(",");
    }

    $timeout($scope.refresh, 300);
});