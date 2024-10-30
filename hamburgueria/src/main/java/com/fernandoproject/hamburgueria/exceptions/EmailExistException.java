package com.fernandoproject.hamburgueria.exceptions;

// A classe EmailExistException estende a classe Exception
// e é utilizada para indicar que um email já existe no sistema.
public class EmailExistException extends Exception{
    
    // SerialVersionUID é um identificador único para a versão da classe.
    // Ele é usado durante a serialização/deserialização de objetos.
    private static final long serialVersionUID = 1L;

    // Construtor da classe que recebe uma mensagem como argumento.
    // Essa mensagem será passada para o construtor da classe pai (Exception).
    public EmailExistException(String msg) {
        super(msg); // Chama o construtor da classe Exception com a mensagem fornecida.
    }
}
