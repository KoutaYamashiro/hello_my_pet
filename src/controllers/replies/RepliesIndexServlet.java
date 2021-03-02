package controllers.replies;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Reply;
import utils.DBUtil;

/**
 * Servlet implementation class RepliesIndexServlet
 */
@WebServlet("/replies/index")
public class RepliesIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RepliesIndexServlet() {
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

        // ページネーション
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        // 全ての返信を取得
        List<Reply> replies = em.createNamedQuery("getAllReplies", Reply.class)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .getResultList();

        // 全ての返信をカウント
        long replies_count = (long) em.createNamedQuery("getRepliesCount", Long.class)
                .getSingleResult();

        // DAOの破棄
        em.close();
        // リクエストスコープに各データをセット
        request.setAttribute("replies", replies);
        request.setAttribute("replies_count", replies_count);
        request.setAttribute("page", page);
        // セッションスコープにフラッシュメッセージがあるならば
        if (request.getSession().getAttribute("flush") != null) {
            // リクエストスコープにフラッシュメッセージをセットする
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            // リクエストスコープのフラッシュメッセージを削除
            request.getSession().removeAttribute("flush");
        }
        // 画面遷移
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/replies/index.jsp");
        rd.forward(request, response);
    }

}
