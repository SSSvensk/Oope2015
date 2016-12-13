package juuriluokka;

/**
  * Luokka, jossa lötkön koordinaateista luodaan 
  * Paikka-tyyppinen olio.
  * <p>
  * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2015
  * </p>
  * @author Sami-Santeri Svensk
  */

public class Paikka {
   private int[] koordinaatit;

   public Paikka(int x, int y) {
     koordinaatit = new int[2];
     koordinaatit(x, y);
   }

   public void koordinaatit(int x, int y) {
     if (x >= 0 && y >= 0) {
        koordinaatit[0] = x;
        koordinaatit[1] = y;
     }
   }

   public int[] koordinaatit() {
     return koordinaatit;
   }


   public String toString() {
     String tyhja1;
     String tyhja2;
     if (koordinaatit[0] < 10)
         tyhja1 = "  ";
     else if (koordinaatit[0] >= 10 && koordinaatit[0] < 100)
         tyhja1 = " ";
     else
         tyhja1 = "";

     if (koordinaatit[1] < 10)
         tyhja2 = "  ";
     else if (koordinaatit[1] >= 10 && koordinaatit[1] < 100)
         tyhja2 = " ";
     else
         tyhja2 = "";
     final char EROTIN = '|';
     String jono = koordinaatit[0] + tyhja1 + EROTIN + koordinaatit[1] + tyhja2;
     return jono;
   }
}