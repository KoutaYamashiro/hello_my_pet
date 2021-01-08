<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがありました。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<label for="code">飼い主　番号</label><br />
<input type="text" name="code" value="${employee.code}" />
<br /><br />

<label for="name">お名前</label><br />
<input type="text" name="name" value="${employee.name}" />
<br /><br />

<label for="password">パスワード</label><br />
<input type="password" name="password" />
<br /><br />

<label for="admin_flag">登録種別</label><br />
<select name="admin_flag">
    <option value="0"<c:if test="${user.admin_flag == 0}"> selected</c:if>>飼い主</option>
    <option value="1"<c:if test="${user.admin_flag == 1}"> selected</c:if>>ペットハウス</option>
</select>
<br /><br />

<input type="hidden" name="_token" value="${_token}" />
<button type="submit">登録</button>