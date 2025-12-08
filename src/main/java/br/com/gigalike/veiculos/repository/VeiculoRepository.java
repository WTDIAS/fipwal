package br.com.gigalike.veiculos.repository;
import br.com.gigalike.veiculos.model.Veiculo;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo,Long> {
    List<Veiculo> findTop10By();

    List<Veiculo> findAllByCodigoFipe(@NotNull(message = "O campo código fipe (CodigoFipe) é obrigatório.") String s);


}
