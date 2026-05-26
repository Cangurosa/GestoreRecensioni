package it.uniroma3.siw.recensioni.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Contenuto {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String titolo;
    private String tipo;
    private Integer anno;
    private String descrizione;


    @OneToMany(mappedBy = "contenuto")
    private List<Recensione> recensioni;

    @ManyToMany
    @JoinTable(
    name = "contenuto_categoria",
    joinColumns = @JoinColumn(name = "contenuto_id"),
    inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private List<Categoria> categorie;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getAnno() {
        return anno;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
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

    public List<Categoria> getCategorie() {
        return categorie;
    }

    public void setCategorie(List<Categoria> categorie) {
        this.categorie = categorie;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Contenuto contenuto = (Contenuto) o;
        return Objects.equals(getId(), contenuto.getId()) && Objects.equals(getTitolo(), contenuto.getTitolo()) && Objects.equals(getTipo(), contenuto.getTipo()) && Objects.equals(getAnno(), contenuto.getAnno()) && Objects.equals(getDescrizione(), contenuto.getDescrizione()) && Objects.equals(getRecensioni(), contenuto.getRecensioni()) && Objects.equals(getCategorie(), contenuto.getCategorie());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitolo(), getTipo(), getAnno(), getDescrizione(), getRecensioni(), getCategorie());
    }
}
