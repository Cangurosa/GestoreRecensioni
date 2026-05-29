package it.uniroma3.siw.recensioni.service;

import it.uniroma3.siw.recensioni.model.Categoria;
import it.uniroma3.siw.recensioni.model.Contenuto;
import it.uniroma3.siw.recensioni.repository.CategoriaRepository;
import it.uniroma3.siw.recensioni.repository.ContenutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContenutoService {
    private final ContenutoRepository contenutoRepository;
    private final CategoriaRepository categoriaRepository;

    public ContenutoService(ContenutoRepository contenutoRepository, CategoriaRepository categoriaRepository) {
        this.contenutoRepository = contenutoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Metodo per creare un contenuto associato a una categoria
     */
    public Contenuto creaContenuto(Long categoriaId, Contenuto contenuto){
        Categoria categoria = categoriaRepository.findById(categoriaId).orElseThrow(() -> new RuntimeException("Categoria non trovata"));

        contenuto.setCategoria(categoria);
        return contenutoRepository.save(contenuto);
    }
/*
    public Contenuto getContenutoById(Long id){

    }

    public List<Contenuto> getAllContenuti(){

    }

    public List<Contenuto> getContenutoByCategoria(Long categoriaId){}
*/
}
