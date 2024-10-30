package com.fernandoproject.hamburgueria.dao;

// Importa as classes necessárias do Spring Data JPA
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// Importa a classe Usuario do pacote model
import com.fernandoproject.hamburgueria.model.Usuario;

// Interface UsuarioDao que estende JpaRepository para operações CRUD com a entidade Usuario
public interface UsuarioDao extends JpaRepository<Usuario, Long>{
    
    // Consulta para encontrar um usuário pelo seu email
    @Query("select a from Usuario a where a.email = :email")
    public Usuario findByEmail(String email);

    // Consulta para buscar um usuário com base no nome de usuário e senha
    @Query("select a from Usuario a where a.user = :user and a.senha = :senha")
    public Usuario buscarLogin(String user, String senha);

    // Consulta para encontrar um usuário pelo nome de usuário
    @Query("select a from Usuario a where a.user = :user")
    public Usuario findByUser(String user);
}
