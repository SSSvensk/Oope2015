import java.io.*;
import java.util.Scanner;
import fi.uta.csjola.oope.lista.*;
import apulaiset.*;
import juuriluokka.*;

/**
  * Testiluokka, jossa annetaan komento
  * ja kutsutaan Simulaattori-luokan metodeja.
  * <p>
  * Harjoitusty�, Olio-ohjelmoinnin perusteet, kev�t 2015
  * </p>
  * @author Sami-Santeri Svensk
  */

public class Oope2015HT {

  public static void main(String[] args) {
     try {

        //Ohjelman komennot vakioina.
        final String LATAA = "lataa";
        final String LISTAA = "listaa";
        final String LUO = "luo";
        final String LIIKU = "liiku";
        final String TALLENNA = "tallenna";
        final String LOPETA = "lopeta";

        boolean jatka = true;
        Simulaattori alustaja = new Simulaattori();
        
        //Muutama lista.
        OmaLista lista = alustaja.lista();
        OmaLista tarkistuslista = alustaja.tarkistuslista();
        OmaLista paikkalista = alustaja.paikkalista();
        OmaLista paikat = alustaja.paikat();

        int[] palaute = alustaja.lataa();

        Simulaattori simu = new Simulaattori(palaute[0], palaute[1], palaute[2], palaute[3], lista, tarkistuslista, paikkalista, paikat);
        String tiedotMerkkijonona = simu.pakkaaTiedot();

        //Alkutekstien tulostus.
        simu.tulostaAlkutekstit();

        //Luuppi, joka py�ritt�� ohjelmaa.
        do {
           try {

              // komento luetaan k�ytt�j�lt�.
              String komento = simu.lueKomento();
              String[] parametrit = simu.erotteleParametrit(komento);
    
              /*  Jos komento on lataa, kutsutaan lataa-metodia, ja saadaan paluuarvona yksiulotteinen
               *  int-taulukko, jossa on siemenluku, maksimi x- ja y-arvot sek� l�tk�jen yksil�imiseen
               *  k�ytett�v� indeksi-arvo. Taulun arvot sijoitetaan merkkijonoon, jota k�ytet��n tallentamisessa
               *  sek� kaikkien l�tk�jen listaamisessa.
               */

              if (komento.equals(LATAA)) {

                 palaute = simu.lataa();

                 //Muokataan tietojen esitysmuotoa lis��m�ll� v�lily�ntej� riippuen tiedon pituudesta.
                 tiedotMerkkijonona = simu.pakkaaTiedot();
              }

              // jos komento on listaa, listataan kaikki listalle s�il�tyt l�tk�t.
              else if (parametrit[0].equals(LISTAA) && parametrit.length == 1) {
                 lista.listaa(tiedotMerkkijonona);
              }

              //jos komento on listaa, ja sis�lt�� yhden parametrin listataan 
              //parametrin mukaisen l�tk�n indeksiarvon kanssa samanlaiset l�tk�t.
              else if (parametrit[0].equals(LISTAA) && parametrit.length == 2) {
                 if (Integer.parseInt(parametrit[1]) <= simu.indeksi()) 
                     lista.listaaSamat(parametrit);
                 else
                     System.out.println("Virhe!");
              }

              else if (parametrit[0].equals(LISTAA) && parametrit.length == 3) {
                 if (Integer.parseInt(parametrit[1]) <= simu.maxX() && Integer.parseInt(parametrit[2]) <= simu.maxY())
                    lista.listaaPaikanAlkiot(parametrit);
                 else
                    System.out.println("Virhe!");
              }

              else if (komento.equals(LUO)) {   
                 simu.luo();
              }
       
              //jos komento on "liiku", eik� sis�ll� parametreja, liikutetaan kaikkia
              //listan l�tk�j� sattumanvaraisesti antamalla Automaatti-luokasta 
              //uudet sattumanvaraiset koordinaatit.
              else if (parametrit[0].equals(LIIKU) && parametrit.length == 1) {
                 simu.liiku();    
              }
 
              //jos komento on "liiku" ja sis�lt�� kolme parametria, annetaan 
              //ensimm�ist� parametria vastaavalle l�tk�lle kahden viimeisen parametrin 
              //mukaiset arvot koordinaatistossa.
              else if (parametrit[0].equals(LIIKU) && parametrit.length == 4) {
                 if (Integer.parseInt(parametrit[2]) <= simu.maxX() && Integer.parseInt(parametrit[3]) <= simu.maxY())
                     simu.liikutaYhta(parametrit);
                 else
                     System.out.println("Virhe!"); 
              }
              //tallennetaan, jos komento on tallenna.
              else if (komento.equals(TALLENNA)) 
                 simu.tallenna();
              else if (komento.equals(LOPETA))
                 jatka = false; 
              else
                 System.out.println("Virhe!");
           }

           //Napataan muutama ajonaikainen poikkeus.
           catch (NumberFormatException | NullPointerException | ClassCastException | UnsupportedOperationException e) {
              System.out.println("Virhe!");
           }
        }
        while(jatka);

        //tulostetaan hyv�stit ohjelman juhlalliseksi lopetukseksi.
        System.out.println("Ohjelma lopetettu.");
    }

    //Napataan tiedoston l�ytym�tt�myydest� johtuva poikkeus.
    catch (FileNotFoundException e) {
        System.out.println("Virhe!");
    }
  }
}