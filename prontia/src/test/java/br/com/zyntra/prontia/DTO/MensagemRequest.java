package br.com.zyntra.prontia.DTO;

import jakarta.validation.constraints.NotBlank;

public record MensagemRequest(
        @NotBlank String Telefone,
        @NotBlank String Mensagem
) {

    public String texto() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'texto'");
    }
}