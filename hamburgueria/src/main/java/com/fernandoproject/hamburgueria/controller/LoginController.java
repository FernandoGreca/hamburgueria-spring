package com.fernandoproject.hamburgueria.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fernandoproject.hamburgueria.exceptions.ServiceExc;
import com.fernandoproject.hamburgueria.model.Usuario;
import com.fernandoproject.hamburgueria.service.ServiceUsuario;
import com.fernandoproject.hamburgueria.util.Util;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @GetMapping("/login-cliente")
    public ModelAndView loginClientePage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("clientes/login-cliente");
        return mv;
    }

    @GetMapping("/hamburgueria")
    public ModelAndView hamburgueriaPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("pages/hamburgueria");
        return mv;
    }
    

    @PostMapping("loginCliente")
    public ModelAndView loginCliente(@Valid Usuario usuario, BindingResult br, HttpSession session) {
        ModelAndView mv = new ModelAndView();

        try {
            mv.addObject("usuario", new Usuario());
            if (br.hasErrors()) {
                mv.setViewName("clientes/login-cliente");
            }

            Usuario clienteLogado = serviceUsuario.loginUsuario(usuario.getUser(), Util.md5(usuario.getSenha()));

            if (clienteLogado == null) {
                mv.addObject("msgErroLoginCliente", "Usuário não encontrado. Tente novamente!");
                mv.setViewName("clientes/login-cliente");
            } else {
                session.setAttribute("clienteLogado", clienteLogado);
                mv.setViewName("pages/index");
                return mv;
            }

            return mv;
        } catch (NoSuchAlgorithmException | ServiceExc e) {
            mv.addObject("msgErroLoginCliente", "Usuário não encontrado. Tente Novamente!");
            mv.setViewName("clientes/login-cliente");
        }
        
        return mv;
    }
    
    @PostMapping("logoutCliente")
    public ModelAndView logoutCliente(HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:/");
    }
}
