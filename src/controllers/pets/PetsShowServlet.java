package controllers.pets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

            //  ログインユーザーのIDを取得
            User login_user = (User) request.getSession().getAttribute("login_user");

            // 詳細を見るペットのIDを取得
            Pet pet = em.find(Pet.class, Integer.parseInt(request.getParameter("id")));
            System.out.println("＊＊＊ペットID＊＊＊" + pet);

            // セットしたされた情報の重複チェック
            long checkMyFavoriteCount = (long)em.createNamedQuery("checkMyFavoriteCount", Long.class)
                                                            .setParameter("user", login_user)
                                                            .setParameter("pet", pet)
                                                            .getSingleResult();
            System.out.println("＊＊＊チェックcheckMyFavoriteCount＊＊＊" + checkMyFavoriteCount);

         // ペットのいいね数を取得
            long favoritesCount = (long)em.createNamedQuery("getPetFavoritesCount", Long.class)
                                                             .setParameter("pet", pet)
                                                             .getSingleResult();
            System.out.println("＊＊＊チェックfavoritesCount＊＊＊" + favoritesCount);

            // 値をセット
//            request.setAttribute("favoriteCheckFlag", checkMyFavorite);
            request.setAttribute("favoritesCount", favoritesCount);
            request.setAttribute("checkMyFavoriteCount", checkMyFavoriteCount);
            //フォロー判定ここまで

            em.close();

            request.setAttribute("pet", pet);
            request.setAttribute("_token", request.getSession().getId());

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/pets/show.jsp");
            rd.forward(request, response);
        }

}
