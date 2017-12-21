<%--
  Created by IntelliJ IDEA.
  User: luqinglin
  Date: 2017/12/20
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<form action="/database/edit" method="post" class="definewidth m20">
    <table class="table table-bordered table-hover m10">
        <<input type="hidden" name="id" value="${database.id}"/>
        <tr>
            <td class="tableleft">名称</td>
            <td><input type="text" name="name" value="${database.name}"/></td>
        </tr>
        <tr>
            <td width="10%" class="tableleft">驱动</td>
            <td>
                <select name="driver">
                    <option value="${mysql.className}">${mysql.dbType}</option>
                    <option value="${postgrepsql.className}">${postgrepsql.dbType}</option>
                    <option value="${sqlserver.className}">${sqlserver.dbType}</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tableleft">jdbc-url</td>
            <td><input type="text" name="url" value="${database.url}"/></td>
        </tr>
        <tr>
            <td class="tableleft">用户名</td>
            <td><input type="text" name="username" value="${database.username}"/></td>
        </tr>
        <tr>
            <td class="tableleft">密码</td>
            <td><input type="text" name="password" value="${database.password}"/></td>
        </tr>
        <tr>
            <td class="tableleft"></td>
            <td>
                <button type="submit" class="btn btn-primary" type="button">保存</button> &nbsp;&nbsp;<button type="button" class="btn btn-success" name="backid" id="backid">返回列表</button>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
<script>
    $(function () {
        $('#backid').click(function(){
            window.location.href="/database/all";
        });

    });
</script>
