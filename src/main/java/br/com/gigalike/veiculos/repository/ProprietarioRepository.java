package br.com.gigalike.veiculos.repository;
import br.com.gigalike.veiculos.model.Proprietario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProprietarioRepository extends JpaRepository<Proprietario,Long> {
    List<Proprietario> findTop10By();
}
