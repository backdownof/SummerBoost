package com.backdownof.servlet;

import com.backdownof.dao.PlayerDao;
import com.backdownof.util.PlayerUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/")
public class MainServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            System.out.println(req.getHeader(headerNames.nextElement()));
        }

        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try (var printWriter = resp.getWriter()) {
            printWriter.write("<h1>Hello player. Do you want a cheat?</h1>" +
                    PlayerUtil.playersToString(PlayerDao.getInstance().findAll()));
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
