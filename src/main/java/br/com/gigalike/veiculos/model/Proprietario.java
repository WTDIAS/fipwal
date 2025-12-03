package br.com.gigalike.veiculos.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "proprietarios")
public class Proprietario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String telefone;
    @Column(nullable = false)
    private boolean ativo = true;

    @OneToMany(mappedBy = "proprietario")
    private List<Veiculo> veiculos = new ArrayList<>();

    public void incluirVeiculo(Veiculo veiculo){
        this.veiculos.add(veiculo);
        veiculo.setProprietario(this);
    }
}
