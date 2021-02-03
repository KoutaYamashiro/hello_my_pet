<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>管理者　ユーザー新規登録ページ</h2>

        <form method="POST" action="<c:url value='/users/create' />">
            <c:import url="_form.jsp" />
        </form>

        <p><a id=all_pages href="<c:url value='/users/index' />">一覧ページに戻る</a></p>
    </c:param>
</c:import>