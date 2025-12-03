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
    VeiculoDto toDto(Veiculo veiculo);
    Veiculo toEntity(VeiculoDto veiculoDto);
    List<VeiculoDto> listToDto(List<Veiculo> veiculosList);
}
