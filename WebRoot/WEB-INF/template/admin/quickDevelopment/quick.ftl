<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.admin.add")}</title>


    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
    <script type="text/javascript">
        $().ready(function() {

            var $inputForm = $("#inputForm");
            var $add = $("#add");
            var $minus = $("#minus");
            var $mould = $("#mould");
            var $box = $("#box");

        [@flash_message /]

            var $count = 0;
            $add.click(function(){
                $count++;
                var $addUl = $mould.clone();
                $addUl.css("position", "absolute");
                $addUl.find("input[name='name1']").attr("name","entityAttributes[" + $count + "].name");
                $addUl.find("select[name='type1']").attr("name","entityAttributes[" + $count + "].type");
                $addUl.find("input[name='memo1']").attr("name","entityAttributes[" + $count + "].memo");
                $addUl.find("input[name='isRequired1']").attr("name","entityAttributes[" + $count + "].isRequired");
                $addUl.find("input[name='length1']").attr("name","entityAttributes[" + $count + "].length");
                $addUl.find("select[name='isSelect1']").attr("name","entityAttributes[" + $count + "].isSelect");
                $addUl.attr("id","mould_" + $count);
                $box.append($addUl);
                $addUl.removeClass("hidden");
                $addUl.css("width", "10%");
                $addUl.animate({right: "0px", top:"800px"},0)
                        .animate({right:"100px",top:"100px",width:"70%"},2000);

                var i = setInterval(function(){
                    $addUl.css("right", null);
                    $addUl.css("top", null);
                    $addUl.css("width", "100%");
                    $addUl.css("position", "static");
                },2001);



            });

            $minus.click(function(){
                if($count <= 0){
                    return false;
                }
                var top = $count * 50;
                var $minusUl = $("#mould_" + $count);
                $count--;
                $minusUl.css("position", "absolute");
                $minusUl.css("width", "70%");
                $minusUl.css("top", top + "px");
                $minusUl .animate({left: "10px", top:"800px", opacity:"0",width:"10%"},700);

                setInterval(function(){
                    $minusUl.remove();
                },700);
            });


            // 表单验证
            $inputForm.validate({
                rules: {
                    name:"required",
                    chineseName:"required",
                    [#list 1.. 100 as i]
                        'entityAttributes[${i}].name':"required",
                        'entityAttributes[${i}].memo':"required",
                        'entityAttributes[${i}].type':"required",
                    [/#list]

                }
            });



        });
    </script>
    <style type="text/css">
        body{
            font-family: "Microsoft YaHei", "微软雅黑", MicrosoftJhengHei, "华文细黑", Helvetica, Arial, sans-serif;
            overflow-x: hidden;
        }
        .core{
            margin: 30px auto;
            width: 76%;
            opacity: 0.6;
            background: rgb(160, 113, 85);
        }
        ul{
            width: 100%;
            border-bottom: 1px solid #333;

        }
        ul li{
            width: 16%;
            float: left;
            text-align: center;
        }
        .title{
            height: 50px;
        }
        .title li{
            height: 30px;
            line-height: 30px;
            color: blanchedalmond;
            font-size: 18px;
            font-weight: bolder;
            margin: 10px 0;
        }
        .name{
            height: 50px;
            background: #b9873b;
        }
        .name li{
            height: 30px;
            line-height: 30px;
            color: blanchedalmond;
            font-size: 18px;
            font-weight: bolder;
            margin: 10px 0;
        }
        .record{
            height: 50px;
        }
        .record li{
            height: 30px;
            margin: 10px 0;
            background: rgb(160, 113, 85);
        }
        input{
            height: 26px;
            font-size: 16px;
            border-radius:20px;
            padding-left: 10px;
        }
        select{
            height: 32px;
            font-size: 16px;
        }
        .button{
            margin: 5px 20px;
            background: antiquewhite;
        }
        #add{
            position: absolute;
            right: 119px;
            top: 54px;
        }
        #minus{
            position: absolute;
            left: 119px;
            top: 70px;
        }
        #dustbin{
            position: absolute;
            left: 10px;
            bottom: 10px;
        }
        #create{
            position: absolute;
            right: 10px;
            bottom: 10px;
        }
    </style>
