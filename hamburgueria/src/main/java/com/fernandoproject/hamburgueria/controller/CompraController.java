package com.fernandoproject.hamburgueria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fernandoproject.hamburgueria.model.Compra;
import com.fernandoproject.hamburgueria.service.ServiceCompra;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;


@Controller
public class CompraController {

    @Autowired
    private ServiceCompra compraService;

    @GetMapping("/listar")
    public ModelAndView exibirCompras() {
        List<Compra> compras = compraService.listarTodasAsCompras();
        Collections.reverse(compras);
        ModelAndView mv = new ModelAndView(); // Nome da página HTML (compras.html)
        mv.setViewName("pages/compras");
        mv.addObject("compras", compras); // Adiciona a lista de compras ao modelo
        return mv;
    }
}
