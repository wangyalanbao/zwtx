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
                    title: "飞机机型",
            [@compress single_line = true]
                    content: '
            <table id="moreTable" class="moreTable">


            <tr>
            <th>
            ${message("airline.typeId")}:
                <\/th>
            <td>
            <select name="typeId">
            <option value="">${message("admin.common.choose")}<\/option>
                [#list planeType as type]
                <option value="${type.id}"[#if type.id == typeId] selected="selected"[/#if]>
                ${type.typeName}
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


        // 机场选择
        $search.autocomplete("${base}/admin/airport/search_airport.jhtml", {
            dataType: "json",
            max: 20,
            width: 190,
            scrollHeight: 430,
            minChars:0,
            parse: function(data) {
                return $.map(data, function(item) {
                    return {
                        data: item,
                        value: item.name
                    }
                });
            },
            formatItem: function(item) {
                if ($.inArray(item.name, name) < 0) {
                    return '<h4 title="' + item.name + '">' + item.name + '<\/h4>'
                            + '<span style="color: #999999">'  +'四字码：'+ '<\/span>' + item.code_4  + '<\/br>'
                            + '<span style="color: #999999">'  +'三字码：'+ '<\/span>' + item.code_3  + '<\/br>'
                            ;
                } else {
                    return false;
                }
            }
        }).result(function(event, item) {
            $(this).val(item.name);
            $(this).closest("td").find('input:first').val(item.id);

        });


        });
    </script>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.airline.list")} <span>(${message("admin.page.total", page.total)})</span>
</div>
<form id="listForm" action="list.jhtml" method="get">
    <input type="hidden" id="month" name="month" value="${month}" />
    <input type="hidden" id="brandId" name="brandId" value="${brandId}" />
    <input type="hidden" id="typeId" name="typeId" value="${typeId}" />
    <div class="bar">
        <a href="add.jhtml" class="iconButton">
            <span class="addIcon">&nbsp;</span>${message("admin.common.add")}
        </a>
        <div class="buttonWrap">
            <a href="javascript:;" id="deleteButton" class="iconButton disabled">
                <span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
            </a>
            <a href="javascript:;" id="refreshButton" class="iconButton">
                <span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
            </a>

            <a href="javascript:;" id="moreButton" class="button">飞机机型</a>
            <a href="javascript:;" id="airlineButton" class="button">机场名称</a>

            <div class="menuWrap">
                <a href="javascript:;" id="filterSelect" class="button">
                    月份<span class="arrow">&nbsp;</span>
                </a>
                <div class="popupMenu">
                    <ul id="filterOption" class="check">
                        <li >
                            <a href="javascript:;" name="month" val=""[#if !month?? ] class="checked"[/#if]>全部</a>
                        </li>
                        <li >
                            <a href="javascript:;" name="month" val="1"[#if month?? && month=1] class="checked"[/#if]>一月</a>
                        </li>
                        <li >
                            <a href="javascript:;" name="month" val="2"[#if month?? && month=2] class="checked"[/#if]>二月</a>
                        </li>
                        <li >
                            <a href="javascript:;" name="month" val="3"[#if month?? && month=3] class="checked"[/#if]>三份</a>
                        </li>
                        <li >
                            <a href="javascript:;" name="month" val="4"[#if month?? && month=4] class="checked"[/#if]>四月</a>
                        </li>
                        <li >
                            <a href="javascript:;" name="month" val="5"[#if month?? && month=5] class="checked"[/#if]>五月</a>
                        </li>
                        <li >
                            <a href="javascript:;" name="month" val="6"[#if month?? && month=6] class="checked"[/#if]>六月</a>
                        </li>
                        <li >
                            <a href="javascript:;" name="month" val="7"[#if month?? && month=7] class="checked"[/#if]>七月</a>
                        </li>
                        <li >
                            <a href="javascript:;" name="month" val="8"[#if month?? && month=8] class="checked"[/#if]>八月</a>
                        </li>
                        <li >
                            <a href="javascript:;" name="month" val="9"[#if month?? && month=9] class="checked"[/#if]>九月</a>
                        </li>
                        <li >
                            <a href="javascript:;" name="month" val="10"[#if month?? && month=10] class="checked"[/#if]>十月</a>
                        </li>
                        <li >
                            <a href="javascript:;" name="month" val="11"[#if month?? && month=11] class="checked"[/#if]>十一月</a>
                        </li>
                        <li >
                            <a href="javascript:;" name="month" val="12"[#if month?? && month=12] class="checked"[/#if]>十二月</a>
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
        </div>

    </div>
    <table id="listTable" class="list">
        <tr>
            <th class="check">
                <input type="checkbox" id="selectAll" />
            </th>
            <th>
                <a href="javascript:;" class="sort" name="departureId">${message("airline.departureId")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="destinationId">${message("airline.destinationId")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="timeCost">${message("airline.timeCost")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="typeId">${message("airline.typeId")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="month">${message("airline.month")}
            </th>
            <th>
            ${message("airline.isVirtual")}
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
            ${airline.departureId.name}
            </td>
            <td>
            ${airline.destinationId.name}
            </td>
            <td>
            ${airline.timeCost}
            </td>
            <td>
            ${airline.typeId.typeName}
            </td>
            <td>
            ${airline.month}
            </td>
            <td>
                [#if airline.isVirtual == "true"]
                    <span class="green">是</span>
                [#else]
                    <span class="green">否</span>
                [/#if]
            </td>
            <td>
                <a href="edit.jhtml?id=${airline.id}">[${message("admin.common.edit")}]</a>
            </td>
        </tr>
    [/#list]
    </table>
[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
    [#include "/admin/include/pagination.ftl"]
[/@pagination]
    <div id="airlineFlight" style="display: none; position: fixed;background-color: #ffffff;top:30%;left: 35%;border:2px solid #ddd; z-index: 100;">
        <h2 style="text-align:center">机场名称</h2>
        <form id="airlineForm" >

            <div style="height: 120px; overflow-x: hidden; overflow-y: auto;">
                <table class="input" >

                    <tr>
                        <th>
                        ${message("airline.departureId")}
                        </th>
                        <td>
                            <input class="text addInput" type="hidden" id="departureId"  name="departureId" value="${departureId}"/>
                            <input class="text search addInput" type="text" id="departureAdd"  name="departureAdd" val='addInput' />

                        </td>
                    </tr>

                    <tr>
                        <th>
                        ${message("airline.destinationId")}
                        </th>
                        <td>
                            <input class="text addInput" type="hidden" id="destinationId"  name="destinationId" value="${destinationId}" />
                            <input class="text search addInput" type="text" id="destinationAdd"  name="destinationAdd"  val="addInput"/>

                        </td>
                    </tr>
                    <tr>
                        <th>
                            &nbsp;
                        </th>
                        <td>
                            <input type="button" class="button" id="submitFlight" value="${message("admin.common.submit")}" />
                            <input type="button" class="button" id="cancelFlight" value="${message("admin.common.back")}" />
                        </td>
                    </tr>
                </table>

            </div>
        </form>
    </div>
</form>
</body>
</html>