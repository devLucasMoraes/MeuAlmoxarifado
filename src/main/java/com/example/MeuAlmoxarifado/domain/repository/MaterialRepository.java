package com.example.MeuAlmoxarifado.domain.repository;


import com.example.MeuAlmoxarifado.domain.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

    boolean existsByDescricao(String descricao);
}
