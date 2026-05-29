package it.uniroma3.siw.recensioni.service;

import it.uniroma3.siw.recensioni.model.Categoria;
import it.uniroma3.siw.recensioni.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {

        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Metodo per ottenere tutte le categorie disponibili
     */
    public List<Categoria> getAllCategorie() {
        List<Categoria> categorie = new ArrayList<>();
        categoriaRepository.findAll().forEach(categorie::add);
        return categorie;
    }

    /**
     * Metodo per trovare la categoria in base al suo Id
     */
    public Categoria getCategoriaById(Long categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria non trovata"));
    }
}