package controllers.users;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;
import utils.DBUtil;

/**
 * Servlet implementation class UsersEditServlet
 */
@WebServlet("/users/edit")
public class UsersEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // DAOインスタンスの生成
        EntityManager em = DBUtil.createEntityManager();

        // ユーザーIDを1件のみデータベースから取得
        User u = em.find(User.class, Integer.parseInt(request.getParameter("id")));
        // DAOの破棄
        em.close();

        // ユーザー情報とセッションIDをリクエストスコープに登録
        request.setAttribute("user", u);
        request.setAttribute("_token", request.getSession().getId());
        // ユーザーIDをセッションスコープに登録
        request.getSession().setAttribute("user_id", u.getId());

        // 画面遷移
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/users/edit.jsp");
        rd.forward(request, response);
    }

}
