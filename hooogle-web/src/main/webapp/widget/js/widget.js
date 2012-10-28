// 配置别名和预加载项
seajs.config({
  alias: {
    'jquery': '../../jquery/jquery-1.8.1.js',
    'jqueryui': '../../jqueryui/jquery-ui-1.8.23/js/jquery-ui-1.8.23.custom.min.js',
    'jqueryui.css': '../../jqueryui/jquery-ui-1.8.23/css/smoothness/jquery-ui-1.8.23.custom.css',
    'cache': '../../cache/js/cache.js',
    'path': '../../path/js/path.js',
    'css': '../../css/js/css.js',
    'shell': '../../shell/js/shell.js',
    'shell.css': '../../shell/css/shell.css',
    'servers': '../../servers/js/servers.js',
    'servers.css': '../../servers/css/servers.css',
    'rest': '../../rest/js/rest.js',
    'ztree': '../../ztree/JQuery zTree v3.4/js/jquery.ztree.all-3.4.js',
    'ztree.css': '../../ztree/patch/patch.css',
    'catalogs': '../../catalogs/js/catalogs.js'
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
