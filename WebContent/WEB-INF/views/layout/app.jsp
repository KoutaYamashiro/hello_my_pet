<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ハロー！My ペット</title>
<link rel="stylesheet" href="<c:url value='/css/reset.css' />">
<link rel="stylesheet" href="<c:url value='/css/style.css' />">
</head>
<body>
    <div id="wrapper">
        <div id="header">
            <div id="header_menu">
                <h1>
                    <a class="hello" href="<c:url value='/' />">ハロー！My ペット</a>
                </h1>&nbsp;&nbsp;&nbsp;
                <c:if test="${sessionScope.login_user != null}">
                    <c:if test="${sessionScope.login_user.admin_flag == 0}">
                        <a href="<c:url value='/users/index' />">ユーザー一覧</a>&nbsp;&nbsp;
                    </c:if>
                        <a href="<c:url value='/pets/index' />">ペット一覧</a>&nbsp;&nbsp;
                        <a href="<c:url value='仮' />">お問い合わせ一覧</a>&nbsp;&nbsp;
                </c:if>
            </div>
            <c:if test="${sessionScope.login_user != null}">
                <div id="user_name">
                    <c:out value="${sessionScope.login_user.name}" />様&nbsp;&nbsp;
                    <a href="<c:url value='/logout' />">ログアウト</a>
                </div>
            </c:if>
        </div>
        <div id="content">${param.content}</div>
        <div id="footer">by Kota Yamashiro.</div>
    </div>
</body>
</html>