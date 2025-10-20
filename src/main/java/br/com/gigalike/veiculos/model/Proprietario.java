package br.com.gigalike.veiculos.model;
import br.com.gigalike.veiculos.utilitarios.StringNormalizer;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proprietarios")
public class Proprietario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String nome;
    private String telefone;

    @OneToMany(mappedBy = "proprietario")
    private List<Veiculo> veiculos = new ArrayList<>();

    public void incluirVeiculo(Veiculo veiculo){
        veiculo.setProprietario(this);
        this.veiculos.add(veiculo);
    }

    public Proprietario() { }

    public Proprietario(long id, String nome, String telefone) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

    public Proprietario(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = StringNormalizer.toLowerCase(nome);
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Proprietario{" +
                "id=" + id +
                ", nome='" + nome +
                ", telefone='" + telefone +
                '}';
    }
}
