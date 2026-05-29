package it.uniroma3.siw.recensioni.repository;

import it.uniroma3.siw.recensioni.model.Categoria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria, Long>{
}