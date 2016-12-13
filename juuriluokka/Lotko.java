package juuriluokka;

/**
  * Abstrakti yliluokka lötköille. 
  * <p>
  * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2015
  * </p>
  * @author Sami-Santeri Svensk
  */

public abstract class Lotko {
  private int koko;
  private StringBuilder perima;
  private int indeksi;
  private Paikka koordinaatit;

  public Lotko(int k, StringBuilder p, int i, Paikka ko) {
    koko(k);
    perima(p);
    indeksi(i);
    koordinaatit(ko);
  }

  public void koko(int k) {
    if (k >= 0)
       koko = k;
  }

  public int koko() {
    return koko;
  } 

  public void indeksi(int i) {
    if (i >= 0)
       indeksi = i;
  }

  public int indeksi() {
    return indeksi;
  } 

  public void perima(StringBuilder p) {
    if (p != null)
       perima = p;
  }

  public StringBuilder perima() {
    return perima;
  }       

  public void koordinaatit(Paikka ko) {
    if (ko != null)
       koordinaatit = ko;
  }

  public Paikka koordinaatit() {
    return koordinaatit;
  }      

  //Abstrakti-metodi, joka toteutetaan konkreettisissa aliluokissa.

  public abstract Object lisaanny(int indeksi, Lotko lotkotus, Lotko kaveri);

  public String toString() {
     String indtyhja;
     if (indeksi < 10)
        indtyhja = "  ";
     else if (indeksi >= 10 && indeksi < 100)
        indtyhja = " ";
     else
        indtyhja = "";
     String kokotyhja;
     if (koko < 10)
        kokotyhja = "       ";
     else if (koko >= 10 && koko < 100)
        kokotyhja = "      ";
     else if (koko >= 100 && koko < 1000)
        kokotyhja = "     ";
     else if (koko >= 1000 && koko < 10000)
        kokotyhja = "    ";
     else if (koko >= 10000 && koko < 100000)
        kokotyhja = "   ";
     else if (koko >= 100000 && koko < 1000000)
        kokotyhja = "  ";
     else if (koko >= 1000000 && koko < 10000000)
        kokotyhja = " ";
     else
        kokotyhja = "";
     final char EROTIN = '|';
     String merkkijono = indeksi + indtyhja + EROTIN + koordinaatit + EROTIN + getClass().getSimpleName() + " " + EROTIN + koko + kokotyhja + EROTIN + perima;
     return merkkijono;
   }

  public int compareTo(Lotko l) {
     if (koko < l.koko)
         return 1;
     else if (koko == l.koko)
         return 0;
     else
         return -1;
  }
}
