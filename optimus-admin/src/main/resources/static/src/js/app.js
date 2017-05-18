/**
 * Created by weichunhe on 2015/12/29.
 */
define('app', ['config', 'base'], function (config) {

    var app = angular.module('app', ['ngExtend', 'ui.router']);
    angular.CFG = config; //保存配置数据
    app.constant('VIEWS_BASE_PATH', config.views_base_path);

    app.config(function ($stateProvider, VIEWS_BASE_PATH, $controllerProvider, $filterProvider, $requireProvider, $urlRouterProvider, $provide) {

        app.register = {
            controller: $controllerProvider.register,
            filter: $filterProvider.register,
            factory: $provide.factory
        };

        //路由配置使用
        function resolve($q, url, deps) {
            var def = $q.defer();
            require(deps, function () {
                def.resolve();
            }, function () {
                def.resolve();
                console.warn(url + '没有对应的js依赖!');
            });
            return def.promise;
        }

        //处理url,添加后缀
        var suffix = config.view_suffix;

        function addSuffix(url) {
            if (url.indexOf('.') !== -1) {
                return url;
            }
            var index = url.indexOf('?');
            if (index === -1) {
                return url + suffix;
            } else {
                return url.substring(0, index) + suffix + url.substring(index);
            }
        }

        /**
         * 根据一定的规则取出依赖
         * abc/def/hg.html 以hg为依赖
         * @param url
         */
        function getDeps(url) {
            var dep = url;
            if (dep) {
                if (dep.indexOf('/') === 0) {
                    dep = dep.substring(1);
                }
                dep = dep.split(/[.\?]/)[0];
            }
            return [dep];
        }

        $urlRouterProvider.when(/^\/?$/, '/products');

        $stateProvider
        //默认规则配置
            .state('def', {
                url: '{url:[^@]*}',
                templateUrl: function ($stateParams) {
                    var url = VIEWS_BASE_PATH + $stateParams.url;
                    return addSuffix(url);
                },
                resolve: {
                    require: function ($q, $stateParams) {
                        return resolve($q, $stateParams.url, getDeps($stateParams.url));
                    }
                }
            });
    });

    return app;
});

require(['app', 'config', 'condition'], function (app, config) {
    app.controller('rootController', function ($rootScope, $timeout,$myhttp) {
        $rootScope.$on('$stateChangeSuccess',
            function (event, toState, toParams, fromState, fromParams) {
                $rootScope.$broadcast(config.EVENTS.ROUTER_CHANGE_SUCCESS, null);
                
            });
        $rootScope.random = function(){

        };
        $rootScope.member = {};
        $myhttp.get("/member",function(member){
        	$rootScope.member = member;
        });
        $rootScope.logout = function(){
        	window.location.href = APPROOT+"/logout";
        }
    });

    app.controller('menuController', function ($rest, $scope) {
        $scope.menuData = [];
        $rest.get('menu', null, function (data) {
            console.log('menu data', data);
            $scope.menuData = data;
            //$scope.$apply();
        });
    });

    angular.element(document).ready(function () {
        //阻止 # 导航
        $(document).delegate('a', 'click', function (event) {
            var href = $(this).attr('href');
            if (href === '#') {
                event.preventDefault();
            }
        });

        angular.bootstrap(document, ['app']);
    });
});



