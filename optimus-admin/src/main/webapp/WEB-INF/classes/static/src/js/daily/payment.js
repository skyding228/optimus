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
require('app').register.controller('paymentContorller', function ($scope, $myhttp, $timeout, $location) {
    $scope.orderId = $location.search().orderId; //查看具体的某个订单
    $scope.loading = false;
    $scope.loadingMakeRecord = false; //生成出入款记录

    $scope.date = new Date();

    //待执行的出入款记录
    $scope.totalWaitExecs = 0;

    $scope.payment = {};

    $scope.checkboxs = {};

    $scope.allIsCheck = false;

    $scope.orders = [];

    /**
     * 全部选中
     * @param onlyValid 只是进行校验并不实际执行
     */
    $scope.checkAll = function (onlyValid) {
        if ($scope.orderId) {
            return;
        }
        $scope.orders = [].concat($scope.payment.ins || []).concat($scope.payment.outs || []);
        //如果有没选中的
        onlyValid && ($scope.allIsCheck = !_.find($scope.orders, function (o) {
            return !$scope.checkboxs[o.id];
        }));

        onlyValid || _.each($scope.orders, function (o) {
            $scope.checkboxs[o.id] = $scope.allIsCheck;
        });
        netting();
    };

    $scope.check = function (id) {
        $scope.checkAll(true);
    };

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

    //进行轧差
    function netting() {
        var inM = 0, outM = 0, netM = 0;
        _.each($scope.payment.ins || [], function (p) {
            if ($scope.checkboxs[p.id]) { //选中的
                inM = p.amount - 0 + inM;
            }
        });

        _.each($scope.payment.outs || [], function (p) {
            if ($scope.checkboxs[p.id]) { //选中的
                outM = p.amount - 0 + outM;
            }
        });

        if (inM > outM) {
            $scope.payment.nettingV = "入款";
            netM = inM - outM;
        } else {
            $scope.payment.nettingV = "出款";
            netM = outM - inM;
        }
        console.log(inM, outM, netM);
        $scope.payment.inV = angular.formatNum(inM);
        $scope.payment.outV = angular.formatNum(outM);
        $scope.payment.nettingMoneyV = angular.formatNum(netM);
    }


    $scope.refresh = function () {
    	$scope.checkboxs = {};
    	$scope.allIsCheck = false;
        $scope.loading = true;
        $myhttp('loading', $scope).get('/payment/waitpayment', {orderId: $scope.orderId}, function (result) {
            $scope.payment = result;
            $scope.checkAll();
        });

        //查询待执行的出入款记录结果
        $myhttp.get("/payment/waitexecs", null, function (result) {
            //待执行的出入款记录
            $scope.totalWaitExecs = result && _.isArray(result) ? result.length : 0;
        })
    };

    $scope.makeRecord = function () {
        var ids = getCheckedOrderIds();
        if (!ids) {
            angular.info("请选择记录!");
            return;
        }
        $scope.loadingMakeRecord = true;
        $myhttp("loadingMakeRecord", $scope).get("/payment/order", {ids: ids}, function (result) {
            $scope.refresh();
        });
    };
    $timeout($scope.refresh, 300);
});