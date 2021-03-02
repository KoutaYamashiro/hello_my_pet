<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
                <h2>id : ${pet.id}のペット情報　編集ページ</h2>
                <form method="POST" enctype="multipart/form-data" action="<c:url value='/pets/update' />">
                    <c:import url="_form.jsp" />
                </form>

                <p>
                    <a id=destroy href="#" onclick="confirmDestroy();">飼い主が決まったと更新する</a>
                </p>
                <form method="POST" action="<c:url value='/pets/destroy' />">
                    <input type="hidden" name="_token" value="${_token}" />
                    <input type="hidden" name="pet_id" value="${pet.id}" />
                </form>
                <script>
                    function confirmDestroy() {
                        if(confirm("本当によろしいですか？")) {
                            document.forms[1].submit();
                        }
                    }
                </script>

        <p><a id=all_pets href="<c:url value='/pets/index' />">ペット一覧に戻る</a></p>
    </c:param>
</c:import>