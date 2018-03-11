<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.planeBrand.add")}</title>


<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");

	[@flash_message /]

	// 表单验证
	$inputForm.validate({
		rules: {
			name:"required",
			order: "required"
        }
	});

});
</script>
<style type="text/css">
    .roles label {
    width: 150px;
    display: block;
    float: left;
    padding-right: 6px;
        }
</style>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.planeBrand.add")}
	</div>
	<form id="inputForm" action="save.jhtml" method="post">
		<table class="input">
            <tr>
                <th>
                    <span class="requiredField">*</span>${message("planeBrand.name")}:
                </th>
                <td>
                    <input type="text" id="name" name="name" class="text" maxlength="200" />
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
				${message("admin.common.setting")}:
                </th>
                <td>
                    <label>
                        <input type="checkbox" name="isShow" value="true" checked="checked" />${message("planeBrand.isShow")}
                        <input type="hidden" name="isShow" value="false" />
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