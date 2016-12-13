// Otetaan k�ytt��n lista-pakkauksen luokat.
import fi.uta.csjola.oope.lista.*;
import juuriluokka.*;

/** Viikkoharjoitus 6, teht�v�t 2 - 5.
  *
  * Yhteen suuntaan linkitetyst� Oope-listasta peritty luokka.
  * <p>
  * Olio-ohjelmoinnin perusteet, kev�t 2015.
  * <p>
  * @author Jorma Laurikkala (jorma.laurikkala@uta.fi),
  * @author Sami-Santeri Svensk (uusia metodeja)
  * Informaatiotieteiden yksikk�, Tampereen yliopisto.
  *
  */

public class OmaLista extends LinkitettyLista {

   /*
    * Uudet listaoperaatiot.
    *
    */

   /** Poistaa listalta null-arvoiset alkiot. Muokattu 6.3., jolloin metodin tyypiksi
     * vaihdettu int ja lis�tty poistettujen laskurin kasvatus.)
     *
     * @return poistettujen alkioiden lukum��r�.
     */
   public int karsi() {
      // Poistettujen null-arvoisten alkioiden lukum��r�.
      int poistettuja = 0;

      // Tutkitaan alkioita, kunnes saavutaan listan loppuun.
      int i = 0;
      while (i < koko()) {
         // Poistetaan solmu, jolla on null-arvoinen alkio. Alkiolaskuria ei kasvateta,
         // koska poistetun solmun kohdalle tulee poistettua solmua seuraava solmu.
         if (alkio(i) == null) {
            poista(i);
            poistettuja++;
         }
         // Siirryt��n seuraavaan paikkaan.
         else
            i++;
      }

      // Palautetaan poistettujen alkoiden lukum��r�.
      return poistettuja;
   }

   /** Poistaa listan l p��n ja lis�� sen t�m�n listan loppuun.
     * 
     * @return true, jos voitiin lis�t� ja false, jos parametrissa oli virhe.
     */
   public boolean lisaaLoppuun(LinkitettyLista l) {
      // Null-arvoinen viite tai lista on tyhj�.
      if (l == null || l.koko() == 0)
         return false;

      // Poistetaan listan l p�� ja lis�t��n se t�m�n listan loppuun.
      lisaaLoppuun(l.poistaAlusta());

      // Viestit��n onnistumisesta.
      return true;
   }

   /** Poistaa listalta annettua oliota equals-mieless� vastaavat alkiot.
     *
     * @param alkio olio, jota vastaavat alkiot poistetaan listalta.
     * @return viitteet poistettuihin olioihin. Alkioiden j�rjestys on sama
     * kuin t�ll� listalla. Paluuarvo on null, jos vastaavia alkioita ei l�ytynyt,
     * parametri on null-arvoinen tai lista on tyhj�.
     */
   public OmaLista poista(Object alkio) {
      // Voidaan hakea.
      if (alkio != null && koko() > 0) {
         // Luodaan oma lista poistetuille. Poistettujen listalta asetetaan
         // viite kuhunkin t�lt� listalta poistettuun alkioon.
         OmaLista poistetut = new OmaLista();

         // K�yd��n lista l�pi alusta loppuun.
         int i = 0;
         while (i < koko()) {
            // Tutkitaan onko tietoalkio sama kuin parametri.
            boolean loytyi = alkio(i).equals(alkio);

            // Poistetaan t�lt� listalta ja lis�t��n palautettavalle listalle.
            // Laskuria ei kasvateta, koska nykyiselle paikalle siirtyy
            // poistettavaa solmua seuraava solmu.
            if (loytyi)
               poistetut.lisaaLoppuun(poista(i));
            // Siirryt��n seuraavaan paikkaan.
            else
               i++;
         }
         // Jos poistettiin, niin palautetaan viite poistettujen listaan.
         // Muuten palautetaan null-arvo.
         return poistetut.koko() > 0 ? poistetut : null;
      }

      // Ei voitu poistaa - palautetaan null-arvo.
      else
         return null;
   }

