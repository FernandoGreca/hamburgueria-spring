package com.fernandoproject.hamburgueria.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fernandoproject.hamburgueria.model.Usuario;

public interface UsuarioDao extends JpaRepository<Usuario, Long>{
    
    @Query("select a from Usuario a where a.email = :email")
    public Usuario findByEmail(String email);

    @Query("select a from Usuario a where a.user = :user and a.senha = :senha")
    public Usuario buscarLogin(String user, String senha);

    @Query("select a from Usuario a where a.user = :user")
    public Usuario findByUser(String user);
}
