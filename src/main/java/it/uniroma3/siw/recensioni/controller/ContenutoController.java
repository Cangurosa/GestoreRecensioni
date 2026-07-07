package it.uniroma3.siw.recensioni.controller;

import it.uniroma3.siw.recensioni.classiAusiliarie.Categoria;
import it.uniroma3.siw.recensioni.model.Contenuto;
import it.uniroma3.siw.recensioni.service.ContenutoService;
import it.uniroma3.siw.recensioni.service.RecensioneService;
import it.uniroma3.siw.recensioni.service.UtenteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ContenutoController {
    private final ContenutoService contenutoService;
    private final UtenteService utenteService;
    private final RecensioneService recensioneService;

    public ContenutoController(ContenutoService contenutoService, UtenteService utenteService, RecensioneService recensioneService) {
        this.contenutoService = contenutoService;
        this.utenteService = utenteService;
        this.recensioneService = recensioneService;
    }

    @GetMapping ("/admin/nuovoContenuto")
    public String mostraFormNuovoContenutoAdmin(Model model){
        model.addAttribute("modelContenuto", new Contenuto());
        model.addAttribute("categorie", Categoria.values());
        return "/admin/nuovoContenuto";
    }

    @GetMapping ("/listaContenuti")
    public String mostraListaContenuti(Model model){
        model.addAttribute("modelContenuti", this.contenutoService.getAllContenuti());
        return "listaContenuti";
    }

    @PostMapping("/admin/dettaglioContenuto/{id}/elimina")
    public String eliminaContenuto(@PathVariable Long id) {
        this.contenutoService.eliminaContenuto(id);
        return "redirect:/listaContenuti";
    }

    @PostMapping("/admin/nuovoContenuto")
    public String registraNuovoContenuto(@ModelAttribute Contenuto contenuto,
                                         @RequestParam(value="imageFile", required = false) MultipartFile file,
                                         @RequestParam(value = "imageUrl", required = false) String imageUrl){
        this.contenutoService.aggiungiNuovoContenuto(contenuto, file, imageUrl);
        return "redirect:/listaContenuti";
    }

    @GetMapping("/listaContenuti/{id}")
    public String mostraDettaglioContenuto(Model model, @PathVariable Long id) {
        Contenuto contenuto = this.contenutoService.getContenutoById(id);

        model.addAttribute("modelContenuto", contenuto);
        model.addAttribute("modelRecensioni", this.recensioneService.getALLRecensioniDiUnContenutoSpecifico(id));

        return "contenuto";
    }
}
