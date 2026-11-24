package br.com.zyntra.prontia.AI;

public record MensagemIa(String role, String content) {

    public static MensagemIa system(String texto) {
        return new MensagemIa("system", texto);
    }

    public static MensagemIa user(String texto) {
        return new MensagemIa("user", texto);
    }

    public static MensagemIa assistant(String texto) {
        return new MensagemIa("assistant", texto);
    }
}