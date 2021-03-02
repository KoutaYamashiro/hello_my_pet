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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // EntityManagerのオブジェクトを生成
        EntityManager em = DBUtil.createEntityManager();

        // Contactのインスタンスを生成
        Contact c = new Contact();

        // 問い合わせするペットのIDを取得
        Pet pet = em.find(Pet.class, Integer.parseInt(request.getParameter("pet_id")));

        // 変数cに現在ログインしているユーザーの情報をセットする
        c.setUser((User) request.getSession().getAttribute("login_user"));
        // 変数cにペット情報をセットする
        c.setPet(pet);
        // 変数cに入力した内容をセットする
        c.setContent(request.getParameter("content"));

        // 現在日時を取得して変数pに作成日時にセットする
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        c.setCreated_at(currentTime);

        // バリデーター の呼び出し
        List<String> errors = ContactValidator.validate(c);
        // errorsリストに1つでも追加されていたら
        if (errors.size() > 0) {
            // DAOの破棄
            em.close();
            // リクエストスコープに各データをセット
            request.setAttribute("_token", request.getSession().getId());
            request.setAttribute("contact", c);
            request.setAttribute("errors", errors);
            // 画面遷移
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/contacts/new.jsp");
            rd.forward(request, response);
        } else {
            // データベースに保存する
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
            // DAOの破棄
            em.close();
            // セッションスコープにフラッシュメッセージをセットする
            request.getSession().setAttribute("flush", "お問い合わせしました！返信をお待ち下さい。");
            // 画面遷移
            response.sendRedirect(request.getContextPath() + "/contacts/index");
        }
    }
}
