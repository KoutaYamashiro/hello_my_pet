<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>お問い合わせID：${contact.id} 返信ページ</h2>
         <table>
                    <tbody>
                        <tr>
                            <th>お問い合わせペットID</th>
                            <td><c:out value="${contact.pet.id}" /></td>
                        </tr>
                        <tr>
                            <th>名前</th>
                            <td class="user_name"><c:out value="${user.name}" /></td>
                        </tr>
                        <tr>
                            <th>メールアドレス</th>
                            <td><c:out value="${user.mail_address}" /></td>
                        </tr>
                        <tr>
                            <th>問い合わせ日時</th>
                            <td><fmt:formatDate value="${contact.created_at}" pattern="yyyy年MM月dd日 HH:mm" /></td>
                        </tr>
                        <tr>
                            <th>お問い合わせ内容</th>
                            <td>
                                <pre><c:out value="${contact.content}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>返信日時</th>
                            <td><fmt:formatDate value="${仮}" pattern="yyyy年MM月dd日 HH:mm" /></td>
                        </tr>
                        <tr>
                            <th>返信者</th>
                            <td><c:out value="${仮}" /></td>
                        </tr>
                        <tr>
                            <th>返信内容</th>
                            <td>
                                <pre><c:out value="${仮}" /></pre>
                            </td>
                        </tr>
                    </tbody>
                </table><br />


        <form method="POST" action="<c:url value='/replies/create' />">
            <c:import url="_form.jsp" />
        </form>

        <p><a href="<c:url value='/pets/show' />">問い合わせペット詳細</a></p>
        <p><a href="<c:url value='/contacts/show' />">問い合わせ詳細に戻る</a></p>
    </c:param>
</c:import>