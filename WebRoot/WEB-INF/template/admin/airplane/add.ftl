<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.airplane.add")}</title>

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

            var $regNo = $("#listTable input.regNo");

            var $inputForm = $("#inputForm");
            var $fieldSet = $(".fieldSet");
            var $typeIds = $("#typeIds");
            var $browserButton = $("#browserButton");
            var $productImageTable = $("#productImageTable");
            var $cutImageTable = $("#cutImageTable");
            var $addProductImage = $("#addProductImage");
            var $deleteProductImage = $("a.deleteProductImage");
            var airplaneImageIndex = 0;
            var $uploadButton = $("#uploadButton");
            var $titles = ["外观图片","内舱图片","座舱布局图片","其他图片"];
            var $search = $(".search");

        [@flash_message /]

            $browserButton.browser();

            //注册号

            var ias = $('#imgCrop').imgAreaSelect({
                handles: true,
                aspectRatio:"15:8",
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
                                    <input style="width:100%"  readonly="readonly" type="text" name="airplaneImages[' + airplaneImageIndex + '].source" class="productImageFile"  class="text" value='+ message.content +' \/>
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
                                        $(".xxDialog").remove();
                                        $(".dialogOverlay").remove();
                                    }
                                }
                            });

                        }
                    });

                });
            }

            // 增加商品图片
            $addProductImage.click(function() {
            [@compress single_line = true]
                var trHtml =
                        '<tr>
                        <td>
                        <input type="file" name="airplaneImages[' + airplaneImageIndex + '].file" class="productImageFile" \/>
                <\/td>
            <td>
            <input type="text" name="airplaneImages[' + airplaneImageIndex + '].title" class="text" maxlength="200" \/>
                <\/td>
            <td>
            <input type="text" name="airplaneImages[' + airplaneImageIndex + '].order" class="text airplaneImageOrder" maxlength="9" style="width: 50px;" \/>
                <\/td>
            <td>
            <a href="javascript:;" class="deleteProductImage">[${message("admin.common.delete")}]<\/a>
            <\/td>
            <\/tr>';
            [/@compress]
                $productImageTable.append(trHtml);
                airplaneImageIndex ++;
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
                    number: "required",
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
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.airplane.add")}
</div>
<form id="inputForm" action="save.jhtml" method="post" enctype="multipart/form-data" novalidate="novalidate">
    <input type="hidden" id="planeBrandId" name="planeBrandId" value="${planeBrandId}" />
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
    [#--<input type="button" value="${message("planeType.engine")}" />--]
    [#--</li>--]
    [#--<li>--]
    [#--<input type="button" value="${message("planeType.propertyss")}" />--]
    [#--</li>--]
    </ul>

    <table class="input tabContent">
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airplane.brand")}:
            </th>
            <td>
                <select name="brandI" id="planeBrandId" class="fieldSet">
                    <option value="">请选择飞机品牌</option>
                [#list planeBrands as brand]
                    <option value="${brand.id}">
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
                </select>
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airplane.capacity")}:
            </th>
            <td>
                <input type="text" name="capacity" class="text" />
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airplane.company")}:
            </th>
            <td>
                <select name="companyId" >
                    <option value="">请选择</option>
                [#list companys as company]
                    <option value="${company.id}">
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
                    <option value="">请选择</option>
                    <option value="B-">B-</option>
                    <option value="VP-">VP-</option>
                    <option value="N">N</option>
                [#--<input id="text" type="text" style="width: 55px" placeholder="输入前缀"/>--]
                [#--<input type="button" name="regNo" id="add" value="添加" class="text"style="width: 40px" onclick="addTextToDropDownList();"/>--]
                </select>
                <input type="text" name="number" id="number"  class="text" />
            </td>
        </tr>

        <tr>
            <th>
                <span class="requiredField">*</span>${message("airplane.loadedPrice")}:
            </th>
            <td>
                <input type="text" name="loadedPrice" class="text" />
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airplane.emptyPrice")}:
            </th>
            <td>
                <input type="text" name="emptyPrice" class="text" />
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airplane.extLoadedPrice")}:
            </th>
            <td>
                <input type="text" name="extLoadedPrice" class="text" />
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airplane.extEmptyPrice")}:
            </th>
            <td>
                <input type="text" name="extEmptyPrice" class="text" />
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airplane.chargeType")}:
            </th>
            <td>
                <label>
                    <input type="radio" name="chargeType" value="full" checked="checked" />全包
                    <input type="radio" name="chargeType" value="task" />任务
                </label>
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airplane.typeLevel")}:
            </th>
            <td>
                <label>
                    <input type="radio" name="typeLevel" value="middle" checked="checked" />中程
                    <input type="radio" name="typeLevel" value="middleDistant" />中远程
                    <input type="radio" name="typeLevel" value="distant" />远程
                    <input type="radio" name="typeLevel" value="maxDistant" />超远程
                </label>
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airplane.airportId")}:
            </th>
            <td>
                <input class="text addInput" type="hidden" id="departureIdAdd"  name="departureIdAdd" />
                <input class="text search addInput" type="text" id="departureAdd"  name="departureAdd" val='addInput'/>

            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airplane.cruisingSpeed")}:
            </th>
            <td>
                <input type="text" name="cruisingSpeed" class="text" />
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("airplane.minimumHour")}:
            </th>
            <td>
                <input type="text" name="minimumHour" class="text" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("airplane.isBooking")}:
            </th>
            <td>
                <label>
                    <input type="checkbox" name="isBooking" value="true" checked="checked" />是
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
                    <input type="checkbox" name="airportIds" value="${airport.id}" />${airport.name}
                </label>
            [/#list]
            </td>
        </tr>
    </table>

    <div class="tabContent">

        <table class="input ">
            <tr>
                <th>
                ${message("Product.image")}:
                </th>
                <td>
                <span class="fieldSet">
                    <img  src="" id="imgCrop" name="imgCrop"/><br/>
                    <input type="button" id="browserButton" class="button" value="${message("admin.browser.select")}" />
                    <input type="button" id="uploadButton" class="button" value="确认上传" />
                </span>
                    <h3>依次为：外观图片,内舱图片,座舱布局图片,其他图片(可多张)</h3>
                </td>
            </tr>
        </table>
        <table id="cutImageTable"  class="input ">
            <tr class="title">
                <td width="50%">
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
        </table>
    </div>
    <!--
    <table id="productImageTable" class="input tabContent">
        <tr>
            <td colspan="4">
                <a href="javascript:;" id="addProductImage" class="button">${message("admin.product.addProductImage")}</a>
            </td>
        </tr>
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
    </table>
    -->
    <table class="input tabContent">
        <tr>
            <th>
            ${message("planeType.maxTakeoff")}:
            </th>
            <td>
                <input type="text" name="maxTakeoffWeight" class="text"  />
            </td>
        </tr>
        <tr>
            <th>
            ${message("planeType.emptyWeight")}:
            </th>
            <td>
                <input type="text" name="emptyWeight" class="text" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("planeType.manuDate")}:
            </th>
            <td>
                <input type="text" name="manuDate" id="manuDate" class="text" />
            </td>
        </tr>

        <tr>
            <th>
            ${message("planeType.descend")}:
            </th>
            <td>
                <textarea name="descend" class="text"></textarea>
            </td>
        </tr>
    </table>

    <table class="input tabContent">
        <tr>
            <th>
            ${message("planeType.lengths")}:
            </th>
            <td>
                <input type="text" name="lengths" class="text" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("planeType.width")}:
            </th>
            <td>
                <input type="text" name="width" class="text" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("planeType.height")}:
            </th>
            <td>
                <input type="text" name="height" class="text" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("planeType.passengerCabinLength")}:
            </th>
            <td>
                <input type="text" name="passengerCabinLength" class="text" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("planeType.passengerCabinWidth")}:
            </th>
            <td>
                <input type="text" name="passengerCabinWidth" class="text" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("planeType.passengerCabinHeight")}:
            </th>
            <td>
                <input type="text" name="passengerCabinHeight" class="text" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("planeType.luggageCompartment")}:
            </th>
            <td>
                <input type="text" name="luggageCompartment" class="text" />
            </td>
        </tr>
    [#--</table>--]

    [#--<table  class="input tabContent">--]
        <tr>
            <th>
            ${message("planeType.engineType")}:
            </th>
            <td>
                <input type="text" name="engineType" class="text" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("planeType.engineModel")}:
            </th>
            <td>
                <input type="text" name="engineModel" class="text" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("planeType.engineManufacturer")}:
            </th>
            <td>
                <input type="text" name="engineManufacturer" class="text" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("planeType.fuelTankCapacity")}:
            </th>
            <td>
                <input type="text" name="fuelTankCapacity" class="text" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("planeType.oilConsumption")}:
            </th>
            <td>
                <input type="text" name="oilConsumption" class="text" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("planeType.maxPower")}:
            </th>
            <td>
                <input type="text" name="maxPower" class="text" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("planeType.maxPowerSpeed")}:
            </th>
            <td>
                <input type="text" name="maxPowerSpeed" class="text" />
            </td>
        </tr>
    [#--</table>--]

    [#--<table class="input tabContent">--]
        <tr>
            <th>
            ${message("planeType.speed")}:
            </th>
            <td>
                <input type="text" name="speed" class="text" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("planeType.voyage")}:
            </th>
            <td>
                <input type="text" name="voyage" class="text" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("planeType.ceiling")}:
            </th>
            <td>
                <input type="text" name="ceiling" class="text" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("planeType.minTakeoffDistance")}:
            </th>
            <td>
                <input type="text" name="minTakeoffDistance" class="text" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("planeType.minLandingDistance")}:
            </th>
            <td>
                <input type="text" name="minLandingDistance" class="text" />
            </td>
        </tr>
    </table>

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