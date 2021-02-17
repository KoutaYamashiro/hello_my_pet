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
 * いいね追加のコントローラ
 */
@WebServlet("/favorites/create")
public class FavoritesCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FavoritesCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // EntityManagerのオブジェクトを生成
        EntityManager em = DBUtil.createEntityManager();

        // いいねを生成
        Favorite f = new Favorite();

        // いいねしたペットのIDを取得
        Pet pet = em.find(Pet.class, Integer.parseInt(request.getParameter("pet_id")));

        // テーブルに値をセット
        f.setUser((User) request.getSession().getAttribute("login_user"));
        f.setPet(pet);

        // すでにいいねしたペットの場合はエラーとなる
//        List<String> error = FavoriteValidator.validate(f, true);
//        if (error.size() > 0) {
//            em.close();

            request.setAttribute("_token", request.getSession().getId());
            request.setAttribute("favorite", f);
//            request.setAttribute("error", error);

//            RequestDispatcher rd = request.getRequestDispatcher("/");
//            rd.forward(request, response);
//        } else {

            // データベースを更新
            em.getTransaction().begin();
            em.persist(f);
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "いいねしました。");
            // トップページへリダイレクト
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
//}
