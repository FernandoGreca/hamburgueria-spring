package com.fernandoproject.hamburgueria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fernandoproject.hamburgueria.dao.CompraDao;
import com.fernandoproject.hamburgueria.model.Compra;

import java.util.List;

@Service
public class ServiceCompra {
    
    @Autowired
    private CompraDao compraRepositorio;

    public List<Compra> listarTodasAsCompras() {
        return compraRepositorio.findAll();
    }
}
