<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>ペット達のページ</h2>
        <table id="pet_list">
            <tbody>
                <tr>
                    <th class="name">飼い主名</th>
                    <th class="date">日付</th>
                    <th class="pet_name">ペットのお名前</th>
                    <th class="pet_type">ペットの種類</th>
                    <th class="pet_breed">ペットの品種</th>
                    <th class="pet_action">詳細</th>
                </tr>
                <c:forEach var="pet" items="${pets}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="name"><c:out value="${pet.user.name}" /></td>
                        <td class="date"><fmt:formatDate value='${pet.date}' pattern='yyyy-MM-dd' /></td>
                        <td class="pet_name">${pet.name}</td>
                        <td class="pet_type">${pet.type}</td>
                        <td class="pet_breed">${pet.breed}</td>
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
                        <a href="<c:url value='/pets/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/pets/new' />">新しいペットの登録</a></p>

    </c:param>
</c:import>