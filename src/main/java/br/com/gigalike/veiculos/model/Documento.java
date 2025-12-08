package br.com.gigalike.veiculos.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "documentos")
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String renavam;

    @Column(nullable = false)
    private boolean ativo = true;

    @OneToOne(mappedBy = "documento",fetch = FetchType.LAZY)
    private Veiculo veiculo;
}
