<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>ユーザー一覧ページ</h2>
        <table id="user_list">
            <tbody>
                <tr class=row>
                    <th>ユーザー番号</th>
                    <th>名前</th>
                    <th>登録種別</th>
                    <th>詳細</th>
                </tr>
                <c:forEach var="user" items="${users}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td><c:out value="${user.code}" /></td>
                        <td><c:out value="${user.name}" /></td>
                        <td>
                                <c:choose>
                                    <c:when test="${user.admin_flag == 0}">管理者</c:when>
                                    <c:when test="${user.admin_flag == 1}">ユーザー</c:when>
                                    <c:otherwise>ペットオーナー</c:otherwise>
                                </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${user.delete_flag == 1}">
                                    （退会されています。）
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/users/show?id=${user.id}' />">詳細を確認する</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${users_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((users_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/users/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/users/new' />">新しいユーザーの登録</a></p>

    </c:param>
</c:import>
