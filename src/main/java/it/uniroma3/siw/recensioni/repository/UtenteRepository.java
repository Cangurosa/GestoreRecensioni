package it.uniroma3.siw.recensioni.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.recensioni.model.Utente;

@Repository
public interface UtenteRepository extends CrudRepository<Utente, Long>{

}
