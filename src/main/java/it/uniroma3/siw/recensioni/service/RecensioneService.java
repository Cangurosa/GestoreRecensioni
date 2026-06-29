package it.uniroma3.siw.recensioni.service;

import it.uniroma3.siw.recensioni.model.Contenuto;
import it.uniroma3.siw.recensioni.model.Recensione;
import it.uniroma3.siw.recensioni.model.Utente;
import it.uniroma3.siw.recensioni.repository.ContenutoRepository;
import it.uniroma3.siw.recensioni.repository.RecensioneRepository;
import it.uniroma3.siw.recensioni.repository.UtenteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecensioneService {
    private final RecensioneRepository recensioneRepository;
    private final UtenteRepository utenteRepository;
    private final ContenutoRepository contenutoRepository;

    public RecensioneService(RecensioneRepository recensioneRepository, UtenteRepository utenteRepository, ContenutoRepository contenutoRepository) {
        this.recensioneRepository = recensioneRepository;
        this.utenteRepository = utenteRepository;
        this.contenutoRepository = contenutoRepository;
    }

    public List<Recensione> getAllRecensioniDellUtente(String username){
        Utente utenteCorrente=this.utenteRepository.findByUsername(username);
        if(utenteCorrente!=null)
            return utenteCorrente.getRecensioni();
        else
            return null;
    }

    public List<Recensione> getAllRecensioniDatabase(){
        return this.recensioneRepository.findAll();
    }

    public List<Recensione> getALLRecensioniDiUnContenutoSpecifico(Long idContenuto){
        Contenuto contenutoCorrente=this.contenutoRepository.findById(idContenuto).orElse(null);
        if(contenutoCorrente!=null)
            return contenutoCorrente.getRecensioni();
        else
            return null;
    }

    public void eliminaRecensione(Long id){
        Recensione recensioneCorrente=this.recensioneRepository.findById(id).orElse(null);
        if(recensioneCorrente!=null){
            Contenuto contenuto=recensioneCorrente.getContenuto();
            contenuto.getRecensioni().remove(recensioneCorrente);
            recensioneCorrente.setContenuto(null);
            this.recensioneRepository.delete(recensioneCorrente);
        }


    }

    public void salvaRecensione(Recensione recensione, String username, Long idContenuto){
        recensione.setUtente(this.utenteRepository.findByUsername(username));
        recensione.setDataEOra(LocalDateTime.now());
        recensione.setContenuto(this.contenutoRepository.findById(idContenuto).orElse(null));
        this.recensioneRepository.save(recensione);
    }

}
