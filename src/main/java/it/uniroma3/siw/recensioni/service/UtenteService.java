package it.uniroma3.siw.recensioni.service;

import it.uniroma3.siw.recensioni.classiAusiliarie.RuoloUtente;
import it.uniroma3.siw.recensioni.model.Utente;
import it.uniroma3.siw.recensioni.repository.UtenteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public UtenteService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Utente findByUsername(String username) {
        return this.utenteRepository.findByUsername(username);
    }

    public void saveUtente(Utente utente) {
        this.utenteRepository.save(utente);
    }

    public void registraNuovoUtente(Utente utente) {
        if (this.utenteRepository.findByUsername(utente.getUsername()) != null) {
            throw new RuntimeException("Username già esistente");
        }

        Utente utenteCorrente = new Utente();
        utenteCorrente.setEmail(utente.getEmail());
        utenteCorrente.setUsername(utente.getUsername());
        utenteCorrente.setPassword(passwordEncoder.encode(utente.getPassword()));
        utenteCorrente.setRuoloUtente(RuoloUtente.USER);

        this.utenteRepository.save(utenteCorrente);
    }

    @Transactional(readOnly = true)
    public List<Utente> trovaTuttiGliUtenti() {
        return utenteRepository.findAll();
    }

    @Transactional
    public void invertiRuoloUtente(String usernameUtenteDaModificare) {
        Utente utente=findByUsername(usernameUtenteDaModificare);

        if(utente.getRuoloUtente()==RuoloUtente.USER) {
            utente.setRuoloUtente(RuoloUtente.ADMIN);
        }
        else {
            utente.setRuoloUtente(RuoloUtente.USER);
        }

        utenteRepository.save(utente);
    }

    @Transactional(readOnly = true)
    public List<Utente> getUtentiFiltrati(String keyword) {
        if(keyword!=null && !keyword.trim().isEmpty()) {
            return utenteRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
        }

        return utenteRepository.findAll();
    }

}