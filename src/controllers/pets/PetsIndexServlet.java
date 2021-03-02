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

import models.Pet;
import utils.DBUtil;

/**
 * Servlet implementation class PetsIndexServlet
 */
@WebServlet("/pets/index")
public class PetsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PetsIndexServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // DAOインスタンスの生成
        EntityManager em = DBUtil.createEntityManager();

        // ページネーション
        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }

        // すべてのペット一覧を取得
        List<Pet> pets = em.createNamedQuery("getAllPets", Pet.class)
                                  .setFirstResult(10 * (page - 1))
                                  .setMaxResults(10)
                                  .getResultList();

        // すべてのペット数をカウントする
        long pets_count = (long)em.createNamedQuery("getPetsCount", Long.class)
                                     .getSingleResult();
        // DAOの破棄
        em.close();

        // リクエストスコープに各データをセット
        request.setAttribute("pets", pets);
        request.setAttribute("pets_count", pets_count);
        request.setAttribute("page", page);

        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        // 画面遷移
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/pets/index.jsp");
        rd.forward(request, response);
    }

}
