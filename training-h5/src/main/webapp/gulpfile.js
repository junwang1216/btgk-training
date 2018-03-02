var gulp = require('gulp');
var stylus = require('gulp-stylus');
var watch = require('gulp-watch');
var notify = require('gulp-notify');
var plumber = require('gulp-plumber');
var rename = require('gulp-rename');
var through = require('through');
var uglify = require('gulp-uglify');
var minify = require('gulp-clean-css');
var clean = require('gulp-clean');
var nib = require('nib');
var jshint = require('gulp-jshint');

/*=============================================================================================================================================*/
/* css gulp */

gulp.task('clean-files', function() {
    gulp.src('./Content/dest/*', {read: false})
        .pipe(plumber({errorHandler: notify.onError('error message: <%= error.message %>')}))
        .pipe(clean({force: true}))
        .pipe(notify({
            message: '<%= file.relative %> has bean removed successful',
            title: 'clean files'}));
});

gulp.task('stylus-compile', function() {
    gulp.src(['./Content/stylus/*/*.styl'])
        .pipe(plumber({errorHandler: notify.onError('error message: <%= error.message %>')}))
        .pipe(stylus({use: [nib()]}))
        .pipe(gulp.dest('./Content/dest/css'))
        .pipe(notify({
            message: '<%= file.relative %> stylus compiled successful',
            title: 'stylus compile'}))
        .pipe(rename({suffix: '.min'}))
        .pipe(minify())
        .pipe(gulp.dest('./Content/dest/css'))
        .pipe(plumber.stop())
        .pipe(notify({
            message: '<%= file.relative %> stylus minimised successful',
            title: 'stylus compile'}));
});

gulp.task('scripts-compile', function(){
    return gulp.src(['./Content/third/*/**.js'])
        .pipe(plumber({errorHandler: notify.onError('error message: <%= error.message %>')}))
        .pipe(gulp.dest('./Content/dest/third'))
        .pipe(notify({
            message: '<%= file.relative %> scripts compiled successful',
            title: 'scripts compile'}))
        .pipe(rename({suffix : '.min'}))
        .pipe(uglify())
        .pipe(gulp.dest('./Content/dest/third'))
        .pipe(plumber.stop())
        .pipe(notify({
            message: '<%= file.relative %> scripts minimised successful',
            title: 'scripts compile'}));
});

gulp.task('script-jshint', function() {
    gulp.src('./Content/scripts/**/*.js')
    .pipe(watch(function(files) {
        return files.pipe(jshint())
            .pipe(jshint.reporter());
    }));
});

/*=============================================================================================================================================*/
/* webpack gulp */

//var gulp = require("gulp");
var gutil = require("gulp-util");
var webpack = require("webpack");
var webpackConfig = require("./webpack.config.js");

gulp.task("js-compile", ["webpack:build"]);

gulp.task("webpack:build", function(callback) {
    var myConfig = Object.create(webpackConfig);
    myConfig.plugins = myConfig.plugins.concat(
        new webpack.DefinePlugin({
            "process.env": {
                // This has effect on the react lib size
                "NODE_ENV": JSON.stringify("production")
            }
        }),
        new webpack.optimize.DedupePlugin(),
        new webpack.optimize.UglifyJsPlugin({
            compress: {
                warnings: false
            }
        })
    );

    webpack(myConfig, function(err, stats) {
        if(err) throw new gutil.PluginError("webpack:build", err);
        gutil.log("[webpack:build]", stats.toString({
            colors: true
        }));
        callback();
    });
});

gulp.task('default', ['build']);
gulp.task('build', ['clean-files', 'stylus-compile', 'scripts-compile', 'js-compile']);
gulp.task('check', ['script-jshint']);
gulp.task('clean', ['clean-files']);
