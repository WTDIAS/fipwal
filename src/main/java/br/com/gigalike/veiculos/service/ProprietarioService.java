package br.com.gigalike.veiculos.service;

import br.com.gigalike.veiculos.dto.ProprietarioDto;
import br.com.gigalike.veiculos.exception.ExceptionBadRequest;
import br.com.gigalike.veiculos.exception.ExceptionNotFound;
import br.com.gigalike.veiculos.mapper.ProprietarioMapper;
import br.com.gigalike.veiculos.model.Proprietario;
import br.com.gigalike.veiculos.model.Veiculo;
import br.com.gigalike.veiculos.repository.ProprietarioRepository;
import br.com.gigalike.veiculos.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class ProprietarioService {
    @Autowired
    private ProprietarioRepository proprietarioRepository;
    @Autowired
    private ProprietarioMapper proprietarioMapper;
    @Autowired
    private VeiculoRepository veiculoRepository;

    public ProprietarioDto salvarProprietario(ProprietarioDto proprietarioDto){
        if (proprietarioDto.nome() == null || proprietarioDto.nome().isEmpty()){
            throw new ExceptionBadRequest("Informe um nome para o proprietário.");
        }
        Proprietario proprietario = proprietarioMapper.toEntity(proprietarioDto);
        return proprietarioMapper.toDto(proprietarioRepository.save(proprietario));
    }

    public ProprietarioDto buscaProprietarioPorId(long id){
        Proprietario proprietario = proprietarioRepository.findById(id).orElseThrow(()->new ExceptionBadRequest("Proprietário não encontrado."));
        return proprietarioMapper.toDto(proprietario);
    }

    public List<ProprietarioDto> buscaProprietarios() {
        List<Proprietario> proprietarioList =  proprietarioRepository.findTop10By();
        return proprietarioMapper.listToDto(proprietarioList);
    }

    public void excluir(long id) {
        Proprietario proprietario = proprietarioRepository.findById(id).orElseThrow(
                ()->new ExceptionNotFound("Não encontrado proprietário com id " + id + " para fazer a exclusão."));
        Veiculo veiculo = veiculoRepository.findByProprietarioId(id);
        if (veiculo != null){
            veiculo.setProprietario(null);
        }
        proprietario.setAtivo(false);
    }
}