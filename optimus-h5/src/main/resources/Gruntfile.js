  module.exports = function (grunt) {
  grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        concat: {
            dist: {
                src: ['lib/js-min/*.js', '!lib/js-min/require*.js'],
                dest: 'lib/js-min/app.js'
            }
        },
        useminPrepare: {
             foo:{
                src: '*.html',
                root: 'static'
             } 
         },
         clean: ["static/**"],
         copy:{
             main: {
                files: [
                  {expand: true,cwd:'static-src', src: ['**'], dest: 'static/'}
                ]
              }
         },
        uglify: {
            options: {
                banner: '/*! <%= pkg.name %> <%= grunt.template.today("yyyy-mm-dd HH") %> */\n',
                mangle: false,
                report: "min"
            },
            build: {
                files: [{
                    expand: true,
                    cwd: 'static-src',
                    src: ['!**/*.min.js','**/*.js'],
                    dest: 'static'
                    //ext: '.min.js'
                }]
            }
            
        },
        cssmin: {
            target: {
                files: [{
                    expand: true,
                    cwd: 'static-src',
                    src: ['**/*.css', '!**/*.min.css'],
                    dest: 'static'
                }]
            }
        },
        htmlmin: {                                     // Task
            dist: {                                      // Target
                options: {                                 // Target options
                    removeComments: true,
                    collapseWhitespace: true
                },
                files: [{
                    expand: true,
                    cwd: 'static',
                    src: ['**/*.html', '!**/*.min.html'],
                    dest: 'static'
                }]
            }
        },
        filerev: {
            options: {
              algorithm: 'md5',
              length: 8
            },
            assets: {
              files: [{
                    src: [
                        'static/**/*.{css,jpg,jpeg,gif,png,js,html}'
                    ]   
              }]
            }
        },
        usemin: {
          html:'static/*.html',
          options: {
            assetsDirs: ['static']
          }
        },
        replace: {
          html: {
            src: ['static/*.html'],
            overwrite: true,                 // overwrite matched source files 
            replacements: [{
              from:/(\w+\.)+(css|jpg|jpeg|gif|png|js|html)/g,
              to: function (matchedWord, index, fullText, regexMatches) {
                    var map = grunt.filerev.miniMap;
                    //grunt.log.writeln(matchedWord+":"+index+":"+map[matchedWord]);
                    return map[matchedWord] || matchedWord;
              }
            }]
          },
          jsp: {
            src: ['application-src/*.jsp'],
            dest:'application/',
            replacements: [{
              from:/(\w+\.)+(css|jpg|jpeg|gif|png|js|html)/g,
              to: function (matchedWord, index, fullText, regexMatches) {
                    var map = grunt.filerev.miniMap;
                    grunt.log.writeln(matchedWord+":"+index+":"+map[matchedWord]);
                    return map[matchedWord] || matchedWord;
              }
            }]
          }
        }
    });

  grunt.registerTask('foo', 'A sample task that logs stuff.', function(arg1, arg2) {
        var fs = require('fs');
        function fileName(str){
            var i = str.lastIndexOf("/");
            if (i !== -1) {
                return str.substring(i+1);
            }
            i = str.lastIndexOf("\\");
            if (i !== -1) {
                return str.substring(i+1);
            };
            return str;
        }
        var map = {};
        for(var k in grunt.filerev.summary){
            map[fileName(k)] = fileName(grunt.filerev.summary[k]);
        }
        grunt.filerev.miniMap = map;
        fs.writeFileSync("revSourceMap.txt",JSON.stringify(map));
        grunt.log.writeln("success");
    });

    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-htmlmin');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-text-replace');
    grunt.loadNpmTasks('grunt-filerev');
    grunt.registerTask('build', ['clean','copy','uglify', 'cssmin','filerev','foo', 'replace','htmlmin']);
    grunt.registerTask('default', ['clean','copy','uglify', 'cssmin','filerev','foo', 'replace','htmlmin']);
}