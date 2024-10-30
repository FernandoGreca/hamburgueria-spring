package com.fernandoproject.hamburgueria.dao;

// Importa a interface JpaRepository do Spring Data JPA, que fornece métodos para operações CRUD.
import org.springframework.data.jpa.repository.JpaRepository;

// Importa a classe Compra que representa a entidade da compra.
import com.fernandoproject.hamburgueria.model.Compra;

// Interface CompraDao que estende JpaRepository, permitindo operações de banco de dados para a entidade Compra.
public interface CompraDao extends JpaRepository<Compra, Long>{
    // JpaRepository fornece métodos prontos para salvar, buscar, atualizar e deletar objetos do tipo Compra.
}
