Progetto:
Un corriere espresso ha molti centri di accettazione e smistamento 
distribuiti sul territorio nazionale identificati 
dal codice di avviamento postale del luogo in cui si trovano;
i pacchi sono identificati da un codice numerico generato al momento dell'accettazione. 
Un server TCP tiene traccia del centro di accettazione e dei centri di smistamento in cui 
è transi-tato ogni singolo pacco con associata la data/ora di accettazione o transito
(timestamp):
quando il server viene avviato carica da un file in formato CSV
l'elenco dei pacchi da consegnare con la lista dei centri in cui è 
transitato e i relativi Timestamp e quando viene arrestato genera il file 
per il proprio riavvio. 
Mentre è attivo il server è in grado di accettare i seguenti comandi:

A)Accettazione di un nuovo pacco con gene-razione del codice numerico,
registrazione del centro di accettazione e del relativo Timestamp;

B) transito del pacco da un centro di smistamento;

C)richiesta del centro di accettazione e dei centri di smistamento da cui è transitato un de-terminato pacco con relativo timestomp (nella risposta deve essere fornito lo stato del pac-co:già consegnato o non ancora consegnato); 

d) consegna del pacco al destinatario finale

Dopo aver definito e documentato il protocollo applicativo, 
realizzzare in linguaggio java il server TCP che gestisce il protocollo.
Allo scopo di testare le funzionalita' del server realizzare un client TCP che, fontico com input un elenco
di centri di accettazione e di smistamento, invia al server una richiesta di trasferimento di un pacco.


