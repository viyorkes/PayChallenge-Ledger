package com.totvs.contasapagar.domain;

import com.totvs.contasapagar.domain.model.Conta;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContaSpecification {

    public static Specification<Conta> comFiltro(LocalDate dataVencimento, String descricao) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (dataVencimento != null) {
                predicates.add(cb.equal(root.get("dataVencimento"), dataVencimento));
            }

            if (descricao != null && !descricao.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}