package com.estudando.controle.unicesumar.estudando_controle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.estudando.controle.unicesumar.estudando_controle.model.UsuarioModel;
import com.estudando.controle.unicesumar.estudando_controle.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    // Exibe a página de login
    @GetMapping("/login")
    public String login() {
        return "login"; // Retorna a página de login (sem a extensão .html)
    }

    // Trata a requisição POST do formulário de login
    @PostMapping("/validar-credenciais")
    public String validarCredenciais(@RequestParam String email, @RequestParam String senha,
            Model model, HttpSession session) {
        // Verifica as credenciais do usuário
        Boolean logou = usuarioService.findByEmailAndSenha(email, senha, model, session);
        if (logou) {
            return "redirect:/lista-tarefa"; // Redireciona para a página inicial após login
        } else {
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
            @RequestParam String confirmaSenha, Model model, HttpSession session) {

        UsuarioModel usuario = new UsuarioModel();
        if (!usuario.validaSenha(senha, confirmaSenha)) {
            model.addAttribute("msg", "As senhas não conferem!");
            return "cadastro"; // Retorna para a página de login com mensagem de erro
        }
        // Cria um novo usuário com os dados fornecidos
        Boolean cadastrou = usuarioService.save(nome, email, senha, model, session);

        if (!cadastrou) {
            return "cadastro";
        }
        // Redireciona para a página home após o cadastro
        return "redirect:/lista-tarefa";
    }
}
