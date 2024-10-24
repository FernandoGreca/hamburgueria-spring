package com.fernandoproject.hamburgueria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fernandoproject.hamburgueria.exceptions.EmailExistException;
import com.fernandoproject.hamburgueria.model.Usuario;
import com.fernandoproject.hamburgueria.service.ServiceUsuario;

import jakarta.validation.Valid;

@Controller
public class CadastroController {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @GetMapping("/cadastro-cliente")
    public ModelAndView cadastroClientePage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("clientes/cadastro-cliente");
        mv.addObject("usuario", new Usuario());
        return mv;
    }

    @PostMapping("salvarUsuarioCliente")
    public ModelAndView salvarUsuarioCliente(@Valid Usuario usuario, BindingResult br) {
        ModelAndView mv = new ModelAndView();

        if (br.hasErrors()) {
            mv.addObject("msgErroCadastro", "Preencha todos os campos corretamente para completar o cadastro.");
            mv.setViewName("clientes/cadastro-cliente");
        } else {
            try {
                serviceUsuario.salvarUsuario(usuario);
                mv.setViewName("redirect:/login-cliente");
            } catch (EmailExistException e) {
                mv.addObject("msgErroCadastro", e.getMessage());
                mv.setViewName("clientes/cadastro-cliente");
            } catch (Exception e) {
                mv.addObject("msgErroCadastro", "Ocorreu um erro inesperado. Tente novamente mais tarde.");
                mv.setViewName("clientes/cadastro-cliente");
            }
        }
        
        return mv;
    }
}
