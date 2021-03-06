<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <div id="image">
            <img class="cats" src="<c:url value='/images/mypets/tokage01.jpg' />">
        </div>
        <h2 id=pets_list>Pets List</h2>
        <table id="pet_list">
                <tbody class="row${status.count % 2}">
                    <tr>
                        <th>画像</th>
                        <td class="pet_image">${" "}</td>
                    </tr>
                    <tr>
                        <th>種類</th>
                        <td class="pet_breed">${pet.pet_breed}</td>
                    </tr>
                    <tr>
                        <th>誕生日</th>
                        <td class="birthday"><fmt:formatDate value='${""}'
                                pattern='yyyy-MM-dd' /></td>
                    </tr>
                    <tr>
                        <th>いいね数</th>
                        <td class="favorite"><c:out value="${pet.likes}" /></td>
                    </tr>
                    <tr>
                        <th>価格</th>
                        <td class="pet_price"><c:out value="${' '}" /></td>
                    </tr>
                    <tr>
                        <th>詳細</th>
                        <td><c:choose>
                                <c:when test="${pet.delete_flag == 1}">
                                    （削除済み）
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/pets/show?id=${pet.id}' />">詳細を表示</a>
                                </c:otherwise>
                            </c:choose></td>
                    </tr>
                </tbody>
        </table>

        <div id="pagination">
            （全 ${pets_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((pets_count - 1) / 15) + 1}"
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

    </c:param>
</c:import>