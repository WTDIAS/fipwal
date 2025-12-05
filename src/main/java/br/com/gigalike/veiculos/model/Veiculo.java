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
@Entity
@Table(name = "veiculos")
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String tipoVeiculo;
    private String marca;
    private String modelo;
    private int ano;
    @Column(nullable = false)
    private double preco;
    private Double capacidadeCarga;
    private Double capacidadePortaMalas;
    private int cilindradas;
    private String observacao;
    private String combustivel;
    @Column(nullable = false)
    private String codigoFipe;
    @Column(nullable = false)
    private boolean ativo = true;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Documento documento;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Proprietario proprietario;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "veiculo_acessorio",
            joinColumns = @JoinColumn(name = "veiculo_id"),
            inverseJoinColumns = @JoinColumn(name = "acessorio_id")
    )
    private Set<Acessorio> acessorios = new HashSet<>();

    public void adicionaAcessorio(Acessorio acessorio){
        this.acessorios.add(acessorio);
        acessorio.getVeiculos().add(this);
    }

}
