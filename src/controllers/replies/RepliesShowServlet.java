package controllers.replies;

import java.io.IOException;

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
 * Servlet implementation class RepliesShowServlet
 */
@WebServlet("/replies/show")
public class RepliesShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RepliesShowServlet() {
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
        // 返信の情報を取得
        Reply r = em.find(Reply.class, Integer.parseInt(request.getParameter("id")));
        // DAOの破棄
        em.close();
        // リクエストスコープに各データをセット
        request.setAttribute("reply", r);
        request.setAttribute("_token", request.getSession().getId());
        // 画面遷移
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/contacts/show.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
