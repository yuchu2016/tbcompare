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
            <td>
                <select class="to">
                    <option value="0">--请选择--</option>
                    <c:forEach items="${databaseList}" var="database">
                        <option value="${database.id}">${database.name}</option>
                    </c:forEach>
                </select>
            </td>
            <td>表</td>
            <td>
                <select class="table-list-2" name="table">
                </select>
            </td>
        </tr>
        <tr>
            <td class="tableleft"></td>
            <td colspan="3">
                <a data-toggle="modal" href="#example" class="btn btn-primary" id="compare">比较</a>
                <%--<button type="submit" class="btn btn-primary" type="button">保存</button> &nbsp;&nbsp;<button type="button" class="btn btn-success" name="backid" id="backid">返回列表</button>--%>
            </td>
        </tr>
    </table>
    <!--模态框 -->
    <div id="example" class="modal hide fade in" style="display: none;">
        <div class="modal-header">
            <a class="close" data-dismiss="modal">×</a>
            <h3>表结构比较</h3>
        </div>
        <div class="modal-body">
            <div class="col-sm-6">
                <table class="table table-bordered m10 from-list">
                    <tr>
                        <th colspan="5" style="text-align:center;">源数据库</th>
                    </tr>
                    <tr>
                        <th width="100px" class="tableleft">字段名</th>
                        <th width="100px" class="tableleft">注释</th>
                        <th width="100px" class="tableleft">类型</th>
                        <th width="100px" class="tableleft">大小</th>
                        <th width="100px" class="tableleft">是否为空</th>
                        <th width="100px" class="tableleft">是否主键</th>
                    </tr>

                </table>
                <table class="table table-bordered m10 to-list">
                    <tr>
                        <th colspan="5" style="text-align:center;">目标数据库</th>
                    </tr>
                    <tr>
                        <th width="100px" class="tableleft">字段名</th>
                        <th width="100px" class="tableleft">注释</th>
                        <th width="100px" class="tableleft">类型</th>
                        <th width="100px" class="tableleft">大小</th>
                        <th width="100px" class="tableleft">是否为空</th>
                        <th width="100px" class="tableleft">是否主键</th>
                    </tr>

                </table>
            </div>
        </div>
        <div class="modal-footer">
            <a href="#" class="btn" data-dismiss="modal">取消</a>
        </div>
    </div>



</form>
</body>
</html>
<script src="/try/bootstrap/twitter-bootstrap-v2/js/bootstrap-modal.js"></script>
<script>
    $(function () {
        $(".from-select").change(function () {
            var id =$(this).val();
            $(".table-list").find("option").remove();
            $.post("/database/getTableList",{id:id},function (result) {
                $.each(result.data,function (i,field) {
                    $(".table-list").prepend("<option value="+field.name+">"+field.name+"</option>");
                })
            })
        });
        $(".to").change(function () {
            var id =$(this).val();
            $(".table-list-2").find("option").remove();
            $.post("/database/getTableList",{id:id},function (result) {
                $.each(result.data,function (i,field) {
                    $(".table-list-2").prepend("<option value="+field.name+">"+field.name+"</option>");
                })
            })
        });
        $("#compare").click(function () {
            var id =$(".from-select").val();
            var fromTablename=$(".table-list").val();
            var toId = $(".to").val();
            var toTableName=$(".table-list-2").val();
            //alert(id+tablename);
            $(".column-list").find(".column").remove();
            $.post("/database/getCompareList",{id:id,fromTableName:fromTablename,toId:toId,toTableName:toTableName},function (result) {
                $(".from-list").find("td").remove();
                $(".to-list").find("td").remove();
                if(result.data.fromList==null&&result.data.toList==null){
                    alert("字段名称无差别");
                }
                $.each(result.data.fromList,function (i,field) {
                    $(".from-list").append("<tr class='column'>\n" +
                        "     <td class='col-name'>"+field.name+"</td>\n" +
                        "     <td class='col-type'>"+field.remarks+"</td>\n" +
                        "     <td class='col-type'>"+field.type+"</td>\n" +
                        "     <td class='col-size'>"+field.size+"</td>\n" +
                        "     <td class='col-isNull'>"+field.isNull+"</td>\n" +
                        "     <td class='col-isPrimary'>"+field.isPrimary+"</td>\n" +
                        " </tr>");
                });
                $.each(result.data.toList,function (i,field) {
                    $(".to-list").append("<tr class='column'>\n" +
                        "     <td class='col-name'>"+field.name+"</td>\n" +
                        "     <td class='col-type'>"+field.remarks+"</td>\n" +
                        "     <td class='col-type'>"+field.type+"</td>\n" +
                        "     <td class='col-size'>"+field.size+"</td>\n" +
                        "     <td class='col-isNull'>"+field.isNull+"</td>\n" +
                        "     <td class='col-isPrimary'>"+field.isPrimary+"</td>\n" +
                        " </tr>");
                });
            });

        });
    });
</script>
