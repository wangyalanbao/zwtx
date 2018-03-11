<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.flightPlan.list")}</title>


    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.lSelect.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>

    <script type="text/javascript">
        $().ready(function() {

            var $listForm = $("#listForm");
            var $moreButton = $("#moreButton");
            var $filterSelect = $("#filterSelect");
            var $filterOption = $("#filterOption a");
            var $fulfill = $("#listTable a.fulfill");
            var $editPlanFulfill = $("#listTable a.editPlanFulfill");
            var $fulfillForm = $("#fulfillForm");
            var $editPlanCancel = $("#editPlanCancel");
            var $fulfillFlight = $("#fulfillFlight");
            var $editPlanFlight = $("#editPlanFlight");
            var $submitFlight = $("#submitFlight");
            var $cancelFlight = $("#cancelFlight");
            var $typeCarry = $("#typeCarry");
            var $typeTuning = $("#typeTuning");
            var $typeService = $("#typeService");
            var $editing = $(".editing");
            var $type = $("input[name='type']:checked");
            var $timezone = $("input[name='timezone']:checked");
            var $date = $("#date");
            var $isReal = $("#listTable a.isReal");

            var $delete = $("#listTable a.disabled");
            var $search = $(".search");
            var $flightPlanId ;

            var $methodSelect = $("#methodSelect");
            var $methodSelectB = $("#methodSelectB");
            var $methodSelectT = $("#methodSelectT");
            var $methodOption = $("#methodOption a");
            var $methodOptionB = $("#methodOptionB a");
            var $methodOptionT = $("#methodOptionT a");


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
                                    location.reload(true);
                                }
                            }
                        });
                    }
                });
                return false;
            });

            // 更改真实调机
            $isReal.click(function() {
                var $this = $(this);
                $.dialog({
                    type: "warn",
                    content: "确定要更改为真实调机么？",
                    onOk: function() {
                        $.ajax({
                            url: "updateTuning.jhtml",
                            type: "GET",
                            data: {id: $this.attr("val"),isReal:true,landingTime:$(this).closest("td").find("input").val()},
                            dataType: "json",
                            cache: false,
                            success: function(message) {
                                $.message(message);
                                if (message.type == "success") {
                                    location.reload(true);
                                }
                            }
                        });
                    }
                });
                return false;
            });


            // 添加航班计划
            $fulfill.click(function() {
                $fulfillFlight.show();
                $("#departureI").val("");
                $("#takeoffTime").val("");
                $("#landingTime").val("");
                $("#destinationI").val("");
                $("#actualTakeoffTime").val("");
                $("#actualLandingTime").val("");
                $("#flyingTime").val("");
                $flightPlanId = $(this).attr("val");
                $("#temp_date").val($(this).closest("td").find("span").attr("val"));

            });


            //选择任务类型  editing
            $typeCarry.live("click",function(){
                $editing.show();

            });
            $typeTuning.live("click",function(){
                $editing.show();

            });
            //任务类型为检修时全部隐藏
            $typeService.live("change",function(){
                $editing.hide();


            });

            // 提交完成信息
            $submitFlight.click(function() {
                if($("input[name='type']:checked").val() == ""){
                    alert("请选择任务类型！");
                    return;
                }
                if($("input[name='type']:checked").val() == "carry" ||$("input[name='type']:checked").val() == "tuning" ){

                    if( $("#departureIdAdd").val() == ""){
                        alert("请选择出发地！");
                        return;
                    }
                    if($("#destinationIdAdd").val() == ""){
                        alert("请选择降落地！");
                        return;
                    }
                    if($("#departureIdAdd").val() == $("#destinationIdAdd").val()){
                        alert("出发地和降落地不可相同！");
                        return;
                    }
                    if($("#takeoffTime").val() == ""){
                        alert("请输入出发时间！");
                        return;
                    }
                    if($("#landingTime").val() == ""){
                        alert("请输入降落时间！");
                        return;
                    }
                    var takeoff =  $("#takeoffTime").val();
                    var landing =  $("#landingTime").val();
                    takeoff = takeoff.replace(/-/g,'/');
                    landing = landing.replace(/-/g,'/');
                    var takeoffTime = new Date(takeoff);
                    var landingTime = new Date(landing);
                    $.ajax({
                        url: "add.jhtml",
                        type: "GET",
                        data:  {type:$("input[name='type']:checked").val(),flightPlanId:$flightPlanId, timezone:$("input[name='timezone']:checked").val(),departureI:$("#departureIdAdd").val(), destinationI:$("#destinationIdAdd").val(), takeoffTime:takeoffTime.getTime(), landingTime:landingTime.getTime(),actualTakeoffTime:$("#actualTakeoffTime").val(), actualLandingTime:$("#actualLandingTime").val(),flyingTime:$("#flyingTime").val()},
                        dataType: "json",
                        cache: false,
                        success: function(message) {
                            $.message(message);

                            $fulfillFlight.hide();
                            location.reload(true);
                        }
                    });
                } else{

                    var str =  $("#temp_date").val();
                    str = str.replace(/-/g,'/');
                    var date = new Date(str);

                    $.ajax({
                        url: "add.jhtml",
                        type: "GET",
                        data:  {type:$("input[name='type']:checked").val(),flightPlanId:$flightPlanId,takeoffTime:date.getTime(), landingTime:date.getTime()},
                        dataType: "json",
                        cache: false,
                        success: function(message) {
                            $.message(message);

                            $fulfillFlight.hide();
                            location.reload(true);
                        }
                    });
                }


            });

            // 关闭提交完成输入框
            $cancelFlight.click(function() {
                $fulfillFlight.hide();
                location.reload(true);
            });

            //把时间戳转换为Date
            function getLocalTime(nS) {
                return new Date(parseInt(nS)).toLocaleString().replace(/下午/g, " ").replace(/上午/g, " ").replace(/年|月/g, "-").replace(/日/g, " ");
            }
            // 编辑航班计划
            $editPlanFulfill.click(function() {
                $editPlanFlight.show();
                $.ajax({
                    url: "editU.jhtml",
                    type: "GET",
                    data:  {flightPlanId:$(this).attr("val")},
                    dataType: "json",
                    cache: false,
                    success: function(data) {
                        $("#eflightPlanId").attr("value",data.flightPlanId);
                        $("input[name='type']:checked").val(data.typePlan);
                        if( $("input[name='type']:checked").val()=="service"){
                            $("#etypeService").attr("checked","checked");
                            $editing.hide();
                        }else if($("input[name='type']:checked").val()=="tuning"){
                            $("#etypeTuning").attr("checked","checked");
                        }else{
                            $("#etypeCarry").attr("checked","checked");
                        }
                        $("input[name='type']:checked").attr("value",data.typePlan);
                        $("#edepartureIdAdd").attr("value",data.departureI);
                        $("#edepartureAdd").attr("value",data.departureName);
                        $("#edestinationIdAdd").attr("value",data.destinationI);
                        $("#edestinationAdd").attr("value",data.destinationName);
                        $("#etakeoffTime").attr("value",getLocalTime(data.takeoffTime));
                        $("#elandingTime").attr("value",getLocalTime(data.landingTime));
                        $("#eactualTakeoffTime").attr("value",getLocalTime(data.actualTakeoffTime));
                        $("#eactualLandingTime").attr("value",getLocalTime(data.actualLandingTime));
                        $("#eflyingTime").attr("value",data.flyingTime);

                    }
                });
                $flightPlanId = $(this).attr("val");
                $("#temp_date").val($(this).closest("td").find("span").attr("val"));

            });
            // 关闭编辑航班计划
            $editPlanCancel.click(function() {
                $editPlanFlight.hide();
                location.reload(true);
            });

            // 提交编辑航班计划
            $("#editSubmitFlight").click(function() {
                if($("input[name='type']:checked").val() == ""){
                    alert("请选择任务类型！");
                    return;
                }
                if($("input[name='type']:checked").val() == "carry" ||$("input[name='type']:checked").val() == "tuning" ){

                    if( $("#edepartureIdAdd").val() == ""){
                        alert("请选择出发地！");
                        return;
                    }
                    if($("#edestinationIdAdd").val() == ""){
                        alert("请选择降落地！");
                        return;
                    }
                    if($("#edepartureIdAdd").val() == $("#edestinationIdAdd").val()){
                        alert("出发地和降落地不可相同！");
                        return;
                    }
                    if($("#etakeoffTime").val() == ""){
                        alert("请输入出发时间！");
                        return;
                    }
                    if($("#elandingTime").val() == ""){
                        alert("请输入降落时间！");
                        return;
                    }
                    var takeoff =  $("#etakeoffTime").val();
                    var landing =  $("#elandingTime").val();
                    takeoff = takeoff.replace(/-/g,'/');
                    landing = landing.replace(/-/g,'/');
                    var takeoffTime = new Date(takeoff);
                    var landingTime = new Date(landing);
                    $.ajax({
                        url: "editPlan.jhtml",
                        type: "GET",
                        data:  {type:$("input[name='type']:checked").val(),flightPlanId:$("#eflightPlanId").val(), timezone:$("input[name='timezone']:checked").val(),departureI:$("#edepartureIdAdd").val(), destinationI:$("#edestinationIdAdd").val(), takeoffTime:takeoffTime.getTime(), landingTime:landingTime.getTime(),actualTakeoffTime:$("#eactualTakeoffTime").val(), actualLandingTime:$("#eactualLandingTime").val(),flyingTime:$("#eflyingTime").val()},
                        dataType: "json",
                        cache: false,
                        success: function(message) {
                            $.message(message);

                            $fulfillFlight.hide();
                            location.reload(true);
                        }
                    });
                } else{

                    var str =  $("#temp_date").val();
                    str = str.replace(/-/g,'/');
                    var date = new Date(str);

                    $.ajax({
                        url: "editPlan.jhtml",
                        type: "GET",
                        data:  {type:$("input[name='type']:checked").val(),flightPlanId:$("#eflightPlanId").val(),takeoffTime:date.getTime(), landingTime:date.getTime()},
                        dataType: "json",
                        cache: false,
                        success: function(message) {
                            $.message(message);

                            $fulfillFlight.hide();
                            location.reload(true);
                        }
                    });
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

            });

            // 选择型号$methodSelectB $methodSelectT
            $methodSelectB.mouseover(function() {
                var $this = $(this);
                var offset = $this.offset();
                var $menuWrap = $this.closest("div.menuWrap");
                var $popupMenu = $menuWrap.children("div.popupMenu");
                $popupMenu.css({left: offset.left, top: offset.top + $this.height() + 2}).show();
                $menuWrap.mouseleave(function() {
                    $popupMenu.hide();
                });
            });
            $methodOptionB.click(function() {
                var $this = $(this);
                var $dest = $("#brandId");
                if ($this.hasClass("checked")) {
                    $dest.val("");
                } else {
                    $dest.val($this.attr("val"));
                }
                $listForm.submit();
                return false;
            });
            // 选择类型
            $methodSelectT.mouseover(function() {
                var $this = $(this);
                var offset = $this.offset();
                var $menuWrap = $this.closest("div.menuWrap");
                var $popupMenu = $menuWrap.children("div.popupMenu");
                $popupMenu.css({left: offset.left, top: offset.top + $this.height() + 2}).show();
                $menuWrap.mouseleave(function() {
                    $popupMenu.hide();
                });
            });
            $methodOptionT.click(function() {
                var $this = $(this);
                var $dest = $("#typeI");
                if ($this.hasClass("checked")) {
                    $dest.val("");
                } else {
                    $dest.val($this.attr("val"));
                }
                $listForm.submit();
                return false;
            });
            // 选择公司
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

            //
            $methodOption.click(function() {
                var $this = $(this);
                var $dest = $("#companyId");
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
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.flightPlan.list")} <span>(${message("admin.page.total", page.total)})</span>
</div>
<form id="listForm" action="list.jhtml" method="get">
    <input type="hidden" id="departureId" name="departureId" value="${departureId}" />
    <input type="hidden" id="destinationId" name="destinationId" value="${destinationId}" />
    <input type="hidden" id="brandId" name="brandId" value="${brandId}" />
    <input type="hidden" id="typeI" name="typeI" value="${typeI}" />
    <input type="hidden" id="companyId" name="companyId" value="${companyId}" />
    <input type="hidden" id="temp_date"  name="temp_date" value="" />
    <div class="bar">
        <div class="buttonWrap">
            <a href="javascript:;" id="refreshButton" class="iconButton">
                <span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
            </a>
            <div class="menuWrap">
                <a href="javascript:;" id="methodSelectB" class="button">
                ${message("airplane.brand")}<span class="arrow">&nbsp;</span>
                </a>
                <div class="popupMenu">
                    <ul id="methodOptionB" class="check">
                        <li >
                            <a href="javascript:;" name="brandId" val=""[#if brandId== ""] class="checked"[/#if]>${message("Order.all")}</a>
                        </li>
                    [#list planeBrands as planeBrand]
                        <li class="separator">
                            <a href="javascript:;" name="brandId" val="${planeBrand.id}"[#if planeBrand.id == brandId] class="checked"[/#if]>${planeBrand.name}</a>
                        </li>
                    [/#list]
                    </ul>
                </div>
            </div>
            <div class="menuWrap">
                <a href="javascript:;" id="methodSelectT" class="button">
                ${message("airplane.type")}<span class="arrow">&nbsp;</span>
                </a>
                <div class="popupMenu">
                    <ul id="methodOptionT" class="check">
                        <li >
                            <a href="javascript:;" name="typeI" val=""[#if typeI== ""] class="checked"[/#if]>${message("Order.all")}</a>
                        </li>
                    [#list planeTypes as type]
                        <li class="separator">
                            <a href="javascript:;" name="typeI" val="${type.id}"[#if type.id == typeI] class="checked"[/#if]>${type.typeName}</a>
                        </li>
                    [/#list]
                    </ul>
                </div>
            </div>

            <div class="menuWrap">
                <a href="javascript:;" id="methodSelect" class="button">
                ${message("airplane.company")}<span class="arrow">&nbsp;</span>
                </a>
                <div class="popupMenu">
                    <ul id="methodOption" class="check">
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
            <a href="javascript:;" id="moreButton" class="button">
                计划时间
                <input type="text" id="date" name="date" class="text Wdate" value="${date}" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});"/>
                <span onclick="$('#listForm').submit();">&nbsp;&nbsp;搜索</span>
            </a>
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
                        <a href="javascript:;"[#if page.searchProperty == "regNo"] class="current"[/#if] val="regNo">注册号</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <table id="listTable" class="list">
        <tr>

            <th>
                <a href="javascript:;" class="sort" name="regNo">${message("flightPlan.regNo")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="company">${message("flightPlan.company")}</a>
            </th>
            <th>
                <a href="javascript:;" class="sort" name="company"> ${message("airplane.type")}</a>
            </th>
        [#list dates as date]
            <th>
                <span title="${date?string("yyyy-MM-dd")}">${date?string("yyyy-MM-dd E")}</span>
            </th>
        [/#list]
        </tr>
    [#list page.content as flightPlan]
        <tr>
            <input type="hidden" name="ids" value="${flightPlan.id}" />

            <td>
            [#--注册号	所在机场--]
            ${flightPlan.regNo}
                <br>
            ${flightPlan.airportId.name}
            </td>
            <td>
            ${flightPlan.company.name}
            </td>
            <td>
            [#--飞机品牌	飞机型号--]
            ${flightPlan.brand}
                <br>
            ${flightPlan.type}
            </td>
            [#list dates as date]
                <td>
                    [#list flightPlan.flightPlans as flightPlan]
                        [#if flightPlan.takeoffTime?string("yyyy-MM-dd") = date?string("yyyy-MM-dd")]
                            <a href="#" id="editPlanFulfill" class="button editPlanFulfill" style="float: left;"val="${flightPlan.id}">
                            [#--<a href="editPlan.jhtml?id=${flightPlan.id}" id="editPlanFulfill" class="button editPlanFulfill" style="float: left;">--]
                                [#if flightPlan.type = "service"]
                                    维修
                                [#elseif flightPlan.type = "tuning"]
                                    调机
                                [#elseif flightPlan.type = "carry"]
                                    载客
                                [/#if]
                            </a>
                            <a href="javascript:;" id="deleteButton" class="iconButton disabled delete" style="float: right;" val="${flightPlan.id}">
                                <span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
                            </a>
                            <br>
                            [#if flightPlan.type = "service"]
                            ${flightPlan.airplane.airportId.name}<br>
                            [#elseif flightPlan.type = "tuning"]
                            ${flightPlan.departureId.name}-${flightPlan.destinationId.name}
                                <a href="javascript:;" id="isReal" class="isReal" style="" val="${flightPlan.id}">
                                    <input type="hidden" value="${flightPlan.landingTime?string("yyyy-MM-dd")}"/>
                                    [确认调机]
                                </a><br>
                            [#elseif flightPlan.type = "carry"]
                            ${flightPlan.departureId.name}-${flightPlan.destinationId.name}<br>
                            [/#if]

                        [#--${flightPlan.takeoffTime?string("yyyy-MM-dd HH:mm:ss")}至<br>--]
                        [#--${flightPlan.landingTime?string("yyyy-MM-dd HH:mm:ss")}<br>--]
                        [/#if]
                    [/#list]

                    <a href="#" id="fulfill" class="fulfill" val="${flightPlan.id}">+ 添加计划</a>
                    <span type="hidden" val="${date?string("yyyy-MM-dd")}" id="getDate${product_index}" class="getDate"/>

                </td>
            [/#list]

        </tr>
    [/#list]
    </table>

[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
    [#include "/admin/include/pagination.ftl"]
[/@pagination]
[#--确定完成航班计划--]
    <div id="fulfillFlight" style="display: none; position: fixed;background-color: #ffffff;top:14%;left: 25%;border:2 solid #ddd; z-index: 100;">
        <h2 style="text-align:center;background-color: #63f666">添加计划</h2>
        <form id="fulfillForm" >
            <input type="hidden" name="flightPlanId" id="flightPlanId" value="${flightPlan.id}" />

            <div style="height: 360px; overflow-x: hidden; overflow-y: auto;width: 400px;">
                <table class="input" >
                    <tr >
                        <th>
                            <span class="requiredField">*</span>任务类型:
                        </th>
                        <td>
                            <label>
                                <input type="radio" id="typeCarry"  class="typeCarry" name="type" value="carry"  checked="checked" />任务
                                <input type="radio" id="typeTuning"  class="typeTuning" name="type" value="tuning"/>调机
                                <input type="radio" id="typeService"  class="typeService" name="type" value="service" />检修
                            </label>

                        </td>
                    </tr>

                    <tr  class="editing">
                        <th>
                            <span class="requiredField">*</span>${message("flightPlan.departureI")}
                        </th>
                        <td>
                            <input class="text addInput" type="hidden" id="departureIdAdd"  name="departureIdAdd" />
                            <input class="text search addInput" type="text" id="departureAdd"  name="departureAdd" val='addInput' />
                        </td>
                    </tr>
                    <tr  class="editing">
                        <th>
                            <span class="requiredField">*</span>${message("flightPlan.destinationI")}
                        </th>
                        <td>
                            <input class="text addInput" type="hidden" id="destinationIdAdd"  name="destinationIdAdd"  />
                            <input class="text search addInput" type="text" id="destinationAdd"  name="destinationAdd" val='addInput'  />

                        </td>
                    </tr >
                    <tr class="editing">
                        <th >
                            <span class="requiredField">*</span>时间类型:
                        </th>
                        <td>
                            <label >
                                <input type="radio" name="timezone"  value="beijing"  checked="checked" />北京时间
                                <input type="radio" name="timezone"  value="utc"/>UTC
                            </label>

                        </td>
                    </tr>
                    <tr  class="editing">
                        <th>
                            <span class="requiredField">*</span>计划起飞时间：
                        </th>
                        <td>
                            <input type="text" name="takeoffTime" id="takeoffTime"  class="text Wdate"  onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});" />
                        </td>
                    </tr>
                    <tr  class="editing">
                        <th>
                            <span class="requiredField">*</span>计划降落时间：
                        </th>
                        <td>

                            <input type="text" name="landingTime" id="landingTime"  class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '#F{$dp.$D(\'takeoffTime\')}'});" />
                        </td>
                    </tr>
                    <tr  class="editing">
                        <th>
                        ${message("flightPlan.actualTakeoffTime")}
                        </th>
                        <td>
                            <input type="text" name="actualTakeoffTime" id="actualTakeoffTime"  class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});" />
                        </td>
                    </tr>
                    <tr  class="editing">
                        <th>
                        ${message("flightPlan.actualLandingTime")}
                        </th>
                        <td>

                            <input type="text" name="actualLandingTime" id="actualLandingTime"  class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});" />
                        </td>
                    </tr>

                    <tr  class="editing">
                        <th>
                            飞行时间(h):
                        </th>
                        <td>
                            <input type="text" id="flyingTime" name="flyingTime" class="text" maxlength="200" />
                        </td>
                    </tr>

                    <tr>
                        <th>
                            &nbsp;
                        </th>
                        <td>
                            <input type="button" class="button submitFlight" id="submitFlight" value="${message("admin.common.submit")}" />
                            <input type="button" class="button" id="cancelFlight" value="${message("admin.common.back")}" />
                        </td>
                    </tr>
                </table>

            </div>
        </form>
    </div>
[#--编辑航班计划--]
    <div id="editPlanFlight" style="display: none; position: fixed;background-color: #ffffff;top:14%;left: 25%;border:2 solid #ddd; z-index: 100;">
        <h2 style="text-align:center;background-color: #63f666">添加计划</h2>
        <form id="editPlanFulfillForm" >
            <input type="hidden" name="flightPlanId" id="eflightPlanId" value="" />

            <div style="height: 360px; overflow-x: hidden; overflow-y: auto;width: 400px;">
                <table class="input" >
                    <tr >
                        <th>
                            <span class="requiredField">*</span>任务类型:
                        </th>
                        <td>
                            <label>
                                <input type="radio" id="etypeCarry"  class="typeCarry" name="type" value="carry"  checked="checked" />任务
                                <input type="radio" id="etypeTuning"  class="typeTuning" name="type" value="tuning"/>调机
                                <input type="radio" id="etypeService"  class="typeService" name="type" value="service" />检修
                            </label>

                        </td>
                    </tr>

                    <tr  class="editing">
                        <th>
                            <span class="requiredField">*</span>${message("flightPlan.departureI")}
                        </th>
                        <td>
                            <input class="text addInput" type="hidden" id="edepartureIdAdd"  name="departureIdAdd" value="" />
                            <input class="text search addInput" type="text" id="edepartureAdd"  name="departureAdd" val='addInput' value="" />
                        </td>
                    </tr>
                    <tr  class="editing">
                        <th>
                            <span class="requiredField">*</span>${message("flightPlan.destinationI")}
                        </th>
                        <td>
                            <input class="text addInput" type="hidden" id="edestinationIdAdd"  name="destinationIdAdd"  />
                            <input class="text search addInput" type="text" id="edestinationAdd"  name="destinationAdd" val='addInput'  />

                        </td>
                    </tr >
                    <tr class="editing">
                        <th >
                            <span class="requiredField">*</span>时间类型:
                        </th>
                        <td>
                            <label >
                                <input type="radio" name="timezone"  value="beijing"  checked="checked" />北京时间
                                <input type="radio" name="timezone"  value="utc"/>UTC
                            </label>

                        </td>
                    </tr>
                    <tr  class="editing">
                        <th>
                            <span class="requiredField">*</span>计划起飞时间：
                        </th>
                        <td>
                            <input type="text" name="takeoffTime" id="etakeoffTime"  class="text Wdate"  onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});" />
                        </td>
                    </tr>
                    <tr  class="editing">
                        <th>
                            <span class="requiredField">*</span>计划降落时间：
                        </th>
                        <td>

                            <input type="text" name="landingTime" id="elandingTime"  class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '#F{$dp.$D(\'takeoffTime\')}'});" />
                        </td>
                    </tr>
                    <tr  class="editing">
                        <th>
                        ${message("flightPlan.actualTakeoffTime")}
                        </th>
                        <td>
                            <input type="text" name="actualTakeoffTime" id="eactualTakeoffTime"  class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});" />
                        </td>
                    </tr>
                    <tr  class="editing">
                        <th>
                        ${message("flightPlan.actualLandingTime")}
                        </th>
                        <td>

                            <input type="text" name="actualLandingTime" id="eactualLandingTime"  class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});" />
                        </td>
                    </tr>

                    <tr  class="editing">
                        <th>
                            飞行时间(h):
                        </th>
                        <td>
                            <input type="text" id="flyingTime" name="eflyingTime" class="text" maxlength="200" />
                        </td>
                    </tr>

                    <tr>
                        <th>
                            &nbsp;
                        </th>
                        <td>
                            <input type="button" class="button editSubmitFlight" id="editSubmitFlight" value="${message("admin.common.submit")}" />
                            <input type="button" class="button" id="editPlanCancel" value="${message("admin.common.back")}" />
                        </td>
                    </tr>
                </table>

            </div>
        </form>
    </div>
</form>
</body>
</html>