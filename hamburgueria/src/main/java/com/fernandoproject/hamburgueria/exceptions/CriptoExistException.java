package com.fernandoproject.hamburgueria.exceptions;

// Classe que representa uma exceção personalizada chamada CriptoExistException
public class CriptoExistException extends Exception {
    
    // Número de versão para controle de compatibilidade de serialização
    private static final long serialVersionUID = 1L;

    // Construtor da exceção que recebe uma mensagem como parâmetro
    public CriptoExistException(String msg) {
        // Chama o construtor da classe pai (Exception) passando a mensagem
        super(msg);
    }
}
