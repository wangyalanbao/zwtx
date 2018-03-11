<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.airplane.list")}</title>


    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
    <script type="text/javascript">
        $().ready(function() {

            var $moreButton = $("#moreButton");
            var $airportButton = $("#airportButton");
            var $airportFlight = $("#airportFlight");
            var $cancelFlight = $("#cancelFlight");
            var $submitFlight = $("#submitFlight");

            var $listForm = $("#listForm");
            var $search = $(".search");
        [@flash_message /]


            $airportButton.click(function() {
                $airportFlight.show();

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
                $airportFlight.hide();
            });

            $moreButton.click(function() {
                    $.dialog({
                    title: "${message("admin.product.moreOption")}",
            [@compress single_line = true]
                    content: '
            <table id="moreTable" class="moreTable">
            <tr>
            <th>
            ${message("airplane.brand")}:
                <\/th>
            <td>
            <select name="planeBrandId">
            <option value="">${message("admin.common.choose")}<\/option>
                [#list planeBrands as planeBrand]
                <option value="${planeBrand.id}"[#if planeBrand.id == planeBrandId] selected="selected"[/#if]>

                ${planeBrand.name}
                <\/option>
                [/#list]
            <\/select>

            <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("airplane.type")}:
                <\/th>
            <td>
            <select name="typeId">
            <option value="">${message("admin.common.choose")}<\/option>
                [#list planeTypes as type]
                <option value="${type.id}"[#if type.id == typeId] selected="selected"[/#if]>
                ${type.typeName}
                <\/option>
                [/#list]
            <\/select>
            <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("airplane.company")}:
                <\/th>
            <td>
            <select name="companyId">
            <option value="">${message("admin.common.choose")}<\/option>
                [#list companys as company]
                <option value="${company.id}"[#if company.id == companyId] selected="selected"[/#if]>
                ${company.name}
                <\/option>
                [/#list]
            <\/select>
            <\/td>
            <\/tr>

            <\/table>',
            [/@compress]
                width: 470,
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
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.airplane.list")} <span>(${message("admin.page.total", page.total)})</span>
</div>
<form id="listForm" action="list.jhtml" method="get">
    <input type="hidden" id="departureId" name="departureId" value="${departureId}" />
    <input type="hidden" id="planeBrandId" name="planeBrandId" value="${brandId}" />
    <input type="hidden" id="typeId" name="typeId" value="${typeId}" />
    <input type="hidden" id="companyId" name="companyId" value="${companyId}" />
[#--<input type="hidden" id="airportId" name="airportId" value="${airportId}" />--]
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
            <a href="javascript:;" id="moreButton" class="button">${message("admin.product.moreOption")}</a>
            <a href="javascript:;" id="airportButton" class="button">机场名称</a>

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
        <div class="menuWrap">
            <div class="search">
                <span id="searchPropertySelect" class="arrow">&nbsp;</span>
                <input type="text" id="searchValue" name="searchValue" value="${page.searchValue}" maxlength="200" />
                <button type="submit">&nbsp;</button>
            </div>
            <div class="popupMenu">
                <ul id="searchPropertyOption">
                    <li>
                        <a href="javascript:;"[#if page.searchProperty == "regNo"] class="current"[/#if] val="regNo">注册号</a>
                    </li>
                    <li>
                        <a href="javascript:;"[#if page.searchProperty == "manuDate"] class="current"[/#if] val="manuDate">出厂年份</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <table id="listTable" class="list">
        <tr>
            <th class="check">
                <input type="checkbox" id="selectAll" />
            </th>
            <th>
            ${message("airplane.brand")}
            </th>
            <th>
            ${message("airplane.type")}
            </th>
            <th>
                <a href="javascript:;" class="sort" name="regNo">${message("airplane.regNo")}</a>
            </th>
            <th>
            ${message("airplane.airportId")}
            </th>
            <th>
            ${message("airplane.company")}
            </th>
            <th>
                <a href="javascript:;" class="sort" name="voyage">${message("airplane.voyage")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="capacity">${message("airplane.capacity")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="manuDate">${message("airplane.manuDate")}</a>
            </th>

            <th>
                <span>${message("admin.common.handle")}</span>
            </th>
        </tr>
    [#list page.content as airplane]
        <tr>
            <td>
                <input type="checkbox" name="ids" value="${airplane.id}" />
            </td>
            <td>
            ${airplane.brandId.name}
            </td>
            <td>
            ${airplane.typeId.typeName}
            </td>
            <td>
            ${airplane.regNo}
            </td>
            <td>
            ${airplane.airportId.name}
            </td>
            <td>
            ${airplane.company.name}
            </td>
            <td>
            ${airplane.voyage}
            </td>
            <td>
            ${airplane.capacity}
            </td>
            <td>
                [#if airplane.manuDate?has_content]
                ${airplane.manuDate}
                [#else]
                    <span class="red">未设置</span>
                [/#if]
            </td>
            <td>
                <a href="edit.jhtml?id=${airplane.id}">[${message("admin.common.edit")}]</a>
                <a href="view.jhtml?id=${airplane.id}">[${message("admin.common.view")}]</a>
            </td>
        </tr>
    [/#list]
    </table>
[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
    [#include "/admin/include/pagination.ftl"]
[/@pagination]

    <div id="airportFlight" style="display: none; position: fixed;background-color: #ffffff;top:15%;left: 35%;border:2px solid #ddd; z-index: 100;">
        <h2 style="text-align:center">机场名称</h2>
        <form id="fulfillForm" >

            <div style="height: 80px; overflow-x: hidden; overflow-y: auto;">
                <table class="input" >
                    <tr>
                        <th>
                        ${message("flightPlan.destinationI")}
                        </th>
                        <td>

                            <input class="text addInput" type="hidden" id="airportI"  name="airportI" value=""/>
                            <input class="text search addInput" type="text" id="departureAdd"  name="departureAdd" val='addInput' />

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