package com.fernandoproject.hamburgueria.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fernandoproject.hamburgueria.model.ItensCompra;

public interface ItensCompraDao extends JpaRepository<ItensCompra, Long> {
    
}
