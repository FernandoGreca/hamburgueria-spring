package com.fernandoproject.hamburgueria.util; // Pacote onde a classe Util está localizada

import java.math.BigInteger; // Importa a classe BigInteger para manipulação de números inteiros grandes
import java.security.MessageDigest; // Importa a classe MessageDigest para gerar hashes
import java.security.NoSuchAlgorithmException; // Importa a exceção que pode ser lançada se um algoritmo específico não estiver disponível

public class Util { // Declaração da classe Util
    
    // Método estático que recebe uma senha e retorna seu hash MD5
    public static String md5(String senha) throws NoSuchAlgorithmException {
        // Cria uma instância de MessageDigest para o algoritmo MD5
        MessageDigest messageDigest = MessageDigest.getInstance("md5");
        // Gera o hash da senha em bytes e o converte para um BigInteger
        BigInteger hash = new BigInteger(1, messageDigest.digest(senha.getBytes()));
        // Retorna o hash como uma string em formato hexadecimal
        return hash.toString(16);
    }
}
