package controllers.pets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Pet;

/**
 * Servlet implementation class PetsNewServlet
 */
@WebServlet("/pets/new")
public class PetsNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PetsNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("_token", request.getSession().getId());

        // Petのインスタンスを生成
        Pet p = new Pet();
        // リクエストスコープにデータをセット
        request.setAttribute("pet", p);

        // 画面遷移
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/pets/new.jsp");
        rd.forward(request, response);
    }

}







