package juuriluokka;

/**
  * Konkreettinen aliluokka klimpeille. 
  * <p>
  * Harjoitusty�, Olio-ohjelmoinnin perusteet, kev�t 2015
  * </p>
  * @author Sami-Santeri Svensk
  */


public class Klimppi extends Lotko implements Comparable<Klimppi> {
   private char vari;

   public Klimppi(int i, Paikka ko, char v, int k, StringBuilder p) {
     super(k,p,i, ko);     
     vari(v);
   }

   public void vari(char v) {
     if (v == 'P' || v == 'S')
         vari = v; 
   }

   public char vari() {
     return vari;
   }

   /**
     * Metodi, joka luo uuden klimpin vertailemalla kahta klimppi�.
     * @param nykyinen indeksiarvo
     * @param varsinainen lis��ntym��n valittu l�tk�.
     * @param luomiseen tarvittu toinen l�tk�.
     * @return luotu klimppi.
     * @throws UnsupportedOperationException, jos lis��ntyv� l�tk� ei ole klimppi.
     */

   public Object lisaanny(int indeksi, Lotko lotkotus, Lotko kaveri) throws UnsupportedOperationException {
      Klimppi uusiKlimppi = null;
      if (lotkotus instanceof Klimppi && kaveri instanceof Klimppi) {
          Klimppi klimpikas = (Klimppi)lotkotus;
          Klimppi kaveriKlimppi = (Klimppi)kaveri;

          int uusiKoko = (klimpikas.koko() + kaveriKlimppi.koko()) / 2;
          String paikkaString = klimpikas.koordinaatit().toString();
          String[] jutut = new String[2];
          jutut = paikkaString.split("[|]");
          for (int j = 0; j < 2; j++) {
              jutut[j] = jutut[j].trim();
          }
          int x = Integer.parseInt(jutut[0]);
          int y = Integer.parseInt(jutut[1]);
          Paikka uusiPaikka = new Paikka(x, y);
               
          char vanhaVari = klimpikas.vari();
          char uusiVari;
          if (vanhaVari == 'S')
              uusiVari = 'P';
          else
              uusiVari = 'S';
          String apuTayttaja = "";
          apuTayttaja = apuTayttaja + klimpikas.perima().charAt(0) + klimpikas.perima().charAt(1) + klimpikas.perima().charAt(2) + klimpikas.perima().charAt(3) + kaveriKlimppi.perima().charAt(4) + kaveriKlimppi.perima().charAt(5) + kaveriKlimppi.perima().charAt(6) + kaveriKlimppi.perima().charAt(7);
          StringBuilder uusiPerima = new StringBuilder(apuTayttaja);
          uusiKlimppi = new Klimppi(indeksi, uusiPaikka, uusiVari, uusiKoko, uusiPerima);
      }
      else
          throw new UnsupportedOperationException();
      return uusiKlimppi;
   }

   public String toString() {
      String varityhja = "       ";
      final char EROTIN = '|';
      String kmerkkijono = super.toString() + EROTIN + vari + varityhja + EROTIN;  
      return kmerkkijono;
   }

   public boolean equals(Klimppi k) {
      if (vari == k.vari())
         return true;
      else
         return false;
   }

   public int compareTo(Klimppi k) {
      return super.compareTo(k);
   }
}