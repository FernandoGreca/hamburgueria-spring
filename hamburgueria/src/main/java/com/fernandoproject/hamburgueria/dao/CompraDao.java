package com.fernandoproject.hamburgueria.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fernandoproject.hamburgueria.model.Compra;

public interface CompraDao extends JpaRepository<Compra, Long>{
    
}
