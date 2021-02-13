<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when
                test="${sessionScope.login_user.admin_flag == 1 ||
                            (sessionScope.login_user.admin_flag == 0 && sessionScope.login_user.id == user.id)}">
                <h2>プロフィール情報 詳細ページ</h2>
                <table>
                    <tbody>
                        <tr>
                            <th>ユーザーID</th>
                            <td><c:out value="${user.id}" /></td>
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
                            <th>登録種別</th>
                            <td><c:choose>
                                    <c:when test="${user.admin_flag == 1}">管理者</c:when>
                                    <c:otherwise>ユーザー</c:otherwise>
                                </c:choose></td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td><fmt:formatDate value="${user.created_at}"
                                    pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td><fmt:formatDate value="${user.updated_at}"
                                    pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        </tr>
                    </tbody>
                </table>
                <p>
                    <a id=profile_edit
                        href="<c:url value='/users/edit?id=${user.id}' />">ユーザーの情報を変更する</a>
                </p>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p>
            <a id=logout href="<c:url value='/logout' />">ログアウト</a>
        </p>
        <p>
            <a id=all_users href="<c:url value='/users/index' />">ユーザー一覧に戻る</a>
        </p>
    </c:param>
</c:import>