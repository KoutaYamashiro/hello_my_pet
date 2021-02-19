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
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        // ログインユーザーを取得
        User login_user = (User)request.getSession().getAttribute("login_user");

        // ページネーション
        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }

        // ユーザーがお問い合わせした内容取得
        List<Contact> contact_pets = em.createNamedQuery("getMyContacts", Contact.class)
                                  .setParameter("user", login_user)
                                  .setFirstResult(10 * (page - 1))
                                  .setMaxResults(10)
                                  .getResultList();
        System.out.println("*** 取得チェック***" + contact_pets);

        // ユーザーがお問い合わせした数をカウント
        long contacts_count = (long)em.createNamedQuery("getMyContactsCount", Long.class)
                                     .setParameter("user", login_user)
                                     .getSingleResult();
        System.out.println("*** カウントチェック***" + contacts_count);

        em.close();

        // 値をセット
        request.setAttribute("contact_pets", contact_pets);
        request.setAttribute("contacts_count", contacts_count);
        request.setAttribute("page", page);

        // flushがあった場合のみ表示
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/contacts/index.jsp");
        rd.forward(request, response);
    }
}
