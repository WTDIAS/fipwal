package br.com.gigalike.veiculos.mapper;
import br.com.gigalike.veiculos.dto.AcessorioDto;
import br.com.gigalike.veiculos.model.Acessorio;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AcessorioMapper {
    AcessorioDto toDto(Acessorio acessorio);
    Acessorio toEntity(AcessorioDto acessorioDto);
    List<AcessorioDto> listToDto(List<Acessorio> acessorioList);
}

