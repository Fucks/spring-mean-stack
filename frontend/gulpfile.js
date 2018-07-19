'use strict';

var gulp = require('gulp'),
    jshint = require('gulp-jshint'),
    browserify = require('gulp-browserify'),
    concat = require('gulp-concat'),
    rimraf = require('gulp-rimraf'),
    sass = require('gulp-sass'),
    autoprefixer = require('gulp-autoprefixer'),
    filter = require('gulp-filter'),
    uglify = require('gulp-uglify'),
    flatten = require('gulp-flatten'),
    googleWebFonts = require('gulp-google-webfonts'),
    mainBowerFiles = require('main-bower-files');

// Modules for webserver and livereload
var express = require('express'),
    refresh = require('gulp-livereload'),
    livereload = require('connect-livereload'),
    livereloadport = 35729,
    serverport = 5000;

// Set up an express server (not starting it yet)
var server = express();
// Add live reload
server.use(livereload({port: livereloadport}));
// Use our 'dist' folder as rootfolder
server.use(express.static('./dist'));
// Because I like HTML5 pushstate .. this redirects everything back to our index.html
server.all('/*', function(req, res) {
  res.sendFile('index.html', { root: 'dist' });
});

// Dev task
gulp.task('dev', ['clean', 'views', 'styles', 'fonts', 'lint', 'scripts', 'browserify'], function() { });

// Clean task
gulp.task('clean', function() {
	gulp.src('./dist/views', { read: false }) // much faster
  .pipe(rimraf({force: true}));
});

// JSHint task
gulp.task('lint', function() {

	gulp.src(mainBowerFiles())
		.pipe(filter('**/*.js'))
    .pipe(concat('vendor.js'))
		.pipe(jshint())
    .pipe(uglify())
		.pipe(gulp.dest('./dist/js'));
});

gulp.task('scripts', function(){

  var jsFiles = ['!./src/app/app.js','./src/app/**/*.js'];

  gulp.src(jsFiles)
    .pipe(concat('scripts.js'))
    .pipe(jshint())
    .pipe(gulp.dest('./dist/js'));
});

// Styles task
gulp.task('styles', function() {

  gulp.src(['src/app/**/*.scss','src/app/**/*.css'])
    // The onerror handler prevents Gulp from crashing when you make a mistake in your SASS
    .pipe(sass({onError: function(e) { console.log(e); } }))
    // Optionally add autoprefixer
    .pipe(autoprefixer('last 2 versions', '> 1%', 'ie 8'))
    .pipe(concat('styles.css'))
    .pipe(gulp.dest('dist/css/'));

  gulp.src(mainBowerFiles())
    .pipe(filter('**/*.css'))
    .pipe(concat('vendor.css'))
    .pipe(gulp.dest('./dist/css'));
});

//fonts tasks
gulp.task('fonts', function(){
  let materialFontSrc = 'bower_components/material-design-icons/iconfont/*.{woff,woff2,ttf,css}';

  gulp.src([materialFontSrc])
    .pipe(flatten())
    .pipe(gulp.dest('dist/fonts/'));

});

// Browserify task
gulp.task('browserify', function() {
  // Single point of entry (make sure not to src ALL your files, browserify will figure it out)
  gulp.src(['src/app/app.js'])
  .pipe(browserify({
    insertGlobals: true,
    debug: false
  }))
  // Bundle to a single file
  .pipe(concat('bundle.js'))
  // Output it to our dist folder
  .pipe(gulp.dest('dist/js'));
});

// Views task
gulp.task('views', function() {
  // Get our index.html
  gulp.src('src/app/index.html')
  // And put it in the dist folder
  .pipe(gulp.dest('dist/'));

  // Any other view files from app/views
  gulp.src(['!src/app/index.html','src/app/**/*.html'])
  // Will be put in the dist/views folder
  .pipe(gulp.dest('dist/app/'));
});

gulp.task('watch', ['lint', 'scripts', 'styles'], function() {
  // Start webserver
  server.listen(serverport);
  // Start live reload
  refresh.listen(livereloadport);

  // Watch our scripts, and when they change run lint and browserify
  gulp.watch(['src/app/*.js', 'src/app/**/*.js'],[
    'lint',
    'scripts',
    'browserify'
  ]);
  // Watch our sass files
  gulp.watch(['src/app/**/*.scss', 'src/app/**/*.css'], [
    'styles'
  ]);

  gulp.watch(['src/app/**/*.html'], [
    'views'
  ]);

  console.log("Started http-server on port: "+ serverport);
  console.log("Go to http://localhost:"+serverport);

  gulp.watch('./dist/**').on('change', refresh.changed);

});

gulp.task('default', ['dev', 'watch']);
