define(function(require, exports, module){
    var $ = require('jquery')
    exports.add = function(url){
        if (document.createStyleSheet){
            document.createStyleSheet(url)
        }else {
            $("head").append($("<link rel='stylesheet' href='"+url+"' type='text/css' media='screen' />"))
        }
    };
});