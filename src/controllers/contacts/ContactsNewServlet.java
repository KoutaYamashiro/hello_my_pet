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
import models.Pet;
import utils.DBUtil;

/**
 * Servlet implementation class ContactsNewServlet
 */
@WebServlet("/contacts/new")
public class ContactsNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactsNewServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // CSRF対策
        request.setAttribute("_token", request.getSession().getId());
        // DAOインスタンスの生成
        EntityManager em = DBUtil.createEntityManager();

        // Contactクラスの生成
        Contact c = new Contact();
        // 問い合わせするペットのIDを取得
        Pet pet = em.find(Pet.class, Integer.parseInt(request.getParameter("id")));
        // ペット情報をセット
        c.setPet(pet);

        // リクエストスコープに各データをセット
        request.setAttribute("contact", c);
        request.setAttribute("pet", pet);

        // 画面遷移
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/contacts/new.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
