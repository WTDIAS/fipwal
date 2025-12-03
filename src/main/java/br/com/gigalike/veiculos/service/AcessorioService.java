package br.com.gigalike.veiculos.service;
import br.com.gigalike.veiculos.dto.AcessorioDto;
import br.com.gigalike.veiculos.exception.ExceptionBadRequest;
import br.com.gigalike.veiculos.exception.ExceptionNotFound;
import br.com.gigalike.veiculos.mapper.AcessorioMapper;
import br.com.gigalike.veiculos.model.Acessorio;
import br.com.gigalike.veiculos.repository.AcessorioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AcessorioService {
    @Autowired
    private AcessorioRepository acessorioRepository;
    @Autowired
    private AcessorioMapper acessorioMapper;

    public AcessorioDto buscarDtoPorId(long id){
        Acessorio acessorio = acessorioRepository.findById(id).orElseThrow(
                ()-> new ExceptionBadRequest("Acessório não encontrado com ID: " + id));
        return acessorioMapper.toDto(acessorio);
    }


    public AcessorioDto salvarAcessorioNoBd(AcessorioDto acessorioDto){
       if (acessorioDto == null || acessorioDto.nome() == null || acessorioDto.nome().trim().isEmpty() || acessorioDto.preco() <= 0){
           throw new ExceptionBadRequest("Dados inválidos para cadastro de acessório.");
       }
       Acessorio acessorio = acessorioMapper.toEntity(acessorioDto);
       return acessorioMapper.toDto(acessorioRepository.save(acessorio));
    }


    public List<AcessorioDto> buscarAcessorios() {
        List<Acessorio> acessorioList = acessorioRepository.findTop10By();
        if (acessorioList.isEmpty()){
            throw new ExceptionNotFound("Nenhum acessório encontrado.");
        }
        return acessorioMapper.listToDto(acessorioList);
    }

    public void deletaAcessorio(long id) {
        if (!acessorioRepository.existsById(id)){
            throw new ExceptionNotFound("Acessório com id: " + id + " não encontrado para exclusão.");
        }
        acessorioRepository.deleteById(id);
    }
}
