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
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
    <script type="text/javascript">

        $().ready(function() {

            var $listForm = $("#listForm");
            var $filterSelect = $(".filterSelect");
            var $filterOption = $(".filterOption a");
            var $print = $("select[name='print']");
            var $export = $("#export");
            var $deleteButton = $("#deleteButton");
            var $changeStatus = $(".changeStatus");
            var $searchButton = $("#search");
            var $search_regNo = $("#search_regNo");
            var $search_departureId = $("#search_departureId");
            var $search_destinationId = $("#search_destinationId");
            var $search = $(".searchAirport");

        [@flash_message /]

            // 机型，注册号搜索
            $searchButton.click(function() {
                $("#regNo").val($search_regNo.val());
                $("#departureId").val($search_departureId.attr("var"));
                $("#destinationId").val($search_destinationId.attr("var"));
                $listForm.submit();
                return false;
            });

            $changeStatus.click(function() {
                if(confirm("确定更改状态吗？")){
                    var $this = $(this);
                    var $status = $(this).attr("var");
                    var $id = $(this).attr('planid');
                    $.ajax({
                        url: "changeStatus.jhtml",
                        type: "POST",
                        data:{id:$id,status:$status},
                        dataType: "json",
                        cache: false,
                        success: function(data) {
                            if (data.type == "success") {
                                if($status == "forbidden") {
                                    $this.closest("tr").find('.forbidden').hide();
                                    $this.closest("tr").find('.activate').show();
                                    $this.closest("tr").find('td:first').html('${message("flightPlan.status.userhidden")}');
                                } else {
                                    $this.closest("tr").find('.forbidden').show();
                                    $this.closest("tr").find('.activate').hide();
                                    $this.closest("tr").find('td:first').html('${message("flightPlan.status.usershow")}');
                                }
                            } else {
                                $.message(data.message);
                            }
                        }
                    });
                }
            });

            // 导出
            $export.click(function() {
                if ($deleteButton.hasClass("disabled")) {
                    alert('${message("admin.order.selectOrder")}');
                    return false;
                }
                $listForm.attr("action","export.jhtml");
                $listForm.submit();
            });

            // 筛选
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

            // 打印选择
            $print.change(function() {
                var $this = $(this);
                if ($this.val() != "") {
                    window.open($this.val());
                }
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
                $(this).attr("var", item.id);
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
<form id="listForm" action="specialList.jhtml" method="get">
    <input type="hidden" id="status" name="status" value="${status}" />
    <input type="hidden" id="companyId" name="companyId" value="${companyId}" />
    <input type="hidden" id="airplaneType" name="airplaneType" value="${airplaneType}" />
    <input type="hidden" id="regNo" name="regNo" value="${regNo}" />
    <input type="hidden" id="departureId" name="departureId" value="${departureId}" />
    <input type="hidden" id="destinationId" name="destinationId" value="${destinationId}" />
    <div class="bar">
        <div class="buttonWrap">
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
            <div class="menuWrap">
                <a href="javascript:;" id="filterSelect" class="button filterSelect">
                    航段状态<span class="arrow">&nbsp;</span>
                </a>
                <div class="popupMenu">
                    <ul id="filterOption" class="check filterOption">
                        <li>
                            <a href="javascript:;" name="status" val=""[#if status == ""] class="checked"[/#if]>全部</a>
                        </li>
                        <li>
                            <a href="javascript:;" name="status" val="usershow"[#if status == "usershow"] class="checked"[/#if]>${message("flightPlan.status.usershow")}</a>
                        </li>
                        <li>
                            <a href="javascript:;" name="status" val="userhidden"[#if status == "userhidden"] class="checked"[/#if]>${message("flightPlan.status.userhidden")}</a>
                        </li>
                        <li>
                            <a href="javascript:;" name="status" val="reserve"[#if status == "reserve"] class="checked"[/#if]>${message("flightPlan.status.reserve")}</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="menuWrap">
                <a href="javascript:;" id="methodSelect" class="button filterSelect">
                ${message("airplane.company")}<span class="arrow">&nbsp;</span>
                </a>
                <div class="popupMenu">
                    <ul id="methodOption" class="check filterOption">
                        <li >
                            <a href="javascript:;" name="companyId" val=""[#if companyId== ""] class="checked"[/#if]>${message("Order.all")}</a>
                        </li>
                    [#list companys as company]
                        <li class="separator">
                            <a href="javascript:;" name="companyId" val="${company.id}"[#if company.id == companyId] class="checked"[/#if]>${company.name}</a>
                        </li>
                    [/#list]
                    </ul>
                </div>
            </div>
            <div class="menuWrap">
                <a href="javascript:;" id="methodSelect" class="button filterSelect">
                    飞机型号<span class="arrow">&nbsp;</span>
                </a>
                <div class="popupMenu">
                    <ul id="methodOption" class="check filterOption">
                        <li >
                            <a href="javascript:;" name="airplaneType" val=""[#if airplaneType== ""] class="checked"[/#if]>全部</a>
                        </li>
                    [#list planeTypes as type]
                        <li class="separator">
                            <a href="javascript:;" name="airplaneType" val="${type.typeName}"[#if type.typeName == airplaneType] class="checked"[/#if]>${type.typeName}</a>
                        </li>
                    [/#list]
                    </ul>
                </div>
            </div>
            <div class="menuWrap">
                <span style="height: 23px;margin-left: 4px">${message("Order.airplane.regNo")}</span>
                <input type="text" id="search_regNo" value="${regNo}" maxlength="200" style="height: 23px"/>
                <span style="height: 23px;margin-left: 4px">始发地</span>
                <input class="text searchAirport" type="text" id="search_departureId" var="${departureId}"  value="${departureName}"/>
                <span style="height: 23px;margin-left: 4px">目的地</span>
                <input class="text searchAirport" type="text" id="search_destinationId" var="${destinationId}" value="${destinationName}"/>
                <input type="button" class="button"  id="search" style="height: 29px" value="${message("admin.common.search")}" />
            </div>
        </div>
    </div>
    <table id="listTable" class="list">
        <tr>
            <th>
                <a href="javascript:;" class="sort" name="status">航段状态</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="airplaneType">飞机型号</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="regNo">注册号</a>
            </th>
            <th>
                <span>航空公司</span>
            </th>
            <th>
                <span>始发地</span>
            </th>
            <th>
                <span>目的地</span>
            </th>
            <th>
                <span>原价(元)</span>
            </th>
            <th>
                <span>特价(元)</span>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="takeoffTime">出发时间</a>
            </th>
            <th>
                <span>${message("admin.common.handle")}</span>
            </th>
        </tr>
    [#list page.content as flightPlan]
        <tr>
            <td>
            ${message("flightPlan.status." + flightPlan.status)}
            </td>
            <td>
            ${flightPlan.airplaneType}
            </td>
            <td>
            ${flightPlan.regNo}
            </td>
            <td>
            ${flightPlan.company.name}
            </td>
            <td>
            ${flightPlan.departure}
            </td>
            <td>
            ${flightPlan.destination}
            </td>
            <td>
            ${flightPlan.originalPrice?string(',###')}
            </td>
            <td>
            ${flightPlan.specialprice?string(',###')}
            </td>
            <td>
                <span title="${flightPlan.takeoffTime?string("yyyy-MM-dd HH:mm:ss")}">${flightPlan.takeoffTime?string("yyyy-MM-dd HH:mm")}</span>
            </td>
            <td>
                <a href="edit.jhtml?id=${flightPlan.id}">[${message("admin.common.edit")}]</a>
                <a href=javascript:;" var="forbidden" planid="${flightPlan.id}" class="changeStatus forbidden"  [#if flightPlan.status != "usershow"]style="display:none " [/#if] >[禁用]</a>
                <a href=javascript:;" var="activate"  planid="${flightPlan.id}"  class="changeStatus activate"  [#if flightPlan.status != "userhidden"]style="display:none " [/#if] >[激活]</a>
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