package controllers.favorites;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Favorite;
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
        User login_user = (User) request.getSession().getAttribute("login_user");

        // いいねを解除するペットのIDを取得
       Pet p = em.find(Pet.class, Integer.parseInt(request.getParameter("pet_id")));

       // いいねリストの中から、いいね解除されるペットIDを取得
       Integer pet = 0;
       pet = em.createNamedQuery("getDestroyPet", Integer.class)
                        .setParameter("user", login_user)
                        .setParameter("pet", p)
                        .getSingleResult();

       Favorite f = em.find(Favorite.class, pet);

            em.getTransaction().begin();
            em.remove(f);
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "いいねを解除しました。");
         // トップページへリダイレクト
            response.sendRedirect(request.getContextPath() + "/");
    }

}






