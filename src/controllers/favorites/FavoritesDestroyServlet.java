package controllers.favorites;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Pet;
import models.User;
import utils.DBUtil;

/**
 * Servlet implementation class FavoritesDestroyServlet
 */
@WebServlet("/favorites/destroy")
public class FavoritesDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FavoritesDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // EntityManagerのオブジェクトを生成
        EntityManager em = DBUtil.createEntityManager();

        // ログインユーザーのIDを取得
        User u = (User) request.getSession().getAttribute("login_user");

        // いいねを解除するペットのIDを取得
       Pet p = em.find(Pet.class, Integer.parseInt(request.getParameter("pet_id")));

            em.getTransaction().begin();
            em.remove(p);
            em.remove(u);
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "いいねを解除しました。");
         // トップページへリダイレクト
            response.sendRedirect(request.getContextPath() + "/");
    }

}






