package br.com.gigalike.veiculos.repository;
import br.com.gigalike.veiculos.model.Acessorio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AcessorioRepository extends JpaRepository<Acessorio,Long> {

    List<Acessorio> findTop10By();
}
