package com.fernandoproject.hamburgueria.service;

// Importação das classes necessárias para o funcionamento do serviço
import java.security.NoSuchAlgorithmException;

// Importação de anotações e classes do Spring Framework
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Importação das classes relacionadas ao DAO e modelos
import com.fernandoproject.hamburgueria.dao.UsuarioDao;
import com.fernandoproject.hamburgueria.exceptions.CriptoExistException;
import com.fernandoproject.hamburgueria.exceptions.EmailExistException;
import com.fernandoproject.hamburgueria.exceptions.ServiceExc;
import com.fernandoproject.hamburgueria.model.Usuario;
import com.fernandoproject.hamburgueria.util.Util;

// Anotação que indica que esta classe é um serviço do Spring
@Service
public class ServiceUsuario {

    // Injeção de dependência do DAO de usuário
    @Autowired
    private UsuarioDao usuarioRepositorio;

    // Método para salvar um novo usuário
    public void salvarUsuario(Usuario usuario) throws Exception {
        try {
            // Verifica se já existe um usuário com o mesmo email
            if (usuarioRepositorio.findByEmail(usuario.getEmail()) != null) {
                throw new EmailExistException("Email ja cadastrado. Tente novamente!");
            }
            // Verifica se já existe um usuário com o mesmo nome de usuário
            if (usuarioRepositorio.findByUser(usuario.getUser()) != null) {
                throw new EmailExistException("Nome de usuário ja cadastrado. Tente novamente!");
            }

            // Criptografa a senha do usuário utilizando MD5
            usuario.setSenha(Util.md5(usuario.getSenha()));

        } catch (NoSuchAlgorithmException e) {
            // Lança uma exceção personalizada em caso de erro na criptografia
            throw new CriptoExistException("Erro na criptografia da senha.");
        }

        // Salva o usuário no repositório
        usuarioRepositorio.save(usuario);
    }

    // Método para realizar o login de um usuário
    public Usuario loginUsuario(String user, String senha) throws ServiceExc {
        // Busca o usuário no repositório com base no nome de usuário e senha
        Usuario userLogin = usuarioRepositorio.buscarLogin(user, senha);
        return userLogin; // Retorna o usuário encontrado
    }
}
