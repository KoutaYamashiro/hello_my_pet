<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${user != null}">
                <h2>id : ${user.id} の飼い主様情報　詳細ページ</h2>

                <table>
                    <tbody>
                        <tr>
                            <th>飼い主様　ID</th>
                            <td><c:out value="${user.code}" /></td>
                        </tr>
                        <tr>
                            <th>お名前</th>
                            <td><c:out value="${user.name}" /></td>
                        </tr>
                        <tr>
                            <th>登録種別</th>
                            <td>
                                <c:choose>
                                    <c:when test="${user.admin_flag == 1}">ペットハウス</c:when>
                                    <c:otherwise>飼い主様</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td>
                                <fmt:formatDate value="${user.created_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td>
                                <fmt:formatDate value="${user.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                    </tbody>
                </table>

                <p><a href="<c:url value='/users/edit?id=${user.id}' />">飼い主様の情報を編集する</a></p>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value='/users/index' />">全体ページに戻る</a></p>
    </c:param>
</c:import>