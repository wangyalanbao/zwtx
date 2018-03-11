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

            var $selectAll = $("#selectAll");
            var $select = $("input[val='select']");

        [@flash_message /]

            $selectAll.click(function() {
                if($(this).is(':checked') ){
                    $select.attr("checked", "checked");
                }else{
                    $select.attr("checked", false);
                }
            });

        });
    </script>
    <style type="text/css">

        .total{
            border-top: 2px solid #C9C3C3;
        }

    </style>
    <style type="text/css">
        td ul{
            width: 100%;
        }
        ul li{
            float: left;
            width: 30%;
        }
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
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 运力确认
</div>
<form id="listForm" action="capacity.jhtml" method="POST">
    <input type="hidden" name="quoteId" value="${quote.id}"/>
    <input type="hidden" name="id" value="${capacity.id}"/>
    <div id="addQuoteAirline" style="width: 1000px">
        <table class="list" >
            <tr>
                <th class="check">
                    <input type="checkbox" id="selectAll" />
                </th>
                <th width="20%">确认项</th>
                <th>详细内容</th>
            </tr>
            <tr>
                <td>
                    <input type="checkbox" name="base" value="true" [#if capacity.base == true ]checked="checked" [/#if] val="select" >
                </td>
                <td>${message("Capacity.base")}</td>
                <td>
                    <ul>
                        <li>论证类型：运力确认</li>
                        <li>论证级别：${message("Quote.hasExternal." + quote.hasExternal)}</li>
                        <li>飞机型号：${quote.type}</li>
                        <li>指定飞机：${quote.regNo}</li>
                        <li>报价单号：${quote.sn}</li>
                        <li>发布时间：${quote.createDate?string("yyyy-MM-dd HH:mm")}</li>
                        <li>该机型可用运力</li>
                        <li>紧急级别</li>
                        <li>客户</li>
                        <li>客户级别</li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox" name="flightMission" value="true" [#if capacity.flightMission == true ]checked="checked" [/#if] val="select">
                </td>
                <td>${message("Capacity.flightMission")}</td>
                <td>
                [#list quote.quoteAirlineList as quoteAirline]
                    <p  [#if quoteAirline_index > 0]class="separator"[/#if]  >${message("OrderAirline.info")}：
                        [#if quoteAirline.specialType == 'succession' || quoteAirline.specialType == 'interrupted']
                        ${quoteAirline.departure.name} --> ${quoteAirline.center.name} -->  ${quoteAirline.destination.name}
                        [#else ]
                        ${quoteAirline.departure.name} - ${quoteAirline.destination.name}
                        [/#if]
                        &nbsp;&nbsp;
                        [#if quoteAirline.isSpecial == true]调机[#else ]任务[/#if]
                        &nbsp;&nbsp;
                    ${message("OrderAirline.timeCost")}：
                        [#if quoteAirline.timeCost??]
                        ${quoteAirline.timeCost?number*60}分钟
                        [/#if]
                        &nbsp;&nbsp;

                        [#if quoteAirline.isSpecial == true][#else ] ${message("Order.firstTakeoffTime")}：${quoteAirline.takeoffTime?string("yyyy-MM-dd HH:mm")}[/#if]
                    </p>
                [/#list]
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox" name="intelligenceMat" value="true" [#if capacity.intelligenceMat == true ]checked="checked" [/#if] val="select">
                </td>
                <td>${message("Capacity.intelligenceMat")}</td>
                <td>
                    <ul>
                        <li>新开机场</li>
                        <li>特殊机场</li>
                        <li>机场限制</li>
                        <li>航行资料</li>
                        <li>特殊运行限制</li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox" name="machineMat" value="true" [#if capacity.machineMat == true ]checked="checked" [/#if] val="select">
                </td>
                <td>${message("Capacity.machineMat")}</td>
                <td>
                    <ul>
                        <li>机组实力和资源</li>
                        <li>执勤期</li>
                        <li>飞行时间确认</li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox" name="flightAttendantMat" value="true" [#if capacity.flightAttendantMat == true ]checked="checked" [/#if] val="select">
                </td>
                <td>${message("Capacity.flightAttendantMat")}</td>
                <td>
                    <ul>
                        <li>乘务实力和资源</li>
                        <li>执勤期</li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox" name="MCC" value="true" [#if capacity.MCC == true ]checked="checked" [/#if] val="select">
                </td>
                <td>${message("Capacity.MCC")}</td>
                <td>
                    <ul>
                        <li>飞行适航情况</li>
                        <li>跟机机务资源</li>
                        <li>机场除防冰能力</li>
                        <li>地形数据库</li>
                        <li>导航数据库</li>
                        <li>电子航图</li>
                        <li>维修运行限制条件</li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox" name="serviceGuaranteeMat" value="true" [#if capacity.serviceGuaranteeMat == true ]checked="checked" [/#if] val="select">
                </td>
                <td>${message("Capacity.serviceGuaranteeMat")}</td>
                <td>
                    <ul>
                        <li>机场保障能力</li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox" name="workingCapitalMat" value="true" [#if capacity.workingCapitalMat == true ]checked="checked" [/#if] val="select">
                </td>
                <td>${message("Capacity.workingCapitalMat")}</td>
                <td>
                    <ul>
                        <li>国内计划申请时限</li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox" name="securityManagementCenter" value="true" [#if capacity.securityManagementCenter == true ]checked="checked" [/#if] val="select">
                </td>
                <td>${message("Capacity.securityManagementCenter")}</td>
                <td>
                    <ul>
                        <li>安保情况评估</li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox" name="catering" value="true" [#if capacity.catering == true ]checked="checked" [/#if] val="select">
                </td>
                <td>${message("Capacity.catering")}</td>
                <td>
                    <ul>
                        <li>完备</li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox" name="historicalArgument" value="true" [#if capacity.historicalArgument == true ]checked="checked" [/#if] val="select">
                </td>
                <td>${message("Capacity.historicalArgument")}</td>
                <td>
                    <ul>
                        <li>历史航线论证单</li>
                    </ul>
                </td>
            </tr>
            [#if quote.hasExternal == true]
                <tr>
                    <td>
                        <input type="checkbox" name="foreignSupervisor" value="true" [#if capacity.foreignSupervisor == true ]checked="checked" [/#if] val="select">
                    </td>
                    <td>${message("Capacity.foreignSupervisor")}</td>
                    <td>
                        <ul>
                            <li>机组签证办理时限</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="checkbox" name="operationControl" value="true" [#if capacity.operationControl == true ]checked="checked" [/#if] val="select">
                    </td>
                    <td>${message("Capacity.operationControl")}</td>
                    <td>
                        <ul>
                            <li>国际机场三关服务时间</li>
                            <li>机场加油能力</li>
                            <li>国际许可申请时限</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="checkbox" name="financeDepartment" value="true" [#if capacity.financeDepartment == true ]checked="checked" [/#if] val="select">
                    </td>
                    <td>${message("Capacity.financeDepartment")}</td>
                    <td>
                        <ul>
                            <li>飞机保险覆盖区域</li>
                        </ul>
                    </td>
                </tr>
            [/#if]
            <tr>
                <td>

                </td>
                <td>
                    <input type="submit" class="button" value="${message("admin.common.submit")}" />
                    <input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" /></td>
                </td>
                <td>
                    <input type="button" class="button" value="撤销报价"  onclick="if(confirm('撤消后状态将不可修改！')){location.href='cancelled.jhtml?quoteId=${quote.id}'}" /></td>
                </td>
            </tr>
            <tr>
                <td>

                </td>
                <td></td>
                <td></td>
            </tr>
        </table>

    </div>
</form>
</body>

</html>