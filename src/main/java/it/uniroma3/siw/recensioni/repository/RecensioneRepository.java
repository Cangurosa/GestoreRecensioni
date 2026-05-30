package it.uniroma3.siw.recensioni.repository;

import it.uniroma3.siw.recensioni.model.Recensione;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecensioneRepository extends CrudRepository<Recensione, Long> {

    List<Recensione> findByContenutoTitoloContainingIgnoreCase(String titolo);

    List<Recensione> findByStelle(Integer stelle);

    List<Recensione> findByContenutoId(Long contenutoId);

    List<Recensione> findByUtenteId(Long utenteId);
}