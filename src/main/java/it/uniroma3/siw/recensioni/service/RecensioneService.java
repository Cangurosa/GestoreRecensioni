package it.uniroma3.siw.recensioni.service;

import it.uniroma3.siw.recensioni.model.Recensione;
import it.uniroma3.siw.recensioni.model.Utente;
import it.uniroma3.siw.recensioni.repository.RecensioneRepository;
import it.uniroma3.siw.recensioni.repository.UtenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecensioneService {
    private final RecensioneRepository recensioneRepository;
    private final UtenteRepository utenteRepository;

    public RecensioneService(RecensioneRepository recensioneRepository, UtenteRepository utenteRepository) {
        this.recensioneRepository = recensioneRepository;
        this.utenteRepository = utenteRepository;
    }

    public List<Recensione> listaTutteRecensioniDellUtente(Long idUtente){
        Utente utenteCorrente=this.utenteRepository.findById(idUtente).orElse(null);
        if(utenteCorrente!=null)
            return utenteCorrente.getRecensioni();
        else return null;
    }



}
