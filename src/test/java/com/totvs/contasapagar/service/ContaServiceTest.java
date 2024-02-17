package com.totvs.contasapagar.service;

import com.totvs.contasapagar.domain.model.Conta;
import com.totvs.contasapagar.domain.repository.ContaRepository;
import com.totvs.contasapagar.application.service.ContaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ContaService contaService;

    @Test
    public void salvarConta_DeveRetornarContaSalva() {
        Conta conta = new Conta();
        conta.setDescricao("Teste");
        conta.setValor(new BigDecimal("100.00"));
        // Configure sua conta conforme necessário

        when(contaRepository.save(any(Conta.class))).thenReturn(conta);

        Conta contaSalva = contaService.salvarConta(conta);

        assertThat(contaSalva).isNotNull();
        verify(contaRepository, times(1)).save(conta);
    }
}
