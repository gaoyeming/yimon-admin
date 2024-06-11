const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave:false,
  publicPath: '/',
  outputDir: '../yimon-web/src/main/resources/static',
  assetsDir: 'assets',
  productionSourceMap: false
})
