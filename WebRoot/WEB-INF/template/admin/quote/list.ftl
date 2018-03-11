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
            var $print = $("select[name='print']");
            var $methodSelect = $("#methodSelect");
            var $methodOption = $("#methodOption a");
            var $filterSelect = $("#filterSelect");
            var $filterOption = $("#filterOption a");
            var $addButton = $("#addButton");
            var $addQuote = $("#addQuote");
            var $search = $(".search");
            var $cancleButton = $(".cancleButton");
            var $moreWay = $("#moreWay");
            var $oneWay = $("#oneWay");
            var $returnWay =$("#returnWay");
            var $addFlight = $("#addFlight");
            var $flightList = $("#flightList");
            var $airlineDelete = $(".airlineDelete");
            var $quoteButton = $("#quoteButton");
            var $quoteForm = $("#quoteForm");
            var $quoting = $("#quoting");
            var $addQuoteAirline = $("#addQuoteAirline");
            var $submitQuote = $(".submitQuote");
            var $selectAllQuote =$("#selectAllQuote");
            var $select = $("input[name='select']");
            var $airportChange = $(".airportChange");
            var $selectAllAirplane = $("#selectAllAirplane");
            var $airplaneIds = $("input[name='airplaneIds']");
            var $canncleLine = $(".canncleLine");

        [@flash_message /]

            init();
            function init(){
                if($quoting.val() == "true"){
                    $(".dialogOverlay").show();
                    $addQuoteAirline.show();
                }
            }

            function accMul(arg1,arg2)
            {
                var m=0,s1=arg1.toString(),s2=arg2.toString();
                try{m+=s1.split(".")[1].length}catch(e){}
                try{m+=s2.split(".")[1].length}catch(e){}
                return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
            }

            // 飞机
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

            // 飞机选项
            $methodOption.click(function() {
                var $this = $(this);
                var $dest = $("#airplaneId");
                if ($this.hasClass("checked")) {
                    $dest.val("");
                } else {
                    $dest.val($this.attr("val"));
                }
                $listForm.submit();
                return false;
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
                $(this).closest("td").find('input:first').val(item.id);
                if($returnWay.attr("checked") == "checked"){
                    $("#departureId_1").val($("#destinationId_0").val());
                    $("#departure_1").val($("#destination_0").val());
                    $("#departure_1").attr("readonly","readonly");
                    $("#destinationId_1").val($("#departureId_0").val());
                    $("#destination_1").val($("#departure_0").val());
                    $("#destination_1").attr("readonly","readonly");
                }
                if($moreWay.attr("checked") == "checked"){
                    $("#departureId_1").val($("#destinationId_0").val());
                    $("#departure_1").val($("#destination_0").val());
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
        .hideDiv td {
            border: none !important;
        }

        #addQuote table.list tr:last-child td .airlineDelete{
            display: block !important;
        }

        .hideDiv{
            width: 100%;
            min-height: 300px;
            max-height: 100%;
            overflow-y: scroll;
            background-color: #ffffff;
        }
        #addQuoteAirline input{
            border:none;
        }
        .total input{
            width: 50px;
        }

        .total{
            border-top: 1px solid #C9C3C3;
        }

        .inputSelect{
            border: none;
            -moz-appearance: none;
            -webkit-appearance:none;
            appearance:none;
        }
        #listTable td{
            border-bottom: 1px solid #C9C3C3;
            padding-left: 0px
        }
        .deleteLine{
            background-color: #C9C3C3;
        }
        .deleteLine:hover {
            background-color: #C9C3C3 !important;
        }
    </style>
</head>

