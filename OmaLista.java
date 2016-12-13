// Otetaan käyttöön lista-pakkauksen luokat.
import fi.uta.csjola.oope.lista.*;
import juuriluokka.*;

/** Viikkoharjoitus 6, tehtävät 2 - 5.
  *
  * Yhteen suuntaan linkitetystä Oope-listasta peritty luokka.
  * <p>
  * Olio-ohjelmoinnin perusteet, kevät 2015.
  * <p>
  * @author Jorma Laurikkala (jorma.laurikkala@uta.fi),
  * @author Sami-Santeri Svensk (uusia metodeja)
  * Informaatiotieteiden yksikkö, Tampereen yliopisto.
  *
  */

public class OmaLista extends LinkitettyLista {

   /*
    * Uudet listaoperaatiot.
    *
    */

   /** Poistaa listalta null-arvoiset alkiot. Muokattu 6.3., jolloin metodin tyypiksi
     * vaihdettu int ja lisätty poistettujen laskurin kasvatus.)
     *
     * @return poistettujen alkioiden lukumäärä.
     */
   public int karsi() {
      // Poistettujen null-arvoisten alkioiden lukumäärä.
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
         // Siirrytään seuraavaan paikkaan.
         else
            i++;
      }

      // Palautetaan poistettujen alkoiden lukumäärä.
      return poistettuja;
   }

   /** Poistaa listan l pään ja lisää sen tämän listan loppuun.
     * 
     * @return true, jos voitiin lisätä ja false, jos parametrissa oli virhe.
     */
   public boolean lisaaLoppuun(LinkitettyLista l) {
      // Null-arvoinen viite tai lista on tyhjä.
      if (l == null || l.koko() == 0)
         return false;

      // Poistetaan listan l pää ja lisätään se tämän listan loppuun.
      lisaaLoppuun(l.poistaAlusta());

      // Viestitään onnistumisesta.
      return true;
   }

   /** Poistaa listalta annettua oliota equals-mielessä vastaavat alkiot.
     *
     * @param alkio olio, jota vastaavat alkiot poistetaan listalta.
     * @return viitteet poistettuihin olioihin. Alkioiden järjestys on sama
     * kuin tällä listalla. Paluuarvo on null, jos vastaavia alkioita ei löytynyt,
     * parametri on null-arvoinen tai lista on tyhjä.
     */
   public OmaLista poista(Object alkio) {
      // Voidaan hakea.
      if (alkio != null && koko() > 0) {
         // Luodaan oma lista poistetuille. Poistettujen listalta asetetaan
         // viite kuhunkin tältä listalta poistettuun alkioon.
         OmaLista poistetut = new OmaLista();

         // Käydään lista läpi alusta loppuun.
         int i = 0;
         while (i < koko()) {
            // Tutkitaan onko tietoalkio sama kuin parametri.
            boolean loytyi = alkio(i).equals(alkio);

            // Poistetaan tältä listalta ja lisätään palautettavalle listalle.
            // Laskuria ei kasvateta, koska nykyiselle paikalle siirtyy
            // poistettavaa solmua seuraava solmu.
            if (loytyi)
               poistetut.lisaaLoppuun(poista(i));
            // Siirrytään seuraavaan paikkaan.
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

   /** Hakee listan suurimman alkion. Operaatio ei käytä listan alkio-metodia,
     * jotta listaa ei käytäisi kutakin alkiota vertailtaessa turhaan läpi
     * alusta lähtien vertailtavaan alkion saakka. Seuraava-viitteen avulla
     * listaa kulkemalla päästään seuraavaan solmuun ja sen alkioon huomattavasti
     * vähemmällä laskennalla.
     *
     * @return viite haettuun alkioon. Paluuarvo on null, jos lista on tyhjä.
     */
   // Lisämääre, jolla kääntäjälle vakuutetaan, että metodin koodi on turvallista.
   // Ilman määrettä kääntäjä varoittaa, että Comparable-rajapintaa käytetään
   // ei-geneerisellä tavalla.
   @SuppressWarnings({"unchecked"})
   public Object haeSuurin1() {
      // Ei voida hakea. Palautetaan heti null-arvo.
      if (koko() == 0)
         return null;

      // Nykyiseen solmuun liittyvä viite, joka liitetään aluksi listan pääsolmuun.
      Solmu nykyinenSolmu = paa();

      // Tämän hetkinen maksimialkio.
      Object suurin = alkio(0);

      // Siirrytään seuraavaan solmuun.
      nykyinenSolmu = nykyinenSolmu.seuraava();

      // Tutkitaan alkioita, kunnes saavutetaan listan loppu.
      while (nykyinenSolmu != null) {
         // Asetaan compareTo-metodia kutsuvaan metodiin rajapinnan tyyppinen viite,
         // jotta voidaan kutsua rajapinnan metodia, joka ei ole saatavilla Object-
         // tyyppisen viitteen kautta.
         Comparable vertailtava = (Comparable)nykyinenSolmu.alkio();

         // Löydettiin uusi maksimi.
         if (vertailtava.compareTo(suurin) > 0)
            // Asetaan viite uuden maksimiarvon sisältävään alkioon.
            suurin = vertailtava;

         // Siirrytään seuraavaan solmuun.
         nykyinenSolmu = nykyinenSolmu.seuraava();
      }

      // Palautetaan suurimpaan alkioon liittyvä viite.
      return suurin;
   }

   /** Hakee listan suurimman alkion. Alkio-metodin käyttö selkeyttää, mutta samalla
     * hidastaa metodin toimintaa.
     *
     * @return viite haettuun alkioon. Paluuarvo on null, jos lista on tyhjä.
     */
   // Lisämääre, jolla kääntäjälle vakuutetaan, että metodin koodi on turvallista.
   // Ilman määrettä kääntäjä varoittaa, että Comparable-rajapintaa käytetään
   // ei-geneerisellä tavalla.
   @SuppressWarnings({"unchecked"})
   public Object haeSuurin2() {
      // Ei voida hakea. Palautetaan heti null-arvo.
      if (koko() == 0)
         return null;

      // Tämän hetkinen maksimialkio.
      Object suurin = alkio(0);

      // Tutkitaan alkioita, kunnes saavutetaan listan loppu.
      int i = 0;
      while (i < koko()) {
         // Asetaan compareTo-metodia kutsuvaan metodiin rajapinnan tyyppinen viite,
         // jotta voidaan kutsua rajapinnan metodia, joka ei ole saatavilla Object-
         // tyyppisen viitteen kautta.
         Comparable vertailtava = (Comparable)alkio(i);

         // Löydettiin uusi maksimi.
         if (vertailtava.compareTo(suurin) > 0)
            // Asetaan viite uuden maksimiarvon sisältävään alkioon.
            suurin = vertailtava;

         // Siirrytään seuraavaan paikkaan.
         i++;
      }

      // Palautetaan suurimpaan alkioon liittyvä viite.
      return suurin;
   }

  /**
    * Metodi, joka etsii kaikki saman paikan lötköt luomista varten.
    * @param paikan koordinaatit String-muodossa.
    * @param laskurina toimiva luku lötköjen etsimiseen listalta.
    */

  public void haePaikanLotkot(String lotkonenPaikka, int i, OmaLista paikkalista) {
       int listanKoko = koko();

       //Vertaillaan ja lisätään paikkalistalle saman paikan lötköt
       for (int b = i + 1; b < listanKoko; b++) {
           Lotko vertailtava = (Lotko)alkio(b);
           String vertailtavaPaikka = vertailtava.koordinaatit().toString();
           if (vertailtavaPaikka.equals(lotkonenPaikka)) 
               paikkalista.lisaaLoppuun(vertailtava);
       }
  }

  /**
    * Metodi, jossa tulostetaan näytölle OmaLista-luokan listan kaikki lötköt.
    * @param esitettävässä muodossa oleva merkkijono, joka sisältää siemenluvun ja max-koordinaatit.
    */
  public void listaa(String tiedotMerkkijonona) {      
       //Tulostetaan simulaattorin tiedot.
       System.out.println(tiedotMerkkijonona);

       //Tulostetaan kaikki listalla olevat lötköt.
       for (int i = 0; i < koko(); i++) {
           System.out.println(alkio(i));
       }
  }

  /**
    * Metodi, jossa listataan parametrina annetun lötkön indeksiarvon kanssa samanlaiset lötköt.
    * Jos indeksiarvon lötkö on tyypiltään Klimppi, tulostetaan saman väriset Klimpit
    * Jos indeksiarvon lötkö on tyypiltään Pläntti, tulostetaan saman muotoiset Pläntit.
    * @param parametrit, joista erotellaan vertailtavan lötkön indeksi.
    */
  public void listaaSamat(String[] parametrit) {

       //Määritellään annetuista parametreistä vertailtavan lötkön indeksi.
       int ind = Integer.parseInt(parametrit[1]);

       //etsitään listasta indeksiarvon omaava lötkö.
       Lotko kohde = (Lotko)alkio(ind);

       //Tarkastetaan onko lötkö klimppi vai pläntti.
       String klnimi = kohde.getClass().getSimpleName();

       //Verrataan vertailuun otettua lötköä listan muihin lötköihin.
       for (int i = 0; i < koko(); i++) {
           //Tarkastetaan toisen vertailtavan luokka.
           Lotko vertailtava = (Lotko)alkio(i);
           String luokkaNimena = vertailtava.getClass().getSimpleName();

           //Jos molemmat ovat klimppejä niin...
           if (klnimi.equals(luokkaNimena) && klnimi.equals("Klimppi")) {
              Klimppi k = (Klimppi)kohde;
              Klimppi v = (Klimppi)vertailtava;

              //Jos klimpit ovat samanlaisia, tulostetaan vertailtava.
              if (k.equals(v))
                 System.out.println(vertailtava);
           }    

           //Jos molemmat ovat plänttejä, niin...
           if (klnimi.equals(luokkaNimena) && klnimi.equals("Plantti")) {
              Plantti k = (Plantti)kohde;
              Plantti v = (Plantti)vertailtava;

              //Jos pläntit ovat samanlaisia, tulostetaan vertailtava.
              if (k.equals(v))
                 System.out.println(vertailtava);
           }                      
       }
  }

  /**
    * Metodi, joka listaa samassa paikassa sijaitsevat lötköt.
    * @param parametrit-taulukko, josta erotellaan halutun vertailupaikan koordinaatit.
    */
  public void listaaPaikanAlkiot(String[] parametrit) {
       //Sijoitetaan paremetrien arvot x- ja y-koordinaattien arvoksi, ja luodaan vertailtava paikka.
       int x = Integer.parseInt(parametrit[1]);
       int y = Integer.parseInt(parametrit[2]);
       Paikka vertailtavaPaikka = new Paikka(x, y);
       String paikka = vertailtavaPaikka.toString();

       //Käydään läpi listan alkioita, ja tulostetaan saman paikan omaavat lötköt.
       for (int v = 0; v < koko(); v++) {
           Lotko lo = (Lotko)alkio(v);
           String paikkaString = lo.koordinaatit().toString();
           if (paikka.equals(paikkaString)) 
               System.out.println(lo);
       }
  }

  /**
    * Metodi, joka tarkastaa tarkistuslistalta ettei samaa paikkaa ole käyty käsittelemässä jo.
    * @param paikka String-muodossa
    * @return true, jos paikka on jo käsitelty; false, jos ei olla käsitelty.
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
    * Metodi, joka palauttaa paluuarvonaan paikan suurimman pläntin paikkalistan indeksi ja koon.
    * @return suurimman pläntin indeksi paikkalistalla
    */

  public int haeSuurinPlantti() {
       //haetaan paikkalistan pläntit, jotta lisääntyminen onnistuu suunnnitelusti.
       int suurin = 0;
       int suurinKoko = 0;
       Plantti suurinPlantti = null;
       for (int f = 0; f < koko(); f++) {
          Lotko planttiko = (Lotko)alkio(f);
          String paikkalistanAlkionLuokka = planttiko.getClass().getSimpleName();

          //Jos suurinta plänttiä ei ole määritelty, määritellään ensimmäinen paikkalistan pläntti
          //automaattisesti suurimmaksi pläntiksi.
          if (paikkalistanAlkionLuokka.equals("Plantti") && suurinKoko == 0) {
              suurinPlantti = (Plantti)alkio(f);
              suurinKoko = planttiko.koko();
              suurin = f;
          }     
          //Jos suurin pläntti on määritelty (suurin koko suurempi kuin nolla), vertaillaan plänttejä
          //Lötkö-luokan compareTo-metodilla.
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
      // Listan alkioittainen merkkijonoesitys tänne.
      String alkiot = "[";

      // Tarkistetaan, että listalla on alkioita.
      if (!onkoTyhja()) {
         // Aloitetaan parametrina saadun listan päästä.
         Solmu paikassa = paa();

         // Edetään solmu kerrallaan, kunnes löydetään alkio tai lista loppuu.
         while (paikassa != null) {
            // Liitetään apuviite paikassa-viitteeseen liittyvän solmun alkioon.
            Object paikanAlkio = paikassa.alkio();

            // Siirrytään seuraavaan solmuun. Seuraava-aksessori palauttaa
            // viitteen paikassa-viitteeseen liittyvää solmua _seuraavaan_
            // solmuun. Sijoituksen jälkeen paikassa-viite liittyy tähän solmuun.
            paikassa = paikassa.seuraava();

            // Lisätään alkio ja erotin merkkijonoon.
            alkiot += paikanAlkio;
            if (paikassa != null)
               alkiot += ", ";
         }

      }

      // Viimeistellään esitys.
      alkiot += "]";

      // Palautetaan oma lista merkkijonona.
      return alkiot;
   }
}