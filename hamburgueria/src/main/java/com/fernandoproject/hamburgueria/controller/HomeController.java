package com.fernandoproject.hamburgueria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fernandoproject.hamburgueria.dao.PratoDao;
import com.fernandoproject.hamburgueria.model.Prato;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class HomeController {

    @Autowired
    private PratoDao pratoRepositorio;
    

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("pages/index");
        return mv;
    }

    @GetMapping("/cardapio")
    public ModelAndView cardapio() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("pages/cardapio");
        return mv;
    }

    @GetMapping("/adicionar-prato")
    public ModelAndView adicionarPrato() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("pages/adicionar-prato");
        mv.addObject("prato", new Prato());
        return mv;
    }

    @PostMapping("AdicionarPrato")
    public ModelAndView adicionarPrato(@Valid Prato prato, BindingResult br) {
        ModelAndView mv = new ModelAndView();

        if (br.hasErrors()) {
            mv.setViewName("pages/adicionar-prato");
            mv.addObject("prato");
        } else {
            mv.setViewName("redirect:/cardapio");
            pratoRepositorio.save(prato);
        }
        
        return mv;
    }
    
}
