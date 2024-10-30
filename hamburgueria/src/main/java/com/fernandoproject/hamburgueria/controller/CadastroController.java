package com.fernandoproject.hamburgueria.controller;

// Importando as classes necessárias para a funcionalidade do controlador.
import org.springframework.beans.factory.annotation.Autowired; // Para injeção de dependência.
import org.springframework.stereotype.Controller; // Para marcar a classe como um controlador Spring.
import org.springframework.validation.BindingResult; // Para manipular os resultados de validação.
import org.springframework.web.bind.annotation.PostMapping; // Para mapear requisições POST.
import org.springframework.web.servlet.ModelAndView; // Para retornar a visualização.

import com.fernandoproject.hamburgueria.exceptions.EmailExistException; // Exceção personalizada para e-mails existentes.
import com.fernandoproject.hamburgueria.model.Usuario; // Classe modelo que representa um usuário.
import com.fernandoproject.hamburgueria.service.ServiceUsuario; // Serviço para manipulação de usuários.

import jakarta.validation.Valid; // Anotação para validação de objetos.

@Controller // Anotação que indica que esta classe é um controlador Spring.
public class CadastroController {

    @Autowired // Injeção de dependência do serviço de usuário.
    private ServiceUsuario serviceUsuario;

    @PostMapping("salvarUsuarioCliente") // Mapeia requisições POST para a URL especificada.
    public ModelAndView salvarUsuarioCliente(@Valid Usuario usuario, BindingResult br) {
        ModelAndView mv = new ModelAndView(); // Criação de um objeto ModelAndView para manipular a resposta.

        // Verifica se há erros de validação nos dados do usuário.
        if (br.hasErrors()) {
            mv.addObject("msgErroCadastro", "Preencha todos os campos corretamente para completar o cadastro."); // Adiciona mensagem de erro ao modelo.
            mv.setViewName("clientes/login-cliente"); // Define a visualização a ser retornada.
        } else {
            try {
                serviceUsuario.salvarUsuario(usuario); // Tenta salvar o usuário usando o serviço.
                mv.setViewName("redirect:/login-cliente"); // Redireciona para a página de login após o sucesso.
            } catch (EmailExistException e) { // Captura exceção específica para e-mail já existente.
                mv.addObject("msgErroCadastro", e.getMessage()); // Adiciona a mensagem da exceção ao modelo.
                mv.setViewName("clientes/login-cliente"); // Retorna à visualização de login com erro.
            } catch (Exception e) { // Captura qualquer outra exceção não tratada.
                mv.addObject("msgErroCadastro", "Ocorreu um erro inesperado. Tente novamente mais tarde."); // Mensagem genérica de erro.
                mv.setViewName("clientes/login-cliente"); // Retorna à visualização de login com erro.
            }
        }
        
        return mv; // Retorna o ModelAndView com a resposta.
    }
}
