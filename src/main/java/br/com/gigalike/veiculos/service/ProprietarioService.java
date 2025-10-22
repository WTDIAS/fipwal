package br.com.gigalike.veiculos.service;

import br.com.gigalike.veiculos.dto.ProprietarioDto;
import br.com.gigalike.veiculos.exception.FipewalException400BadRequest;
import br.com.gigalike.veiculos.exception.FipewalException500InternalServerError;
import br.com.gigalike.veiculos.mapper.ProprietarioMapper;
import br.com.gigalike.veiculos.model.Proprietario;
import br.com.gigalike.veiculos.repository.ProprietarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProprietarioService {
    @Autowired
    ProprietarioRepository proprietarioRepository;
    @Autowired
    ProprietarioMapper proprietarioMapper;

    public ProprietarioDto salvarProprietario(ProprietarioDto proprietarioDto){
        if (proprietarioDto.nome() == null || proprietarioDto.nome().isEmpty()){
            throw new FipewalException400BadRequest("Informe um nome para o proprietário.");
        }
        Proprietario proprietario = proprietarioMapper.toEntity(proprietarioDto);
        return proprietarioMapper.toDto(proprietarioRepository.save(proprietario));
    }

    public ProprietarioDto buscaProprietarioPorId(long id){
        Proprietario proprietario = proprietarioRepository.findById(id).orElseThrow(()->new FipewalException400BadRequest("Proprietário não encontrado."));
        return proprietarioMapper.toDto(proprietario);
    }

    public List<ProprietarioDto> buscaProprietarios() {
        List<Proprietario> proprietarioList =  proprietarioRepository.findTop10By();
        return proprietarioMapper.listToDto(proprietarioList);
    }
}