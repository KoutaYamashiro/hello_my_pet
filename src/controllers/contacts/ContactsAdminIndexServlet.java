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
 * Servlet implementation class ContactsAdminIndexServlet
 */
@WebServlet("/contacts/admin")
public class ContactsAdminIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactsAdminIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // DAOインスタンスの生成
        EntityManager em = DBUtil.createEntityManager();

        // ログインユーザーを取得
        User login_user = (User) request.getSession().getAttribute("login_user");

        // ページネーション
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        // お問い合わせ内容取得
        List<Contact> contacts = em.createNamedQuery("getAllContacts", Contact.class)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .getResultList();

        // お問い合わせ数カウント
        long contacts_count = (long) em.createNamedQuery("getContactsCount", Long.class)
                .getSingleResult();
        // DAOの破棄
        em.close();

        // リクエストスコープに各データをセット
        request.setAttribute("login_user", login_user);
        request.setAttribute("contacts", contacts);
        request.setAttribute("contacts_count", contacts_count);
        request.setAttribute("page", page);

        // セッションスコープにフラッシュメッセージがあるならば
        if (request.getSession().getAttribute("flush") != null) {
            // リクエストスコープにフラッシュメッセージをセットする
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            // リクエストスコープのフラッシュメッセージを削除
            request.getSession().removeAttribute("flush");
        }
        // 画面遷移
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/contacts/admin.jsp");
        rd.forward(request, response);
    }
}
