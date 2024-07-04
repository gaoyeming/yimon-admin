const { defineConfig } = require('@vue/cli-service')
const webpack = require('webpack');

module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  publicPath: '/',
  outputDir: '../yimon-web/src/main/resources/static',
  assetsDir: 'assets',
  productionSourceMap: false,
  configureWebpack: {
    // 在这里添加特性标志的定义
    plugins: [
      new webpack.DefinePlugin({
        __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: false
      })
    ]
  }
})
