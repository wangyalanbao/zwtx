<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.main.myreport")}</title>


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

        [@flash_message /]


        });
    </script>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.main.myreport")}
</div>
<form id="inputForm" action="save.jhtml" method="post" enctype="multipart/form-data">
    <ul id="tab" class="tab">
        <li>
            <input type="button" value="${message("admin.myreport.jingluo")}" />
        </li>
        <li>
            <input type="button" value="${message("admin.myreport.tizhi")}" />
        </li>
        <li>
            <input type="button" value="${message("admin.myreport.zangfu")}" />
        </li>
        <li>
            <input type="button" value="${message("admin.myreport.xinli")}" />
        </li>
        <li>
            <input type="button" value="${message("admin.myreport.xindian")}" />
        </li>
        <li>
            <input type="button" value="${message("admin.myreport.xueya")}" />
        </li>
        <li>
            <input type="button" value="${message("admin.myreport.xueyang")}" />
        </li>
        <li>
            <input type="button" value="${message("admin.myreport.tiwen")}" />
        </li>

        <li>
            <input type="button" value="${message("admin.myreport.huxi")}" />
        </li>
        <li>
            <input type="button" value="血糖" />
        </li>
    </ul>
    <!-- 经络 -->
    <div class="tabContent">
        <table id="listTable" class="list">
        [#list jingluo as jl]
            <tr>
                <td>
                    ${message("admin.myreport.result")}：${jl.subject.name}
                </td>
                <td>
                    ${message("admin.myreport.shijian")}：${jl.createDate?string("yyyy-MM-dd HH:mm:ss")}
                </td>
            </tr>
        [/#list]
        </table>
    </div>
    <!-- 体质 -->
    <div class="tabContent">
        <table id="listTable" class="list">
        [#list tizhi as tz]
            <tr>
                <td>
                ${message("admin.myreport.result")}：${tz.subject.name}
                </td>
                <td>
                ${message("admin.myreport.shijian")}：${tz.createDate?string("yyyy-MM-dd HH:mm:ss")}
                </td>
            </tr>
        [/#list]
        </table>
    </div>
    <!-- 脏腑 -->
    <div class="tabContent">
        <table id="listTable" class="list">
        [#list zangfu as zf]
            <tr>
                <td>
                ${message("admin.myreport.result")}：${zf.zz_name}
                </td>
                <td>
                ${message("admin.myreport.shijian")}：${zf.createDate?string("yyyy-MM-dd HH:mm:ss")}
                </td>
            </tr>
        [/#list]
        </table>
    </div>
    <!-- 心理 -->
    <div class="tabContent">
        <table id="listTable" class="list">
        [#list xinli as xl]
            <tr>
                <td>
                ${message("admin.myreport.result")}：${xl.subject.name}
                </td>
                <td>
                ${message("admin.myreport.shijian")}：${xl.createDate?string("yyyy-MM-dd HH:mm:ss")}
                </td>
            </tr>
        [/#list]
        </table>
    </div>
    <!-- 心电 -->
    <div class="tabContent">
        <iframe name="main" frameborder="0" width="1000" height="800" scrolling="yes" style="overflow: visible;" src="${base}/subject_report/getecgreport.jhtml?datatype=10&mcId=${mcId}">${message("admin.main.doctorPause")}</iframe>
    </div>
    <!-- 血压 -->
    <div class="tabContent">
        <iframe name="main" frameborder="0" width="1000" height="800" scrolling="yes" style="overflow: visible;" src="${base}/subject_report/getreport.jhtml?datatype=30&mcId=${mcId}">${message("admin.main.doctorPause")}</iframe>
    </div>
    <!-- 血氧 -->
    <div class="tabContent">
        <iframe name="main" frameborder="0" width="1000" height="800" scrolling="yes" style="overflow: visible;" src="${base}/subject_report/getreport.jhtml?datatype=20&mcId=${mcId}">${message("admin.main.doctorPause")}</iframe>
    </div>
    <!-- 体温 -->
    <div class="tabContent">
        <iframe name="main" frameborder="0" width="1000" height="800" scrolling="yes" style="overflow: visible;" src="${base}/subject_report/getreport.jhtml?datatype=40&mcId=${mcId}">${message("admin.main.doctorPause")}</iframe>
    </div>
    <!-- 呼吸 -->
    <div class="tabContent">
        <iframe name="main" frameborder="0" width="1000" height="800" scrolling="yes" style="overflow: visible;" src="${base}/subject_report/getreport.jhtml?datatype=50&mcId=${mcId}">${message("admin.main.doctorPause")}</iframe>
    </div>
    <!-- 血糖 -->
    <div class="tabContent">
        <iframe name="main" frameborder="0" width="1000" height="800" scrolling="yes" style="overflow: visible;" src="${base}/subject_report/getreport.jhtml?datatype=60&mcId=${mcId}">${message("admin.main.doctorPause")}</iframe>
    </div>


</form>
</body>
</html>