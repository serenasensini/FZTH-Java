# Cos'è

Apache Maven è uno strumento per la gestione dei progetti in Java. Uno dei vantaggi principali è quello di ottenere una gestione e download automatico delle librerie necessarie al progetto con un'ovvia risoluzione delle eventuali dipendenze.
Il principale componente di Maven è il file pom.xml, un file di configurazione che contiene tutte le informazioni che riguardano il progetto, come le dipendenze. POM sta per Project Object Model e ogni singolo progetto è descritto attraverso il file di configurazione pom.xml; aiuta l’esecuzione di un progetto Java, tenendo aggiornate le dipendenze del progetto, senza la necessità di dover scaricare e aggiornare a mano le singole librerie, così come le dipendenze di ciascuna di esse.

Un esempio di file pom.xml è il seguente:

```xml
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

Per esaminare meglio i vari tag, di seguito una breve spiegazione:
<modelVersion> : dichiara la versione del POM;
<groupId> : ID del gruppo del progetto, spiegato meglio di seguito;
<artifactId> : ID dell’artifact, spiegato meglio di seguito;
<version> : versione del progetto;
<packaging> : il tipo di archivio che verrà esportato, che nel caso del progetto d'esempio, è in .jar;
<dependencies> : contiene tutte le dipendenze (librerie) di cui il progetto ha bisogno; ognuna di esse viene specificata all'interna di un singolo tag <dependency>, dove, come sopra, si specifica groupId, artifactId e version della libreria. 

Il miglior modo per trovare le librerie di cui si ha bisogno, così come le versioni necessarie, è sufficiente andare sul [repository](https://mvnrepository.com/) ufficiale e cercare il nome della libreria. Una volta selezionata, si può anche scorrere in basso e trovare le dipendenze di compilazione, se ce ne sono.

## Tips

- Qual è il significato di groupId e artifactId? groupId identifica in modo univoco il tuo progetto in tutti i progetti, quindi è necessario applicare uno schema di denominazione. Un groupId deve seguire le regole del nome del pacchetto Java. Per esempio. it.progetto.java, it.progetto.java.models
Un buon metodo per determinare la granularità di groupId è utilizzare la struttura del progetto. Cioè, se il progetto corrente è un progetto a più moduli, dovrebbe aggiungere un nuovo identificatore al groupId del genitore.
artifactId è il nome del package senza versione. Se lo hai creato, puoi scegliere il nome che preferisci con lettere minuscole e senza strani simboli. Se si tratta di un vaso di terze parti, devi prendere il nome del barattolo mentre viene distribuito, quindi il nome del file .jar che verrà utilizzato.
Per esempio. progetto, mio-progetto
