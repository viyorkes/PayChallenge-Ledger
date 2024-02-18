package com.totvs.contasapagar.domain.repository;

import com.totvs.contasapagar.domain.model.Conta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ContaPageRepository extends JpaRepository<Conta, Long>, JpaSpecificationExecutor<Conta> {
}
