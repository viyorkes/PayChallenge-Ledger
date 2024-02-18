package com.totvs.contasapagar.presentation.controller;


import com.totvs.contasapagar.application.service.ContaService;
import com.totvs.contasapagar.application.service.dto.AtualizacaoContaDTO;
import com.totvs.contasapagar.domain.model.Conta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;


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


    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarConta(@PathVariable Long id, @RequestBody AtualizacaoContaDTO contaDTO) {
        try {
            Conta contaAtualizada = contaService.atualizarConta(id, contaDTO);
            return ResponseEntity.ok(contaAtualizada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar conta: " + e.getMessage());
        }
    }



}
