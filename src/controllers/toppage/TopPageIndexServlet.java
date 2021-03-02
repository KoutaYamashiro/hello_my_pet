package controllers.toppage;

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
 * Servlet implementation class TopPageIndexServlet
 */
@WebServlet("/index.html")
public class TopPageIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopPageIndexServlet() {
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

        // 開くページ数を取得（デフォルトは1ページ目）
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        // 最大件数と開始位置を指定して全てのペット情報を取得
        List<Pet> pets = em.createNamedQuery("getAllPets", Pet.class)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .getResultList();

        // 全てのペット数を取得
        long petsCount = (long) em.createNamedQuery("getPetsCount", Long.class)
                .getSingleResult();

        // DAOの破棄
        em.close();

        // リクエストスコープに各データをセット
        request.setAttribute("pets", pets);
        request.setAttribute("petsCount", petsCount); // 全ペット数
        request.setAttribute("page", page); // ページ数

        // フラッシュメッセージ処理
        // セッションスコープにflushメッセージがセットされているならば
        if (request.getSession().getAttribute("flush") != null) {
            // そのflushメッセージをリクエストスコープにセット
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            // セッションスコープに保存されたflushメッセージ削除
            request.getSession().removeAttribute("flush");
        }

        // 画面遷移
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
        rd.forward(request, response);
    }

}
