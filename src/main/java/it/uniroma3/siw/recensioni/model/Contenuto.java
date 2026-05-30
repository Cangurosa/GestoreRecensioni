package it.uniroma3.siw.recensioni.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Contenuto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String titolo;

    private String descrizione;
    private Tipo tipo;
    private Genere genere;
    private String img;

    @OneToMany(mappedBy = "contenuto")
    private List<Recensione> recensioni;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Genere getGenere() {
        return genere;
    }

    public void setGenere(Genere genere) {
        this.genere = genere;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public List<Recensione> getRecensioni() {
        return recensioni;
    }

    public void setRecensioni(List<Recensione> recensioni) {
        this.recensioni = recensioni;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Contenuto contenuto = (Contenuto) o;
        return Objects.equals(id, contenuto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}