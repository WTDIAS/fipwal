package br.com.gigalike.veiculos.repository;
import br.com.gigalike.veiculos.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    List<Documento> findTop10By();
}
