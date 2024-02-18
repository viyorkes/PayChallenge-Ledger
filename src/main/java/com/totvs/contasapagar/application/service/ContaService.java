package com.totvs.contasapagar.application.service;


import com.totvs.contasapagar.application.service.dto.AtualizacaoContaDTO;
import com.totvs.contasapagar.domain.repository.ContaRepository;
import com.totvs.contasapagar.domain.model.Conta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;



@Service
@RequiredArgsConstructor
public class ContaService {
    private final ContaRepository contaRepository;

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

}