</head>
<body background="${base}/resources/admin/images/background.jpg">
<img src="${base}/resources/admin/images/add.jpg" alt="添加一行" title="添加一行" id="add" width="40" height="40"/>
<img src="${base}/resources/admin/images/minus.png" alt="删除一行" title="删除一行" id="minus"  width="40" height="12"/>
<img src="${base}/resources/admin/images/dustbin.jpg" alt="删除一行" title="删除一行" id="dustbin"  width="150" height="150"/>
<img src="${base}/resources/admin/images/create.jpg" alt="删除一行" title="删除一行" id="create"  width="150" height="150"/>
<div class="core">
    <form id="inputForm" action="create.jhtml" method="post">
        <div id="box">
            <ul class="name">
                <li>对象名称</li>
                <li><input type="text" name="name"/></li>
                <li>中文名称</li>
                <li><input type="text" name="chineseName"/></li>
                <li></li>
                <li></li>
            </ul>
            <ul class="title">
                <li>名称</li>
                <li>备注</li>
                <li>类型</li>
                <li>是否必填</li>
                <li>最大长度</li>
                <li>以此为筛选</li>
            </ul>
            <ul class="record">
                <li ><input type="text" name="entityAttributes[0].name"/></li>
                <li><input type="text" name="entityAttributes[0].memo"/></li>
                <li>
                    <select name="entityAttributes[0].type">
                        <option value="String">String</option>
                        <option value="Integer">Integer</option>
                        <option value="Long">Long</option>
                        <option value="Float">Float</option>
                        <option value="BigDecimal">BigDecimal</option>
                        <option value="Boolean">Boolean</option>
                        <option value="Date">Date</option>
                        <option value="byte">byte</option>
                        <option value="short">short</option>
                        <option value="int">int</option>
                        <option value="long">long</option>
                        <option value="float">float</option>
                        <option value="double">double</option>
                        <option value="boolean">boolean</option>
                        <option value="char">char</option>
                    [#list entityList as entity]
                        <option value="${entity}">${entity}</option>
                    [/#list]
                    </select>
                </li>
                <li>
                    <input type="checkbox" name="entityAttributes[0].isRequired" value="true" />
                </li>
                <li>
                    <input type="text" name="entityAttributes[0].length"  onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"  />
                </li>
                <li>
                    <select name="entityAttributes[0].isSelect">
                        <option value="false">否</option>
                        <option value="true">是</option>
                    </select>
                </li>
            </ul>
        </div>
       <div class="submit">
           <input type="submit" class="button" value="${message("admin.common.submit")}" />
       </div>
    </form>
</div>

<ul class="record hidden" id="mould">
    <li><input type="text" name="name1"/></li>
    <li><input type="text" name="memo1"/></li>
    <li>
        <select name="type1">
            <option value="String">String</option>
            <option value="Integer">Integer</option>
            <option value="Long">Long</option>
            <option value="Float">Float</option>
            <option value="BigDecimal">BigDecimal</option>
            <option value="Boolean">Boolean</option>
            <option value="Date">Date</option>
            <option value="byte">byte</option>
            <option value="short">short</option>
            <option value="int">int</option>
            <option value="long">long</option>
            <option value="float">float</option>
            <option value="double">double</option>
            <option value="boolean">boolean</option>
            <option value="char">char</option>
        [#list entityList as entity]
            <option value="${entity}">${entity}</option>
        [/#list]
        </select>
    </li>
    <li>
        <input type="checkbox" name="isRequired1" value="true"  />
    </li>
    <li>
        <input type="text" name="length1" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"  />
    </li>
    <li>
        <select name="isSelect1">
            <option value="false">否</option>
            <option value="true">是</option>
        </select>
    </li>
</ul>
</body>
</html>