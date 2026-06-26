package it.uniroma3.siw.recensioni.controller;

import it.uniroma3.siw.recensioni.model.Recensione;
import it.uniroma3.siw.recensioni.model.Utente;
import it.uniroma3.siw.recensioni.service.ContenutoService;
import it.uniroma3.siw.recensioni.service.RecensioneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
@Controller
public class RecensioneController {

    private final RecensioneService recensioneService;
    private final ContenutoService contenutoService;

    public RecensioneController(RecensioneService recensioneService, ContenutoService contenutoService) {
        this.recensioneService = recensioneService;
        this.contenutoService = contenutoService;
    }

    @GetMapping("/recensioni")
    public String creaPaginaListaRecesioni(Model model) {
        model.addAttribute("modelRecensioni", this.recensioneService.getAllRecensioniDatabase());
        return "listaRecensioni";
    }

    @GetMapping("/recensioni/mie")
    public String creaPaginaListaMieRecensioni(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("modelRecensioni", this.recensioneService.getAllRecensioniDellUtente(username));
        return "listaRecensioni";
    }

    @GetMapping("/recensioni/nuova")
    public String creaPaginaNuovaRecensione(Model model) {
        model.addAttribute("modelContenuti", this.contenutoService.getAllContenuti());
        model.addAttribute("recensione", new Recensione());
        return "nuovaRecensione";
    }

    @PostMapping("/recensioni/nuova")
    public String salvaNuovaRecensione(@ModelAttribute("recensione") Recensione recensione,
                                       @RequestParam("contenutoId") Long contenutoId,
                                       Principal principal) {

        String username = principal.getName();

        this.recensioneService.salvaRecensione(recensione, username, contenutoId);

        return "redirect:/recensioni/mie";
    }

    @PostMapping("/r/{id}/elimina")
    public String eliminaRecensione(@PathVariable Long id) {
        this.recensioneService.eliminaRecensione(id);
        return "redirect:/recensioni/mie";
    }
}