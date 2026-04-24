package it.uniroma3.siw.recensioni.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Recensione {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer stelle;
    private String descrizione;

    //mapping
    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "contenuto_id")
    private Contenuto contenuto;

    

    public Long getId() {
        return id;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((stelle == null) ? 0 : stelle.hashCode());
        result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Recensione other = (Recensione) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (stelle == null) {
            if (other.stelle != null)
                return false;
        } else if (!stelle.equals(other.stelle))
            return false;
        if (descrizione == null) {
            if (other.descrizione != null)
                return false;
        } else if (!descrizione.equals(other.descrizione))
            return false;
        return true;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getStelle() {
        return stelle;
    }
    public void setStelle(Integer stelle) {
        this.stelle = stelle;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    
}
