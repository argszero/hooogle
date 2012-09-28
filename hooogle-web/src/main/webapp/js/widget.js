// 配置别名和预加载项
seajs.config({
  alias: {
    'jquery': 'https://a.alipayobjects.com/static/arale/jquery/1.7.2/jquery.js'
  },
  preload: ["jquery"]
})
// 将 jQuery 暴露到全局
seajs.modify('jquery', function(require, exports) {
  window.jQuery = window.$ = exports
})
//将指定的Component加载到Body
define(function(require) {
    var widgetName = location.search.match(/widget=([^&]*)/);
    if(widgetName.length>0&&widgetName[1]!=null){
        require.async('./'+widgetName[1],function(Widget){
            Widget.newInstance().appendTo($('body'));
        });
    }
});
