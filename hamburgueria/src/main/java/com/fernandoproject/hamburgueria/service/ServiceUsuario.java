package com.fernandoproject.hamburgueria.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fernandoproject.hamburgueria.dao.UsuarioDao;
import com.fernandoproject.hamburgueria.exceptions.CriptoExistException;
import com.fernandoproject.hamburgueria.exceptions.EmailExistException;
import com.fernandoproject.hamburgueria.exceptions.ServiceExc;
import com.fernandoproject.hamburgueria.model.Usuario;
import com.fernandoproject.hamburgueria.util.Util;

@Service
public class ServiceUsuario {
    
    @Autowired
    private UsuarioDao usuarioRepositorio;

    public void salvarUsuario(Usuario usuario) throws Exception {
        try {
            if (usuarioRepositorio.findByEmail(usuario.getEmail()) != null) {
                throw new EmailExistException("Email ja cadastrado. Tente novamente!");
            }
            if (usuarioRepositorio.findByUser(usuario.getUser()) != null) {
                throw new EmailExistException("Nome de usuário ja cadastrado. Tente novamente!");
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
