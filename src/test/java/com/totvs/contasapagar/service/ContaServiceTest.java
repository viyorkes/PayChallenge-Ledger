package com.totvs.contasapagar.service;



import com.totvs.contasapagar.application.dto.AtualizacaoContaDTO;
import com.totvs.contasapagar.application.service.ContaService;
import com.totvs.contasapagar.domain.model.Conta;
import com.totvs.contasapagar.domain.repository.ContaPageRepository;
import com.totvs.contasapagar.domain.repository.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private ContaPageRepository contaPageRepository;

    @InjectMocks
    private ContaService contaService;

    private Conta conta;

    @BeforeEach
    void setUp() {
        conta = new Conta();
        conta.setId(1L);
        conta.setDataVencimento(LocalDate.now());
        conta.setDescricao("Teste");
        conta.setValor(new BigDecimal("100.00"));
        conta.setSituacao(Conta.SituacaoConta.PENDENTE);
    }

    @Test
    void salvarContaTest() {
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);
        Conta resultado = contaService.salvarConta(conta);
        assertNotNull(resultado);
        assertEquals(conta.getId(), resultado.getId());
    }

    @Test
    void atualizarContaTest() {
        AtualizacaoContaDTO dto = new AtualizacaoContaDTO();
        dto.setDataVencimento(LocalDate.now());
        dto.setDataPagamento(LocalDate.now());
        dto.setValor(new BigDecimal("200.00"));
        dto.setDescricao("Atualizado");
        dto.setSituacao(Conta.SituacaoConta.PAGA);

        when(contaRepository.findById(anyLong())).thenReturn(Optional.of(conta));
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);

        Conta resultado = contaService.atualizarConta(1L, dto);

        assertNotNull(resultado);
        assertEquals(dto.getDescricao(), resultado.getDescricao());
    }

    @Test
    void atualizarContaNotFoundTest() {
        when(contaRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> contaService.atualizarConta(1L, new AtualizacaoContaDTO()));
    }

    @Test
    void buscarContasFiltradasTest() {
        Page<Conta> expectedPage = new PageImpl<>(Collections.singletonList(conta));

        // Uso de any() para Specification para evitar o problema de correspondência de argumentos
        when(contaPageRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(expectedPage);

        Page<Conta> resultado = contaService.buscarContasFiltradas(LocalDate.now(), "Teste", PageRequest.of(0, 10));

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.getTotalElements());
    }

    @Test
    void buscarContaPorIdTest() {
        when(contaRepository.findById(anyLong())).thenReturn(Optional.of(conta));
        Optional<Conta> resultado = contaService.buscarContaPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals(conta.getId(), resultado.get().getId());
    }

    @Test
    void calcularTotalPagamentosPagosPorPeriodoTest() {
        BigDecimal expectedTotal = new BigDecimal("300.00");
        when(contaRepository.findTotalPagosPorPeriodo(any(LocalDate.class), any(LocalDate.class))).thenReturn(expectedTotal);

        BigDecimal resultado = contaService.calcularTotalPagamentosPagosPorPeriodo(LocalDate.now(), LocalDate.now().plusDays(1));

        assertNotNull(resultado);
        assertEquals(expectedTotal, resultado);
    }
}

