import { useState, useEffect } from 'react';
import './GestioneRuoli.css';

function GestioneRuoli() {
    const [utenti, setUtenti] = useState([]);
    const [keyword, setKeyword] = useState('');
    const [searchInput, setSearchInput] = useState('');
    const [utenteAttivo, setUtenteAttivo] = useState(null);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        controllaUtente();
    }, []);

    useEffect(() => {
        if (utenteAttivo) {
            caricaUtenti(keyword);
        }
    }, [utenteAttivo, keyword]);

    const controllaUtente = () => {
        fetch('http://localhost:8080/api/auth/me', { credentials: 'include' })
            .then(response => {
                if (response.ok) return response.json();
                throw new Error("Non loggato");
            })
            .then(data => setUtenteAttivo(data))
            .catch(() => setUtenteAttivo(null));
    };

    const caricaUtenti = (searchKw) => {
        setLoading(true);
        const url = searchKw 
            ? `http://localhost:8080/admin/api/utenti?keyword=${encodeURIComponent(searchKw)}` 
            : 'http://localhost:8080/admin/api/utenti';

        fetch(url, { credentials: 'include' })
            .then(response => {
                if(response.ok) return response.json();
                throw new Error("Errore nel caricamento");
            })
            .then(data => {
                setUtenti(data);
                setLoading(false);
            })
            .catch(error => {
                console.error("Errore nel caricamento:", error);
                setLoading(false);
            });
    };

    const gestisciRicerca = (e) => {
        e.preventDefault();
        setKeyword(searchInput);
    };

    const annullaFiltro = (e) => {
        e.preventDefault();
        setSearchInput('');
        setKeyword('');
    };

    const cambiaRuolo = (username) => {
        fetch(`http://localhost:8080/admin/api/utenti/${username}/cambiaRuolo`, {
            method: 'POST',
            credentials: 'include'
        }).then(response => {
            if (response.ok) {
                caricaUtenti(keyword);
            } else {
                alert("Errore durante la modifica del ruolo");
            }
        }).catch(error => {
            console.error("Errore durante la modifica", error);
            alert("Errore di rete durante la modifica");
        });
    };

    // Se l'utente non è loggato o non è admin, non permettere l'accesso
    if (utenteAttivo && (!utenteAttivo.ruoli || !utenteAttivo.ruoli.includes('ADMIN'))) {
        return (
            <div className="react-page">
                <h2>Accesso Negato</h2>
                <p>Devi essere un amministratore per visualizzare questa pagina.</p>
            </div>
        );
    }

    return (
        <div className="react-page">
            <div className="topbar">
                <h2>Gestione Ruoli Utenti</h2>
                <button className="btn-secondario" onClick={() => window.location.href='http://localhost:8080/home'}>
                    <i className="fa-solid fa-house" style={{marginRight: '10px'}}></i>
                    Torna Alla Home
                </button>
            </div>

            <div className="contenuto-principale">
                <div className="search-card">
                    <form onSubmit={gestisciRicerca} className="form-inline">
                        <input 
                            type="text" 
                            value={searchInput}
                            onChange={(e) => setSearchInput(e.target.value)}
                            placeholder="Cerca per username o email..."
                            className="input-moderno"
                        />
                        <button type="submit" className="btn-primario">Cerca</button>
                        {keyword && (
                            <button type="button" className="btn-secondario" onClick={annullaFiltro} style={{marginLeft: '10px'}}>
                                Annulla
                            </button>
                        )}
                    </form>
                </div>

                {loading ? (
                    <p>Caricamento in corso...</p>
                ) : (
                    <table className="tabella-moderna">
                        <thead>
                            <tr>
                                <th>Nome Utente</th>
                                <th>Email</th>
                                <th>Ruolo Attuale</th>
                                <th style={{width: '180px'}}>Azioni</th>
                            </tr>
                        </thead>
                        <tbody>
                            {utenti.map(utente => (
                                <tr key={utente.username}>
                                    <td>{utente.username}</td>
                                    <td>{utente.email}</td>
                                    <td>{utente.ruoloUtente}</td>
                                    <td>
                                        {utenteAttivo && utente.username !== utenteAttivo.username && (
                                            <button 
                                                className={`bottone-tabella ${utente.ruoloUtente === 'ADMIN' ? 'btn-danger' : ''}`}
                                                onClick={() => cambiaRuolo(utente.username)}
                                            >
                                                {utente.ruoloUtente === 'ADMIN' ? (
                                                    <><i className="fa-solid fa-user-minus" style={{marginRight: '5px'}}></i> Rimuovi Admin</>
                                                ) : (
                                                    <><i className="fa-solid fa-user-shield" style={{marginRight: '5px'}}></i> Rendi Admin</>
                                                )}
                                            </button>
                                        )}
                                    </td>
                                </tr>
                            ))}
                            {utenti.length === 0 && (
                                <tr>
                                    <td colSpan="4" style={{ textAlign: 'center' }}>Nessun utente trovato</td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                )}
            </div>
        </div>
    );
}

export default GestioneRuoli;
