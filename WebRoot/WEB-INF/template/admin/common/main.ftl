[#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.main.title")}</title>


    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <link href="${base}/resources/admin/css/main.css" rel="stylesheet" type="text/css" />
    <link rel="icon" href="${base}/favicon.ico" mce_href="${base}/favicon.ico" type="image/x-icon">
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <style type="text/css">
        *{
            font: 12px tahoma, Arial, Verdana, sans-serif;
        }
        html, body {
            width: 100%;
            height: 100%;
            overflow: hidden;
        }
    </style>
    <script type="text/javascript">
        $().ready(function() {

            var $nav = $("#nav a");
            var $menu = $("#menu dl");
            var $menuItem = $("#menu a");
            var $video = $("#video");

            $nav.click(function() {
                var $this = $(this);
                $nav.removeClass("current");
                $this.addClass("current");
                var $currentMenu = $($this.attr("href"));
                $menu.hide();
                $currentMenu.show();
                $("#iframe").attr("src",$currentMenu.find("a:first").attr("href"));
                return false;
            });

            $menuItem.click(function() {
                var $this = $(this);
                $menuItem.removeClass("current");
                $this.addClass("current");
            });

            $video.live("click", function(){
                win = window.open("../video_call_account/index.jhtml", "OAopenWindow", "toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no,status=no,width=" + (screen.width - 5) + ",height=" + (screen.height - 38) + ",top=0,left=0");
            });



        });
    </script>
</head>
<body>
<script type="text/javascript">
    if (self != top) {
        top.location = self.location;
    };
</script>
<table class="main">
    <tr>
        <th class="logo">
            <a href="main.jhtml">
                <img src="${base}/resources/admin/images/header_logo.gif" />
            </a>
        </th>
        <th>
            <div id="nav" class="nav">
                <ul>
                [#list ["admin:member", "admin:memberRank", "admin:memberAttribute", "admin:consultation"] as permission]
                    [@shiro.hasPermission name = permission]
                        <li>
                            <a href="#member">${message("admin.main.memberNav")}</a>
                        </li>
                        [#break /]
                    [/@shiro.hasPermission]
                [/#list]
                [#list ["admin:ad", "admin:adPosition"] as permission]
                    [@shiro.hasPermission name = permission]
                        <li>
                            <a href="#ad">广告</a>
                        </li>
                        [#break /]
                    [/@shiro.hasPermission]
                [/#list]
                [#--[#list ["admin:planeBrand","admin:specialList","admin:quote","admin:push","admin:inland_cost","admin:foreign_cost","admin:order_passenger","admin:order_airline"] as permission]
                    [@shiro.hasPermission name = permission]
                        <li>
                            <a href="#operation" >${message("admin.main.operation")}</a>
                        </li>
                        [#break /]
                    [/@shiro.hasPermission]
                [/#list]
                [#list ["admin:order","admin:cache"] as permission]
                    [@shiro.hasPermission name = permission]
                        <li>
                            <a href="#order" >${message("admin.main.orderNav")}</a>
                        </li>
                        [#break /]
                    [/@shiro.hasPermission]
                [/#list]
                [#list ["admin:customer","admin:customerFeedback"] as permission]
                    [@shiro.hasPermission name = permission]
                        <li>
                            <a href="#customer" >${message("admin.main.customer")}</a>
                        </li>
                        [#break /]
                    [/@shiro.hasPermission]
                [/#list]
                [#list ["admin:planeBrand","admin:cache"] as permission]
                    [@shiro.hasPermission name = permission]
                        <li>
                            <a href="#statistics" >${message("admin.main.statisticsNav")}</a>
                        </li>
                        [#break /]
                    [/@shiro.hasPermission]
                [/#list]--]
                [#list ["admin:setting", "admin:area", "admin:paymentMethod", "admin:shippingMethod", "admin:deliveryCorp", "admin:paymentPlugin", "admin:storagePlugin", "admin:refundsPlugin", "admin:admin", "admin:role", "admin:message", "admin:log","admin:software_manage","admin:versions_update","admin:institution","admin:seo","admin:sitemap","admin:template", "admin:cache","admin:company","admin:resources"] as permission]
                    [@shiro.hasPermission name = permission]
                        <li>
                            <a href="#system">${message("admin.main.systemGroup")}</a>
                        </li>
                        [#break /]
                    [/@shiro.hasPermission]
                [/#list]
                    <li>
                        <a href="#" target="_blank">${message("admin.main.home")}</a>
                    </li>
                </ul>
            </div>
            <div class="link">
            </div>
            <div class="link">
                <strong>[@shiro.principal /]</strong>
            ${message("admin.main.hello")}!
                <a href="../profile/edit.jhtml" target="iframe">[${message("admin.main.profile")}]</a>
                <a href="../logout.jsp" target="_top">[${message("admin.main.logout")}]</a>
            </div>
        </th>
    </tr>
    <tr>
        <td id="menu" class="menu">
            <dl id="member">
                <dt>${message("admin.main.memberGroup")}</dt>
            [@shiro.hasPermission name="admin:member"]
                <dd>
                    <a href="../member/list.jhtml" target="iframe">${message("admin.main.member")}</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:memberRank"]
                <dd>
                    <a href="../member_rank/list.jhtml" target="iframe">${message("admin.main.memberRank")}</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:review"]
                <dd>
                    <a href="../review/list.jhtml" target="iframe">${message("admin.main.review")}</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:consultation"]
                <dd>
                    <a href="../consultation/list.jhtml" target="iframe">${message("admin.main.consultation")}</a>
                </dd>
            [/@shiro.hasPermission]
            </dl>

            <dl id="ad">
                <dt>广告位</dt>
            [@shiro.hasPermission name="admin:ad"]
                <dd>
                    <a href="../ad/list.jhtml" target="iframe">广告</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:adPosition"]
                <dd>
                    <a href="../ad_position/list.jhtml" target="iframe">广告位</a>
                </dd>
            [/@shiro.hasPermission]
            </dl>
           [#-- <dl id="base" style="display: block;">
                <dt>${message("admin.main.base")}</dt>
            [@shiro.hasPermission name="admin:planeBrand"]
                <dd>
                    <a href="../plane_brand/list.jhtml" target="iframe">${message("admin.main.planeBrand")}</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:airplane"]
                <dd>
                    <a href="../airplane/list.jhtml" target="iframe">${message("admin.main.airplane")}</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:airport"]
                <dd>
                    <a href="../airport/list.jhtml" target="iframe">${message("admin.main.airport")}</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:airline"]
                <dd>
                    <a href="../airline/list.jhtml" target="iframe">${message("admin.main.airline")}</a>
                </dd>
            [/@shiro.hasPermission]
            </dl>--]

            <dl id="system" >
                <dt>${message("admin.main.systemGroup")}</dt>
            [@shiro.hasPermission name="admin:setting"]
                <dd>
                    <a href="../setting/edit.jhtml" target="iframe">${message("admin.main.setting")}</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:admin"]
                <dd>
                    <a href="../admin/list.jhtml" target="iframe">${message("admin.main.admin")}</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:role"]
                <dd>
                    <a href="../role/list.jhtml" target="iframe">${message("admin.main.role")}</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:company"]
                <dd>
                    <a href="../company/list.jhtml" target="iframe">${message("admin.main.company")}</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:area"]
                <dd>
                    <a href="../area/list.jhtml" target="iframe">${message("admin.main.area")}</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:paymentMethod"]
                <dd>
                    <a href="../payment_method/list.jhtml" target="iframe">${message("admin.main.paymentMethod")}</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:shippingMethod"]
                <dd>
                    <a href="../shipping_method/list.jhtml" target="iframe">${message("admin.main.shippingMethod")}</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:deliveryCorp"]
                <dd>
                    <a href="../delivery_corp/list.jhtml" target="iframe">${message("admin.main.deliveryCorp")}</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:paymentPlugin"]
                <dd>
                    <a href="../payment_plugin/list.jhtml" target="iframe">${message("admin.main.paymentPlugin")}</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:refundsPlugin"]
                <dd>
                    <a href="../refunds_plugin/list.jhtml" target="iframe">${message("admin.main.refundsPlugin")}</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:log"]
                <dd>
                    <a href="../log/list.jhtml" target="iframe">${message("admin.main.log")}</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:storagePlugin"]
                <dd>
                    <a href="../storage_plugin/list.jhtml" target="iframe">${message("admin.main.storagePlugin")}</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:software_manage"]
                <dd>
                    <a href="../software_manage/list.jhtml" target="iframe">${message("admin.main.software_manage")}</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:resources"]
                <dd>
                    <a href="../resources/list.jhtml" target="iframe">${message("admin.main.resources")}</a>
                </dd>
            [/@shiro.hasPermission]
            </dl>

            [#--<dl id="operation">
                <dt>${message("admin.main.operation")}</dt>
            [@shiro.hasPermission name="admin:planeBrand"]
                <dd>
                    <a href="../flightplan/list.jhtml" target="iframe">航班计划</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:specialList"]
                <dd>
                    <a href="../flightplan/specialList.jhtml" target="iframe">特价航段</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:quote"]
                <dd>
                    <a href="../quote/list.jhtml" target="iframe">报价管理</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:push"]
                <dd>
                    <a href="../push/list.jhtml" target="iframe">推送</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:inland_cost"]
                <dd>
                    <a href="../inland_cost/list.jhtml" target="iframe">机场代理费</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:order_passenger"]
                <dd>
                    <a href="../order_passenger/list.jhtml" target="iframe">乘客管理</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:order_airline"]
                <dd>
                    <a href="../order_airline/list.jhtml" target="iframe">行线乘客管理</a>
                </dd>
            [/@shiro.hasPermission]

            </dl>

            <dl id="order">
                <dt>${message("admin.main.orderNav")}</dt>
            [@shiro.hasPermission name="admin:order"]
                <dd>
                    <a href="../order/list.jhtml" target="iframe">${message("admin.main.order")}</a>
                </dd>
                <dd>
                    <a href="../order/valetList.jhtml" target="iframe">代客订单</a>
                </dd>
            [/@shiro.hasPermission]
            </dl>

            <dl id="customer">
                <dt>${message("admin.main.customer")}</dt>
            [@shiro.hasPermission name="admin:customer"]
                <dd>
                    <a href="../customer/list.jhtml" target="iframe">${message("admin.customer.lists")}</a>
                </dd>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="admin:customerFeedback"]
                <dd>
                    <a href="../customer_feedback/list.jhtml" target="iframe">${message("admin.main.customer_feedback")}</a>
                </dd>
            [/@shiro.hasPermission]
            </dl>

            <dl id="statistics">
                <dt>${message("admin.main.statisticsNav")}</dt>
            [@shiro.hasPermission name="admin:planeBrand"]
                <dd>
                    <a href="../plane_brand/list.jhtml" target="iframe">${message("admin.main.planeBrand")}</a>
                </dd>
            [/@shiro.hasPermission]
            </dl>--]

        </td>

        <td>
            <iframe id="iframe" name="iframe" src="index.jhtml" frameborder="0"></iframe>
        </td>

    </tr>
</table>
</body>
</html>
