package com.fernandoproject.hamburgueria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fernandoproject.hamburgueria.dao.CarrinhoDao;
import com.fernandoproject.hamburgueria.dao.PratoDao;
import com.fernandoproject.hamburgueria.model.Carrinho;
import com.fernandoproject.hamburgueria.model.Prato;
import com.fernandoproject.hamburgueria.model.Usuario;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CarrinhoController {

    private final CarrinhoDao carrinhoRepositorio;
    private final PratoDao pratoRepositorio;

    public CarrinhoController(CarrinhoDao carrinhoRepositorio, PratoDao pratoRepositorio) {
        this.carrinhoRepositorio = carrinhoRepositorio;
        this.pratoRepositorio = pratoRepositorio;
    }

    @GetMapping("/carrinho")
    public ModelAndView exibirCarrinho(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("clienteLogado");

        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        Carrinho carrinho = carrinhoRepositorio.findByUsuario(usuario);
        if (carrinho == null) {
            carrinho = new Carrinho();
            carrinho.setUsuario(usuario);
        }

        ModelAndView mv = new ModelAndView();
        mv.setViewName("pages/carrinho");
        mv.addObject("pratos", carrinho.getPratos());
        mv.addObject("total", calcularTotalCarrinho(carrinho));

        return mv;
    }

    @PostMapping("AdicionarAoCarrinho")
    public ModelAndView adicionarAoCarrinho(@RequestParam Long pratoId, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        Usuario usuario = (Usuario) session.getAttribute("clienteLogado");

        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        Prato prato = pratoRepositorio.findById(pratoId).orElse(null);
        if (prato == null) {
            mv.addObject("msgPrato", "Prato não encontrado!");
            mv.setViewName("redirect:/cardapio");
            return mv;
        }

        Carrinho carrinho = carrinhoRepositorio.findByUsuario(usuario);
        if (carrinho == null) {
            carrinho = new Carrinho();
            carrinho.setUsuario(usuario);
        }

        // Verifique se o prato já está no carrinho para evitar duplicações
        if (!carrinho.getPratos().contains(prato)) {
            carrinho.getPratos().add(prato);
        }

        try {
            carrinhoRepositorio.save(carrinho);
        } catch (Exception e) {
            // Log para depurar o erro
            System.out.println("------------------------------------ EEERRROOOO ------------------" + e.getMessage());
            e.printStackTrace();
            mv.addObject("msgPrato", "Erro ao adicionar prato ao carrinho!");
            mv.setViewName("redirect:/cardapio");
            return mv;
        }

        mv.addObject("msgPrato", "Prato adicionado com sucesso");
        mv.setViewName("redirect:/cardapio");
        return mv;
    }

    private double calcularTotalCarrinho(Carrinho carrinho) {
        return carrinho.getPratos().stream().mapToDouble(Prato::getPreco).sum();
    }
}
