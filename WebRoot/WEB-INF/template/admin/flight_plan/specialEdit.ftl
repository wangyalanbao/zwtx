<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.orderPassenger.edit")}</title>


    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>

    <style type="text/css">
        .shippingMethods label {
            width: 150px;
            display: block;
            float: left;
            padding-right: 6px;
        }
    </style>
    <script type="text/javascript">
        $().ready(function() {

            var $inputForm = $("#inputForm");
            var $browserButton = $("#browserButton");

        [@flash_message /]

            $browserButton.browser();

            // 表单验证
            $inputForm.validate({
                rules: {
                    name:"required",
                    identityType:"required",
                    idCardNo: {
                        required: true,
                        remote: "check_idCardNo.jhtml",
                        cache: false
                    }
                }
            });


        });
    </script>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 编辑特价航段
</div>
<form id="inputForm" action="update.jhtml" method="post">
    <input type="hidden" name="id" value="${flightPlan.id}" />
    <table class="input">

        <tr>
            <th>
            始发地:
            </th>
            <td>
               ${flightPlan.departure}
            </td>
        </tr>
        <tr>
            <th>
            目的地:
            </th>
            <td>
            ${flightPlan.destination}
            </td>
        </tr>
        <tr>
            <th>
            航空公司:
            </th>
            <td>
            ${flightPlan.company.name}
            </td>
        </tr>
        <tr>
            <th>
                注册号:
            </th>
            <td>
            ${flightPlan.regNo}
            </td>
        </tr>
        <tr>
            <th>
                出发时间:
            </th>
            <td>
                <span title="${flightPlan.takeoffTime?string("yyyy-MM-dd HH:mm:ss")}">${flightPlan.takeoffTime?string("yyyy-MM-dd HH:mm")}</span>
            </td>
        </tr>
        <tr>
            <th>
                原价(元):
            </th>
            <td>
           <input name="originalPrice" value="${flightPlan.originalPrice}" class="text" />
            </td>
        </tr>
        <tr>
            <th>
                特价(元):
            </th>
            <td>
                <input name="specialprice" value="${flightPlan.specialprice}" class="text" />
            </td>
        </tr>
        <tr>
            <th>
                &nbsp;
            </th>
            <td>
                <input type="submit" class="button" value="${message("admin.common.submit")}" />
                <input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='specialList.jhtml'" />
            </td>
        </tr>
    </table>
</form>
</body>
</html>