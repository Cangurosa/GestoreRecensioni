package it.uniroma3.siw.recensioni.controller;

import it.uniroma3.siw.recensioni.dto.UtenteDto;
import it.uniroma3.siw.recensioni.model.Utente;
import it.uniroma3.siw.recensioni.service.UtenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/api/utenti")
public class UtenteRestController {

    private final UtenteService utenteService;

    public UtenteRestController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UtenteDto> getUtenti(@RequestParam(value = "keyword", required = false) String keyword) {
        List<Utente> utenti = utenteService.getUtentiFiltrati(keyword);
        return utenti.stream()
                .map(u -> new UtenteDto(u.getUsername(), u.getEmail(), u.getRuoloUtente()))
                .collect(Collectors.toList());
    }

    @PostMapping("/{usernameUtenteDaModificare}/cambiaRuolo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> cambiaRuolo(@PathVariable String usernameUtenteDaModificare, Principal principal) {
        String usernameLoggato = principal.getName();
        if (!usernameLoggato.equals(usernameUtenteDaModificare)) {
            utenteService.invertiRuoloUtente(usernameUtenteDaModificare);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
