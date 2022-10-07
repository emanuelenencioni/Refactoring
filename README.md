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
![](doc/diagram_imgs/useCaseDiagram.png)
## Use Case Template
![](doc/diagram_imgs/UseCaseTemplate-1.jpg)
![](doc/diagram_imgs/UseCaseTemplate-2.jpg)
![](doc/diagram_imgs/UseCaseTemplate-3.jpg)
# **Progettazione ed Implementazione**

## Scelte implementative e considerazioni

## Class Diagram

## Classi ed Interfacce

## Design Patterns

All'interno del progetto abbiamo fatto uso dei seguenti Design Pattern:

- Strategy

- Factory

### STRATEGY

Lo **Strategy** è un Design Pattern comportamentale che permette di cambiare il comportamento di una classe a runtime, cambiando l'algortmo utilizzato per svolgere ua determinata mansione.
Abbiamo scelto di utilizzare il Design Pattern Strategy al fine di offrire all'utente la possibilità di effettuare numerose scelte, in particolare sugli algoritmi di semplificazione del grafo, di valutazione della loss e di costruzione delle matrici di cooccorrenza.
In questo modo viene anche offerta all'utente la possibilità di effettuare delle valutazioni sui risultati provenienti dall'utilizzo di particolari combinazioni di algoritmi.


### FACTORY

Il **Factory** è un Design Pattern creazionale che permette di istanziare oggetti più facilmente, senza dover specificare i dettagli.
Abbiamo deciso di utilizzare il Design Pattern Factory al fine di rendere più elegante, ordinata ed agevole la creazione automatica delle differenti strategie di semplificazione del grafo. In particolare viene utilizzato all'interno del metodo "findBestSolution()" della classe GraphManager, in quanto questo metodo necessita di utilizzare tutte le diverse streategie di semplificazione del grafo, al fine di trovare la strategia che minimizza il valore della loss.
    
# **Unit Test**
Siccome ogni classe è fortemente collegata alle altre, è stata presa la decisione di testare la maggior parte del codice per evitare il maggior numero di bugs. Nel progetto è stato usato il framework JUnit 4.13.2.
### **AppModelTest**
In questa classe di test sono state testate tutte le classi appartenenti al package **ApplicationModel**. In particolare sono stati testati i metodi di creazione delle matrici, e selezione della strategia a livello di EndPoint e di UseCase. A livello di BusinessLogic non è stato eseguito alcun test poichè risulterebbero ripetitivi.
![](doc/test_img/appmodelTest_1.png)
![](doc/test_img/appmodelTest_2.png)
![](doc/test_img/appmodelTest_3.png)
![](doc/test_img/appmodelTest_4.png)
![](doc/test_img/appmodelTest_5.png)

### **WeightedGraphTest**
Classe di test per il grafo pesato. Essendo una struttura abbsastanza complessa, è stato valutato utile eseguire dei test su queste classi.
![](doc/test_img/weightedGraphTest_1.png)
![](doc/test_img/weightedGraphTest_2.png)

### **ManagerTest**
Classe di test per tutta la parte di creazione del grafo e semplificazione del grafo. Test molto importanti in quanto verificano la correttezza matematica delle varie strategie utilizzate.
![](doc/test_img/managerTest_1.png)
![](doc/test_img/managerTest_2.png)
![](doc/test_img/managerTest_3.png)
![](doc/test_img/managerTest_4.png)
![](doc/test_img/managerTest_5.png)
![](doc/test_img/managerTest_6.png)
