import java.io.*;
import java.util.Scanner;
import fi.uta.csjola.oope.lista.*;
import apulaiset.*;
import juuriluokka.*;

/**
  * Simulaattoriluokka, joka sisältää ohjelman toimintoihin
  * tarvittavat metodit.
  * <p>
  * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2015
  * </p>
  * @author Sami-Santeri Svensk
  */

public class Simulaattori {

  private int siemen;
  private int maxX;
  private int maxY;
  private int indeksi;
  private OmaLista lista;
  private OmaLista paikkalista;
  private OmaLista tarkistuslista;
  private OmaLista paikat;
 
  //Rakentajia ja aksessoreita.

  public Simulaattori() {
      lista = new OmaLista();
      paikkalista = new OmaLista();
      tarkistuslista = new OmaLista();
      paikat = new OmaLista();
  }

  //Parametrillinen rakentaja, joka saa parametreina siemenluvun, max-koordinaatit ja parametrittomassa
  //rakentajassa alustetut listat.

  public Simulaattori(int s, int x, int y, int i, OmaLista l, OmaLista t, OmaLista pl, OmaLista p) {
      siemen(s);
      maxX(x);
      maxY(y);
      indeksi(i);
      lista = l;
      tarkistuslista = t;
      paikkalista = pl;
      paikat = p;
  }

  public void siemen(int s) throws NullPointerException, NumberFormatException  {
      if (siemen > 0 || siemen <= 0)
         siemen = s;
      else
         throw new NullPointerException();
  }

  public int siemen() {
      return siemen;
  }

  public void maxX(int x) throws NullPointerException, NumberFormatException  {
      if (x >= 0)
         maxX = x;
      else 
         throw new NullPointerException();
  }

  public int maxX() {
      return maxX;
  }

  public void maxY(int y) throws NullPointerException, NumberFormatException {
      if (maxY >= 0)
         maxY = y;
      else
         throw new NullPointerException();
  }

  public int maxY() {
      return maxY;
  }

  public void indeksi(int i) throws NullPointerException {
      if (i >= 0)
         indeksi = i;
      else
         throw new NullPointerException();
  }

  public int indeksi() {
      return indeksi;
  }

  public OmaLista lista() {
     return lista;
  }

  public OmaLista paikkalista() {
     return paikkalista;
  }

  public OmaLista tarkistuslista() {
     return tarkistuslista;
  }

  public OmaLista paikat() {
     return paikat;
  }

  //metodit.

  public void tulostaAlkutekstit() {
       System.out.println("************");
       System.out.println("* ALKULIMA *");
       System.out.println("************");
  }

  /**
    * Metodi, joka erottelee komennon sisältämät tiedot.
    * @param käyttäjän syöttämä komento.
    * @return yksiulotteinen String-taulukko, joka sisältää komennon (ja parametrit).
    */

  public String[] erotteleParametrit(String komento) {
       // luodaan neljän alkion kokoinen taulukko, jota käytetään komennon ja parametrien käsittelyyn.
       String[] parametrit = new String[4];

       // pilkotaan komento String-luokan split-metodia käyttäen.
       parametrit = komento.split("[ ]");
       return parametrit;
  }   

  /**
    * Metodi, joka pakkaa siemenluvun ja max-koordinaatit esitettävään muotoon.
    * @return merkkijono, joka sisältää siemen luvun ja max-koordinaatit esitettävässä muodossa.
    */
   
  public String pakkaaTiedot() {
       final char EROTIN = '|';
       String siementyhja = lisaaValilyonnitTulosteeseen(siemen);
       String xtyhja = lisaaValilyonnitTulosteeseen(maxX);
       String ytyhja = lisaaValilyonnitTulosteeseen(maxY);
       String tiedotMerkkijonona = siemen + siementyhja + EROTIN + maxX + xtyhja + EROTIN + maxY + ytyhja + EROTIN;
       return tiedotMerkkijonona;
  }

