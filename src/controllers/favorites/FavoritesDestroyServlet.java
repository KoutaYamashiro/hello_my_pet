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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // DAOインスタンスの生成
        EntityManager em = DBUtil.createEntityManager();

        // Userインスタンスを生成し、変数uに現在ログインしているuser情報をセットする
        User login_user = (User) request.getSession().getAttribute("login_user");

        // Petインスタンスを生成し、変数pに現在見ているPetの情報をセットする
        Pet p = em.find(Pet.class, Integer.parseInt(request.getParameter("pet_id")));

        // いいねリストの中から、いいね解除されるペットIDを取得
        Integer pet = 0;
        pet = em.createNamedQuery("getDestroyPet", Integer.class)
                .setParameter("user", login_user)
                .setParameter("pet", p)
                .getSingleResult();

        Favorite f = em.find(Favorite.class, pet);

        // favoriteを削除する
        em.getTransaction().begin();
        em.remove(f);
        em.getTransaction().commit();
        // DAOの破棄
        em.close();
        // セッションスコープにフラッシュメッセージをセットする
        request.getSession().setAttribute("flush", "いいねを解除しました。");
        // 画面遷移
        response.sendRedirect(request.getContextPath() + "/");
    }

}
