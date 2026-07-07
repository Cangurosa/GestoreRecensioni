package it.uniroma3.siw.recensioni.service;

import it.uniroma3.siw.recensioni.model.Contenuto;
import it.uniroma3.siw.recensioni.model.Recensione;
import it.uniroma3.siw.recensioni.repository.ContenutoRepository;
import it.uniroma3.siw.recensioni.repository.RecensioneRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.tools.FileObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ContenutoService {
    private final ContenutoRepository contenutoRepository;
    private final RecensioneRepository recensioneRepository;

    private final String UPLOAD_DIRECTORY=System.getProperty("user.dir")+ "/uploads";

    public ContenutoService(ContenutoRepository contenutoRepository, RecensioneRepository recensioneRepository) {
        this.contenutoRepository = contenutoRepository;
        this.recensioneRepository = recensioneRepository;
    }

    public List<Contenuto> getAllContenuti(){
        return this.contenutoRepository.findAll();
    }

    public Contenuto getContenutoById(Long id) {
        return this.contenutoRepository.findById(id).orElse(null);
    }

    public void aggiungiNuovoContenuto(Contenuto contenuto, MultipartFile file, String imageUrl) {
        try {
            //gestione immagine caricata dal PC
            if(file!=null && !file.isEmpty()) {
                Path uploadPath=Paths.get(UPLOAD_DIRECTORY);

                //crea la cartella uploads se non esiste già
                if(!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String fileName=file.getOriginalFilename();
                Path filePath=Paths.get(UPLOAD_DIRECTORY, fileName);

                //salva il file sul disco
                Files.write(filePath, file.getBytes());

                //imposta il percorso dell'immagine nel db
                contenuto.setImmagineCopertina("/uploads/" +fileName);
            }
            //gestione dell'URL se non è stato caricato nessun file
            else if(imageUrl!=null && !imageUrl.trim().isEmpty()) {
                contenuto.setImmagineCopertina(imageUrl);
            }

            this.contenutoRepository.save(contenuto);
        } catch(IOException e) {
            //in caso di errore lancia un'eccezione
            throw new RuntimeException("Errore durante il salvataggio dell'immagine di copertina", e);
        }
    }

    public void eliminaContenuto(Long id) {
        Contenuto contenuto = this.contenutoRepository.findById(id).orElse(null);

        if (contenuto != null) {

            for (Recensione recensione : contenuto.getRecensioni()) {
                this.recensioneRepository.delete(recensione);
            }

            this.contenutoRepository.delete(contenuto);
        }
    }
}