  /**
    * Metodi, jossa luetaan lotkot.txt-tiedostosta lötköjen tiedot.
    * Lukemisen jälkeen luodaan tiedostossa mainitun luokan perusteella
    * joko Klimppi -tai Pläntti-tyyppinen lötkö.
    * Lisäksi lisätään luodut lötköt OmaLista-luokan listalle.
    * @return yksiulotteinen int-taulukko, joka sisältää siemenluvun, max-koordinaatit ja indeksiluvun.
    * @throws FileNotFoundException, jos tiedostoa ei löytynyt.
    */
  public int[] lataa() throws FileNotFoundException, NumberFormatException {
       String merkkijono = "";
       int lukuLaskuri = 1;
       int xmax = 0;
       int ymax = 0;
       int ind = 0;
       indeksi = ind;
       File tiedosto = new File("lotkot.txt");
       Scanner sc = new Scanner(tiedosto);

       //Tärkeimmät simulaation tiedot säilövä yksiulotteinen taulukko.
       int[] tiedot = new int[4];

       //Poistetaan vanhat listat.
       poistaLista(lista);
       poistaLista(paikat);

       //luuppi, jossa skannataan lotkot.txt-tiedostoa.
       while (sc.hasNextLine()) {
          //ylimmän rivin tietojen (siemenluvun ja max.koordinaattien) lukeminen.
          if (lukuLaskuri == 1) {
             merkkijono = sc.nextLine();
             String[] osat = merkkijono.split("[|]");

             int siemen = Integer.parseInt(osat[0].trim());
             tiedot[0] = siemen;
             Automaatti.alusta(siemen);
             xmax = Integer.parseInt(osat[1].trim());
             tiedot[1] = xmax;
             ymax = Integer.parseInt(osat[2].trim());
             tiedot[2] = ymax;
          }

          // lötköjen tietojen lukeminen muilta riveiltä.
          else { 
             merkkijono = sc.nextLine();
             String[] osat2 = merkkijono.split("[|]");
             String tyyppi = osat2[0].trim();
             int koko = Integer.parseInt(osat2[1].trim());
             StringBuilder perima = new StringBuilder(osat2[2].trim());
             String ominaisuus = osat2[3].trim();
                    
             //annetaan satunnaiset paikat.
             int[] lotkonPaikka = Automaatti.annaPaikka(xmax, ymax);
             int koordinaattiX = lotkonPaikka[0];
             int koordinaattiY = lotkonPaikka[1];

             //annetaan paikan indeksiarvot parametrina oliolle.
             Paikka koordinaatit = new Paikka(koordinaattiX, koordinaattiY);

             // jos tyyppi on Klimppi, luodaan klimppi-luokan olio ja välitetään
             // parametreina sen tiedot. Lopuksi luotu klimppi-luokan olio
             // lisätään listan loppuun.
             if (tyyppi.equals("Klimppi")) {
                 char vari = muutaTyyppiChariksi(ominaisuus);
                 Klimppi klimppi = new Klimppi(indeksi, koordinaatit, vari, koko, perima);
                 indeksi = lisaaListanLoppuun(klimppi, koordinaatit);
             }

             //jos tyyppi on Plantti, luodaan pläntti-luokan olio ja tehdään
             //samat toimenpiteet kuin edellä.     
             else if (tyyppi.equals("Plantti")) {
                 boolean soikea = muutaTyyppiBooleaniksi(ominaisuus);
                 Plantti plantti = new Plantti(indeksi, koordinaatit, soikea, koko, perima);              
                 indeksi = lisaaListanLoppuun(plantti, koordinaatit);  
             }
             tiedot[3] = indeksi;
          }
          lukuLaskuri++;
       } 
       sc.close();
       return tiedot;
  }

  /**
    * Metodi, joka lukee In-luokan avulla käyttäjän syöttämän komennon
    * @return annettu komento.
    */

  public String lueKomento() {
       System.out.println("Kirjoita komento:");
       String komento = In.readString();
       return komento;
  }

  public char muutaTyyppiChariksi(String v) {
       char vari = 'P';
       if (v.equals("S"))
           vari = 'S';
       else if (v.equals("P"))
           vari = 'P';
       return vari;
  }

  public boolean muutaTyyppiBooleaniksi(String s) {
       boolean soikea = false;
       if (s.equals("true"))
           soikea = true;
       else if (s.equals("false"))
           soikea = false;
       return soikea;
  }

