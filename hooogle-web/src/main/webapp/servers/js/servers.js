//Widget
define(function(require, exports, module) {
	var $ = require('jquery')
	var cache = require('cache')
	var path = require('path')
	var templateString = cache.get(path.resolve('../html/servers.html', module.uri));
	exports.newInstance = function(config) {
	    require('jqueryui')
	    require('jqueryui.css')
		var servers = $(templateString)

		$.extend(servers, {
			init: function() {
                $('.open',this).click($.proxy(this.openCatalogDialog,this))
			},
			openCatalogDialog:function(){

			    var catalogs = require('catalogs').newInstance()
			    $('<div></div>').append(catalogs).dialog()
			}
		})
		$.extend(servers, config)
		servers.init()
		return servers
	}
})

