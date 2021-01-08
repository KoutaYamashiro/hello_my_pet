<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<label for="date">日付</label><br />
<input type="date" name="pet_date" value="<fmt:formatDate value='${pet.pet_date}' pattern='yyyy-MM-dd' />" />
<br /><br />


<label for="name">ペットのお名前</label><br />
<input type="text" name="pet_name" value="${pet.pet_name}" />
<br /><br />

<label for="type">ペットの種類</label><br />
<input type="text" name="pet_type" value="${pet.pet_type}" />
<br /><br />

<label for="breed">ペットの品種</label><br />
<input type="text" name="pet_breed" value="${pet.pet_breed}" />
<br /><br />

<label for="age">年齢</label><br />
<input type="text" name="age" value="${pet.age}" />
<br /><br />

<label for="home_town">住んでいる場所</label><br />
<input type="text" name="home_town" value="${pet.home_town}" />
<br /><br />

<label for="nemo">メモ</label><br />
<textarea name="nemo" rows="10" cols="50">${pet.nemo}</textarea>
<br /><br />

<input type="hidden" name="_token" value="${_token}" />
<button type="submit">登録</button>