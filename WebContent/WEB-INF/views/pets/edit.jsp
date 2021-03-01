<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
                <h2>id : ${pet.id}のペット情報　編集ページ</h2>
                <form method="POST" enctype="multipart/form-data" action="<c:url value='/pets/update' />">
                    <c:import url="_form.jsp" />
                </form>
                <p><a href="#" onclick="confirmDestroy();">このペットは家族が決まりました。</a></p>
                <form method="POST" action="<c:url value='/pets/destroy' />">
                    <input type="hidden" name="_token" value="${_token}" />
                </form>
                <script>
                    function confirmDestroy() {
                        if(confirm("決定でよろしいですか？")) {
                            document.forms[1].submit();
                        }
                    }
                </script>

        <p><a href="<c:url value='/pets/show' />">ペット詳細に戻る</a></p>
    </c:param>
</c:import>