<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 报价列表 <span>(${message("admin.page.total", page.total)})</span>
</div>
<form id="listForm" action="list.jhtml" method="get">
    <input type="hidden" id="airplaneId" name="airplaneId" value="${airplaneId}" />
    <input type="hidden" id="status" name="status" value="${status}" />
    <input type="hidden" id="quoting" name="quoting" value="${quoting}" />
    <input type="hidden" id="hasExpired" name="hasExpired" value="[#if hasExpired??]${hasExpired?string("true", "false")}[/#if]" />
    <div class="bar">
        <div class="buttonWrap">
            <a href="add.jhtml" id="addButton" class="iconButton">
                添加报价
            </a>
            <div class="menuWrap">
                <a href="javascript:;" id="methodSelect" class="button">
                ${message("Order.airplane.regNo")}/${message("Order.airplane.type")}<span class="arrow">&nbsp;</span>
                </a>
                <div class="popupMenu">
                    <ul id="methodOption" class="check">
                        <li >
                            <a href="javascript:;" name="airplaneId" val=""[#if airplaneId== ""] class="checked"[/#if]>${message("Order.all")}</a>
                        </li>
                    [#list airplaneList as airplane]
                        <li class="separator">
                            <a href="javascript:;" name="airplaneId" val="${airplane.id}"[#if airplane.id == airplaneId] class="checked"[/#if]>${airplane.regNo}/${airplane.type}</a>
                        </li>
                    [/#list]
                    </ul>
                </div>
            </div>
            <div class="menuWrap">
                <a href="javascript:;" id="filterSelect" class="button">
               确认状态<span class="arrow">&nbsp;</span>
                </a>
                <div class="popupMenu">
                    <ul id="filterOption" class="check">
                        <li>
                            <a href="javascript:;" name="status" val="unconfirmed"[#if status == "unconfirmed"] class="checked"[/#if]>${message("Quote.status.unconfirmed")}</a>
                        </li>
                        <li>
                            <a href="javascript:;" name="status" val="confirming"[#if status == "confirming"] class="checked"[/#if]>${message("Quote.status.confirming")}</a>
                        </li>
                        <li>
                            <a href="javascript:;" name="status" val="confirmed"[#if status == "confirmed"] class="checked"[/#if]>${message("Quote.status.confirmed")}</a>
                        </li>
                        <li>
                            <a href="javascript:;" name="status" val="cancelled"[#if status == "cancelled"] class="checked"[/#if]>${message("Quote.status.cancelled")}</a>
                        </li>

                    </ul>
                </div>
            </div>
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
                <a href="javascript:;" class="sort" name="sn">报价单号</a>
            </th>
            <th>
                <a href="javascript:;"  >${message("Order.airplane.regNo")}/${message("Order.airplane.type")}</a>
            </th>
            <th>
                <a href="javascript:;">${message("Order.Airline")}</a>
            </th>
            <th>
                <a href="javascript:;">类型</a>
            </th>
            <th>
                <a href="javascript:;" name="firstTakeoffTime">${message("Order.firstTakeoffTime")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="paymentStatus">小时/价</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="orderStatus">地面费用</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="paymentStatus">总价</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="createDate">创建时间</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="status">确认状态</a>
            </th>
            <th>
                <span>${message("admin.common.handle")}</span>
            </th>
        </tr>
    [#list page.content as quote]
        <tr>
            <td>
                <input type="checkbox" name="ids" value="${quote.id}" />
            </td>
            <td>
            ${quote.sn}
            </td>
            <td>
            ${quote.regNo}/${quote.type}
            </td>
            <td>
                [#list quote.quoteAirlineList as quoteAirline]
                    <p  [#if quoteAirline_index > 0]class="separator"[/#if]  >
                        [#if quoteAirline.specialType == 'succession' || quoteAirline.specialType == 'interrupted']
                        ${quoteAirline.departure.name} --> ${quoteAirline.center.name} -->  ${quoteAirline.destination.name}
                        [#else ]
                        ${quoteAirline.departure.name} - ${quoteAirline.destination.name}
                        [/#if]
                        &nbsp; &nbsp;
                        [#if quoteAirline.timeCost??]
                            [#if quoteAirline.timeCost?index_of(".")?number > -1]
                                [#if quoteAirline.timeCost?substring(0,quoteAirline.timeCost?index_of("."))?number > 0]
                                ${quoteAirline.timeCost?substring(0,quoteAirline.timeCost?index_of("."))}小时
                                [/#if]
                            ${("0." + quoteAirline.timeCost?substring(quoteAirline.timeCost?index_of(".") + 1))?number*60}分钟
                            [#else]
                            ${quoteAirline.timeCost}小时
                            [/#if]
                        [/#if]
                    </p>
                [/#list]
            </td>
            <td>
                [#list quote.quoteAirlineList  as quoteAirline]
                    <p  [#if quoteAirline_index > 0]class="separator"[/#if]  [#if quoteAirline.isSpecial == true][#else ]style="color: #ff0000" [/#if]>
                        [#if quoteAirline.isSpecial == true]调机[#else ]任务[/#if]
                    </p>
                [/#list]
            </td>
            <td>
                [#list quote.quoteAirlineList as quoteAirline]
                    <p  [#if quoteAirline_index > 0]class="separator"[/#if]  >
                        [#if quoteAirline.isSpecial == true][#else ] ${quoteAirline.takeoffTime?string("yyyy-MM-dd HH:mm")}[/#if]
                    </p>
                [/#list]
            </td>
            <td>
                [#list quote.quoteAirlineList as quoteAirline]
                    <p  [#if quoteAirline_index > 0]class="separator"[/#if]  >${quoteAirline.unitPrice?string(',###')}元 </p>
                [/#list]
            </td>
            <td>
                [#list quote.quoteAirlineList as quoteAirline]
                    <p  [#if quoteAirline_index > 0]class="separator"[/#if]  > ${quoteAirline.maintenanceCost?string(',###')}元</p>
                [/#list]
            </td>
            <td>
            ${quote.totalAmount?string(',###')}元
            </td>
            <td>${quote.createDate?string("yyyy-MM-dd HH:mm")}</td>
            <td>${message("Quote.status."+ quote.status) }</td>
            <td>
                <a href="view.jhtml?id=${quote.id}">[${message("admin.common.view")}]</a>
                [#if quote.status != "cancelled"]
                    <a href="confirmed.jhtml?id=${quote.id}">[运力确认]</a>
                [/#if]
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