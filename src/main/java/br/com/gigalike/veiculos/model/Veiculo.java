package br.com.gigalike.veiculos.model;
import br.com.gigalike.veiculos.utilitarios.StringNormalizer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "veiculos")
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int tipoVeiculo;
    private String marca;
    private String modelo;
    private int ano;
    private double preco;
    private Double capacidadeCarga;
    private Double capacidadePortaMalas;
    private int cilindradas;
    private String observacao;

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

    public Veiculo() { }

    public void adicionaAcessorio(Acessorio acessorio){
        acessorio.getVeiculos().add(this);
        this.acessorios.add(acessorio);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public int getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(int tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = StringNormalizer.toLowerCase(marca);
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = StringNormalizer.toLowerCase(modelo);
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Double getCapacidadeCarga() {
        return capacidadeCarga;
    }

    public void setCapacidadeCarga(Double capacidadeCarga) {
        this.capacidadeCarga = capacidadeCarga;
    }

    public Double getCapacidadePortaMalas() {
        return capacidadePortaMalas;
    }

    public void setCapacidadePortaMalas(Double capacidadePortaMalas) {
        this.capacidadePortaMalas = capacidadePortaMalas;
    }

    public int getCilindradas() {
        return cilindradas;
    }

    public void setCilindradas(int cilindradas) {
        this.cilindradas = cilindradas;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = StringNormalizer.toLowerCase(observacao);
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public Set<Acessorio> getAcessorios() {
        return acessorios;
    }

    public void setAcessorios(Set<Acessorio> acessorios) {
        this.acessorios = acessorios;
    }

    @Override
    public String toString() {
        String acessoriosStr = acessorios.stream().map(Acessorio::getNome).collect(Collectors.joining(", "));
        return "Veiculo{ " +
                "id: " + id +
                ", tipoVeiculo=" + TipoVeiculo.getStrTipo(tipoVeiculo) +
                ", marca: " + marca +
                ", modelo: " + modelo +
                ", ano: " + ano +
                ", preco: " + preco +
                ", capacidadeCarga: " + capacidadeCarga +
                ", capacidadePortaMalas: " + capacidadePortaMalas +
                ", cilindradas: " + cilindradas +
                ", observacao: " + observacao +
                documento +
                proprietario +
                ", acessórios: " + acessoriosStr +
                '}';
    }
}
