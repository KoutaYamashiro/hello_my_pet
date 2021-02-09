<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:if test="${errors != null}">
    <div id="flush_error">
            入力内容にエラーがあります。<br />
            <c:forEach var="error" items="${errors}">
                ・<c:out value="${error}" />
                <br />
            </c:forEach>
    </div>
</c:if>

<label for="pet_breed">ペットの種類</label><br />
<input type="text" name="pet_breed" value="${pet.pet_breed}" />
<br /><br />

<label for="pet_image">ペットの画像</label><br />
<input type="file" name="file" value="${pet.pet_image}" />
<br /><br />

<label for="date">誕生日</label><br />
<input type="date" name="birthday"
    value="<fmt:formatDate value='${pet.birthday}' pattern='yyyy-MM-dd' />" />
<br /><br />

<label for="pet_price">ペットの金額</label><br />
<input type="text" name="pet_price" value="${pet.pet_price}" />
<br /><br />

<label for="appeal_point">アピールポイント</label><br />
<textarea name="appeal_point" rows="10" cols="50">${pet.appeal_point}</textarea>
<br /><br />

<input type="hidden" name="_token" value="${_token}" />
<button type="submit">登録</button>