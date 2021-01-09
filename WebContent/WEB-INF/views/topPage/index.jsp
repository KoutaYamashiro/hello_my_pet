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
        <h2>Hello My Pet  へようこそ</h2>
        <h3>【ペット　一覧】</h3>
        <table id="pet_list">
            <tbody>
                <tr>
                    <th class="pet_name">飼い主名</th>
                    <th class="pet_date">日付</th>
                    <th class="pet_pet_name">ペットの名前</th>
                    <th class="pet_favorite">♡  お気に入り</th>
                    <th class="pet_pet_image">ペットの画像</th>
                    <th class="pet_pet_type">ペットの種類</th>
                    <th class="pet_pet_breed">ペットの品種</th>
                    <th class="pet_action">詳細</th>
                </tr>
                <c:forEach var="pet" items="${pets}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="pet_name"><c:out value="${pet.user.name}" /></td>
                        <td class="pet_date"><fmt:formatDate value='${pet.pet_date}' pattern='yyyy-MM-dd' /></td>
                        <td class="pet_pet_name">${pet.pet_name}</td>
                        <td class="pet_pet_image">${""}</td>
                        <td class="pet_pet_type">${pet.pet_type}</td>
                        <td class="pet_pet_breed">${pet.pet_breed}</td>
                        <td class="pet_favorite">${""}</td>
                        <td class="pet_action"><a href="<c:url value='/pets/show?id=${pet.id}' />">詳細を確認する</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${pets_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((pets_count - 1) / 15) + 1}" step="1">
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
        <p><a href="<c:url value='/pets/new' />">新しいペットの登録</a></p>
    </c:param>
</c:import>