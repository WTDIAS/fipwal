package br.com.gigalike.veiculos.repository;
import br.com.gigalike.veiculos.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo,Long> {
    List<Veiculo> findTop10By();
}
