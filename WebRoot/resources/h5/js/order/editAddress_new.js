$(function () {
    var d = 10;
    var q = 40;
    var m = new Array();

    function l() {
        m.push(new Date());
        if (m.length > 1 && ((m[m.length - 1].getTime() - m[m.length - 2].getTime()) < 360000000)) {
            return true
        }
        return false
    }

    function c(s) {
        var i = new RegExp("^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]|[a-zA-Z0-9])*$");
        if (Boolean(s)) {
            return i.test(s)
        }
        return false
    }

    function r(t) {
        var s = new RegExp("^(\\d{7}|\\d{10}|\\d{11})$");
        var i = $("#old_address_mobile").val();
        if (Boolean(i) && i == t) {
            return true
        } else {
            if (s.test(t)) {
                return k(t)
            } else {
                return false
            }
        }
    }

    function k(s) {
        var i = true;
        $.ajax({
            type: "post",
            url: "/norder/validateMobile.action",
            data: {mobile: s},
            async: false,
            dataType: "json",
            error: function () {
            },
            success: function (t) {
                if (!Boolean(t.Flag) && Boolean(t.Message)) {
                    i = false
                }
            }
        });
        return i
    }

    function p(i) {
        return Boolean(i) && i.length <= q ? true : false
    }

    function o() {
        return Boolean($("#btn-select-region").attr("region-data"))
    }

    function g() {
        var t = $("#uersNameId").val();
        var s = $("#mobilePhoneId").val();
        var i = $("#address_where").val();
        return (c(t) && r(s) && p(i) && o())
    }

    function j() {
        var t = $("#uersNameId").val();
        var s = $("#mobilePhoneId").val();
        var i = $("#address_where").val();
        if (!c(t)) {
            n("收件人姓名只能有<br>中文、英文字符或数字组成", 2000)
        } else {
            if (!r(s)) {
                n("输入手机格式不对，请重新输入", 2000)
            } else {
                if (!o()) {
                    n("所在地区不能为空", 2000)
                } else {
                    if (!p(i)) {
                        n("详细地址不能为空", 2000)
                    }
                }
            }
        }
    }

    function b() {
        var u = $("#uersNameId").val();
        var t = $("#mobilePhoneId").val();
        var s = $("#address_where").val();
        var i = o();
        return Boolean(u && i && t && s)
    }

    function a() {
        if (b()) {
            $("#submitId").css({color: "#fff", background: "#f35656", border: "1px solid #f35656"})
        } else {
            $("#submitId").css({color: "#bfbfbf", background: "#f0f0f0", border: "1px solid #f0f0f0"})
        }
    }

    function n(i, s) {
        $(".verify").html(i);
        $(".popup-w").css({height: "100%", display: "block"});
        $(".verify-w").show();
        $(".verify-w").css({position: "absolute", top: 100});
        var t = setTimeout(function () {
            $(".popup-w").hide();
            $(".verify-w").hide()
        }, s)
    }

    function h(s, i) {
        $("#succesTipId").html(s);
        $(".popup-w").css({height: "100%", display: "block"});
        $(".confirm-w").hide();
        $(".delok-w").show();
        $(".delok-w").css({top: $(window).scrollTop() + $(window).height() / 2 - $(".delok-w").height() / 2});
        var t = setTimeout(function () {
            $(".popup-w").hide();
            $(".delok-w").hide()
        }, i)
    }

    Zepto("#puller").generateRegionList({
        actionURL: "/norder/", sid: $("#sid").val(), callBack: function () {
            document.getElementById("address.idProvince").value = $("#jdDeliverList1 .checked").attr("id") ? $("#jdDeliverList1 .checked").attr("id") : 0;
            document.getElementById("address.idCity").value = $("#jdDeliverList2 .checked").attr("id") ? $("#jdDeliverList2 .checked").attr("id") : 0;
            document.getElementById("address.idArea").value = $("#jdDeliverList3 .checked").attr("id") ? $("#jdDeliverList3 .checked").attr("id") : 0;
            document.getElementById("address.idTown").value = $("#jdDeliverList4 .checked").attr("id") ? $("#jdDeliverList4 .checked").attr("id") : 0;
            var i = $("#jdDeliverList1 .checked span").text() ? $("#jdDeliverList1 .checked span").text() : "";
            var u = $("#jdDeliverList2 .checked span").text() ? $("#jdDeliverList2 .checked span").text() : "";
            var t = $("#jdDeliverList3 .checked span").text() ? $("#jdDeliverList3 .checked span").text() : "";
            var s = $("#jdDeliverList4 .checked span").text() ? $("#jdDeliverList4 .checked span").text() : "";
            $("#addressLabelId").text(i + u + t + s);
            $("#detailedAddressId").text(i + u + t + s);
            $("#provinceNameIgnoreId").val(i);
            $("#cityNameIgnoreId").val(u);
            $("#areaNameIgnoreId").val(t);
            $("#townNameIngoreId").val(s);
            a()
        }
    });
    $(".switch").click(function () {
        $(this).toggleClass("switched");
        if ("switch switched" == $(this).attr("class")) {
            $("#addressDefaultId").val(true)
        } else {
            $("#addressDefaultId").val(false)
        }
    });
    $(".confirm-del").click(function () {
        if ($("#defaultFlag").val() == 1 || $("#addressTypeId").val() == "add") {
            return false
        }
        if ($(document).height() < $(window).height()) {
            $(".popup-w").css({height: $(window).height(), display: "block"})
        } else {
            $(".popup-w").css({height: $(document).height(), display: "block"})
        }
        $(".confirm-w").show();
        $(".confirm-w").css({top: $(window).scrollTop() + $(window).height() / 2 - $(".confirm-w").height() / 2});
        $(".cb-ok").click(function () {
            h("删除成功!", 1000);
            window.location = $("#deleteButton").attr("action")
        });
        $("#cancelDeleteAddress").click(function () {
            $(".confirm-w").hide();
            $(".popup-w").hide()
        })
    });
    $(".ad-name").blur(function () {
        a();
        o();
        if (!c($("#uersNameId").val())) {
            $("#uersNameId").val("");
            n("收件人姓名只能有<br>中文、英文字符或数字组成", 2000)
        }
    });
    $(".ad-mobile").blur(function () {
        a();
        if (!r($("#mobilePhoneId").val())) {
            n("输入手机格式不对，请重新输入", 2000)
        }
    });
    $(".textauto").blur(function () {
        a();
        if (!p($("#address_where").val())) {
            n("详细地址不能为空且最多为40个文字", 2000)
        }
    });
    $("#submitId").click(function () {
        a();
        if (g()) {
            if (l()) {
                n("请勿重复提交", 2000);
                return true
            }
            if (document.getElementById("address.idProvince").value == 84 || document.getElementById("provinceNameIgnoreId").value == "钓鱼岛") {
                h("保存失败!", 2000);
                m.length = 0
            } else {
                h("保存并使用成功!", 1000);
                $("#editAddressForm").submit()
            }
            return true
        }
        j();
        return false
    });
    $("#mobileInputDiv")[0].oninput = function () {
        a()
    };
    $(".ad-name")[0].oninput = function (s) {
        var i = $("#uersNameId").val();
        if (i && i.length > d) {
            $("#uersNameId").val(i.substr(0, d));
            n("收货人姓名多为10个文字", 2000)
        }
        a()
    };
    var f = document.getElementsByClassName("textauto");
    for (var e = 0; e < f.length; e++) {
        f[e].oninput = function (t) {
            a();
            var s = $("#address_where").val();
            if (s && s.length > q) {
                $("#address_where").val(s.substr(0, q));
                n("详细地址多为40个文字", 2000)
            }
            var i = this.scrollHeight;
            if (i > 25 && i < 100) {
                this.style.height = i + "px"
            } else {
                if (i > 80) {
                    this.style.height = 80 + "px"
                } else {
                    if (i < 25) {
                        this.style.height = 25 + "px"
                    }
                }
            }
        }
    }
});