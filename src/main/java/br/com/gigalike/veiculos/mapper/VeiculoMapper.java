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
    @Mapping(source = "acessorios", target = "acessoriosDto")
    @Mapping(source = "proprietario", target = "proprietarioDto")
    @Mapping(source = "documento", target = "documentoDto")
    VeiculoDto toDto(Veiculo veiculo);
    Veiculo toEntity(VeiculoDto veiculoDto);
    List<VeiculoDto> listToDto(List<Veiculo> veiculosList);
}
