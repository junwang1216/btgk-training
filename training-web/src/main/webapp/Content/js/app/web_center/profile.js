requirejs.config({
    baseUrl: 'Content/',
    paths: {
        "jquery"    : 'bower_components/jquery/dist/jquery',

        "base"      : 'js/app/web/base'
    },  // 依赖关系
    waitSeconds: 0,
    urlArgs: '_=' + new Date().getTime()
});

require(['jquery', 'base'], function ($) {
    'use strict';

    console.log('体大资料库，专业的提供您所需要的文献资料。');
});
