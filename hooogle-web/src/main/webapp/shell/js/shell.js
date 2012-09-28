//Widget
define(function(require, exports, module) {
	var $ = require('jquery')
	var cache = require('cache');
	var path = require('path')
	var css = require('css')
	var templateString = cache.get(path.resolve('../html/shell.html', module.uri));
	function commandLineKeyDown(a, b, c) {
		if (a.keyCode == 13) {
			var output = output.append()
		}
	}
	exports.newInstance = function(config) {
		css.add(path.resolve('../css/shell.css', module.uri))
		var shell = $(templateString)
		$.extend(shell, {
			command: $('input', shell),
			body: $('.body', shell),
			output: $('.output', shell),
			shellName: '',
			height: 400,
            historyPos:0,
			history: [],
			init: function() {
				this.body.height(this.height)
				this.command.keyup($.proxy(this.onCommmandKeyup, this))
				shell.mouseup($.proxy(this.onShellMouseup, this))
			},
			onShellMouseup: function() {
				if (document.selection) txt = document.selection.createRange().text;
				else if (window.getSelection) txt = window.getSelection().toString();
				if (txt.length == 0) {
					this.command.focus();
				}
			},
			onCommmandKeyup: function(evt) {
				if (evt.keyCode === 13) {
					var command = this.command.val();
					this.out("<div class='shell_input'><span class='less'>" + this.shellName + "&gt;&nbsp;</span>" + command.replace(/</g, "&lt;") + "</div>");
					this.onCommand(command);
				} else if (evt.keyCode == 38) { //up key
					this.showHisAndMove( - 1);
				} else if (evt.keyCode == 40) { //down key
					this.showHisAndMove(1);
				}
			},
			out: function(text) {
				var sp = text.split("\n")
				for (var i = 0; i < sp.length; i++) {
					var div = document.createElement("div")
					div.innerHTML = sp[i]
					this.output.append(div)
				}
				this.body.scrollTop(122500)
				this.command.val("")
			},
			addUniqueHis: function(command) {
				this.history.push(command)
				var i = 0
				for (; i <= this.history.length - 2; i++) {
					if (this.history[i] === command) { //如果这个命令已经执行过，则删除掉执行历史（后面的往前挪）
						for (; i <= this.history.length - 2; i++) {
							this.history[i] = this.history[i + 1]
						}
						this.history.pop()
						break
					}
				}
			},
			showHisAndMove: function(step) {
				if (step == -1) { //显示上一个命令
                    this.historyPos -= 1
                    if(this.historyPos<0) this.historyPos+=this.history.length
					this.command.val(this.history[this.historyPos]) 
				} else if (step == 1) { //显示下一个命令
                    this.historyPos += 1
                    if(this.historyPos>this.history.length) this.historyPos-=this.history.length
					this.command.val(this.history[this.historyPos-1]) 
				}
			},
			onCommand: function(command) {
				this.addUniqueHis(command)
				if (command === "clearhis") {
					this.history = [];
				}else if( command === "cls"){
                    this.output.remove()    
                }
			}
		})
		$.extend(shell, config)
		shell.init()
		return shell
	};
});

