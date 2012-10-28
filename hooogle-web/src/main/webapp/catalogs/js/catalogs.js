//Widget
define(function(require, exports, module) {
	var $ = require('jquery')
	var rest = require('rest')

	exports.newInstance = function(config) {
		require('jqueryui')
		require('jqueryui.css')
		require('ztree')
		require('ztree.css')
		var catalogs = $("<div>catalogs</div>")
		$.extend(catalogs,{init:function(){
            this.loadCatalogs()
		}})
		$.extend(catalogs,config)
		id = "tree" + new Date().getTime()
		rest.get("${backend.rest}/catalog", function(data) {
			var rootNode = {
				name: "catalogs",
				id: - 1,
				isParent: true,
				children: []
			}
			var allNodes = []
			allNodes[ - 1] = rootNode
			$.each(data, function(i, catalog) {
				var node;
				if (typeof(allNodes[catalog.id]) != "object") {
					node = {
						isParent: true,
						children: []
					}
					allNodes[catalog.id] = node
				} else {
					node = allNodes[catalog.id]
				}
				$.extend(node, catalog)
				catalog.parentId = (catalog.parentId == null ? - 1: catalog.parentId)
				var parentNode = allNodes[catalog.parentId]
				if (typeof(parentNode) != "object") {
					parentNode = {
						isParent: true,
						children: []
					}
					allNodes[catalog.parentId] = parentNode
				}
				parentNode.children.push(node)
			})
			console.log(rootNode)
			zNodes = [rootNode]
			var setting = {
				view: {
					addHoverDom: addHoverDom,
					removeHoverDom: removeHoverDom,
					selectedMulti: false
				},
				edit: {
					enable: true,
					editNameSelectAll: true
				}
			}
			var tree = $.fn.zTree.init($('<ul class="ztree" id="' + id + '"></ul>').appendTo(catalogs), setting, zNodes)
			function addHoverDom(treeId, treeNode) {
				var sObj = $("#" + treeNode.tId + "_span")
				if (treeNode.editNameFlag || $("#addBtn_" + treeNode.id).length > 0) return
				var addStr = "<span class='button add' id='addBtn_" + treeNode.id + "' title='add node' onfocus='this.blur()'></span>"
				sObj.after(addStr)
				var btn = $("#addBtn_" + treeNode.id)
				if (btn) btn.bind("click", function() {
					var dialog = $('<div><div><input></input></div></div>').dialog({
						buttons: {
							"确定": function() {
								var name = $('input', $(this)).val()
								rest.post("${backend.rest}/catalog", {
									name: name,
									parentId: treeNode.id
								},
								function(data) {
									tree.addNodes(treeNode, {
										id: data,
										pId: treeNode.id,
										isParent: true,
										name: name
									})
								})
								$(this).dialog("close")
							},
							"取消": function() {
								$(this).dialog("close")
							}
						}
					})
					return false
				})
			}
			function removeHoverDom(treeId, treeNode) {
				$("#addBtn_" + treeNode.id).unbind().remove()
			}
		})
		catalogs.init()
		return catalogs
	}
})

