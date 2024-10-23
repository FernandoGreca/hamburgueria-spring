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
import com.fernandoproject.hamburgueria.model.ItensCompra;
import com.fernandoproject.hamburgueria.model.Prato;


@Controller
public class CarrinhoController {

    private List<ItensCompra> itensCompra = new ArrayList<>();

    @Autowired
    private PratoDao pratoRepositorio;

    @GetMapping("/carrinho")
    public ModelAndView carrinhoPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("pages/carrinho");
        mv.addObject("listaItens", itensCompra);
        return mv;
    }

    @GetMapping("/adicionarCarrinho/{id}")
    public ModelAndView adicionarCarrinho(@PathVariable long id) {
        ModelAndView mv = new ModelAndView();
        
        Optional<Prato> pratoClicado = pratoRepositorio.findById(id);
        Prato prato = pratoClicado.get();
        ItensCompra item = new ItensCompra();
        item.setPrato(prato);
        item.setValorUnitario(prato.getPreco());
        item.setQuantidade(item.getQuantidade() + 1);
        item.setValorTotal(item.getQuantidade() * item.getValorUnitario());
        itensCompra.add(item);

        mv.addObject("listaItens", itensCompra);
        mv.setViewName("redirect:/carrinho");
        return mv;
    }
}
