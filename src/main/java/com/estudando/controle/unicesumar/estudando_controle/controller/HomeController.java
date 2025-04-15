package com.estudando.controle.unicesumar.estudando_controle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.estudando.controle.unicesumar.estudando_controle.model.UsuarioModel;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    // Exibe a página home após o login
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        // Verifica se o usuário está autenticado na sessão
        Object objectSession = session.getAttribute("usuario");
        if (objectSession != null) {
            UsuarioModel usuario = (UsuarioModel) objectSession;
            model.addAttribute("nome", usuario.getNome());
            session.setAttribute("usuario", usuario);
            return "home"; // Retorna a página home (home.html)
        }
        // Se não houver sessão, redireciona para login
        return "redirect:/login"; // Redireciona para a tela de login caso o usuário não esteja logado
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalida a sessão
        return "redirect:/index"; // Redireciona para a tela de login
    }
}
