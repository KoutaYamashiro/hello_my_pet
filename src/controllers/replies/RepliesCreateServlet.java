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
        // String型の _tokenにパラメーターの_tokenを代入する
        String _token = (String) request.getParameter("_token");
        // _tokenがnullではなく、且つセッションIDと等しいならば
        if (_token != null && _token.equals(request.getSession().getId())) {
            // DAOインスタンスの生成
            EntityManager em = DBUtil.createEntityManager();

            // Replyを生成
            Reply r = new Reply();

            // 返信する問い合わせのIDを取得
            Contact contact = em.find(Contact.class, Integer.parseInt(request.getParameter("contact_id")));

            // 変数rに各情報をセットする
            r.setUser((User) request.getSession().getAttribute("login_user"));
            r.setContent(request.getParameter("content"));
            r.setContact(contact);

            // 現在日時を取得して変数pに作成日時にセットする
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            r.setCreated_at(currentTime);

            // バリデーター の呼び出し
            List<String> errors = ReplyValidator.validate(r);
            // errorsリストに1つでも追加されていたら
            if (errors.size() > 0) {
                // DAOの破棄
                em.close();
                // リクエストスコープに各データをセット
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("reply", r);
                request.setAttribute("errors", errors);
                // 画面遷移
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/replies/new.jsp");
                rd.forward(request, response);
            } else {
                // データベースに保存する
                em.getTransaction().begin();
                em.persist(r);
                em.getTransaction().commit();
                em.close();
                // セッションスコープにフラッシュメッセージをセットする
                request.getSession().setAttribute("flush", "問い合わせに返信しました。");
                // 画面遷移
                response.sendRedirect(request.getContextPath() + "/contacts/admin");
            }
        }
    }
}
