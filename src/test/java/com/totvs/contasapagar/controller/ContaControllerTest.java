package com.totvs.contasapagar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.totvs.contasapagar.application.dto.AtualizacaoContaDTO;
import com.totvs.contasapagar.application.dto.SituacaoContaDTO;
import com.totvs.contasapagar.application.service.ContaService;
import com.totvs.contasapagar.domain.model.Conta;
import com.totvs.contasapagar.presentation.controller.ContaController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ContaController.class)
public class ContaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContaService contaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void criarConta_DeveRetornarContaCriada() throws Exception {
        Conta novaConta = new Conta(); // Configure a nova conta conforme necessário
        when(contaService.salvarConta(any(Conta.class))).thenReturn(novaConta);

        mockMvc.perform(post("/api/contas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaConta)))
                .andExpect(status().isOk());
    }

    @Test
    public void atualizarConta_QuandoContaExiste_DeveRetornarContaAtualizada() throws Exception {
        AtualizacaoContaDTO contaDTO = new AtualizacaoContaDTO(); // Configure o DTO conforme necessário
        Conta contaAtualizada = new Conta(); // Simule a conta atualizada
        when(contaService.atualizarConta(eq(1L), any(AtualizacaoContaDTO.class))).thenReturn(contaAtualizada);

        mockMvc.perform(put("/api/contas/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contaDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void atualizarConta_QuandoContaNaoExiste_DeveRetornarNotFound() throws Exception {
        AtualizacaoContaDTO contaDTO = new AtualizacaoContaDTO(); // Configure o DTO conforme necessário
        doThrow(new EntityNotFoundException()).when(contaService).atualizarConta(eq(1L), any(AtualizacaoContaDTO.class));

        mockMvc.perform(put("/api/contas/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contaDTO)))
                .andExpect(status().isNotFound());
    }


    @Test
    public void alterarSituacaoConta_QuandoContaExiste_DeveRetornarSucesso() throws Exception {
        SituacaoContaDTO situacaoContaDTO = new SituacaoContaDTO(Conta.SituacaoConta.PAGA);
        Conta contaAtualizada = new Conta(); // Substitua pela sua implementação de Conta
        contaAtualizada.setId(1L);
        contaAtualizada.setSituacao(Conta.SituacaoConta.PAGA);

        given(contaService.alterarSituacaoConta(anyLong(), any(Conta.SituacaoConta.class))).willReturn(contaAtualizada);

        mockMvc.perform(put("/api/contas/{id}/situacao", 1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(situacaoContaDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void alterarSituacaoConta_QuandoContaNaoExiste_DeveRetornarNotFound() throws Exception {
        SituacaoContaDTO situacaoContaDTO = new SituacaoContaDTO(Conta.SituacaoConta.PAGA);

        given(contaService.alterarSituacaoConta(anyLong(), any(Conta.SituacaoConta.class)))
                .willThrow(new EntityNotFoundException("Conta não encontrada com ID: "));

        mockMvc.perform(put("/api/contas/{id}/situacao", 1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(situacaoContaDTO)))
                .andExpect(status().isNotFound());
    }

//

}
