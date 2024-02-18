package com.totvs.contasapagar.application.service.dto;



import com.totvs.contasapagar.domain.model.Conta;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AtualizacaoContaDTO {

    @NotNull
    private LocalDate dataVencimento;

    private LocalDate dataPagamento;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal valor;

    @NotNull
    @Size(min = 1, max = 255)
    private String descricao;

    @NotNull
    private Conta.SituacaoConta situacao;
}
