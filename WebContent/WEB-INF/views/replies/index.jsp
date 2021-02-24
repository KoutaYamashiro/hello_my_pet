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
        <h2>返信　一覧</h2>
        <table id="reply_list">
            <tbody>
                <tr>
                    <th class="reply_name">氏名</th>
                    <th class="created_at">返信日時</th>
                    <th class="reply_content">返信内容</th>
                    <th class="reply_action">操作</th>
                </tr>
                <c:forEach var="reply" items="${replies}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="reply_name"><c:out value="${reply.user.name}" /></td>
                        <td class="created_at"><fmt:formatDate value='${reply.created_at}' pattern='yyyy-MM-dd' /></td>
                        <td class="reply_content">${reply.content}</td>
                        <td class="reply_action"><a href="<c:url value='/replies/show?id=${reply.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${replies_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((replies_count - 1) / 10) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/replies/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a id=all_pages href="<c:url value='/' />">トップページへ戻る</a></p>

    </c:param>
</c:import>
