<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>お問い合わせ　一覧</h2>
        <table id="contact_list">
            <tbody>
                <tr>
                    <th class="contact_id">お問い合わせID</th>
                    <th class="contact_name">氏名</th>
                    <th class="contact_created">お問い合わせ日時</th>
                    <th class="contact_action">詳細</th>
                </tr>
                <c:forEach var="contact" items="${contacts}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="contact_di"><c:out value="${contact.user.id}" /></td>
                        <td class="contact_name"><c:out value="${contact.user.name}" /></td>
                        <td class="contact_created"><fmt:formatDate value='${contact.created_at}' pattern='yyyy-MM-dd HH:mm:ss ' /></td>
                        <td class="contact_action"><a href="<c:url value='/contacts/show?id=${contact.id}' />">詳細を表示</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${contacts_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((contacts_count - 1) / 10) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/contacts/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/' />">トップページへ戻る</a></p>

    </c:param>
</c:import>