package br.com.gigalike.veiculos.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "acessorios")
public class Acessorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Column(nullable = false)
    private double preco;

    @Column(nullable = false)
    private boolean ativo = true;

    @ManyToMany(mappedBy = "acessorios")
    private Set<Veiculo> veiculos = new HashSet<>();

}
