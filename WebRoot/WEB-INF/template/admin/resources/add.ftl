<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.resources.add")}</title>


    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
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
            var $browserButton = $("#browserButton");
            var $productImageTable = $("#productImageTable");
            var $addProductImage = $("#addProductImage");
            var $deleteProductImage = $("a.deleteProductImage");
            var $resourcesType = $("#resourcesType");
            var productImageIndex = 0;
            var $logo = $("#logo");

		[@flash_message /]


            $browserButton.browser();

            $resourcesType.change(function() {
                if ($(this).val() == "") {
                    $logo.val("").prop("disabled", true);
                    $browserButton.prop("disabled", true);
                } else {
                    $logo.prop("disabled", false);
                    $browserButton.prop("disabled", false);
                }
            });

            // 增加商品图片
            $addProductImage.click(function() {
			[@compress single_line = true]
                var trHtml =
                        '<tr>
                        <td>
                        <input type="file" name="resourcesWarehouses[' + productImageIndex + '].file" class="productImageFile" \/>
                <\/td>
            <td>
            <input type="text" name="resourcesWarehouses[' + productImageIndex + '].title" class="text" maxlength="200" \/>
                <\/td>
            <td>
            <select name="resourcesWarehouses[' + productImageIndex + '].type" id="resourcesType">
            <option value="">${message("admin.common.choose")}</option>
				[#list types.keySet() as key]
                <option value="${key}">
				${types.get(key)}
                </option>
				[/#list]
            </select>
            <\/td>
            <td>
            <input type="text" name="resourcesWarehouses[' + productImageIndex + '].order" class="text productImageOrder" maxlength="9" style="width: 50px;" \/>
                <\/td>
            <td>
            <a href="javascript:;" class="deleteProductImage">[${message("admin.common.delete")}]<\/a>
            <\/td>
            <\/tr>';
			[/@compress]
                $productImageTable.append(trHtml);
                productImageIndex ++;
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

            $.validator.addClassRules({
                productImageFile: {
                    required: true
                },
                productImageOrder: {
                    digits: true
                }
            });

            // 表单验证
            $inputForm.validate({
                rules: {
                    name: "required",
                },
                messages: {
                    sn: {
                        pattern: "${message("admin.validate.illegal")}",
                        remote: "${message("admin.validate.exist")}"
                    }
                }
            });

        });
    </script>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.resources.add")}
</div>
<form id="inputForm" action="save.jhtml" method="post" enctype="multipart/form-data">
    <ul id="tab" class="tab">
        <li>
            <input type="button" value="${message("admin.product.base")}" />
        </li>
        <li>
            <input type="button" value="${message("admin.resources.content")}" />
        </li>
        <li>
            <input type="button" value="${message("admin.resources.files")}" />
        </li>
    </ul>
    <table class="input tabContent">

        <tr>
            <th>
                <span class="requiredField">*</span>${message("Product.name")}:
            </th>
            <td>
                <input type="text" name="name" class="text" maxlength="200" />
            </td>
        </tr>

        <tr>
            <th>
			${message("Product.memo")}:
            </th>
            <td>
                <input type="text" name="memo" class="text" maxlength="200" />
            </td>
        </tr>
        <tr>
            <th>
			${message("admin.common.order")}:
            </th>
            <td>
                <input type="text" name="order" class="text" maxlength="16" />
            </td>
        </tr>
    </table>
    <table class="input tabContent">
        <tr>
            <td>
                <textarea id="editor" name="content" class="editor" style="width: 100%;"></textarea>
            </td>
        </tr>
    </table>
    <table id="productImageTable" class="input tabContent">
        <tr>
            <td colspan="4">
                <a href="javascript:;" id="addProductImage" class="button">${message("admin.resources.addFile")}</a>
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
			${message("admin.resources.type")}
            </td>
            <td>
			${message("admin.common.order")}
            </td>
            <td>
			${message("admin.common.delete")}
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
</body>
</html>