package controllers.favorites;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Favorite;
import models.User;
import utils.DBUtil;

/**
 * Servlet implementation class LikesIndexServlet
 */
@WebServlet("/favorites/index")
public class FavoritesIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FavoritesIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        // ログインユーザーのIDを取得
        User login_user = (User) request.getSession().getAttribute("login_user");

        // ペットのIDを取得
        //Pet pet_details = em.find(Pet.class, Integer.parseInt(request.getParameter("pet_id")));

        // ページネーション
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        // ログインユーザーのいいねしたペット一覧を取得
        List<Favorite> favorite_pets = em.createNamedQuery("getMyFavorites", Favorite.class)
                                                             .setParameter("user", login_user)
//                                                             .setParameter("pet", pet_details)
                                                             .setFirstResult(10 * (page -1))
                                                             .setMaxResults(10)
                                                             .getResultList();

        // ログインユーザーのいいねしたペット数を取得
        long pets_count = (long)em.createNamedQuery("getMyFavoritesCount", Long.class)
                                                    .setParameter("user", login_user)
                                                    .getSingleResult();

        em.close();

        request.setAttribute("favorite_pets", favorite_pets);
        request.setAttribute("pets_count", pets_count);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/favorites/index.jsp");
        rd.forward(request, response);
    }

}










