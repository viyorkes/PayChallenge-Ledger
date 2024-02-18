package com.totvs.contasapagar.domain.repository;


import com.totvs.contasapagar.domain.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {


    @Query("SELECT SUM(p.valor) FROM Conta p WHERE p.situacao = 'PAGA' AND p.dataPagamento BETWEEN :inicio AND :fim")
    BigDecimal findTotalPagosPorPeriodo(LocalDate inicio, LocalDate fim);




}