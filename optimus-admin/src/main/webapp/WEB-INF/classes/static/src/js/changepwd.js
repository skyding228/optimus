/**
 *
 * 作者：weich 邮箱：1329555958@qq.com 日期：2016/3/21
 *
 * 未经作者本人同意，不允许将此文件用作其他用途。违者必究。 产品列表
 *
 * @ngdoc
 * @author weich
 * @name Role
 * @description
 */

require('app').register.controller('changepwdController', function ($scope, $myhttp, $timeout) {
    $scope.pwd = '';
    $scope.newPwd = '';
    $scope.confirmPwd = '';
    $scope.errorText = '';

    $scope.loading = false;
    $scope.change = function () {
        if (!$scope.pwd) {
            $scope.errorText = "请输入原始密码";
            return;
        }
        if (!$scope.newPwd) {
            $scope.errorText = "请输入新秘密";
            return;
        }
        if (!$scope.confirmPwd) {
            $scope.errorText = "请输入确认密码";
            return;
        }
        if ($scope.newPwd !== $scope.confirmPwd) {
            $scope.errorText = "两次输入密码不一致";
            return;
        }
        if ($scope.newPwd.length > 20 || $scope.newPwd < 6) {
            $scope.errorText = "请输入6-20位的密码";
            return;
        }
        $scope.loading = true;
        $scope.errorText = '';
        var param = {};
        param.payPasswd = hex_sha1($scope.pwd);
        param.type = hex_sha1($scope.newPwd);
        $myhttp('loading', $scope).post('/changepwd', JSON.stringify(param), function (result) {
            if (!result.url) {
                $scope.errorText = result.msg;
            } else {
                window.location.href = APPROOT + result.url;
            }
        });
    };
    require(['sha1']);
});