/**
 * 
 * 作者：weich 邮箱：1329555958@qq.com 日期：2016/3/25
 * 
 * 未经作者本人同意，不允许将此文件用作其他用途。违者必究。
 * 
 * @ngdoc
 * @author weich
 * @name Role
 * @description
 */
require('app').register.controller('paymentDeatilController', function($scope,
		$myhttp, $timeout, $location) {
	var param = $scope.param = $location.search();

	$scope.fundRecords = []; // 基金的申购赎回记录

	$scope.paymentRecords = []; // 定期的申购记录

	$scope.repaymentRecords = []; // 还款记录

	$scope.loading = false;

	$scope.queryPage = null;

	var pagination = $scope.pagination = {
		currentPage : 1,
		totalPage : 0
	};

	function query() {
		if (!param.subjectNo) {
			angular.info('找不到相关产品!');
			return;
		}
		if (param.type === 'FUND') { // 查询基金的申购赎回
			$scope.queryPage = queryFundRecords;
		} else if (param.type === 'SUBJECT') { // 查询定期
			if(param.actionType==='ACTION_PROVISION_INVEST'){ //备付金
				$scope.queryPage = queryPaymentRecords;
			}else if(param.actionType==='ACTION_PROVISION_REPAYMENT'){
				$scope.queryPage = queryRepaymentRecords;
			} else if (param.paymentType === 'PAYMENT_OUT') {// 查询 订单状态为确认成功的申购记录
				$scope.queryPage = queryPaymentRecords;
			} else if (param.paymentType === 'PAYMENT_IN') { // 查询还款
				$scope.queryPage = queryRepaymentRecords;
			} else {
				angular.info('出入款类型不正确!');
				return;
			}
		} else {
			angular.info('产品类型不正确!');
			return;
		}
		$scope.queryPage();
	}

	// 查询申购记录
	function queryPaymentRecords(page) {
		$scope.loading = true;
		var p = {};
		p.pageNum = page || 1;
		p.subjectNo = param.subjectNo;
		p.statusList = 'PAY_SUCCESS,BID_SUCCESS';
		if (param.actionType === 'ACTION_PROVISION_INVEST') {
			p.plat = param.plat;
		}
		$myhttp('loading', $scope).get('/product/investRecords', p,
				angular.ajaxIsSuccess(function(result) {
					// 分页信息
					pagination.currentPage = result.pageIndex;
					pagination.totalPage = result.pageCount;

					$scope.paymentRecords = result.result;
				}));
	}

	// 查询还款记录
	function queryRepaymentRecords(page) {
		$scope.loading = true;
		var p = {};
		p.pageNum = page || 1;
		p.subjectNo = param.subjectNo;
		if (param.actionType === 'ACTION_PROVISION_REPAYMENT') {
			p.plat = param.toPlat;
		}

		$myhttp('loading', $scope).get('/product/repaymentRecords', p,
				angular.ajaxIsSuccess(function(result) {
					// 分页信息
					pagination.currentPage = result.pageIndex;
					pagination.totalPage = result.pageCount;

					$scope.repaymentRecords = result.result;
				}));
	}

	// 查询基金申购赎回记录
	function queryFundRecords(page) {
		$scope.loading = true;
		var p = {};
		p.pageNum = page || 1;
		// p.orderStatus = 'EXECUTED_SUCCESS';
		p.endTime = param.endTime;

		$myhttp('loading', $scope).get('/fund/orders', p, function(result) {
			// 分页信息
			pagination.currentPage = result.pageIndex;
			pagination.totalPage = result.pageCount;

			$scope.fundRecords = result.result;
		});
	}

	$timeout(query, 300);

});