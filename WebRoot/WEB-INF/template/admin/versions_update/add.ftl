<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.versions_update.add")}</title>


    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
    <style type="text/css">
        .authorities label {
            min-width: 120px;
            _width: 120px;
            display: block;
            float: left;
            padding-right: 4px;
            _white-space: nowrap;
        }
    </style>
    <script type="text/javascript">
        $().ready(function() {
            var $importMoreTab = $(".importMoreTab");
            var $inputForm = $("#inputForm");
            var $selectAll = $("#inputForm .selectAll");

		[@flash_message /]


            $selectAll.click(function() {
                var $this = $(this);
                var $thisCheckbox = $this.closest("tr").find(":checkbox");
                if ($thisCheckbox.filter(":checked").size() > 0) {
                    $thisCheckbox.prop("checked", false);
                } else {
                    $thisCheckbox.prop("checked", true);
                }
                return false;
            });

            // 表单验证
            $inputForm.validate({
                rules: {
                    name: "required",
                    authorities: "required"
                }
            });

        });
    </script>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.versions_update.add")}
</div>
<form id="inputForm" action="save.jhtml" method="post" enctype="multipart/form-data">
    <input type="hidden" id="sId" name="sId" value="${sId}" />
    <table class="input">
        <tr>
            <th>
			${message("versions_update.downurl")}:
            </th>
            <td>
                <input type="file"  name="file" class="text" maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
			${message("versions_update.versionsNum")}:
            </th>
            <td>
                <input type="text" name="versionsNum" class="text" maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
                &nbsp;
            </th>
            <td>
                <input type="submit" class="button" value="${message("admin.common.submit")}" />
                <input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml?sId=${sId}'" />
            </td>
        </tr>
    </table>
</form>
</body>
</html>