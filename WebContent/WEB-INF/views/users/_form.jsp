<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<label for="name">氏名</label><br />
<input type="text" name="name" value="${user.name}" />
<br /><br />

<label for="mail_address">メールアドレス</label><br />
<input type="text" name="mail_address" value="${user.mail_address}" />
<br /><br />

<label for="password">パスワード</label><br />
<input type="password" name="password" />
<br /><br />

<c:choose>
    <c:when test="${sessionScope.login_user.admin_flag == 1}">
        <label for="admin_flag">登録種別</label><br />
        <select name="admin_flag">
                  <option value="0"<c:if test="${user.admin_flag == 0}"> selected</c:if>>ユーザー</option>
                <option value="1"<c:if test="${user.admin_flag == 1}"> selected</c:if>>管理者</option>
        </select>
    </c:when>
    <c:otherwise>
           <input type="hidden" name="admin_flag" value="0" />
    </c:otherwise>
</c:choose>


<br /><br />

<input type="hidden" name="_token" value="${_token}" />
<button type="submit">登録</button>