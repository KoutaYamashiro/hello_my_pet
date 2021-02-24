package controllers.replies;

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
import models.Reply;
import models.User;
import models.validators.ReplyValidator;
import utils.DBUtil;

/**
 * Servlet implementation class RepliesCreateServlet
 */
@WebServlet("/replies/create")
public class RepliesCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RepliesCreateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String _token = (String) request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId())) {
            // EntityManagerのオブジェクトを生成
            EntityManager em = DBUtil.createEntityManager();

            // 新しい返信を生成
            Reply r = new Reply();

            //  時間を生成
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            // 返信する問い合わせのIDを取得
            Contact contact = em.find(Contact.class, Integer.parseInt(request.getParameter("contact_id")));

            // テーブルに値をセット
            r.setUser((User) request.getSession().getAttribute("login_user"));
            r.setContent(request.getParameter("content"));
            r.setContact(contact);
            r.setCreated_at(currentTime);

            List<String> errors = ReplyValidator.validate(r);

            // エラーがある場合
            if (errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("reply", r);

                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/replies/new.jsp");
                rd.forward(request, response);
            } else {
                // データベースを更新
                em.getTransaction().begin();
                em.persist(r);
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "問い合わせに返信しました。");
                // お問い合わせ一覧へリダイレクト
                response.sendRedirect(request.getContextPath() + "/contacts/index");
            }
        }
    }
}
