package controllers.pets;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import models.Pet;
import models.User;
import models.validators.PetValidator;
import utils.DBUtil;

/**
 * Servlet implementation class PetsCreateServlet
 */
@WebServlet("/pets/create")
@MultipartConfig(location = "C:\\pleiades\\workspace\\hello_my_pet\\tmp", maxFileSize = 1048576)
public class PetsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PetsCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part part = request.getPart("file");
        String name = this.getFileName(part);
        String fileName = getServletContext().getRealPath("/upload") + "/" + name;
        System.out.println("File Path : " + fileName);
        part.write(fileName);

        //part.write("C:\\pleiades\\workspace\\hello_my_pet\\upload\\" + name);

        String _token = (String) request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Pet p = new Pet();

            p.setUser((User) request.getSession().getAttribute("login_user"));

            Date pet_date = new Date(System.currentTimeMillis());
            String rd_str = request.getParameter("pet_date");
            if (rd_str != null && !rd_str.equals("")) {
                pet_date = Date.valueOf(request.getParameter("pet_date"));
            }

            p.setPet_date(pet_date);
            p.setPet_name(request.getParameter("pet_name"));
            p.setLikes(0);
            p.setImage_url(name);
            p.setPet_type(request.getParameter("pet_type"));
            p.setPet_breed(request.getParameter("pet_breed"));
            p.setAge(request.getParameter("age"));
            p.setHome_town(request.getParameter("home_town"));
            p.setMemo(request.getParameter("memo"));
            p.setDelete_flag(0);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            p.setCreated_at(currentTime);
            p.setUpdated_at(currentTime);

            List<String> errors = PetValidator.validate(p);
            if (errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("report", p);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/pets/new.jsp");
                rd.forward(request, response);
            } else {
                em.getTransaction().begin();
                em.persist(p);
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "登録が完了しました。");
                response.sendRedirect(request.getContextPath() + "/pets/index");
            }
        }
    }

    private String getFileName(Part part) {
        String name = null;
        for (String dispotion : part.getHeader("Content-Disposition").split(";")) {
            if (dispotion.trim().startsWith("filename")) {
                name = dispotion.substring(dispotion.indexOf("=") + 1).replace("\"", "").trim();
                name = name.substring(name.lastIndexOf("\\") + 1);
                break;
            }
        }
        return name;
    }

}