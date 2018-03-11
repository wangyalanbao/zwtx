<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.main.memberChild")}</title>


    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
    <script type="text/javascript">
        $().ready(function() {

            var $delete = $("#listTable a.delete");
            var $detail = $(".detail");

        [@flash_message /]

            // 删除
            $delete.click(function() {
                var $this = $(this);
                $.dialog({
                    type: "warn",
                    content: "${message("admin.dialog.deleteConfirm")}",
                    onOk: function() {
                        $.ajax({
                            url: "delete.jhtml",
                            type: "POST",
                            data: {id: $this.attr("val")},
                            dataType: "json",
                            cache: false,
                            success: function(message) {
                                $.message(message);
                                if (message.type == "success") {
                                    $this.closest("tr").remove();
                                }
                            }
                        });
                    }
                });
                return false;
            });

            // 更多选项
            $detail.click(function() {
                $.dialog({
                    title: "${message("admin.push.detail")}",
                    content:$(this).attr("val"),
                    width: 300,
                    ok: "${message("admin.dialog.ok")}",
                    onOk: function() {
                        return;
                    }
                });
            });
        });
    </script>
    <style type="text/css">
        .dialogBottom input:nth-child(2){
            display: none;
        }
        a input{
            background: url(../images/common.gif) 0px 0px repeat-x;
            border: 1px solid #b7c8d9;
        }
        .dialogContent{
            padding: 10px 5px;
            line-height: 20px;
        }
    </style>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.push.list")} <span>(${message("admin.page.total", page.total)})</span>
    &nbsp;&nbsp;
</div>
<form id="listForm" action="list.jhtml?mId=${mId}" method="get">
    <input type="hidden" id="mId" name="mId" value="${mId}" />
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

    </div>
    <table id="listTable" class="list">
        <tr>
            <th class="check">
                <input type="checkbox" id="selectAll" />
            </th>
            <th>
                <a href="javascript:;" class="sort" name="createDate">${message("admin.common.createDate")}</a>
            </th>
            <th>
                <span>${message("admin.push.content")}</span>
            </th>
            <th>
                <span>${message("admin.push.type")}</span>
            </th>
            <th>
                <span>${message("admin.push.way")}</span>
            </th>
            <th>
                <span>${message("admin.push.user")}</span>
            </th>
            <th>
                <span>${message("admin.common.handle")}</span>
            </th>
        </tr>
    [#list page.content as push]
        <tr>
            <td>
                <input type="checkbox" name="ids" value="${push.id}" />
            </td>
            <td>
            ${push.createDate}
            </td>
            <td>
            ${abbreviate(push.content, 100, "...")}
            </td>
            <td>
            ${push.type}
            </td>
            <td>
            ${push.way}
            </td>
            <td>
            ${push.user}
            </td>
            <td>
                <a href="javascript:;" class="detail" val="${push.content}">${message("admin.push.detail")}</a>
            </td>
        </tr>
    [/#list]
    </table>
[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
    [#include "/admin/include/pagination.ftl"]
[/@pagination]
</form>
<div class="content">

</div>
</body>

</html>