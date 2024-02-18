package com.totvs.contasapagar.application.service;


import com.totvs.contasapagar.application.dto.AtualizacaoContaDTO;
import com.totvs.contasapagar.domain.ContaSpecification;
import com.totvs.contasapagar.domain.repository.ContaPageRepository;
import com.totvs.contasapagar.domain.repository.ContaRepository;
import com.totvs.contasapagar.domain.model.Conta;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.EnumSet;


@Service
@RequiredArgsConstructor
public class ContaService {
    private final ContaRepository contaRepository;


    private final ContaPageRepository contaPageRepository;

    @Transactional
    public Conta salvarConta(Conta conta) {
        return contaRepository.save(conta);
    }


    @Transactional
    public Conta atualizarConta(Long id, AtualizacaoContaDTO dto) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

        conta.atualizarDados(dto.getDataVencimento(), dto.getDataPagamento(),
                dto.getValor(), dto.getDescricao(), dto.getSituacao());

        return contaRepository.save(conta);
    }

    @Transactional
    public Conta alterarSituacaoConta(Long id, Conta.SituacaoConta novaSituacao) {

        if (!EnumSet.of(Conta.SituacaoConta.PENDENTE, Conta.SituacaoConta.PAGA, Conta.SituacaoConta.CANCELADA).contains(novaSituacao)) {
            throw new IllegalArgumentException("Situação da conta inválida.");
        }

        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

        conta.setSituacao(novaSituacao);
        return contaRepository.save(conta);
    }


    @Transactional(readOnly = true)
    public Page<Conta> buscarContasFiltradas(LocalDate dataVencimento, String descricao, Pageable pageable) {
        return contaPageRepository.findAll(ContaSpecification.comFiltro(dataVencimento, descricao), pageable);
    }


}
