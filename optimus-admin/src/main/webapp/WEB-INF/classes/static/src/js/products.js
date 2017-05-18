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

require('app').register.controller('productsController', function ($scope, $myhttp, $timeout) {
    //分页信息
    var pagination = $scope.pagination = {};
    //查询参数
    var condition = $scope.condition = {};

    var allStatus = '03,04,05,08,10,13,15,20,99,50';
    $scope.status = allStatus;
    $scope.type = '';

    var status = $scope.statuses = [{
        text: '全部', value: allStatus
    }, {
        text: '未上架', value: '0'
    }, {
        text: '已上架', value: '1'
    }, {
        text: '已开售', value: '05'
    }, {
        text: '已满标', value: '10'
    }, {
        text: '还款中', value: '15'
    }, {
        text: '已完结', value: '20'
    }];
    var types = $scope.types = [{
        text: '全部', value: ''
    }];

    $scope.loading = false;
    $scope.shelvesLoading = {}; //上下架时加载

    $scope.products = [];


    $scope.query = function (page) {
        var param = {};
        $scope.loading = true;

        param.pageNum = page || 1;
        param.pageSize = 10;
        //选择了状态
        if ($scope.status !== '') {
            if ($scope.status === '0') {
                param.subjectTag = '0';
            } else if ($scope.status === '1') {
                param.subjectTag = '1';
            } else if ($scope.status !== '1') {
                //param.subjectTag = '1';
                param.status = $scope.status;
            }
        }
        console.info("请求参数", param);
        $myhttp('loading', $scope).get('/product/list', param, angular.ajaxIsSuccess(function (result) {
            //分页信息
            pagination.currentPage = result.pageIndex;
            pagination.totalPage = result.pageCount;

            setProducts(result.result);
        }));
    };
    //产品上下架
    $scope.shelves = function (prd) {
        var param = {subjectNo: prd.subjectNo, subjectTag: (prd.subjectTag - 0) ? '0' : '1'};
        $scope.shelvesLoading[param.subjectNo] = true;
        $myhttp('shelvesLoading.' + param.subjectNo, $scope).post('/product/shelves', JSON.stringify(param), angular.ajaxIsSuccess(function (result) {
            prd.subjectTag = param.subjectTag;
            canShelves();
        }));
    };

    function setProducts(result) {
        $scope.products = result || [];
        canShelves();
    }

    function canShelves() {
        _.each($scope.products, function (prd) {
            //判断显示上架还是下架,未到投标开始日期并且标的状态为未招标的标的才能修改
            var time = new Date().getTime();
            if (prd.status === '03' || prd.status === '05') {
                prd.canEdit = true;
                if (prd.subjectTag === '1') {
                    prd.btnText = '下架';
                    prd.btnClass = 'btn-warning';
                } else {
                    prd.btnText = '上架';
                    prd.btnClass = 'btn-success';
                }
            }
        });
    }

    pagination.currentPage = 1;
    pagination.totalPage = 1;
    pagination.onSelect = function (page) {
        $scope.query(page);
    };

    //初始加载
    $timeout($scope.query, 1000);
});