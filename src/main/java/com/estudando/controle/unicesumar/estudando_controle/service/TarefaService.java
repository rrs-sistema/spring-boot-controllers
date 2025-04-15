package com.estudando.controle.unicesumar.estudando_controle.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.estudando.controle.unicesumar.estudando_controle.dto.TarefaDTO;
import com.estudando.controle.unicesumar.estudando_controle.model.TarefaModel;
import com.estudando.controle.unicesumar.estudando_controle.model.UsuarioModel;

import jakarta.servlet.http.HttpSession;

@Service
@SuppressWarnings("unchecked")
public class TarefaService {

    public Boolean save(String nome, Long usuarioId, Boolean concluida, Model model, HttpSession session) {
        // Recupera a lista de usuários da sessão
        List<UsuarioModel> usuarios = (List<UsuarioModel>) session.getAttribute("usuarios");

        // Encontra o usuário selecionado
        Optional<UsuarioModel> usuarioSelecionado = usuarios.stream()
                .filter(usuario -> usuario.getId().equals(usuarioId))
                .findFirst();

        if (!usuarioSelecionado.isPresent()) {
            model.addAttribute("msg", "Ops! Selecione o usuário responsável pela tarefa!");
            return false;
        }

        TarefaModel tarefa = new TarefaModel(nome, usuarioSelecionado.get(), concluida);
        tarefa.setUsuario(usuarioSelecionado.get());
        // Recupera a lista de tarefas da sessão, ou cria uma nova lista se não existir

        List<TarefaModel> tarefas = (List<TarefaModel>) session.getAttribute("tarefas");
        if (tarefas == null) {
            tarefas = new ArrayList<>();
        }

        if (tarefa.getNome() == null || tarefa.getNome().isEmpty()) {
            throw new RuntimeException("Descrição da tarefa não pode ser vazio!");
        }
        if (tarefas != null && tarefas.size() > 0)
            tarefa.setId((long) (tarefas.size() + 1));
        else
            tarefa.setId(1L);

        if (tarefa.getConcluida()) {
            tarefa.setDataCriacao(LocalDateTime.now());
            tarefa.setDataConclusao(LocalDateTime.now());
        }

        // Adiciona a nova tarefa à lista de tarefas
        tarefas.add(tarefa);

        // Armazena a lista de tarefas atualizada na sessão
        session.setAttribute("tarefas", tarefas);

        return true;
    }

    public TarefaModel update(TarefaModel tarefa, HttpSession session) {
        // Recupera a lista de tarefas da sessão
        List<TarefaModel> tarefas = (List<TarefaModel>) session.getAttribute("tarefas");

        // Se a lista de tarefas não estiver na sessão, cria uma nova
        if (tarefas == null) {
            tarefas = new ArrayList<>();
            session.setAttribute("tarefas", tarefas); // Salva a lista na sessão se não existir
        }

        // Encontra a tarefa existente na lista
        var tarefaExistente = tarefas.stream()
                .filter(taf -> taf.getId().equals(tarefa.getId()))
                .findFirst()
                .orElse(null);

        // Se a tarefa for encontrada, atualiza ela
        if (tarefaExistente != null) {
            tarefas.remove(tarefaExistente); // Remove a tarefa antiga
            tarefas.add(tarefa); // Adiciona a tarefa atualizada
        } else {
            throw new RuntimeException("Tarefa não encontrada");
        }

        // Atualiza a lista de tarefas na sessão
        session.setAttribute("tarefas", tarefas);

        return tarefa;
    }

    public TarefaModel updateStatus(Long id, HttpSession session) {
        TarefaModel tarefa = null;
        // Recupera a lista de tarefas da sessão
        List<TarefaModel> tarefas = (List<TarefaModel>) session.getAttribute("tarefas");

        // Se a lista de tarefas não estiver na sessão, cria uma nova
        if (tarefas == null) {
            tarefas = new ArrayList<>();
            session.setAttribute("tarefas", tarefas); // Salva a lista na sessão se não existir
        }

        // Encontra a tarefa existente na lista
        tarefa = tarefas.stream()
                .filter(taf -> taf.getId().equals(
                        id))
                .findFirst()
                .orElse(null);

        // Se a tarefa for encontrada, atualiza ela
        if (tarefa != null) {
            tarefa.setConcluida(!tarefa.getConcluida());
            if (tarefa.getConcluida()) {
                tarefa.setDataConclusao(LocalDateTime.now());
            } else {
                tarefa.setDataCriacao(LocalDateTime.now());
                tarefa.setDataConclusao(null);
            }

            tarefas.remove(tarefa); // Remove a tarefa antiga
            tarefas.add(tarefa); // Adiciona a tarefa atualizada
        } else {
            throw new RuntimeException("Tarefa não encontrada");
        }

        // Atualiza a lista de tarefas na sessão
        session.setAttribute("tarefas", tarefas);

        return tarefa;
    }

    public Optional<TarefaModel> findById(Long id, HttpSession session) {
        List<TarefaModel> tarefas = (List<TarefaModel>) session.getAttribute("tarefas");
        return tarefas.stream().filter(usuario -> usuario.getId().equals(id)).findFirst();
    };

    public void findAll(Model model, HttpSession session) {
        List<TarefaModel> tarefas = (List<TarefaModel>) session.getAttribute("tarefas");

        List<TarefaDTO> tarefaDTOs = new ArrayList<>();

        if (tarefas != null) {
            for (TarefaModel tarefa : tarefas) {
                TarefaDTO tarefaDTO = new TarefaDTO(
                        tarefa.getId(),
                        tarefa.getNome(),
                        tarefa.getUsuario().getNome(),
                        tarefa.getDataCriacao(),
                        tarefa.getDataConclusao(),
                        tarefa.getConcluida());
                tarefaDTOs.add(tarefaDTO);
            }
        }

        model.addAttribute("tarefas", tarefaDTOs);
    }
}
