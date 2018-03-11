var spiner = createSpinner();
var background = document.getElementById("background");
var spinerShow = function () {
    background.style.display = "block";
    spiner.spin(background)
};
var spinerHide = function () {
    background.style.display = "none";
    spiner.stop()
};
function sliderLoad() {
    if (location.hash.length != 0) {
        location.hash = ""
    }
}
function backScroll() {
    background.style.top = document.body.scrollTop + "px"
}
window.onscroll = backScroll;
var openPwdWindowHeight = $("#openPwdWindow").height();
var inputPwdWindowHeight = $("#inputPwdWindow").height();
var stockWindowHeight = $("div[name='stockWindow']").height();
var initConfirmDiv = function () {
    $(".confirm-w").css({display: "none"})
}();
var checkStockout = function () {
    if ($("#stockStatus").val() == "1") {
        openStockWindow()
    }
}();
var calcYunfee = function (e, a, c) {
    var b = "";
    if (e == 1) {
        var d = $("#bean_select").find("option:selected").val();
        b = "&updateFlag=1&IsUseJdBean=" + a;
        if (!isNaN(d)) {
            b += "&useJdBeanCount=" + d + ""
        }
    } else {
        b = "&updateFlag=0&useBalance=" + a
    }
    if (c || c == 0) {
        b += "&flowType=" + c
    }
    jQuery.get("/norder/calcYunfeeVm.action?sid=" + $("#sid").val() + b, function (f) {
        if (f != null) {
            if (f.indexOf("Copyright") == -1) {
                $("#yunfeeList").html(f);
                $("#payMoney").text($("#hiddenPayMoney").val());
                window.scrollTo(0, $(window).scrollTop() + 500)
            }
        }
    })
};
function stockVerify() {
}
function virtualPayHandler() {
    if ($("#isUsedVirtualPay").val() == "true") {
        if ($("#isOpenPaymentPassword").val() == "true") {
            openInputPwdWindow()
        } else {
            openPwdLinkWindow()
        }
    }
}
function submitOrder(a) {
    if ($("#stockStatus").val() != "2") {
        openStockWindow()
    } else {
        if ($("#isUsedVirtualPay").val() == "false") {
            setTimeout("$('#orderForm').submit()", 100);
            pingClick(a, "MNeworder_Submit", "", "", "")
        } else {
            virtualPayHandler()
        }
    }
}
function virtualPaySubmit(c) {
    if ($("#isUsedVirtualPay").val() == "true" && $("#securityPayPassword").val()) {
        var b = $("#sid").val();
        var d = $("#flowTypeId").val() || $("#flowTypeId").val() == 0 ? $("#flowTypeId").val() : "";
        var f = $("#securityPayPassword").val();
        var a = $("#paymentId").val();
        var e = $("#isMixPayMentId").val();
        jQuery.post("/norder/checkVirtualPay.action", {
            "order.securityPayPassword": f,
            sid: b,
            flowType: d,
            mixPayMent: e
        }, function (l, k) {
            $("#virtualPayTipData").html(l);
            if ($("#FlagId") && $("#FlagId").val() == "false") {
                $("#erroTip").text($("#MessageId").val());
                $(".pay-password input").addClass("pp-shake");
                $(".pay-password input").val("");
                $(".pp-btn").addClass("pass-no");
                $(".pay-password p").hide();
                $(".pay-password .pp-red").show()
            } else {
                var i = Boolean($("#factPriceId").val()) ? $("#factPriceId").val() : "";
                var h = Boolean($("#orderNumberId").val()) ? $("#orderNumberId").val() : "";
                if (a == 4 && e == "false" && Number(i) != 0) {
                    var j = Boolean($("#skuIdsListId").val()) ? $("#skuIdsListId").val() : "";
                    var g = "/norder/payOnLine.action?orderId=" + h + "&factPrice=" + i + "&skuIdsList=" + j;
                    setTimeout(location.href = g, 100)
                } else {
                    var o = e;
                    var n = Boolean($("#paymentTypeId").val()) ? $("#paymentTypeId").val() : "";
                    var m = "/norder/successOrder.action?orderId=" + h + "&sid=" + b + "&mixPayMent=" + o + "&paymentType=" + n + "&factPrice=" + i;
                    setTimeout(location.href = m, 100)
                }
            }
        });
        pingClick(c, "MNeworder_Submit", "", "", "")
    }
}
var jdBeanHandler = function (b, a) {
    if ($("#useJdbeanIcon").attr("class") == "switch") {
        $("#useJdbeanIcon").addClass("switched");
        $(".jdb-sel-num").show();
        setJdBeanDiscountTip()
    } else {
        $("#useJdbeanIcon").removeClass("switched");
        $(".jdb-sel-num").hide()
    }
    calcJdBeanAndBalance(1, b);
    pingClick(a, "MNeworder_JdBean", "", "", "")
};
var selectJdBean = function (a) {
    setJdBeanDiscountTip();
    calcYunfee(1, true, a)
};
var balanceHandler = function (b, a) {
    if ($("#useBalanceIcon").attr("class") == "switch") {
        $("#useBalanceIcon").addClass("switched")
    } else {
        $("#useBalanceIcon").removeClass("switched")
    }
    calcJdBeanAndBalance(0, b);
    pingClick(a, "MNeworder_RestAccount", "", "", "")
};
var calcJdBeanAndBalance = function (a, b) {
    if (a == 1) {
        if ($("#useJdbeanIcon").attr("class") == "switch switched") {
            calcYunfee(1, true, b)
        } else {
            calcYunfee(1, false, b)
        }
    } else {
        if ($("#useBalanceIcon").attr("class") == "switch switched") {
            calcYunfee(0, true, b)
        } else {
            calcYunfee(0, false, b)
        }
    }
};
var setJdBeanDiscountTip = function () {
    var c = $("#bean_select").find("option:selected").val();
    var b = $("#bean_select").find("option:selected").attr("rate");
    var a = c / b;
    $("#jdBeanDiscount_tip").html("\u62b5\uffe5" + a)
};
function openStockWindow() {
    $(".popup-w").css({height: $(document).height() + $("#pay-bar").height() * 4, display: "block"});
    $("div[name='stockWindow']").css({
        top: $(window).scrollTop() + $(window).height() / 2 - stockWindowHeight / 2,
        display: "block"
    })
}
function closeStockWindow() {
    $(".popup-w").css({display: "none"});
    $("div[name='stockWindow']").css({display: "none"})
}
function openPwdLinkWindow() {
    $(".popup-w").css({height: $(document).height(), display: "block"});
    $("#openPwdWindow").css({
        top: $(window).scrollTop() + $(window).height() / 2 - openPwdWindowHeight / 2,
        display: "block"
    })
}
function closePwdLinkWindow() {
    $(".popup-w").css({display: "none"});
    $("#openPwdWindow").css({display: "none"})
}
function openInputPwdWindow() {
    $(".popup-w").css({height: $(document).height(), display: "block"});
    $("#inputPwdWindow").css({
        display: "block",
        top: $(window).scrollTop() + $(window).height() / 2 - inputPwdWindowHeight / 2
    });
    var a = 6;
    $(".pay-password input")[0].oninput = function () {
        if ($(".pay-password input").val() == "") {
            $(".pp-btn").addClass("pass-no");
            $(".pass-del").hide()
        } else {
            $(".pp-btn").removeClass("pass-no");
            $(".pass-del").show()
        }
    };
    $(".pass-del").click(function () {
        $(".pay-password input").val("");
        $(".pp-btn").addClass("pass-no")
    });
    if ($(this).hasClass("pass-no1")) {
        return
    } else {
        $(".pp-btn").click(function () {
            $(".pass-del").hide();
            if ($(this).hasClass("pass-no")) {
                return
            } else {
                var b = setTimeout(function () {
                    $(".pay-password input").removeClass("pp-shake")
                }, 1000)
            }
        })
    }
}
function closeInputPwdWindow() {
    $(".popup-w").css({display: "none"});
    $("#inputPwdWindow").css({display: "none"})
}
var checkSecurityPayPassword = function () {
    var a = $("#payPassword").val();
    if ($("#showPayPassword").val() == "true" && (a == undefined || a.trim() == "")) {
        $("#submiOrder").addClass("new-abtn-default");
        return false
    } else {
        $("#submiOrder").removeClass("new-abtn-default");
        return true
    }
};
var orderDate = new Array();
var verifyPassPort = function () {
    if ($("#userPayPassword").val() != "true") {
        if (confirm("\u4e3a\u4e86\u4fdd\u969c\u60a8\u7684\u8d44\u4ea7\u5b89\u5168\uff0c\u60a8\u9700\u8981\u5f00\u542f\u652f\u4ed8\u5bc6\u7801\u624d\u80fd\u4f7f\u7528\u4eac\u8c46\u3001\u4f59\u989d\u7b49\u865a\u62df\u8d44\u4ea7\u3002\u662f\u5426\u5f00\u542f\u652f\u4ed8\u5bc6\u7801\uff1f")) {
            location.href = "https://passport.m.jd.com/payPassword/openPayPassword.action?urlFrom=2&sid=" + $("#sid").val()
        }
    }
};
var verifyOrder = function () {
    var a = $("#isIdTown").val();
    if ("true" == a) {
        if (confirm("\u4e3a\u4e86\u8ba9\u60a8\u4eab\u53d7\u66f4\u4e3a\u7cbe\u51c6\u7684\u914d\u9001\u670d\u52a1\uff0c\u6211\u4eec\u63d0\u4f9b\u4e86\u56db\u7ea7\u5730\u5740\u9009\u9879\uff0c\u8bf7\u60a8\u7acb\u523b\u5b8c\u5584\u5730\u5740\u4fe1\u606f\u4ee5\u514d\u5f71\u54cd\u6b63\u5e38\u4e0b\u5355")) {
            $("#addressLink").click()
        }
        return false
    }
    orderDate.push(new Date());
    if (orderDate.length > 1 && (orderDate[orderDate.length - 1].getTime() - orderDate[orderDate.length - 2].getTime() < 2000)) {
        return false
    }
    if (!checkSecurityPayPassword()) {
        if ($("#userPayPassword").val() != "true") {
            if (confirm("\u4e3a\u4e86\u4fdd\u969c\u60a8\u7684\u8d44\u4ea7\u5b89\u5168\uff0c\u60a8\u9700\u8981\u5f00\u542f\u652f\u4ed8\u5bc6\u7801\u624d\u80fd\u4f7f\u7528\u4eac\u8c46\u3001\u4f59\u989d\u7b49\u865a\u62df\u8d44\u4ea7\u3002\u662f\u5426\u5f00\u542f\u652f\u4ed8\u5bc6\u7801\uff1f")) {
                location.href = "https://passport.m.jd.com/payPassword/openPayPassword.action?urlFrom=2&sid=" + $("#sid").val()
            }
        } else {
            alert("\u652f\u4ed8\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a")
        }
        return false
    }
    return true
};
$(function () {
    $("#bean_select").bind("click", function () {
        $(".mask").css({height: document.documentElement.scrollHeight + "px"}).show()
    });
    $("#bean_select").bind("blur focusout change", function () {
        $(".mask").hide()
    });
    $(".mask").bind("click", function () {
        $(".mask").hide()
    });
    $(".pos-rel.arrow-down").bind("click", function () {
        $("#bean_select").click()
    })
});
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