<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.planeType.edit")}</title>

    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
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
    </style>
    <script type="text/javascript">
        $().ready(function() {

            var $inputForm = $("#inputForm");


        [@flash_message /]
            $browserButton.browser();



            // 表单验证
            $inputForm.validate({
                rules: {
                    typeName: "required",
                    order: "required"
                }
            });

        });
    </script>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.planeType.edit")}
</div>
<form id="inputForm" action="update.jhtml" method="post" enctype="multipart/form-data">
    <input type="hidden" id="planeBrandId" name="planeBrandId" value="${planeBrandId}" />
    <input type="hidden" name="id" value="${planeType.id}" />
    <ul id="tab" class="tab">
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
                ${message("planeBrand.name")}:
                </th>
                <td>
                    <input type="text" name="planeBrandName" class="text" maxlength="200" value="${planeBrand.name}" readonly/>
                </td>
            </tr>
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("planeType.typeName")}:
                </th>
                <td>
                    <input type="text" name="typeName" class="text" value="${planeType.typeName}" maxlength="200" />
                </td>
            </tr>
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("answer.orders")}:
                </th>
                <td>
                    <input type="text" name="order" class="text" value="${planeType.order}" maxlength="9" />
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.maxTakeoff")}:
                </th>
                <td>
                    <input type="text" name="maxTakeoffWeight" class="text" maxlength="13" value="${planeType.maxTakeoff}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.emptyWeight")}:
                </th>
                <td>
                    <input type="text" name="emptyWeight" class="text" maxlength="13" value="${planeType.emptyWeight}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.manuDate")}:
                </th>
                <td>
                    <input type="text" id="manuDate" name="manuDate" class="text Wdate" value="[#if planeType.manuDate??]${planeType.manuDate?string("yyyy-MM-dd HH:mm:ss")}[/#if]" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'manuDate\')}'});" />
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.advantage")}:
                </th>
                <td>
                    <textarea name="advantage" class="text" value="${planeType.advantage}"></textarea>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.shortage")}:
                </th>
                <td>
                    <textarea name="shortage" class="text" value="${planeType.shortage}"></textarea>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.descend")}:
                </th>
                <td>
                    <textarea name="descend" class="text" value="${planeType.descend}"></textarea>
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
                    <input type="text" name="lengths" class="text" value="${planeType.lengths}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.width")}:
                </th>
                <td>
                    <input type="text" name="width" class="text" value="${planeType.width}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.height")}:
                </th>
                <td>
                    <input type="text" name="height" class="text" value="${planeType.height}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.passengerCabinLength")}:
                </th>
                <td>
                    <input type="text" name="passengerCabinLength" class="text" value="${planeType.passengerCabinLength}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.passengerCabinWidth")}:
                </th>
                <td>
                    <input type="text" name="passengerCabinWidth" class="text" value="${planeType.passengerCabinWidth}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.passengerCabinHeight")}:
                </th>
                <td>
                    <input type="text" name="passengerCabinHeight" class="text" value="${planeType.passengerCabinHeight}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.luggageCompartment")}:
                </th>
                <td>
                    <input type="text" name="luggageCompartment" class="text" value="${planeType.luggageCompartment}"/>
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
                    <input type="text" name="engineType" class="text" value="${planeType.engineType}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.engineModel")}:
                </th>
                <td>
                    <input type="text" name="engineModel" class="text" value="${planeType.engineModel}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.engineManufacturer")}:
                </th>
                <td>
                    <input type="text" name="engineManufacturer" class="text" value="${planeType.engineManufacturer}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.fuelTankCapacity")}:
                </th>
                <td>
                    <input type="text" name="fuelTankCapacity" class="text" value="${planeType.fuelTankCapacity}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.oilConsumption")}:
                </th>
                <td>
                    <input type="text" name="oilConsumption" class="text" value="${planeType.oilConsumption}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.maxPower")}:
                </th>
                <td>
                    <input type="text" name="maxPower" class="text" value="${planeType.maxPower}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.maxPowerSpeed")}:
                </th>
                <td>
                    <input type="text" name="maxPowerSpeed" class="text" value="${planeType.maxPowerSpeed}"/>
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
                    <input type="text" name="speed" class="text"  value="${planeType.speed}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.voyage")}:
                </th>
                <td>
                    <input type="text" name="voyage" class="text"  value="${planeType.voyage}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.ceiling")}:
                </th>
                <td>
                    <input type="text" name="ceiling" class="text"  value="${planeType.ceiling}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.minTakeoffDistance")}:
                </th>
                <td>
                    <input type="text" name="minTakeoffDistance" class="text"  value="${planeType.minTakeoffDistance}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.minLandingDistance")}:
                </th>
                <td>
                    <input type="text" name="minLandingDistance" class="text"  value="${planeType.minLandingDistance}"/>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.cruisingSpeed")}:
                </th>
                <td>
                    <input type="text" name="cruisingSpeed" class="text" value="${planeType.cruisingSpeed}"/>
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
                <input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml?planeBrandId=${planeBrandId}'" />
            </td>
        </tr>
    </table>
</form>
</body>
</html>