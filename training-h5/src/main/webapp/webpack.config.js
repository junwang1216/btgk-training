var webpack = require('webpack');
//var HtmlWebpackPlugin = require('html-webpack-plugin');
var glob = require('glob');
var path = require('path');

function getEntry() {
    var entry = {};

    glob.sync('./Content/scripts/*(student|teacher)/**/*.js').forEach(function (dir) {
        var name = path.basename(dir, '.js');
        var d = dir.replace('./Content/js/', '');
        d = path.dirname(d);
        var prop = [d, name].join('/');
        entry[prop] = dir;
    });

    return entry;
}

module.exports = {
    entry: getEntry(),
    output: {
        context: './',
        path: './Content/dest/scripts/',
        filename: '/[name].js',
        chunkFilename: '/chunk/[name].js'
    },
    module: {
        loaders: [{
            test: /\.html$/,
            loader: 'html'
        }]
    },
    resolve: {
        root: path.resolve('./'),
        extensions: ['', '.js', '.html'],
        alias: {
            vue: 'Content/bower_components/vue/dist/vue.js',
            resource: 'Content/bower_components/vue-resource/dist/vue-resource-loan.js',
            loading: 'Content/scripts/widgets/loading.js',
            alert: 'Content/scripts/widgets/alert.js',
            common :'Content/scripts/widgets/common.js',
            protocols: 'Content/scripts/widgets/protocols/index.js',
            step: 'Content/scripts/widgets/step/index.js',
            sms: 'Content/scripts/widgets/sms'
        }
    },
    plugins: [
        new webpack.optimize.CommonsChunkPlugin({
            name: 'vendor',
            minChunks: 2
        })
    ]
};
