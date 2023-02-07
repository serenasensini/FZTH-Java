# Java:JDBC

## JDBC: cos'è

"In informatica JDBC (Java DataBase Connectivity), è un connettore (driver) per database che consente l'accesso e la gestione della persistenza dei dati sulle basi di dati da qualsiasi programma scritto con il linguaggio di programmazione Java, indipendentemente dal tipo di DBMS utilizzato." (cfr. [Wikipedia.org](https://it.wikipedia.org/wiki/Java_DataBase_Connectivity))

## Come settarlo in Eclipse (molto simile per Intellij)

Click destro sul progetto -> Properties -> Buildpath -> Libraries -> Add External JAR e selezionate “mysql-connector-java-5.1.14-bin.jar” JAR file dalla cartella in cui è stato scaricato.

![Immagine](http://theopentutorials.com/totwp331/wp-content/uploads/jdbc-examples-introduction_2992/add-mysql-connector-eclipse.jpg)

Seguire [questa](https://ibytecode.com/blog/jdbc-mysql-connection-tutorial/) guida.

## Come funziona

Esempio:

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class TestDatabase {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public void readDataBase() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // preparo la connessione al database
            connect = DriverManager.getConnection("jdbc:mysql://localhost/database?" + "user=test&password=test");
            // Statement mi permette di creare query ed eseguirle sul database
            statement = connect.createStatement();
            // ResultSet salva il risultato dell'esecuzione della query
            resultSet = statement.executeQuery("select * from database.utente where nome='Marco'");
            //salvo nel ResultSet
            stampaResultSet(resultSet);

            // PreparedStatements può essere usato per inserire i valori nella query - è un metodo molto sicuro per evitare SQLInjection
            preparedStatement = connect.prepareStatement("insert into database.utente values (?, ?, ?)");
            // Parameters iniziano da 1
            preparedStatement.setString(1, "Marco");
            preparedStatement.setString(2, "Rossi");
            preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
            preparedStatement.executeUpdate();
  
            //Verifico che sia andato a buon fine l'inserimento
            preparedStatement = connect.prepareStatement("SELECT nome, cognome, data_nascita from database.utente");
            resultSet = preparedStatement.executeQuery();
            //salvo nel ResultSet
            stampaResultSet(resultSet);

            // Rimuovo l'utente
            preparedStatement = connect.prepareStatement("delete from database.utente where nome= ? ; ");
            preparedStatement.setString(1, "Marco");
            preparedStatement.executeUpdate();

            resultSet = statement.executeQuery("select * from database.utente");
            
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }

    }

  private void stampaResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String nome = resultSet.getString("nome");
            String cognome = resultSet.getString("cognome");
            Date date = resultSet.getDate("data_nascita");
            System.out.println("nome: " + nome);
            System.out.println("cognome: " + cognome);
            System.out.println("data_nascita: " + data_nascita);
        }
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

public class Main {
    public static void main(String[] args) throws Exception {
        TestDatabase dao = new TestDatabase();
        dao.readDataBase();
    }

}

```

Link driver Java:

- [MySQL](http://www.java2s.com/Code/Jar/c/Downloadcommysqljdbc515jar.htm)
- [SQLServer](https://docs.microsoft.com/it-it/sql/connect/jdbc/microsoft-jdbc-driver-for-sql-server?view=sql-server-2017)
- [Oracle](https://www.oracle.com/technetwork/database/application-development/jdbc/downloads/index.html)
- [PostgreSQL](https://jdbc.postgresql.org/)
