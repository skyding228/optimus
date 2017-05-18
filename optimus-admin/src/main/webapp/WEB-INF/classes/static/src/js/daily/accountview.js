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
require('app').register.controller('accountViewController', function ($scope, $timeout, $myhttp) {

    $scope.entrys = [];

    $scope.startTime = new Date(new Date().getTime() - 30 * 24 * 3600000);
    $scope.endTime = new Date();

    $scope.loading = false;

    $scope.pagination = {currentPage: 1, totalPage: 0};

    var moment = require('moment');
    $scope.query = function (page) {
        $scope.loading = true;

        var param = {};
        param.startTime = moment($scope.startTime).format('x');
        param.endTime = moment($scope.endTime).format('x');

        param.pageNum = page || 1;
        console.log(param);
        $myhttp('loading', $scope).get('/account/changehistory', param, function (result) {
            $scope.pagination.currentPage = result.pageIndex;
            $scope.pagination.totalPage = result.pageCount;

            $scope.entrys = result.result;
        });
    };


    $timeout($scope.query, 300);
});