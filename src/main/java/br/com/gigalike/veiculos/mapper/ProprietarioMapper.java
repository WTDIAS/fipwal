package br.com.gigalike.veiculos.mapper;
import br.com.gigalike.veiculos.dto.ProprietarioDto;
import br.com.gigalike.veiculos.model.Proprietario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProprietarioMapper {
    ProprietarioDto toDto(Proprietario proprietario);
    Proprietario toEntity(ProprietarioDto proprietarioDto);
    List<ProprietarioDto> listToDto (List<Proprietario> proprietarioList);
}
