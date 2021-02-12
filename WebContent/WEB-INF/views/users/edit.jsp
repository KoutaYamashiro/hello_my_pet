<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when
               test="${user != null && sessionScope.login_user.admin_flag == 1 ||
                            (sessionScope.login_user.admin_flag == 0 && sessionScope.login_user.id == user.id)}">
                <h2>id : ${user.id} のプロフィール情報 編集ページ</h2>
                <p>
                    ※パスワードは変更する場合のみ入力してください。<br />&nbsp;&nbsp;
                </p>
                <form method="POST" action="<c:url value='/users/update' />">
                    <c:import url="_form.jsp" />
                </form>
                <p id=admin>
                    ※管理者で登録するのは従業員のみです。<br />&nbsp;&nbsp; すべてのペット情報が編集できます。
                </p>

                <p>
                    <a id=destroy href="#" onclick="confirmDestroy();">退会する</a>
                </p>
                <form method="POST" action="<c:url value='/users/destroy' />">
                    <input type="hidden" name="_token" value="${_token}" />
                </form>

                <script>
                    function confirmDestroy() {
                        if (confirm("本当に退会してよろしいですか？")) {
                            document.forms[1].submit();
                        }
                    }
                </script>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p>
            <a id=all_pages href="<c:url value='/' />">トップページに戻る</a>
        </p>
    </c:param>
</c:import>