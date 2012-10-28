define(function(require, exports, module) {
    // parameter:
    //1. method,url,callback
    //2. method,url,data,callback
    //3. method,url,pathData,data,callback
    exports.rest = function(method, url, pathData, data, callback) {
        if(typeof(arguments[2]) == "function"){
            callback = arguments[2]
            pathData =  data = {}
        }else if(typeof(arguments[2]) == "object" && typeof(arguments[3]) == "function"){
            callback = arguments[3]
            data = pathData
        }
        return $.ajax({
            url:url,
            contentType: "application/json",
            dataType: "json",
            type:method,
            data:(method === "GET" || method === "DELETE") ? data : JSON.stringify(data)
        }).done(callback);
    }
	exports.get = function(url, pathData, data, callback) {
		return exports.rest('GET', url, pathData, data, callback);
	};
	exports.post = function(url, pathData, data, callback) {
	    return exports.rest('POST', url, pathData, data, callback);
    };
})