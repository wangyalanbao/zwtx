var spiner = createSpinner();
var background = document.getElementById("background");
var spinerShow = function () {
    background.style.display = "block";
    spiner.spin(background);
    var a = parseInt(document.documentElement.clientHeight / 2);
    console.log("midScreen=" + a + ",type=" + typeof a);
    $("#background > div").css({position: "fixed", top: a})
};
var spinerHide = function () {
    background.style.display = "none";
    spiner.stop()
};
var addCartWare = function (h, d, g, c, f, e, b) {
    var a = "/cart/add.json?wareId=" + h + "&num=" + d + "&sid=" + $("#sid").val();
    a = appendSuit(a, g, c, f, e, b);
    spinerShow();
    jQuery.get(a, {}, function (i) {
        calcCartCheck(i, h);
        calcCartGifts(i, h);
        if (e != null && e != "") {
            calcCartYanBao(i, h, c)
        }
    })
};
var addCartWareDirect = function (h, d, g, c, f, e, b) {
    var a = "/cart/add.json?wareId=" + h + "&num=" + d + "&sid=" + $("#sid").val();
    a = appendSuit(a, g, c, f, e, b);
    jQuery.get(a)
};
var modifyWare = function (i, b, e, a, k, c, l, d) {
    var f;
    if ((!isBlank(a) && a != 4)) {
        f = k
    } else {
        f = b
    }
    var h = $("#num" + f).val();
    var j = $("#limitSukNum" + f).val();
    if (!/^\d+$/.test(h)) {
        alert("\u4fee\u6539\u6570\u91cf\u5931\u8d25\uff0c\u8bf7\u91cd\u8bd5");
        $("#num" + f).val(1);
        h = 1
    }
    if (Number(h) > j) {
        alert("\u4fee\u6539\u6570\u91cf\u5931\u8d25\uff0c\u8bf7\u91cd\u8bd5");
        $("#num" + f).val(j);
        h = j
    }
    if (Number(h) < 1) {
        alert("\u4fee\u6539\u6570\u91cf\u5931\u8d25\uff0c\u8bf7\u91cd\u8bd5");
        $("#num" + f).val(1);
        h = 1
    }
    if (h == 1) {
        $("#subnum" + f).addClass("disabled")
    }
    if (h < j) {
        $("#addnum" + f).removeClass("disabled")
    }
    if (h > 1) {
        $("#subnum" + f).removeClass("disabled")
    }
    if (h == j) {
        $("#addnum" + f).addClass("disabled")
    }
    $("#numerror" + b).text("");
    if (isBlank(e) || (!isBlank(a) && a == 4)) {
        e = $("#num" + b).val()
    } else {
        c = $("#num" + k).val()
    }
    var g = "/cart/modify.json?wareId=" + b + "&num=" + e + "&sid=" + $("#sid").val();
    g = appendSuit(g, a, k, c, l, d);
    spinerShow();
    jQuery.get(g, {}, function (m) {
        calcCartCheck(m, f, i, true);
        calcCartGifts(m, b);
        $("span[name='suitSkuNum" + b + "']").text(h);
        pingClick(cartUse, "MShopcart_Amount", f, "modifyWare", "http://p.m.jd.com/cart/modify.action")
    })
};
var deleteWare = function (g, b, d, a, h, c, i, f) {
    pingClick(cartUse, "MShopcart_Delete", "", "deleteWare", "");
    if (confirm("\u786e\u5b9a\u5220\u9664\u5417\uff1f")) {
        var e = "/cart/remove.json?wareId=" + b + "&num=" + d + "&sid=" + $("#sid").val();
        e = appendSuit(e, a, h, c, i);
        spinerShow();
        jQuery.get(e, {}, function (m) {
            calcCartCheck(m, b);
            calcCartGifts(m, b);
            var j = h;
            if (f != "gift") {
                if (!a || a == 4) {
                    $("#checkIcon" + b).removeAttr("data-sku");
                    j = b;
                    var l = $("#product" + b);
                    l.hide();
                    l.attr("name", "remove" + l.name);
                    calcCartShop(g)
                } else {
                    var n = document.getElementsByName("item" + b);
                    var k = n.length;
                    if (k == 1) {
                        var l = $("#product" + b);
                        l.hide();
                        l.attr("name", "remove" + l.name);
                        calcCartShop(g)
                    } else {
                        $("#product" + h).hide();
                        $("#hr" + h).hide();
                        if (n[k - 1].id == "product" + h) {
                            $("#hr" + n[k - 2].id.substring(7)).hide()
                        }
                        $("#product" + h).attr("name", "deleteitem" + b)
                    }
                    $("#checkIcon" + h).removeAttr("data-sku")
                }
            }
            pingCartDel(cartUse, "MShopcartDeleteProduct_Sure", "", j);
            pingClick(cartUse, "MShopcartDeleteProduct_Sure", j, "deleteWare", "")
        })
    } else {
        pingClick(cartUse, "MShopcartDeleteProduct_Cancel", "", "deleteWare", "")
    }
};
var deleteWareDirect = function (h, c, g, b, f, e, d) {
    var a = "/cart/remove.json?wareId=" + h + "&num=" + c + "&sid=" + $("#sid").val();
    a = appendSuit(a, g, b, f, e);
    jQuery.get(a)
};
var showChooseGifts = function (a, h, d, g, c, b, f) {
    document.getElementById("mask-con").innerHTML = document.getElementById("chooseGifts" + h).innerHTML;
    document.getElementById("chooseGifts" + h).innerHTML = "";
    appendant({ID: "chooseGifts" + h});
    if (cartUse) {
        var e;
        if (Number(f) > 0) {
            e = "jiajiagou"
        } else {
            e = "manzeng"
        }
        pingClick(cartUse, "MShopcart_Getresent", e, "showChooseGifts", "")
    }
};
var appendant = function (l) {
    var g, d, j, f, i, h = 0;

    function a() {
        i = document.body.scrollTop;
        $(".shp-cart-list").css({height: d}).addClass(".crop-list").hide();
        $(".shp-cart-list .cart-checkbox").hide();
        $(".shp-cart-icon-remove").css("display", "none");
        $(".shp-cart-list .cart-product-cell-1").css("visibility", "hidden");
        $("body").css({"padding-bottom": 0, width: "100%", height: d, overflow: "hidden", position: "relative"});
        $(window).bind("scroll", function (m) {
            m.preventDefault;
            return false
        })
    }

    function c() {
        $(".shp-cart-list .cart-checkbox").show();
        $(".shp-cart-icon-remove").css("display", "inline-block");
        $(".shp-cart-list .cart-product-cell-1").css("visibility", "visible");
        $("#mask").css({visibility: "hidden"});
        $("#mask-con").css({top: "1px"});
        $("body").css({"padding-bottom": 50, width: "100%", height: "auto", overflow: "auto"});
        $(".shp-cart-list").css({height: "auto"}).removeClass(".crop-list").show();
        window.scrollTo(0, i)
    }

    function k() {
        if ($("#mask-click-hidden").length == 0) {
            $("#mask").prepend('<div class="mask-click-hidden" id="mask-click-hidden"></div>')
        }
        i = document.body.scrollTop;
        d = document.documentElement.clientHeight;
        g = document.documentElement.clientWidth;
        j = $("#mask .additional-list-container").height() + 15;
        f = (parseInt(d * 0.6) > j) ? j : parseInt(d * 0.6);
        if ($(".additional-item-title").length > 0) {
            f += $(".additional-item-title").height()
        }
        window.addEventListener("orientationchange", e)
    }

    function b() {
        $("#mask").css({visibility: "visible"});
        k();
        a();
        h = -(f + 42);
        setTimeout(function () {
            $("#mask .additional-list-wrapper").css({height: f, overflow: "auto"});
            $("#mask-con").css({top: h})
        }, 100);
        $("#mask-click-hidden").unbind("click");
        $("#mask-click-hidden").bind("click", function (o) {
            c();
            if (l != null) {
                document.getElementById(l.ID).innerHTML = document.getElementById("mask-con").innerHTML;
                document.getElementById("mask-con").innerHTML = "";
                var n = document.querySelectorAll(".wastebin");
                for (var m = 0; m < n.length; m++) {
                    $(n[m]).find(".wastebin-up").removeClass("wastebin-down-animi");
                    $(n[m]).removeClass("westebin-rock-animi")
                }
            }
        });
        $("#mask .btn-jd-red").bind("click", function (m) {
            c()
        })
    }

    function e() {
        if ($("#mask").css("visibility") == "hidden") {
            return
        }
        var m = 0;
        switch (window.orientation) {
            case -90:
            case 90:
                setTimeout(function () {
                    b()
                }, 300);
                break;
            default:
                setTimeout(function () {
                    b()
                }, 300);
                break
        }
        return m
    }

    b()
};
function resizePriseFontSize() {
    $(".shp-cart-item-price").each(function () {
        var a = $(this).html();
        var b = a.replace(/\,/g, "");
        $(this).html(b);
        if (b.length > 10 && b.length <= 12) {
            $(this).addClass("priceSizeMid")
        } else {
            if (b.length > 12) {
                $(this).addClass("priceSizeSmall")
            }
        }
    })
}
var showGifts = function (b) {
    var a = "/cart/cart.json?sid=" + $("#sid").val();
    spinerShow();
    jQuery.get(a, {}, function (c) {
        calcCartGifts(c, b)
    })
};
var hideChooseGifts = function (a) {
    if (Number($("#hidegiftSelectNum" + a).val()) > 0) {
        $("#hr" + a).removeClass("diver-hr-dashed");
        $("#hr" + a).addClass("diver-hr-solid")
    }
    var h = $("span[name=gift" + a + "]");
    if (h.size() > 0) {
        var b = "";
        var e = "";
        var c = false;
        h.each(function () {
            if ($(this).hasClass("checked")) {
                var i = $(this).attr("data-gift");
                if (b == "") {
                    b = i
                } else {
                    b = b + "@@@" + i
                }
            }
            if ($(this).hasClass("newSelect")) {
                $(this).removeClass("newSelect");
                c = true
            }
            if ($(this).hasClass("newCancel")) {
                c = true;
                $(this).removeClass("newCancel");
                var i = $(this).attr("data-gift");
                if (e == "") {
                    e = i
                } else {
                    e = e + "@@@" + i
                }
            }
        });
        var j;
        if (c) {
            if (b == "") {
                j = "/cart/removeWares.json?wareInfos=" + e + "&sid=" + $("#sid").val()
            } else {
                j = "/cart/addWares.json?wareInfos=" + b + "&sid=" + $("#sid").val()
            }
            spinerShow();
            jQuery.get(j, {}, function (i) {
                calcCartGifts(i, a)
            })
        }
    }
    document.getElementById("mask").style.visibility = "hidden";
    document.getElementById("chooseGifts" + a).innerHTML = document.getElementById("mask-con").innerHTML;
    document.getElementById("mask-con").innerHTML = "";
    if (cartUse) {
        var k = "manzeng";
        if (Number($("#hidegiftSelectType" + a).val()) > 0) {
            k = "jiajiagou"
        }
        var f = document.getElementsByName("gift" + a);
        for (var d = 0; d < f.length; d++) {
            var h = f[d];
            if ($(h).hasClass("checked")) {
                var g = $("#giftId" + h.id).val();
                pingCartAdd(cartUse, "MProductdetail_Addtocart", k, g + "");
                pingClick(cartUse, "MProductdetail_Addtocart", g, "ChooseGifts", "")
            }
        }
    }
};
var showChooseYanBao = function (b, g, e, f, d, c) {
    if (b == "0") {
        var a = "/cart/check.json?wareId=" + g + "&num=" + $("#num" + g).val() + "&checked=5&sid=" + $("#sid").val();
        a = appendSuit(a, f, d, c);
        spinerShow();
        jQuery.get(a, {}, function (h) {
            calcCartCheck(h, g);
            document.getElementById("mask-con").innerHTML = document.getElementById("chooseYanbao" + g + d).innerHTML;
            document.getElementById("mask-con").style.top = (document.documentElement.scrollTop || document.body.scrollTop) + 10 + "px"
        })
    } else {
        document.getElementById("mask-con").innerHTML = document.getElementById("chooseYanbao" + g + d).innerHTML;
        document.getElementById("chooseYanbao" + g + d).innerHTML = "";
        appendant({ID: "chooseYanbao" + g + d});
        pingClick(cartUse, "MShopcart_Warranty", d, "showChooseYanBao", "http://p.m.jd.com/cart/cart.action")
    }
};
var hideChooseYanBao = function (b, a) {
    document.getElementById("mask").style.visibility = "hidden";
    document.getElementById("chooseYanbao" + b + a).innerHTML = document.getElementById("mask-con").innerHTML;
    document.getElementById("mask-con").innerHTML = ""
};
var selectGifts = function (g, f, h, b, a, e) {
    var c = 0;
    var d = $("#" + g + b);
    if (d.hasClass("checked")) {
        c = Number($("#hidegiftSelectNum" + g).val()) - Number(a);
        $("#hidegiftSelectNum" + g).val(c);
        $("#giftSelectNum" + g).html(c);
        d.removeClass("checked");
        if (d.hasClass("newSelect")) {
            d.removeClass("newSelect")
        } else {
            d.addClass("newCancel")
        }
    } else {
        c = Number($("#hidegiftSelectNum" + g).val()) + Number(a);
        if (c > e) {
            toaster("\u6362\u8d2d\u6570\u91cf\u5df2\u7ecf\u6ee1\u55bd~", "", true);
            return
        }
        $("#hidegiftSelectNum" + g).val(c);
        $("#giftSelectNum" + g).html(c);
        d.addClass("checked");
        if (d.hasClass("newCancel")) {
            d.removeClass("newCancel")
        } else {
            d.addClass("newSelect")
        }
    }
};
var deleteGifts = function (d, c, e, b, a) {
    deleteWare("", d, c, e, b, a, "", "gift")
};
var deleteYanBao = function (g, f, h, c, b, e, d) {
    var a = "/cart/removeYB.json?wareId=" + g + "&num=" + f + "&sid=" + $("#sid").val();
    a = appendSuit(a, h, c, b, e, d);
    jQuery.get(a)
};
var keydown = function (a) {
    $("#" + a).addClass("checked")
};
var changeSelected = function (h, b, c, a, f, i) {
    var g = 6;
    var e = b;
    if (a == undefined || a == 4) {
        e = b
    } else {
        e = f
    }
    if ($("#checkIcon" + e).hasClass("checked")) {
        $("#checkIcon" + e).removeClass("checked")
    } else {
        $("#checkIcon" + e).addClass("checked");
        g = 5
    }
    var d = "/cart/check.json?wareId=" + b + "&num=" + c + "&checked=" + g + "&sid=" + $("#sid").val();
    d = appendSuit(d, a, f, i);
    spinerShow();
    jQuery.get(d, {}, function (j) {
        if (j != undefined) {
            calcCartCheck(j, b, h);
            calcCartGifts(j, b);
            spinerHide();
            if (g == 5) {
                pingClick(cartUse, "MShopcart_CheckProd", e, "", "")
            }
        } else {
            if ($("#checkIcon" + b).hasClass("checked")) {
                $("#checkIcon" + b).removeClass("checked")
            } else {
                $("#checkIcon" + b).addClass("checked")
            }
        }
    })
};
var calcCartCheck = function (o, b, r, m) {
    try {
        if (!isBlank(o.cart)) {
            o = o.cart;
            document.getElementById("cart_oriPrice").innerHTML = o.Price.toFixed(2);
            document.getElementById("cart_rePrice").innerHTML = o.RePrice.toFixed(2);
            document.getElementById("cart_realPrice").innerHTML = o.PriceShow;
            resizeBottomPriseFontSize();
            resizeBottomTotalFontSize();
            var a;
            if (o.checkedWareNum > 99) {
                a = "99+"
            } else {
                a = o.checkedWareNum
            }
            document.getElementById("checkedNum").innerHTML = a;
            if (b == -2) {
                var s = o.vendors;
                var q;
                var t;
                var p;
                var l;
                var h;
                var n;
                for (var f = 0; f < s.length; f++) {
                    if (s[f].sorted[0].item.CheckType == 1) {
                        $("#checkShop" + s[f].shopId).addClass("checked")
                    } else {
                        $("#checkShop" + s[f].shopId).removeClass("checked")
                    }
                    t = s[f].sorted;
                    for (var d = 0; d < t.length; d++) {
                        p = t[d];
                        l = p.item.Id;
                        if (p.itemType == 1 || p.itemType == 4) {
                            if (p.item.CheckType == 1) {
                                $("#checkIcon" + l).addClass("checked")
                            } else {
                                $("#checkIcon" + l).removeClass("checked")
                            }
                        } else {
                            skus = p.item.Skus;
                            for (var c = 0; c < skus.length; c++) {
                                var h = skus[c];
                                if (h.CheckType == 1) {
                                    $("#checkIcon" + h.Id).addClass("checked")
                                } else {
                                    $("#checkIcon" + h.Id).removeClass("checked")
                                }
                            }
                        }
                    }
                }
            }
            if (m && (!$("#checkIcon" + b).hasClass("checked"))) {
                $("#checkIcon" + b).addClass("checked")
            }
            if (r != null && r != "") {
                calcShopCheck(o, r)
            }
            if (o.Num == o.checkedWareNum) {
                $("#checkIcon-1").addClass("checked")
            } else {
                $("#checkIcon-1").removeClass("checked")
            }
            judgeSubmit(o.checkedWareNum, o.GlobalNum, o.LocalNum)
        } else {
            $("#notEmptyCart").hide();
            $("#payment_p").hide();
            $("#emptyCart").show()
        }
    } catch (g) {
    }
    spinerHide()
};
var calcCartGifts = function (s, d) {
    try {
        if (!isBlank(s.cart)) {
            s = s.cart;
            document.getElementById("cart_oriPrice").innerHTML = s.Price.toFixed(2);
            document.getElementById("cart_rePrice").innerHTML = s.RePrice.toFixed(2);
            document.getElementById("cart_realPrice").innerHTML = s.PriceShow;
            var a;
            if (s.checkedWareNum > 99) {
                a = "99+"
            } else {
                a = s.checkedWareNum
            }
            document.getElementById("checkedNum").innerHTML = a;
            var t = s.vendors;
            var r;
            var u;
            var f;
            var l;
            for (var n = 0; n < t.length; n++) {
                l = 0;
                if (t[n].shopId == d) {
                    l = 1
                }
                r = t[n].sorted;
                for (var h = 0; h < r.length; h++) {
                    if (r[h].itemType == 1 || r[h].itemType == 4) {
                        continue
                    }
                    u = r[h].item;
                    f = u.Id;
                    if (l == 1 || d == -2 || f == d) {
                        var o = "<div class='diver-hr-dashed'></div> <span class='shop-footer-total'>\u5c0f\u8ba1\u003a<i>" + u.PriceShow + "</i></span>";
                        if (u.RePrice > 0) {
                            o = o + "<span class='shop-footer-cashback'>\u8fd4\u73b0\u003a\uffe5<i>" + u.RePrice.toFixed(2) + "</i></span>"
                        }
                        $("#shopfooter" + f).html(o);
                        if (u.SType == 11 || u.SType == 16) {
                            if (u.linkUrl && !(u.SType == 16 && u.CanSelectGifts.length >= 1 && u.CanSelectedGiftNum > 0)) {
                                document.getElementById("shopping" + u.Id).innerHTML = document.getElementById("spanshopping" + f) == undefined ? "" : document.getElementById("spanshopping" + f).innerHTML;
                                $("#shopping" + f).show();
                                $("#spanshopping" + f).hide()
                            } else {
                                $("#spanshopping" + f).show();
                                document.getElementById("shopping" + f).innerHTML = "";
                                $("#shopping" + f).hide()
                            }
                        }
                        if (!!u.STip) {
                            if (u.STip.indexOf("\u9886\u53d6\u8d60\u54c1") != -1 && u.STip.indexOf("\u5148\u5230\u5148\u5f97") == -1) {
                                $("#sTip" + f).html(u.STip.substr(0, u.STip.indexOf("\u9886\u53d6\u8d60\u54c1")))
                            } else {
                                $("#sTip" + f).html(u.STip)
                            }
                        }
                        var c = $("#sid").val();
                        var b = "";
                        for (var m = 0; m < u.Gifts.length; m++) {
                            var p = u.Gifts[m];
                            b += '<input type="hidden" name="showgift' + u.Id + '" class="cart-suit-showgift-input" value="' + p.Id + '">';
                            if (u.AddMoney > 0) {
                                b += '<div class="diver-hr-dashed clear"></div><div class="items"><div class="shp-cart-item-core"><div class="cart-product-cell-3"><span class="shp-cart-item-price">' + p.PriceShow + '</span></div><a class="cart-product-cell-1 shp-cart-tag-trade" href=\'http://item.m.jd.com/product/' + p.Id + ".html?sid=" + c + '\'><img class="cart-photo-thumb" alt="" src="http://img10.360buyimg.com/n7/' + p.ImgUrl + '"  onerror="http://misc.360buyimg.com/lib/skin/e/i/error-jd.gif"/></a><div class="cart-product-cell-2"><div class="cart-product-name"><a href=\'http://item.m.jd.com/product/' + p.Id + ".html?sid=" + c + "'><span>" + p.Name + '</span></a></div><div class="shp-cart-opt"><span class="shp-cart-amount">x </span><span class="shp-cart-amount">' + p.Num + '</span><a class="shp-cart-icon-remove" href="javascript:deleteGifts(' + u.Id + "," + u.Num + "," + u.SType + "," + p.Id + "," + p.Num + ')"></a></div></div></div></div>'
                            } else {
                                b += '<span class="gifts-details"><div class="gifts-detail-description">[\u8d60\u54c1] ' + p.Name + " x" + p.Num + ' </div><a class="shp-cart-icon-remove" href="javascript:deleteGifts(' + u.Id + "," + u.Num + "," + u.SType + "," + p.Id + "," + p.Num + ')"></a></span>'
                            }
                        }
                        $("#givenGift" + f).html(b);
                        if (u.Gifts.length == 0) {
                            $("#hr" + f).removeClass("diver-hr-solid");
                            $("#hr" + f).addClass("diver-hr-dashed");
                            $("#hidegiftSelectNum" + f).val(0)
                        }
                        if (u.CanSelectGifts.length > 0) {
                            makeChooseGift(u.Id, u.Num, u.SType, u.CheckType, u.CanSelectGifts, u.Gifts.length, u.CanSelectedGiftNum, u.AddMoney)
                        }
                        if (u.CanSelectedGiftNum > 0 && u.CanSelectedGiftNum > u.Gifts.length && u.CanSelectGifts.length > 0) {
                            $("#notGivenGift" + u.Id).show()
                        } else {
                            $("#notGivenGift" + u.Id).hide()
                        }
                        $("span[name='optionsGift']").removeClass("checked")
                    }
                }
            }
            judgeSubmit(s.checkedWareNum, s.GlobalNum, s.LocalNum)
        } else {
            $("#notEmptyCart").hide();
            $("#payment_p").hide();
            $("#emptyCart").show()
        }
    } catch (q) {
    }
    spinerHide()
};
var calcCartShop = function (a) {
    try {
        var c = $("li[name=productGroup" + a + "]");
        if (c.size() == 0) {
            $("#shop" + a).hide()
        }
    } catch (b) {
    }
    return isHiden
};
var makeChooseGift = function (c, j, a, b, h, l, o, d) {
    if ($("#chooseGifts" + c).html() == "") {
        return
    }
    var n = '<input type="hidden" id="hidegiftSelectNum' + c + '" value="' + l + '"><input type="hidden" id="hidegiftSelectType' + c + '" value="' + d + '" ><div class="additional-list-items" ><div class="additional-list-header"><span class="additional-list-header-right"><a class="btn-jd-red" href="javascript:hideChooseGifts(\'' + c + '\')">\u786e\u5b9a</a></span>\u5df2\u9009\u62e9 <span class="highlight"><span id="giftSelectNum' + c + '">' + l + "</span>/" + o + '</span> \u4ef6</div><div class="additional-list-wrapper"><div class="additional-list-container"> ';
    var m = "";
    for (var f = 0; f < h.length; f++) {
        var e = h[f];
        m += '<div class="additional-items"><div class="item-wrapper"><div class="additional-item-content"><div class="cart-product-name"><a href="#" style="cursor:default;"><span>\u3010\u8d60\u54c1\u3011' + e.Name + '</span></a></div><div class="shp-cart-promotion-opt"><span class="shp-cart-promotion-right"><span class="shp-cart-item-price">' + e.PriceShow + '</span></span><span class="shp-cart-promotion-left">';
        if (e.giftMsg) {
            m += '<span class="icon-exchange">' + e.giftMsg + "</span>"
        } else {
            m += '<span class="shp-cart-amount">x ' + e.Num + "</span>"
        }
        m += "</span></div></div></div>";
        if (!e.giftMsg) {
            m += '<div class="additional-check-wrapper"><span id="' + c + e.Id + '" name="gift' + c + '"';
            if (e.CheckType == 1) {
                m += 'class="cart-checkbox checked"'
            } else {
                m += 'class="cart-checkbox"'
            }
            m += "data-gift='" + c + "@@" + j + "@@" + a + "@@" + e.Id + "@@" + e.Num + "'onclick=\"selectGifts('" + c + "'," + j + "," + a + "," + e.Id + "," + e.Num + "," + o + ')"></span><input type="hidden" id="num' + c + e.Id + '" value="' + e.Num + '"><input type="hidden" id="giftId' + c + e.Id + '" value="' + e.Id + '"></div>'
        }
        m += '<div class="additional-item-thumb"><img class="cart-photo-thumb" alt="" src="http://img10.360buyimg.com/n7/' + e.ImgUrl + '" onerror="http://misc.360buyimg.com/lib/skin/e/i/error-jd.gif"></div>';
        m += '</div><div class="diver-hr-solid clear"></div>'
    }
    var k = "</div></div></div>";
    var g = n + m + k;
    $("#chooseGifts" + c).html(g)
};
var calcCartYanBao = function (h, a, n) {
    try {
        if (!isBlank(h.cart)) {
            h = h.cart;
            document.getElementById("cart_oriPrice").innerHTML = h.Price.toFixed(2);
            document.getElementById("cart_rePrice").innerHTML = h.RePrice.toFixed(2);
            document.getElementById("cart_realPrice").innerHTML = h.PriceShow;
            var m = h.vendors;
            var o;
            var l;
            var g;
            for (var c = 0; c < m.length; c++) {
                o = m[c].sorted;
                for (var b = 0; b < o.length; b++) {
                    l = o[b];
                    if (l.itemType == 1) {
                        if (a == "" || a == null) {
                            g = l.item;
                            if (l.item.Id == n) {
                                showCartYanBao("", "", "", g);
                                return
                            }
                        }
                    } else {
                        if (l.item.Id == a) {
                            var k = l.item;
                            var f = k.Skus;
                            for (var b = 0; b < f.length; b++) {
                                g = f[b];
                                if (g.Id == n) {
                                    showCartYanBao(l.item.Id, l.item.Num, l.item.SType, g);
                                    return
                                }
                            }
                        }
                    }
                }
            }
        } else {
            $("#notEmptyCart").hide();
            $("#payment_p").hide();
            $("#emptyCart").show()
        }
    } catch (d) {
    } finally {
        spinerHide()
    }
};
var showCartYanBao = function (g, f, h, b) {
    var a;
    var d;
    if (b.YbSkus && b.YbSkus.length > 0) {
        var e = b.YbSkus;
        a = '<div id="showyanbao' + g + b.Id + '">';
        for (var c = 0; c < e.length; c++) {
            d = e[c];
            if (c == 0) {
                a += '<div class="shp-cart-item-abstract clear">'
            } else {
                a += '<div class="shp-cart-item-abstract clear padd0">'
            }
            a += '<div class="cart-product-cell-3"><span class="shp-cart-item-price">' + d.MainYbSku.PriceShow + "</span></div>";
            if (c == 0) {
                a += '<span class="cart-product-cell-additional" href="#"><a href="javascript:showChooseYanBao(\'1\',\'' + g + "','" + f + "','" + h + "','" + b.Id + "','" + b.Num + '\')" class="btn-white">\u4fee\u6539\u670d\u52a1</a></span>'
            } else {
                a += '<span class="cart-product-cell-additional" href="#">&nbsp;</span>'
            }
            if (d && d.MainYbSku) {
                a += '<div class="cart-product-cell-2"><div class="cart-product-name" id="yanbao' + d.MainYbSku.Id + '"><span>' + d.MainYbSku.Name + "</span></div></div>"
            }
            a += "</div>"
        }
        a += "</div>"
    } else {
        a = '<div class="shp-cart-item-abstract empty clear" id="showyanbao' + g + b.Id + '"><span class="cart-product-cell-additional" href="#"><a href="javascript:showChooseYanBao(\'1\',\'' + g + "','" + f + "','" + h + "','" + b.Id + "','" + b.Num + '\')" class="btn-white">\u5ef6\u4fdd\u670d\u52a1</a></span></div>'
    }
    $("#givenYanbao" + g + b.Id).html(a)
};
var disableSubmit = function () {
    $("#submit").css("background", "#999");
    $("#submit").unbind("click")
};
var enableSubmit = function (a, b) {
    $("#submit").css("background", "#c00000");
    if (a == "" || (a == 0 && b > 0)) {
        $("#submitType").val(1);
        $("#submit").unbind("click");
        $("#submit").bind("click", submit);
        return
    }
    if (a > 0 && b == 0) {
        $("#submitType").val(2);
        $("#submit").unbind("click");
        $("#submit").bind("click", submit);
        return
    }
    if (a > 0 && b > 0) {
        $("#submit").unbind("click");
        $("#submit").bind("click", function () {
            popModal({
                globalPrice: "0.00",
                localPrice: "0.00",
                globalNum: "" + a,
                localNum: "" + b,
                callback: function () {
                    if ($("input[name='shopping']")[0].checked) {
                        $("#submitType").val(2);
                        $("#submit").click(submit())
                    } else {
                        $("#submitType").val(1);
                        $("#submit").click(submit())
                    }
                }
            })
        });
        return
    }
};
var judgeSubmit = function (c, a, b) {
    if (c <= 0) {
        disableSubmit();
        $("#checkAll").removeClass("checked")
    } else {
        enableSubmit(a, b)
    }
};
var appendSuit = function (e, f, b, d, c, a) {
    if (!isBlank(e)) {
        if (!isBlank(f)) {
            e += "&sType=" + f
        }
        if (!isBlank(b)) {
            e += "&suitSkuId=" + b
        }
        if (!isBlank(d)) {
            e += "&suitSkuNum=" + d
        }
        if (!isBlank(c)) {
            e += "&ybId=" + c
        }
        if (!isBlank(a)) {
            e += "&ybNum=" + a
        }
    }
    return e
};
var submit = function () {
    var a = document.getElementById("cart_realPrice").innerHTML;
    if (Number(a) <= 0) {
        return
    }
    if ($("#submitType").val() == 1) {
        location.href = "/norder/order.action?enterOrder=true&sid=" + $("#sid").val();
        pingClick(cartUse, "Shopcart_Pay", "", "submit", "http://p.m.jd.com/norder/order.action")
    } else {
        location.href = "http://m.jd.hk/norder/order.action?enterOrder=true&sid=" + $("#sid").val();
        pingClick(cartUse, "MGlobalCart_Account", "international", "submit", "http://p.m.jd.com/norder/order.action")
    }
};
var isBlank = function (a) {
    if (a == undefined || a == null || a.length == 0) {
        return true
    } else {
        return false
    }
};
var deSelectedAll = function () {
    var b = -2;
    $("#checkIcon" + b).removeClass("checked");
    spinerShow();
    var a = "/cart/check.json?checked=" + 8 + "&sid=" + $("#sid").val();
    jQuery.get(a, {}, function (c) {
        if (c != undefined) {
            calcCartCheck(c, b);
            calcCartGifts(c, b);
            spinerHide()
        } else {
            if ($("#checkIcon" + b).hasClass("checked")) {
                $("#checkIcon" + b).removeClass("checked")
            } else {
                $("#checkIcon" + b).addClass("checked")
            }
        }
    })
};
var selectedAll = function () {
    var b = -2;
    $("#checkIcon" + b).addClass("checked");
    spinerShow();
    var a = "/cart/check.json?checked=" + 7 + "&sid=" + $("#sid").val();
    jQuery.get(a, {}, function (c) {
        if (c != undefined) {
            calcCartCheck(c, b);
            calcCartGifts(c, b);
            spinerHide()
        } else {
            if ($("#checkIcon" + b).hasClass("checked")) {
                $("#checkIcon" + b).removeClass("checked")
            } else {
                $("#checkIcon" + b).addClass("checked")
            }
        }
    })
};
var checkAllHandler = function () {
    if ($("#checkIcon-1").hasClass("checked")) {
        $("#checkIcon-1").removeClass("checked ");
        deSelectedAll()
    } else {
        $("#checkIcon-1").addClass("checked");
        selectedAll();
        pingClick(cartUse, "MShopcart_CheckAll", "", "", "")
    }
};
var setFixTip = function (b, a) {
    window.onscroll = function () {
        var d = document.documentElement.scrollTop || document.body.scrollTop;
        var c = $("#" + a).offset().top;
        if (d >= c - 10) {
            $("#" + b + "p").css("height", $("#" + b).height());
            $("#" + b).css("position", "fixed");
            $("#" + b).css("z-index", "10001");
            $("#" + b).css("top", "0px");
            $("#" + b).css("border-bottom", "1px solid #e4393c")
        } else {
            $("#" + b + "p").css("height", "0px");
            $("#" + b).css("position", "");
            $("#" + b).css("z-index", "");
            $("#" + b).css("border-bottom", "0px solid #e4393c");
            $("#" + b).css("top", $("#" + a).offset().top + "px")
        }
    }
};
var addWareBybutton = function (i, b, e, a, k, c, l, d) {
    var h = 0;
    var f;
    if (isBlank(e) || (!isBlank(a) && a == 4)) {
        f = b
    } else {
        f = k
    }
    h = Number($("#num" + f).val()) + 1;
    var j = $("#limitSukNum" + f).val();
    if (h > j) {
        return
    }
    if (h > 1) {
        $("#subnum" + f).removeClass("disabled")
    }
    if (h == j) {
        $("#addnum" + f).addClass("disabled")
    }
    if (isBlank(e) || (!isBlank(a) && a == 4)) {
        e = h
    } else {
        c = h
    }
    $("#num" + f).val(h);
    var g = "/cart/modify.json?wareId=" + b + "&num=" + e + "&sid=" + $("#sid").val();
    g = appendSuit(g, a, k, c, l, d);
    spinerShow();
    jQuery.get(g, {}, function (m) {
        calcCartCheck(m, f, i, true);
        calcCartGifts(m, b);
        $("span[name='suitSkuNum" + b + "']").text(h);
        pingClick(cartUse, "MShopcart_EditAmount", f, "addWareBybutton", "http://p.m.jd.com/cart/modify.action")
    })
};
var subWareBybutton = function (i, b, e, a, j, c, k, d) {
    var h = 0;
    var f;
    if (isBlank(e) || (!isBlank(a) && a == 4)) {
        f = b
    } else {
        f = j
    }
    h = Number($("#num" + f).val()) - 1;
    if (h <= 0) {
        return
    }
    if (h == 1) {
        $("#subnum" + f).addClass("disabled")
    }
    if (h < $("#limitSukNum" + f).val()) {
        $("#addnum" + f).removeClass("disabled")
    }
    if (isBlank(e) || (!isBlank(a) && a == 4)) {
        e = h
    } else {
        c = h
    }
    $("#num" + f).val(h);
    var g = "/cart/modify.json?wareId=" + b + "&num=" + e + "&sid=" + $("#sid").val();
    g = appendSuit(g, a, j, c, k, d);
    spinerShow();
    jQuery.get(g, {}, function (l) {
        calcCartCheck(l, f, i, true);
        calcCartGifts(l, b);
        $("span[name='suitSkuNum" + b + "']").text(h);
        pingClick(cartUse, "MShopcart_EditAmount", f, "subWareBybutton", "http://p.m.jd.com/cart/modify.action")
    })
};
var selectYanbao = function (d, j, b, m, f, o, h, e) {
    if ($("#" + d + m + o).hasClass("checked")) {
        deleteYanBao(d, j, b, m, f, o, h);
        $("#" + d + m + o).removeClass("checked")
    } else {
        var a = "" + d + m + e;
        var k = document.getElementsByName(a);
        var c;
        var n;
        for (var g = 0; g < k.length; g++) {
            var l = k[g];
            if (l.className == "cart-checkbox checked") {
                l.className = "cart-checkbox"
            }
        }
        $("#" + d + m + o).addClass("checked")
    }
};
var addYanbao = function (e, l, b, p, f) {
    var q = null;
    var k = null;
    var m = e + p;
    var d = document.getElementsByName("brandId" + m);
    for (var h = 0; h < d.length; h++) {
        var c = d[h].value;
        var a = "" + e + p + c;
        var n = document.getElementsByName(a);
        for (var g = 0; g < n.length; g++) {
            var o = n[g];
            if (o.className == "cart-checkbox checked") {
                platformId = $("#ybId" + o.id).val();
                platformNum = $("#num" + o.id).val();
                if (q == null) {
                    q = platformId;
                    k = platformNum
                } else {
                    q += "@@" + platformId;
                    k += "@@" + platformNum
                }
            }
        }
    }
    if (q != null) {
        addCartWare(e, l, b, p, f, q, k)
    } else {
        showYanbao(e, p)
    }
    hideChooseYanBao(e, p)
};
var showYanbao = function (c, b) {
    var a = "/cart/cart.json?sid=" + $("#sid").val();
    spinerShow();
    jQuery.get(a, {}, function (d) {
        calcCartYanBao(d, c, b)
    })
};
var beWalk = function () {
    var b = $("#sid").val();
    var a = "http://m.jd.com/index.html";
    pingClick(cartUse, "MShopcart_Stroll", "", "beWalk", a);
    if (b != null && b == "") {
        a += "?sid=" + b
    }
    window.location.href = a
};
var beActPage = function (f, g, d, e) {
    var b = $("#sid").val();
    var a = "http://m.jd.com/sale/" + f;
    if (cartUse) {
        var c;
        if (g == 11) {
            c = "manjian"
        } else {
            if (Number(d) > 0) {
                c = "jiajiagou"
            } else {
                c = "manzeng"
            }
        }
        pingClick(cartUse, "MShopcart_Label", c, "beActPage", a)
    }
    if (e != null && e != "") {
        a += "?cartFlag=" + e
    }
    window.location.href = a
};
var beLoginEmptyCart = function (a) {
    pingClick(cartUse, "MShopcart_LoginEmptyCart", "", "beLoginEmptyCart", a);
    window.location.href = a
};
var beShop = function (b) {
    var a = "http://ok.jd.com/m/index-" + b + ".htm";
    pingClick(cartUse, "MShopcart_ShopEntrance", b, "beShop", a);
    window.location.href = a
};
var beLogin = function (a) {
    pingClick(cartUse, "MShopcart_LoginFullCart", "", "beLogin", a);
    window.location.href = a
};
var pingClick = function (b, h, f, d, a) {
    if (b) {
        try {
            var g = new MPing.inputs.Click(h);
            g.event_param = f;
            var c = new MPing();
            c.send(g)
        } catch (i) {
        }
    }
};
var pingCartDel = function (a, f, d, c) {
    if (a) {
        try {
            var h = new MPing.inputs.RmCart(f, c);
            h.event_param = d;
            var b = new MPing();
            b.send(h)
        } catch (g) {
        }
    }
};
var pingCartAdd = function (b, f, d, a) {
    if (b) {
        try {
            var h = new MPing.inputs.AddCart(f, a);
            h.event_param = d;
            var c = new MPing();
            c.send(h)
        } catch (g) {
        }
    }
};
var timerEvt;
function toaster(a, i, f) {
    clearTimeout(timerEvt);
    timerEvt = function () {
        return false
    };
    var b = 2000;
    if ($("#popup_block").length == 0) {
        $("body").append('<div id="popup_block" class="pop-block"><div class="popup_wrapper"><div class="popup_title"></div><div class="popup_content"></div></div></div>')
    }
    var l = $("#popup_block");
    $("#popup_block .popup_content").html(a);
    $("#popup_block .popup_title").html(i);
    var g = (f) ? "nowrap" : "inherit";
    $("#popup_block").show();
    var k = window.pageXOffset || document.documentElement.scrollLeft || document.body.scrollLeft;
    var c = document.documentElement.clientWidth || document.body.clientWidth;
    var j = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
    var d = document.documentElement.clientHeight || document.body.clientHeight;
    var e = (d - $("#popup_block").height()) / 2 + j;
    var h = (c - $("#popup_block").width()) / 2 + k;
    l.css({top: e, left: h, "white-space": g});
    timerEvt = setTimeout(function () {
        $("#popup_block").hide()
    }, b)
}
var popModal = function (e) {
    var m = {callback: null};
    var q = $.fn.extend(m, e);
    var r = "\u62b1\u6b49\uff0c\u60a8\u7684\u8d2d\u7269\u8f66\u5305\u542b\uff1a\u5168\u7403\u8d2d\u5546\u54c1\uff0c\u9700\u548c\u5176\u4ed6\u5546\u54c1\u5206\u5f00\u7ed3\u7b97\uff01";
    var a = "\u5168\u7403\u8d2d\u5546\u54c1";
    var j = "\u5176\u4ed6\u5546\u54c1";
    var g = "\u8fd4\u56de\u8d2d\u7269\u8f66";
    var k = "\u53bb\u7ed3\u7b97";
    var o = "";
    o += '<div id="pop-modal" class="pop-floor" style="display:none;">';
    o += '<div class="cover-floor"></div>';
    o += '<div class="info-box">';
    o += "<form>";
    o += '<div class="info-box-content" id="pop-info">' + r + "</div>";
    o += '<div class="info-box-item">';
    o += '<div class="options">';
    o += '<div class="options-check-wrapper">';
    o += '<input type="radio" id="internationalID" name="shopping" checked="checked" class="cssRadio"/>';
    o += '<label class="btnClass" for="internationalID"></label>';
    o += "</div>";
    o += '<div class="options-content">';
    o += '<div class="options-title" id="pop-title-international">' + a + "</div>";
    o += '<div class="options-info"><span id="international-numbersID">0</span>\u4ef6</div>';
    o += "</div>";
    o += "</div>";
    o += '<div class="options">';
    o += '<div class="options-check-wrapper">';
    o += '<input type="radio" id="otherID" name="shopping"  class="cssRadio"/>';
    o += '<label class="btnClass" for="otherID"></label>';
    o += "</div>";
    o += '<div class="options-content">';
    o += '<div class="options-title" id="pop-title-regular">' + j + "</div>";
    o += '<div class="options-info"><span id="other-numbersID">0</span>\u4ef6</div>';
    o += "</div>";
    o += "</div>";
    o += "</div>";
    o += '<div class="btn-box">';
    o += '<a href="#jd" class="btn-box-back" id="back-to-cart">' + g + '</a><span class="btn-box-space"></span><a href="#jd" class="btn-box-check" id="continue-payment">' + k + "</a>";
    o += "</form></div>";
    o += "</div>";
    o += "</div>";
    var d, b, i, l, f, h;
    var c = {
        initModal: function () {
            $("body").append(o);
            b = $("#pop-modal");
            mdlForm = b.find("form").eq(0);
            i = $("#international-numbersID");
            l = $("#other-numbersID");
            f = $("#back-to-cart");
            h = $("#continue-payment");
            d = $("body").height();
            $(".cover-floor").css({height: d});
            var s = parseInt((document.documentElement.clientWidth - 272) / 2);
            var t = parseInt((document.documentElement.clientHeight - 280) / 2);
            $(".info-box").css({marginLeft: s, marginTop: t})
        }, show: function () {
            b.show()
        }, hide: function () {
            b.hide()
        }, clear: function () {
            mdlForm.reset()
        }, draw: function () {
            i.html(q.globalNum);
            l.html(q.localNum)
        }
    };
    var p = function () {
        c.initModal();
        c.draw();
        c.show();
        n()
    };
    var n = function () {
        f.unbind("click");
        h.unbind("click");
        f.bind("click", function () {
            pingClick(cartUse, "MGlobalCart_BacktoCart", "international", "", "http://p.m.jd.com/cart/cart.action");
            c.hide()
        });
        h.bind("click", function () {
            q.callback();
            c.hide()
        })
    };
    return p()
};
function resizeBottomPriseFontSize() {
    var a = 8;
    $(".bottom-bar-price").each(function () {
        var b = $(this).html();
        switch (b.length) {
            case 17:
                a = resizeScreenWidth(8);
                $(this).css("font-size", a + "px");
                break;
            case 16:
                a = resizeScreenWidth(8);
                $(this).css("font-size", a + "px");
                break;
            case 15:
                a = resizeScreenWidth(9);
                $(this).css("font-size", a + "px");
                break;
            case 14:
                a = resizeScreenWidth(9);
                $(this).css("font-size", a + "px");
                break;
            case 13:
                a = resizeScreenWidth(10);
                $(this).css("font-size", a + "px");
                break;
            case 12:
                a = resizeScreenWidth(11);
                $(this).css("font-size", a + "px");
                break;
            case 11:
                a = resizeScreenWidth(12);
                $(this).css("font-size", a + "px");
                break;
            case 10:
                a = resizeScreenWidth(14);
                $(this).css("font-size", a + "px");
                break;
            case 9:
                a = resizeScreenWidth(15);
                $(this).css("font-size", a + "px");
                break;
            default:
                $(this).css("font-size", "16px")
        }
    })
}
function resizeBottomTotalFontSize() {
    var a = 0;
    $(".bottom-total-price").each(function () {
        if (a = 1) {
        }
        var b = $(this).html().replace(/<.*?>/g, "");
        var c = 8;
        switch (b.length) {
            case 31:
                c = resizeScreenWidth(8);
                $(this).css("font-size", c + "px");
                $(this).find("span").css("font-size", c + "px");
                break;
            case 30:
                c = resizeScreenWidth(8);
                $(this).css("font-size", c + "px");
                $(this).find("span").css("font-size", c + "px");
                break;
            case 29:
                c = resizeScreenWidth(8);
                $(this).css("font-size", c + "px");
                $(this).find("span").css("font-size", c + "px");
                break;
            case 28:
                c = resizeScreenWidth(8);
                $(this).css("font-size", c + "px");
                $(this).find("span").css("font-size", c + "px");
                break;
            case 27:
                c = resizeScreenWidth(8);
                $(this).css("font-size", c + "px");
                $(this).find("span").css("font-size", c + "px");
                break;
            case 26:
                c = resizeScreenWidth(8);
                $(this).css("font-size", c + "px");
                $(this).find("span").css("font-size", c + "px");
                break;
            case 25:
                c = resizeScreenWidth(8);
                $(this).css("font-size", c + "px");
                $(this).find("span").css("font-size", c + "px");
                break;
            case 24:
                c = resizeScreenWidth(8);
                $(this).css("font-size", c + "px");
                $(this).find("span").css("font-size", c + "px");
                break;
            case 23:
                c = resizeScreenWidth(9);
                $(this).css("font-size", c + "px");
                $(this).find("span").css("font-size", c + "px");
                break;
            case 22:
                c = resizeScreenWidth(9);
                $(this).css("font-size", c + "px");
                $(this).find("span").css("font-size", c + "px");
                break;
            case 21:
                c = resizeScreenWidth(9);
                $(this).css("font-size", c + "px");
                $(this).find("span").css("font-size", c + "px");
                break;
            case 20:
                c = resizeScreenWidth(10);
                $(this).css("font-size", c + "px");
                $(this).find("span").css("font-size", c + "px");
                break;
            case 19:
                c = resizeScreenWidth(11);
                $(this).css("font-size", c + "px");
                $(this).find("span").css("font-size", c + "px");
                break;
            case 17:
                c = resizeScreenWidth(12);
                $(this).css("font-size", c + "px");
                $(this).find("span").css("font-size", c + "px");
                break;
            default:
                $(this).css("font-size", "10px");
                $(this).find("span").css("font-size", "10px")
        }
        a = 1
    })
}
function resizeScreenWidth(b) {
    var c = document.body.clientWidth;
    if (c == 320) {
        return b
    } else {
        if (c >= 480) {
            c = 480
        }
    }
    var a = (c - 100) / 220;
    return b * a
}
var selectGroup = function (e) {
    var d = 6;
    var b = $(".group-" + e);
    var c = "";
    if ($("#checkShop" + e).hasClass("checked")) {
        $("#checkShop" + e).removeClass("checked");
        b.each(function () {
            if ($(this).hasClass("checked")) {
                $(this).removeClass("checked")
            }
            var f = $(this).attr("data-sku");
            if (f != null) {
                if (c == "") {
                    c = f
                } else {
                    c = c + "@@@" + f
                }
            }
        })
    } else {
        $("#checkShop" + e).addClass("checked");
        b.each(function () {
            if (!$(this).hasClass("checked")) {
                $(this).addClass("checked")
            }
            var f = $(this).attr("data-sku");
            if (f != null) {
                if (c == "") {
                    c = f
                } else {
                    c = c + "@@@" + f
                }
            }
        });
        d = 5
    }
    var a = "/cart/checkWares.json?wareInfos=" + c + "&checked=" + d + "&sid=" + $("#sid").val();
    spinerShow();
    jQuery.get(a, {}, function (f) {
        if (f != undefined) {
            calcCartCheck(f, "");
            calcCartGifts(f, e);
            spinerHide()
        } else {
            if ($("#checkShop" + e).hasClass("checked")) {
                $("#checkShop" + e).removeClass("checked");
                b.each(function () {
                    if ($(this).hasClass("checked")) {
                        $(this).removeClass("checked")
                    }
                })
            } else {
                $("#checkShop" + e).addClass("checked");
                b.each(function () {
                    if (!$(this).hasClass("checked")) {
                        $(this).addClass("checked")
                    }
                })
            }
        }
    })
};
var popMessage = function (m) {
    var g = {callback: null};
    var e = $.extend(g, m);
    var d = e.description || "ȷ��Ҫɾ������";
    var h = e.cancelText || "ȡ��";
    var f = e.saveText || "ȷ��";
    var n = "";
    n += '<div class="cart-pop-floor" id="pop-modal">';
    n += '<div class="cart-cover-floor"></div>';
    n += ' <div class="cart-info-box">';
    n += ' <div class="cart-info-box-content cart-bdb-1px">';
    n += ' <span class="cart-info-box-text">' + d + "</span>";
    n += ' </div><div class="cart-btn-box">';
    n += ' <a class="cart-btn-box-back" id="cancel">' + h + "</a>";
    n += ' <a  class="cart-btn-box-check" id="isok">' + f + "</a>";
    n += " </div></div></div>";
    var j, c, a, i;
    var k = {
        initModel: function () {
            c = $("#pop-modal");
            if (c.length == 0) {
                $("body").append(n);
                c = $("#pop-modal")
            }
            a = $("#cancel");
            i = $("#isok");
            j = $("body").height();
            var o = parseInt((document.documentElement.clientWidth) * 0.15 / 2);
            var p = parseInt((document.documentElement.clientHeight - 151) / 2);
            $(".cart-info-box").css({marginLeft: o, marginTop: p})
        }, show: function () {
            c.show()
        }, hide: function () {
            c.show()
        }
    };
    var l = function () {
        k.initModel();
        c.show();
        b()
    };
    var b = function () {
        a.unbind("click");
        i.unbind("click");
        a.bind("click", function () {
            e.callback(false);
            c.hide()
        });
        i.bind("click", function () {
            e.callback(true);
            c.hide()
        })
    };
    return l()
};
var controlBin = {
    openBin: function () {
        var b = document.querySelectorAll(".wastebin");
        for (var a = 0; a < b.length; a++) {
            $(b[a]).bind("click", function () {
                if ($(this).find(".wastebin-up").hasClass("wastebin-up-animi")) {
                } else {
                    $(this).find(".wastebin-up").removeClass("wastebin-down-animi");
                    $(this).find(".wastebin-up").addClass("wastebin-up-animi");
                    $(this).removeClass("westebin-rock-animi")
                }
                popMessage({
                    callback: function (c) {
                        controlBin.closeBin();
                        if (c) {
                        }
                    }, description: "ȷ��Ҫɾ����Щ��Ʒ����"
                })
            })
        }
    }, closeBin: function () {
        var b = document.querySelectorAll(".wastebin");
        for (var a = 0; a < b.length; a++) {
            if ($(b[a]).find(".wastebin-up").hasClass("wastebin-up-animi")) {
                $(b[a]).addClass("westebin-rock-animi");
                $(b[a]).find(".wastebin-up").removeClass("wastebin-up-animi");
                $(b[a]).find(".wastebin-up").addClass("wastebin-down-animi")
            } else {
            }
        }
    }
};
function controlPop(b) {
    var e = {maskID: "mask"};
    var c = $.extend(e, b);
    var a = document.getElementById(c.maskID);
    var d = function () {
        var f = document.getElementById("mask-click-hidden");
        if (f == null) {
            f = document.createElement("div");
            f.className = "mask-click-hidden";
            f.id = "mask-click-hidden";
            a.appendChild(f)
        }
        f.addEventListener("click", function () {
            a.style.display = "none"
        })
    };
    return d()
}
var calcShopCheck = function (d, f) {
    try {
        if (!isBlank(d)) {
            var g = d.vendors;
            var h;
            var k;
            var c;
            var j;
            if (f == null) {
                for (var a = 0; a < g.length; a++) {
                    h = g[a].shopId;
                    k = $(".group-" + h);
                    j = k.length;
                    c = k.filter(".checked").length;
                    if (j == c) {
                        $("#checkShop" + h).addClass("checked")
                    } else {
                        $("#checkShop" + h).removeClass("checked")
                    }
                }
            } else {
                for (var a = 0; a < g.length; a++) {
                    h = g[a].shopId;
                    if (f == h) {
                        k = $(".group-" + h);
                        j = k.length;
                        c = k.filter(".checked").length;
                        if (j == c) {
                            $("#checkShop" + h).addClass("checked")
                        } else {
                            $("#checkShop" + h).removeClass("checked")
                        }
                        return
                    }
                }
            }
        }
    } catch (b) {
    }
};