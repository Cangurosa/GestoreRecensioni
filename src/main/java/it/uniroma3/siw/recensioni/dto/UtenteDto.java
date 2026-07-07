package it.uniroma3.siw.recensioni.dto;

import it.uniroma3.siw.recensioni.classiAusiliarie.RuoloUtente;

public class UtenteDto {
    private String username;
    private String email;
    private RuoloUtente ruoloUtente;

    public UtenteDto(String username, String email, RuoloUtente ruoloUtente) {
        this.username = username;
        this.email = email;
        this.ruoloUtente = ruoloUtente;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RuoloUtente getRuoloUtente() {
        return ruoloUtente;
    }

    public void setRuoloUtente(RuoloUtente ruoloUtente) {
        this.ruoloUtente = ruoloUtente;
    }
}
