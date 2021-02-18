<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${sessionScope.login_user.admin_flag == 1 ||
                            (sessionScope.login_user.admin_flag == 0 && sessionScope.login_user.id == contact.user.id)}">
                <h2>お問い合わせ詳細ページ</h2>
                <table>
                    <tbody>
                        <tr>
                            <th>お問い合わせペットID</th>
                            <td><c:out value="${contact.pet.id}" /></td>
                        </tr>
                        <tr>
                            <th>名前</th>
                            <c:choose>
                                    <c:when test="${sessionScope.login_user.admin_flag == 0}">
                                          <td class="user_name"><c:out value="${login_user.name}" /></td>
                                    </c:when>
                                    <c:otherwise>
                                           <td class="user_name"><c:out value="${user.name}" /></td>
                                    </c:otherwise>
                                </c:choose>
                        </tr>
                        <tr>
                            <th>メールアドレス</th>
                            <td><c:out value="${login_user.mail_address}" /></td>
                        </tr>
                        <tr>
                            <th>問い合わせ日時</th>
                            <td><fmt:formatDate value="${contact.created_at}" pattern="yyyy年MM月dd日 HH:mm" /></td>
                        </tr>
                        <tr>
                            <th>お問い合わせ内容</th>
                            <td>
                                <pre><c:out value="${contact.content}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>返信日時</th>
                            <td><fmt:formatDate value="${仮}" pattern="yyyy年MM月dd日 HH:mm" /></td>
                        </tr>
                        <tr>
                            <th>返信者</th>
                            <td><c:out value="${仮}" /></td>
                        </tr>
                        <tr>
                            <th>返信内容</th>
                            <td>
                                <pre><c:out value="${仮}" /></pre>
                            </td>
                        </tr>
                    </tbody>
                </table><br />
                    <c:if test="${sessionScope.login_user.id == contact.user.id}">
                        <form method="POST" action="<c:url value='/contacts/new?id=${contact.pet.id}' />">
                            <input type="hidden" name="pet_id" value="${contact.pet.id}">
                            <button type="submit" name="contents">問い合わせる</button>
                        </form>
                  </c:if>
                  <c:if test="${sessionScope.login_user.admin_flag == 1}">
                        <form method="POST" action="<c:url value='/replies/new?id=${contact.id}' />">
                            <input type="hidden" name="pet_id" value="${contact.id}">
                            <button type="submit" name="contents">問い合わせに返信する</button>
                        </form>
                  </c:if>
            </c:when>
            <c:otherwise>
                    <h2>お問い合わせは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>
        <p><a href="<c:url value='/pets/show?id=${contact.pet.id}' />">問い合わせペット詳細</a></p>
        <p><a href="<c:url value="/contacts/index" />">問い合わせ一覧に戻る</a></p>
    </c:param>
</c:import>