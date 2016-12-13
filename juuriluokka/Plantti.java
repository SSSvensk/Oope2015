package juuriluokka;

/**
  * Konkreettinen aliluokka plänteille. 
  * <p>
  * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2015
  * </p>
  * @author Sami-Santeri Svensk
  */

public class Plantti extends Lotko implements Comparable<Plantti> {
   private boolean soikea;

   public Plantti(int i, Paikka ko, boolean s, int k, StringBuilder p) {
     super(k,p,i, ko);
     soikea(s);
   }

   public void soikea(boolean s) {
      if (s)
        soikea = true;
      else if (!s)
        soikea = false;
   }

   public boolean soikea() {
     return soikea;
   }

   /**
     * Kopiorakentaja, jossa lisääntymään valittu pläntti syväkopioidaan.
     * @param Lisaantymään valittu pläntti.
     */
     

   public Plantti(Plantti p) {
      super(p.koko(), p.perima(), p.indeksi(), p.koordinaatit());
      soikea(p.soikea);
   }

   /**
     * Metodi, joka luo uuden pläntin syväkopioivalla tavalla.
     * @param nykyinen indeksiarvo
     * @param varsinainen lisääntymään valittu lötkö.
     * @param klimppien luomiseen tarvittu toinen lötkö, jota ei tarvita tässä.
     * @return luotu pläntti.
     * @throws UnsupportedOperationException, jos lisääntyvä lötkö ei ole pläntti.
     */

   public Object lisaanny(int indeksi, Lotko lotkotus, Lotko kaveri) throws UnsupportedOperationException {
      Plantti uusiPlantti = null;

      if (lotkotus instanceof Plantti) {
         Plantti lisaantyva = (Plantti)lotkotus;

         //Kutsutaan kopiorakentajaa.
         uusiPlantti = new Plantti(lisaantyva);

         //Asetetaan uudet arvot settereillä
         uusiPlantti.indeksi(indeksi);
         uusiPlantti.soikea(lisaantyva.soikea);
         StringBuilder vanhaPerima = lisaantyva.perima();
         String apuTayttaja = "";
         for (int p = 7; p >= 0; p--) {
             apuTayttaja = apuTayttaja + vanhaPerima.charAt(p);
         }
         StringBuilder uusiPerima = new StringBuilder(apuTayttaja);
         uusiPlantti.perima(uusiPerima);
         uusiPlantti.koko(lisaantyva.koko() / 2);
      }

      //Jos parametrina välitetty lötkö ei ole pläntti, heitetään poikkeus.
      else
         throw new UnsupportedOperationException();

      //Palautetaan uusi pläntti.
      return uusiPlantti;
   }

   public String toString() {
      String tyhja;
      if (soikea)
          tyhja = "    ";
      else
          tyhja = "   ";
      final char EROTIN = '|';
      String pmerkkijono = super.toString() + EROTIN + soikea + tyhja + EROTIN;  
      return pmerkkijono;
   }

  public int compareTo(Plantti p) {
      return super.compareTo(p);
  }

   public boolean equals(Plantti p) {
      boolean samaMuoto = soikea == p.soikea;
      return samaMuoto;
   }
   
}
