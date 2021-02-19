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
 * Servlet implementation class UsersUpdateServlet
 */
@WebServlet("/users/update")
public class UsersUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersUpdateServlet() {
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

            User u = em.find(User.class, (Integer)(request.getSession().getAttribute("user_id")));

            // 現在の値と異なるメールアドレスが入力されていたら
            // 重複チェックを行う指定をする
            Boolean mail_addressDuplicateCheckFlag = true;
            if(u.getMail_address().equals(request.getParameter("mail_address"))) {
                mail_addressDuplicateCheckFlag = false;
            } else {
                u.setMail_address(request.getParameter("mail_address"));
            }

            // パスワード欄に入力があったら
            // パスワードの入力値チェックを行う指定をする
            Boolean passwordCheckFlag = true;
            String password = request.getParameter("password");
            if(password == null || password.equals("")) {
                passwordCheckFlag = false;
            } else {
                u.setPassword(
                        EncryptUtil.getPasswordEncrypt(
                                password,
                                (String)this.getServletContext().getAttribute("pepper")
                                )
                        );
            }

            u.setName(request.getParameter("name"));
            u.setAdmin_flag(Integer.parseInt(request.getParameter("admin_flag")));
            u.setUpdated_at(new Timestamp(System.currentTimeMillis()));     // 更新日時のみ上書き
            u.setDelete_flag(0);

            // バリデーションを実行してエラーがあったら編集画面のフォームに戻る
            List<String> errors = UserValidator.validate(u, mail_addressDuplicateCheckFlag, passwordCheckFlag);
            if(errors.size() > 0) {
                em.close();

                // フォームに初期値を設定、さらにエラーメッセージを送る
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("user", u);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/users/edit.jsp");
                rd.forward(request, response);
            } else {
                // データベースを更新
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "更新が完了しました。");
                // セッションスコープ上の不要になったデータを削除
                request.getSession().removeAttribute("user_id");
                // indexページへリダイレクト
                response.sendRedirect(request.getContextPath() + "/users/index");
            }
        }
    }

}
