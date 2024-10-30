package com.fernandoproject.hamburgueria.exceptions; // Pacote onde a classe ServiceExc está localizada

public class ServiceExc extends Exception { // Declaração da classe ServiceExc que estende a classe Exception
    private static final long serialVersionUID = 1L; // Versão da classe para controle de serialização

    // Construtor da classe ServiceExc que recebe uma mensagem de erro como parâmetro
    public ServiceExc(String msg) {
        super(msg); // Chama o construtor da classe pai (Exception) com a mensagem recebida
    }
}
