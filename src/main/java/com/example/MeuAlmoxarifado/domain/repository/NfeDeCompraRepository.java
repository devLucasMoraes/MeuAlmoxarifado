package com.example.MeuAlmoxarifado.domain.repository;


import com.example.MeuAlmoxarifado.domain.model.NfeDeCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NfeDeCompraRepository extends JpaRepository<NfeDeCompra, Long> {
}
