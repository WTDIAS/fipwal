package br.com.gigalike.veiculos.mapper;
import br.com.gigalike.veiculos.dto.DocumentoDto;
import br.com.gigalike.veiculos.model.Documento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface DocumentoMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "renavam", target = "renavam")
    DocumentoDto toDto(Documento documento);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "renavam", target = "renavam")
    Documento toEntity(DocumentoDto documentoDto);

    List<DocumentoDto> listToDto(List<Documento> documentoList);
}
