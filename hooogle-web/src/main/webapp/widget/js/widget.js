// 配置别名和预加载项
seajs.config({
  alias: {
    'jquery': '../../jquery/jquery-1.8.1.js', //目前这句没用，因为这句要求修改jquery
    'cache': '../../cache/js/cache.js',
    'path': '../../path/js/path.js',
    'css': '../../css/js/css.js',
    'shell': '../../shell/js/shell.js'
  },
  preload: ["../../jquery/jquery-1.8.1.js"]
})
// 将 jQuery 暴露到全局
seajs.modify('jquery', function(require, exports) {
  window.jQuery = window.$ = exports
})
//将指定的Component加载到Body
define(function(require) {
    var widgetName = location.search.match(/widget=([^&]*)/);
    if(widgetName.length>0&&widgetName[1]!=null){
        require.async(widgetName[1],function(Widget){
            Widget.newInstance().appendTo($('body'))
        });
    }
});
