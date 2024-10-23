package com.fernandoproject.hamburgueria.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.fernandoproject.hamburgueria.dao.PratoDao;
import com.fernandoproject.hamburgueria.model.Compra;
import com.fernandoproject.hamburgueria.model.ItensCompra;
import com.fernandoproject.hamburgueria.model.Prato;


@Controller
public class CarrinhoController {

    private List<ItensCompra> itensCompra = new ArrayList<>();
    private Compra compra = new Compra();

    @Autowired
    private PratoDao pratoRepositorio;


    private void calculaTotal() {
        compra.setValorTotal(0.0);
        for (ItensCompra it : itensCompra) {
            compra.setValorTotal(compra.getValorTotal() + it.getValorTotal());
        }
    }

    @GetMapping("/carrinho")
    public ModelAndView carrinhoPage() {
        ModelAndView mv = new ModelAndView();
        calculaTotal();
        mv.addObject("compra", compra);
        mv.setViewName("pages/carrinho");
        mv.addObject("listaItens", itensCompra);
        return mv;
    }

    @GetMapping("/alterarQuantidade/{id}/{acao}")
    public ModelAndView alterarQuantidade(@PathVariable long id, @PathVariable Integer acao) {

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

        return new ModelAndView("redirect:/carrinho");
    }

    @GetMapping("/removerPrato/{id}")
    public ModelAndView removerPratoCarrinho(@PathVariable long id) {
        for (ItensCompra it : itensCompra) {
            if (it.getPrato().getId() == id) {
                itensCompra.remove(it);
                break;
            }
        }

        return new ModelAndView("redirect:/carrinho");
    }

    @GetMapping("/adicionarCarrinho/{id}")
    public ModelAndView adicionarCarrinho(@PathVariable long id) {
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
            item.setQuantidade(item.getQuantidade() + 1);
            item.setValorTotal(item.getValorTotal() + item.getQuantidade() * item.getValorUnitario());
            itensCompra.add(item);
        }
        
        return new ModelAndView("redirect:/carrinho");
    }
}
