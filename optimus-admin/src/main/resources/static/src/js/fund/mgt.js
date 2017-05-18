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

require('app').register.controller('fundMgtController', function ($scope, $myhttp, $timeout) {
    $scope.loading = false;

    $scope.fund = {};

    $scope.query = function () {
        $scope.loading = true;
        var param = {};
        console.info("请求参数", param);
        $myhttp('loading', $scope).get('/fund/info', param, function (result) {
            setFund(result);
        });
    };

    $scope.save = function () {
        $scope.loading = true;
        var param = $scope.fund;
        //param.canBuy = $scope.fund.canBuy ? 1 : 0;
        console.info("保存参数", param);
        $myhttp('loading', $scope).post('/fund/saveInfo', JSON.stringify(param), function (result) {
            setFund(result);
        });
    };

    function setFund(result) {
        result.minAmount = parseFloat(result.minAmount || 0);
        result.tenThousandProfit = parseFloat(result.tenThousandProfit || 0);
        result.returnRate7 = parseFloat(result.returnRate7 || 0);
        $scope.fund = result;
    }

    //初始加载
    $timeout($scope.query, 1000);
});