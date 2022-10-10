package servlet;

import service.HumanBeingService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mood")
public class MoodServlet extends HttpServlet {

    private HumanBeingService humanBeingService;

    public MoodServlet() {
        this.humanBeingService = new HumanBeingService();
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        humanBeingService.deleteByMood(request, response);
    }
}