  /**
    * Metodi, joka luo uuden lötkön jos tilanne sen sallii.
    */
  public void luo() {
       boolean samaPaikka = false;
       int listanKoko = lista.koko();

       for (int i = 0; i < listanKoko; i++) {
           Lotko lotkonen = (Lotko)lista.alkio(i);
           Paikka lpaikka = lotkonen.koordinaatit();
           String lotkonenPaikka = lpaikka.toString();
           boolean onJoLisaannytty = false;
     
           //tarkistetaan, ettei samaa paikkaa ole käyty käsittelemässä jo.
           samaPaikka = tarkistuslista.tarkistaOnkoPaikkaJoKasitelty(lotkonenPaikka);

           //jos paikkaa ei löydy listalta, ruvetaan luomaan uutta lötköä.
           //Ensiksi tallennetaan lötkö paikan lötköistä koostuvalle listalle, ja käsittelemätön paikka
           //tarkistuslistalle, josta tarkistetaan käsitellyt paikat.
           if (!samaPaikka) {
              paikkalista.lisaaLoppuun(lotkonen);
              tarkistuslista.lisaaLoppuun(lpaikka);

              //haetaan saman paikan omaavat lötköt.
              lista.haePaikanLotkot(lotkonenPaikka, i, paikkalista);
        
               //tarkastellaan saman paikan lötköjä.
               for (int g = 0; g < paikkalista.koko(); g++) {
                   Lotko tarkasteltava = (Lotko)paikkalista.alkio(g);
                   String tarkasteltavaLuokka = tarkasteltava.getClass().getSimpleName();

                   //Haetaan paikan suurin pläntti.
                   int suurin = paikkalista.haeSuurinPlantti();

                   //Jos paikassa on klimppi, tarkastellaan voidaanko lisääntyä.
                   if (tarkasteltavaLuokka.equals("Klimppi") && !onJoLisaannytty) {
                      Klimppi klimpikas = (Klimppi)paikkalista.alkio(g);
                      boolean paikastaJoLuotuKlimppi = false;
      
                      //etsitään paikkalistalta toista klimppiä.
                      for (int h = g + 1; h < paikkalista.koko(); h++) {
                          Lotko lotikka = (Lotko)paikkalista.alkio(h);
                          String lotikkaLuokka = lotikka.getClass().getSimpleName();
                          if (lotikkaLuokka.equals("Klimppi") && !paikastaJoLuotuKlimppi) {
                              Klimppi kaveriKlimppi = (Klimppi)paikkalista.alkio(h);
                              boolean varivertailu = klimpikas.equals(kaveriKlimppi);

                              //jos klimpit ovat samanlaiset, aloitetaan uuden klimpin luonti.
                              if (varivertailu == true) {
                                 Object uusi = klimpikas.lisaanny(indeksi, tarkasteltava, lotikka);
                                 if (uusi instanceof Klimppi) { 
                                    Klimppi uusiKlimppi = (Klimppi)uusi;
                                    String[] klimppiTaulukko = uusiKlimppi.koordinaatit().toString().split("[|]");
                                    for (int w = 0; w < klimppiTaulukko.length; w++) { 
                                        klimppiTaulukko[w] = klimppiTaulukko[w].trim();
                                    }
                                    Paikka uusiPaikka = new Paikka(Integer.parseInt(klimppiTaulukko[0]), Integer.parseInt(klimppiTaulukko[1]));
                                    //lisätään uusi klimppi listalle ja muutetaan boolean-muuttuja trueksi.
                                    indeksi = lisaaListanLoppuun(uusiKlimppi, uusiPaikka);
                                    paikastaJoLuotuKlimppi = true;
                                 }
                              }
                          }
                      }
                      if (indeksi >= listanKoko)
                          onJoLisaannytty = true;
                   }

                   //Jos pläntti, tarkastellaan voidaanko lisääntyä.
                   else if (tarkasteltavaLuokka.equals("Plantti") && g == suurin) {
                      boolean sama = false;
                      Plantti lisaantyva = (Plantti)paikkalista.alkio(g);
                      Object uusi = lisaantyva.lisaanny(indeksi, lisaantyva, tarkasteltava);
                      if (uusi instanceof Plantti) {
                          Plantti uusiPlantti = (Plantti)uusi; 
                          String[] uusiTaulukko = uusiPlantti.koordinaatit().toString().split("[|]");
                          for (int r = 0; r < uusiTaulukko.length; r++) { 
                             uusiTaulukko[r] = uusiTaulukko[r].trim();
                          }
                          Paikka uusiPaikka = new Paikka(Integer.parseInt(uusiTaulukko[0]), Integer.parseInt(uusiTaulukko[1]));

                          //Jos paikkalistalla on enemmän kuin yksi alkiota, vertaillaan. Jos ei ole, luodaan 
                          //ilman vertailuja.
                          if (paikkalista.koko() > 1) {
                             sama = etsiSamoja(uusiPlantti, suurin);
                          }
                          else
                              sama = false;

                          //Jos ei ole samoja, lisätään pläntti lötkölistalle.
                          if (!sama) 
                              indeksi = lisaaListanLoppuun(uusiPlantti, uusiPaikka);   
                      }
                   }
               }
               //poistetaan paikkalistan alkiot kierroksen päätteeksi.
               poistaLista(paikkalista);
           }
       }  
       //Poistetaan tarkistuslista.
       poistaLista(tarkistuslista);
       //return indeksi;
  }

