package com.backdownof.servlet;

import com.backdownof.service.PlayerService;
import com.backdownof.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/players")
public class PlayerServlet extends HttpServlet {
    private final PlayerService playerService = PlayerService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("players", playerService.findAll());

        req.getRequestDispatcher(JspHelper.getPath("players")).forward(req, resp);
    }
}
