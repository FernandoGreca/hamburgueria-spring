package com.fernandoproject.hamburgueria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class CarrinhoController {

    @GetMapping("/carrinho")
    public ModelAndView carrinhoPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("pages/carrinho");
        return mv;
    }

    @GetMapping("/adicionarCarrinho/{id}")
    public ModelAndView adicionarCarrinho(@PathVariable long id) {
        System.out.println("ID DO PRODUTO: " + id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("pages/carrinho");
        return mv;
    }
}
