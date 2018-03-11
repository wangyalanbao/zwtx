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

        [@flash_message /]


        });
    </script>
    <style type="text/css">

        .total{
            border-top: 2px solid #C9C3C3;
        }


    </style>
</head>

<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 查看报价
</div>
<form id="listForm" action="list.jhtml" method="get">
</form>
<div id="addQuoteAirline" style="width: 1250px">
    <table class="list" >
        <tr>
            <th width="50%"> 航段信息    &nbsp;&nbsp;&nbsp;飞机：${quote.airplane.brand}（${quote.airplane.regNo}/${quote.airplane.type}）</th>
            <th>类型</th>
            <th>起飞时间</th>
            <th>飞行时长</th>
            <th>小时/元</th>
            <th>地面费用</th>
        </tr>
    [#list quote.quoteAirlineList as quoteAirline]
        <tr>
            <td>
                [#if quoteAirline.specialType == 'succession' || quoteAirline.specialType == 'interrupted']
                ${quoteAirline.departure.name}
                    -->
                ${quoteAirline.center.name}
                    -->
                ${quoteAirline.destination.name}
                [#else]
                ${quoteAirline.departure.name}
                    -->
                ${quoteAirline.destination.name}

                [/#if]
            </td>
            <td>[#if quoteAirline.isSpecial == true]调机[#else ]任务[/#if]</td>
            <td>
                [#if quoteAirline.isSpecial == true][#else ] ${quoteAirline.takeoffTime?string("yyyy-MM-dd HH:mm")}[/#if]
            </td>
            <td>
            ${quoteAirline.timeCost}小时
            </td>
            <td>
            ${quoteAirline.unitPrice?string(',###')}元
            </td>
            <td>
            ${quoteAirline.maintenanceCost?string(',###')}元
            </td>
        </tr>
    [/#list]
        <tr class="total">
            <td>
                总价：${quote.totalAmount?string(',###')}元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                实际消费小时数：${quote.actualHours}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                补齐小时数：${quote.lackHour}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                调正天数：${quote.stopDays}</td>
            <td></td>
            <td></td>
            <td>总飞行时长：${quote.totalHour}</td>
            <td>最低消费小时数:${quote.lowestHour}</td>
            <td>
                总地面费用：${quote.totalMaintenance?string(',###')}元
            </td>
        </tr>
        <tr><td> <input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" /></td>
            <td></td> <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
    </table>

</div>

</body>

</html>