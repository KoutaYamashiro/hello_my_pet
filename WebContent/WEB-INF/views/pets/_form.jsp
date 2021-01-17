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
<label for="date">掲載日</label>
<br />
<input type="date" name="pet_date"
    value="<fmt:formatDate value='${pet.pet_date}' pattern='yyyy-MM-dd' />" />
<br />
<br />

<label for="pet_name">ペットの名前</label>
<br />
<input type="text" name="pet_name" value="${pet.pet_name}" />
<br />
<br />

<label for="pet_breed">ペットの品種</label>
<br />
<input type="text" name="pet_breed" value="${pet.pet_breed}" />
<br />
<br />

<label for="file">ペットの画像</label>
<br />
    <input class="bottom" type="file" name="file" value="${pet.image_url}"/><br />
<br />

<p>
<label>ペットの種類</label>
<br />
<select  class="pet_type" name="pet_type">
<option value="ネコ">ネコ</option>
<option value="イヌ">イヌ</option>
<option value="小動物">小動物（ウサギ・ハムスターなど）</option>
<option value="鳥類">鳥類</option>
<option value="爬虫類">爬虫類</option>
<option value="その他">その他</option>
</select>
</p>

<label for="visit_area">見学地域</label>
<br />
<input type="text" name="visit_area" value="${pet.visit_area}" />
<br />
<br />

<label for="date">誕生日</label>
<br />
<input type="date" name="birthday"
    value="<fmt:formatDate value='${pet.birthday}' pattern='yyyy-MM-dd' />" />
<br />
<br />

<label for="appeal_point">アピールポイント</label>
<br />
<textarea name="appeal_point" rows="10" cols="50">${pet.appeal_point}</textarea>
<br />
<br />

<input type="hidden" name="_token" value="${_token}" />
<button type="submit">登録</button>