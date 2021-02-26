<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
<c:param name="content">
         <h2>✉　お問い合わせ</h2>
         <p> 当店のペットに関すること、</p>
         <p>ご相談、ご質問等、お気軽にお問い合わせください。</p>
         <br /><br />

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
         <br /><br />

        <form method="POST" action="<c:url value='/contacts/create' />">
            <c:import url="_form.jsp" />
        </form>
        <br />

        <p><a id=show_pet href="<c:url value='/pets/show?id=${pet.id}' />">問い合わせペット詳細</a></p>
        <p><a id=all_pages href="<c:url value='/' />">ペット一覧ページに戻る</a></p>
    </c:param>
</c:import>