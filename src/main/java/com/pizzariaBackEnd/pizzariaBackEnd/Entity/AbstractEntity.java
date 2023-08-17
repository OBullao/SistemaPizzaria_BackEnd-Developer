package com.pizzariaBackEnd.pizzariaBackEnd.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@MappedSuperclass
public class AbstractEntity {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Getter@Setter
    @Column(name = "Cadastro", nullable = false)
    private LocalDateTime cadastro;

    @Getter@Setter
    @Column(name = "Atualizacao")
    private LocalDateTime atualizacao;

    @Getter@Setter
    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    @PrePersist
    private void prePresist(){
        this.cadastro = LocalDateTime.now();
        this.ativo=true;
    }

    @PreUpdate
    private void preUpdate(){
        this.atualizacao = LocalDateTime.now();
    }



}
