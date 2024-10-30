package com.fernandoproject.hamburgueria.controller; // Declaração do pacote onde a classe está localizada.

import org.springframework.stereotype.Controller; // Importa a anotação Controller do Spring.
import org.springframework.web.servlet.ModelAndView; // Importa a classe ModelAndView para manipulação de modelos e visualizações.
import org.springframework.web.bind.annotation.GetMapping; // Importa a anotação GetMapping para mapear requisições GET.

@Controller // Indica que esta classe é um controlador no padrão MVC do Spring.
public class HomeController { // Declaração da classe HomeController.

    @GetMapping("/") // Mapeia requisições GET para a raiz do aplicativo ("/").
    public ModelAndView index() { // Método que retorna um objeto ModelAndView.
        ModelAndView mv = new ModelAndView(); // Cria uma nova instância de ModelAndView.
        mv.setViewName("pages/index"); // Define o nome da view a ser renderizada.
        return mv; // Retorna o objeto ModelAndView.
    }

}
