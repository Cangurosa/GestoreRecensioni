package it.uniroma3.siw.recensioni.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String tipo;

    @OneToMany(mappedBy = "categoria")
    private List<Contenuto> contenuti;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Contenuto> getContenuti() {
        return contenuti;
    }

    public void setContenuti(List<Contenuto> contenuti) {
        this.contenuti = contenuti;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(getId(), categoria.getId()) && Objects.equals(getTipo(), categoria.getTipo()) && Objects.equals(getContenuti(), categoria.getContenuti());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTipo(), getContenuti());
    }
}