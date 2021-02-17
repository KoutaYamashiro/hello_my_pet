<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <div id="image">
            <img class="pets" src="<c:url value='/images/mypets/tokage01.jpg' />">
        </div>
        <h2>Pets List</h2>
            <table id="pet_list">
            <c:forEach var="pet" items="${pets}" varStatus="status">
            <tbody class="row${status.count % 2}">
                    <tr>
                            <th>ペット写真</th>
                            <td class="pet_image"><img src="https://yamashiro-test-20200114.s3-ap-northeast-1.amazonaws.com/uploaded/${pet.pet_image}"></td>
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
                            </td>
                    </tr>
                    <tr>
                            <th>いいね数</th>
                            <td class="favorite"><c:out value="${favoritesCount}" /></td>
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
                            </c:choose></td>
                    </tr>
            </tbody>
            </c:forEach>
        </table>

        <div id="pagination">
            （全 ${pets_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((pets_count - 1) / 10) + 1}" step="1">
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

    </c:param>
</c:import>