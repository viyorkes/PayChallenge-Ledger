package com.totvs.contasapagar.controller;

import com.totvs.contasapagar.application.service.ContaService;
import com.totvs.contasapagar.domain.model.Conta;
import com.totvs.contasapagar.presentation.controller.ContaController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(ContaController.class)
public class ContaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContaService contaService;

    @Test
    public void criarConta_ShouldReturnSavedAccount() throws Exception {
        Conta conta = new Conta();
        // Configure sua conta conforme necessário
        conta.setDescricao("Conta de Luz");
        // Outras configurações...

        given(contaService.salvarConta(any(Conta.class))).willReturn(conta);

        mockMvc.perform(post("/api/contas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Conta de Luz\",\"valor\":100.00,\"dataVencimento\":\"2023-12-31\",\"situacao\":\"PENDENTE\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"descricao\":\"Conta de Luz\"}")); // Ajuste conforme a resposta esperada
    }
}

