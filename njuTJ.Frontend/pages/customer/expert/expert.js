var app = getApp();
Page({
  data: {
    showList: []
  },
  onLoad: function (options) {
    var that = this;
    var restaurantId = options.restaurant;
    //根据request获得食堂id并由此获得菜单
    wx.request({
      url: app.globalData.backendUrl + "record/waitForCheck",
      method: "POST",
      success: function (res) {
        var tempList = [];
        for (var i = 0; i < res.data.length; i++) {
          listItem = {
            recordId: res.data[i].recordId,
            url: res.ata[i].url
          };
        }

        that.setData({
          showList: tempList
        })
      }
    })
  },
});