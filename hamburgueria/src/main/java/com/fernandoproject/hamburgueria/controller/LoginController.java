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
    
    @GetMapping("/")
    public ModelAndView loginPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login/login");
        mv.addObject("msg", "Projeto da página de login");
        return mv;
    }

    @GetMapping("/index")
    public ModelAndView indexPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("pages/index");
        return mv;
    }
    
    
    @PostMapping("login")
    public ModelAndView login(@Valid Usuario usuario, BindingResult br, HttpSession session) {
        ModelAndView mv = new ModelAndView();

        try {
            mv.addObject("usuario", new Usuario());
            if (br.hasErrors()) {
                mv.setViewName("login/login");
            }

            Usuario userLogin = serviceUsuario.loginUsuario(usuario.getUser(), Util.md5(usuario.getSenha()));

            if (userLogin == null) {
                mv.addObject("msgErroLogin", "Usuário não encontrado. Tente novamente!");
                mv.setViewName("login/login");
            } else {
                session.setAttribute("usuarioLogado", userLogin);
                return indexPage();
            }

            return mv;
        } catch (NoSuchAlgorithmException | ServiceExc e) {
            mv.addObject("msgErroLogin", "Usuário não encontrado. Tente Novamente!");
            mv.setViewName("login/login");
        }
        
        return mv;
    }

    @PostMapping("logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return loginPage();
    }
    
    
}
