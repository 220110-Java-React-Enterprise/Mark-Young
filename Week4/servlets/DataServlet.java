package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import utils.GlobalStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DataServlet extends HttpServlet {
    /*
    This will take a simple GET request and respond with "Pong!" and status 202, indicating the request was accepted.
     */

    GlobalStore store;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(202);
        resp.getWriter().print("Pong!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.readValue(req);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // super.doDelete(req, resp);
    }
}