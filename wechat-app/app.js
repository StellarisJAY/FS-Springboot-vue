//app.js
App({
  globalData: {
    serviceUrl: 'http://192.168.0.106:8001'
  },
  toMyfile: function(){
    wx.navigateTo({
      url: '/pages/myfile/myfile',
    })
  }
})