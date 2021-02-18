package controllers.replies;

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
import utils.DBUtil;

/**
 * Servlet implementation class RepliesNewServlet
 */
@WebServlet("/replies/new")
public class RepliesNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RepliesNewServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("_token", request.getSession().getId());

        EntityManager em = DBUtil.createEntityManager();

        // 返信を生成
        Reply r = new Reply();

        // 問い合わせIDを取得
        Contact contact = em.find(Contact.class, Integer.parseInt(request.getParameter("id")));
        // 値をセット
        r.setContact(contact);

        request.setAttribute("reply", r);
        request.setAttribute("contact", contact);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/replies/new.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
