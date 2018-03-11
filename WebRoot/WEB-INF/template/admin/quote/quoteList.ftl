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

            // 删除或增加前后调机
            $canncleLine.click(function() {
                var $this = $(this);
                var $out = $this.attr("out");
                var $in = $this.attr("in");

                // 原飞行时长
                var $oldTimeCost = $("#timeCost_" + $out + "_" + $in).val();
                // 原地面费用
                var $oldMaintenanceCost =  $("#maintenanceCost_" + $out + "_" + $in).val();
                // 原飞行费用
                var $oldFlyingCost = $("#flyingCost_" + $out + "_" + $in).val();

                // 飞机载客单价
                var $loadedPrice = $("#loadedPrice_" + $out ).val();
                // 原总飞行时长
                var $totalTimeCost = $("#totalHour_" + $out ).val();
                // 原补齐小时数
                var $lackHour = $("#lackHour_" + $out ).val();
                // 最低小时消费
                var $lowestHour = $("#lowestHour_" + $out ).val();
                // 实际消费小时数
                var $actualHours = $("#actualHours_" + $out ).val();
                // 总价
                var $totalAmount = $("#totalAmount_" + $out ).val();
                // 总地面费用
                var $totalMaintenance = $("#totalMaintenance_" + $out ).val();

                if($this.is(":checked")){
                    $this.closest("tr").removeClass("deleteLine");
                    var $type = $this.closest("tr").attr("type");
                    if($type == "before"){
                        $this.closest("tr").find("select:first").attr("disabled", false);
                    } else if($type == "after") {
                        $this.closest("tr").find("select:eq(1)").attr("disabled", false);
                    }

                    $("#isDelete_" + $out + "_" + $in).val(false);
                    //新总飞行时长
                    var $newTotalTime = parseFloat($totalTimeCost) + parseFloat($oldTimeCost);
                    $("#totalHour_" + $out).val(parseFloat($newTotalTime).toFixed(2));
                    // 新补齐小时数
                    var $newLackHour = 0;
                    if(parseFloat($newTotalTime) < parseFloat($lowestHour)){
                        // 新补齐小时数
                        $newLackHour = parseFloat($lowestHour) - parseFloat($newTotalTime);
                    } else {
                        $("#lackHour_" + $out ).val(0);
                    }
                    $("#lackHour_" + $out ).val(parseFloat($newLackHour).toFixed(2));

                    // 新实际消费小时数
                    var $newActualHours = parseFloat($newTotalTime) + parseFloat($newLackHour);
                    $("#actualHours_" + $out ).val(parseFloat($newActualHours).toFixed(2));

                    // 新总地面费用
                    var $newTotalMaintenance = parseFloat($totalMaintenance) + parseFloat($oldMaintenanceCost);
                    $("#totalMaintenance_" + $out ).find("option:first").val(parseFloat($newTotalMaintenance).toFixed(0));
                    $("#totalMaintenance_" + $out).find("option:first").text("总地面费用：" + fmoney(parseFloat($newTotalMaintenance).toFixed(0)) + "元");

                    // 新总价
                    var $newTotalAmount = parseFloat($totalAmount) + (parseFloat($oldMaintenanceCost) + parseFloat($oldFlyingCost));
                    $newTotalAmount = $newTotalAmount - accMul($lackHour,$loadedPrice) + accMul($newLackHour,$loadedPrice)
                    $("#totalAmount_" + $out).find("option:first").val(parseFloat($newTotalAmount).toFixed(0));
                    $("#totalAmount_" + $out).find("option:first").text("总价：" + fmoney(parseFloat($newTotalAmount).toFixed(0)) + "元");
                } else {
                    $this.closest("tr").addClass("deleteLine");
                    $this.closest("tr").find("select").attr("disabled", "disabled");
                    $("#isDelete_" + $out + "_" + $in).val(true);

                    //新总飞行时长
                    var $newTotalTime = parseFloat($totalTimeCost) - parseFloat($oldTimeCost);
                    $("#totalHour_" + $out).val(parseFloat($newTotalTime).toFixed(2));
                    // 新补齐小时数
                    var $newLackHour = 0;
                    if(parseFloat($newTotalTime) < parseFloat($lowestHour)){
                        // 新补齐小时数
                        $newLackHour = parseFloat($lowestHour) - parseFloat($newTotalTime);
                    } else {
                        $("#lackHour_" + $out ).val(0);
                    }
                    $("#lackHour_" + $out ).val(parseFloat($newLackHour).toFixed(2));

                    // 新实际消费小时数
                    var $newActualHours = parseFloat($newTotalTime) + parseFloat($newLackHour);
                    $("#actualHours_" + $out ).val(parseFloat($newActualHours).toFixed(2));

                    // 新总地面费用
                    var $newTotalMaintenance = parseFloat($totalMaintenance) - parseFloat($oldMaintenanceCost);
                    $("#totalMaintenance_" + $out ).find("option:first").val(parseFloat($newTotalMaintenance).toFixed(0));
                    $("#totalMaintenance_" + $out).find("option:first").text("总地面费用：" + fmoney(parseFloat($newTotalMaintenance).toFixed(0)) + "元");

                    // 新总价
                    var $newTotalAmount = $totalAmount - (parseFloat($oldMaintenanceCost) + parseFloat($oldFlyingCost));
                    $newTotalAmount = $newTotalAmount - accMul($lackHour,$loadedPrice) + accMul($newLackHour,$loadedPrice)
                    $("#totalAmount_" + $out).find("option:first").val(parseFloat($newTotalAmount).toFixed(0));
                    $("#totalAmount_" + $out).find("option:first").text("总价：" + fmoney(parseFloat($newTotalAmount).toFixed(0)) + "元");
                }
            });

            $airportChange.click(function() {
                var $this = $(this);
                var $out = $this.attr("out");
                var $in = $this.attr("in");
                var $old = $this.attr("old");
                var $new = $this.val();
                if($old == $new){
                    return;
                }
                $this.attr("old",$new);
                // 原飞行时长
                var $oldTimeCost = $("#timeCost_" + $out + "_" + $in).val();
                // 原地面费用
                var $oldMaintenanceCost =  $("#maintenanceCost_" + $out + "_" + $in).val();
                // 原飞行费用
                var $oldFlyingCost = $("#flyingCost_" + $out + "_" + $in).val();

                // 飞机载客单价
                var $loadedPrice = $("#loadedPrice_" + $out ).val();
                // 原总飞行时长
                var $totalTimeCost = $("#totalHour_" + $out ).val();
                // 原补齐小时数
                var $lackHour = $("#lackHour_" + $out ).val();
                // 最低小时消费
                var $lowestHour = $("#lowestHour_" + $out ).val();
                // 实际消费小时数
                var $actualHours = $("#actualHours_" + $out ).val();
                // 总价
                var $totalAmount = $("#totalAmount_" + $out ).val();
                // 总地面费用
                var $totalMaintenance = $("#totalMaintenance_" + $out ).val();

                var $airplaneId = $("#airplaneId_" + $out).val();
                var $departureId = $("#departureId_" + $out + "_" + $in).val();
                var $destinationId = $("#destinationId_" + $out + "_" + $in).val();
                var $takeoffTime = $("#takeoffTime_" + $out + "_" + $in).val();
                $(".inputSelect").attr("disabled",false);
                $.ajax({
                    url: "oneLineCost.jhtml",
                    type: "GET",
                    data: {airplaneId: $airplaneId, departureId: $departureId, destinationId : $destinationId, takeoffTime: $takeoffTime},
                    dataType: "json",
                    success: function(data) {
                        var quoteAirline = data;
                        var $timeCost = quoteAirline["timeCost"];
                        $("#departureId_" + $out + "_" + $in).val(quoteAirline["departureId"]);
                        $("#destinationId_" + $out + "_" + $in).val(quoteAirline["destinationId"]);
                        $("#timeCost_" + $out + "_" + $in).find("option:first").val($timeCost);
                        $("#timeCost_" + $out + "_" + $in).find("option:first").text($timeCost + "小时");
                        var $maintenanceCost = quoteAirline["maintenanceCost"];
                        $("#maintenanceCost_" + $out + "_" + $in).find("option:first").val($maintenanceCost);
                        $("#maintenanceCost_" + $out + "_" + $in).find("option:first").text(fmoney($maintenanceCost) + "元");
                        var $flyingCost = quoteAirline["flyingCost"];
                        $("#flyingCost_" + $out + "_" + $in).val($flyingCost);

                        //新总飞行时长
                        var $newTotalTime = parseFloat($totalTimeCost) - parseFloat($oldTimeCost) + parseFloat($timeCost);
                        $("#totalHour_" + $out).val(parseFloat($newTotalTime).toFixed(2));
                        // 新补齐小时数
                        var $newLackHour = 0;
                        if(parseFloat($newTotalTime) < parseFloat($lowestHour)){
                            // 新补齐小时数
                            $newLackHour = parseFloat($lowestHour) - parseFloat($newTotalTime);
                        } else {
                            $("#lackHour_" + $out ).val(0);
                        }
                        $("#lackHour_" + $out ).val(parseFloat($newLackHour).toFixed(2));

                        // 新实际消费小时数
                        var $newActualHours = parseFloat($newTotalTime) + parseFloat($newLackHour);
                        $("#actualHours_" + $out ).val(parseFloat($newActualHours).toFixed(2));

                        // 新总地面费用
                        var $newTotalMaintenance = parseFloat($totalMaintenance) - parseFloat($oldMaintenanceCost) + parseFloat($maintenanceCost);
                        $("#totalMaintenance_" + $out ).find("option:first").val(parseFloat($newTotalMaintenance).toFixed(0));
                        $("#totalAmount_" + $out).find("option:first").text("总地面费用：" + fmoney(parseFloat($newTotalMaintenance).toFixed(0)) + "元");

                        // 新总价
                        var $newTotalAmount = $totalAmount - (parseFloat($oldMaintenanceCost) + parseFloat($oldFlyingCost)) + (parseFloat($maintenanceCost) + parseFloat($flyingCost));
                        $newTotalAmount = $newTotalAmount - accMul($lackHour,$loadedPrice) + accMul($newLackHour,$loadedPrice)
                        $("#totalAmount_" + $out).find("option:first").val(parseFloat($newTotalAmount).toFixed(0));
                        $("#totalAmount_" + $out).find("option:first").text("总价：" + fmoney(parseFloat($newTotalAmount).toFixed(0)) + "元");
                        $this.attr("old",$new);
                    }
                });
                $(".inputSelect").attr("disabled","disabled");

            });

            function accMul(arg1,arg2)
            {
                var m=0,s1=arg1.toString(),s2=arg2.toString();
                try{m+=s1.split(".")[1].length}catch(e){}
                try{m+=s2.split(".")[1].length}catch(e){}
                return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
            }

            $selectAllQuote.click(function() {
                if($(this).is(':checked') ){
                    $select.attr("checked", "checked");
                }else{
                    $select.attr("checked", false);
                }
            });

            $selectAllAirplane.click(function() {
                if($(this).is(':checked') ){
                    $airplaneIds.attr("checked", "checked");
                }else{
                    $airplaneIds.attr("checked", false);
                }
            });

            // 提交报价
            $submitQuote.click(function() {

                var bool = true;
                $.each($select, function(i, select){
                    if(select.checked == true){
                        bool = true;
                        return false;
                    } else {
                        bool = false;
                    }
                });
                if(!bool){
                    alert("请选择要提交的报价！");
                    return false;
                }
                $(".inputSelect").attr("disabled",false);
                $("input[type='text']").attr("readonly", false);
                $quoteForm.submit();
                $(".inputSelect").attr("disabled","disabled");
                $("input[type='text']").attr("readonly", "readonly");
                return false;
            });

            //格式化金额
            function fmoney(s)
            {
                var n=0;
                s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";//更改这里n数也可确定要保留的小数位
                var l = s.split(".")[0].split("").reverse(),
                        r = s.split(".")[1];
                t = "";
                for(i = 0; i < l.length; i++ )
                {
                    t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
                }
                return t.split("").reverse().join("");//保留2位小数  如果要改动 把substring 最后一位数改动就可
            }

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
<div id="addQuoteAirline" class="hideDiv" >
    <div class="path">
        <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 价格明细
    </div>
    <form id="quoteForm" action="saveQuote.jhtml" method="POST">
        <div style="text-align: right;width: 100%;margin-top: 10px;margin-bottom: 10px">
            <a href="javascript:;"  style="height: 30px;line-height: 26px;padding: 5px 26px 5px 26px;outline: none;border: 1px solid rgb(183, 200, 217);" >
                <input type="checkbox" id="selectAllQuote"  value=""/> 全选/反选
            </a>
            &nbsp;&nbsp;
            <a href="javascript:;" class="submitQuote"  style="height: 30px;line-height: 26px;padding: 5px 26px 5px 26px;outline: none;border: 1px solid rgb(183, 200, 217);" >
                提交报价
            </a>
            &nbsp;&nbsp;
            <a href="list.jhtml" class="cancleButton"  style="height: 30px;line-height: 26px;padding: 5px 26px 5px 26px;outline: none;border: 1px solid rgb(183, 200, 217);" >
                关闭
            </a>
        </div>
    [#list quotes as tempQuote]
        <input type="hidden" name="quotes[${tempQuote_index}].airplaneId" id="airplaneId_${tempQuote_index}" value="${tempQuote.airplaneId}"/>
        <input type="hidden" name="quotes[${tempQuote_index}].regNo" value="${tempQuote.regNo}"/>
        <input type="hidden" name="quotes[${tempQuote_index}].type" value="${tempQuote.type}"/>
        <input type="hidden" name="quotes[${tempQuote_index}].planType" value="${quote.planType}"/>
        <input type="hidden" name="quotes[${tempQuote_index}].takeoffTime" value="${quote.takeoffTime?string("yyyy-MM-dd HH:mm:ss")}"/>
        <input type="hidden" name="quotes[${tempQuote_index}].departureId" value="${tempQuote.departureId}"/>
        <input type="hidden" name="quotes[${tempQuote_index}].destinationId" value="${tempQuote.destinationId}"/>
        <input type="hidden" name="quotes[${tempQuote_index}].totalDay" value="${tempQuote.totalDay}"/>
        <input type="hidden" id="loadedPrice_${tempQuote_index}" value="${tempQuote.airplane.loadedPrice}"/>
        <table class="list" >
            <tr>
                <th width="55%"> 航段信息    &nbsp;&nbsp;&nbsp;飞机：${tempQuote.airplane.brand}（${tempQuote.airplane.regNo}/${tempQuote.airplane.type}）</th>
                <th>起飞时间</th>
                <th>飞行时长</th>
                <th>小时/元</th>
                <th>地面费用</th>
            </tr>

            [#list tempQuote.quoteAirlineList as quoteAirline]
                <input type="hidden" id="flyingCost_${tempQuote_index}_${quoteAirline_index}" value="${quoteAirline.flyingCost}"/>
                <input type="hidden"  name="quotes[${tempQuote_index}].quoteAirlineList[${quoteAirline_index}].isSpecial" value="${quoteAirline.isSpecial}"/>
                <input type="hidden"  name="quotes[${tempQuote_index}].quoteAirlineList[${quoteAirline_index}].specialType" value="${quoteAirline.specialType}"/>
                <input type="hidden" id="isDelete_${tempQuote_index}_${quoteAirline_index}"  name="quotes[${tempQuote_index}].quoteAirlineList[${quoteAirline_index}].isDelete" value="false"/>
                <tr type="${quoteAirline.specialType}">
                    <td>
                        [#if quoteAirline.specialType == 'before']
                            <input type="checkbox"  class="canncleLine" out="${tempQuote_index}" in="${quoteAirline_index}" checked="checked"/>
                            <input type="hidden" id="takeoffTime_${tempQuote_index}_${quoteAirline_index}" name="quotes[${tempQuote_index}].quoteAirlineList[${quoteAirline_index}].takeoffTime" value="${quoteAirline.takeoffTime?string("yyyy-MM-dd HH:mm:ss")}"/>
                            <select name="quotes[${tempQuote_index}].quoteAirlineList[${quoteAirline_index}].departureId" id="departureId_${tempQuote_index}_${quoteAirline_index}"
                                    out="${tempQuote_index}" in="${quoteAirline_index}" class="airportChange" old="${quoteAirline.departureId}">
                                [#list tempQuote.airplane.airports as airport]
                                    <option value="${airport.id}" [#if airport.id==quoteAirline.departureId]selected="selected" [/#if]>${airport.name}</option>
                                [/#list]
                            </select>
                            -->
                            <select name="quotes[${tempQuote_index}].quoteAirlineList[${quoteAirline_index}].destinationId" class="inputSelect" disabled="disabled"  id="destinationId_${tempQuote_index}_${quoteAirline_index}" out="${tempQuote_index}" in="${quoteAirline_index}">
                                <option  value="${quoteAirline.destinationId}">${quoteAirline.destination.name}</option>
                            </select>
                        [#elseif quoteAirline.specialType == 'after']
                            <input type="checkbox"  class="canncleLine" out="${tempQuote_index}" in="${quoteAirline_index}" checked="checked"/>
                            <input type="hidden" id="takeoffTime_${tempQuote_index}_${quoteAirline_index}" value="${quoteAirline.takeoffTime?string("yyyy-MM-dd HH:mm:ss")}"/>
                            <select name="quotes[${tempQuote_index}].quoteAirlineList[${quoteAirline_index}].departureId" class="inputSelect" disabled="disabled"  id="departureId_${tempQuote_index}_${quoteAirline_index}" out="${tempQuote_index}" in="${quoteAirline_index}">
                                <option  value="${quoteAirline.departureId}">${quoteAirline.departure.name}</option>
                            </select>
                            -->
                            <select name="quotes[${tempQuote_index}].quoteAirlineList[${quoteAirline_index}].destinationId"  id="destinationId_${tempQuote_index}_${quoteAirline_index}"
                                    out="${tempQuote_index}" in="${quoteAirline_index}" class="airportChange"  old="${quoteAirline.destinationId}">
                                [#list tempQuote.airplane.airports as airport]
                                    <option value="${airport.id}" [#if airport.id==quoteAirline.destinationId]selected="selected" [/#if]>${airport.name}</option>
                                [/#list]
                            </select>
                        [#elseif quoteAirline.specialType == 'succession' || quoteAirline.specialType == 'interrupted']
                            <select name="quotes[${tempQuote_index}].quoteAirlineList[${quoteAirline_index}].departureId" class="inputSelect" disabled="disabled" >
                                <option  value="${quoteAirline.departureId}">${quoteAirline.departure.name}</option>
                            </select>
                            -->
                            <select name="quotes[${tempQuote_index}].quoteAirlineList[${quoteAirline_index}].centerId" class="inputSelect" disabled="disabled" >
                                <option  value="${quoteAirline.centerId}">${quoteAirline.center.name}</option>
                            </select>
                            -->
                            <select name="quotes[${tempQuote_index}].quoteAirlineList[${quoteAirline_index}].destinationId" class="inputSelect" disabled="disabled" >
                                <option  value="${quoteAirline.destinationId}">${quoteAirline.destination.name}</option>
                            </select>
                        [#else]
                            <input type="hidden" id="takeoffTime_${tempQuote_index}_${quoteAirline_index}" name="quotes[${tempQuote_index}].quoteAirlineList[${quoteAirline_index}].takeoffTime" value="${quoteAirline.takeoffTime?string("yyyy-MM-dd HH:mm:ss")}"/>
                            <select name="quotes[${tempQuote_index}].quoteAirlineList[${quoteAirline_index}].departureId" class="inputSelect" disabled="disabled" >
                                <option  value="${quoteAirline.departureId}">${quoteAirline.departure.name}</option>
                            </select>
                            -->
                            <select name="quotes[${tempQuote_index}].quoteAirlineList[${quoteAirline_index}].destinationId" class="inputSelect" disabled="disabled">
                                <option  value="${quoteAirline.destinationId}">${quoteAirline.destination.name}</option>
                            </select>

                        [/#if]
                        &nbsp;（[#if quoteAirline.isSpecial == true]调机[#else ]任务[/#if]）
                    </td>
                    <td>
                        [#if quoteAirline.isSpecial == true][#else ] ${quoteAirline.takeoffTime?string("yyyy-MM-dd HH:mm")}[/#if]
                    </td>
                    <td>
                        <select name="quotes[${tempQuote_index}].quoteAirlineList[${quoteAirline_index}].timeCost" id="timeCost_${tempQuote_index}_${quoteAirline_index}" class="inputSelect" disabled="disabled">
                            <option  value="${quoteAirline.timeCost}">${quoteAirline.timeCost}小时</option>
                        </select>
                    </td>
                    <td>
                    ${quoteAirline.unitPrice?string(',###')}元
                    </td>
                    <td>
                        <select name="quotes[${tempQuote_index}].quoteAirlineList[${quoteAirline_index}].maintenanceCost" id="maintenanceCost_${tempQuote_index}_${quoteAirline_index}" class="inputSelect" disabled="disabled">
                            <option  value="${quoteAirline.maintenanceCost}">${quoteAirline.maintenanceCost?string(',###')}元</option>
                        </select>
                    </td>
                </tr>
            [/#list]
            <tr class="total">
                <td>
                    <select name="quotes[${tempQuote_index}].totalAmount" id="totalAmount_${tempQuote_index}" class="inputSelect" disabled="disabled">
                        <option  value="${tempQuote.totalAmount}">总价：${tempQuote.totalAmount?string(',###')}元</option>
                    </select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    实际消费小时数：<input type="text" name="quotes[${tempQuote_index}].actualHours" value="${tempQuote.actualHours}" id="actualHours_${tempQuote_index}" readonly="readonly"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    补齐小时数：<input type="text" name="quotes[${tempQuote_index}].lackHour" value="${tempQuote.lackHour}" id="lackHour_${tempQuote_index}"  readonly="readonly"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    调正天数：<input type="text" name="quotes[${tempQuote_index}].stopDays" value="${tempQuote.stopDays}" id="totalAmount_${tempQuote_index}"  readonly="readonly"/></td>
                <td></td>
                <td>总飞行时长：<input type="text" name="quotes[${tempQuote_index}].totalHour" value="${tempQuote.totalHour}" id="totalHour_${tempQuote_index}" readonly="readonly"/></td>
                <td></td>
                <td>
                    <select name="quotes[${tempQuote_index}].totalMaintenance" id="totalMaintenance_${tempQuote_index}" class="inputSelect" disabled="disabled">
                        <option  value="${tempQuote.totalMaintenance}">总地面费用：${tempQuote.totalMaintenance?string(',###')}元</option>
                    </select>
                </td>
            </tr>
            <tr><td>自定义总价：<input type="text" name="quotes[${tempQuote_index}].customAmount"  id="customAmount_${tempQuote_index}" style="border: 1px solid #C9C3C3;width: 100px;" onkeyup="value=value.replace(/[^\d]/g,'')" onafterpaste="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"/>元</td>
                <td></td>
                <td>最低消费小时数:<input type="text" name="quotes[${tempQuote_index}].lowestHour" value="${tempQuote.lowestHour}" id="lowestHour_${tempQuote_index}" style="width: 30px"/></td>
                <td></td>
                <td>选择&nbsp;&nbsp;<input type="checkbox" name="select" value="${tempQuote_index}" /></td>
            </tr>
        </table>
    [/#list]

    </form>
</div>
<div class="dialogOverlay" style="display: none;"></div>

</body>

</html>