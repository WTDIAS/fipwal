package br.com.gigalike.veiculos.service;
import br.com.gigalike.veiculos.dto.AcessorioDto;
import br.com.gigalike.veiculos.exception.FipewalException400BadRequest;
import br.com.gigalike.veiculos.exception.FipewalException500InternalServerError;
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
    AcessorioRepository acessorioRepository;
    @Autowired
    AcessorioMapper acessorioMapper;

    public AcessorioDto buscarDtoPorId(long id){
        Acessorio acessorio = acessorioRepository.findById(id).orElseThrow(()-> new FipewalException500InternalServerError("Acessório não encontrado com ID: " + id));
        return acessorioMapper.toDto(acessorio);
    }


    public AcessorioDto salvarAcessorioNoBd(AcessorioDto acessorioDto){
        Acessorio acessorio = acessorioMapper.toEntity(acessorioDto);
         return acessorioMapper.toDto(acessorioRepository.save(acessorio));
    }


    public List<AcessorioDto> buscarAcessorios() {
        List<Acessorio> acessorioList = acessorioRepository.findTop10By();
        if (acessorioList.isEmpty()){
            throw new FipewalException500InternalServerError("Nenhum acessório encontrado.");
        }
        return acessorioMapper.listToDto(acessorioList);
    }

    public void deletaAcessorio(long id) {
        if (!acessorioRepository.existsById(id)){
            throw new FipewalException400BadRequest("Acessório não encontrado para exclusão.");
        }
        acessorioRepository.deleteById(id);
    }
}
