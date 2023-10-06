package com.example.MeuAlmoxarifado.domain.repository;


import com.example.MeuAlmoxarifado.domain.model.Destino;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinoRepository extends JpaRepository<Destino, Long> {
    Page<Destino> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    boolean existsByNome(String nome);
}
