package br.com.zyntra.prontia.controller;

import br.com.zyntra.prontia.DTO.MensagemRequest;
import br.com.zyntra.prontia.DTO.MensagemResponse;
import br.com.zyntra.prontia.service.AtendimentoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/mensagens")
public class MensagemController {
    private final AtendimentoService atendimentoService;
    
    public MensagemController(AtendimentoService atendimentoService) {
        this.atendimentoService = atendimentoService;
    }
    @PostMapping
    public MensagemResponse receberMensagem(@RequestBody @Valid MensagemRequest request) {
        return atendimentoService.responder(request);
    }
}