   /** Hakee listan suurimman alkion. Operaatio ei k�yt� listan alkio-metodia,
     * jotta listaa ei k�yt�isi kutakin alkiota vertailtaessa turhaan l�pi
     * alusta l�htien vertailtavaan alkion saakka. Seuraava-viitteen avulla
     * listaa kulkemalla p��st��n seuraavaan solmuun ja sen alkioon huomattavasti
     * v�hemm�ll� laskennalla.
     *
     * @return viite haettuun alkioon. Paluuarvo on null, jos lista on tyhj�.
     */
   // Lis�m��re, jolla k��nt�j�lle vakuutetaan, ett� metodin koodi on turvallista.
   // Ilman m��rett� k��nt�j� varoittaa, ett� Comparable-rajapintaa k�ytet��n
   // ei-geneerisell� tavalla.
   @SuppressWarnings({"unchecked"})
   public Object haeSuurin1() {
      // Ei voida hakea. Palautetaan heti null-arvo.
      if (koko() == 0)
         return null;

      // Nykyiseen solmuun liittyv� viite, joka liitet��n aluksi listan p��solmuun.
      Solmu nykyinenSolmu = paa();

      // T�m�n hetkinen maksimialkio.
      Object suurin = alkio(0);

      // Siirryt��n seuraavaan solmuun.
      nykyinenSolmu = nykyinenSolmu.seuraava();

      // Tutkitaan alkioita, kunnes saavutetaan listan loppu.
      while (nykyinenSolmu != null) {
         // Asetaan compareTo-metodia kutsuvaan metodiin rajapinnan tyyppinen viite,
         // jotta voidaan kutsua rajapinnan metodia, joka ei ole saatavilla Object-
         // tyyppisen viitteen kautta.
         Comparable vertailtava = (Comparable)nykyinenSolmu.alkio();

         // L�ydettiin uusi maksimi.
         if (vertailtava.compareTo(suurin) > 0)
            // Asetaan viite uuden maksimiarvon sis�lt�v��n alkioon.
            suurin = vertailtava;

         // Siirryt��n seuraavaan solmuun.
         nykyinenSolmu = nykyinenSolmu.seuraava();
      }

      // Palautetaan suurimpaan alkioon liittyv� viite.
      return suurin;
   }

   /** Hakee listan suurimman alkion. Alkio-metodin k�ytt� selkeytt��, mutta samalla
     * hidastaa metodin toimintaa.
     *
     * @return viite haettuun alkioon. Paluuarvo on null, jos lista on tyhj�.
     */
   // Lis�m��re, jolla k��nt�j�lle vakuutetaan, ett� metodin koodi on turvallista.
   // Ilman m��rett� k��nt�j� varoittaa, ett� Comparable-rajapintaa k�ytet��n
   // ei-geneerisell� tavalla.
   @SuppressWarnings({"unchecked"})
   public Object haeSuurin2() {
      // Ei voida hakea. Palautetaan heti null-arvo.
      if (koko() == 0)
         return null;

      // T�m�n hetkinen maksimialkio.
      Object suurin = alkio(0);

      // Tutkitaan alkioita, kunnes saavutetaan listan loppu.
      int i = 0;
      while (i < koko()) {
         // Asetaan compareTo-metodia kutsuvaan metodiin rajapinnan tyyppinen viite,
         // jotta voidaan kutsua rajapinnan metodia, joka ei ole saatavilla Object-
         // tyyppisen viitteen kautta.
         Comparable vertailtava = (Comparable)alkio(i);

         // L�ydettiin uusi maksimi.
         if (vertailtava.compareTo(suurin) > 0)
            // Asetaan viite uuden maksimiarvon sis�lt�v��n alkioon.
            suurin = vertailtava;

         // Siirryt��n seuraavaan paikkaan.
         i++;
      }

      // Palautetaan suurimpaan alkioon liittyv� viite.
      return suurin;
   }

  /**
    * Metodi, joka etsii kaikki saman paikan l�tk�t luomista varten.
    * @param paikan koordinaatit String-muodossa.
    * @param laskurina toimiva luku l�tk�jen etsimiseen listalta.
    */

