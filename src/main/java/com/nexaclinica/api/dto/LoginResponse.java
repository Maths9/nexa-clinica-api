package com.nexaclinica.api.dto;

import com.nexaclinica.api.entity.enums.RoleUsuario;

public record LoginResponse(
        Long id,
        String nome,
        String email,
        RoleUsuario role,
        Boolean ativo
) {}
