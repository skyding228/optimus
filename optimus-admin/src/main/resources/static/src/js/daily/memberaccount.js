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
require('app').register.controller('memberAccountController', function ($scope, $timeout, $myhttp) {

    $scope.accounts = [];

    $scope.userId = null;

    $scope.loading = false;

    $scope.pagination = {currentPage: 1, totalPage: 0};

    $scope.query = function (page) {
        $scope.loading = true;
        var param = {};
        param.userId = $scope.userId;
        param.pageNum = page || 1;
        console.log(param);
        $myhttp('loading', $scope).get('/account/list', param, function (result) {
            $scope.pagination.currentPage = result.pageIndex;
            $scope.pagination.totalPage = result.pageCount;
            $scope.accounts = result.result;
        });
    };

    $timeout($scope.query, 300);
});