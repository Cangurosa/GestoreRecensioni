package it.uniroma3.siw.recensioni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.recensioni.model.Recensione;

@Repository
public interface RecensioneRepository extends JpaRepository<Recensione, Long> {

}
