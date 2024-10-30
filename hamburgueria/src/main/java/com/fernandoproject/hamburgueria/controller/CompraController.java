package com.fernandoproject.hamburgueria.controller;

// Importações necessárias para a classe Controller e outras funcionalidades
import org.springframework.beans.factory.annotation.Autowired; // Para injeção de dependência
import org.springframework.stereotype.Controller; // Anotação que indica que esta classe é um Controller

import com.fernandoproject.hamburgueria.model.Compra; // Importa a classe Compra que representa uma compra
import com.fernandoproject.hamburgueria.service.ServiceCompra; // Importa o serviço de compra
import org.springframework.web.bind.annotation.GetMapping; // Importa a anotação para mapeamento de requisições GET
import org.springframework.web.servlet.ModelAndView; // Importa a classe ModelAndView para manipulação de modelos e visualizações

import java.util.Collections; // Importa a classe Collections para manipulação de listas
import java.util.List; // Importa a interface List para uso de listas

// Anotação que indica que esta classe é um Controller do Spring
@Controller
public class CompraController {

    // Injeção de dependência do serviço de compras
    @Autowired
    private ServiceCompra compraService;

    // Mapeamento da requisição GET para o endpoint "/listar"
    @GetMapping("/listar")
    public ModelAndView exibirCompras() {
        // Chama o serviço para listar todas as compras
        List<Compra> compras = compraService.listarTodasAsCompras();
        // Inverte a ordem da lista de compras
        Collections.reverse(compras);
        
        ModelAndView mv = new ModelAndView(); // Criação de um objeto ModelAndView para a resposta
        mv.setViewName("pages/compras"); // Define a página HTML que será renderizada
        mv.addObject("compras", compras); // Adiciona a lista de compras ao modelo para ser usada na visualização
        return mv; // Retorna o ModelAndView para a requisição
    }
}
