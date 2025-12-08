package br.com.gigalike.veiculos.repository;
import br.com.gigalike.veiculos.model.Acessorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AcessorioRepository extends JpaRepository<Acessorio,Long> {
    List<Acessorio> findTop10By();

    @Query(
            value = "SELECT * FROM ACESSORIOS A JOIN VEICULO_ACESSORIO B ON A.ID = B.ACESSORIO_ID WHERE B.VEICULO_ID = :idVeiculo",
            nativeQuery = true
    )
    List<Acessorio> buscaAcessoriosDoVeiculo(long idVeiculo);
}
