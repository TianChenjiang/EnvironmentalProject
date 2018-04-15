App({
  onLaunch: function () {
    console.log('App Launch')
  },
  onShow: function () {
    console.log('App Show')
  },
  onHide: function () {
    console.log('App Hide')
  },
  globalData: {
    hasLogin: false,
    backendUrl: "http://139.199.222.72:8080/",
    backendSupplierUrl: "http://139.199.222.72:8080/",
    PictureUrl:""
  }
});