package controllers.contacts;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Contact;
import models.User;
import utils.DBUtil;

/**
 * Servlet implementation class ContactsIndexServlet
 */
@WebServlet("/contacts/index")
public class ContactsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactsIndexServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // DAOインスタンスの生成
        EntityManager em = DBUtil.createEntityManager();

        // セッションスコープから login_user 情報を抜き出す
        User login_user = (User) request.getSession().getAttribute("login_user");

        // ページネーション
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        // ユーザーがお問い合わせした一覧を表示する
        List<Contact> contacts = em.createNamedQuery("getMyAllContacts", Contact.class)
                .setParameter("user", login_user)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .getResultList();

        // ユーザーのお問い合わせ数をカウント
        long contacts_count = (long) em.createNamedQuery("getMyContactsCount", Long.class)
                .setParameter("user", login_user)
                .getSingleResult();
        // DAOの破棄
        em.close();

        // リクエストスコープに各データをセット
        request.setAttribute("contacts", contacts);
        request.setAttribute("contacts_count", contacts_count);
        request.setAttribute("page", page);

        // セッションスコープにエラーメッセージがあるならば
        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            // エラーメッセージを削除
            request.getSession().removeAttribute("flush");
        }

        // 画面遷移
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/contacts/index.jsp");
        rd.forward(request, response);
    }
}
