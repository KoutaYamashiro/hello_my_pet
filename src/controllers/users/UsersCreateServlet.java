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

            // ユーザーを生成
            User u = new User();

            // 値をセット
            u.setName(request.getParameter("name"));
            u.setMail_address(request.getParameter("mail_address"));
            u.setPassword(
                EncryptUtil.getPasswordEncrypt(
                    request.getParameter("password"),
                        (String)this.getServletContext().getAttribute("pepper")
                    )
                );

            // ユーザー登録の場合は　０　で登録するための処理記載
            Integer admin_flag = new Integer(0);
            String af_str = request.getParameter("admin_flag");
            if(af_str != null && !af_str.equals("")) {
                admin_flag = Integer.parseInt(request.getParameter("admin_flag"));
            }
            u.setAdmin_flag(admin_flag);

            System.out.println("＊＊＊＊＊＊＊＊admin_flagチェック＊＊＊＊＊＊＊＊" + admin_flag);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            u.setCreated_at(currentTime);
            u.setUpdated_at(currentTime);
            u.setDelete_flag(0);

            //バリデーションを実行してエラーがあったら新規登録のフォームに戻る
            List<String> errors = UserValidator.validate(u, true, true);
            if(errors.size() > 0) {
                em.close();

                // フォームに初期値を設定、さらにエラーメッセージを送る
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("user", u);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/users/new.jsp");
                rd.forward(request, response);
            } else {
                // データベースに保存
                em.getTransaction().begin();
                em.persist(u);
                em.getTransaction().commit();
                request.getSession().setAttribute("flush", "登録がありがとうございます！ログインをお願いします。");
                em.close();

                // ログインページにリダイレクト
                response.sendRedirect(request.getContextPath() + "/login");
            }
        }
    }

}