  public void haePaikanLotkot(String lotkonenPaikka, int i, OmaLista paikkalista) {
       int listanKoko = koko();

       //Vertaillaan ja lis�t��n paikkalistalle saman paikan l�tk�t
       for (int b = i + 1; b < listanKoko; b++) {
           Lotko vertailtava = (Lotko)alkio(b);
           String vertailtavaPaikka = vertailtava.koordinaatit().toString();
           if (vertailtavaPaikka.equals(lotkonenPaikka)) 
               paikkalista.lisaaLoppuun(vertailtava);
       }
  }

  /**
    * Metodi, jossa tulostetaan n�yt�lle OmaLista-luokan listan kaikki l�tk�t.
    * @param esitett�v�ss� muodossa oleva merkkijono, joka sis�lt�� siemenluvun ja max-koordinaatit.
    */
  public void listaa(String tiedotMerkkijonona) {      
       //Tulostetaan simulaattorin tiedot.
       System.out.println(tiedotMerkkijonona);

       //Tulostetaan kaikki listalla olevat l�tk�t.
       for (int i = 0; i < koko(); i++) {
           System.out.println(alkio(i));
       }
  }

  /**
    * Metodi, jossa listataan parametrina annetun l�tk�n indeksiarvon kanssa samanlaiset l�tk�t.
    * Jos indeksiarvon l�tk� on tyypilt��n Klimppi, tulostetaan saman v�riset Klimpit
    * Jos indeksiarvon l�tk� on tyypilt��n Pl�ntti, tulostetaan saman muotoiset Pl�ntit.
    * @param parametrit, joista erotellaan vertailtavan l�tk�n indeksi.
    */
  public void listaaSamat(String[] parametrit) {

       //M��ritell��n annetuista parametreist� vertailtavan l�tk�n indeksi.
       int ind = Integer.parseInt(parametrit[1]);

       //etsit��n listasta indeksiarvon omaava l�tk�.
       Lotko kohde = (Lotko)alkio(ind);

       //Tarkastetaan onko l�tk� klimppi vai pl�ntti.
       String klnimi = kohde.getClass().getSimpleName();

       //Verrataan vertailuun otettua l�tk�� listan muihin l�tk�ihin.
       for (int i = 0; i < koko(); i++) {
           //Tarkastetaan toisen vertailtavan luokka.
           Lotko vertailtava = (Lotko)alkio(i);
           String luokkaNimena = vertailtava.getClass().getSimpleName();

           //Jos molemmat ovat klimppej� niin...
           if (klnimi.equals(luokkaNimena) && klnimi.equals("Klimppi")) {
              Klimppi k = (Klimppi)kohde;
              Klimppi v = (Klimppi)vertailtava;

              //Jos klimpit ovat samanlaisia, tulostetaan vertailtava.
              if (k.equals(v))
                 System.out.println(vertailtava);
           }    

           //Jos molemmat ovat pl�nttej�, niin...
           if (klnimi.equals(luokkaNimena) && klnimi.equals("Plantti")) {
              Plantti k = (Plantti)kohde;
              Plantti v = (Plantti)vertailtava;

              //Jos pl�ntit ovat samanlaisia, tulostetaan vertailtava.
              if (k.equals(v))
                 System.out.println(vertailtava);
           }                      
       }
  }

  /**
    * Metodi, joka listaa samassa paikassa sijaitsevat l�tk�t.
    * @param parametrit-taulukko, josta erotellaan halutun vertailupaikan koordinaatit.
    */
  public void listaaPaikanAlkiot(String[] parametrit) {
       //Sijoitetaan paremetrien arvot x- ja y-koordinaattien arvoksi, ja luodaan vertailtava paikka.
       int x = Integer.parseInt(parametrit[1]);
       int y = Integer.parseInt(parametrit[2]);
       Paikka vertailtavaPaikka = new Paikka(x, y);
       String paikka = vertailtavaPaikka.toString();

       //K�yd��n l�pi listan alkioita, ja tulostetaan saman paikan omaavat l�tk�t.
       for (int v = 0; v < koko(); v++) {
           Lotko lo = (Lotko)alkio(v);
           String paikkaString = lo.koordinaatit().toString();
           if (paikka.equals(paikkaString)) 
               System.out.println(lo);
       }
  }

