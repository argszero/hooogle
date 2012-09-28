//Widget
define(function(require, exports, module){
    var $ = require('jquery')
    var cache =  require('cache');
    var path =  require('path')
    var css =  require('css')
    var templateString = cache.get(path.resolve('../html/shell.html',module.uri));
    exports.newInstance = function(){
        css.add(path.resolve('../css/shell.css',module.uri))
        var shell = $(templateString)
        var commandLine = $('input',shell)
        commandLine.click(function(){alert(1);});
        return shell
    };
});