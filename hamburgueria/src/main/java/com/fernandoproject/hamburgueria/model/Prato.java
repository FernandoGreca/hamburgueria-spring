package com.fernandoproject.hamburgueria.model;

// Importando as anotações necessárias para a persistência e validação
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

// Definindo a classe como uma entidade JPA
@Entity
// Especificando o nome da tabela no banco de dados
@Table(name = "Prato")
public class Prato {
    
    // Definindo o ID como chave primária e gerando automaticamente
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // O campo nome não pode ser vazio e deve ter um tamanho entre 3 e 32 caracteres
    @NotBlank(message = "O campo nome não pode ser vazio.")
    @Size(min = 3, max = 32, message = "O campo nome deve ter entre 3 á 32 caracteres.")
    private String nome;

    // O campo descrição não pode ser vazio e deve ter um tamanho entre 10 e 128 caracteres
    @NotBlank(message = "O campo descrição não pode ser vazio.")
    @Size(min = 10, max = 128, message = "A descrição deve ter entre 10 á 128 caracteres.")
    private String descricao;

    // O campo preço não pode ser nulo e deve ser um valor positivo
    @NotNull(message = "O preço não pode ser nulo.")
    @Positive(message = "O preço deve ser um valor positivo.")
    private Double preco;

    // Campo para armazenar o nome da imagem do prato
    private String nomeImagem;

    // Método getter para o nome da imagem
    public String getNomeImagem() {
        return nomeImagem;
    }

    // Método setter para o nome da imagem
    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }

    // Método getter para o ID do prato
    public long getId() {
        return id;
    }

    // Método setter para o ID do prato
    public void setId(long id) {
        this.id = id;
    }

    // Método getter para o nome do prato
    public String getNome() {
        return nome;
    }

    // Método setter para o nome do prato
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Método getter para a descrição do prato
    public String getDescricao() {
        return descricao;
    }

    // Método setter para a descrição do prato
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // Método getter para o preço do prato
    public Double getPreco() {
        return preco;
    }

    // Método setter para o preço do prato
    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
