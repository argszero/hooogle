define(function(require, exports, module) {
	var DIRNAME_RE = /.*(?=\/.*$)/
	var MULTIPLE_SLASH_RE = /([^:\/])\/\/+/g

	/**
       * Canonicalizes a path.
       * realpath('./a//b/../c') ==> 'a/c'
       */
	function realpath(path) {
		MULTIPLE_SLASH_RE.lastIndex = 0

		// 'file:///a//b/c' ==> 'file:///a/b/c'
		// 'http://a//b/c' ==> 'http://a/b/c'
		if (MULTIPLE_SLASH_RE.test(path)) {
			path = path.replace(MULTIPLE_SLASH_RE, '$1\/')
		}

		// 'a/b/c', just return.
		if (path.indexOf('.') === - 1) {
			return path
		}

		var original = path.split('/')
		var ret = [],
		part

		for (var i = 0; i < original.length; i++) {
			part = original[i]

			if (part === '..') {
				if (ret.length === 0) {
					throw new Error('The path is invalid: ' + path)
				}
				ret.pop()
			}
			else if (part !== '.') {
				ret.push(part)
			}
		}

		return ret.join('/')
	}

	exports.resolve = function(id, refUri) {
		var s = refUri.match(DIRNAME_RE)
		var dirName = (s ? s[0] : '.') + '/'
		return realpath(dirName + id)
	};
});

