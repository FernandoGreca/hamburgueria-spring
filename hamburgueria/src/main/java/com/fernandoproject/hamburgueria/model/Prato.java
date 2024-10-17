package com.fernandoproject.hamburgueria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Prato")
public class Prato {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "O campo nome não pode ser vazio.")
    @Size(min = 3, max = 32, message = "O campo nome deve ter entre 3 á 32 caracteres.")
    private String nome;

    @NotBlank(message = "O campo descrição não pode ser vazio.")
    @Size(min = 10, max = 128, message = "A descrição deve ter entre 10 á 128 caracteres.")
    private String descricao;

    @NotNull(message = "O preço não pode ser nulo.")
    @Positive(message = "O preço deve ser um valor positivo.")
    private Double preco;


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Double getPreco() {
        return preco;
    }
    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
