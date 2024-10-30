package com.fernandoproject.hamburgueria.service;

// Importa as classes necessárias do Spring Framework
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Importa o DAO e o modelo da Compra
import com.fernandoproject.hamburgueria.dao.CompraDao;
import com.fernandoproject.hamburgueria.model.Compra;

import java.util.List;

// Indica que esta classe é um serviço do Spring
@Service
public class ServiceCompra {
    
    // Injeta uma instância do CompraDao usando a anotação @Autowired
    @Autowired
    private CompraDao compraRepositorio;

    // Método que lista todas as compras
    public List<Compra> listarTodasAsCompras() {
        // Retorna todas as compras usando o repositório
        return compraRepositorio.findAll();
    }
}
