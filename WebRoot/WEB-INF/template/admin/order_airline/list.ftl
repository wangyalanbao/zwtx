<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.airline.list")}</title>


    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
    <script type="text/javascript">
        $().ready(function() {

            var $listForm = $("#listForm");
            var $moreButton = $("#moreButton");
            var $filterSelect = $("#filterSelect");
            var $filterOption = $("#filterOption a");
            var $airlineButton = $("#airlineButton");
            var $airlineFlight = $("#airlineFlight");
            var $cancelFlight = $("#cancelFlight");
            var $submitFlight = $("#submitFlight");
            var $search = $(".search");

        [@flash_message /]


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

            $airlineButton.click(function() {
                $airlineFlight.show();


            });

            $submitFlight.click(function() {

                $("#moreTable :input").each(function() {
                    var $this = $(this);
                    $("#" + $this.attr("name")).val($this.val());
                });
                $listForm.submit();

            });
            // 关闭
            $cancelFlight.click(function() {
                $airlineFlight.hide();
            });
            // 更多选项
            $moreButton.click(function() {
                    $.dialog({
                    title: "订单编码",
            [@compress single_line = true]
                    content: '
            <table id="moreTable" class="moreTable">


            <tr>
            <th>
            订单编码:
                <\/th>
            <td>
            <select name="orderId">
            <option value="">${message("admin.common.choose")}<\/option>
                [#list orderAll as order]
                <option value="${order.id}"[#if order.id == tripOrder] selected="selected"[/#if]>
                ${order.sn}
                <\/option>
                [/#list]
            <\/select>
            <\/td>
            <\/tr>
            <\/table>',
            [/@compress]
                width: 300,
                        modal: true,
                        ok: "${message("admin.dialog.ok")}",
                        cancel: "${message("admin.dialog.cancel")}",
                        onOk: function() {
                    $("#moreTable :input").each(function() {
                        var $this = $(this);
                        $("#" + $this.attr("name")).val($this.val());
                    });
                    $listForm.submit();
                }
            });
        });


        });
    </script>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.airline.list")} <span>(${message("admin.page.total", page.total)})</span>
</div>
<form id="listForm" action="list.jhtml" method="get">
    <input type="hidden" id="brandId" name="brandId" value="${brandId}" />
    <input type="hidden" id="orderId" name="orderId" value="${orderId}" />
[#--<input type="hidden" id="typeId" name="typeId" value="${typeId}" />--]
    <div class="bar">
    [#--<a href="add.jhtml" class="iconButton">--]
    [#--<span class="addIcon">&nbsp;</span>${message("admin.common.add")}--]
    [#--</a>--]
        <div class="buttonWrap">
            <a href="javascript:;" id="deleteButton" class="iconButton disabled">
                <span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
            </a>
            <a href="javascript:;" id="refreshButton" class="iconButton">
                <span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
            </a>

            <a href="javascript:;" id="moreButton" class="button">订单编码</a>

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
        </div>

    </div>
    <table id="listTable" class="list">
        <tr>
            <th class="check">
                <input type="checkbox" id="selectAll" />
            </th>
            <th>
                <a href="javascript:;" class="sort" name="departure">${message("airline.departureId")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="destination">${message("airline.destinationId")}</a>
            </th>
            <th>
                订单编码
            </th>

            <th>
                <span>${message("admin.common.handle")}</span>
            </th>
        </tr>
    [#list page.content as airline]
        <tr>
            <td>
                <input type="checkbox" name="ids" value="${airline.id}" />
            </td>
            <td>
            ${airline.departure}
            </td>
            <td>
            ${airline.destination}
            </td>
            <td>
            ${airline.tripOrder.sn}
            </td>

            <td>
                <a href="addPassenger.jhtml?airlineId=${airline.id}">[${message("admin.common.addPassenger")}]</a>
                <a href="airlineExport.jhtml?airlineId=${airline.id}">[导出乘客信息表]</a>
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