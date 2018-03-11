[#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.order.list")}</title>


    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
    <script type="text/javascript">

        $().ready(function() {

            var $listForm = $("#listForm");
            var $filterSelect = $("#filterSelect");
            var $filterOption = $("#filterOption a");
            var $print = $("select[name='print']");
            var $categorySelect = $("#categorySelect");
            var $methodSelect = $("#methodSelect");
            var $categoryOption = $("#categoryOption a");
            var $methodOption = $("#methodOption a");
            var $export = $("#export");
            var $deleteButton = $("#deleteButton");

        [@flash_message /]

            // 导出
            $export.click(function() {
                if ($deleteButton.hasClass("disabled")) {
                    alert('${message("admin.order.selectOrder")}');
                    return false;
                }
                $listForm.attr("action","export.jhtml");
                $listForm.submit();
            });

            // 订单筛选
            $filterSelect.mouseover(function() {
                var $this = $(this);
                var offset = $this.offset();
                var $menuWrap = $this.closest("div.menuWrap");
                var $popupMenu = $menuWrap.children("div.popupMenu");
                $popupMenu.css({left: offset.left, top: offset.top + $this.height() + 2}).show();
                $menuWrap.mouseleave(function() {
                    $popupMenu.hide();
                });
            });

            // 筛选选项
            $filterOption.click(function() {
                var $this = $(this);
                var $dest = $("#" + $this.attr("name"));
                if ($this.hasClass("checked")) {
                    $dest.val("");
                } else {
                    $dest.val($this.attr("val"));
                }
                $listForm.submit();
                return false;
            });

            // 订单分类
            $categorySelect.mouseover(function() {
                var $this = $(this);
                var offset = $this.offset();
                var $menuWrap = $this.closest("div.menuWrap");
                var $popupMenu = $menuWrap.children("div.popupMenu");
                $popupMenu.css({left: offset.left, top: offset.top + $this.height() + 2}).show();
                $menuWrap.mouseleave(function() {
                    $popupMenu.hide();
                });
            });

            // 分类选项
            $categoryOption.click(function() {
                var $this = $(this);
                var $dest = $("#isSpecial");
                if ($this.hasClass("checked")) {
                    $dest.val("");
                } else {
                    $dest.val($this.attr("val"));
                }
                $listForm.submit();
                return false;
            });

            // 支付方式
            $methodSelect.mouseover(function() {
                var $this = $(this);
                var offset = $this.offset();
                var $menuWrap = $this.closest("div.menuWrap");
                var $popupMenu = $menuWrap.children("div.popupMenu");
                $popupMenu.css({left: offset.left, top: offset.top + $this.height() + 2}).show();
                $menuWrap.mouseleave(function() {
                    $popupMenu.hide();
                });
            });

            // 支付方式选项
            $methodOption.click(function() {
                var $this = $(this);
                var $dest = $("#companyId");
                if ($this.hasClass("checked")) {
                    $dest.val("");
                } else {
                    $dest.val($this.attr("val"));
                }
                $listForm.submit();
                return false;
            });

            // 打印选择
            $print.change(function() {
                var $this = $(this);
                if ($this.val() != "") {
                    window.open($this.val());
                }
            });

        });
    </script>
    <style type="text/css">
        table td p{
            line-height: 1.8;
            height: 42px;
        }
        table td p.separator {
            border-top: 1px dotted #b8d3ee;
        }
    </style>
</head>

<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.order.list")} <span>(${message("admin.page.total", page.total)})</span>
</div>
<form id="listForm" action="list.jhtml" method="get">
    <input type="hidden" id="orderStatus" name="orderStatus" value="${orderStatus}" />
    <input type="hidden" id="paymentStatus" name="paymentStatus" value="${paymentStatus}" />
    <input type="hidden" id="isSpecial" name="isSpecial" value="${isSpecial}" />
    <input type="hidden" id="companyId" name="companyId" value="${companyId}" />
    <input type="hidden" id="hasExpired" name="hasExpired" value="[#if hasExpired??]${hasExpired?string("true", "false")}[/#if]" />
    <div class="bar">
        <div class="buttonWrap">
            <a href="javascript:;" id="deleteButton" class="iconButton disabled">
                <span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
            </a>
            <a href="javascript:;" id="refreshButton" class="iconButton">
                <span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
            </a>
            <div class="menuWrap">
                <a href="javascript:;" id="methodSelect" class="button">
                ${message("airplane.company")}<span class="arrow">&nbsp;</span>
                </a>
                <div class="popupMenu">
                    <ul id="methodOption" class="check">
                        <li >
                            <a href="javascript:;" name="companyId" val=""[#if companyId== ""] class="checked"[/#if]>${message("Order.all")}</a>
                        </li>
                    [#list companyList as company]
                        <li class="separator">
                            <a href="javascript:;" name="companyId" val="${company.id}"[#if company.id == companyId] class="checked"[/#if]>${company.name}</a>
                        </li>
                    [/#list]
                    </ul>
                </div>
            </div>
            <div class="menuWrap">
                <a href="javascript:;" id="filterSelect" class="button">
                ${message("admin.order.filter")}<span class="arrow">&nbsp;</span>
                </a>
                <div class="popupMenu">
                    <ul id="filterOption" class="check">
                        <li>
                            <a href="javascript:;" name="orderStatus" val="unconfirmed"[#if orderStatus == "unconfirmed"] class="checked"[/#if]>${message("Order.OrderStatus.unconfirmed")}</a>
                        </li>
                        <li>
                            <a href="javascript:;" name="orderStatus" val="confirmed"[#if orderStatus == "confirmed"] class="checked"[/#if]>${message("Order.OrderStatus.confirmed")}</a>
                        </li>
                        <li>
                            <a href="javascript:;" name="orderStatus" val="completed"[#if orderStatus == "completed"] class="checked"[/#if]>${message("Order.OrderStatus.completed")}</a>
                        </li>
                        <li>
                            <a href="javascript:;" name="orderStatus" val="cancelled"[#if orderStatus == "cancelled"] class="checked"[/#if]>${message("Order.OrderStatus.cancelled")}</a>
                        </li>
                        <li class="separator">
                            <a href="javascript:;" name="paymentStatus" val="unpaid"[#if paymentStatus == "unpaid"] class="checked"[/#if]>${message("Order.PaymentStatus.unpaid")}</a>
                        </li>
                        <li>
                            <a href="javascript:;" name="paymentStatus" val="partialPayment"[#if paymentStatus == "partialPayment"] class="checked"[/#if]>${message("Order.PaymentStatus.partialPayment")}</a>
                        </li>
                        <li>
                            <a href="javascript:;" name="paymentStatus" val="paid"[#if paymentStatus == "paid"] class="checked"[/#if]>${message("Order.PaymentStatus.paid")}</a>
                        </li>
                        <li>
                            <a href="javascript:;" name="paymentStatus" val="partialRefunds"[#if paymentStatus == "partialRefunds"] class="checked"[/#if]>${message("Order.PaymentStatus.partialRefunds")}</a>
                        </li>
                        <li>
                            <a href="javascript:;" name="paymentStatus" val="refunded"[#if paymentStatus == "refunded"] class="checked"[/#if]>${message("Order.PaymentStatus.refunded")}</a>
                        </li>
                        <li class="separator">
                            <a href="javascript:;" name="hasExpired" val="true"[#if hasExpired?? && hasExpired] class="checked"[/#if]>${message("admin.order.hasExpired")}</a>
                        </li>
                        <li>
                            <a href="javascript:;" name="hasExpired" val="false"[#if hasExpired?? && !hasExpired] class="checked"[/#if]>${message("admin.order.unexpired")}</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="menuWrap">
                <a href="javascript:;" id="categorySelect" class="button">
                ${message("admin.order.categoryFilter")}<span class="arrow">&nbsp;</span>
                </a>
                <div class="popupMenu">
                    <ul id="categoryOption" class="check">
                        <li >
                            <a href="javascript:;" name="isSpecial" val=""[#if !isSpecial??] class="checked"[/#if]>${message("Order.all")}</a>
                        </li>
                        <li class="separator">
                            <a href="javascript:;" name="isSpecial" val="false"[#if isSpecial?? && !isSpecial] class="checked"[/#if]>任务</a>
                        </li>
                        <li class="separator">
                            <a href="javascript:;" name="isSpecial" val="true"[#if isSpecial?? && isSpecial] class="checked"[/#if]>调机</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="menuWrap">
                <a href="javascript:;" id="pageSizeSelect" class="button">
                ${message("admin.page.pageSize")}<span class="arrow">&nbsp;</span>
                </a>
                <div class="popupMenu">
                    <ul id="pageSizeOption">
                        <li>
                            <a href="javascript:;"[#if page.pageSize == 10] class="current"[/#if] val="10">10</a>
                        </li>
                        <li>
                            <a href="javascript:;"[#if page.pageSize == 20] class="current"[/#if] val="20">20</a>
                        </li>
                        <li>
                            <a href="javascript:;"[#if page.pageSize == 50] class="current"[/#if] val="50">50</a>
                        </li>
                        <li>
                            <a href="javascript:;"[#if page.pageSize == 100] class="current"[/#if] val="100">100</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="menuWrap">
                <span style="height: 23px;margin-left: 4px">${message("Order.sn")}</span>
                <input type="text" id="searchValue" name="searchValue" value="${page.searchValue}" maxlength="200" style="height: 23px"/>
                <input type="submit" class="button"  style="height: 29px" value="${message("admin.common.search")}" />
            </div>
        </div>
    </div>
    <table id="listTable" class="list">
        <tr>
            <th class="check">
                <input type="checkbox" id="selectAll" />
            </th>
            <th>
                <a href="javascript:;" class="sort" name="sn">${message("Order.sn")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="isSpecial">${message("Order.categoryId")}</a>
            </th>
            <th>
                <span>${message("Order.airplane.type")}</span>
            </th>
            <th>
                <span>${message("Order.airplane.regNo")}</span>
            </th>
            <th>
                <a href="javascript:;"  name="member">${message("Order.member.name")}</a>
            </th>
            <th>
                <a href="javascript:;"  name="company">${message("airplane.company")}</a>
            </th>
            <th>
                <a href="javascript:;">${message("Order.Airline")}</a>
            </th>
            <th>
                <a href="javascript:;" name="firstTakeoffTime">${message("Order.firstTakeoffTime")}</a>
            </th>
            <th>
                <a href="javascript:;">${message("Order.passengerNum")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="paymentStatus">${message("Order.total")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="orderStatus">${message("Order.orderStatus")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="paymentStatus">${message("Order.paymentStatus")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="createDate">${message("Order.createDate")}</a>
            </th>
            <th>
                <span>${message("admin.common.handle")}</span>
            </th>
        </tr>
    [#list page.content as order]
        <tr>
            <td>
                <input type="checkbox" name="ids" value="${order.id}" />
            </td>
            <td>
            ${order.sn}
            </td>
            <td>
                [#if order.isSpecial == true]
                    调机
                [#else ]
                    任务
                [/#if]
            </td>
            <td>
            ${order.airplane.typeId.typeName}
            </td>
            <td>
            ${order.airplane.regNo}
            </td>
            <td>
            ${order.customerId.realName}
            </td>
            <td>
            ${order.company.name}
            </td>
            <td>
                [#list order.orderAirlines as orderAirlin]
                    <p  [#if orderAirlin_index > 0]class="separator"[/#if]>${message("OrderAirline.info")}：${orderAirlin.departure} - ${orderAirlin.destination}
                        <br/>
                    ${message("OrderAirline.timeCost")}：
                        [#if orderAirlin.timeCost??]
                            [#if orderAirlin.timeCost?index_of(".")?number > -1]
                                [#if orderAirlin.timeCost?substring(0,orderAirlin.timeCost?index_of("."))?number > 0]
                                ${orderAirlin.timeCost?substring(0,orderAirlin.timeCost?index_of("."))}小时
                                [/#if]
                            ${("0." + orderAirlin.timeCost?substring(orderAirlin.timeCost?index_of(".") + 1))?number*60}分钟
                            [#else]
                            ${orderAirlin.timeCost}小时
                            [/#if]
                        [/#if]
                    </p>
                [/#list]
            </td>
            <td>
                [#list order.orderAirlines as orderAirlin]
                    <p  [#if orderAirlin_index > 0]class="separator"[/#if]>${orderAirlin.takeoffTime?string("yyyy-MM-dd HH:mm")} </p>
                [/#list]
            </td>
            <td>
                [#list order.orderAirlines as orderAirlin]
                    <p  [#if orderAirlin_index > 0]class="separator"[/#if]>${orderAirlin.passengerNum} </p>
                [/#list]
            </td>
            <td>
            ${order.amount?string(',###')}元
            </td>
            <td>
            ${message("Order.OrderStatus." + order.orderStatus)}
                [#if order.expired]<span class="gray">(${message("admin.order.hasExpired")})</span>[/#if]
            </td>
            <td>
            ${message("Order.PaymentStatus." + order.paymentStatus)}
            </td>
            <td>
                <span title="${order.createDate?string("yyyy-MM-dd HH:mm:ss")}">${order.createDate?string("yyyy-MM-dd HH:mm")}</span>
            </td>
            <td>
                <a href="view.jhtml?id=${order.id}">[${message("admin.common.view")}]</a>
                [#if !order.expired && order.orderStatus == "unconfirmed"]
                    <a href="edit.jhtml?id=${order.id}">[${message("admin.common.edit")}]</a>
                [#else]
                    <span title="${message("admin.order.editNotAllowed")}">[${message("admin.common.edit")}]</span>
                [/#if]
            [#--<a href="addPassenger.jhtml?orderId=${order.id}">[${message("admin.common.addPassenger")}]</a>--]
            </td>
        </tr>
    [/#list]
    </table>
[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
    [#include "/admin/include/pagination.ftl"]
[/@pagination]
</form>
</body>
</html>