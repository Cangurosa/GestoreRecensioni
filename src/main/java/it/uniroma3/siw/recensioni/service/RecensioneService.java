package it.uniroma3.siw.recensioni.service;

import it.uniroma3.siw.recensioni.model.Contenuto;
import it.uniroma3.siw.recensioni.model.Recensione;
import it.uniroma3.siw.recensioni.model.Utente;
import it.uniroma3.siw.recensioni.repository.ContenutoRepository;
import it.uniroma3.siw.recensioni.repository.RecensioneRepository;
import it.uniroma3.siw.recensioni.repository.UtenteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecensioneService {
    private final RecensioneRepository recensioneRepository;
    private final UtenteRepository utenteRepository;
    private final ContenutoRepository contenutoRepository;

    public RecensioneService(RecensioneRepository recensioneRepository, UtenteRepository utenteRepository, ContenutoRepository contenutoRepository){
        this.recensioneRepository = recensioneRepository;
        this.utenteRepository = utenteRepository;
        this.contenutoRepository = contenutoRepository;
    }

    /**
     * Metodo per creare una nuova recensione
     */
    public Recensione creaRecensione(Long utenteId, Long contenutoId, Recensione recensione){
        Utente utente = utenteRepository.findById(utenteId).orElseThrow(() -> new RuntimeException("Utente non trovato"));
        Contenuto contenuto = contenutoRepository.findById(contenutoId).orElseThrow(() -> new RuntimeException("Contenuto non trovato"));

        if(recensione.getStelle()==null || recensione.getStelle()<1 || recensione.getStelle()>5){
            throw new RuntimeException("Il voto deve essere compreso tra 1 e 5");
        }
        recensione.setUtente(utente);
        recensione.setContenuto(contenuto);

        return recensioneRepository.save(recensione);
    }

    /**
     * Metodo per trovare tutte le recensioni di un utente
     */
    public List<Recensione> getAllRecensioniByUtente(Long utenteId) {
        if (!utenteRepository.existsById(utenteId)) {
            throw new RuntimeException("Utente non trovato");
        }
        return recensioneRepository.findByUtenteId(utenteId);
    }

    /**
     * Metodo per vedere i dettagli di una recensione
     */
    public Recensione getRecensioneById(Long recensioneId){
        return recensioneRepository.findById(recensioneId).orElseThrow(() -> new RuntimeException("Recensione non trovata"));
    }

    /**
     * Metodo per modificare una recensione
     */
    public Recensione modificaRecensione(Long utenteId, Long recensioneId, Recensione recensioneAggiornata){
        Utente utente = utenteRepository.findById(utenteId).orElseThrow(() -> new RuntimeException("Utente non trovato"));
        Recensione recensione = recensioneRepository.findById(recensioneId).orElseThrow(() -> new RuntimeException("Recensione non trovata"));

        if(!recensione.getUtente().getId().equals(utenteId)){
            throw new RuntimeException("Non puoi modificare una recensione di un altro utente");
        }

        if (recensioneAggiornata.getDescrizione() == null || recensioneAggiornata.getDescrizione().isBlank()) {
            throw new RuntimeException("La descrizione è obbligatoria");
        }

        if(recensioneAggiornata.getStelle()==null || recensioneAggiornata.getStelle()<1 || recensioneAggiornata.getStelle()>5){
            throw new RuntimeException("Il voto deve essere compreso tra 1 e 5");
        }

        recensione.setStelle(recensioneAggiornata.getStelle());
        recensione.setDescrizione((recensioneAggiornata.getDescrizione()));

        return recensioneRepository.save(recensione);
    }

    /**
     * Metodo per eliminare una recensione
     */
    public void eliminaRecensione(Long utenteId, Long recensioneId) {
        Recensione recensione = recensioneRepository.findById(recensioneId).orElseThrow(() -> new RuntimeException("Recensione non trovata"));

        if (!recensione.getUtente().getId().equals(utenteId)) {
            throw new RuntimeException("Non puoi eliminare una recensione di un altro utente");
        }

        recensioneRepository.delete(recensione);
    }

    /**
     *Metodo per cercare una recensione dal titolo del contenuto
     */
    public List<Recensione> getRecensioniByTitoloContenuto(String titolo){
        if(titolo==null || titolo.isBlank()){
            throw new RuntimeException("Il titolo del contenuto è obbligatorio");
        }
        return recensioneRepository.findByContenutoTitoloContainingIgnoreCase(titolo);
    }

    /**
     * Metodo per cercare una recensione dal voto in stelle
     */
    public List<Recensione> getRecensioniByStelle(Integer stelle){
        if(stelle==null || stelle < 1 || stelle > 5){
            throw new RuntimeException("Le stelle devono essere comprese tra 1 e 5");
        }
        return recensioneRepository.findByStelle(stelle);
    }

    /**
     * Metodo per trovare tutte le recensioni di un contenuto
     */
    public List<Recensione> getRecensioniByContenuto(Long contenutoId){
        if(!contenutoRepository.existsById(contenutoId)){
            throw new RuntimeException("Contenuto non trovato");
        }
        return recensioneRepository.findByContenutoId(contenutoId);
    }

}
