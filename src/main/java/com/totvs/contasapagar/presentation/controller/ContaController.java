package com.totvs.contasapagar.presentation.controller;


import com.totvs.contasapagar.application.dto.AtualizacaoContaDTO;
import com.totvs.contasapagar.application.dto.SituacaoContaDTO;
import com.totvs.contasapagar.application.service.ContaService;
import com.totvs.contasapagar.application.service.ExcelService;
import com.totvs.contasapagar.domain.model.Conta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/contas")
public class ContaController {

    private final ContaService contaService;
    @Autowired
    ExcelService excelService;

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


    @PutMapping("/{id}/situacao")
    public ResponseEntity<?> alterarSituacaoConta(@PathVariable Long id, @Valid @RequestBody SituacaoContaDTO situacaoContaDTO) {
        try {
            Conta contaAtualizada = contaService.alterarSituacaoConta(id, situacaoContaDTO.getSituacao());
            return ResponseEntity.ok(contaAtualizada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada com ID: " + id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar situação da conta: " + e.getMessage());
        }
    }


    @GetMapping("/contas-a-pagar")
    public ResponseEntity<Page<Conta>> buscarContasFiltradas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataVencimento,
            @RequestParam String descricao,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Conta> contas = contaService.buscarContasFiltradas(dataVencimento, descricao, pageable);
        return ResponseEntity.ok(contas);
    }


    @GetMapping("{id}")
    public ResponseEntity<Conta> getContaById(@PathVariable Long id) {
        return contaService.buscarContaPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/total-contas-pagas")
    public ResponseEntity<BigDecimal> getTotalPagamentosPagosPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        BigDecimal total = contaService.calcularTotalPagamentosPagosPorPeriodo(inicio, fim);
        return ResponseEntity.ok(total == null ? BigDecimal.ZERO : total);
    }


    @GetMapping("/processar-excel")
    public ResponseEntity<String> processarExcel() {
        try {
            String mensagem = excelService.processarESalvarContas();
            return ResponseEntity.ok(mensagem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao processar o arquivo Excel: " + e.getMessage());
        }
    }


}
