package com.example.MeuAlmoxarifado.domain.model;


import com.example.MeuAlmoxarifado.controller.transacaoSaida.dto.request.EditTransacaoSaidaDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transacoes_saida")
@Getter
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

    public void adicionarItem(ItemTransacaoSaida item) {
        this.itens.add(item);
    }
    public void removerItem(ItemTransacaoSaida item) {
        this.itens.remove(item);
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void update(EditTransacaoSaidaDTO dados, Requisitante requisitante, Destino destino) {
        if(dados.dataRequisicao() != null) {
            this.dataRequisicao = dados.dataRequisicao();
        }
        if(dados.ordemProducao() != null) {
            this.ordemProducao = dados.ordemProducao();
        }
        if(dados.obs() != null) {
            this.obs = dados.obs();
        }
        if(dados.idDestino() != null) {
            this.destino = destino;
        }
        if(dados.idRequisitante() != null) {
            this.requisitante = requisitante;
        }
    }
}
