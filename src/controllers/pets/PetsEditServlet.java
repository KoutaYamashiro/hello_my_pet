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
 * Servlet implementation class PetsEditServlet
 */
@WebServlet("/pets/edit")
public class PetsEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PetsEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Pet p = em.find(Pet.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        User login_user = (User)request.getSession().getAttribute("login_user");
        if(p != null && login_user.getId() == p.getUser().getId()) {
            request.setAttribute("pet", p);
            request.setAttribute("_token", request.getSession().getId());
            request.getSession().setAttribute("pet_id", p.getId());
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/pets/edit.jsp");
        rd.forward(request, response);
    }

}
