package it.uniroma3.siw.recensioni.service;

import it.uniroma3.siw.recensioni.model.Contenuto;
import it.uniroma3.siw.recensioni.repository.ContenutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContenutoService {
    private final ContenutoRepository contenutoRepository;

    public ContenutoService(ContenutoRepository contenutoRepository) {
        this.contenutoRepository = contenutoRepository;
    }

    public List<Contenuto> getAllContenuti(){
        return this.contenutoRepository.findAll();
    }
}
