package it.uniroma3.siw.recensioni.repository;

import it.uniroma3.siw.recensioni.model.Genere;
import it.uniroma3.siw.recensioni.model.Recensione;
import it.uniroma3.siw.recensioni.model.Tipo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.recensioni.model.Contenuto;

import java.util.List;

@Repository
public interface ContenutoRepository extends CrudRepository<Contenuto, Long>{
    Contenuto findByContenutoTitoloContainingIgnoreCase(String titolo);
    List<Contenuto> findByContenutoGenereContainingIgnoreCase(Genere genere);
    List<Contenuto> findByContenutoTipoContainingIgnoreCase(Tipo tipo);
}
