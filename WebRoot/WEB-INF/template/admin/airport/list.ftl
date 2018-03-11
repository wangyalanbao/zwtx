<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.airport.list")}</title>


    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
    <script type="text/javascript">
        $().ready(function() {

            var $listForm = $("#listForm");
            var $filterSelect = $("#filterSelect");
            var $filterOption = $("#filterOption a");
        [@flash_message /]

            // 基地筛选
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

        });
    </script>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.airport.list")} <span>(${message("admin.page.total", page.total)})</span>
</div>
<form id="listForm" action="list.jhtml" method="get">
    <input type="hidden" id="isVirtual" name="isVirtual" value="[#if isVirtual??]${isVirtual?string("true", "false")}[/#if]" />
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
            <div class="menuWrap">
                <a href="javascript:;" id="filterSelect" class="button">
                ${message("airport.virtual")}<span class="arrow">&nbsp;</span>
                </a>
                <div class="popupMenu">
                    <ul id="filterOption" class="check">
                        <li>
                            <a href="javascript:;" name="isVirtual" val="true"[#if isVirtual?? && isVirtual] class="checked"[/#if]>${message("airport.isShow")}</a>
                        </li>
                        <li>
                            <a href="javascript:;" name="isVirtual" val="false"[#if isVirtual?? && !isVirtual] class="checked"[/#if]>${message("airport.noisShow")}</a>
                        </li>
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
        <div class="menuWrap">
            <div class="search">
                <span id="searchPropertySelect" class="arrow">&nbsp;</span>
                <input type="text" id="searchValue" name="searchValue" value="${page.searchValue}" maxlength="200" />
                <button type="submit">&nbsp;</button>
            </div>
            <div class="popupMenu">
                <ul id="searchPropertyOption">
                    <li>
                        <a href="javascript:;"[#if page.searchProperty == "name"] class="current"[/#if] val="name">${message("airport.name")}</a>
                    </li>
                    <li>
                        <a href="javascript:;"[#if page.searchProperty == "code_4"] class="current"[/#if] val="code_4">${message("airport.code_4")}</a>
                    </li>
                    <li>
                        <a href="javascript:;"[#if page.searchProperty == "code_3"] class="current"[/#if] val="code_3">${message("airport.code_3")}</a>
                    </li>
                    <li>
                        <a href="javascript:;"[#if page.searchProperty == "baseAirport"] class="current"[/#if] val="baseAirport">${message("airport.baseAirport")}</a>
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
                <a href="javascript:;" class="sort" name="code_4">${message("airport.code_4")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="code_3">${message("airport.code_3")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="name">${message("airport.name")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="baseAirport">${message("airport.baseAirport")}</a>
            </th>
            <th>
            ${message("airport.virtual")}
            </th>
            <th>
            ${message("airport.city")}
            </th>
            <th>
            ${message("airport.cityEnglish")}
            </th>
            <th>
            ${message("airport.area")}
            </th>
            <th>
            ${message("airport.areaEnglish")}
            </th>
            <th>
            ${message("airport.longitudes")}
            </th>
            <th>
            ${message("airport.magVariation")}
            </th>
            <th>
            ${message("airport.meanSeaLevel")}
            </th>
            <th>
                <span>${message("admin.common.handle")}</span>
            </th>
        </tr>
    [#list page.content as airport]
        <tr>
            <td>
                <input type="checkbox" name="ids" value="${airport.id}" />
            </td>
            <td>
            ${airport.code_4}
            </td>
            <td>
            ${airport.code_3}
            </td>
            <td>
            ${airport.name}
            </td>
            <td>
                [#if airport.isVirtual == "true"]
                    --
                [#else]
                ${airport.name}
                [/#if]
            </td>
            <td>
                [#if airport.isVirtual == "true"]
                    <span class="green">是</span>
                [#else]
                    <span class="green">否</span>
                [/#if]
            </td>
            <td>
            ${airport.cityEnglish}
            </td>
            <td>
            ${airport.city}
            </td>
            <td>
            ${airport.areaEnglish}
            </td>
            <td>
            ${airport.area}
            </td>
            <td>
                经度 ${airport.longitude}
                纬度${airport.latitude}
            </td>
            <td>
            ${airport.magVariation}
            </td>
            <td>
            ${airport.meanSeaLevel}
            </td>

            <td>
                <a href="edit.jhtml?id=${airport.id}">[${message("admin.common.edit")}]</a>
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