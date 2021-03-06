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
                    <tbody class="row">
                        <tr>
                            <th>ペット写真</th>
                            <td class="pet_image"><img
                                src="https://yamashiro-test-20200114.s3-ap-northeast-1.amazonaws.com/uploaded/${pet.pet_image}"></td>
                        </tr>
                        <tr>
                            <th>種類</th>
                            <td class="pet_breed">${pet.pet_breed}</td>
                        </tr>
                        <tr>
                            <th>誕生日</th>
                            <td class="birthday"><fmt:formatDate value='${pet.birthday}'
                                    pattern='yyyy年MM月dd日' /> 生まれ</td>
                        </tr>
                        <tr>
                            <th>いいね数</th>
                            <td class="favorites"><c:out value="${favoritesCount}" /></td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td><fmt:formatDate value="${pet.created_at}" pattern="yyyy年MM月dd日 HH:mm" /></td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td><fmt:formatDate value="${pet.updated_at}" pattern="yyyy年MM月dd日 HH:mm" /></td>
                        </tr>
                        <tr>
                            <th>生体価格</th>
                            <td class="pet_price"><c:out value="${pet.pet_price}" />円（税込み）</td>
                        </tr>
                        <tr>
                            <th>アピールポイント</th>
                            <td>
                                <pre><c:out value="${pet.appeal_point}" /></pre>
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
                <a id=update href="<c:url value="/pets/edit?id=${pet.id}" />">ペット情報を更新する</a>
                <input type="hidden" name="pet_id" value="${pet.id}">
            </p>
            <p>
                <a id=all_pets href="<c:url value="/pets/index" />">ペット 一覧ページに戻る</a>
            </p>
        </c:if>
        <!-- ユーザー表示 -->
        <c:if test="${sessionScope.login_user.admin_flag == 0}"><br />
            <!-- いいね　ボタン -->
                <c:choose>
                <c:when test="${checkMyFavoriteCount == 0}">
                    <td class="favorite">
                        <form method="POST" action="<c:url value='/favorites/create' />">
                            <input type="hidden" name="pet_id" value="${pet.id}">
                            <button type="submit" name="favorite">いいね</button>
                        </form>
                    </td>
                </c:when>
                <c:otherwise>
                    <td class="favorite">
                        <form method="POST" action="<c:url value='/favorites/destroy' />">
                            <input type="hidden" name="pet_id" value="${pet.id}">
                            <button type="submit" name="favorite">いいね解除</button>
                        </form>
                    </td>
                </c:otherwise>
            </c:choose>
            <c:if test="${favorite_count}">

            </c:if>
            <br>
            <!-- お問い合わせ　ボタン -->
            <form method="POST"
                action="<c:url value='/contacts/new?id=${pet.id}' />">
                <input type="hidden" name="pet_id" value="${pet.id}">
                <button type="submit" name="contents">✉この仔について問い合わせる</button>
            </form>
            <p>※気に入った仔がいましたら、まずはフォームからご連絡ください。</p>
            <p>後日ご来店いただき、店頭でのご購入・お引き渡しとなります。</p>
            <p>※メールは24時間受付中です。お気軽にお問い合わせください。</p>
            <p>
                <a id=index href="<c:url value="/favorites/index" />">お気に入りペット一覧ページに戻る</a>
            </p>
            <p>
                <a id=index href="<c:url value="/pets/index" />">トップページに戻る</a>
            </p>
        </c:if>

    </c:param>
</c:import>