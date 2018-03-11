<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.airplane.edit")}</title>

    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <link href="${base}/resources/admin/css/jcrop/imgareaselect-default.css" rel="stylesheet" type="text/css" />

    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jcrop/jquery.imgareaselect.pack.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common2.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
    <style type="text/css">
        .specificationSelect {
            height: 100px;
            padding: 5px;
            overflow-y: scroll;
            border: 1px solid #cccccc;
        }

        .specificationSelect li {
            float: left;
            min-width: 150px;
            _width: 200px;
        }
        a.deleteProductImage{
            display: none;
        }
        #cutImageTable tr:last-child td .deleteProductImage{
            display: block !important;
        }
    </style>
    <script type="text/javascript">
        $().ready(function() {

            var $inputForm = $("#inputForm");
            var $fieldSet = $(".fieldSet");
            var $typeIds = $("#typeIds");

            var $cutImageTable = $("#cutImageTable");
            var $productImageTable = $("#productImageTable");
            var $addProductImage = $("#addProductImage");
            var $deleteProductImage = $("a.deleteProductImage");
            var $editProductImage = $("a.editProductImage");
            var airplaneImageIndex = ${(airplane.airplaneImages?size)!"0"};

            var $browserButton = $("#browserButton");
            var $uploadButton = $("#uploadButton");
            var $titles = ["外观图片","内舱图片","座舱布局图片","其他图片"];
            var $search = $(".search");
            var isEdit = false;
            var selectTr;

        [@flash_message /]

            $browserButton.browser();
            $editProductImage.browser();

            var ias = $('#imgCrop').imgAreaSelect({
                handles: true,
                aspectRatio:"4:3",
                movable:true,
                onSelectEnd: preview
            });

            function preview(img, selection) {
                if (!selection.width || !selection.height)
                    return;
                var x1=selection.x1;
                var y1=selection.y1;
                var x2=selection.x2;
                var y2=selection.y2;
                var w=selection.width;
                var h=selection.height;
                var imagepath = $('#imgCrop')[0].src;
                $uploadButton.click( function() {
                    $.dialog({
                        type: "warn",
                        content: "确认上传剪裁后的图片么?",
                        ok: message("admin.dialog.ok"),
                        cancel: message("admin.dialog.cancel"),
                        onOk: function() {
                            $.ajax({
                                url: "cutImage.jhtml",
                                type: "GET",
                                data: { x1: x1, y1: y1,x2: x2, y2: y2, w: w, h: h, imageIndex:airplaneImageIndex, path:imagepath },
                                dataType: "json",
                                cache: false,
                                success: function(message) {
                                    $.message(message);
                                    if (message.type == "success") {
                                        // 编辑的情况
                                        if(isEdit == true){
                                            selectTr.find("td:first input").val(message.content);
                                            selectTr.find("td:first a").hide();
                                            var input =  '<input style="width:100%"  readonly="readonly" type="text" class="productImageFile" class="text" value='+ message.content +' \/>';
                                            selectTr.find("td:first").append(input);
                                            isEdit = false;
                                            return;
                                        }
                                        var count = $cutImageTable[0].rows.length - 1;
                                        var title;
                                        if(count >= 4){
                                            title =  $titles[3]
                                        }else{
                                            title =  $titles[count];
                                        }
                                        var order = Number(count) + 1;
                                    [@compress single_line = true]
                                        var trHtml =
                                                '<tr>
                                                <td width ="35%">
                                    <input style="width:100%"  readonly="readonly" type="text" name="airplaneImages[' + airplaneImageIndex + '].source" class="productImageFile" class="text" value='+ message.content +' \/>
                                    <\/td>
                                    <td  width ="5%">
                                    <input type="text"  readonly="readonly" name="airplaneImages[' + airplaneImageIndex + '].title" class="text" maxlength="100" value='+ title +' \/>
                                    <\/td>
                                    <td  width ="5%">
                                    <input type="text"  readonly="readonly" name="airplaneImages[' + airplaneImageIndex + '].order" class="text airplaneImageOrder" maxlength="9" style="width: 50px;" value='+ order +' \/>
                                    <\/td>
                                    <td>
                                    <a href="javascript:;" class="deleteProductImage">[${message("admin.common.delete")}]<\/a>
                                    <\/td>
                                    <\/tr>';
                                    [/@compress]
                                        $cutImageTable.append(trHtml);
                                        airplaneImageIndex ++;
                                    }
                                }
                            });
                        }
                    });
                });
            }

            // 编辑商品图片
            $editProductImage.click(function() {
                isEdit = true;
                selectTr = $(this).closest("tr");
            });
            $browserButton.click(function() {
                isEdit = false;
            });

            // 删除商品图片
            $deleteProductImage.live("click", function() {
                var $this = $(this);
                $.dialog({
                    type: "warn",
                    content: "${message("admin.dialog.deleteConfirm")}",
                    onOk: function() {
                        $this.closest("tr").remove();
                    }
                });
            });


            // 表单验证
            $inputForm.validate({
                rules: {
                    typeName: "required",
                    order: "required",
                    isBooking: "required",
                    cruisingSpeed: "required",
                    chargeType: "required",
                    typeLevel: "required",
                    emptyPrice: "required",
                    loadedPrice: "required",
                    extEmptyPrice: "required",
                    extLoadedPrice: "required",
                    regNo: "required",
                    capacity : "required",
                    brandI : "required",
                    companyId : "required",
                    typeI : "required",
                    minimumHour :{
                        number:true,
                        required:true
                    },
                    departureAdd : "required",
                    airportIds :{
                        maxlength:5,
                        required:true
                    }
                },

            });

            // 修改飞机品牌
            $fieldSet.live("change",function(){
                var $this = $(this);
                var $typeValue = $typeIds.val();
                var $planeBrandValue = $this.val();
                if($planeBrandValue != '' || $planeBrandValue != undefined){
                    $.ajax({
                        url: "${base}/admin/airline/getPlaneType.jhtml",
                        type: "GET",
                        data: {id: $planeBrandValue},
                        dataType: "json",
                        success: function(data) {
                            $typeIds.empty();
                            var $commonOption = $("<option>").val("").text("${message("admin.common.choose")}");
                            $typeIds.append($commonOption);
                            $.each(data, function(i, planType){
                                var $option = $("<option>").val(planType.id).text(planType.typeName);
                                $typeIds.append($option);
                            });
                            $typeIds.val($typeValue);
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

        });
    </script>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.airplane.edit")}
</div>
<form id="inputForm" action="update.jhtml" method="post" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${airplane.id}" />
    <input type="hidden" id="type" name="type" value="${airplane.typeId.id}" />
    <ul id="tab" class="tab">
        <li>
            <input type="button" value="${message("airplane.message")}" />
        </li>
        <li>
            <input type="button" value="${message("airplane.image")}" />
        </li>
        <li>
            <input type="button" value="${message("planeType.base")}" />
        </li>
        <li>
            <input type="button" value="${message("planeType.body")}" />
        </li>
    [#--<li>--]
    [#--<input type="button" value="${message("admin.subjectReport.attributes")}" />--]
    [#--</li>--]
    [#--<li>--]
    [#--<input type="button" value="${message("admin.subjectReport.resources")}" />--]
    [#--</li>--]

    </ul>
    <div class="tabContent">
        <table class="input">
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("airplane.brand")}:
                </th>
                <td>
                    <select name="planeBrandI" id="planeBrandId" class="fieldSet">
                        <option value="">请选择飞机品牌</option>
                    [#list planeBrands as brand]
                        <option value="${brand.id}"[#if brand == airplane.brandId] selected="selected"[/#if]>
                        ${brand.name}
                        </option>
                    [/#list]
                    </select>
                </td>
            </tr>
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("airplane.type")}:
                </th>
                <td>
                    <select id="typeIds" name="typeI">
                        <option value="">${message("admin.common.choose")}</option>
                    [#list planeTypes as type]
                        <option value="${type.id}"[#if type == airplane.typeId] selected="selected"[/#if]>
                        ${type.typeName}
                        </option>
                    [/#list]
                    </select>
                </td>
            </tr>
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("airplane.capacity")}:
                </th>
                <td>
                    <input type="text" name="capacity" class="text" value="${airplane.capacity}"/>
                </td>
            </tr>
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("airplane.company")}:
                </th>
                <td>
                    <select name="companyId" >
                        <option value="">请选航空公司</option>
                    [#list companys as company]
                        <option value="${company.id}" [#if company == airplane.company] selected="selected"[/#if]>
                        ${company.name}
                        </option>
                    [/#list]
                    </select>
                </td>
            </tr>
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("airplane.regNo")}:
                </th>
                <td>
                    <select name="regNos" id="dropdownlist">
                        <option value="" [#if airplane.regNos == ""]selected="selected"[/#if]>请选择</option>
                        <option value="B-" [#if airplane.regNos == "B-"]selected="selected"[/#if]>B-</option>
                        <option value="VP-" [#if airplane.regNos == "VP-"]selected="selected"[/#if]>VP-</option>
                        <option value="N" [#if airplane.regNos == "N"]selected="selected"[/#if]>N</option>
                    </select>
                    <input type="text" name="number" id="number" value="${airplane.number}" class="text" />
                </td>
            </tr>

            <tr>
                <th>
                    <span class="requiredField">*</span>${message("airplane.loadedPrice")}:
                </th>
                <td>
                    <input type="text" name="loadedPrice" class="text" value="${airplane.loadedPrice}"/>
                </td>
            </tr>
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("airplane.emptyPrice")}:
                </th>
                <td>
                    <input type="text" name="emptyPrice" class="text" value="${airplane.emptyPrice}"/>
                </td>
            </tr>
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("airplane.extLoadedPrice")}:
                </th>
                <td>
                    <input type="text" name="extLoadedPrice" class="text" value="${airplane.extLoadedPrice}"/>
                </td>
            </tr>
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("airplane.extEmptyPrice")}:
                </th>
                <td>
                    <input type="text" name="extEmptyPrice" class="text" value="${airplane.extEmptyPrice}"/>
                </td>
            </tr>


            <tr>
                <th>
                    <span class="requiredField">*</span>${message("airplane.chargeType")}:
                </th>
                <td>
                    <label>
                        <input type="radio" name="chargeType" value="full"  [#if  airplane.chargeType == "full"] checked="checked"[/#if]/>全包
                        <input type="radio" name="chargeType" value="task" [#if  airplane.chargeType == "task"] checked="checked"[/#if]/>任务
                    </label>
                </td>
            </tr>
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("airplane.typeLevel")}:
                </th>
                <td>
                    <label>
                        <input type="radio" name="typeLevel" value="middle"  [#if  airplane.typeLevel == "middle"] checked="checked"[/#if]/>中程
                        <input type="radio" name="typeLevel" value="middleDistant" [#if  airplane.typeLevel == "middleDistant"] checked="checked"[/#if]/>中远程
                        <input type="radio" name="typeLevel" value="distant" [#if  airplane.typeLevel == "distant"] checked="checked"[/#if]/>远程
                        <input type="radio" name="typeLevel" value="maxDistant" [#if  airplane.typeLevel == "maxDistant"] checked="checked"[/#if]/>超远程
                    </label>
                </td>
            </tr>
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("airplane.airportId")}:
                </th>
                <td>
                    <input class="text addInput" type="hidden" id="departureIdAdd"  name="departureIdAdd" value="${airplane.airportId.id}"/>
                    <input class="text search addInput" type="text" id="departureAdd"  name="departureAdd" val="${airplane.airportId.id}" value="${airplane.airportId.name}"/>
                </td>
            </tr>
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("airplane.cruisingSpeed")}:
                </th>
                <td>
                    <input type="text" name="cruisingSpeed" class="text" value="${airplane.cruisingSpeed}"/>
                </td>
            </tr>
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("airplane.minimumHour")}:
                </th>
                <td>
                    <input type="text" name="minimumHour" class="text" value="${airplane.minimumHour}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("airplane.isBooking")}:
                </th>
                <td>
                    <label>
                        <input type="checkbox" name="isBooking" value="true" checked="checked" [#if airplane.isBooking] checked="checked"[/#if] />是
                        <input type="hidden" name="isBooking" value="false" />
                    </label>
                </td>
            </tr>
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("airplane.airports")}:
                </th>
                <td colspan="2">
                [#list virtuals as airport]
                    <label>
                        <input type="checkbox" name="airportIds" value="${airport.id}" [#if airplane.airports?seq_contains(airport)] checked="checked"[/#if] />
                    ${airport.name}
                    </label>
                [/#list]
                </td>
            </tr>
        </table>
    </div>
    <div class="tabContent">
        <table class="input">
            <tr>
                <th>
                ${message("Product.image")}:
                </th>
                <td>
					<span class="fieldSet">
						<!--<input type="text" name="image" class="text" maxlength="200" title="${message("admin.product.imageTitle")}" />-->
                        <img  src="" id="imgCrop" name="imgCrop"/><br/>
						<input type="button" id="browserButton" class="button" value="${message("admin.browser.select")}" />
                        <input type="button" id="uploadButton" class="button" value="确认上传" />
					</span>
                    <h3>依次为：外观图片,内舱图片,座舱布局图片,其他图片(可多张)</h3>
                </td>
            </tr>
        </table>
        <table id="cutImageTable"  class="input list">
            <tr class="title">
                <td>
                ${message("ProductImage.file")}
                </td>
                <td>
                ${message("ProductImage.title")}
                </td>
                <td>
                ${message("admin.common.order")}
                </td>
                <td>
                ${message("admin.common.delete")}
                </td>
            </tr>
        [#list airplane.airplaneImages as airplaneImage]
            <tr>
                <td>
                    <input type="hidden" name="airplaneImages[${airplaneImage_index}].source" value="${airplaneImage.source}" />
                    <a href="${airplaneImage.source}" target="_blank">${message("admin.common.view")}</a>
                </td>
                <td>
                    <input type="text"  readonly="readonly" name="airplaneImages[${airplaneImage_index}].title" class="text" maxlength="200" value="${airplaneImage.title}" />
                </td>
                <td>
                    <input type="text"  readonly="readonly" name="airplaneImages[${airplaneImage_index}].order" class="text airplaneImagesOrder" value="${airplaneImage.order}" maxlength="9" style="width: 50px;" />
                </td>
                <td>
                    <a href="javascript:;" class="editProductImage" style="float: left;">[${message("admin.common.edit")}]</a>
                    <a href="javascript:;" class="deleteProductImage">[${message("admin.common.delete")}]</a>
                </td>
            </tr>
        [/#list]
        </table>
    </div>

    <div class="tabContent">
        <table class="input">

            <tr>
                <th>
                ${message("planeType.maxTakeoff")}:
                </th>
                <td>
                    <input type="text" name="maxTakeoffWeight" class="text" value="${airplane.maxTakeoff}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.emptyWeight")}:
                </th>
                <td>
                    <input type="text" name="emptyWeight" class="text"  value="${airplane.emptyWeight}"/>
            </tr>
            <tr>
                <th>
                ${message("planeType.manuDate")}:
                </th>
                <td>
                    <input type="text" name="manuDate" class="text" maxlength="20" value="${airplane.manuDate}"/>
            </tr>

            <tr>
                <th>
                ${message("planeType.descend")}:
                </th>
                <td>
                    <textarea name="descend" class="text" value="${airplane.descend}"></textarea>
                </td>
            </tr>

        </table>
    </div>
    <div class="tabContent">
        <table class="input">
            <tr>
                <th>
                ${message("planeType.lengths")}:
                </th>
                <td>
                    <input type="text" name="lengths" class="text" value="${airplane.lengths}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.width")}:
                </th>
                <td>
                    <input type="text" name="width" class="text" value="${airplane.width}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.height")}:
                </th>
                <td>
                    <input type="text" name="height" class="text" value="${airplane.height}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.passengerCabinLength")}:
                </th>
                <td>
                    <input type="text" name="passengerCabinLength" class="text" value="${airplane.passengerCabinLength}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.passengerCabinWidth")}:
                </th>
                <td>
                    <input type="text" name="passengerCabinWidth" class="text" value="${airplane.passengerCabinWidth}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.passengerCabinHeight")}:
                </th>
                <td>
                    <input type="text" name="passengerCabinHeight" class="text" value="${airplane.passengerCabinHeight}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.luggageCompartment")}:
                </th>
                <td>
                    <input type="text" name="luggageCompartment" class="text" value="${airplane.luggageCompartment}"/>
                </td>
            </tr>
        [#--</table>--]
        [#--</div>--]
        [#--<div class="tabContent">--]
        [#--<table id="attributeTable" class="input">--]
            <tr>
                <th>
                ${message("planeType.engineType")}:
                </th>
                <td>
                    <input type="text" name="engineType" class="text" value="${airplane.engineType}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.engineModel")}:
                </th>
                <td>
                    <input type="text" name="engineModel" class="text" value="${airplane.engineModel}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.engineManufacturer")}:
                </th>
                <td>
                    <input type="text" name="engineManufacturer" class="text" value="${airplane.engineManufacturer}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.fuelTankCapacity")}:
                </th>
                <td>
                    <input type="text" name="fuelTankCapacity" class="text" value="${airplane.fuelTankCapacity}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.oilConsumption")}:
                </th>
                <td>
                    <input type="text" name="oilConsumption" class="text" value="${airplane.oilConsumption}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.maxPower")}:
                </th>
                <td>
                    <input type="text" name="maxPower" class="text" value="${airplane.maxPower}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.maxPowerSpeed")}:
                </th>
                <td>
                    <input type="text" name="maxPowerSpeed" class="text" value="${airplane.maxPowerSpeed}"/>
                </td>
            </tr>
        [#--</table>--]
        [#--</div>--]
        [#--<div class="tabContent">--]
        [#--<table id="resourcesTable" class="input">--]
            <tr>
                <th>
                ${message("planeType.speed")}:
                </th>
                <td>
                    <input type="text" name="speed" class="text"  value="${airplane.speed}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.voyage")}:
                </th>
                <td>
                    <input type="text" name="voyage" class="text"  value="${airplane.voyage}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.ceiling")}:
                </th>
                <td>
                    <input type="text" name="ceiling" class="text"  value="${airplane.ceiling}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.minTakeoffDistance")}:
                </th>
                <td>
                    <input type="text" name="minTakeoffDistance" class="text"  value="${airplane.minTakeoffDistance}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.minLandingDistance")}:
                </th>
                <td>
                    <input type="text" name="minLandingDistance" class="text"  value="${airplane.minLandingDistance}"/>
                </td>
            </tr>
        </table>
    </div>
    <table class="input">
        <tr>
            <th>
                &nbsp;
            </th>
            <td>
                <input type="submit" class="button" value="${message("admin.common.submit")}" />
                <input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
            </td>
        </tr>
    </table>
</form>

<script type="text/javascript">
    function addTextToDropDownList()
    {
        var text=document.getElementById("text");
        var ddl=document.getElementById("dropdownlist");
        if(text.value.length>0)
        {
            var option=document.createElement("option");
            option.appendChild(document.createTextNode(text.value));
            ddl.appendChild(option);

        }
    }
</script>
</body>
</html>