  public boolean etsiSamoja(Plantti uusiPlantti, int suurin) {
      int n = 0;
      boolean sama = false;
      do {
          Lotko lollertava = (Lotko)paikkalista.alkio(n);
          String lollertavaLuokka = lollertava.getClass().getSimpleName();
          if (lollertavaLuokka.equals("Plantti") && n != suurin) {
               Plantti plantahtava = (Plantti)paikkalista.alkio(n);
               boolean lollertavaMuoto = plantahtava.soikea();
               boolean samaMuoto = uusiPlantti.equals(plantahtava);
               if (samaMuoto) {
                   sama = true;
                   break;
               }
               else
                   sama = false;
           }                  
           n++;
      }
      while (n < paikkalista.koko() && !sama);
      return sama;
  }

  /**
    * Metodi, joka lisää lötkön listan loppuun ja koordinaatit paikat-listalle.
    * @param uusi, lötköjä säilövälle listalle lisättävä lotko
    * @param lötkön paikka, joka lisätään paikat-listalle.
    * @return lisäysten jälkeinen indeksiarvo.
    */
  public int lisaaListanLoppuun(Lotko lisattava, Paikka uusiPaikka) {
       if (lisattava instanceof Klimppi) {
           Klimppi uusiKlimppi = (Klimppi)lisattava;
           lista.lisaaLoppuun(uusiKlimppi);
           paikat.lisaaLoppuun(uusiPaikka);
       }
       else if (lisattava instanceof Plantti) {
           Plantti uusiPlantti = (Plantti)lisattava;
           lista.lisaaLoppuun(uusiPlantti);
           paikat.lisaaLoppuun(uusiPaikka);
       }
       indeksi++;
       return indeksi;
  }

  /**
    * Metodi, jossa liikutetaan kaikkia lötköjä Automaatti-luokan antamiin satunnaiskoordinaatteihin.
    * Vanhat koordinaatit korvataan uusilla.
    */
  public void liiku() {
       //Käydään listaa läpi yksitellen, ja muutetaan kunkin lötkön koordinaatteja.
       for (int i = 0; i < lista.koko(); i++) {
           Lotko o = (Lotko)lista.alkio(i);
           Paikka opaikka = (Paikka)paikat.alkio(i);
           String paikkaString = opaikka.toString();

           //pilkotaan lötkön tiedot 2 paikan kokoiseen taulukkoon ja erotetaan välilyönnit varsinaisesta tiedosta.
           String[] paikkaTaulukko = paikkaString.split("[|]");
           for (int j = 0; j < 2; j++) {
              paikkaTaulukko[j] = paikkaTaulukko[j].trim();
           }

           //Poimitaan nykyiset x- ja y-arvot, ja annetaan Automaatti-luokalle, joka arpoo
           //uudet paikat.
           int x = Integer.parseInt(paikkaTaulukko[0]);
           int y = Integer.parseInt(paikkaTaulukko[1]);
           int[] uudetPaikat = Automaatti.annaPaikka(x, y, maxX, maxY);

           //Uuden paikan koordinaatit annetaan Paikka-luokan oliolle.
           Paikka paikka = new Paikka(uudetPaikat[0], uudetPaikat[1]);
           o.koordinaatit(paikka);
           Paikka p = (Paikka)paikat.alkio(i);
           p.koordinaatit(uudetPaikat[0], uudetPaikat[1]);
       }
  }

  /**
    * Metodi, jossa liikutetaan käyttäjän antamana parametriarvona antaman indeksiarvon mukaista
    * lötköä parametreina annettuhin uuteen paikkaan.
    * @param parametrit-taulukko, josta erotellaan liikuteltavan lötkön indeksi ja haluttu uusi paikka.
    */
  public void liikutaYhta(String[] parametrit) {

       //Tarkastetaan syötteestä halutun lötkön indeksi, ja uudet koordinaatit.
       int index = Integer.parseInt(parametrit[1]);
       int uusix = Integer.parseInt(parametrit[2]);
       int uusiy = Integer.parseInt(parametrit[3]);

       //Annetaan Paikka-luokan oliolle uudet arvot parametreina.
       Paikka paikka = new Paikka(uusix, uusiy);

       //Lötkölle välitetään parametrina uuden paikan koordinaatit Paikka-luokan oliota hyödyntäen.
       Lotko o = (Lotko)lista.alkio(index);
       o.koordinaatit(paikka);

       Paikka p = (Paikka)paikat.alkio(index);
       p.koordinaatit(uusix, uusiy);
  }

