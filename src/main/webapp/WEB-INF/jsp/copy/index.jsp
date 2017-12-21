<%--
  Created by IntelliJ IDEA.
  User: luqinglin
  Date: 2017/12/20
  Time: 12:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../Css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="../Css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="../Css/style.css" />
    <script type="text/javascript" src="../Js/jquery.js"></script>
    <script type="text/javascript" src="../Js/jquery.sorted.js"></script>
    <script type="text/javascript" src="../Js/bootstrap.js"></script>
    <script type="text/javascript" src="../Js/ckform.js"></script>
    <script type="text/javascript" src="../Js/common.js"></script>

    <style type="text/css">
        body {
            padding-bottom: 40px;
        }
        .sidebar-nav {
            padding: 9px 0;
        }

        @media (max-width: 980px) {
            /* Enable use of floated navbar text */
            .navbar-text.pull-right {
                float: none;
                padding-left: 5px;
                padding-right: 5px;
            }
        }


    </style>
</head>
<body>
<form action="/" method="post" class="definewidth m20">
    <table class="table table-bordered table-hover m10">
        <tr>
            <td width="10%" class="tableleft">源数据库</td>
            <td>
                <select class="from-select" name="from">
                    <option value="0">--请选择--</option>
                    <c:forEach items="${databaseList}" var="database">
                        <option value="${database.id}">${database.name}</option>
                    </c:forEach>
                </select>
            </td>
            <td>表</td>
            <td>
                <select class="table-list" name="table">
                </select>
            </td>
        </tr>
        <tr>
            <td class="tableleft">目标数据库</td>
            <td colspan="3">
                <select class="to">
                    <option value="0">--请选择--</option>
                    <c:forEach items="${databaseList}" var="database">
                        <option value="${database.id}">${database.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tableleft"></td>
            <td>
                <a data-toggle="modal" href="#example" class="btn btn-primary" id="copy">复制</a>
                <%--<button type="submit" class="btn btn-primary" type="button">保存</button> &nbsp;&nbsp;<button type="button" class="btn btn-success" name="backid" id="backid">返回列表</button>--%>
            </td>
        </tr>
    </table>
    <!--模态框 -->
    <div id="example" class="modal hide fade in" style="display: none;">
        <div class="modal-header">
            <a class="close" data-dismiss="modal">×</a>
            <h3>表结构复制</h3>
        </div>
        <div class="modal-body">
            <div class="col-sm-6">
                <table class="table table-bordered m10 column-list">
                    <tr>
                        <th width="100px" class="tableleft">字段名</th>
                        <th width="100px" class="tableleft">类型</th>
                        <th width="100px" class="tableleft">大小</th>
                        <th width="100px" class="tableleft">是否为空</th>
                        <th width="100px" class="tableleft">是否主键</th>
                    </tr>

                </table>
            </div>
        </div>
        <div class="modal-footer">
            <a href="#" class="btn btn-success tb-submit">提交</a>
            <a href="#" class="btn" data-dismiss="modal">取消</a>
        </div>
    </div>



</form>
</body>
</html>
<script src="/try/bootstrap/twitter-bootstrap-v2/js/bootstrap-modal.js"></script>
<script>
    $(function () {
        $('#backid').click(function(){
            window.location.href="index.html";
        });
        $(".from-select").change(function () {
            var id =$(this).val();
            $(".table-list").find("option").remove();
            $.post("/database/getTableList",{id:id},function (result) {
                $.each(result.data,function (i,field) {
                    $(".table-list").prepend("<option value="+field.name+">"+field.name+"</option>");
                })

            })

        });
        $("#copy").click(function () {
            var id =$(".from-select").val();
            var tablename=$(".table-list").val();
            var toId = $(".to").val();
            //alert(id+tablename);
            $(".column-list").find(".column").remove();
            $.post("/database/getColumnList",{id:id,tableName:tablename,toId:toId},function (result) {
                $.each(result.data,function (i,field) {
                    $(".column-list").append("<tr class='column'>\n" +
                        "     <td class='col-name'>"+field.name+"</td>\n" +
                        "     <td class='col-type'><select class='type-class' name='type' width='100px'><option value="+field.type+">"+field.type+"</option></select></td>\n" +
                        "     <td class='col-size'><input width='100px' type='text' name='size' value="+field.size+"> </td>\n" +
                        "     <td class='col-isNull'>"+field.isNull+"</td>\n" +
                        "     <td class='col-isPrimary'>"+field.isPrimary+"</td>\n" +
                        " </tr>");
                });
                $.post("/database/getColumn",{toId:toId},function (result) {
                    $.each(result.data,function (i,field) {
                        $(".type-class").append("<option value="+field.type+">"+field.type+"</option>");
                    })
                })
            });

        });
        $(".tb-submit").click(function () {
            var toId = $(".to").val();
            var tablename=$(".table-list").val();
            var jsonData = "[";
            $(".column").each(function(i){
                jsonData += "{\"name\":\""+$(this).find(".col-name").text()+"\",\"type\":\""+$(this).find(".col-type>select").val()+"\",\"size\":\""+$(this).find(".col-size>input").val()+
                    "\",\"isNull\":"+$(this).find(".col-isNull").text()+",\"isPrimary\":"+$(this).find(".col-isPrimary").text()+"},";
            });
            jsonData = jsonData.substring(0,jsonData.length-1);
            //jsonData=jsonData.TrimEnd(',');
            jsonData +="]";
            console.log(jsonData);
            //alert(jsonData);
            $.post("/database/createTable",{toId:toId,tableName:tablename,data:jsonData},function (result) {
                alert(result.msg);
            })
        });
    });
</script>
