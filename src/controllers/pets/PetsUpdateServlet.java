package controllers.pets;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Pet;
import models.validators.PetValidator;
import utils.DBUtil;

/**
 * Servlet implementation class PetsUpdateServlet
 */
@WebServlet("/pets/update")
public class PetsUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PetsUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            // セッションスコープからペットのIDを取得して
            // 該当のIDのペット情報1件のみをデータベースから取得
            Pet p = em.find(Pet.class, (Integer)(request.getSession().getAttribute("pet_id")));

            // フォームの内容を各フィールドに上書き
            p.setPet_breed(request.getParameter("pet_breed"));
            p.setPet_image(request.getParameter("pet_image"));
            p.setBirthday(Date.valueOf(request.getParameter("birthday")));
            p.setPet_price(request.getParameter("pet_price"));
            p.setAppeal_point(request.getParameter("appeal_point"));
            // 更新日時のみ上書き
            p.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            List<String> errors = PetValidator.validate(p);
            if(errors.size() > 0) {
                em.close();

                // ペット情報とセッションID、エラー情報をリクエストスコープに登録
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("pet", p);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/pets/edit.jsp");
                rd.forward(request, response);
            } else {
                // データベースを更新
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();

                request.getSession().setAttribute("flush", "更新が完了しました。");
                // セッションスコープ上の不要になったデータを削除
                request.getSession().removeAttribute("pet_id");
                // indexページへリダイレクト
                response.sendRedirect(request.getContextPath() + "/pets/index");
            }
        }
    }



}
