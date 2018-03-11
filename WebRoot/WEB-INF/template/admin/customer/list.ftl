<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.customer.list")}</title>


    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
    <script type="text/javascript">
        $().ready(function() {

            var $news = $("#listTable a.news");
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

            //发送消息
            $news.click(function() {
                var $customeId = $(this).attr("val");
                    $.dialog({
                    title: "发送消息",
            [@compress single_line = true]
                    content: '
            <table id="moreTable" class="moreTable">

            <tr>
            <td>
            <textarea name="content" id="content" class="text"><\/textarea>
            <\/td>
            <\/tr>

            <\/table>',
            [/@compress]
                width: 470,
                        modal: true,
                        ok: "${message("admin.dialog.ok")}",
                        cancel: "${message("admin.dialog.cancel")}",
                        onOk: function() {
                    var $content = $("#content").val();
                    if($content == "" || $content == undefined){
                        alert("内容不能为空");
                        return false;
                    }else{
                        $.ajax({
                            url: "send.jhtml",
                            type: "GET",
                            data: {content:$content,customeId:$customeId},
                            dataType: "json",

                        });
                        alert("发送成功");
                    }

                }
            });
        });


        });
    </script>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.customer.list")} <span>(${message("admin.page.total", page.total)})</span>
</div>
<form id="listForm" action="list.jhtml" method="get">
    <input type="hidden" id="charted" name="charted" value="[#if charted??]${charted?string("true", "false")}[/#if]" />

    <div class="bar">
        <div class="buttonWrap">
            <a href="javascript:;" id="deleteButton" class="iconButton disabled">
                <span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
            </a>
            <a href="javascript:;" id="refreshButton" class="iconButton">
                <span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
            </a>
            <div class="menuWrap">
                <a href="javascript:;" id="filterSelect" class="button">
                ${message("customer.ischarted")}<span class="arrow">&nbsp;</span>
                </a>
                <div class="popupMenu">
                    <ul id="filterOption" class="check">
                        <li>
                            <a href="javascript:;" name="charted" val="true"[#if charted?? && charted] class="checked"[/#if]>${message("customer.yCharted")}</a>
                        </li>
                        <li>
                            <a href="javascript:;" name="charted" val="false"[#if charted?? && !charted] class="checked"[/#if]>${message("customer.noCharted")}</a>
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
                        <a href="javascript:;"[#if page.searchProperty == "telephone"] class="current"[/#if] val="telephone">${message("customer.telephone")}</a>
                    </li>
                    <li>
                        <a href="javascript:;"[#if page.searchProperty == "realName"] class="current"[/#if] val="realName">${message("customer.realName")}</a>
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
                <a href="javascript:;" class="sort" name="charted">${message("customer.charted")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="realName">${message("customer.realName")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="sex">${message("customer.sex")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="telephone">${message("customer.telephone")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="companys">${message("customer.companys")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="email">${message("customer.email")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="identityType">${message("customer.identityType")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="identityNo">${message("customer.identityNo")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="identityExpiryEnd">${message("customer.identityExpiryEnd")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="birthDate">${message("customer.birthDate")}</a>
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
    [#list page.content as customer]
        <tr>
            <td>
                <input type="checkbox" name="ids" value="${customer.id}" />
            </td>
            <td>
                [#if !customer.charted]
                    <span class="green">${message("customer.noCharted")}</span>
                [#else]
                    <span class="green">${message("customer.yCharted")}</span>
                [/#if]
            </td>
            </td>
            <td>
            ${customer.realName}
            </td>
            <td>
                [#if customer.sex?has_content]
                ${message("customer.sex." + customer.sex)}
                [#else ]
                    <span ></span>
                [/#if]
            </td>
            <td>
            ${customer.telephone}
            </td>
            <td>
            ${customer.companys}
            </td>
            <td>
            ${customer.email}
            </td>
            <td>
                [#if customer.identityType?has_content]
                ${message("customer.identityType." + customer.identityType)}
                [#else ]
                    <span ></span>
                [/#if]
            </td>
            <td>
            ${customer.identityNo}
            </td>
            <td>
                [#if customer.identityExpiryEnd?has_content]
                    <span >${customer.identityExpiryEnd?string("yyyy-MM-dd")}</span>
                [#else ]
                    <span ></span>
                [/#if]
            </td>
            <td>
                [#if customer.birthDate?has_content]
                    <span >${customer.birthDate?string("yyyy-MM-dd")}</span>
                [#else ]
                    <span ></span>
                [/#if]
            </td>
            <td>
            ${customer.business}
            </td>
            <td>
            ${customer.nationality}
            </td>
            <td>
                <a href="edit.jhtml?id=${customer.id}">[${message("admin.common.edit")}]</a>
                <a href="#" id="news" class="news" val="${customer.id}">[${message("customer.news")}]</a>
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