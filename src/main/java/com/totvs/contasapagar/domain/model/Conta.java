package com.totvs.contasapagar.domain.model;



import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;


@Entity
@Data
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private BigDecimal valor;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private SituacaoConta situacao;

    public enum SituacaoConta {
        PENDENTE, PAGA, CANCELADA
    }




    public void atualizarDados(LocalDate dataVencimento, LocalDate dataPagamento,
                               BigDecimal valor, String descricao, SituacaoConta situacao) {
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.valor = valor;
        this.descricao = descricao;
        this.situacao = situacao;
    }
}
