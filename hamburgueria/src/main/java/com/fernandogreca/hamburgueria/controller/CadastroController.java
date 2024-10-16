package com.fernandogreca.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fernandogreca.login.exceptions.EmailExistException;
import com.fernandogreca.login.model.Usuario;
import com.fernandogreca.login.service.ServiceUsuario;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class CadastroController {

    @Autowired
    private ServiceUsuario serviceUsuario;
    
    @GetMapping("/cadastro")
    public ModelAndView cadastroPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login/cadastro");
        mv.addObject("usuario", new Usuario());
        return mv;
    }

    @PostMapping("salvarUsuario")
    public ModelAndView salvarUsuario(@Valid Usuario usuario, BindingResult br) {
        ModelAndView mv = new ModelAndView();

        if (br.hasErrors()) {
            mv.addObject("msgErroCadastro", "Preencha todos os campos corretamente para completar o cadastro.");
            mv.setViewName("login/cadastro");
        } else {
            try {
                serviceUsuario.salvarUsuario(usuario);
                mv.setViewName("redirect:/");
            } catch (EmailExistException e) {
                mv.addObject("msgErroCadastro", e.getMessage());
                mv.setViewName("login/cadastro");
            } catch (Exception e) {
                mv.addObject("msgErroCadastro", "Ocorreu um erro inesperado. Tente novamente mais tarde.");
                mv.setViewName("login/cadastro");
            }
        }
        
        return mv;
    }
    
    
}
