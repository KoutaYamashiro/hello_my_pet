<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>問い合わせID：${contact_id} 返信ページ</h2>

        <form method="POST" action="<c:url value='/replies/create' />">
            <c:import url="_form.jsp" />
        </form>

        <p><a href="<c:url value='/pets/show' />">問い合わせペット詳細</a></p>
        <p><a href="<c:url value='/contacts/show' />">問い合わせ詳細に戻る</a></p>
    </c:param>
</c:import>