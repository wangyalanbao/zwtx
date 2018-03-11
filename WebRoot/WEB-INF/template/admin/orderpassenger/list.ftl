<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.orderPassenger.list")}</title>


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


            // 是否包机筛选
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
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.orderPassenger.list")} <span>(${message("admin.page.total", page.total)})</span>
</div>
<form id="listForm" action="list.jhtml" method="get">

    <div class="bar">
        <div class="buttonWrap">
            <a href="add.jhtml" class="iconButton">
                <span class="addIcon">&nbsp;</span>${message("admin.common.add")}
            </a>
            <a href="javascript:;" id="deleteButton" class="iconButton disabled">
                <span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
            </a>
            <a href="javascript:;" id="refreshButton" class="iconButton">
                <span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
            </a>
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
                        <a href="javascript:;"[#if page.searchProperty == "idCardNo"] class="current"[/#if] val="idCardNo">${message("customer.identityNo")}</a>
                    </li>
                    <li>
                        <a href="javascript:;"[#if page.searchProperty == "telephone"] class="current"[/#if] val="telephone">${message("customer.telephone")}</a>
                    </li>
                    <li>
                        <a href="javascript:;"[#if page.searchProperty == "name"] class="current"[/#if] val="name">${message("customer.realName")}</a>
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
                <a href="javascript:;" class="sort" name="name">${message("customer.realName")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="pinyin">${message("orderPassenger.pinyin")}</a>
            </th>

            <th>
                <a href="javascript:;" class="sort" name="telephone">${message("customer.telephone")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="idCardNo">${message("customer.identityNo")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="business">${message("customer.business")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="nationality">${message("customer.nationality")}</a>
            </th>
            <th>
                <span>${message("admin.common.handle")}</span>
            </th>
        </tr>
    [#list page.content as passenger]
        <tr>
            <td>
                <input type="checkbox" name="ids" value="${passenger.id}" />
            </td>

            <td>
            ${passenger.name}
            </td>
            <td>
            ${passenger.pinyin}
            </td>
            <td>
            ${passenger.telephone}
            </td>
            <td>
            ${passenger.idCardNo}
            </td>
            <td>
            ${passenger.business}
            </td>
            <td>
            ${passenger.nationality}
            </td>
            <td>
                <a href="edit.jhtml?id=${passenger.id}">[${message("admin.common.edit")}]</a>
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