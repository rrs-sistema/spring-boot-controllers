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
        UsuarioModel usuario = (UsuarioModel) session.getAttribute("usuario");

        if (usuario != null) {
            model.addAttribute("usuario", usuario); // Exibe as informações do usuário na home

            if (usuario.getEmail().equalsIgnoreCase("rivaldo@gmail.com")) {
                model.addAttribute("nome", "Rivaldo Roberto");
            } else if (usuario.getEmail().equalsIgnoreCase("acsa@gmail.com")) {
                model.addAttribute("nome", "Jullia Acsa");
            } else {
                model.addAttribute("nome", "Usuário não encontrado!");
            }

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
