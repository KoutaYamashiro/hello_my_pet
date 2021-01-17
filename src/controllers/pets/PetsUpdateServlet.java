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

            Pet p = em.find(Pet.class, (Integer)(request.getSession().getAttribute("pet_id")));

            p.setPet_date(Date.valueOf(request.getParameter("pet_date")));
            p.setPet_name(request.getParameter("pet_name"));
            p.setPet_type(request.getParameter("pet_type"));
            p.setPet_breed(request.getParameter("pet_breed"));
            p.setBirthday(Date.valueOf(request.getParameter("birthday")));
            p.setVisit_area(request.getParameter("visit_area"));
            p.setAppeal_point(request.getParameter("appeal_point"));
            p.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            List<String> errors = PetValidator.validate(p);
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("pet", p);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/pets/edit.jsp");
                rd.forward(request, response);
            } else {
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "更新が完了しました。");

                request.getSession().removeAttribute("pet_id");

                response.sendRedirect(request.getContextPath() + "/pets/index");
            }
        }
    }


}
