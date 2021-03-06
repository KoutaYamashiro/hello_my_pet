<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${pets_count == 0}">
                  <h3>いいねしたペットは見つかりませんでした。</h3>
            </c:when>
            <c:otherwise>
                <h2>いいねしたペット</h2>
                <table id="favorites_list">
                        <c:forEach var="pet" items="${favorite_pets}" varStatus="status">
                        <tbody class="row${status.count % 2}">
                            <tr>
                                <th>ペット写真</th>
                                <td class="pet_image">
                                    <img src="https://yamashiro-test-20200114.s3-ap-northeast-1.amazonaws.com/uploaded/${pet.pet_image}">
                                </td>
                            </tr>
                            <tr>
                                <th>種類</th>
                                <td class="pet_breed">${pet.pet_breed}</td>
                            </tr>
                            <tr>
                                <th>誕生日</th>
                                <td class="birthday">
                                <fmt:formatDate value='${pet.birthday}' pattern='yyyy年MM月dd日' />
                                 生まれ
                            </tr>
                            <tr>
                                <th>いいね数</th>
                                <td class="favorite"><c:out value="${fn:length(pet.favoritedUsers)}" /></td>
                            </tr>
                            <tr>
                                <th>生体価格</th>
                                <td class="pet_price"><c:out value="${pet.pet_price}" />円（税込み）</td>
                            </tr>
                            <tr>
                                <th>詳細</th>
                                <td class="delete_flag"><c:choose>
                                        <c:when test="${pet.delete_flag == 1}">
                                            <a id=details href="<c:url value='/pets/show?id=${pet.id}' />">（家族が決まりました）詳細</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a id=details href="<c:url value='/pets/show?id=${pet.id}' />">詳細を表示</a>
                                        </c:otherwise>
                                    </c:choose>
                               </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <div id="pagination">
                    （全 ${pets_count} 件）<br />
                    <c:forEach var="i" begin="1" end="${((pets_count - 1) / 10) + 1}"
                        step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <c:out value="${i}" />&nbsp;
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value='/?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
        <p>
            <a id=top_page href="<c:url value="/" />">トップページに戻る</a>
        </p>

    </c:param>
</c:import>