  /**
    * Metodi, joka tarkastaa tarkistuslistalta ettei samaa paikkaa ole k�yty k�sittelem�ss� jo.
    * @param paikka String-muodossa
    * @return true, jos paikka on jo k�sitelty; false, jos ei olla k�sitelty.
    */
  public boolean tarkistaOnkoPaikkaJoKasitelty(String lotkonenPaikka) {
       boolean samaPaikka = false;
       int a = 0;
       while (!samaPaikka && a < koko()) { 
            Paikka tarkistusP = (Paikka)alkio(a);
            String tarkistusPaikka = tarkistusP.toString();
            if (tarkistusPaikka.equals(lotkonenPaikka)) {
               samaPaikka = true;
               break;
            }
       a++;
       }    
       return samaPaikka;
  }

  /**
    * Metodi, joka palauttaa paluuarvonaan paikan suurimman pl�ntin paikkalistan indeksi ja koon.
    * @return suurimman pl�ntin indeksi paikkalistalla
    */

  public int haeSuurinPlantti() {
       //haetaan paikkalistan pl�ntit, jotta lis��ntyminen onnistuu suunnnitelusti.
       int suurin = 0;
       int suurinKoko = 0;
       Plantti suurinPlantti = null;
       for (int f = 0; f < koko(); f++) {
          Lotko planttiko = (Lotko)alkio(f);
          String paikkalistanAlkionLuokka = planttiko.getClass().getSimpleName();

          //Jos suurinta pl�ntti� ei ole m��ritelty, m��ritell��n ensimm�inen paikkalistan pl�ntti
          //automaattisesti suurimmaksi pl�ntiksi.
          if (paikkalistanAlkionLuokka.equals("Plantti") && suurinKoko == 0) {
              suurinPlantti = (Plantti)alkio(f);
              suurinKoko = planttiko.koko();
              suurin = f;
          }     
          //Jos suurin pl�ntti on m��ritelty (suurin koko suurempi kuin nolla), vertaillaan pl�nttej�
          //L�tk�-luokan compareTo-metodilla.
          else if (paikkalistanAlkionLuokka.equals("Plantti") && suurinKoko > 0) {
              int planttiVertailu = suurinPlantti.compareTo((Plantti)planttiko); 
              if (planttiVertailu == 1) {
                 suurinPlantti = (Plantti)alkio(f);
                 suurinKoko = planttiko.koko();
                 suurin = f;
              }        
          }       
       }
       return suurin;
  }

   /*
    * Object-luokan metodin korvaus.
    *
    */

   public String toString() {
      // Listan alkioittainen merkkijonoesitys t�nne.
      String alkiot = "[";

      // Tarkistetaan, ett� listalla on alkioita.
      if (!onkoTyhja()) {
         // Aloitetaan parametrina saadun listan p��st�.
         Solmu paikassa = paa();

         // Edet��n solmu kerrallaan, kunnes l�ydet��n alkio tai lista loppuu.
         while (paikassa != null) {
            // Liitet��n apuviite paikassa-viitteeseen liittyv�n solmun alkioon.
            Object paikanAlkio = paikassa.alkio();

            // Siirryt��n seuraavaan solmuun. Seuraava-aksessori palauttaa
            // viitteen paikassa-viitteeseen liittyv�� solmua _seuraavaan_
            // solmuun. Sijoituksen j�lkeen paikassa-viite liittyy t�h�n solmuun.
            paikassa = paikassa.seuraava();

            // Lis�t��n alkio ja erotin merkkijonoon.
            alkiot += paikanAlkio;
            if (paikassa != null)
               alkiot += ", ";
         }

      }

      // Viimeistell��n esitys.
      alkiot += "]";

      // Palautetaan oma lista merkkijonona.
      return alkiot;
   }
}