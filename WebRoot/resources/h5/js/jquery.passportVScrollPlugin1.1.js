(function (a) {
    a.fn.jdVScrollPlugin = function (c) {
        var h = function () {
            var j = {};
            if (typeof(defaultScrollParam) != "undefined" && defaultScrollParam) {
                j = defaultScrollParam
            }
            return j
        };
        var f = a.extend({}, {
            hasData: function () {
                return false
            }, isLoading: function () {
                return true
            }, onBeforeLoadData: function () {
            }, onLoadData: function () {
            }, onAfterLoadData: function () {
            }, adapterDocumentHeight: function () {
                var j = 10;
                if (a.browser.msie) {
                } else {
                    if (a.browser.safari) {
                        j = 100
                    } else {
                        if (a.browser.mozilla) {
                        } else {
                            if (a.browser.opera) {
                            } else {
                            }
                        }
                    }
                }
                return j
            }, addRunTopDiv: function () {
            }, initRunTopMenu: function () {
            }, displayRunTopMenu: function () {
            }, readyLoadingDiv: function () {
            }, isLoadData: function () {
                var l = f.adapterDocumentHeight();
                var m = parseFloat(a(window).height());
                var j = parseFloat(a(window).scrollTop());
                var k = a(document).height();
                lazyheight = m + j;
                if (k - l <= lazyheight) {
                    return true
                }
                return false
            }, isInitScroll: false
        }, h(), c);
        var b = function () {
            if (f.hasData() && !f.isLoading()) {
                f.onBeforeLoadData();
                f.onLoadData();
                f.onAfterLoadData()
            }
        };
        var i = function () {
            if (e()) {
                return true
            } else {
                if (f.hasData()) {
                    b();
                    i();
                    return true
                } else {
                    return false
                }
            }
        };
        var e = function () {
            var k = parseFloat(a(window).height());
            var j = a(document).height();
            return j > k ? true : false
        };
        var d = function () {
            a(window).unbind("scroll")
        };
        var g = function () {
            if (f.isInitScroll) {
                i()
            }
            f.addRunTopDiv();
            f.initRunTopMenu();
            f.readyLoadingDiv();
            a(window).bind("scroll", function (j) {
                if (f.isLoadData()) {
                    b()
                }
                f.displayRunTopMenu()
            });
            return f
        };
        return g()
    }
})(jQuery);