<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${pet != null}">
                <h2>id : ${pet.id}のペット 詳細ページ</h2>

                 <table id="pet_list">
            <tbody class="row${status.count % 2}">
                    <tr>
                            <th>画像 仮処置</th>
                            <td class="pet_image"><c:out value="" /></td>
                    </tr>
                    <tr>
                            <th>種類</th>
                            <td class="pet_breed">${pet.pet_breed}</td>
                    </tr>
                    <tr>
                            <th>誕生日</th>
                            <td class="birthday">
                                 <fmt:formatDate value='${pet.birthday}' pattern='yyyy-MM-dd' />
                            </td>
                    </tr>
                    <tr>
                            <th>いいね数</th>
                            <td class="favorites"><c:out value="${pet.favorits}" /></td>
                    </tr>
                    <tr>
                            <th>価格</th>
                            <td class="pet_price"><c:out value="${pet.pet_price}" />円（税込み）</td>
                    </tr>
                    <tr>
                            <th>アピール</th>
                            <td>
                                <pre><c:out value="${report.appeal_point}" /></pre>
                            </td>
                    </tr>
            </tbody>
        </table>

                </c:when>
                    <c:otherwise>
                        <h2>お探しのペットデータは見つかりませんでした。</h2>
                    </c:otherwise>
        </c:choose>

        <!-- 管理者表示 -->
        <c:if test="${sessionScope.login_user.admin_flag == 1}">
                <p>
                     <a href="<c:url value="/pets/edit?id=${pet.id}" />">ペット情報を更新する</a>
                </p>
                <p>
                     <a href="<c:url value="/pets/index" />">ペット 一覧ページに戻る</a>
               </p>
        </c:if>

        <!-- ユーザー表示 -->
        <c:if test="${sessionScope.login_user.admin_flag == 0}">
                <!-- いいね　ボタンを追加 -->
                <c:if test="${sessionScope.login_user.id != pet.user.id}">
                    <form method="POST" action="<c:url value='/favorits/create' />">
                        <button type="submit" name="favorits" value="${1}">いいね</button>
                    </form>
                </c:if>
                <!-- お問い合わせ　ボタンを追加 -->
                <c:if test="${sessionScope.login_user.id != pet.user.id}">
                    <form method="POST" action="<c:url value='/contents/create' />">
                        <button type="submit" name="favorits">✉この仔について問い合わせる</button>
                    </form>
                    <p>※気に入った仔がいましたら、まずはフォームからご連絡ください。</p>
                    <p> 後日ご来店いただき、店頭でのご購入・お引き渡しとなります。</p>
                    <p>※メールは24時間受付中です。お気軽にお問い合わせください。</p>
                </c:if>
                <p><a href="<c:url value="　" />">お気に入りペット一覧ページに戻る</a></p>
                <p><a href="<c:url value="/pets/index" />">トップページに戻る</a></p>
        </c:if>

    </c:param>
</c:import>