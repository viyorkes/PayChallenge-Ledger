package com.totvs.contasapagar.application.service;


import com.totvs.contasapagar.domain.repository.ContaRepository;
import com.totvs.contasapagar.domain.model.Conta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ContaService {
    private final ContaRepository contaRepository;

    @Transactional
    public Conta salvarConta(Conta conta) {
        return contaRepository.save(conta);
    }


}
