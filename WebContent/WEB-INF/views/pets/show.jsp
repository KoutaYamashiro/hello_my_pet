<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${pet != null}">
                <h2>ペット　詳細ページ</h2>

                <table>
                    <tbody>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${pet.user.name}" /></td>
                        </tr>
                        <tr>
                            <th>日付</th>
                            <td><fmt:formatDate value="${pet.pet_date}" pattern="yyyy-MM-dd" /></td>
                        </tr>
                        <tr>
                            <th>ペットのお名前</th>
                            <td>
                                <pre><c:out value="${pet.pet_name}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>ペットの種類</th>
                            <td>
                                <pre><c:out value="${pet.pet_type}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>ペットの品種</th>
                            <td>
                                <pre><c:out value="${pet.pet_breed}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>年齢</th>
                            <td>
                                <pre><c:out value="${pet.age}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>住んでいる場所</th>
                            <td>
                                <pre><c:out value="${pet.home_town}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>メモ</th>
                            <td>
                                <pre><c:out value="${pet.nemo}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td>
                                <fmt:formatDate value="${pet.created_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td>
                                <fmt:formatDate value="${pet.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                    </tbody>
                </table>

                <c:if test="${sessionScope.login_user.id == pet.user.id}">
                    <p><a href="<c:url value="/pets/edit?id=${pet.id}" />">このペットの情報を編集する</a></p>
                </c:if>
            </c:when>
            <c:otherwise>
                <h2>お探しのペットデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value="/pets/index" />">ペット　一覧ページに戻る</a></p>
    </c:param>
</c:import>