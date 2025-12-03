package br.com.gigalike.veiculos.mapper;
import br.com.gigalike.veiculos.dto.DocumentoDto;
import br.com.gigalike.veiculos.model.Documento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface DocumentoMapper {
    DocumentoDto toDto(Documento documento);
    Documento toEntity(DocumentoDto documentoDto);
    List<DocumentoDto> listToDto(List<Documento> documentoList);
}
