<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>
    </div>
</c:if>

<label for="content">返信内容</label><br />
<textarea name="content" rows="10" cols="50">${reply.content}</textarea>
<br /><br />

<input type="hidden" name="_token" value="${_token}" />
<input type="hidden" name="contact_id" value="${contact.id}">
<button type="submit">返信</button>