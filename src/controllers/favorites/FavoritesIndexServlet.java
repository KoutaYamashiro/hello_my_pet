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

import models.Pet;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // DAOインスタンスの生成
        EntityManager em = DBUtil.createEntityManager();

        // ログインユーザーを取得
        User login_user = (User) request.getSession().getAttribute("login_user");

        // ページネーション
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        // ユーザーがいいねしたペットを取得
        List<Pet> favorite_pets = em.createNamedQuery("getMyFavoritePets", Pet.class)
                .setParameter("user", login_user)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .getResultList();

        // ユーザーがいいねしたペットをカウント
        long pets_count = (long) em.createNamedQuery("getMyFavoritesCount", Long.class)
                .setParameter("user", login_user)
                .getSingleResult();
        // DAOの破棄
        em.close();

        // リクエストスコープに各データをセット
        request.setAttribute("favorite_pets", favorite_pets);
        request.setAttribute("pets_count", pets_count);
        request.setAttribute("page", page);

        // セッションスコープにフラッシュメッセージがあるならば
        if (request.getSession().getAttribute("flush") != null) {
            // リクエストスコープにエラーメッセージをセットする
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            // リクエストスコープのエラーメッセージを削除
            request.getSession().removeAttribute("flush");
        }
        // 画面遷移
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/favorites/index.jsp");
        rd.forward(request, response);
    }

}
