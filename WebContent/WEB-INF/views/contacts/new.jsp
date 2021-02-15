<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
<c:param name="content">
         <h2>✉　お問い合わせ</h2>
         <p> 当店のペットに関すること、</p>
         <p>ご相談、ご質問等、お気軽にお問い合わせください。</p>
         <br /><br />

        <form method="POST" action="<c:url value='/contacts/create' />">
            <c:import url="_form.jsp" />
        </form>

        <p><a href="<c:url value='/pets/show' />">問い合わせペット詳細</a></p>
        <br />
        <p><a href="<c:url value='/' />">ペット一覧ページに戻る</a></p>
    </c:param>
</c:import>