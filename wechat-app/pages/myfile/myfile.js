// pages/myfile/myfile.js
const app  = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    currentPath: 1,       // 当前路径id
    upperPath: 1,         // 上级目录id
    files: [{}]           // 页面显示的文件数组
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // 页面初始化时加载根目录内容
    this.getMyFiles();
  },

  /**
   * 获取当前用户在当前路径下的所有文件，接收json
   */
  getMyFiles: function(){
    var _this = this;
    wx.request({
      url: app.globalData.serviceUrl + "/user_files",
      method: 'GET',
      dataType: 'json',
      data: {
        'user_id': wx.getStorageSync("user").user_id,// 当前用户id
        'parent': this.data.currentPath             // 当前路径id
      },
      success: function(response){
        _this.setData({          // 存储文件信息到本地
          files: response.data
        })
        // 如果当前不是根目录，在加载文件夹内容后调用方法 查找上级目录id
        if(_this.data.currentPath != 1){
          _this.getUpperPath();
        }
      },
      fail: function(response){
        console.log(response);
      }
    })
  },

  /**
   * 点击文件夹后打开文件夹，显示文件夹内所有内容
   * @param {} e 
   */
  openFolder: function(e){
    // 设置页面变量：当前路径id
    this.setData({
      currentPath: e.currentTarget.dataset.id
    })
    // 加载文件夹内容
    this.getMyFiles();
  },

  /**
   * 查找当前文件夹的上一级文件夹id
   */
  getUpperPath: function(){
    var _this = this;
    wx.request({
      url: app.globalData.serviceUrl + "/upperpath",
      method: 'GET',
      data:{
        'currentPath': this.data.currentPath,  // 当前文件夹id
        'user_id': wx.getStorageSync('user').user_id  // 当前用户id
      },
      success:function(response){
        _this.setData({
          upperPath: response.data              // 设置上级目录id
        })
      },
      fail: function(response){
        console.log(response);
      }
    })
  },

  /**
   * 点击返回上级目录后触发
   * 设置当前路径为上级路径，然后加载上级路径内容
   */
  toUpperPath: function(){
    this.setData({
      currentPath: this.data.upperPath
    })
    this.getMyFiles();
  },

  /**
   * 点击根目录后触发
   * 设置当前目录为根目录，然后加载根目录的内容
   */
  toRootPath: function(){
    this.setData({
      currentPath: 1
    })
    this.getMyFiles();
  },
  

  
  // 以下均是页面跳转逻辑
  toMyfile: function () {
    wx.navigateTo({
      url: '/pages/myfile/myfile',
    })
  },
  toShare: function () {
    wx.navigateTo({
      url: '/pages/share/share',
    })
  },
  toResource: function () {
    wx.navigateTo({
      url: '/pages/resource/resource',
    })
  },
  toUserinfo: function () {
    wx.navigateTo({
      url: '/pages/userinfo/userinfo',
    })
  }
})