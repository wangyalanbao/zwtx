<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.planeType.add")}</title>

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
            var $productCategoryId = $("#productCategoryId");
            var $browserButton = $("#browserButton");
            var $attributeTable = $("#attributeTable");
            var $specificationIds = $("#specificationSelect :checkbox");
            var $specificationProductTable = $("#specificationProductTable");

            var $productTable = $("#productTable");
            var $productSelect = $("#productSelect");
            var $deleteProduct = $("a.deleteProduct");
            var $productTitle = $("#productTitle");

            var $resourcesTable = $("#resourcesTable");
            var $resourcesSelect = $("#resourcesSelect");
            var $deleteResources = $("a.deleteResources");
            var $resourcesTitle = $("#resourcesTitle");

        [@flash_message /]

            var previousProductCategoryId = getCookie("previousProductCategoryId");
            if (previousProductCategoryId != null) {
                $productCategoryId.val(previousProductCategoryId);
            } else {
                previousProductCategoryId = $productCategoryId.val();
            }

            $browserButton.browser();

            // 表单验证
            $inputForm.validate({
                rules: {
                    typeName: "required",
                    order: "required"
                },

            });

        });
    </script>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.planeType.add")}
</div>
<form id="inputForm" action="save.jhtml" method="post" enctype="multipart/form-data" novalidate="novalidate">
    <input type="hidden" id="planeBrandId" name="planeBrandId" value="${planeBrandId}" />
    <ul id="tab" class="tab">
        <li>
            <input type="button" value="${message("planeType.base")}" />
        </li>
        <li>
            <input type="button" value="${message("planeType.body")}" />
        </li>
        <li>
            <input type="button" value="${message("planeType.engine")}" />
        </li>
        <li>
            <input type="button" value="${message("planeType.propertyss")}" />
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
                    <input type="text" name="typeName" class="text" maxlength="200" />
                </td>
            </tr>
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("answer.orders")}:
                </th>
                <td>
                    <input type="text" name="order" class="text" maxlength="9" />
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.maxTakeoff")}:
                </th>
                <td>
                    <input type="text" name="maxTakeoffWeight" class="text" maxlength="13" />
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.emptyWeight")}:
                </th>
                <td>
                    <input type="text" name="emptyWeight" class="text" maxlength="13" />
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.manuDate")}:
                </th>
                <td>
                    <input type="text" id="manuDate" name="manuDate" class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'manuDate\')}'});" />
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.advantage")}:
                </th>
                <td>
                    <textarea name="advantage" class="text"></textarea>
                </td>
            </tr>
            <tr>
                <th>
                ${message("planeType.shortage")}:
                </th>
                <td>
                    <textarea name="shortage" class="text"></textarea>
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
    </div>
    <div class="tabContent">
        <table class="input">
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
        </table>
    </div>
    <div class="tabContent">
        <table  class="input">
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
        </table>
    </div>
    <div class="tabContent">
        <table class="input">
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
            <tr>
                <th>
                ${message("planeType.cruisingSpeed")}:
                </th>
                <td>
                    <input type="text" name="cruisingSpeed" class="text" />
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