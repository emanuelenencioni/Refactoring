### **UNIVERSITA’ DEGLI STUDI DI FIRENZE**
### Corso di Laurea in Ingegneria Informatica
### Elaborato del corso Ingegneria del Software

# **Obiettivo e Descrizione**

Si vuole effettuare il Reactoring di una applicazione monolitica Restful in forma di microservizi.\
Un'applicazione monolitica Restful è dotata di un unico Layer e di un unico Domain Model su cui si espongono tutti i servizi.\
Un'architettura basata su microservizi è realizzata da componenti indipendenti che eseguono ciascun processo applicativo come un servizio.

Ci interessa valutare il carico di IPC (InterProcess Communication) che risulta da diverse soluzioni di Refactoring.
Per farlo, si modella il Domain Model come un grafo in cui i vertici rappresentano le Entità, e gli archi rappresentano l'accoppiamento tra due diverse Entità.\
Successivamente si semplifica il grafo cancellando archi meno pesanti fino ad ottenere delle componenti non connesse e semplici abbastanza.\
Queste componenti non connesse rappresenteranno i microservizi.

Per costruire il grafo passiamo attraverso l'utilizzo delle matrici di Co-Occorrenza, che mappano l'accoppiamento tra le entità su diversi aspetti.

Per questo elaborato, le matrici di Co-Occorrenza sono un dato. In particolare il Software riceve in ingresso i singoli accoppiamenti (Couplings) tra le Entità e con questi costruisce le 4 Matrici di Co-Occorrenza.

La semplificazione del grafo può essere effettuata utilizzando diversi algoritmi. Per questo motivo si è utilizzato il Design Pattern Strategy al fine intercambiare le varie tecniche di semplificazione.

Al fine di valutare il costo delle diverse semplificazioni si utilizza una Funzione di Loss, la quale tiene conto del peso sui tagli e la complessità delle componenti connesse.

# **Possibili aggiunte**

Elenchiamo di seguito possibili implementazioni aggiuntive per questo Software:

- **Sviluppo di un front-end** con interfaccia grafica al fine di facilitare l'inserimento dei dati all'utente.

- Aggiungere la possibilità di **salvare i risultati sul disco**, in modo tale che l'utente possa caricare risultati precedentemente ottenuti e proseguire il suo lavoro dal punto in cui era rimasto.

# **Requisiti**

## Use Case

## Use Case Template

## Mockups (?)

# **Progettazione ed Implementazione**

## Scelte implementative e considerazioni

## Class Diagram

## Classi ed Interfacce

## Design Patterns

## Disposizione delle classi nei package
Di seguito proponiamo il Class Diagram sviluppato:
    "Class diagram"
# **Unit Test**
Siccome ogni classe è fortemente collegata alle altre, è stata presa la decisione di testare la maggior parte del codice per evitare il maggior numero di bugs. Nel progetto è stato usato il framework JUnit 4.13.2.
# **Sequence Diagram**

