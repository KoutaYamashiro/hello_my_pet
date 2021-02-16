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
                <h1><a class="hello" href="<c:url value='/' />">ハロー！My ペット</a></h1>&nbsp;&nbsp;&nbsp;
                <c:choose>
                     <c:when test="${sessionScope.login_user != null && sessionScope.login_user.admin_flag == 1}">
                     <div id="header_menu">
                            <a href="<c:url value='/users/index' />">ユーザー一覧</a>&nbsp;&nbsp;
                            <a href="<c:url value='/pets/index' />">ペット一覧</a>&nbsp;&nbsp;
                            <a href="<c:url value='仮' />">お問い合わせ一覧</a>&nbsp;&nbsp;
                            <div id="user_name">
                                <a href="<c:url value='/users/show?id=${login_user.id}' />">
                                    <c:out value="${sessionScope.login_user.name}" />様プロフィール
                                </a>&nbsp;&nbsp;
                                <a href="<c:url value='/logout' />">ログアウト</a>
                           </div>
                    </div>
                    </c:when>
                    <c:when test="${sessionScope.login_user != null && sessionScope.login_user.admin_flag == 0}">
                    <div id="header_menu">
                              <a href="<c:url value='/favorites/index' />">いいねしたペット一覧</a>&nbsp;&nbsp;
                              <a href="<c:url value='/contacts/index' />">お問い合わせ中</a>&nbsp;&nbsp;
                              <div id="user_name">
                                <a href="<c:url value='/users/show?id=${login_user.id}' />">
                                    <c:out value="${sessionScope.login_user.name}" />様プロフィール
                                </a>&nbsp;&nbsp;
                                <a href="<c:url value='/logout' />">ログアウト</a>
                              </div>
                    </div>
                    </c:when>
                    <c:otherwise>
                              <div id="user_name">
                                <a href="<c:url value='/login' />">ログイン</a>&nbsp;&nbsp;
                                <a href="<c:url value='/users/new' />">新規登録</a>
                              </div>
                    </c:otherwise>
                </c:choose>

        </div>
        <div id="content">${param.content}</div>
        <div id="footer">by Kota Yamashiro.</div>
    </div>
</body>
</html>