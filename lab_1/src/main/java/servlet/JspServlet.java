package servlet;

import dao.HumanBeingDao;
import model.HumanBeing;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/pages/*")
public class JspServlet extends HttpServlet {

    private final HumanBeingDao humanBeingDao;

    public JspServlet() {
        this.humanBeingDao = new HumanBeingDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        String action = request.getPathInfo();
        try {
            switch (action) {
                case "/add-humanBeing-form":
                    showNewForm(request, response);
                    break;
                case "/edit-form":
                    showEditForm(request, response);
                    break;
                case "/get-by-id-form":
                    showGetByIdForm(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    //мапим форму для заполнения
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/humanBeing-form.jsp").forward(request, response);
    }


    //мапим форму для изменияния текущего human
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Optional<HumanBeing> existingHumanBeing = humanBeingDao.getHumanBeingById(id);
        request.setAttribute("humanBeing", existingHumanBeing.get());
        request.getRequestDispatcher("/jsp/humanBeing-form.jsp").forward(request, response);
    }

    //мапим форму для поиска по id
    private void showGetByIdForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/get-by-id.jsp").forward(request, response);
    }

}
