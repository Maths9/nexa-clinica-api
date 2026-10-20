package com.nexaclinica.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "procedimentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Procedimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer duracaoMin;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Builder.Default
    private Boolean ativo = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "procedimento")
    private List<Agendamento> agendamentos;

    @JsonIgnore
    @OneToMany(mappedBy = "procedimento")
    private List<Receita> receitas;

    @JsonIgnore
    @OneToMany(mappedBy = "procedimento")
    private List<HistoricoCliente> historicos;
}
