<%--
  Created by IntelliJ IDEA.
  User: luqinglin
  Date: 2017/12/20
  Time: 10:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<div class="form-inline definewidth m20">
    <%--机构名称：--%>
    <%--<input type="text" name="rolename" id="rolename"class="abc input-default" placeholder="" value="">&nbsp;&nbsp;--%>
    <a href="/database/addView"><button type="submit" class="btn btn-primary">添加数据源</button></a>
        <%--&nbsp;&nbsp; <button type="button" class="btn btn-success" id="addnew">新增机构</button>--%>
</div>
<table class="table table-bordered table-hover definewidth m10" >
    <thead>
    <tr>
        <th>数据源名称</th>
        <th>驱动</th>
        <th>url</th>
        <th>用户名</th>
        <th>密码</th>
        <th>操作</th>
    </tr>
    </thead>
    <c:forEach items="${databaseList}" var="database">
    <tr>
        <td>${database.name}</td>
        <td>${database.driver}</td>
        <td>${database.url}</td>
        <td>${database.username}</td>
        <td>${database.password}</td>
        <td><a href="/database/editView?id=${database.id}">编辑</a>
            <a href="/database/delete?id=${database.id}">删除</a>
        </td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
<script>
    $(function () {

        $('#addnew').click(function(){

            window.location.href="add.html";
        });


    });

    function del(id)
    {


        if(confirm("确定要删除吗？"))
        {

            var url = "index.html";

            window.location.href=url;

        }




    }
</script>
