package it.uniroma3.siw.recensioni.controller;

import it.uniroma3.siw.recensioni.model.Contenuto;
import it.uniroma3.siw.recensioni.model.Recensione;
import it.uniroma3.siw.recensioni.model.Utente;
import it.uniroma3.siw.recensioni.repository.ContenutoRepository;
import it.uniroma3.siw.recensioni.repository.UtenteRepository;
import it.uniroma3.siw.recensioni.service.ContenutoService;
import it.uniroma3.siw.recensioni.service.RecensioneService;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RecensioneController {

    private final RecensioneService recensioneService;
    private final ContenutoService contenutoService;
    private final UtenteRepository utenteRepository;
    private final ContenutoRepository contenutoRepository;

    public RecensioneController(RecensioneService recensioneService, ContenutoService contenutoService, UtenteRepository utenteRepository, ContenutoRepository contenutoRepository) {
        this.recensioneService = recensioneService;
        this.contenutoService = contenutoService;
        this.utenteRepository = utenteRepository;
        this.contenutoRepository = contenutoRepository;
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

        return "redirect:/listaContenuti/" + contenutoId;
    }

    @PostMapping("/r/{id}/elimina")
    public String eliminaRecensione(@PathVariable Long id) {
        this.recensioneService.eliminaRecensione(id);
        return "redirect:/listaContenuti";
    }

    @GetMapping("/r/{recensioneId}/modifica")
    public String apriFormModificaRecensione(@PathVariable("recensioneId") Long idRecensione,Model model, Principal principal) {
        String usernameLoggato=principal.getName();
        Recensione recensione=recensioneService.findRecensioneById(idRecensione);

        if(!recensione.getUtente().getUsername().equals(usernameLoggato)) {
            return "redirect:/recensioni";
        }

        model.addAttribute("recensione", recensione);

        return "form-modifica-recensione";
    }

    @PostMapping("/r/{recensioneId}/modifica")
    public String modificaRecensione(@PathVariable("recensioneId") Long idRecensione, @ModelAttribute("recensione") Recensione recensioneForm, Principal principal) {
        String usernameLoggato=principal.getName();
        Recensione recensioneOriginale=recensioneService.findRecensioneById(idRecensione);

        if(!recensioneOriginale.getUtente().getUsername().equals(usernameLoggato)) {
            return "redirect:/recensioni";
        }

        recensioneOriginale.setTesto(recensioneForm.getTesto());
        recensioneOriginale.setStelle(recensioneForm.getStelle());

        recensioneService.salvaRecensione(recensioneOriginale, principal.getName(), recensioneOriginale.getContenuto().getId());

        return "redirect:/recensioni";
    }

    @GetMapping("/recensioni/cerca")
    public String mostraFormCercaRecensioni(Model model) {
        model.addAttribute("modelContenuti", this.contenutoService.getAllContenuti());
        return "cercaRecensioni";
    }

    @PostMapping("/recensioni/cerca")   //questo metodo fa tornare a listaRecensioni, ma con un modelRecensioni diverso, quindi, le mostra filtrate
    public String trovaRecensioni(Model model, @RequestParam(required = false) String usernameUtente, @RequestParam(required = false) Long idContenuto) {
        if (usernameUtente != null && usernameUtente.isBlank()) {
            usernameUtente = null;
        }

        if (usernameUtente == null && idContenuto == null) {
            model.addAttribute("modelRecensioni", recensioneService.getAllRecensioniDatabase());
        }

        else if (usernameUtente != null && idContenuto == null) {
            Utente utenteCorrente = utenteRepository.findByUsername(usernameUtente);

            if (utenteCorrente == null) {
                model.addAttribute("errore", "Utente non trovato");
                model.addAttribute("modelContenuti", contenutoService.getAllContenuti());
                return "cercaRecensioni";
            }
            else
                model.addAttribute("modelRecensioni", recensioneService.getAllRecensioniDellUtente(utenteCorrente.getUsername()));
        }

        else if (usernameUtente == null) {
            model.addAttribute("modelRecensioni", recensioneService.getALLRecensioniDiUnContenutoSpecifico(idContenuto));
        }

        else { //usernameUtente!=null && idContenuto!=null
            List<Recensione> listaRecensioni = new ArrayList<>();
            Utente utenteCorrente = this.utenteRepository.findByUsername(usernameUtente);
            Contenuto contenutoCorrente = this.contenutoRepository.findById(idContenuto).orElse(null);

            if (utenteCorrente == null) {
                model.addAttribute("errore", "Utente non trovato");
                model.addAttribute("modelContenuti", contenutoService.getAllContenuti());
                return "cercaRecensioni";
            }

            //il contenuto non può esser non trovato perchè viene scelto da un menù a tendina
            List<Recensione> listaRecensioniUtente = this.recensioneService.getAllRecensioniDellUtente(utenteCorrente.getUsername());

            for (Recensione r : listaRecensioniUtente) {
                if (r.getContenuto() != null && r.getContenuto().getId().equals(contenutoCorrente.getId()))
                    listaRecensioni.add(r);
            }

            model.addAttribute("modelRecensioni", listaRecensioni);
        }

        return "listaRecensioni";
    }
}