  /**
    * Metodi, joka tallentaa lötköt sisältävän listan lotkot.txt-tiedostoon.
    * Tallennettavat ominaisuudet ovat lötkön tyyppi, koko, perima ja muoto tai väri.
    * @throws FileNotFoundException, jos tiedostoa ei löydy.
    */
   public void tallenna() throws FileNotFoundException {
       PrintWriter kirjoita = new PrintWriter("lotkot.txt");
       final char EROTIN = '|';
       String siementyhja = lisaaValilyonnitTiedostoon(siemen);
       String xmaxtyhja = lisaaValilyonnitTiedostoon(maxX);
       String ymaxtyhja = lisaaValilyonnitTiedostoon(maxY);
          
       String tiedotMerkkijonona = siemen + siementyhja + EROTIN + maxX + xmaxtyhja + EROTIN + maxY + ymaxtyhja + EROTIN;
       kirjoita.println(tiedotMerkkijonona);
       String tallennettavatTiedot;

       for (int i = 0; i < lista.koko(); i++) {
           Lotko tallennettava = (Lotko)lista.alkio(i);
           String tallennettavanLuokka = tallennettava.getClass().getSimpleName();
           int tallennettavaKoko = tallennettava.koko();
           String kokotyhja = lisaaValilyonnitTiedostoon(tallennettavaKoko);

           StringBuilder tallennettavaPerima = tallennettava.perima();
           tallennettavatTiedot = tallennettavanLuokka + " " + EROTIN + tallennettavaKoko + kokotyhja + EROTIN + tallennettavaPerima + EROTIN;
           if (tallennettavanLuokka.equals("Klimppi")) {
              Klimppi k = (Klimppi)lista.alkio(i);
              char tallennettavaVari = k.vari();
              tallennettavatTiedot = tallennettavatTiedot + tallennettavaVari + "       " + EROTIN;
           }
           else {
              Plantti p = (Plantti)lista.alkio(i);
              boolean tallennettavaSoikea = p.soikea();
              String soikeatyhja = "";
              if (!tallennettavaSoikea)
                 soikeatyhja = "   ";
              else
                 soikeatyhja = "    ";
              tallennettavatTiedot = tallennettavatTiedot + tallennettavaSoikea + soikeatyhja + EROTIN;
           }
           kirjoita.println(tallennettavatTiedot);
           tallennettavatTiedot = "";   
       }
       kirjoita.close();
  }

  /**
    * Metodi, joka lisää välilyönnit näytölle tulostettaviin tietoihin.
    * @param luku, jonka pituuden perusteella lisätään välilyönnit.
    * @return välilyönneistä koostuva merkkijono.
    */

  public String lisaaValilyonnitTulosteeseen(int luku) {
       String tyhja = "";
       if (luku < 10 && luku > -10)
           tyhja = "  ";
       else if ((luku >= 10 && luku < 100) || (luku > -10 && luku <= 0))
           tyhja = " ";
       else
           tyhja = "";   
       return tyhja;  
  }

  /**
    * Metodi, joka lisää välilyönnit tiedostoon tallennettaviin tietoihin.
    * @param luku, jonka pituuden perusteella lisätään välilyönnit.
    * @return välilyönneistä koostuva merkkijono.
    */

  public String lisaaValilyonnitTiedostoon(int luku) {
       String tyhja = "";
       if (luku >= 0 && luku < 10)
          tyhja = "       ";
       else if ((luku >= 10 && luku < 100) || (luku < 0 && luku > -10))
          tyhja = "      ";
       else if ((luku >= 100 && luku < 1000) || (luku <= -10 && luku > -100))
          tyhja = "     ";
       else if ((luku >= 1000 && luku < 10000) || (luku <= -100 && luku > -1000))
          tyhja = "    ";
       else if ((luku >= 10000 && luku < 100000) || (luku <= -1000 && luku > -10000))
          tyhja = "   ";
       else if ((luku >= 100000 && luku < 1000000) || (luku <= -10000 && luku > -100000))
          tyhja = "  ";
       else if ((luku >= 1000000 && luku < 10000000) || (luku <= -100000 && luku > -1000000))
          tyhja = " ";
       else
          tyhja = ""; 

       return tyhja;    
  }

  /**
    * Metodi, joka poistaa listan alkiot.
    * @param lista, jonka alkiot halutaan poistaa.
    */

  public void poistaLista(OmaLista lista) {
      for (int g = lista.koko() - 1; g >= 0; g--) {
          lista.poista(g); 
      }
  }
}