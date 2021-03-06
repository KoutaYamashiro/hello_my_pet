package controllers.pets;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Pet;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeesDestroyServlet
 */
@WebServlet("/pets/destroy")
public class PetsDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PetsDestroyServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String _token = (String) request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            // Petインスタンスを生成し、変数pに現在見ているPetの情報をセットする
            Pet p = em.find(Pet.class, (Integer) (request.getSession().getAttribute("pet_id")));

            p.setDelete_flag(1);
            // 更新日時のみ上書き
            p.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();
            // セッションスコープにフラッシュメッセージをセットする
            request.getSession().setAttribute("flush", "登録を変更しました。");

            // 画面遷移
            response.sendRedirect(request.getContextPath() + "/pets/index");
        }
    }

}
