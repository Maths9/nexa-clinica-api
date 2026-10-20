package com.nexaclinica.api.service;

import com.nexaclinica.api.entity.Procedimento;
import com.nexaclinica.api.repository.ProcedimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcedimentoService {

    private final ProcedimentoRepository procedimentoRepository;

    public List<Procedimento> listarAtivos() {
        return procedimentoRepository.findByAtivoTrue();
    }

    public Procedimento salvar(Procedimento procedimento) {
        return procedimentoRepository.save(procedimento);
    }
}
