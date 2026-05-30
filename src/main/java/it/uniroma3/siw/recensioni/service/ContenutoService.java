package it.uniroma3.siw.recensioni.service;

import it.uniroma3.siw.recensioni.model.Contenuto;
import it.uniroma3.siw.recensioni.model.Genere;
import it.uniroma3.siw.recensioni.model.Tipo;
import it.uniroma3.siw.recensioni.repository.ContenutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContenutoService {
    private final ContenutoRepository contenutoRepository;

    public ContenutoService(ContenutoRepository contenutoRepository) {
        this.contenutoRepository = contenutoRepository;
    }

    /**
     * Metodo per trovare un contenuto dal suo Id
     */
    public Contenuto getContenutoById(Long idContenuto){
        return contenutoRepository.findById(idContenuto).orElseThrow(() -> new RuntimeException("Contenuto non trovato"));
    }

    /**
     * Metodo per trovare un contenuto in base al titolo
     */
    public Contenuto getContenutoByTitolo(String titolo){
        if(titolo==null || titolo.isBlank()){
            throw new RuntimeException("Il titolo del contenuto è obbligatorio");
        }
        return contenutoRepository.findByContenutoTitoloContainingIgnoreCase(titolo);
    }

    /*COMMENTO DA LEGGERE*/
    /*

    Il metodo per creare, modificare o eliminare un nuovo contenuto non l'ho messo perchè
    credo sia inutile creare altri contenuti da inserire dal database, credo sia più comodo
    importarli con un import.sql. Comunque posso sempre aggiungerli se servono

     */

    /**
     * Metodo per trovare i contenuti in base al genere
     */
    public List<Contenuto> getContenutiByGenere(Genere genere){
        if(genere==null){
            throw new RuntimeException("Il genere non deve essere vuoto");
        }
        return contenutoRepository.findByContenutoGenereContainingIgnoreCase(genere);
    }

    /**
     * Metodo per trovare i contenuti in base al tipo
     */
    public List<Contenuto> getContenutiByTipo(Tipo tipo){
        if(tipo==null){
            throw new RuntimeException("Il genere non deve essere vuoto");
        }
        return contenutoRepository.findByContenutoTipoContainingIgnoreCase(tipo);
    }




}
