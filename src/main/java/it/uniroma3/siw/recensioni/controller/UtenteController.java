package it.uniroma3.siw.recensioni.controller;

import it.uniroma3.siw.recensioni.model.Utente;
import it.uniroma3.siw.recensioni.service.UtenteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class UtenteController {

    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping("/registrazione")
    public String mostraRegistrazione(Model model) {
        model.addAttribute("utente", new Utente());
        return "registrazione";
    }

    @PostMapping("/registrazione")
    public String registraUtente(Utente utente) {
        this.utenteService.registraNuovoUtente(utente);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String mostraLogin() {
        return "login";
    }

    @GetMapping("/home/cambiaRuolo")
    public String apriFormCambiaRuolo(Model model) {
        return "redirect:http://localhost:5173/";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/home/cambiaRuolo/{usernameUtenteDaModificare}")
    public String cambiaRuolo(@PathVariable String usernameUtenteDaModificare, Principal principal) {
        String usernameLoggato=principal.getName();

        if(!usernameLoggato.equals(usernameUtenteDaModificare)) {
            utenteService.invertiRuoloUtente(usernameUtenteDaModificare);
        }

        return "redirect:/home/cambiaRuolo";
    }
}