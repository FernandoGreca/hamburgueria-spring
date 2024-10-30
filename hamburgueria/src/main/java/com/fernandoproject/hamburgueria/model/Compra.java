package com.fernandoproject.hamburgueria.model; // Pacote que contém as classes de modelo da hamburgueria

import java.util.Date; // Importa a classe Date para manipulação de datas
import java.util.List; // Importa a interface List para trabalhar com coleções de itens

import jakarta.annotation.Nullable; // Importa a anotação Nullable para indicar que um campo pode ser nulo
import jakarta.persistence.CascadeType; // Importa a enumeração CascadeType para definir o comportamento de cascata nas operações de persistência
import jakarta.persistence.Entity; // Importa a anotação Entity para definir a classe como uma entidade JPA
import jakarta.persistence.FetchType; // Importa a enumeração FetchType para definir o modo de carregamento das entidades relacionadas
import jakarta.persistence.GeneratedValue; // Importa a anotação GeneratedValue para geração automática de valores de id
import jakarta.persistence.GenerationType; // Importa a enumeração GenerationType para definir o tipo de geração do id
import jakarta.persistence.Id; // Importa a anotação Id para indicar o identificador da entidade
import jakarta.persistence.ManyToOne; // Importa a anotação ManyToOne para definir um relacionamento muitos-para-um
import jakarta.persistence.OneToMany; // Importa a anotação OneToMany para definir um relacionamento um-para-muitos
import jakarta.persistence.Table; // Importa a anotação Table para especificar a tabela do banco de dados
import jakarta.persistence.Temporal; // Importa a anotação Temporal para mapear campos de data/hora
import jakarta.persistence.TemporalType; // Importa a enumeração TemporalType para definir o tipo de temporalidade
import jakarta.validation.constraints.NotEmpty; // Importa a anotação NotEmpty para validar se um campo não está vazio

@Entity // Define a classe como uma entidade JPA
@Table(name = "compra") // Especifica que a entidade está mapeada para a tabela "compra"
public class Compra {
    
    @Id // Indica que este campo é a chave primária
    @GeneratedValue(strategy = GenerationType.AUTO) // Define que o id será gerado automaticamente
    private long id; // Atributo que representa o identificador da compra

    @ManyToOne // Define um relacionamento muitos-para-um com a entidade Usuario
    private Usuario usuario; // Atributo que representa o usuário que fez a compra

    @Temporal(TemporalType.TIMESTAMP) // Especifica que o campo dataCompra deve ser tratado como data e hora
    private Date dataCompra = new Date(); // Atributo que representa a data da compra, inicializado com a data atual

    @NotEmpty // Valida que o campo não pode estar vazio
    private String formaPagamento; // Atributo que representa a forma de pagamento da compra

    @NotEmpty // Valida que o campo não pode estar vazio
    private String formaEntrega; // Atributo que representa a forma de entrega da compra

    @Nullable // Indica que o campo pode ser nulo
    private String endereco; // Atributo que representa o endereço de entrega

    private Double valorTotal; // Atributo que representa o valor total da compra

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Define um relacionamento um-para-muitos com a entidade ItensCompra
    private List<ItensCompra> itensCompra; // Atributo que contém a lista de itens da compra


    // Métodos getters e setters para acesso e modificação dos atributos

    public String getFormaEntrega() {
        return formaEntrega; // Retorna a forma de entrega
    }
    public void setFormaEntrega(String formaEntrega) {
        this.formaEntrega = formaEntrega; // Define a forma de entrega
    }
    public String getEndereco() {
        return endereco; // Retorna o endereço
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco; // Define o endereço
    }
    public long getId() {
        return id; // Retorna o id da compra
    }
    public void setId(long id) {
        this.id = id; // Define o id da compra
    }
    public Usuario getUsuario() {
        return usuario; // Retorna o usuário que fez a compra
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario; // Define o usuário da compra
    }
    public Date getDataCompra() {
        return dataCompra; // Retorna a data da compra
    }
    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra; // Define a data da compra
    }
    public String getFormaPagamento() {
        return formaPagamento; // Retorna a forma de pagamento
    }
    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento; // Define a forma de pagamento
    }
    public Double getValorTotal() {
        if (valorTotal == null) { // Verifica se o valorTotal é nulo
            valorTotal = 0.0; // Se for nulo, inicializa com zero
        }
        return valorTotal; // Retorna o valor total
    }
    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal; // Define o valor total da compra
    }
    public List<ItensCompra> getItensCompra() {
        return itensCompra; // Retorna a lista de itens da compra
    }
    public void setItensCompra(List<ItensCompra> itensCompra) {
        this.itensCompra = itensCompra; // Define a lista de itens da compra
    }
}
