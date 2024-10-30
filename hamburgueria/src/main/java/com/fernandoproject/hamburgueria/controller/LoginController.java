package com.fernandoproject.hamburgueria.controller;

// Importa classes necessárias para o funcionamento do controller
import java.security.NoSuchAlgorithmException; // Exceção para erros de algoritmo de segurança

import org.springframework.beans.factory.annotation.Autowired; // Anotação para injeção de dependência
import org.springframework.stereotype.Controller; // Anotação que indica que a classe é um controller do Spring
import org.springframework.validation.BindingResult; // Classe para manipular resultados de validação
import org.springframework.web.bind.annotation.GetMapping; // Anotação para mapear requisições GET
import org.springframework.web.bind.annotation.PostMapping; // Anotação para mapear requisições POST
import org.springframework.web.servlet.ModelAndView; // Classe para modelar e visualizar resultados em um MVC

import com.fernandoproject.hamburgueria.exceptions.ServiceExc; // Exceção personalizada do serviço
import com.fernandoproject.hamburgueria.model.Usuario; // Modelo de dados para o usuário
import com.fernandoproject.hamburgueria.service.ServiceUsuario; // Serviço que manipula a lógica de negócios do usuário
import com.fernandoproject.hamburgueria.util.Util; // Classe utilitária, possivelmente para funções auxiliares

import jakarta.servlet.http.HttpSession; // Para manipulação da sessão HTTP
import jakarta.validation.Valid; // Anotação para validação de objetos

@Controller // Define a classe como um controller
public class LoginController {

    @Autowired // Injeta a dependência do serviço de usuário
    private ServiceUsuario serviceUsuario;

    // Mapeia a requisição GET para a página de login do cliente
    @GetMapping("/login-cliente")
    public ModelAndView loginClientePage() {
        ModelAndView mv = new ModelAndView(); // Cria um objeto ModelAndView
        mv.setViewName("clientes/login-cliente"); // Define a view para o login do cliente
        return mv; // Retorna o ModelAndView
    }

    // Mapeia a requisição GET para a página da hamburgueria
    @GetMapping("/hamburgueria")
    public ModelAndView hamburgueriaPage() {
        ModelAndView mv = new ModelAndView(); // Cria um objeto ModelAndView
        mv.setViewName("pages/hamburgueria"); // Define a view para a página da hamburgueria
        return mv; // Retorna o ModelAndView
    }
    
    // Mapeia a requisição POST para o login do cliente
    @PostMapping("loginCliente")
    public ModelAndView loginCliente(@Valid Usuario usuario, BindingResult br, HttpSession session) {
        ModelAndView mv = new ModelAndView(); // Cria um objeto ModelAndView

        try {
            mv.addObject("usuario", new Usuario()); // Adiciona um novo objeto Usuario à ModelAndView
            if (br.hasErrors()) { // Verifica se há erros de validação
                mv.setViewName("clientes/login-cliente"); // Retorna à página de login se houver erros
            }

            // Tenta fazer login com as credenciais do usuário
            Usuario clienteLogado = serviceUsuario.loginUsuario(usuario.getUser(), Util.md5(usuario.getSenha()));

            if (clienteLogado == null) { // Se as credenciais estiverem incorretas
                mv.addObject("msgErroLoginCliente", "Usuário ou Senha errados. Tente Novamente! "); // Mensagem de erro
                mv.setViewName("clientes/login-cliente"); // Retorna à página de login
            } else { // Se as credenciais estiverem corretas
                session.setAttribute("clienteLogado", clienteLogado); // Armazena o usuário na sessão
                mv.setViewName("redirect:/cardapio"); // Redireciona para a página do cardápio
                return mv; // Retorna o ModelAndView
            }

            return mv; // Retorna o ModelAndView se houver erros
        } catch (NoSuchAlgorithmException | ServiceExc e) { // Captura exceções
            mv.addObject("msgErroLoginCliente", "Usuário não encontrado. Tente Novamente!"); // Mensagem de erro
            mv.setViewName("clientes/login-cliente"); // Retorna à página de login
        }
        
        return mv; // Retorna o ModelAndView no final do método
    }
    
    // Mapeia a requisição POST para o logout do cliente
    @PostMapping("logoutCliente")
    public ModelAndView logoutCliente(HttpSession session) {
        session.invalidate(); // Invalida a sessão do usuário
        return new ModelAndView("redirect:/"); // Redireciona para a página inicial
    }
}
