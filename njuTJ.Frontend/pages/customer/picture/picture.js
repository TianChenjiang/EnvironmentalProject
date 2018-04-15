var app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    Path: '',//传过来的图片地址
    recordId: "",
    tempFilePaths: "",
    Latitude: "",
    Longitude: "",
    Address: "",
    Altitude: "",
    Accuracy: ""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    //获得图片地址
    that.setData({
      tempFilePaths: app.globalData.PictureUrl
    })
    wx.chooseLocation({
      type: 'wgs84',
      success: function (res) {
        var latitude = res.latitude
        var longitude = res.longitude
        var address = res.address
        //var altitude = res.altitude
        //var accuracy = res.accuracy
        that.setData({
          Latitude: latitude,
          Longitude: longitude,
          Address: address
          //Altitude: altitude,
          //Accuracy: accuracy
        })
      }
    })
  },
  /**
  * 加载当前地址
  */


  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  /**
   * 前往人工智能识别界面
   */
  Upload: function () {
    var that = this;
    wx.uploadFile({
      url: app.globalData.backendUrl + "record/uploadImage",
      filePath: that.data.tempFilePaths[0],
      name: 'foodImage',
      success: function (res) {
        var data = JSON.parse(res.data);
        that.setData({
          recordId: data.recordId
        })
        if (data.length == 0) {
          wx.showToast({
            title: '图片上传失败',
            icon: 'warn',
            duration: 1000
          });
        }
        else {
          wx.request({
            url: app.globalData.backendUrl + "record/createRecord",
            method: "POST",
            header: {
              'content-type': 'application/json'
            },
            data: {
              recordId: that.data.recordId,
              name: "",
              lng: that.data.Latitude,
              lat: that.data.Longitude
            },
            success: function (res) {
              wx.showToast({
                title: '添加成功',
                icon: 'success',
                duration: 1000
              });
            }
          })
        }
      }
    })
  },

  /**
   * 返回拍摄界面
   */
  Back: function () {
    var that = this;
    wx.chooseImage({
      count: 1, // 默认9 
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有 
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有 
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片 
        that.setData({
          tempFilePaths: res.tempFilePaths
        })
      }
    })

  },
})