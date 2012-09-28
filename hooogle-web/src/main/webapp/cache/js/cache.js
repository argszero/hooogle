define(function(require, exports, module){
    var $ = require('jquery')
    var cache =[]
    exports.get = function(url){
        return $.ajax({async:false,url:url}).responseText ;
    };
});