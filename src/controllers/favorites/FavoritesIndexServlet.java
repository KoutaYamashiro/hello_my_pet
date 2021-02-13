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
import models.Pet;
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
        // リクエストパラメータから検索条件の取得、（JSPからuser_id, pet_idを取得）

        Pet p = em.find(Pet.class, Integer.parseInt(request.getParameter("pet_id")));

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        List<Favorite> favorites = em.createNamedQuery("getMyAllFavorites", Favorite.class)
                                                  .setParameter("pet", p)
                                                  .setFirstResult(10 * (page -1))
                                                  .setMaxResults(10)
                                                  .getResultList();

        long favorites_count = (long) em.createNamedQuery("getMyFavoritesCount", Long.class)
                                                    .setParameter("pet", p)
                                                    .getSingleResult();

        Integer petUrl = Integer.parseInt(request.getParameter("pet_id"));

        em.close();

        request.setAttribute("favorites", favorites);
        request.setAttribute("favorites_count", favorites_count);
        request.setAttribute("page", page);
        request.setAttribute("pat_id", p);
        request.setAttribute("petUrl", petUrl);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/favorites/index.jsp");
        rd.forward(request, response);
    }

}










