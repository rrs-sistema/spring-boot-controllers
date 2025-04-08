package com.estudando.controle.unicesumar.estudando_controle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.estudando.controle.unicesumar.estudando_controle.model.UsuarioModel;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    // Exibe a página de login
    @GetMapping("/login")
    public String login() {
        return "login"; // Retorna a página de login (sem a extensão .html)
    }

    // Trata a requisição POST do formulário de login
    @PostMapping("/validar-credenciais")
    public String validarCredenciais(@RequestParam String email, @RequestParam String senha, Model model,
            HttpSession session) {
        // Verifica as credenciais do usuário
        if ((email.equalsIgnoreCase("rivaldo@gmail.com") || email
                .equalsIgnoreCase("acsa@gmail.com")) && senha.equals("123456")) {
            // Salva o usuário na sessão
            UsuarioModel user = new UsuarioModel(email, senha);
            session.setAttribute("usuario", user); // Salva o usuário na sessão
            return "redirect:/home"; // Redireciona para a página inicial após login
        } else {
            // Adiciona mensagem de erro e retorna para a página de login
            model.addAttribute("msg", "Usuário ou senha inválidos!");
            return "login"; // Retorna para a página de login com mensagem de erro
        }
    }

    // Exibe a página de cadastro
    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro"; // Retorna a página de cadastro (sem a extensão .html)
    }

    // Processa o cadastro de um novo usuário
    @PostMapping("/cadastro")
    public String cadastrar(@RequestParam String nome, @RequestParam String email, @RequestParam String senha,
            Model model,
            HttpSession session) {
        // Cria um novo usuário com os dados fornecidos
        UsuarioModel usuario = new UsuarioModel(nome, email, senha);

        // Aqui você pode salvar o usuário na sessão ou em uma lista temporária (sem
        // banco de dados)
        session.setAttribute("usuario", usuario); // Salva o usuário na sessão

        // Redireciona para a página home após o cadastro
        return "redirect:/home";
    }
}
