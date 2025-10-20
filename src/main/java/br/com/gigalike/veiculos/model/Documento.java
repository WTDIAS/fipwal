package br.com.gigalike.veiculos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "documentos")
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String renavam;

    @OneToOne(mappedBy = "documento",fetch = FetchType.LAZY)
    private Veiculo veiculo;

    public Documento() {}

    public Documento(long id, String renavam) {
        this.id = id;
        this.renavam = renavam;
    }

    public long getId() {
        return id;
    }

    public String getRenavam() {
        return renavam;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    @Override
    public String toString() {
        return "Documento{" +
                "id=" + id +
                ", renavam='" + renavam +
                '}';
    }
}
