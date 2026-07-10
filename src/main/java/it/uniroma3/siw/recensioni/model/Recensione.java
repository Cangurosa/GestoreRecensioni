package it.uniroma3.siw.recensioni.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Recensione {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer stelle;

    @Column(columnDefinition = "TEXT")
    private String testo;
    private LocalDateTime dataEOra;
    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;
    @ManyToOne
    private Contenuto contenuto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataEOra() {
        return dataEOra;
    }

    public void setDataEOra(LocalDateTime dataEOra) {
        this.dataEOra = dataEOra;
    }

    public Integer getStelle() {
        return stelle;
    }

    public void setStelle(Integer stelle) {
        this.stelle = stelle;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Contenuto getContenuto() {
        return contenuto;
    }

    public void setContenuto(Contenuto contenuto) {
        this.contenuto = contenuto;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Recensione that = (Recensione) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getStelle(), that.getStelle()) && Objects.equals(getTesto(), that.getTesto()) && Objects.equals(getUtente(), that.getUtente()) && Objects.equals(getContenuto(), that.getContenuto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStelle(), getTesto(), getUtente(), getContenuto());
    }
}
