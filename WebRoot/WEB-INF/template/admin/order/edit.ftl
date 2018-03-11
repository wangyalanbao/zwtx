<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.order.edit")}</title>


    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.lSelect.js"></script>
    <script type="text/javascript">
        $().ready(function() {

            var $inputForm = $("#inputForm");
            var $input = $("#inputForm :input:not(#productSn)");
            var $amount = $("#amount");
            var $weight = $("#weight");
            var $quantity = $("#quantity");
            var $isInvoice = $("#isInvoice");
            var $invoiceTitle = $("#invoiceTitle");
            var $tax = $("#tax");
            var $areaId = $("#areaId");
            var $orderItemTable = $("#orderItemTable");
            var $deleteOrderItem = $("#orderItemTable a.deleteOrderItem");
            var $productSn = $("#productSn");
            var $addOrderItem = $("#addOrderItem");
            var isLocked = false;
            var timeouts = {};
            var orderItemIndex = ${order.orderItems?size};
            var $addAirline = $("#addAirline");
            var $airlineNum = $("#airlineNum");
            var $airlineTable = $("#airlineTable");
            var $search = $(".search");
            var $addInput = $(".addInput");
            var $airlineDelete = $(".airlineDelete");
            var $addFlight = $("#addFlight");
            var $orderFlight = $("#orderFlight");
            var $submitFlight = $("#submitFlight");
            var $cancelFlight = $("#cancelFlight");
            var $flightForm = $("#flightForm");
            var $tableFlight = $("#tableFlight");
            var $editFlight = $(".editFlight");
            var $deleteFlight = $(".deleteFlight");
            var $submitForm = $("#submitForm");

        [@flash_message /]

            // 地区选择
            $areaId.lSelect({
                url: "${base}/admin/common/area.jhtml"
            });

            $submitForm.click(function(){
                if($inputForm.valid()){
                    var bool = true;
                    var $departureIds = $("input[name$='departure']")
                    $.each($departureIds, function(i, name){
                        if(name.value == null || name.value == "" || name.value ==undefined ){
                            alert("航段的起始机场不能为空！");
                            bool = false;
                            return false;
                        }
                    });
                    if(bool == false){
                        return;
                    }
                    var $destinations = $("input[name$='destination']")
                    $.each($destinations, function(i, name){
                        if(name.value == null || name.value == "" || name.value ==undefined ){
                            alert("航段的目的机场不能为空！");
                            bool = false;
                            return false;
                        }
                    });
                    if(bool == false){
                        return;
                    }
                    var $takeoffTimes = $("input[name$='takeoffTime']")
                    $.each($takeoffTimes, function(i, name){
                        if(name.value == null || name.value == "" || name.value ==undefined ){
                            alert("航段的起飞时间不能为空！");
                            bool = false;
                            return false;
                        }
                    });
                    if(bool == false){
                        return;
                    }
                    var $passengerNums = $("input[name$='passengerNum']")
                    $.each($passengerNums, function(i, name){
                        if(name.value == null || name.value == "" || name.value ==undefined ){
                            alert("航段的乘客数不能为空！");
                            bool = false;
                            return false;
                        }else{
                            var $maxNum = $("#maxNum");
                            if(parseInt(name.value) > parseInt($maxNum.val())){
                                alert("航段的乘客数不能大于最大乘客数！");
                                bool = false;
                                return false;
                            }
                        }
                    });
                    if(bool == false){
                        return;
                    }
                    // 航段数
                    var $lineNum = $airlineTable[0].rows.length - 2;
                    for(var i = 0, j = i + 1; i < $lineNum , j < $lineNum + 1; i++,j++){
                        if($("#departureId_" + i).val() == $("#destinationId_" + i).val()){
                            alert("起始机场和目的机场不能相同!");
                            return;
                        }
                        if(j < $lineNum){
                            if($("#departureId_" + j).val() == $("#destinationId_" + j).val()){
                                alert("起始机场和目的机场不能相同!");
                                return;
                            }
                            if($("#takeoffTime_" + i).val() >= $("#takeoffTime_" + j).val()){
                                alert("后一航段的起飞时间要大于前一航段的起飞时间!");
                                return;
                            }
                        }
                    }
                    $inputForm.submit();
                }
            });


            // 初始值
            $input.each(function() {
                var $this = $(this);
                $this.data("value", $this.val());
            });

            // 增加航段
            $addAirline.live("click",function() {
                var $addTr = $("#addTr").clone();
                var $recordNum =$airlineTable[0].rows.length - 2;
                $addTr.find("input[name='departureIdAdd']").attr("name","orderAirlines[" + $recordNum + "].departureId");
                $addTr.find("input[name='departureAdd']").attr("name","orderAirlines[" + $recordNum + "].departure");
                $addTr.find("input[name='destinationIdAdd']").attr("name","orderAirlines[" + $recordNum + "].destinationId");
                $addTr.find("input[name='destinationAdd']").attr("name","orderAirlines[" + $recordNum + "].destination");
                $addTr.find("input[name='takeoffTimeAdd']").attr("name","orderAirlines[" + $recordNum + "].takeoffTime");
                $addTr.find("input[name='passengerNumAdd']").attr("name","orderAirlines[" + $recordNum + "].passengerNum");

                $addTr.find("#departureIdAdd").attr("id","departureId_" + $recordNum);
                $addTr.find("#destinationIdAdd").attr("id","destinationId_" + $recordNum);
                $addTr.find("#takeoffTimeAdd").attr("id","takeoffTime_" + $recordNum);

                $addTr.attr("val", $recordNum);

                $addTr.attr("id","addTr_" + $recordNum);
                $addTr.attr("style","");
                $airlineTable.append($addTr);
                $airlineNum.val(parseInt($recordNum) + 1);

//                $addInput
                // 机场选择
                $addTr.find("input[val='addInput']").autocomplete("${base}/admin/airport/search_airport.jhtml", {
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

            // 删除航段
            $airlineDelete.live("click", function(){
                $(this).closest("tr").remove();
            });


            // 检查锁定
            function checkLock() {
                if (!isLocked) {
                    $.ajax({
                        url: "check_lock.jhtml",
                        type: "POST",
                        data: {id: ${order.id}},
                        dataType: "json",
                        cache: false,
                        success: function(message) {
                            if (message.type != "success") {
                                $.message(message);
                                $inputForm.find(":input:not(#backButton), #orderItemTable a.deleteOrderItem").prop("disabled", true);
                                isLocked = true;
                            }
                        }
                    });
                }
            }

            // 检查锁定
            checkLock();
            setInterval(function() {
                checkLock();
            }, 10000);

            // 开据发票
            $isInvoice.click(function() {
                if ($(this).prop("checked")) {
                    $invoiceTitle.prop("disabled", false);
                    $tax.prop("disabled", false);
                } else {
                    $invoiceTitle.prop("disabled", true);
                    $tax.prop("disabled", true);
                }
            });

            // 计算
            $input.bind("input propertychange change", function(event) {
                if (event.type != "propertychange" || event.originalEvent.propertyName == "value") {
//                    calculate($(this));
                }
            });

            // 计算
            function calculate($input) {
                var name = $input.attr("name");
                clearTimeout(timeouts[name]);
                timeouts[name] = setTimeout(function() {
                    if ($inputForm.valid()) {
                        $.ajax({
                            url: "calculate.jhtml",
                            type: "POST",
                            data: $inputForm.serialize(),
                            dataType: "json",
                            cache: false,
                            success: function(data) {
                                if (data.message.type == "success") {
                                    $input.data("value", $input.val());
                                    $amount.text(currency(data.amount, true));
                                    $weight.text(data.weight);
                                    $quantity.text(data.quantity);
                                    if ($input.hasClass("orderItemPrice") || $input.hasClass("orderItemQuantity")) {
                                        var $tr = $input.closest("tr");
                                        $tr.find("span.subtotal").text(currency(data.orderItems[$tr.find("input.orderItemSn").val()].subtotal, true));
                                    }
                                } else {
                                    $.message(data.message);
                                    $input.val($input.data("value"));
                                }
                            }
                        });
                    }
                }, 500);
            }



            $.validator.addClassRules({
                orderItemPrice: {
                    required: true,
                    min: 0,
                    decimal: {
                        integer: 12,
                        fraction: ${setting.priceScale}
                    }
                },
                orderItemQuantity: {
                    required: true,
                    integer: true,
                    min: 1
                }
            });

            // 表单验证
            $inputForm.validate({
                rules: {
                    offsetAmount: {
                        required: true,
                        number: true,
                        decimal: {
                            integer: 12,
                            fraction: ${setting.priceScale}
                        }
                    },
                    point: {
                        required: true,
                        digits: true
                    },
                    freight: {
                        required: true,
                        min: 0,
                        decimal: {
                            integer: 12,
                            fraction: ${setting.priceScale}
                        }
                    },
                    paymentMethodId: "required",
                    tax: {
                        required: true,
                        min: 0,
                        decimal: {
                            integer: 12,
                            fraction: ${setting.priceScale}
                        }
                    },
                    telephone: "required"
                }
            });

            var $isUpdate = false;
            // 增加航班
            $addFlight.click(function() {
                $isUpdate = false;
                $orderFlight.show();
                $(".dialogOverlay").show();
                $("#orderAirlineId").val("");
                $("#flightId").val("");
                $("#flightNo").val("");
                $("#boardingTime").val("");
                $("#boardingPlace").val("");
            });
            // 编辑航班
            $editFlight.live("click", function(){
                $isUpdate = true;
                $orderFlight.show();
                $("#orderAirlineId").val("");
                $("#flightId").val("");
                $("#flightNo").val("");
                $("#boardingTime").val("");
                $("#boardingPlace").val("");
                $(".dialogOverlay").show();
                var $id = $(this).attr("val");
                $.ajax({
                    url: "getFlight.jhtml",
                    type: "GET",
                    data:  {id:$id},
                    dataType: "json",
                    success: function(data) {
                        var date = new Date(data.boardingTime);
                        var   year=date.getFullYear();
                        var   month=date.getMonth()+1;
                        var   day=date.getDate();
                        var   hour=date.getHours();
                        var   minute=date.getMinutes();
                        var   second=date.getSeconds();
                        var boardingTime =   year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
                        $("#orderAirlineId").val(data.orderAirline.id);
                        $("#flightId").val(data.id);
                        $("#flightNo").val(data.flightNo);
                        $("#boardingTime").val(boardingTime);
                        $("#boardingPlace").val(data.boardingPlace);
                    }
                });
            });

            $submitFlight.click(function() {
                if( $("#orderAirlineId").val() == ""){
                    alert("请选择航段！");
                    $("#orderAirlineId").focus();
                    return;
                }
                if($("#flightNo").val() == ""){
                    alert("请输入航班号！");
                    $("#flightNo").focus();
                    return;
                }
                if($("#boardingTime").val() == ""){
                    alert("请输入登机时间！");
                    $("#boardingTime").focus();
                    return;
                }
                if($("#boardingPlace").val() == ""){
                    alert("请输入登机地点！");
                    $("#boardingPlace").focus();
                    return;
                }
                $.ajax({
                    url: "saveFlight.jhtml",
                    type: "GET",
                    data:  {orderId:$("#orderId").val(), flightId:$("#flightId").val(), flightNo:$("#flightNo").val(), orderAirlineId:$("#orderAirlineId").val(), boardingTime:$("#boardingTime").val(), boardingPlace: $("#boardingPlace").val()},
                    dataType: "json",
                    cache: false,
                    success: function(data) {
                        var  $tr = $("#tr_flight").clone();
                        $tr.attr("style","");
                        $tr.find("#td_flightNo").html(data.flightNo);
                        $tr.find("#td_flightAirline").html(data.orderAirline.departure + "-" + data.orderAirline.destination);
                        var date = new Date(data.boardingTime);
                        var year=date.getFullYear();
                        var month=date.getMonth()+1;
                        if((month + "").length < 2){
                            month = "0" + month
                        }
                        var day=date.getDate();
                        if((day + "").length < 2){
                            day = "0" + day
                        }
                        var hour=date.getHours();
                        if((hour + "").length < 2){
                            hour = "0" + hour
                        }
                        var minute=date.getMinutes();
                        if((minute + "").length < 2){
                            minute = "0" + minute
                        }
                        var second=date.getSeconds();
                        if((second +"").length < 2){
                            second = "0" + second
                        }
                        var boardingTime =   year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
                        $tr.find("#td_flightTime").html(boardingTime);
                        $tr.find("#td_flightPlace").html(data.boardingPlace);
                        $tr.find(".editFlight").attr("val", data.id);
                        $tr.find(".deleteFlight").attr("val", data.id);
                        if($isUpdate == false){
                            $tableFlight.append($tr);
                        }
                        $orderFlight.hide();
                        $(".dialogOverlay").hide();
                    }
                });

            });

            // 关闭div
            $cancelFlight.click(function() {
                $orderFlight.hide();
                $(".dialogOverlay").hide();
            });

            // 删除航班
            $deleteFlight.live("click",function(){
                var $id = $(this).attr("val");
                var $this = $(this);
                $.dialog({
                    type: "warn",
                    content: "${message("admin.dialog.deleteConfirm")}",
                    onOk: function() {
                        $.ajax({
                            url: "deleteFlight.jhtml",
                            type: "POST",
                            data:  {id:$id},
                            dataType: "json",
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

        });
    </script>
    <style type="text/css">
        table.list tr:last-child td a{
            display: block !important;
        }
    </style>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.order.edit")}
</div>
<ul id="tab" class="tab">
    <li>
        <input type="button" value="${message("admin.order.orderInfo")}" />
    </li>
    <li>
        <input type="button" value="${message("OrderAirline.info")}" />
    </li>
    <li>
        <input type="button" value="${message("OrderFlight.info")}" />
    </li>
    <li>
        <input type="button" value="${message("admin.order.invoiceInfo")}" />
    </li>
</ul>
<form id="inputForm" action="update.jhtml" method="post">
    <input type="hidden" name="id" value="${order.id}" />
    <input type="hidden" id="maxNum" value="${order.airplane.capacity}"/>
[#--订单信息--]
    <div class="tabContent">
        <table class="input ">
            <tr>
                <th>
                ${message("Order.sn")}:
                </th>
                <td width="360">
                ${order.sn}
                </td>
                <th>
                ${message("admin.common.createDate")}:
                </th>
                <td>
                ${order.createDate?string("yyyy-MM-dd HH:mm:ss")}
                </td>
            </tr>
            <tr>
                <th>
                ${message("Order.orderStatus")}:
                </th>
                <td>
                ${message("Order.OrderStatus." + order.orderStatus)}
                [#if order.expired]
                    <span title="${message("Order.expire")}: ${order.expire?string("yyyy-MM-dd HH:mm:ss")}">(${message("admin.order.hasExpired")})</span>
                [#elseif order.expire??]
                    <span title="${message("Order.expire")}: ${order.expire?string("yyyy-MM-dd HH:mm:ss")}">(${message("Order.expire")}: ${order.expire})</span>
                [/#if]
                </td>
                <th>
                ${message("Order.paymentStatus")}:
                </th>
                <td>
                ${message("Order.PaymentStatus." + order.paymentStatus)}
                </td>
            </tr>
            <tr>
                <th>
                ${message("Order.category")}:
                </th>
                <td>
                [#if order.isSpecial == true]
                    调机
                [#else ]
                    任务
                [/#if]
                </td>
                <th>
                ${message("Member.username")}:
                </th>
                <td>
                ${order.customerId.realName}
                </td>
            </tr>
            <tr>
                <th>
                ${message("Order.amount")}:
                </th>
                <td>
                ${order.amount?string(',###')}元
                    <input type="hidden" class="text" name="totalAmount" value="${(order.totalAmount)}"/>
                </td>
                <th>
                ${message("Order.amountPaid")}:
                </th>
                <td>
                ${currency(order.amountPaid, true)}
                    <input type="hidden" class="text" name="amountPaid" value="${(order.amountPaid)}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("Order.offsetAmount")}:
                </th>
                <td>
                    <input type="text" class="text" name="offsetAmount" value="${(order.offsetAmount)}"/>
                </td>
                <th>
                ${message("Order.telephone")}:
                </th>
                <td>
                    <input type="text" class="text" name="telephone" value="${(order.telephone)}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("airplane.company")}:
                </th>
                <td>
                ${order.company.name}
                </td>
                <th>
                ${message("Order.airplane.type")}:
                </th>
                <td>
                ${order.airplane.typeId.typeName}
                </td>
            </tr>
            <tr>
                <th>
                ${message("Order.airplane.regNo")}:
                </th>
                <td>
                ${order.airplane.regNo}
                </td>
                <th>
                ${message("Order.paymentMethod")}:
                </th>
                <td>
                    <select name="paymentMethodId">
                        <option value="">${message("admin.common.choose")}</option>
                    [#list paymentMethods as paymentMethod]
                        <option value="${paymentMethod.id}"[#if paymentMethod == order.paymentMethod] selected="selected"[/#if]>${paymentMethod.name}</option>
                    [/#list]
                    </select>
                </td>
            </tr>
            <tr>
                <th>
                ${message("Order.Airline.firstTakeoffTime")}:
                </th>
                <td>
                ${order.firstTakeoffTime?string("yyyy-MM-dd HH:mm")}
                </td>
                <th>
                ${message("Order.Airline.lastTakeoffTime")}:
                </th>
                <td>
                ${order.lastTakeoffTime?string("yyyy-MM-dd HH:mm")}
                </td>
            </tr>

        </table>

    </div>
[#--航段信息--]
    <div class="tabContent">
        <input type="hidden" id="airlineNum" value="${airlineNum}"/>
        <table class="list" id="airlineTable">
            <tbody>
            <tr>
                <th >${message("airline.departureId")}</th>
                <th >${message("airline.destinationId")}</th>
                <th >${message("Order.firstTakeoffTime")}</th>
                <th >${message("OrderAirline.timeCost")}</th>
                <th >${message("Order.passengerNum")}</th>
                <th>
                    <span>${message("admin.common.handle")}</span>
                </th>
            </tr>
            <tr id="addTr" style="display: none" val="">
                <td>
                    <input class="text" type="hidden"  name="departureIdAdd" id="departureIdAdd" />
                    <input class="text search addInput" type="text"  name="departureAdd" val="addInput" />
                </td>
                <td>
                    <input class="text" type="hidden"  name="destinationIdAdd"  id="destinationIdAdd" />
                    <input class="text search addInput" type="text"  name="destinationAdd"  val="addInput"  />
                </td>
                <td>
                    <span>
                        <input type="text" name="takeoffTimeAdd" id="takeoffTimeAdd"  class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});"/>
                    </span>
                </td>
                <td>
                    <span id="timeCostAdd">自动计算</span>
                </td>
                <td>
                    <input class="text" type="text"  name="passengerNumAdd"  onkeyup="value=value.replace(/[^\d]/g,'')" onafterpaste="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"  />
                </td>
                <td>
                    <a href="javascript:;" class="delete airlineDelete" val=""   style="display: none">[${message("admin.common.delete")}]</a>
                </td>
            </tr>
            [#list order.orderAirlines as orderAirline]

            <tr id="tr_${orderAirline_index}" val="${orderAirline_index}" >
                <input class="text" type="hidden" name="orderAirlines[${orderAirline_index}].id" value="${orderAirline.id}"/>
                <td>
                    <input class="text" type="hidden" id="departureId_${orderAirline_index}"  name="orderAirlines[${orderAirline_index}].departureId" value="${orderAirline.departureId}"/>
                    <input class="text search" type="text" id="departure_${orderAirline_index}"  name="orderAirlines[${orderAirline_index}].departure" value="${orderAirline.departure}" val="${orderAirline_index}" [#if order.isSpecial == true] readonly="readonly" [/#if]/>
                </td>
                <td>
                    <input class="text" type="hidden" id="destinationId_${orderAirline_index}"  name="orderAirlines[${orderAirline_index}].destinationId" value="${orderAirline.destinationId}" />
                    <input class="text search" type="text" id="destination_${orderAirline_index}"  name="orderAirlines[${orderAirline_index}].destination" value="${orderAirline.destination}"  val="${orderAirline_index}"  [#if order.isSpecial == true] readonly="readonly" [/#if]/>
                </td>
                <td>
                    <span>
                    <input type="text" id="takeoffTime_${orderAirline_index}"  name="orderAirlines[${orderAirline_index}].takeoffTime"  value="${orderAirline.takeoffTime?string("yyyy-MM-dd HH:mm:ss")}"  class="text Wdate"   [#if order.isSpecial == true]  readonly="readonly" [#else ]onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});"[/#if]/>
                    </span>
                </td>
                <td>
                    [#if orderAirline.timeCost??]
                        [#if orderAirline.timeCost?index_of(".")?number > -1]
                            [#if orderAirline.timeCost?substring(0,orderAirline.timeCost?index_of("."))?number > 0]
                            ${orderAirline.timeCost?substring(0,orderAirline.timeCost?index_of("."))}小时
                            [/#if]
                        ${("0." + orderAirline.timeCost?substring(orderAirline.timeCost?index_of(".") + 1))?number*60}分钟
                        [#else]
                        ${orderAirline.timeCost}小时
                        [/#if]
                    [/#if]
                </td>
                <td>
                    <input class="text" type="text" id="passengerNum_${orderAirline_index}"  name="orderAirlines[${orderAirline_index}].passengerNum" value="${orderAirline.passengerNum}"
                           onkeyup="value=value.replace(/[^\d]/g,'')" onafterpaste="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"/>
                </td>
                <td>
                    <a href="javascript:;" class="delete airlineDelete" val="${orderAirline.id}" style="display: none">[${message("admin.common.delete")}]</a>
                </td>
            </tr>
            [/#list]

            </tbody>
        </table>
        <div [#if order.isSpecial == true] style="display: none;" [/#if]>
            <input type="button" class="button" id="addAirline" value="${message("OrderAirline.add")}"  />
        </div>
    </div>
[#--航班信息--]
    <div class="tabContent">
        <table class="input" id="tableFlight">
            <tr class="title">
                <th>
                ${message("OrderFlight.flightNo")}
                </th>
                <th>
                ${message("OrderAirline.name")}
                </th>
                <th>
                ${message("OrderFlight.boardingTime")}
                </th>
                <th>
                ${message("OrderFlight.boardingPlace")}
                </th>
                <th>
                ${message("admin.common.handle")}
                </th>
            </tr>
            <tr style="display: none" id="tr_flight">
                <td id="td_flightNo">
                </td>
                <td width="400"  id="td_flightAirline">
                </td>
                <td id="td_flightTime">
                </td>
                <td id="td_flightPlace">
                </td>
                <td>
                    <input type="button" class="button editFlight" val=""  value="${message("admin.common.edit")}"  />
                    <input type="button" class="button deleteFlight" val=""  value="${message("admin.common.delete")}"  />
                </td>
            </tr>
        [#list order.orderFlights as orderFlight]
            <tr>
                <td>
                ${orderFlight.flightNo}
                </td>
                <td width="400">
                ${orderFlight.orderAirline.departure} - ${orderFlight.orderAirline.destination}
                </td>
                <td>
                ${orderFlight.boardingTime?string("yyyy-MM-dd HH:mm:ss")}
                </td>
                <td>
                ${orderFlight.boardingPlace}
                </td>
                <td>
                    <input type="button" class="button editFlight" val="${orderFlight.id}"  value="${message("admin.common.edit")}"  />
                    <input type="button" class="button deleteFlight" val="${orderFlight.id}" value="${message("admin.common.delete")}"  />
                </td>
            </tr>
        [/#list]
        </table>
        <div>
            <input type="button" class="button" id="addFlight" val="${orderFlight.id}" value="${message("OrderFlight.add")}"  />
        </div>
        <div class="dialogOverlay" style="display: none;"></div>


    </div>
    </div>
[#--发票信息--]
    <table class="input tabContent">
    [#--[#list order.shippings as shipping]--]
        <input type="hidden" name="shippings[0].id" value="${shipping.id}" />
        <tr>
            <th>
            ${message("Order.invoiceTitle")}:
            </th>
            <td>
                <input type="text" class="text" name="shippings[0].invoiceTitle" value="${shipping.invoiceTitle}"/>
            </td>
            <th>
            ${message("Order.shippingMethod")}:
            </th>
            <td>
                <select name="shippingMethodId">
                    <option value="">${message("admin.common.choose")}</option>
                [#list shippingMethods as shippingMethod]
                    <option value="${shippingMethod.id}" [#if shippingMethod.name == shipping.shippingMethod] selected="selected"[/#if] >${shippingMethod.name}</option>
                [/#list]
                </select>
            </td>
        </tr>
        <tr>
            <th>
            ${message("Shipping.trackingNo")}:
            </th>
            <td>
                <input type="text" class="text"  name="shippings[0].trackingNo" value="${shipping.trackingNo}"/>
            </td>
            <th>
            ${message("Order.consignee")}:
            </th>
            <td>
                <input type="text" class="text"  name="shippings[0].consignee" value="${shipping.consignee}"/>
            </td>

        </tr>
        <tr>
            <th>
            ${message("Order.area")}:
            </th>
            <td>
                <span class="fieldSet">
            <input type="hidden" id="areaId" name="areaId" value="${(shipping.area.id)!}" treePath="${(shipping.area.treePath)!}"  />
                </span>
            </td>
            <th>
            ${message("Order.address")}:
            </th>
            <td>
                <input type="text" class="text"  name="shippings[0].address"  value="${shipping.address}"/>
            </td>

        </tr>
        <tr>
            <th>
            ${message("Order.phone")}:
            </th>
            <td>
                <input type="text" class="text"  name="shippings[0].phone"  value="${shipping.phone}"/>
            </td>
            <th>
            ${message("Order.zipCode")}:
            </th>
            <td>
                <input type="text" class="text"  name="shippings[0].zipCode"  value="${shipping.zipCode}"/>
            </td>
        </tr>
        <tr>
            <th>
            ${message("Order.memo")}:
            </th>
            <td colspan="3">
                <input type="text" class="text" name="shippings[0].memo"  value="${shipping.memo}"/>
            </td>
        </tr>
    [#--[/#list]--]
    </table>
    <table class="input">
        <tr>
            <th>
                &nbsp;
            </th>
            <td>
                <input type="button" class="button" id="submitForm" value="${message("admin.common.submit")}" />
                <input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
            </td>
        </tr>
    </table>
</form>
<div id="orderFlight" style="display: none; position: fixed;background-color: #ffffff;top:200px;left: 500px;border:2px solid #ddd; z-index: 100;">
    <h2 >添加航班信息</h2>
    <form id="flightForm" >
        <input type="hidden" name="orderId" id="orderId" value="${order.id}" />
        <input type="hidden" name="id" id="flightId" value="" />
        <div style="height: 200px; overflow-x: hidden; overflow-y: auto;">
            <table class="input" >
                <tr>
                    <th>
                    ${message("OrderAirline.name")}:
                    </th>
                    <td>
                        <select name="orderAirlineId" id="orderAirlineId">
                            <option value="">${message("admin.common.choose")}</option>
                        [#list order.orderAirlines as orderAirline]
                            <option value="${orderAirline.id}" >${orderAirline.departure + "-->" + orderAirline.destination}</option>
                        [/#list]
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>
                    ${message("OrderFlight.flightNo")}:
                    </th>
                    <td>
                        <input type="text" name="orderFlight.flightNo" id="flightNo" class="text"  maxlength="200" />
                    </td>
                </tr>
                <tr>
                    <th>
                    ${message("OrderFlight.boardingTime")}:
                    </th>
                    <td>
                        <input type="text" name="boardingTime" id="boardingTime"  class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});" />
                    </td>
                </tr>
                <tr>
                    <th>
                    ${message("OrderFlight.boardingPlace")}:
                    </th>
                    <td>
                        <input type="text" name="boardingPlace" id="boardingPlace" class="text" />
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
</body>
</html>