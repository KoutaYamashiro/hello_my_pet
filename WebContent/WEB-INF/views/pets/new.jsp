<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>ペット 新規登録ページ</h2>

        <form method="POST"
            action="${pageContext.request.contextPath}/pets/create"
            enctype="multipart/form-data">
            <c:import url="_form.jsp" />
        </form>

        <p>
            <a id=all_pages href="<c:url value='/pets/index' />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>