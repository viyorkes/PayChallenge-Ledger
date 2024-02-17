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
}
