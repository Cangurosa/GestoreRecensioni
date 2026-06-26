package it.uniroma3.siw.recensioni.model;

import java.util.List;
import java.util.Objects;

import it.uniroma3.siw.recensioni.classiAusiliarie.RuoloUtente;
import jakarta.persistence.*;

@Entity
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String password;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private RuoloUtente ruoloUtente;

    @OneToMany(mappedBy = "utente")
    private List<ListaPreferiti> listaPreferiti;

    @OneToMany(mappedBy = "utente")
    private List<Recensione> recensioni;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ListaPreferiti> getListaPreferiti() {
        return listaPreferiti;
    }

    public void setListaPreferiti(List<ListaPreferiti> listaPreferiti) {
        this.listaPreferiti = listaPreferiti;
    }

    public List<Recensione> getRecensioni() {
        return recensioni;
    }

    public void setRecensioni(List<Recensione> recensioni) {
        this.recensioni = recensioni;
    }

    public RuoloUtente getRuoloUtente() {
        return ruoloUtente;
    }

    public void setRuoloUtente(RuoloUtente ruoloUtente) {
        this.ruoloUtente = ruoloUtente;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Utente utente = (Utente) o;
        return Objects.equals(getId(), utente.getId()) && Objects.equals(getUsername(), utente.getUsername()) && Objects.equals(getPassword(), utente.getPassword()) && Objects.equals(getEmail(), utente.getEmail()) && Objects.equals(getListaPreferiti(), utente.getListaPreferiti()) && Objects.equals(getRecensioni(), utente.getRecensioni());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword(), getEmail(), getListaPreferiti(), getRecensioni());
    }
}
