package com.fernandoproject.hamburgueria.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fernandoproject.hamburgueria.dao.CarrinhoDao;
import com.fernandoproject.hamburgueria.dao.PratoDao;
import com.fernandoproject.hamburgueria.model.Carrinho;
import com.fernandoproject.hamburgueria.model.Prato;
import com.fernandoproject.hamburgueria.model.Usuario;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {
    
    private CarrinhoDao carrinhoRepositorio;
    private PratoDao pratoRepositorio;


    public CarrinhoController(CarrinhoDao carrinhoRepositorio, PratoDao pratoRepositorio) {
        this.carrinhoRepositorio = carrinhoRepositorio;
        this.pratoRepositorio = pratoRepositorio;
    }

    @PostMapping("/adicionar")
    public String adicionarAoCarrinho(@RequestParam Long pratoId, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("clienteLogado");

        if (usuario == null) {
            return "Nenhum usuário logado!";
        }

        Prato prato = pratoRepositorio.findById(pratoId).orElse(null);
        if (prato == null) {
            return "Prato não encontrado!";
        }

        Carrinho carrinho = carrinhoRepositorio.findbyUsuario(usuario);
        if (carrinho == null) {
            carrinho = new Carrinho();
            carrinho.setUsuario(usuario);
            carrinho.setPratos(new ArrayList<>());
        }

        carrinho.getPratos().add(prato);

        carrinhoRepositorio.save(carrinho);
        
        return "Prato adicionado ao carrinho!";
    }
    
}
