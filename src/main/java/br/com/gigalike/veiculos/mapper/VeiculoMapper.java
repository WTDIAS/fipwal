package br.com.gigalike.veiculos.mapper;

import br.com.gigalike.veiculos.dto.VeiculoDto;
import br.com.gigalike.veiculos.model.Veiculo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = {
        ProprietarioMapper.class,
        AcessorioMapper.class,
        DocumentoMapper.class
})
public interface VeiculoMapper {

    @Mapping(source = "proprietario", target = "proprietarioDto")
    @Mapping(source = "documento", target = "documentoDto")
    @Mapping(source = "acessorios", target = "acessoriosDto")
    VeiculoDto toDto(Veiculo veiculo);

    @Mapping(source = "proprietarioDto", target = "proprietario")
    @Mapping(source = "documentoDto", target = "documento")
    @Mapping(source = "acessoriosDto", target = "acessorios")
    Veiculo toEntity(VeiculoDto veiculoDto);

    List<VeiculoDto> listToDto(List<Veiculo> veiculosList);
}
