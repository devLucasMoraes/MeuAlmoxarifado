package com.example.MeuAlmoxarifado.controller.filter;

import com.example.MeuAlmoxarifado.domain.model.LocalDeAplicacao;
import org.springframework.data.jpa.domain.Specification;

public record LocalDeAplicacaoSearchFilter(
        String label,
        String nome
) {
    public Specification<LocalDeAplicacao> toSpec() {
        Specification<LocalDeAplicacao> spec = Specification.where(null);

        if (this.label != null) {
            spec = spec.and(nomeLike(this.label));
        }

        if(this.nome != null){
            spec = spec.and(nomeLike(this.nome));
        }

        return spec;
    }

    private Specification<LocalDeAplicacao> nomeLike(String nome) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nome")),
                        "%" + nome.toLowerCase() + "%");
    }

}
