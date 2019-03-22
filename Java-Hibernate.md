# Java:Hibernate

## Cos'è: JPA [?](https://it.wikipedia.org/wiki/Java_Persistence_API)

Java Persistence API, talvolta riferite come JPA, è un framework per Java che si occupa della gestione della persistenza dei dati di un DBMS relazionale. La persistenza, in questo contesto, si occupa della mappatura tra lo schema relazionale della base dati e le Entity, classi annotate che rappresentano oggetti della base dati, tale mappatura viene descritta anche con il termine di object-relational mapping.

## Cos'è: ORM [?](https://it.wikipedia.org/wiki/Object-relational_mapping)
Il mapping relazionale a oggetti (ORM=Object-relational mapping) è semplicemente il processo di persistenza di qualsiasi oggetto Java direttamente su una tabella di database. Di solito, il nome dell'oggetto che viene mantenuto diventa il nome della tabella e ogni campo all'interno di quell'oggetto diventa una colonna. Con la tabella impostata, ogni riga corrisponde a un record nell'applicazione. JPA segue il pattern di tipo ORM.

> Disclaimer: il tutorial usa PostgreSQL come DBMS, ma si può applicare a qualunque DBMS. Si presuppone che quindi le ultime versioni di questo siano già installate prima dell'inizio del tutorial.

> Requisiti: vedi contenuto pom.xml più avanti.

## Cos'è: Hibernate [?](https://hibernate.org/)
Si tratta di un framework che fornisce un servizio di Object-relational mapping (ORM) ovvero gestisce la persistenza dei dati sul database attraverso la rappresentazione e il mantenimento su database relazionale di un sistema di oggetti Java.
Al suo interno, Hibernate è uno strumento di mappatura relazionale a oggetti che fornisce un'implementazione di JPA. Hibernate è una delle implementazioni JPA più mature in circolazione, con un'enorme comunità che supporta il progetto.
Implementa tutte le classi javax.persistence che vedremo nell'esercizio oltre a fornire funzionalità oltre a JPA - Hibernate tools, validation e search. 

Diamo una rapida occhiata a ciò che Hibernate offre con l'annotazione @Entity.

Mentre soddisfa il pattern JPA, l'annotazione @Entity aggiunge ulteriori metadati che vanno oltre le specifiche JPA. Ciò consente di perfezionare la persistenza dell'entità. Ad esempio, diamo un'occhiata ad alcune annotazioni offerte da Hibernate che estendono le funzionalità di @Entity: @Table: ci consente di specificare il nome della tabella creata per l'entità; vale anche la pena notare alcune delle funzionalità extra che l'JPA non specifica, che potrebbero rivelarsi utili in applicazioni di grandi dimensioni:
- Dichiarazioni CRUD personalizzabili con le annotazioni @SQLInsert, @SQLUpate e @SQLDelete;
-Entità immutabili con l'annotazione @Immutable;

## Come funziona
Dovendo mappare una certa entità Java con un'entità presente nel database, si può ricorrere ad un framework come Hibernate per la realizzazione del mapping: la gestione della persistenza dell'oggetto viene completamente gestita da quest'ultimo che, tramite determinate annotazioni all'interno delle classi Java, è in grado di "mappare" e lavorare con gli oggetti presenti all'interno di un certo database. 

