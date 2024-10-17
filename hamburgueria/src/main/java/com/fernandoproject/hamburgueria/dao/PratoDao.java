package com.fernandoproject.hamburgueria.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fernandoproject.hamburgueria.model.Prato;

public interface PratoDao extends JpaRepository<Prato, Long>{
    
}
