<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${sessionScope.login_user.admin_flag == 1 ||
                            (sessionScope.login_user.admin_flag == 0 && sessionScope.login_user.id == user.id)}">
                <h2>お問い合わせ詳細ページ</h2>
                <table>
                    <tbody>
                        <tr>
                            <th>お問い合わせペットID</th>
                            <td><c:out value="${pet.id}" /></td>
                        </tr>
                        <tr>
                            <th>名前</th>
                            <td><c:out value="${user.name}" /></td>
                        </tr>
                        <tr>
                            <th>メールアドレス</th>
                            <td><c:out value="${user.mail_address}" /></td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td><fmt:formatDate value="${contac.created_at}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        </tr>
                    </tbody>
                </table>
                    <c:if test="${sessionScope.login_user.id == contact.user.id}">
                        <p><a href="<c:url value="/contacts/edit?id=${contact.id}" />">お問い合わせする</a></p>
                    </c:if>
            </c:when>
            <c:otherwise>
                    <h2>お問い合わせは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>
        <p><a href="<c:url value='/pets/show?id=${pet.id}' />">問い合わせペット詳細</a></p>
        <p><a href="<c:url value="/contacts/index" />">問い合わせ一覧に戻る</a></p>
    </c:param>
</c:import>