<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.airplane.edit")}</title>

    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.imgbox.pack.js"></script>

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
    </style>
    <script type="text/javascript">
        $().ready(function() {

            $("#example1-1").imgbox({
                'zoomOpacity'	: true,
                'alignment'		: 'center'
            });

            var $inputForm = $("#inputForm");
            var $fieldSet = $(".fieldSet");
            var $typeIds = $("#typeIds");


        [@flash_message /]


            // 修改飞机品牌
            $fieldSet.live("click",function(){
                var $this = $(this);
                var $typeValue = $typeIds.val();
                var $planeBrandValue = $this.val();
                if($planeBrandValue != '' || $planeBrandValue != undefined){
                    $.ajax({
                        url: "getPlaneType.jhtml",
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

        });
    </script>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.airplane.edit")}
</div>
<form id="inputForm" action="update.jhtml" method="post" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${airplane.id}" />
    <ul id="tab" class="tab">
        <li>
            <input type="button" value="${message("airplane.message")}" />
        </li>
        <li>
            <input type="button" value="${message("airplane.image")}" />
        </li>
        <li>
            <input type="button" value="${message("admin.product.base")}" />
        </li>
        <li>
            <input type="button" value="${message("Article.content")}" />
        </li>
        <li>
            <input type="button" value="${message("admin.subjectReport.attributes")}" />
        </li>
        <li>
            <input type="button" value="${message("admin.subjectReport.resources")}" />
        </li>

    </ul>
    <div class="tabContent">
        <table class="input">
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("airplane.brand")}:
                </th>
                <td>
                ${airplane.brandId.name}
                </td>
            </tr>
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("airplane.type")}:
                </th>
                <td>
                ${airplane.typeId.typeName}
                </td>
            </tr>
            <tr>
                <th>
                ${message("airplane.capacity")}:
                </th>
                <td>
                ${airplane.capacity}
                </td>
            </tr>
            <tr>
                <th>
                ${message("airplane.company")}:
                </th>
                <td>
                ${airplane.company.name}
                </td>
            </tr>
            <tr>
                <th>
                ${message("airplane.regNo")}:
                </th>
                <td>
                ${airplane.regNo}
                </td>
            </tr>
            <tr>
                <th>
                ${message("airplane.loadedPrice")}:
                </th>
                <td>
                ${airplane.loadedPrice}
                </td>
            </tr>
            <tr>
                <th>
                ${message("airplane.emptyPrice")}:
                </th>
                <td>
                ${airplane.emptyPrice}
                </td>
            </tr>
            <tr>
                <th>
                ${message("airplane.extLoadedPrice")}:
                </th>
                <td>
                ${airplane.extLoadedPrice}
                </td>
            </tr>
            <tr>
                <th>
                ${message("airplane.extEmptyPrice")}:
                </th>
                <td>
                ${airplane.extEmptyPrice}
                </td>
            </tr>
            <tr>
                <th>
                ${message("airplane.chargeType")}:
                </th>
                <td>
                    <label>
                        <input type="radio" name="chargeType" value="full" [#if airplane.chargeType == "full"] checked="checked"[/#if]/>全包
                        <input type="radio" name="chargeType" value="task"  [#if airplane.chargeType == "task"] checked="checked"[/#if]/>任务
                    </label>
                </td>
            </tr>
            <tr>
                <th>
                ${message("airplane.airportId")}:
                </th>
                <td>
                ${airplane.airportId.name}
                </td>
            </tr>
            <tr>
                <th>
                ${message("airplane.cruisingSpeed")}:
                </th>
                <td>
                ${airplane.cruisingSpeed}
                </td>
            </tr>
            <tr>
                <th>
                ${message("airplane.minimumHour")}:
                </th>
                <td>
                ${airplane.minimumHour}
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

    <table id="productImageTable" class="input tabContent">
        <tr>
            <th>
                描述照片:
            </th>
            <td colspan="2">
            [#list airplane.airplaneImages as img]
                <a id="example1-1" title="图片" href="${img.source}"alt="图片" target="_blank"><img alt="" src="${img.source}"  style="width: 350px;"/></a>
            [/#list ]
            </td>
        </tr>
    </table>

    <div class="tabContent">
        <table class="input">
            <tr>
                <th>
                ${message("planeType.maxTakeoff")}:
                </th>
                <td>
                ${airplane.maxTakeoff}
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.emptyWeight")}:
                </th>
                <td>
                ${airplane.emptyWeight}
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.manuDate")}:
                </th>
                <td>
                ${airplane.manuDate}
                </td>
            </tr>
            </tr>
            <tr>
                <th>
                ${message("planeType.advantage")}:
                </th>
                <td>
                    <textarea name="advantage" class="text" value="${airplane.advantage}"></textarea>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.shortage")}:
                </th>
                <td>
                    <textarea name="shortage" class="text" value="${airplane.shortage}"></textarea>
                </td>
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
                ${airplane.lengths}
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.width")}:
                </th>
                <td>
                ${airplane.width}
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.height")}:
                </th>
                <td>
                ${airplane.height}
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.passengerCabinLength")}:
                </th>
                <td>
                ${airplane.passengerCabinLength}
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.passengerCabinWidth")}:
                </th>
                <td>
                ${airplane.passengerCabinWidth}
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.passengerCabinHeight")}:
                </th>
                <td>
                ${airplane.passengerCabinHeight}
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.luggageCompartment")}:
                </th>
                <td>
                ${airplane.luggageCompartment}
                </td>
            </tr>
        </table>
    </div>
    <div class="tabContent">
        <table id="attributeTable" class="input">
            <tr>
                <th>
                ${message("planeType.engineType")}:
                </th>
                <td>
                ${airplane.engineType}
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.engineModel")}:
                </th>
                <td>
                ${airplane.engineModel}
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.engineManufacturer")}:
                </th>
                <td>
                ${airplane.engineManufacturer}
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.fuelTankCapacity")}:
                </th>
                <td>
                ${airplane.fuelTankCapacity}
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.oilConsumption")}:
                </th>
                <td>
                ${airplane.oilConsumption}
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.maxPower")}:
                </th>
                <td>
                ${airplane.maxPower}
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.maxPowerSpeed")}:
                </th>
                <td>
                ${airplane.maxPowerSpeed}
                </td>
            </tr>
        </table>
    </div>
    <div class="tabContent">
        <table id="resourcesTable" class="input">
            <tr>
                <th>
                ${message("planeType.speed")}:
                </th>
                <td>
                ${airplane.speed}
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.voyage")}:
                </th>
                <td>
                ${airplane.voyage}
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.ceiling")}:
                </th>
                <td>
                ${airplane.ceiling}
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.minTakeoffDistance")}:
                </th>
                <td>
                ${airplane.minTakeoffDistance}
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
                <input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
            </td>
        </tr>
    </table>
</form>
</body>
</html>