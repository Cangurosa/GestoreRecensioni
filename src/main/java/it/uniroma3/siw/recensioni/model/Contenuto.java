package it.uniroma3.siw.recensioni.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Contenuto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String descrizione;

    @ManyToOne
    private Categoria categoria;

    @OneToMany(mappedBy = "contenuto")
    private List<Recensione> recensioni;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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