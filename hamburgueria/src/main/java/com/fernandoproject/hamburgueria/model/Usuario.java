package com.fernandoproject.hamburgueria.model;

// Importa as anotações necessárias do Jakarta Persistence para mapear a classe como uma entidade JPA
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// Importa as anotações para validação de dados
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Declara que a classe Usuario é uma entidade JPA
@Entity
// Especifica o nome da tabela correspondente no banco de dados
@Table(name = "usuario")
public class Usuario {

    // Declara o atributo id como chave primária e gera automaticamente seu valor
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // Valida que o email deve ser um formato válido e não pode ser vazio
    @Email
    @NotBlank(message = "O campo email não pode ser vazio.")
    private String email;

    // Valida que o nome do usuário deve ter entre 3 e 32 caracteres e não pode ser vazio
    @Size(min = 3, max = 32, message = "O nome do seu usuário deve ter no mínimo 3 e no máximo 32 caracteres.")
    @NotBlank(message = "O campo email não pode ser vazio.")
    private String user;

    // Valida que a senha deve ter entre 3 e 32 caracteres
    @Size(min = 3, max = 32, message = "A sua senha deve conter entre 3 a 32 caracteres.")
    private String senha;

    // Atributo que indica se o usuário é um administrador, padrão é false
    private boolean adm = false;
    
    // Método para obter o valor do atributo adm
    public boolean isAdm() {
        return adm;
    }

    // Método para definir o valor do atributo adm
    public void setAdm(boolean adm) {
        this.adm = adm;
    }

    // Método para obter o valor do id
    public long getId() {
        return id;
    }

    // Método para definir o valor do id
    public void setId(long id) {
        this.id = id;
    }

    // Método para obter o valor do email
    public String getEmail() {
        return email;
    }

    // Método para definir o valor do email
    public void setEmail(String email) {
        this.email = email;
    }

    // Método para obter o valor do nome de usuário
    public String getUser() {
        return user;
    }

    // Método para definir o valor do nome de usuário
    public void setUser(String user) {
        this.user = user;
    }

    // Método para obter o valor da senha
    public String getSenha() {
        return senha;
    }

    // Método para definir o valor da senha
    public void setSenha(String senha) {
        this.senha = senha;
    }

    // Sobrescreve o método toString para retornar o nome de usuário
    @Override
    public String toString() {
        return getUser();
    }
}
