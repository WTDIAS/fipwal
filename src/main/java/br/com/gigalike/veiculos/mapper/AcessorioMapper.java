package br.com.gigalike.veiculos.mapper;
import br.com.gigalike.veiculos.dto.AcessorioDto;
import br.com.gigalike.veiculos.model.Acessorio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AcessorioMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "preco", target = "preco")
    AcessorioDto toDto(Acessorio acessorio);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "preco", target = "preco")
    Acessorio toEntity(AcessorioDto acessorioDto);
    List<AcessorioDto> listToDto(List<Acessorio> acessorioList);
}

