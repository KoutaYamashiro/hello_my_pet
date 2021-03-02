package controllers.contacts;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Contact;
import models.Reply;
import models.User;
import utils.DBUtil;

/**
 * Servlet implementation class ContactsShowServlet
 */
@WebServlet("/contacts/show")
public class ContactsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactsShowServlet() {
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

        // お問い合わせを取得
        Contact contact = em.find(Contact.class, Integer.parseInt(request.getParameter("id")));

        // 返信情報を取得
        Reply reply = em.find(Reply.class, Integer.parseInt(request.getParameter("id")));
        // DAOの破棄
        em.close();

        // リクエストスコープに各データをセット
        request.setAttribute("login_user", login_user);
        request.setAttribute("contact", contact);
        request.setAttribute("reply", reply);
        request.setAttribute("_token", request.getSession().getId());

        // 画面遷移
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/contacts/show.jsp");
        rd.forward(request, response);
    }

}
