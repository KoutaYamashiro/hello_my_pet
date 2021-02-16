package controllers.pets;

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
import models.User;
import utils.DBUtil;

/**
 * Servlet implementation class PetsShowServlet
 */
@WebServlet("/pets/show")
public class PetsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PetsShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
      protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            EntityManager em = DBUtil.createEntityManager();

            //  ログインユーザーIDを取得
            User login_user = (User) request.getSession().getAttribute("login_user");

            // ペットIDを取得
            Pet pet = em.find(Pet.class, Integer.parseInt(request.getParameter("id")));

            // フォロー判定　ユーザーとペット情報をセット
            List<Favorite> checkMyFavorite = em.createNamedQuery("checkMyFavorite", Favorite.class)
                                                          .setParameter("user", login_user)
                                                          .setParameter("pet", pet)
                                                          .getResultList();

            // セットしたされた情報と重複チェック
            boolean favorite_check = checkMyFavorite.contains(pet);

            System.out.println("確認" + checkMyFavorite);

            // 値をセット
            request.setAttribute("checkMyFavorite", checkMyFavorite);
            request.setAttribute("favorite_count", favorite_check);
            //フォロー判定ここまで

            em.close();

            request.setAttribute("pet", pet);
            request.setAttribute("_token", request.getSession().getId());

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/pets/show.jsp");
            rd.forward(request, response);
        }

}
