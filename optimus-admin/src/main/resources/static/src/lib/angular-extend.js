/**
 * angular Js 扩展
 * Created by weichunhe on 2015/7/6.
 */
(function (window, angular, undefined) {
    'use strict';

    angular.module('ngExtend', ['ng']).
    provider('$require', function rq() {
        /**
         * 异步加载配置
         * @param deps 如果是单个依赖可以直接写名字,多个依赖使用数组,路径根据require配置
         * @returns {*}
         */
        this.require = function (deps) {
            if (angular.isString(deps)) {
                deps = [deps];
            }
            return ['$rootScope', '$q', function ($rootScope, $q) {
                var def = $q.defer();
                require(deps, function () {
                    $rootScope.$apply(function () {
                        def.resolve();
                    });
                });
                return def.promise;
            }]
        };

        this.$get = function () {
            return this;
        }
    }).

    factory('$myhttp', function ($rootScope) {
        function apply(obj) {
             if(obj && obj.responseText && obj.responseText.indexOf('理财平台-登录') !== -1){
                 window.location.href = APPROOT+"/login";
                 return;
             }
            setTimeout(function () {
                $rootScope.$apply();
            }, 300);
        }

        function err(xhr, status, err) {
            var resp = xhr.responseJSON;
            if (resp.status === "NEED_LOGIN") {
                window.location.href = resp.reason;
                return;
            }
            var msg = resp.reason;
            var reg = new RegExp("[\\u4E00-\\u9FFF]+", "g");
            if (!reg.test(msg)) {
                msg = resp.exception_id ? ("错误编号=" + resp.exception_id) : "";
            }
            angular.info('操作失败:' + msg);
            console.error('$myhttp请求出错', xhr, status, err);
        }

        function get() {
            return $.get.apply(window, arguments).complete(apply).error(err);
        }

        function post() {
            return $.post.apply(window, arguments).complete(apply).error(err);
        }

        /**
         * 如果是事件名称，就会传播此事件，事件数据为 start，end
         * 如果有obj，此属性应为bool类型 就是将此对象上的此属性取反
         * @param name string 事件名称 或属性名称，如果传递了obj
         * @param obj object
         * @returns {{get: get, post: post}}
         */
        function http(name, obj) {
            if (angular.isString(name)) {
                if (angular.isObject(obj)) {
                } else {
                    $rootScope.$emit('HTTP_EVENT', {name: name, data: 'start'});
                }
            }
            function complete() {
                if (angular.isString(name)) {
                    if (angular.isObject(obj)) {
                        _.set(obj, name, false);
                    } else {
                        $rootScope.$emit('HTTP_EVENT', {name: name, data: 'end'});
                    }
                }
                apply.apply(null,arguments);
            }

            return {
                get: function () {
                    return $.get.apply(window, arguments).complete(complete).error(err);
                }, post: function () {
                    return $.post.apply(window, arguments).complete(complete).error(err);
                }
            };
        }

        http.get = get;
        http.post = post;

        return http;
    }).
    factory('$rest', function ($http) {
        var INF_DATA = {}, INF_INDEX = {};
        //获取接口数据
        var PGetInfData = $http.get(angular.CFG.load_interface_url);
        PGetInfData.then(successCb(function (data) {
            INF_DATA = data;
            dealInfData(INF_DATA);
            console.log('接口数据', INF_DATA, INF_INDEX);
        }), errCb);
        //接口数据处理
        function dealInfData(data) {
            _.each(INF_DATA, function (arr, key) {
                _.each(arr, function (inf, index) {
                    INF_INDEX[inf.id] = {type: key, index: index};
                });
            })
        }

        function getInfById(id) {
            if (!INF_INDEX[id]) {
                throw new Error('不存在id=' + id + '的接口!');
            }
            var index = INF_INDEX[id];
            var inf = INF_DATA[index.type][index.index];
            return inf;
        }

        function successCb(cb) {
            return function (resp) {
                var data = resp.data;
                if (data.code !== 0) {
                    console.warn(data);
                    alert(data.msg);
                    return;
                }
                cb(data.data);
            }
        }

        function errCb(resp) {
            console.error(resp);
            alert('请求出现异常!请稍候重试!');
        }

        function get(url, param, cb) {
            var p = $http.get(url, {data: param});
            p.then(successCb(cb), errCb);
            return p;
        }

        function post(url, cb) {
            $http.post(url).then(successCb(cb), errCb);
        }


        var rest = {};
        /**
         * @param id 接口id
         * @param param 参数对象，可以覆盖默认参数
         * @param callback 请求成功之后进行调用,参数是接口返回数据中的data；function(data){}
         * @param config $http的配置参数，可选，保留
         */
        rest.get = function (id, param, callback, config) {
            return PGetInfData.then(function () {
                var http = {}, inf = getInfById(id), param = _.extend({}, inf.param, param);
                get(inf.url, param, callback);
            });
        };

        return rest;
    }).
    /**
     * 日期指令,包含时间
     *  <div my-date-time="" class='input-group date'>
     <div class="input-group-addon">$</div>
     <input type='text' class="form-control"/>
     <span class="input-group-addon">
     <span class="glyphicon glyphicon-calendar"></span>
     </span>
     </div>

     linkSelector: #dtp1 日期范围的结束日期选择器
     */
    directive('myDateTime', function ($parse) {
        return {
            restrict: 'AE'
            , replace: false
            , link: function (scope, element, attrs, controller) {
                var opts = {format: 'YYYY-MM-DD HH:mm:ss', showClose: true, showTodayButton: true}, cfg, $link;
                if (attrs.myDateTime) {
                    cfg = scope.$eval(attrs.myDateTime);
                }
                //范围绑定
                if (cfg && cfg.linkSelector && $(cfg.linkSelector).length) {
                    $link = $(cfg.linkSelector);
                    delete cfg.linkSelector;
                }

                angular.isObject(cfg) && $.extend(opts, cfg);
                element.datetimepicker(opts);

                var $date = element.data('DateTimePicker');
                var ngModel = element.find('input').attr('ng-model');
                if (!ngModel) {
                    return;
                }

                var modelGetter = $parse(ngModel);
                var modelSetter = modelGetter.assign;
                var initDate = modelGetter(scope);
                if (initDate) {
                    $date.date(initDate);
                }
                //双向绑定
                scope.$watch(ngModel, function (n, o) {
                    n && $date.date(n);
                });
                var moment = window.moment || require('moment');
                element.on('dp.change', function (e) {
                    modelSetter(scope, e.date ? moment(e.date._d).format(opts.format) : '');
                    //设置linkto
                    if ($link) {
                        var $dtp = $link.data('DateTimePicker');
                        $dtp.minDate(e.date);
                    }
                });
            }
        }
    }).
    /**
     * 分页指令
     * <pagination total-page="pagination.totalPage" current-page="pagination.currentPage"
     on-select-page="query(page)"></pagination>
     */
    directive('pagination', function () {
        return {
            restrict: 'E'
            ,
            replace: true
            ,
            template: '<div ng-show="totalPage>0" class="dataTables_paginate paging_simple_numbers">                                         ' +
            '  <ul class="pagination">                                                           ' +
            '    <li class="paginate_button previous " ng-class="{disabled:noPrev()}"><a href="#" ng-click="selectPage(1)"  title="首页">1</a></li>                                ' +
            '    <li class="paginate_button previous " ng-class="{disabled:noPrev()}"><a href="#" ng-click="prev()" title="上一页"><</a></li>                                ' +
            '    <li class="paginate_button" ng-repeat=" page in pages" ng-class="{active:isActive(page)}"><a href="javascript:void(0);" ng-click="selectPage(page)">{{page}}</a></li> ' +
            '    <li class="paginate_button next" ng-class="{disabled:noNext()}"><a href="#" ng-click="next()" title="下一页">></a></li>                                ' +
            '    <li class="paginate_button next" ng-class="{disabled:noNext()}"><a href="#" ng-click="selectPage(totalPage)"  title="尾页">{{totalPage||1}}</a></li>                                ' +
            '  </ul>                                                          ' +
            '</div>                                                           '
            ,
            scope: {
                totalPage: '='
                , currentPage: '='
                , onSelectPage: '&'
            },
            link: function (scope, element, attrs, controller) {
                scope.currentPage = scope.currentPage || 1;
                scope.$watch('currentPage', function (value) {
                    scope.pages = getPageItems(scope.totalPage, value, 10);
                    if (scope.currentPage > scope.totalPage) {
                        scope.currentPage = scope.totalPage;
                    }
                });
                scope.$watch('totalPage', function (value) {
                    scope.pages = getPageItems(value, scope.currentPage, 10);
                    if (scope.currentPage > value) {
                        scope.currentPage = value;
                    }
                });
                scope.isActive = function (page) {
                    return scope.currentPage === page;
                };
                scope.selectPage = function (page) {
                    if (page < 1) {
                        page = 1;
                    }
                    if (page > scope.totalPage) {
                        page = scope.totalPage;
                    }
                    if (!scope.isActive(page)) {
                        scope.currentPage = page;
                        scope.onSelectPage({page: scope.currentPage});
                    }
                };
                scope.prev = function () {
                    scope.selectPage(scope.currentPage - 1);
                };
                scope.next = function () {
                    scope.selectPage(scope.currentPage + 1);
                };

                scope.noPrev = function () {
                    return !(scope.currentPage > 1);
                };
                scope.noNext = function () {
                    return !(scope.currentPage < scope.totalPage);
                };
            }
        }
    });

    /**
     * 获取length个要展示的页面span
     */
    function getPageItems(total, current, length) {
        var items = [];
        if (length >= total) {
            for (var i = 1; i <= total; i++) {
                items.push(i);
            }
        } else {
            var base = 0;
            //前移
            if (current - 0 > Math.floor((length - 1) / 2)) {
                //后移
                base = Math.min(total, current - 0 + Math.ceil((length - 1) / 2)) - length;
            }
            for (var i = 1; i <= length; i++) {
                items.push(base + i);
            }
        }
        return items;
    }

})
(window, window.angular);
