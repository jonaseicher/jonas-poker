module.exports = {
  devServer: {
    disableHostCheck: true,
    // proxy: 'http://localhost:8280',
    port: 8180,
  },
  transpileDependencies: [
    'vuetify',
  ],
};
const { gitDescribeSync } = require('git-describe');

process.env.VUE_APP_GIT_HASH = gitDescribeSync().hash;
