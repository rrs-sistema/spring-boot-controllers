package com.estudando.controle.unicesumar.estudando_controle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.estudando.controle.unicesumar.estudando_controle.model.UsuarioModel;
import com.estudando.controle.unicesumar.estudando_controle.service.TarefaService;

import jakarta.servlet.http.HttpSession;

@Controller
public class TarefaController {

    @Autowired
    TarefaService tarefaService;

    // Exibe a página de cadastro
    @SuppressWarnings("unchecked")
    @GetMapping("/cadastro-tarefa")
    public String cadastro(HttpSession session, Model model) {
        UsuarioModel usuario = (UsuarioModel) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login"; // Redireciona para a página de login se o usuário não estiver autenticado
        }

        List<UsuarioModel> usuarios = (List<UsuarioModel>) session.getAttribute("usuarios");
        // Adiciona a lista de usuários no modelo para que ela seja acessada no
        model.addAttribute("usuarios", usuarios);

        return "cadastro-tarefa"; // Retorna a página de cadastro (sem a extensão .html)
    }

    // Processa o cadastro de um novo usuário
    @PostMapping("/cadastro-tarefa")
    public String cadastrar(@RequestParam String nome, @RequestParam Long usuarioId, @RequestParam Boolean concluida,
            HttpSession session, Model model) {
        Boolean cadastrou = tarefaService.save(nome, usuarioId, concluida, model, session);
        if (!cadastrou) {
            return "cadastro-tarefa";
        }
        return "redirect:/lista-tarefa"; // Redireciona para a página de lista de tarefas
    }

    @GetMapping("/lista-tarefa")
    public String pesquisar(HttpSession session, Model model) {
        Object usuario = session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login"; // Redireciona para a página de login se o usuário não estiver autenticado
        }
        tarefaService.findAll(model, session);
        return "lista-tarefa";
    }

    @GetMapping("/alterar-status/{id}")
    public String alterarStatus(@PathVariable Long id, HttpSession session) {
        tarefaService.updateStatus(id, session); // Salva a tarefa com o status alterado
        return "redirect:/lista-tarefa"; // Redireciona de volta para a lista de tarefas
    }

}
