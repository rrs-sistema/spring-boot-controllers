package com.estudando.controle.unicesumar.estudando_controle.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.estudando.controle.unicesumar.estudando_controle.model.UsuarioModel;

import jakarta.servlet.http.HttpSession;

@Service
@SuppressWarnings("unchecked")
public class UsuarioService {
    public Boolean save(String nome, String email, String senha, Model model, HttpSession session) {
        // Recupera a lista de usuarios da sessão, ou cria uma nova lista se não existir
        UsuarioModel usuario = new UsuarioModel(nome, email, senha);

        List<UsuarioModel> usuarios = (List<UsuarioModel>) session.getAttribute("usuarios");
        if (usuarios == null) {
            usuarios = new ArrayList<>();
        }
        var userExiste = usuarios.stream().filter(user -> user.getEmail().equals(email)).findFirst();
        if (userExiste.isPresent()) {
            model.addAttribute("msg", "Ops! Email já cadastrado!");
            return false;
        }
        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            model.addAttribute("msg", "Nome do usuário não pode ser vazio!");
            return false;
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            model.addAttribute("msg", "Email do usuário não pode ser vazio!");
            return false;
        }
        if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
            model.addAttribute("msg", "Senha do usuário não pode ser vazio!");
            return false;
        }
        if (usuarios != null && usuarios.size() > 0)
            usuario.setId((long) (usuarios.size() + 1));
        else
            usuario.setId(1L);

        // Adiciona a novo usuario à lista de usuarios
        usuarios.add(usuario);

        // Armazena a lista de usuarios atualizada na sessão
        session.setAttribute("usuarios", usuarios);
        model.addAttribute("msg", "Usuário cadastrado com sucesso!");
        return true;
    }

    public Boolean findByEmailAndSenha(String email, String senha, Model model, HttpSession session) {
        List<UsuarioModel> usuarios = (List<UsuarioModel>) session.getAttribute("usuarios");
        if (usuarios == null) {
            model.addAttribute("msg", "Usuário não cadastrado!");
            return false;
        }
        var user = usuarios.stream().filter(usuario -> usuario.getEmail().equals(email) && usuario.getSenha()
                .equals(senha)).findFirst();
        if (user == null) {
            model.addAttribute("msg", "Usuário ou senha inválidos!");
            return false;
        }
        session.setAttribute("usuario", user.get());
        return true;
    };
}
