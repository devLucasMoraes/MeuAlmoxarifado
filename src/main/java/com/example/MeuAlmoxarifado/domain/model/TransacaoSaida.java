package com.example.MeuAlmoxarifado.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transacoes_saida")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TransacaoSaida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "data_requisicao")
    private LocalDateTime dataRequisicao;

    @JoinColumn(name = "valor_total")
    private BigDecimal valorTotal;

    @JoinColumn(name = "obs")
    private String obs;

    @JoinColumn(name = "ordem_producao")
    private String ordemProducao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requisitantes_id")
    private Requisitante requisitante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinos_id")
    private Destino destino;

    @OneToMany(mappedBy = "transacaoSaida", cascade = CascadeType.ALL)
    private List<ItemTransacaoSaida> itens = new ArrayList<>();

}
