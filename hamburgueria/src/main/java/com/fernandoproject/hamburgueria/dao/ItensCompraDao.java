package com.fernandoproject.hamburgueria.dao; // Pacote onde a interface ItensCompraDao está localizada.

import org.springframework.data.jpa.repository.JpaRepository; // Importa a interface JpaRepository do Spring Data JPA.

import com.fernandoproject.hamburgueria.model.ItensCompra; // Importa a classe ItensCompra que representa a entidade de itens de compra.

public interface ItensCompraDao extends JpaRepository<ItensCompra, Long> { // Interface ItensCompraDao que estende JpaRepository.
    // A interface JpaRepository fornece métodos CRUD (Create, Read, Update, Delete) para a entidade ItensCompra.
    // O tipo de entidade é ItensCompra e o tipo do ID é Long.
}