[Link tutorial](https://www.tutorialspoint.com/hibernate/pdf/hibernate_quick_guide.pdf)

![Schema](https://www.tutorialspoint.com/hibernate/images/hibernate_high_level.jpg)

Nell'esempio, andremo quindi a creare una classe Utente così fatta:

```
import java.sql.Date;

public class Utente {

	public int idUtente;
	public String nome;
	public String cognome;
	public Date data_nascita;
	public String citta;
	public int telefono;
	
	/*E' fondamentale che ogni attributo presente abbia il suo getter e setter con lo stesso
	 * IDENTICO nome dell'attributo presente come variabile globale.
	 * esempio:
	 * getCOGNOME: sbagliato
	 * getCog: sbagliato
	 * getCognome: corretto
	 * */
	public int getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}
	...		
}

```

Nel database andremo a creare una tabella così fatta:

```
CREATE TABLE UTENTE(
	ID_UTENTE INT PRIMARY KEY AUTOINCREMENT,
	NOME VARCHAR(255) NOT NULL,
	COGNOME VARCHAR(255) NOT NULL,
	DATA_NASCITA DATE,
	CITTA VARCHAR(255) NOT NULL,
	TELEFONO INT
)
```

Riassumendo, i primi due file saranno nella cartella src del progetto Eclipse, mentre nel database sarà presente una tabella creata grazie allo script sopra scritto. 

A questo punto, nella classe Utente andiamo ad aggiungere le seguenti annotazioni:
- @Entity: specifica che la classe è un'entità mappabile con Hibernate;
- @Table(name="nomeTabella"): ci permette di mappare la classe Java con il nome corretto dell'entità del database, qualora non fosse lo stesso;
- @Column: specifica le colonne (i campi) presenti nella tabella del database; aggiungendo il parametro name all'interno delle parentesi, posso dichiarare qual è il nome corretto da utilizzare, qualora non sia lo stesso per via di maiuscole, minuscole o regole di sintassi;
- @Id: è un'annotazione specifica per l'attributo (o gli) che sono chiavi primarie nella tabella;
- @GeneratedValue(strategy=GenerationType.IDENTITY): specifica il tipo di chiave utilizzata; in questo caso, abbia una chiave che si autoincrementa autonomamente.

Per fare questo, andremo ad utilizzare la libreria presente a questo (link)[], che dev'essere inclusa nel progetto. Per includere una libreria nel progetto, è sufficiente cliccare con il tasto destro sul nome del progetto in Eclipse, andare su "Properties", cliccare su Java Build Path/Libraries e cliccare su "Add external Jar". Una volta caricata, per aggiornare il file se ancora vi dovessero essere errori di import, è sufficiente premere Ctrl+Maiusc+O.

La classe viene quindi così modificata:

```
import java.sql.Date;

@Entity
@Table(name="UTENTE")
public class Utente {

  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Id
  @Column(name="ID_UTENTE")
	public int idUtente;
  @Column(name="NOME")
	public String nome;
  @Column(name="COGNOME")
	public String cognome;
  @Column(name="DATA_NASCITA")
	public Date data_nascita;
  @Column(name="CITTA")
	public String citta;
  @Column(name="TELEFONO")
	public int telefono;
	...		
}

```

Per far sì che avvenga il mapping tra l'entità nel database e il model nel codice Java, si deve modificare il file persistence.xml, indicando come configurare i parametri di connessione; un esempio viene riportato qui di seguito.

```

<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence
             http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd"
             version="2.1">

    <persistence-unit name="persistence">
        <description>Hibernate Entity Manager Example</description>
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>

        <properties>
            <property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQL9Dialect" />
            <property name="hibernate.connection.driver_class" value="org.postgresql.Driver" />
            <property name="javax.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/postgres" />
            <property name="javax.persistence.jdbc.user" value="postgres" />
            <property name="javax.persistence.jdbc.password" value="postgres" />
            <property name="hibernate.show_sql" value="true" />
        </properties>

    </persistence-unit>

</persistence>

```

Nel caso precedente, come DBMS viene utilizzato PostgreSQL, e di conseguenza la proprietà che riguarda il nome del driver e l'URL al database fanno riferimento a PostgreSQL. Le righe successive riguardano l'impostazione delle credenziali per accedere al database, rispettivamente user e password, mentre l'ultima configura Hibernate di modo da mostrare nella console di esecuzione di Java le query SQL eseguite.

> Si rende comunque evidente che è molto semplice modificare quei parametri per qualunque tipo di DBMS, MySQL, SQLServer o DB2 che sia.

### pom.xml (dipendenze)
```

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.journaldev.hibernate</groupId>
    <artifactId>hibernate-entitymanager</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>hibernate-entitymanager</name>
    <url>http://maven.apache.org</url>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.2.5</version>
        </dependency>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>5.2.6.Final</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>3.8.1</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>javax.xml.bind</groupId>
            <artifactId>jaxb-api</artifactId>
            <version>2.2.11</version>
        </dependency>
        <dependency>
            <groupId>com.sun.xml.bind</groupId>
            <artifactId>jaxb-core</artifactId>
            <version>2.2.11</version>
        </dependency>
        <dependency>
            <groupId>com.sun.xml.bind</groupId>
            <artifactId>jaxb-impl</artifactId>
            <version>2.2.11</version>
        </dependency>
        <dependency>
            <groupId>javax.activation</groupId>
            <artifactId>activation</artifactId>
            <version>1.1.1</version>
        </dependency>
    </dependencies>

    <build>
        <sourceDirectory>src/main/java</sourceDirectory>
        <plugins>
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.5.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

```

## Tips
- [X] Cos'è il modello MVC? Vedi (link) [https://it.wikipedia.org/wiki/Model-view-controller]
- [X] Cos'è il modello ORM? Vedi (link)[https://it.wikipedia.org/wiki/Object-relational_mapping]
- [X] Cosa uso per far dialogare il mio database con l'applicazione Java? Un driver ad hoc per il tipo di database; i principali (ultime versioni) sono MYSQL [driver](https://dev.mysql.com/downloads/connector/j/5.1.html), PostgreSQL (driver)[https://jdbc.postgresql.org/download.html], SQL Server (driver)[https://www.microsoft.com/it-it/download/details.aspx?id=11774], DB2 (driver)[http://www-01.ibm.com/support/docview.wss?uid=swg21363866]
- [X] Come faccio a rendere una parola tutta in maiuscolo tramite shortcut? Ctrl+Maiusc+X (maiuscolo), Ctrl+Maiusc+Y (minuscolo).
