package it.uniroma3.siw.recensioni.service;

import it.uniroma3.siw.recensioni.model.Utente;
import it.uniroma3.siw.recensioni.repository.UtenteRepository;
import org.springframework.stereotype.Service;

@Service
public class UtenteService {
    private final UtenteRepository utenteRepository;

    public UtenteService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    /**
     * Metodo per trovare un utente dal suo Id
     */
    public Utente getUtenteById (Long idUtente){
        return utenteRepository.findById(idUtente).orElseThrow(() -> new RuntimeException("Utente non trovato"));
    }

    /**
     * Metodo per trovare un utente dal suo username
     */
    public Utente getUtenteByNome (String username){
        return utenteRepository.findByUsername(username);
    }

}
