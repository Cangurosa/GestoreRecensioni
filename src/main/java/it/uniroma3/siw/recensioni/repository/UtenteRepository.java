package it.uniroma3.siw.recensioni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.recensioni.model.Utente;

import java.util.List;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

    Utente findByUsername(String username);

    List<Utente> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(String username, String email);
}
