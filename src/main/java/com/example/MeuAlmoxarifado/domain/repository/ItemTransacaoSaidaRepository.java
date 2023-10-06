package com.example.MeuAlmoxarifado.domain.repository;


import com.example.MeuAlmoxarifado.domain.model.ItemTransacaoSaida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemTransacaoSaidaRepository extends JpaRepository<ItemTransacaoSaida, Long> {
}
