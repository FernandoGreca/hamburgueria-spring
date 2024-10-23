package com.fernandoproject.hamburgueria.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fernandoproject.hamburgueria.model.Carrinho;
import com.fernandoproject.hamburgueria.model.Usuario;

public interface CarrinhoDao extends JpaRepository<Carrinho, Long> {
    Carrinho findbyUsuario(Usuario usuario);
}
