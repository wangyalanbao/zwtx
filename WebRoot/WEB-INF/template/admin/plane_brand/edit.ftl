<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.planeBrand.edit")}</title>


    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
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
                    name: "required",
                    order: "required"

                }
            });

        });
    </script>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.planeBrand.edit")}
</div>
<form id="inputForm" action="update.jhtml" method="post">
    <input type="hidden" name="id" value="${planeBrand.id}" />
    <table class="input">
        <tr>
            <th>
                <span class="requiredField">*</span>${message("planeBrand.name")}:
            </th>
            <td>
                <input type="text" id="name" name="name" class="text" value="${planeBrand.name}" maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("answer.orders")}:
            </th>
            <td>
                <input type="text" name="order" class="text" value="${planeBrand.order}" maxlength="9" />
            </td>
        </tr>
        <tr>
            <th>
            ${message("admin.common.setting")}:
            </th>
            <td>
                <label>
                    <input type="checkbox" name="isShow" value="true"[#if planeBrand.isShow] checked="checked"[/#if] />${message("planeBrand.isShow")}
                    <input type="hidden" name="_isShow" value="false" />
                </label>
            </td>
        </tr>
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
</body>
</html>