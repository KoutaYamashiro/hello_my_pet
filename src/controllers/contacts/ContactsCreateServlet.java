package controllers.contacts;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Contact;
import models.Pet;
import models.User;
import models.validators.ContactValidator;
import utils.DBUtil;

/**
 * Servlet implementation class ContactsCreateServlet
 */
@WebServlet("/contacts/create")
public class ContactsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactsCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // EntityManagerのオブジェクトを生成
           EntityManager em = DBUtil.createEntityManager();

            //   新しいお問い合わせを生成
            Contact c = new Contact();

            //  時間を生成
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            // 問い合わせするペットのIDを取得
            Pet pet = em.find(Pet.class, Integer.parseInt(request.getParameter("pet_id")));

            // Contactテーブルに値をセット
            c.setUser((User)request.getSession().getAttribute("login_user"));
            c.setPet(pet);
            c.setContent(request.getParameter("content"));
            c.setCreated_at(currentTime);

            List<String> errors = ContactValidator.validate(c);
            // エラーがある場合
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("contact", c);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/contacts/new.jsp");
                rd.forward(request, response);
            } else {
                // データベースを更新
                em.getTransaction().begin();
                em.persist(c);
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "お問い合わせしました！返信をお待ち下さい。");
                // お問い合わせ一覧へリダイレクト
                response.sendRedirect(request.getContextPath() + "/contacts/index");
            }
        }
}





