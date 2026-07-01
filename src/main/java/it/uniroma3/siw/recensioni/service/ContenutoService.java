package it.uniroma3.siw.recensioni.service;

import it.uniroma3.siw.recensioni.model.Contenuto;
import it.uniroma3.siw.recensioni.model.Recensione;
import it.uniroma3.siw.recensioni.repository.ContenutoRepository;
import it.uniroma3.siw.recensioni.repository.RecensioneRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import javax.tools.FileObject;
import java.util.List;

@Service
public class ContenutoService {
    private final ContenutoRepository contenutoRepository;
    private final RecensioneRepository recensioneRepository;

    public ContenutoService(ContenutoRepository contenutoRepository, RecensioneRepository recensioneRepository) {
        this.contenutoRepository = contenutoRepository;
        this.recensioneRepository = recensioneRepository;
    }

    public List<Contenuto> getAllContenuti(){
        return this.contenutoRepository.findAll();
    }

    public Contenuto getContenutoById(Long id) {
        return this.contenutoRepository.findById(id).orElse(null);
    }

    public void aggiungiNuovoContenuto(Contenuto contenuto) {
        this.contenutoRepository.save(contenuto);
    }

    public void eliminaContenuto(Long id) {
        Contenuto contenuto = this.contenutoRepository.findById(id).orElse(null);

        if (contenuto != null) {

            for (Recensione recensione : contenuto.getRecensioni()) {
                this.recensioneRepository.delete(recensione);
            }

            this.contenutoRepository.delete(contenuto);
        }
    }
}
