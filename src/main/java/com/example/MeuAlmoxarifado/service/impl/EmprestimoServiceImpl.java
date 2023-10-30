package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.Emprestimo;
import com.example.MeuAlmoxarifado.service.EmprestimoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmprestimoServiceImpl implements EmprestimoService {

    public Page<Emprestimo> findAll(Pageable pageable) {
        return null;
    }

    public Emprestimo findById(Long aLong) {
        return null;
    }

    public Emprestimo create(Emprestimo entity) {
        return null;
    }

    public Emprestimo update(Long aLong, Emprestimo entity) {
        return null;
    }

    public void delete(Long aLong) {

    }
}
