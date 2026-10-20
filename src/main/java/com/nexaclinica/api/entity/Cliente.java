package com.nexaclinica.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nexaclinica.api.entity.enums.TipoCliente;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String telefone;

    private String email;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TipoCliente tipo = TipoCliente.ATIVO;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    private LocalDateTime ultimaVisita;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Agendamento> agendamentos;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Receita> receitas;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<HistoricoCliente> historicos;
}
