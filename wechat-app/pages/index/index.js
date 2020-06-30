//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    username: 'jay',
    password: '123456'
  },
  onLoad: function () {
    
  },
  /**
   * 用户登录逻辑
   */
  login: function(){
    var _this = this;
    wx.request({
      url: app.globalData.serviceUrl + '/user',     // 发送请求，通过name查找用户
      method: 'GET',
      data: {username:this.data.username}, // 发送参数username
      dataType: 'json',         // 接收json
      success: function(response){
        console.log(response);
        // 判断登录密码是否正确
        if(_this.data.password==response.data.password){
          wx.setStorageSync("user", response.data); //将登录的用户信息存到本地缓存
          // 跳转到登录成功的页面
          wx.navigateTo({
            url: '/pages/userinfo/userinfo',
          })
        }
      },
      fail: function(response){
        console.log(response);
      }
    })
  }
})
