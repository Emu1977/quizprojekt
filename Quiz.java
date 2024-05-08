import java.io.*;
import java.util.*;

public class Quiz {

public static String[][] csvArray(String csvDatei) {
  int zeilen = 0;
  int elemente = 0;
  String zeile;
  BufferedReader csvZeilen = null;
  String[][] csvArray = null;
  String fs = System.getProperty("file.separator"); // das Slash-problem elemenieren...

  try {
      csvZeilen = new BufferedReader(new java.io.FileReader(new File("."+fs+"quizprojekt"+fs+"data"+fs+csvDatei+".csv")));
      while ((zeile = csvZeilen.readLine()) != null) {
          if (zeilen == 2) {
              elemente = (zeile.split("; ")).length; 
          }
          zeilen++;
      }
      csvZeilen.close();

      csvArray = new String[zeilen - 2][elemente];
      
      int zeilennr = 0;

      csvZeilen = new BufferedReader(new java.io.FileReader("."+fs+"quizprojekt"+fs+"data"+fs+csvDatei+".csv")); // Auslesen von csvDatei
      while ((zeile = csvZeilen.readLine()) != null) {
          if (zeilennr>1) {
              String[] splitarr = zeile.split("; "); // Splitten der Zeilen
              csvArray[zeilennr-2][0] = splitarr[0]; // erstes Elementobjekt über index 0
              //System.out.print(splitarr[0]);
              csvArray[zeilennr-2][1] = splitarr[1]; // zweites Element über index 1 
              if (splitarr.length > 2) {
                  csvArray[zeilennr-2][2] = splitarr[2]; // drittes Element über index 2 
              }
              if (splitarr.length > 3) {
                  csvArray[zeilennr-2][3] = splitarr[3]; // viertes Element über index 3 
              }
          }
          zeilennr++;
      }
  } catch (IOException ex) {
      ex.printStackTrace();
  } finally {
      try {
          if (csvZeilen != null) {
              csvZeilen.close();
          }
      } catch (IOException ex) {
          ex.printStackTrace();
      }
  }
  
  return csvArray;  
}

    // csvAusgabe gibt den Inhalt der verschieden csv-Dateien im Terminal aus (datei ohne ".csv")
    public static void csvAusgabe(String datei) {
      System.out.println(datei);
      String[][] csvArray_=csvArray(datei);
      for (int i=0; i<csvArray_.length; i++){
          String ausgabe="";
          //System.out.println(i);
          for (int j=0; j<(csvArray_[i].length); j++) {
              //System.out.println(j);
              ausgabe = ausgabe + csvArray_[i][j];
              if ((j<(csvArray_[i].length)-1)) ausgabe = ausgabe +" | ";

          }
          System.out.println(ausgabe);
      }
  }

public static String[][] antwortenZurFrage(int idfrage) {
  final String[][] csvAntworten = csvArray("antworten");
  int anzahlAntworten = 0;
  
  // Zähle die Anzahl der Antworten zur gegebenen Frage
  for (String[] antwort : csvAntworten) {
      if (Integer.parseInt(antwort[0]) == idfrage) {
          anzahlAntworten++;
      }
  }
  
  // Erstelle das Array für die Antworten
  final String[][] antworten = new String[anzahlAntworten][5];
  
  // Kopiere die Antworten zur gegebenen Frage in das Array
  int index = 0;
  Random rnd = new Random();
  for (String[] antwort : csvAntworten) {
      if (Integer.parseInt(antwort[0]) == idfrage) {
          for (int j = 0; j < 4; j++) {
              antworten[index][j] = antwort[j];
          }
          antworten[index][4] = String.valueOf(rnd.nextInt(100));
          index++;
      }
  }
  
  // Sortiere das Array basierend auf der letzten Spalte
  sort2DArrayByColumn(antworten, 4);
  
  return antworten;
}
    public static String[][] fragenRandom() {
        final String[][] csvFragen = csvArray("fragen");
        final String[][] fragen = new String[csvFragen.length][5];
        int index = 0;
        Random rnd = new Random();

        for (String[] antwort : csvFragen) {
                for (int j = 0; j < 3; j++) {
                    fragen[index][j] = antwort[j];
                }
                fragen[index][4] = String.valueOf(rnd.nextInt(100));
                index++;
        }
        sort2DArrayByColumn(fragen, 4);
        return fragen;
    }

  public static void sort2DArrayByColumn(String[][] array, final int column) {
    Arrays.sort(array, new Comparator<String[]>() {
        @Override
        public int compare(String[] row1, String[] row2) {
            // Vergleiche die Werte in der angegebenen Spalte
            return row1[column].compareTo(row2[column]);
        }
    });
  }
  public static void main(String args[]) {
    String[][] fragen = fragenRandom();

    for (int i = 0; i < fragen.length; i++) {
      for (int j = 0; j < fragen[i].length; j++) System.out.print(fragen[i][j] + " ");
      System.out.println();
    }
    // sort according to 5 column
    
    //System.out.println("after sorting");
    /*for (int i = 0; i < antworten.length; i++) {
      for (int j = 0; j < antworten[i].length; j++) System.out.print(antworten[i][j] + " ");
      System.out.println();
    }*/
  }
}

