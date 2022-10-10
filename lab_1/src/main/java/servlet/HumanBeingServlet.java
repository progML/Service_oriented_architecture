package servlet;

import dao.HumanBeingDao;
import model.HumanBeing;
import service.HumanBeingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/")
public class HumanBeingServlet extends HttpServlet {

    private final HumanBeingDao humanBeingDao;
    private HumanBeingService humanBeingService;

    public HumanBeingServlet() {
        this.humanBeingDao = new HumanBeingDao();
        this.humanBeingService = new HumanBeingService();
    }

    //получение списка human
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/humanBeings":
                    doPost(request, response);
                    if (request.getParameterMap().isEmpty()) {
                        getHumanBeing(request, response);
                    } else {
                        getHumanBeingById(request, response);
                    }
                    break;
                case "/humanBeings/filter":
                    filterHumanBeings(request, response);
                    break;
                case "/humanBeings/filter/name":
                    filterHumanBeingsByName(request, response);
                    break;
                case "/humanBeings/sort":
                    sort(request, response);
                    break;
                default:
                    getHumanBeing(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    //добавление нового human
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            humanBeingService.createHumanBeing(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            humanBeingService.updateHumanBeing(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        humanBeingService.deleteHumanBeing(request, response);
    }

    public void getHumanBeing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        humanBeingService.getAllHumanBeing(request, response);
    }

    private void getHumanBeingById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Optional<HumanBeing> humanBeing = humanBeingDao.getHumanBeingById(id);
        if (humanBeing.isPresent()) {
            request.setAttribute("humanBeing", humanBeing.get());
        } else {
            request.setAttribute("msg", "Not found humanBeing with id=" + id);
        }
        request.getRequestDispatcher("/jsp/get-by-id.jsp").forward(request, response);
    }

    private void filterHumanBeings(HttpServletRequest request, HttpServletResponse response) throws Exception {
        humanBeingService.filterHumanBeing(request, response);
    }

    private void filterHumanBeingsByName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        filterHumanBeings(request, response);
    }

    private void sort(HttpServletRequest request, HttpServletResponse response) throws Exception {
        humanBeingService.sort(request, response);
    }


}