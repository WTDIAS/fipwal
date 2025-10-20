package br.com.gigalike.veiculos.mapper;
import br.com.gigalike.veiculos.dto.ProprietarioDto;
import br.com.gigalike.veiculos.model.Proprietario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProprietarioMapper {

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "telefone", target = "telefone")
    @Mapping(source = "id", target = "id")
    ProprietarioDto toDto(Proprietario proprietario);

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "telefone", target = "telefone")
    @Mapping(source = "id", target = "id")
    Proprietario toEntity(ProprietarioDto proprietarioDto);
    List<ProprietarioDto> listToDto (List<Proprietario> proprietarioList);
}
