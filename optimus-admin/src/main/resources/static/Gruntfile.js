  module.exports = function (grunt) {
  grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
         clean: ["asset/css/**","asset/js/**","asset/lib/**","asset/views/**"],
         copy:{
             main: {
                files: [
                  {expand: true,cwd:'src', src: ['**'], dest: 'asset/'}
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
                    cwd: 'src',
                    src: ['!**/*.min.js','**/*.js'],
                    dest: 'asset'
                    //ext: '.min.js'
                }]
            }
            
        },
        cssmin: {
            target: {
                files: [{
                    expand: true,
                    cwd: 'src',
                    src: ['**/*.css', '!**/*.min.css'],
                    dest: 'asset'
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
                    cwd: 'src',
                    src: ['**/*.html', '!**/*.min.html'],
                    dest: 'asset'
                }]
            }
        }
    });


    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-htmlmin');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.registerTask('build', ['clean','copy','uglify', 'cssmin','filerev','foo', 'replace','htmlmin']);
    grunt.registerTask('default', ['clean','uglify', 'cssmin','htmlmin']);
}