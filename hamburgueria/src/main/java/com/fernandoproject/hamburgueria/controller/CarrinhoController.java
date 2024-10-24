package com.fernandoproject.hamburgueria.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fernandoproject.hamburgueria.dao.CompraDao;
import com.fernandoproject.hamburgueria.dao.ItensCompraDao;
import com.fernandoproject.hamburgueria.dao.PratoDao;
import com.fernandoproject.hamburgueria.dao.UsuarioDao;
import com.fernandoproject.hamburgueria.model.Compra;
import com.fernandoproject.hamburgueria.model.ItensCompra;
import com.fernandoproject.hamburgueria.model.Prato;
import com.fernandoproject.hamburgueria.model.Usuario;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpSession;


@Controller
public class CarrinhoController {

    @Autowired
    private PratoDao pratoRepositorio;

    @Autowired
    private UsuarioDao usuarioRepositorio;

    @Autowired
    private CompraDao compraRepositorio;

    @Autowired
    private ItensCompraDao itensCompraRepositorio;

    @Autowired
    private HttpSession session;

    private Usuario usuario;

    private void calculaTotal(List<ItensCompra> itensCompra, Compra compra) {
        compra.setValorTotal(0.0);
        for (ItensCompra it : itensCompra) {
            compra.setValorTotal(compra.getValorTotal() + it.getValorTotal());
        }
    }

    private void buscarUsuarioLogado() {
        String user = session.getAttribute("clienteLogado").toString();
        usuario = usuarioRepositorio.findByUser(user);
    }

    @PostMapping("/finalizar/confirmar")
    public ModelAndView confirmarCompra(String formaDePagamento, String formaDeEntrega,@Nullable String endereco) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("pages/mensagem-finalizou");

        Compra compra = (Compra) session.getAttribute("compra");
        compra.setUsuario(usuario);
        compra.setFormaPagamento(formaDePagamento);
        compra.setFormaEntrega(formaDeEntrega);
        if (endereco != null) { 
            compra.setEndereco(endereco); 
        }
        compraRepositorio.saveAndFlush(compra);

        @SuppressWarnings("unchecked")
        List<ItensCompra> itensCompra = (List<ItensCompra>) session.getAttribute("itensCompra");
        for (ItensCompra it : itensCompra) {
            it.setCompra(compra);
            itensCompraRepositorio.saveAndFlush(it);
        }
        session.setAttribute("compra", null);
        session.setAttribute("itensCompra", null);

        return mv;
    }

    @GetMapping("/finalizar")
    public ModelAndView finalizarCompraPage() {
        @SuppressWarnings("unchecked")
        List<ItensCompra> itensCompra = (List<ItensCompra>) session.getAttribute("itensCompra");
        if (itensCompra == null) {
            itensCompra = new ArrayList<>();
        }
        Compra compra = (Compra) session.getAttribute("compra");
        if (compra == null) {
            compra = new Compra();
        }

        ModelAndView mv = new ModelAndView();
        buscarUsuarioLogado();
        mv.addObject("cliente", usuario);
        calculaTotal(itensCompra, compra);
        mv.addObject("compra", compra);
        mv.setViewName("pages/finalizar-compra");
        mv.addObject("listaItens", itensCompra);
        return mv;
    }
    

    @GetMapping("/carrinho")
    public ModelAndView carrinhoPage() {
        @SuppressWarnings("unchecked")
        List<ItensCompra> itensCompra = (List<ItensCompra>) session.getAttribute("itensCompra");
        if (itensCompra == null) {
            itensCompra = new ArrayList<>();
        }
        Compra compra = (Compra) session.getAttribute("compra");
        if (compra == null) {
            compra = new Compra();
        }

        ModelAndView mv = new ModelAndView();
        calculaTotal(itensCompra, compra);
        mv.addObject("compra", compra);
        mv.setViewName("pages/carrinho");
        mv.addObject("listaItens", itensCompra);
        return mv;
    }

    @GetMapping("/alterarQuantidade/{id}/{acao}")
    public ModelAndView alterarQuantidade(@PathVariable long id, @PathVariable Integer acao) {
        @SuppressWarnings("unchecked")
        List<ItensCompra> itensCompra = (List<ItensCompra>) session.getAttribute("itensCompra");
        if (itensCompra == null) {
            itensCompra = new ArrayList<>();
        }

        for (ItensCompra it : itensCompra) {
            if (it.getPrato().getId() == id) {
                if (acao == 1) {
                    it.setQuantidade(it.getQuantidade() + 1);
                } else if (acao == 0 && it.getQuantidade() != 1) {
                    it.setQuantidade(it.getQuantidade() - 1);
                }
                it.setValorTotal(it.getQuantidade() * it.getValorUnitario());
                break;
            }
        }
        session.setAttribute("itensCompra", itensCompra);
        return new ModelAndView("redirect:/carrinho");
    }

    @GetMapping("/removerPrato/{id}")
    public ModelAndView removerPratoCarrinho(@PathVariable long id) {
        @SuppressWarnings("unchecked")
        List<ItensCompra> itensCompra = (List<ItensCompra>) session.getAttribute("itensCompra");
        if (itensCompra == null) {
            itensCompra = new ArrayList<>();
        }

        for (ItensCompra it : itensCompra) {
            if (it.getPrato().getId() == id) {
                itensCompra.remove(it);
                break;
            }
        }
        session.setAttribute("itensCompra", itensCompra);
        return new ModelAndView("redirect:/carrinho");
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/adicionarCarrinho/{id}")
    public ModelAndView adicionarCarrinho(@PathVariable long id) {
        List<ItensCompra> itensCompra = (List<ItensCompra>) session.getAttribute("itensCompra");
        if (itensCompra == null) {
            itensCompra = new ArrayList<>();
        }
        Compra compra = (Compra) session.getAttribute("compra");
        if (compra == null) {
            compra = new Compra();
        }

        Optional<Prato> pratoClicado = pratoRepositorio.findById(id);
        Prato prato = pratoClicado.get();

        int controle = 0;
        for (ItensCompra it : itensCompra) {
            if (it.getPrato().getId() == prato.getId()) {
                controle = 1;
                it.setQuantidade(it.getQuantidade() + 1);
                it.setValorTotal(it.getQuantidade() * it.getValorUnitario());
                break;
            }
        }

        if (controle == 0) {
            ItensCompra item = new ItensCompra();
            item.setPrato(prato);
            item.setValorUnitario(prato.getPreco());
            item.setQuantidade(1); // Iniciar quantidade com 1
            item.setValorTotal(item.getQuantidade() * item.getValorUnitario());
            itensCompra.add(item);
        }

        session.setAttribute("itensCompra", itensCompra);
        session.setAttribute("compra", compra);

        return new ModelAndView("redirect:/carrinho");
    }
}

