package com.totvs.contasapagar.application.dto;

import com.totvs.contasapagar.domain.model.Conta;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SituacaoContaDTO {

    private Conta.SituacaoConta situacao;

}
