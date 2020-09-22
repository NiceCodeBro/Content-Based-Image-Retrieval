package other;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import vpt.Image;
import vpt.IntegerImage;
import vpt.algorithms.display.Display2D;
import vpt.algorithms.io.Load;

/**
 *
 * @author bilmuhlab
 */
//C:\Users\bilmuhlab\Desktop\lab1\cs464-564\gokada
public class Imageprocessingtest {

    public static void main(String[] args) {
        

        /*
      Image img = Load.invoke("C:\\Users\\muham\\Desktop\\School Files\\Forth Year\\Image Processing\\cs464-564\\cs464-564\\lowContrast.png");
      Display2D.invoke(img);
      int min = img.getXYByte(0, 0);
      int max = img.getXYByte(0, 0);
      int curr;
      
      for (int x = 1; x < img.getXDim(); ++x) {
            for (int y = 1; y < img.getYDim(); ++y) {
                curr = img.getXYByte(x, y);
                if(min> curr){
                    min = curr;
                }
                if(max<curr){
                    max = curr;
                }                
            }
        }
        System.err.println("Max:"+max+"Min:"+min);
        
	
	  // HATALI
      double newVal;
      for (int x = 0; x < img.getXDim(); ++x) {
            for (int y = 0; y < img.getYDim(); ++y) {
                newVal = 255 * ((img.getXYByte(x, y) - min)/(max-min));
                //System.err.println("NewVal"+newVal);
                img.setXYDouble(x, y, newVal);      
            }
        }
      Display2D.invoke(img);
      */
        
    /*
        //----------Noise yok etmek
        // Goruntu belgesini diskten bellege aktar
        Image[] goruntuler = new Image[50];
        for (int i = 0; i < 50; ++i) {
            goruntuler[i] = Load.invoke("C:\\Users\\muham\\Desktop\\School Files\\Forth Year\\Image Processing\\cs464-564\\cs464-564\\gokada\\gokada" + (i + 1) + ".jpg");
        }
        Image ortalama = new DoubleImage(698, 462, 1);

        //foreach img
        for (int i = 0; i < goruntuler.length; i++) {
            //foreach column
            for (int j = 0; j < ortalama.getXDim(); j++) {
                //foreach row
                for (int k = 0; k < ortalama.getYDim(); k++) {
                    double p = ortalama.getXYDouble(j, k);
                    double q = goruntuler[i].getXYDouble(j, k);
                    ortalama.setXYDouble(j, k, p + q / 50.0);
                }
            }
        }
        for (int x = 0; x < ortalama.getXDim(); ++x) {
            for (int y = 0; y < ortalama.getYDim(); ++y) {
                int p = ortalama.getXYByte(x, y);
                int q = p & 0xfffffff0;
                ortalama.setXYByte(x, y, p);
            }
        }
        Display2D.invoke(ortalama);
        
        ortalama = ContrastStretch.invoke(ortalama);
        Display2D.invoke(ortalama, "high contrast");
*/

   /*
        Image img = Load.invoke("C:\\Users\\muham\\Desktop\\School Files\\Forth Year\\Image Processing\\cs464-564\\cs464-564\\gokada\\gokada1.jpg");


		// genisligi ogren
		int genislik = img.getXDim();
		
		// yuksekligi ogren
		int yukseklik = img.getYDim();
		System.err.println(genislik + " " + yukseklik);

		// ayni boyutta bos goruntu olustur
		Image copy = img.newInstance(false);
		
		// 100, 200 konumundaki degeri oku
		int p = img.getXYByte(100, 200);
		System.err.println(p);
		
		// goruntule
		Display2D.invoke(img);
		
		// 100. sutun, 200. satır konumuna yeni deger ata
		img.setXYByte(100, 200, 255);
		
		// yeniden goruntule
		Display2D.invoke(img);	
         */


   /*



        int nOfImage = 0;
        Image img = Load.invoke("blobs.png");
        Image round = GErosion.invoke(img, FlatSE.disk(29));
        Display2D.invoke(round);

        for(int a = 0; a < round.getXDim(); ++a)
            for(int b = 0; b < round.getYDim();++b)
            {
                if(round.getXYByte(a, b) == 255)
                {
                    nOfImage++;
                    paint(a,b,round);
                }
            }


        System.out.print("???? " + nOfImage);
        Display2D.invoke(round,"new");
          */

      /*  Image img = Load.invoke("gears.png");
        Image img2 = BErosion.invoke(img, FlatSE.centerlessCircle(51));
        Display2D.invoke(img,"A1");
        Display2D.invoke(img2,"A2");
        Image img3 = BDilation.invoke(img2, FlatSE.disk(45));
        Display2D.invoke(img3,"A3");

        Image img4 = OR(img,img3);
        Display2D.invoke(img4,"A4");

        Image img5 = GOpening.invoke(img4,FlatSE.disk(7));
        Display2D.invoke(img5,"A5");

        Image img6 = AbsSubtraction.invoke(img5,img4);
        Display2D.invoke(img6,"A6");

        Image img7 = GOpening.invoke(img6,FlatSE.cross(3));
        Display2D.invoke(img7,"A7");

        Image img8 = BDilation.invoke(img7,FlatSE.disk(9));
        Display2D.invoke(img8,"A8");

        Image img9 = GClosing.invoke(img8,FlatSE.disk(9));
        Display2D.invoke(img8,"A9");
        */


        Image imgLetters = Load.invoke("C:\\Users\\muham\\Desktop\\template_matching\\letters.png");
        Image imgK = Load.invoke("C:\\Users\\muham\\Desktop\\template_matching\\template.png");

        Image newImage = new IntegerImage(imgLetters.getXDim(),imgLetters.getYDim(),1);

        for(int i = 0; i < imgLetters.getXDim()-17; ++i)
        {
            for(int j = 0; j < imgLetters.getYDim()-23; ++j)
            {
                newImage.setXYByte(i+17,j+23, Imageprocessingtest.giveCorolation(i,j,imgLetters,imgK));
            }
        }

        Display2D.invoke(newImage,"a");




    }

    static void paint(int x, int y, Image img)
    {
        for(int a = x - 10; a < 40 ; ++a)
            for(int b = y - 10; b < 40; ++b)
                img.setXYByte(a, b, 155);

    }

    public static Image OR(Image img1, Image img2)
    {
        Image img3 = img1.newInstance(false);
        for(int i = 0; i < img1.getSize(); ++i)
        {
            boolean p = img1.getBoolean(i);
            boolean q = img2.getBoolean(i);
            img3.setBoolean(i,p|q);

        }
        return  img3;
    }

    public static Image AND(Image img1, Image img2)
    {
        Image img3 = img1.newInstance(false);
        for(int i = 0; i < img1.getSize(); ++i)
        {
            boolean p = img1.getBoolean(i);
            boolean q = img2.getBoolean(i);
            img3.setBoolean(i,p&q);

        }
        return  img3;
    }

    public static int giveCorolation(int x, int y, Image imgSource, Image target)
    {
        int sum = 0;
        for(int i = x,k=0; i < target.getXDim(); ++i, ++k)
        {
            for(int j = y,l=0; j < target.getYDim(); ++j, ++l)
            {
               sum += (imgSource.getXYByte(x,y)  * target.getXYByte(k,l));
            }
        }

        return sum;
    }
}
