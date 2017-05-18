/**
 * Created by weichunhe on 2015/12/29.
 */

(function (_t) {
    // APPROOT 在home.html中定义，表示工程的根目录

    var min_suffix = _t.UGLIFY ? '-min' : '', // 是否使用压缩文件
        ROOT = APPROOT + 'static/asset/', BASE = ROOT + 'js/', LIB = ROOT + 'lib/', BOWER_ROOT = ROOT
            + 'bower_components/';
    var config = {
        baseUrl: BASE,
        paths: {
            jquery: BOWER_ROOT + 'AdminLTE/plugins/jQuery/jQuery-2.1.4.min',
            angular: BOWER_ROOT + 'angular/angular.min',
            route: BOWER_ROOT
            + 'angular-ui-router/release/angular-ui-router.min',
            lodash: BOWER_ROOT + 'lodash/lodash.min',
            bootstrap: BOWER_ROOT + 'AdminLTE/bootstrap/js/bootstrap.min',
            datepicker: BOWER_ROOT + 'eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min',
            moment: BOWER_ROOT + 'moment/min/moment.min',
            extend: LIB + 'angular-extend',
            base: LIB + 'base',
            sha1: LIB + 'sha1'
        },
        shim: {
            angular: {
                deps: ['jquery']
            },
            route: {
                deps: ['angular']
            },
            bootstrap: {
                deps: ['jquery']
            },
            datepicker: {
                deps: ['jquery', 'bootstrap']
            },
            extend: {
                deps: ['angular']
            },
            base: {
                deps: ['route', 'lodash', 'datepicker', 'extend']
            }
        }
    };

    requirejs.config(config);
})(this);
