package com.totvs.contasapagar.presentation.controller;


import com.totvs.contasapagar.application.service.ContaService;
import com.totvs.contasapagar.domain.model.Conta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/contas")
public class ContaController {

    private final ContaService contaService;

    @Autowired
    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    public ResponseEntity<Conta> criarConta(@RequestBody Conta conta) {
        Conta novaConta = contaService.salvarConta(conta);
        return ResponseEntity.ok(novaConta);
    }


}
