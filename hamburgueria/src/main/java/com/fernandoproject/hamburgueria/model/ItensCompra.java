package com.fernandoproject.hamburgueria.model;

// Anotação que indica que esta classe é uma entidade JPA
import jakarta.persistence.Entity;
// Anotação para gerar valores automaticamente para a chave primária
import jakarta.persistence.GeneratedValue;
// Especifica a estratégia de geração de valor
import jakarta.persistence.GenerationType;
// Anotação que define a chave primária da entidade
import jakarta.persistence.Id;
// Anotação para criar uma relação muitos-para-um
import jakarta.persistence.ManyToOne;
// Define o nome da tabela no banco de dados
import jakarta.persistence.Table;

// Anotação que marca esta classe como uma entidade JPA
@Entity
// Especifica o nome da tabela associada a esta entidade
@Table(name = "itens_compra")
public class ItensCompra {
    
    // Declaração da chave primária da entidade
    @Id
    // Indica que o valor será gerado automaticamente
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // Relacionamento muitos-para-um com a entidade Prato
    @ManyToOne
    private Prato prato;

    // Relacionamento muitos-para-um com a entidade Compra
    @ManyToOne
    private Compra compra;

    // Atributo que representa a quantidade do item
    private Integer quantidade;

    // Atributo que representa o valor unitário do item
    private Double valorUnitario;

    // Atributo que representa o valor total do item
    private Double valorTotal;

    // Método para obter o valor total, inicializando com 0.0 se for nulo
    public Double getValorTotal() {
        if (valorTotal == null) {
            valorTotal = 0.0; // Inicializa valorTotal se for nulo
        }
        return valorTotal; // Retorna o valor total
    }

    // Método para definir o valor total
    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal; // Define o valor total
    }

    // Método para obter o ID
    public long getId() {
        return id; // Retorna o ID
    }

    // Método para definir o ID
    public void setId(long id) {
        this.id = id; // Define o ID
    }

    // Método para obter o prato associado
    public Prato getPrato() {
        return prato; // Retorna o prato associado
    }

    // Método para definir o prato associado
    public void setPrato(Prato prato) {
        this.prato = prato; // Define o prato associado
    }

    // Método para obter a compra associada
    public Compra getCompra() {
        return compra; // Retorna a compra associada
    }

    // Método para definir a compra associada
    public void setCompra(Compra compra) {
        this.compra = compra; // Define a compra associada
    }

    // Método para obter a quantidade, inicializando com 0 se for nulo
    public Integer getQuantidade() {
        if (quantidade == null) {
            quantidade = 0; // Inicializa quantidade se for nulo
        }
        return quantidade; // Retorna a quantidade
    }

    // Método para definir a quantidade
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade; // Define a quantidade
    }

    // Método para obter o valor unitário
    public Double getValorUnitario() {
        return valorUnitario; // Retorna o valor unitário
    }

    // Método para definir o valor unitário
    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario; // Define o valor unitário
    }
}
