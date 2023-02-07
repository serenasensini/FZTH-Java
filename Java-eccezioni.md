# Java:eccezioni

## Cosa sono

Il termine è usato per descrivere l'occorrenza di eventi che alterano il normale flusso di esecuzione di un programma.
Esempi: __NullPointerException__, __FileNotFoundException__, __IndexOutOfBoundsException__...

## Come gestirle

### Prima alternativa

```java
try {
    // il file che cerco di aprire non esiste; viene quindi generata un'eccezione che può essere catturata e gestita
    file = open("filenonesistente.txt");
} catch (FileNotFoundException f) {
    System.out.println("Il file non esiste.");
}

try {
    //l'oggetto "animale" non è stato inizializzato; l'eccezione viene catturata e gestita
    Animale animale;
    animale.getNome();
} catch (NullPointerException e) {
    System.out.println("L'animale non è stato inizializzato");
}
```

### Seconda alternativa

```java
String[] array = {"bla"};
public int quintoElemento(String[] array)
 throws MiaEccezione
 {
    //l'array contiene un solo elemento; un accesso alla quinta posizione, genererà un'eccezione;
    if(array.length() < 5){
      //la classe MiaEccezione dev'essere creata, come di seguito;
      throw new MiaEccezione();
    }else{
      return array[5];
      }
    }
}

public class MiaEccezione extends Exception{
  public MiaEccezione(String message){
    super(message);
  }
}
```

## Tips

- [X] E' possibile gestire più eccezioni per uno stesso blocco? Sì, usando un sistema try/catch con catch multipli.
- [X] Che differenza c'è tra le eccezioni "checked" e "unchecked"? Le eccezioni "checked" sono ad esempio le eccezioni derivanti da Exception; in poche parole sono quelle che il programmatore è obbligato a gestire. Le eccezioni "unchecked" sono spesso difficile da catturare; un esempio sono __NullPointerException__ o __IndexOutOfBoundException__.
- [X] Perché gestire le eccezioni? Per usare un approccio del tipo "defensive programming": riporto le situazioni di errore in modo ordinato, cercando di far eseguire al meglio le attività affidate al programma.
