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
    backendUrl: "http://localhost:8080/",
    backendSupplierUrl: "http://localhost:8080/",
    PictureUrl:""
  }
});