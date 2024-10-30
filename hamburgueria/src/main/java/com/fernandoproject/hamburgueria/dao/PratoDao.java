package com.fernandoproject.hamburgueria.dao; // Declaração do pacote onde a classe está localizada

import org.springframework.data.jpa.repository.JpaRepository; // Importa a interface JpaRepository do Spring Data JPA, que fornece métodos para operações CRUD

import com.fernandoproject.hamburgueria.model.Prato; // Importa a classe Prato, que representa a entidade do prato no banco de dados

// Interface PratoDao que estende JpaRepository
public interface PratoDao extends JpaRepository<Prato, Long>{ // Indica que esta interface irá gerenciar a entidade Prato e que a chave primária é do tipo Long
    
    // Não é necessário implementar métodos, pois JpaRepository já fornece a implementação para operações comuns
}
