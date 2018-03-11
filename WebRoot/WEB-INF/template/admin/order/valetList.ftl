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
            var $listTable = $("#listTable");
            var $filterSelect = $(".filterSelect");
            var $filterOption = $(".filterOption a");
            var $searchButton = $("#search");
            var $search_regNo = $("#search_regNo");
            var $search_type = $("#search_type");
            var $search = $(".search");
            var $transfer = $("#transfer");
            var $moreWay = $("#moreWay");
            var $oneWay = $("#oneWay");
            var $returnWay =$("#returnWay");
            var $addFlight = $("#addFlight");
            var $flightList = $("#flightList");
            var $airlineDelete = $(".airlineDelete");
            var $quoteButton = $("#quoteButton");
            var $quoting = $("#quoting");
            var $addQuoteAirline = $("#addQuoteAirline");
            var $selectAllQuote =$("#selectAllQuote");
            var $select = $("input[name='select']");
            var $selectAllAirplane = $("#selectAllAirplane");
            var $airplaneIds = $("input[name='airplaneIds']");
            var $submit = $(".submit");
            var $load_mark_box = $("#load_mark_box");
            var $createValetOrder = $("#createValetOrder");
            var $shutdown = $("#shutdown");
            var $telephone = $("#telephone");
            var selectTr;
        [@flash_message /]




            // 下单
            $submit.click(function() {
                $(".dialogOverlay").show();
                $load_mark_box.show();
                $("#flightPlanId").val($(this).attr("val"));
                selectTr = $(this).closest("tr");
//                $(".inputSelect").attr("disabled",false);
//                $("input[type='text']").attr("readonly", false);
//                $listForm.attr("action", "createValetOrder.jhtml");
//                $listForm.submit();
//                $(".inputSelect").attr("disabled","disabled");
//                $("input[type='text']").attr("readonly", "readonly");
                return false;
            });
            // 确认下单
            $createValetOrder.click(function() {
                if($telephone.val() == "" || $telephone.val() == undefined){
                    alert("请输入客户的手机号");
                    return false;
                }
                selectTr.siblings().remove();
                $(".inputSelect").attr("disabled",false);
                $("input[type='text']").attr("readonly", false);
                $listForm.attr("action", "createValetOrder.jhtml");
                $listForm.submit();
                $(".inputSelect").attr("disabled","disabled");
                $("input[type='text']").attr("readonly", "readonly");
                return false;
            });

            $shutdown.click(function() {
                $(".dialogOverlay").hide();
                $load_mark_box.hide();
                return false;
            });

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

            // 进行报价
            $quoteButton.click(function() {
                var bool = true;
                var $takeoffTimes = $("input[name$='takeoffTime']")
                var $departureIds = $("input[name$='departureId']")
                var $passengerNums = $("input[name$='passengerNum']")
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
                var $destinationIds = $("input[name$='destinationId']")
                $.each($destinationIds, function(i, name){
                    if(name.value == null || name.value == "" || name.value ==undefined ){
                        alert("航段的目的机场不能为空！");
                        bool = false;
                        return false;
                    }
                });
                if(bool == false){
                    return;
                }
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
                $.each($passengerNums, function(i, name){
                    if(name.value == null || name.value == "" || name.value ==undefined ){
                        alert("航段的乘客数不能为空");
                        bool = false;
                        return false;
                    }
                    if(isNaN(name.value)){
                        alert("乘客数请输入数字");
                        bool = false;
                        return false;
                    }
                });
                if(bool == false){
                    return;
                }

                // 航段数
                var $lineNum = $flightList[0].rows.length - 1;
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
                $("#regNo").val($search_regNo.val());
                $listTable.find("tr").remove();
                $listForm.submit();
                return false;

            });

            $listForm.live("submit",function(){
                $("#regNo").val($search_regNo.val());
            });


            // 添加航段
            $addFlight.click(function() {
                var $addTr = $("#addTr").clone();
                var $recordNum =$flightList[0].rows.length - 1;
                $addTr.find("input[name='departureIdAdd']").attr("name","quoteAirlineList[" + $recordNum + "].departureId");
                $addTr.find("input[name='departureAdd']").attr("name","departureName");
                $addTr.find("input[name='destinationIdAdd']").attr("name","quoteAirlineList[" + $recordNum + "].destinationId");
                $addTr.find("input[name='destinationAdd']").attr("name","destinationName");
                $addTr.find("input[name='takeoffTimeAdd']").attr("name","quoteAirlineList[" + $recordNum + "].takeoffTime");
                $addTr.find("input[name='passengerNumAdd']").attr("name","quoteAirlineList[" + $recordNum + "].passengerNum");
                $addTr.find("#departureIdAdd").attr("id","departureId_" + $recordNum);
                $addTr.find("#departureAdd").attr("id","departure_" + $recordNum);
                $addTr.find("#destinationIdAdd").attr("id","destinationId_" + $recordNum);
                $addTr.find("#destinationAdd").attr("id","destination_" + $recordNum);
                $addTr.find("#takeoffTimeAdd").attr("id","takeoffTime_" + $recordNum);
                $addTr.find("#passengerNumAdd").attr("id","passengerNum_" + $recordNum);

                $addTr.attr("style","");
                $flightList.append($addTr);
                var num = $recordNum - 1;
                $("#departureId_" + $recordNum).val($("#destinationId_" +num).val());
                $("#departure_" + $recordNum).val($("#destination_" +num).val());

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

            // 删除航段
            $airlineDelete.live("click", function(){
                $(this).closest("tr").remove();
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
                var $ul = $this.closest("ul");

                var $dest = $("#" + $this.attr("name"));
                if ($this.hasClass("checked")) {
                    $dest.val("");
                    $ul.find("a").removeClass("checked");;
                    $ul.find("a :first").addClass("checked");
                } else {
                    $ul.find("a").removeClass("checked");;
                    $this.addClass("checked");
                    $dest.val($this.attr("val"));
                }
                var $menuWrap = $this.closest("div.menuWrap");
                var $popupMenu = $menuWrap.children("div.popupMenu");
                $popupMenu.hide();
                return false;
            });

            // 多程
            $moreWay.click(function(){
                $addFlight.show();
                var $recordNum =$flightList[0].rows.length - 1;
                for(var i =$recordNum; i>1;i--){
                    $flightList[0].rows[i].remove();
                }
                var $addTr = $("#addTr").clone();
                $addTr.find("input[name='departureIdAdd']").attr("name","quoteAirlineList[1].departureId");
                $addTr.find("input[name='departureAdd']").attr("name","departureName");
                $addTr.find("input[name='destinationIdAdd']").attr("name","quoteAirlineList[1].destinationId");
                $addTr.find("input[name='destinationAdd']").attr("name","destinationName");
                $addTr.find("input[name='takeoffTimeAdd']").attr("name","quoteAirlineList[1].takeoffTime");
                $addTr.find("input[name='passengerNumAdd']").attr("name","quoteAirlineList[1].passengerNum");
                $addTr.find("#departureIdAdd").attr("id","departureId_1");
                $addTr.find("#departureAdd").attr("id","departure_1");
                $addTr.find("#destinationIdAdd").attr("id","destinationId_1");
                $addTr.find("#destinationAdd").attr("id","destination_1");
                $addTr.find("#takeoffTimeAdd").attr("id","takeoffTime_1");
                $addTr.find("#passengerNumAdd").attr("id","passengerNum_1");
                $addTr.find(".airlineDelete").remove();
                $addTr.attr("style","");
                $flightList.append($addTr);

                $("#departureId_1").val($("#destinationId_0").val());
                $("#departure_1").val($("#destination_0").val());

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

            // 调机
            $transfer.click(function(){
                $addFlight.hide();
                $airlineDelete.hide();
                var $recordNum =$flightList[0].rows.length - 1;
                for(var i =$recordNum; i>1;i--){
                    $flightList[0].rows[i].remove();
                }
            });

            // 单程
            $oneWay.click(function(){
                $addFlight.hide();
                $airlineDelete.hide();
                var $recordNum =$flightList[0].rows.length - 1;
                for(var i =$recordNum; i>1;i--){
                    $flightList[0].rows[i].remove();
                }
            });

            // 往返
            $returnWay.click(function(){
                $addFlight.hide();
                var $recordNum =$flightList[0].rows.length - 1;
                for(var i =$recordNum; i>1;i--){
                    $flightList[0].rows[i].remove();
                }

                var $addTr = $("#addTr").clone();
                $addTr.find("input[name='departureIdAdd']").attr("name","quoteAirlineList[1].departureId");
                $addTr.find("input[name='departureAdd']").attr("name","quoteAirlines[1].departure");
                $addTr.find("input[name='destinationIdAdd']").attr("name","quoteAirlineList[1].destinationId");
                $addTr.find("input[name='destinationAdd']").attr("name","quoteAirlines[1].destination");
                $addTr.find("input[name='takeoffTimeAdd']").attr("name","quoteAirlineList[1].takeoffTime");
                $addTr.find("input[name='passengerNumAdd']").attr("name","quoteAirlineList[1].passengerNum");
                $addTr.find("#departureIdAdd").attr("id","departureId_1");
                $addTr.find("#departureAdd").attr("id","departure_1");
                $addTr.find("#destinationIdAdd").attr("id","destinationId_1");
                $addTr.find("#destinationAdd").attr("id","destination_1");
                $addTr.find("#takeoffTimeAdd").attr("id","takeoffTime_1");
                $addTr.find("#passengerNumAdd").attr("id","passengerNum_1");
                $addTr.find(".airlineDelete").remove();
                $addTr.attr("style","");
                $flightList.append($addTr);

                $("#departureId_1").val($("#destinationId_0").val());
                $("#departure_1").val($("#destination_0").val());
                $("#departure_1").attr("readonly","readonly");
                $("#destinationId_1").val($("#departureId_0").val());
                $("#destination_1").val($("#departure_0").val());
                $("#destination_1").attr("readonly","readonly");

            });

            // 手机号查询客户
            $telephone.autocomplete("${base}/admin/customer/search_customer.jhtml", {
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
                    $("#customerTable").find("input").attr("readonly",false);
                    $("#customerTable").find("select").attr("disabled",false);
                    $("#customerId").val("");
                    $("#realName").val("");
                    $("#sex").val("");
                    $("#email").val("");
                    $("#companys").val("");
                    $("#nationality").val("");
                    $("#identityNo").val("");
                    $("#identityExpiryStart").val("");
                    $("#identityExpiryEnd").val("");
                    $("#birthDate").val("");
                    $("#business").val("");
                    $("#identityType").val("");
                    if ($.inArray(item.telephone, name) < 0) {
                        return '<h4 title="' + item.telephone + '">' + item.telephone + '<\/h4>'
                                + '<span style="color: #999999">'  +'姓名：'+ '<\/span>' + item.realName  + '<\/br>'
                                ;
                    } else {
                        return false;
                    }
                }
            }).result(function(event, item) {
                $(this).val(item.telephone);
                $("#customerId").val(item.id);
                $("#realName").val(item.realName);
                $("#sex").val(item.sex);
                $("#email").val(item.email);
                $("#companys").val(item.companys);
                $("#nationality").val(item.nationality);
                $("#identityNo").val(item.identityNo);
                $("#identityExpiryStart").val(item.identityExpiryStart);
                $("#identityExpiryEnd").val(item.identityExpiryEnd);
                $("#birthDate").val(item.birthDate);
                $("#business").val(item.business);
                $("#identityType").val(item.identityType);
                $("#customerTable").find("input").attr("readonly","readonly");
                $("#customerTable").find("select").attr("disabled","disabled");
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
        .inputSelect{
            border: none;
            -moz-appearance: none;
            -webkit-appearance:none;
            appearance:none;
        }
        #load_mark_box {
            width: 650px;
            height: 300px;
            display: block;
            top: 20%;
            left: 15%;
            position: fixed;
            background-color: #ffffff;
            border: 1px solid #7f7f7f;
            display: none;
            z-index: 101;
        }
    </style>
</head>

<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 代客订单
</div>
<form id="listForm" action="valetList.jhtml" method="get">
    <input type="hidden" id="companyId" name="companyId" value="${companyId}" />
    <input type="hidden" id="type" name="type" value="${type}" />
    <input type="hidden" id="flightPlanId" name="flightPlanId" value="" />
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
                    <a href="javascript:;" name="type" val=""[#if type== ""] class="checked"[/#if]>全部</a>
                </li>
            [#list planeTypes as planeType]
                <li class="separator">
                    <a href="javascript:;" name="type" val="${planeType.typeName}"[#if planeType.typeName == type] class="checked"[/#if]>${planeType.typeName}</a>
                </li>
            [/#list]
            </ul>
        </div>
    </div>
    <div class="menuWrap">
        <span style="height: 23px;margin-left: 4px">${message("Order.airplane.regNo")}</span>
        <input type="text" id="search_regNo" name="regNo" value="${regNo}" maxlength="200" style="height: 23px"/>
    </div>
    <div id="addQuote" class="hideDiv">
        <table class="list" >
            <tr>
                <td>
                    <input type="radio" name="planType" id="transfer" value="transfer" checked/>调机
                    <input type="radio" name="planType" id="oneWay" value="oneWay" [#if quote.planType == 'oneWay' ]checked[/#if]>任务-单程
                    <input type="radio" name="planType" id="returnWay" value="returnWay" [#if quote.planType == 'returnWay' ]checked[/#if]/>任务-往返
                    <input type="radio" name="planType" id="moreWay" value="moreWay" [#if quote.planType == 'moreWay' ]checked[/#if]/>任务-多程
                </td>
                <td></td>
                <td></td>
            </tr>
        </table>
        <table  class="list" id="flightList">
            <tr id="addTr" style="display: none" val="" >
                <td>
                    <span>出发地</span>
                    <input class="text addInput" type="hidden" id="departureIdAdd"  name="departureIdAdd" />
                    <input class="text search addInput" type="text" id="departureAdd"  name="departureAdd" val='addInput' />
                </td>
                <td>
                    <span>目的地</span>
                    <input class="text addInput" type="hidden" id="destinationIdAdd"  name="destinationIdAdd"  />
                    <input class="text search addInput" type="text" id="destinationAdd"  name="destinationAdd" val='addInput'  />
                </td>
                <td>
                    <span>起飞时间</span>
                    <span>
                        <input type="text" name="takeoffTimeAdd" id="takeoffTimeAdd"  class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});"/>
                    </span>
                </td>
                <td>
                    <span>乘客人数</span>
                    <input class="text addInput" type="text" id="passengerNumAdd"  name="passengerNumAdd" val='addInput' />
                    <span style="color: #003bb3;float: right;display: none" class="airlineDelete" > 删除&nbsp;&nbsp;</span>
                </td>
            </tr>
        [#if quote.quoteAirlineList?? && quote.quoteAirlineList?size > 0]
            [#list quote.quoteAirlineList as quoteAirline]
                <tr>
                    <td>
                        <span>出发地</span>
                        <input class="text" type="hidden" id="departureId_${quoteAirline_index}"  name="quoteAirlineList[${quoteAirline_index}].departureId" value="${quoteAirline.departureId}" />
                        <input class="text search" type="text" id="departure_${quoteAirline_index}"  name="departureName"  value="${quoteAirline.departure.name}" />
                    </td>
                    <td>
                        <span>目的地</span>
                        <input class="text" type="hidden" id="destinationId_${quoteAirline_index}"  name="quoteAirlineList[${quoteAirline_index}].destinationId"   value="${quoteAirline.destinationId}"/>
                        <input class="text search" type="text" id="destination_${quoteAirline_index}"  name="destinationName"  value="${quoteAirline.destination.name}" />
                    </td>
                    <td>
                        <span>起飞时间</span>
                    <span>
                        <input type="text" id="takeoffTime_${quoteAirline_index}"  name="quoteAirlineList[${quoteAirline_index}].takeoffTime" value="${quoteAirline.takeoffTime?string("yyyy-MM-dd HH:mm:ss")}"  class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});"/>
                    </span>
                    </td>
                    <td>
                        <span>乘客人数</span>
                        <input class="text" type="text" id="passengerNum_${quoteAirline_index}"  value="${quoteAirline.passengerNum}"   name="quoteAirlineList[${quoteAirline_index}].passengerNum"  />
                    </td>
                </tr>
            [/#list]
        [#else ]
            <tr>
                <td>
                    <span>出发地</span>
                    <input class="text" type="hidden" id="departureId_0"  name="quoteAirlineList[0].departureId" />
                    <input class="text search" type="text" id="departure_0"  name="departureName" />
                </td>
                <td>
                    <span>目的地</span>
                    <input class="text" type="hidden" id="destinationId_0"  name="quoteAirlineList[0].destinationId"  />
                    <input class="text search" type="text" id="destination_0"  name="destinationName"  />
                </td>
                <td>
                    <span>起飞时间</span>
                    <span>
                        <input type="text" id="takeoffTime_0"  name="quoteAirlineList[0].takeoffTime"  class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});"/>
                    </span>
                </td>
                <td>
                    <span>乘客人数</span>
                    <input class="text" type="text" id="passengerNum_0"  name="quoteAirlineList[0].passengerNum"  />
                </td>
            </tr>
        [/#if]
        </table>
        <div style="text-align: right;width: 100%;"><input type="button" class="button" id="addFlight" value="添加航段" style="display: none"/></div>
        <br/>
        <div class="bar">
            <div class="buttonWrap">
                <div class="menuWrap" style="margin-left: 10px">
                    <input type="button" class="button"  id="quoteButton" style="height: 29px" value="${message("admin.common.search")}" />
                </div>
            </div>
        </div>
    [#--<form id="quoteForm" action="quote.jhtml" method="get">--]
        <table id="listTable" class="list">
            <tr>
                <th>
                    <a href="javascript:;">订单类型</a>
                </th>
                <th>
                    <a href="javascript:;"  >${message("Order.airplane.type")}</a>
                </th>
                <th>
                    <a href="javascript:;"  >${message("Order.airplane.regNo")}</a>
                </th>
                <th>
                    <a href="javascript:;">航空公司</a>
                </th>
                <th>
                    <a href="javascript:;">${message("Order.Airline")}</a>
                </th>
                <th>
                    <a href="javascript:;" name="firstTakeoffTime">${message("Order.firstTakeoffTime")}</a>
                </th>
                <th>
                    <a href="javascript:;">座位数</a>
                </th>
                <th>
                    <a href="javascript:;" class="sort" name="paymentStatus">原价</a>
                </th>
                <th>
                    <span>${message("admin.common.handle")}</span>
                </th>
            </tr>
        [#list flightPlanPage.content as flightPlan]
            <tr>
                <td>
                    调机
                </td>
                <td>
                ${flightPlan.airplaneType}
                </td>
                <td>
                ${flightPlan.regNo}
                </td>
                <td>
                ${flightPlan.airplane.company.name}
                </td>
                <td>
                ${flightPlan.departure }  --> ${flightPlan.destination }
                </td>
                <td>
                    <span title="${flightPlan.takeoffTime?string("yyyy-MM-dd HH:mm:ss")}">${flightPlan.takeoffTime?string("yyyy-MM-dd HH:mm")}</span>
                </td>
                <td>
                ${flightPlan.airplane.capacity}
                </td>
                <td>
                ${flightPlan.specialprice?string(',###')}
                </td>
                <td>
                    <a href="javascript:;" class="submit" val="${flightPlan.id}">[下单]</a>
                </td>
            </tr>
        [/#list]
        [#list quotes as tempQuote]
            <tr>
                <input type="hidden" name="quotes[0].airplaneId" id="airplaneId_0" value="${tempQuote.airplaneId}"/>
                <input type="hidden" name="quotes[0].regNo" value="${tempQuote.regNo}"/>
                <input type="hidden" name="quotes[0].type" value="${tempQuote.type}"/>
                <input type="hidden" name="quotes[0].planType" value="${quote.planType}"/>
                <input type="hidden" name="quotes[0].takeoffTime" value="${quote.takeoffTime?string("yyyy-MM-dd HH:mm:ss")}"/>
                <input type="hidden" name="quotes[0].departureId" value="${tempQuote.departureId}"/>
                <input type="hidden" name="quotes[0].destinationId" value="${tempQuote.destinationId}"/>
                <input type="hidden" name="quotes[0].totalDay" value="${tempQuote.totalDay}"/>
                <td>
                    [#if quote.planType == 'transfer']
                        调机
                    [#else ]
                        任务
                    [/#if]
                </td>
                <td>
                ${tempQuote.type}
                </td>
                <td>
                ${tempQuote.regNo}
                </td>
                <td>
                ${tempQuote.airplane.company.name}
                </td>
                <td>
                    [#list tempQuote.quoteAirlineList as quoteAirline]
                        <input type="hidden" id="flyingCost_0_${quoteAirline_index}" value="${quoteAirline.flyingCost}"/>
                        <input type="hidden"  name="quotes[0].quoteAirlineList[${quoteAirline_index}].timeCost" value="${quoteAirline.timeCost}"/>
                        <input type="hidden"  name="quotes[0].quoteAirlineList[${quoteAirline_index}].isSpecial" value="${quoteAirline.isSpecial}"/>
                        <input type="hidden"  name="quotes[0].quoteAirlineList[${quoteAirline_index}].passengerNum" value="${quoteAirline.passengerNum}"/>
                        <input type="hidden"  name="quotes[0].quoteAirlineList[${quoteAirline_index}].specialType" value="${quoteAirline.specialType}"/>
                        <input type="hidden" id="isDelete_0_${quoteAirline_index}"  name="quotes[0].quoteAirlineList[${quoteAirline_index}].isDelete" value="false"/>
                        [#if quoteAirline.specialType??]
                        [#else ]
                            <span >
                                <input type="hidden" id="takeoffTime_0_${quoteAirline_index}" name="quotes[0].quoteAirlineList[${quoteAirline_index}].takeoffTime" value="${quoteAirline.takeoffTime?string("yyyy-MM-dd HH:mm:ss")}"/>
                                <select name="quotes[0].quoteAirlineList[${quoteAirline_index}].departureId" class="inputSelect" disabled="disabled" >
                                    <option  value="${quoteAirline.departureId}">${quoteAirline.departure.name}</option>
                                </select>
                                -->
                                <select name="quotes[0].quoteAirlineList[${quoteAirline_index}].destinationId" class="inputSelect" disabled="disabled">
                                    <option  value="${quoteAirline.destinationId}">${quoteAirline.destination.name}</option>
                                </select>
                                &nbsp;&nbsp;
                            ${quoteAirline.timeCost?number*60}分钟
                            </span>
                            [#if quoteAirline_has_next]
                                <br/>
                            [/#if]
                        [/#if]
                    [/#list]
                </td>
                <td>
                    [#list tempQuote.quoteAirlineList as quoteAirline]
                        [#if quoteAirline.specialType??]
                        [#else ]
                            <span  >
                                [#if quoteAirline.isSpecial == true][#else ] ${quoteAirline.takeoffTime?string("yyyy-MM-dd HH:mm")}[/#if]
                            </span>
                            [#if quoteAirline_has_next]
                                <br/>
                            [/#if]
                        [/#if]
                    [/#list]
                </td>
                <td>
                ${tempQuote.airplane.capacity}
                </td>
                <td>
                    <select name="quotes[0].totalAmount" id="totalAmount_0" class="inputSelect" disabled="disabled">
                        <option  value="${tempQuote.totalAmount}">${tempQuote.totalAmount?string(',###')}元</option>
                    </select>
                </td>

                <td>
                    <a href="javascript:;" class="submit">[下单]</a>
                </td>
                <input type="hidden" name="quotes[0].actualHours" value="${tempQuote.actualHours}" id="actualHours_0"/>
                <input type="hidden" name="quotes[0].lackHour" value="${tempQuote.lackHour}" id="lackHour_0" />
                <input type="hidden" name="quotes[0].stopDays" value="${tempQuote.stopDays}" id="totalAmount_0" /></td>
                <input type="hidden" name="quotes[0].totalHour" value="${tempQuote.totalHour}" id="totalHour_0"/></td>
                <input type="hidden" name="quotes[0].totalMaintenance" value="${tempQuote.totalHour}" id="totalMaintenance_0"/></td>
                <input type="hidden" name="quotes[0].lowestHour" value="${tempQuote.lowestHour}" id="lowestHour_0"/>
                <input type="hidden" name="quotes[0].lowestHour" value="${tempQuote.lowestHour}" id="lowestHour_0"/>
            </tr>
        [/#list]
        </table>
        <div id="load_mark_box">
            <h2 style="text-align: center;background-color: #63f666">客户信息</h2>
            <input type="hidden" name="customerId"  id="customerId" />
            <table class="input" id="customerTable">
                <tr>
                    <th>
                        <span class="requiredField">*</span>${message("customer.telephone")}:
                    </th>
                    <td>
                        <input type="text" id="telephone" name="customer.telephone" class="text"  maxlength="200" />
                    </td>
                    <th>
                    ${message("customer.realName")}:
                    </th>
                    <td>
                        <input type="text" id="realName" name="customer.realName" class="text"  maxlength="200" />
                    </td>
                </tr>
                <tr>
                    <th>
                    ${message("customer.sex")}:
                    </th>
                    <td>
                <span class="fieldSet">
                    <select  name="customer.sex" id="sex">
                    [#list sexs as sex]
                        <option value="${sex}">${message("customer.sex." + sex)}</option>
                    [/#list]
                    </select>
                </span>
                    </td>
                    <th>
                    ${message("customer.email")}:
                    </th>
                    <td>
                        <input type="text" id="email" name="customer.email" class="text" value="${customer.email}" maxlength="200" />
                    </td>
                </tr>
                <tr>
                    <th>
                    ${message("customer.companys")}:
                    </th>
                    <td>
                        <input type="text" id="companys" name="customer.companys" class="text" value="${customer.companys}" maxlength="200" />
                    </td>
                    <th>
                    ${message("customer.nationality")}:
                    </th>
                    <td>
                        <input type="text" id="nationality" name="customer.nationality" class="text" value="${customer.nationality}" maxlength="200" />
                    </td>
                </tr>
                <tr>
                    <th>
                    ${message("customer.identityType")}:
                    </th>
                    <td>
                        <select  name="customer.identityType" id="identityType">
                        [#list identityTypes as identityType]
                            <option value="${identityType}">${message("customer.identityType." + identityType)}</option>
                        [/#list]
                        </select>
                    </td>
                    <th>
                    ${message("customer.identityNo")}:
                    </th>
                    <td>
                        <input type="text" id="identityNo" name="customer.identityNo" class="text" value="${customer.identityNo}" maxlength="200" />
                    </td>
                </tr>
                <tr>
                    <th>
                    ${message("customer.identityExpiryStart")}:
                    </th>
                    <td>
                        <input type="text" id="identityExpiryStart" name="customer.identityExpiryStart" class="text Wdate" value="[#if customer.identityExpiryStart??]${customer.identityExpiryStart?string("yyyy-MM-dd")}[/#if]" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'identityExpiryEnd\')}'});" />
                    </td>
                    <th>
                    ${message("customer.identityExpiryEnd")}:
                    </th>
                    <td>
                        <input type="text" id="identityExpiryEnd" name="customer.identityExpiryEnd" class="text Wdate" value="[#if customer.identityExpiryEnd??]${customer.identityExpiryEnd?string("yyyy-MM-dd")}[/#if]" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'identityExpiryEnd\')}'});" />
                    </td>
                </tr>
                <tr>
                    <th>
                    ${message("customer.birthDate")}:
                    </th>
                    <td>
                        <input type="text" id="birthDate" name="customer.birthDate" class="text Wdate" value="[#if customer.birthDate??]${customer.birthDate?string("yyyy-MM-dd")}[/#if]" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'birthDate\')}'});" />
                    </td>
                    <th>
                    ${message("customer.business")}:
                    </th>
                    <td>
                        <input type="text" id="business" name="customer.business" class="text" value="${customer.business}" maxlength="200" />
                    </td>
                </tr>
                <tr>
                    <th>
                        &nbsp;
                    </th>
                    <td>
                        <input type="button" class="button" value="${message("admin.common.submit")}" id="createValetOrder" />
                        <input type="button" class="button" value="${message("admin.common.back")}" id="shutdown"/>
                    </td>
                </tr>
            </table>
        </div>

    [#--</form>--]
    </div>
[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
    [#include "/admin/include/pagination.ftl"]
[/@pagination]
</form>
<div class="dialogOverlay" style="display: none;"></div>
</body>

</html>