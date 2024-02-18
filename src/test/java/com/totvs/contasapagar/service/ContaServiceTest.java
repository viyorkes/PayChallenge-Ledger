package com.totvs.contasapagar.service;


import com.totvs.contasapagar.application.dto.AtualizacaoContaDTO;
import com.totvs.contasapagar.application.service.ContaService;
import com.totvs.contasapagar.domain.model.Conta;
import com.totvs.contasapagar.domain.repository.ContaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ContaService contaService;

    @Test
    void salvarConta_DeveSalvarConta() {
        Conta conta = new Conta();

        when(contaRepository.save(any(Conta.class))).thenReturn(conta);

        contaService.salvarConta(conta);

        verify(contaRepository).save(conta);
    }

    @Test
    void atualizarConta_QuandoContaExiste_DeveAtualizarConta() {
        Long contaId = 1L;
        Conta contaExistente = new Conta();
        contaExistente.setId(contaId);
        contaExistente.setDescricao("Descrição Original");

        AtualizacaoContaDTO dto = new AtualizacaoContaDTO();
        dto.setDataVencimento(LocalDate.now());
        dto.setDataPagamento(LocalDate.now());
        dto.setValor(new BigDecimal("100.00"));
        dto.setDescricao("Descrição Atualizada");
        dto.setSituacao(Conta.SituacaoConta.PAGA);

        when(contaRepository.findById(contaId)).thenReturn(Optional.of(contaExistente));
        when(contaRepository.save(any(Conta.class))).thenReturn(contaExistente);

        contaService.atualizarConta(contaId, dto);

        verify(contaRepository).save(contaExistente);
        verify(contaRepository).findById(contaId);
    }

    @Test
    void atualizarConta_QuandoContaNaoExiste_DeveLancarExcecao() {
        Long contaId = 1L;
        AtualizacaoContaDTO dto = new AtualizacaoContaDTO();

        when(contaRepository.findById(contaId)).thenThrow(new EntityNotFoundException("Conta não encontrada"));

        assertThrows(EntityNotFoundException.class, () -> contaService.atualizarConta(contaId, dto));

        verify(contaRepository, never()).save(any(Conta.class));
    }


}
