package com.fernandogreca.login.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fernandogreca.login.dao.UsuarioDao;
import com.fernandogreca.login.exceptions.CriptoExistException;
import com.fernandogreca.login.exceptions.EmailExistException;
import com.fernandogreca.login.exceptions.ServiceExc;
import com.fernandogreca.login.model.Usuario;
import com.fernandogreca.login.util.Util;

@Service
public class ServiceUsuario {
    
    @Autowired
    private UsuarioDao usuarioRepositorio;

    public void salvarUsuario(Usuario usuario) throws Exception {
        try {
            if (usuarioRepositorio.findByEmail(usuario.getEmail()) != null) {
                throw new EmailExistException("Email ja cadastrado. Tente novamente!");
            }

            usuario.setSenha(Util.md5(usuario.getSenha()));

        } catch (NoSuchAlgorithmException e) {
            throw new CriptoExistException("Erro na criptografia da senha.");
        }

        usuarioRepositorio.save(usuario);
    }

    public Usuario loginUsuario(String user, String senha) throws ServiceExc {
        Usuario userLogin = usuarioRepositorio.buscarLogin(user, senha);
        return userLogin;
    }
}
