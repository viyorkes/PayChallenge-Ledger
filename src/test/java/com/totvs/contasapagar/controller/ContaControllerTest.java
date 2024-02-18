package com.totvs.contasapagar.controller;

import com.totvs.contasapagar.application.dto.AtualizacaoContaDTO;
import com.totvs.contasapagar.application.dto.SituacaoContaDTO;
import com.totvs.contasapagar.application.service.ContaService;
import com.totvs.contasapagar.application.service.ExcelService;
import com.totvs.contasapagar.domain.model.Conta;
import com.totvs.contasapagar.presentation.controller.ContaController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContaControllerTest {

    @Mock
    private ContaService contaService;

    @Mock
    private ExcelService excelService;

    @InjectMocks
    private ContaController contaController;

    private Conta conta;

    @BeforeEach
    void setUp() {
        conta = new Conta();
        conta.setId(1L);
        conta.setDataVencimento(LocalDate.now());
        conta.setDescricao("Conta de Luz");
        conta.setValor(new BigDecimal("200.00"));
        conta.setSituacao(Conta.SituacaoConta.PENDENTE);
    }

    @Test
    void criarContaTest() {
        when(contaService.salvarConta(any(Conta.class))).thenReturn(conta);
        ResponseEntity<Conta> response = contaController.criarConta(conta);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(conta, response.getBody());
    }

    @Test
    void atualizarContaTest() {
        AtualizacaoContaDTO contaDTO = new AtualizacaoContaDTO();
        contaDTO.setDataVencimento(LocalDate.now());
        contaDTO.setDescricao("Conta de Água");
        contaDTO.setValor(new BigDecimal("100.00"));
        contaDTO.setSituacao(Conta.SituacaoConta.PENDENTE);

        when(contaService.atualizarConta(eq(1L), any(AtualizacaoContaDTO.class))).thenReturn(conta);
        ResponseEntity<?> response = contaController.atualizarConta(1L, contaDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(conta, response.getBody());
    }

    @Test
    void atualizarContaNotFoundTest() {
        AtualizacaoContaDTO contaDTO = new AtualizacaoContaDTO();
        when(contaService.atualizarConta(eq(1L), any(AtualizacaoContaDTO.class))).thenThrow(EntityNotFoundException.class);
        ResponseEntity<?> response = contaController.atualizarConta(1L, contaDTO);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void alterarSituacaoContaTest() {
        SituacaoContaDTO situacaoContaDTO = new SituacaoContaDTO();
        situacaoContaDTO.setSituacao(Conta.SituacaoConta.PAGA);

        when(contaService.alterarSituacaoConta(eq(1L), any(Conta.SituacaoConta.class))).thenReturn(conta);
        ResponseEntity<?> response = contaController.alterarSituacaoConta(1L, situacaoContaDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(conta, response.getBody());
    }

    @Test
    void buscarContasFiltradasTest() {
        Page<Conta> page = new PageImpl<>(Collections.singletonList(conta));
        when(contaService.buscarContasFiltradas(any(LocalDate.class), anyString(), any())).thenReturn(page);

        ResponseEntity<Page<Conta>> response = contaController.buscarContasFiltradas(LocalDate.now(), "Conta", PageRequest.of(0, 10));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getTotalElements());
    }

    @Test
    void getContaByIdTest() {
        when(contaService.buscarContaPorId(1L)).thenReturn(Optional.of(conta));
        ResponseEntity<Conta> response = contaController.getContaById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(conta, response.getBody());
    }

    @Test
    void getTotalPagamentosPagosPorPeriodoTest() {
        BigDecimal total = new BigDecimal("300.00");
        when(contaService.calcularTotalPagamentosPagosPorPeriodo(any(LocalDate.class), any(LocalDate.class))).thenReturn(total);

        ResponseEntity<BigDecimal> response = contaController.getTotalPagamentosPagosPorPeriodo(LocalDate.now().minusMonths(1), LocalDate.now());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(total, response.getBody());
    }

}




