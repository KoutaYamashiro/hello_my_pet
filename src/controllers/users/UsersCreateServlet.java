package controllers.users;

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

import models.User;
import models.validators.UserValidator;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class UsersCreateServlet
 */
@WebServlet("/users/create")
public class UsersCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");

        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            // Userインスタンスの生成
            User u = new User();

            // 変数uに入力された名前をセットする
            u.setName(request.getParameter("name"));
            // 変数uに入力されたアドレスをセットする
            u.setMail_address(request.getParameter("mail_address"));
            // 変数uに暗号化されたパスワードをセットする
            u.setPassword(
                EncryptUtil.getPasswordEncrypt(
                    request.getParameter("password"),
                        (String)this.getServletContext().getAttribute("pepper")
                    )
                );

            //管理者権限以外での ユーザー登録の場合はAdmin_flag(0)で登録するための処理記載
            Integer admin_flag = new Integer(0);
            String af_str = request.getParameter("admin_flag");
            if(af_str != null && !af_str.equals("")) {
                admin_flag = Integer.parseInt(request.getParameter("admin_flag"));
            }
            // 変数uにAdmin_flag(0)をセットする
            u.setAdmin_flag(admin_flag);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            // 変数uに作成日時をセットする
            u.setCreated_at(currentTime);
            // 変数uに更新日時をセットする
            u.setUpdated_at(currentTime);
            // 変数uにDelete_flag(0)をセットする
            u.setDelete_flag(0);

            // バリデーター の呼び出し
            List<String> errors = UserValidator.validate(u, true, true);
            // errorsリストに1つでも追加されていたら
            if(errors.size() > 0) {
                // DAOの破棄
                em.close();

                // リクエストスコープに各データをセット
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("user", u);
                request.setAttribute("errors", errors);
                // 画面遷移
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/users/new.jsp");
                rd.forward(request, response);
            } else {
                // データベースに保存
                em.getTransaction().begin();
                em.persist(u);
                em.getTransaction().commit();
                // DAOの破棄
                em.close();
                // セッションスコープにフラッシュメッセージをセットする
                request.getSession().setAttribute("flush", "登録が完了しました。");
                // セッションスコープにをlogin_userセットする
                request.getSession().setAttribute("login_user", u);
                // 画面遷移
                response.sendRedirect(request.getContextPath() + "/");
            }
        }
    }

}

