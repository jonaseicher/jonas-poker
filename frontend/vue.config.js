module.exports = {
  devServer: {
    disableHostCheck: true,
    port: 8180,
  },
  transpileDependencies: [
    'vuetify',
  ],
  // Change build paths to make them Maven compatible
  // see https://cli.vuejs.org/config/
  outputDir: 'target/dist',
  assetsDir: 'static',
};
