package com.backdownof.servlet;

import com.backdownof.service.PlayerService;
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
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (var printWriter = resp.getWriter()) {
            printWriter.write("<h1>Список игроков</h1>");
            printWriter.write("<ul>");
            playerService.findAll().forEach(playerDto -> printWriter.write("""
                        <li>
                            <a href="/players?nickname=%s">%s</a>
                        </li>
                    """.formatted(playerDto.getNickname(), playerDto.getDescription())));
            printWriter.write("</ul>");
        }
    }
}
