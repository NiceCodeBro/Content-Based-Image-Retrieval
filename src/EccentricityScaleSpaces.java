import vpt.Image;
import vpt.algorithms.io.Load;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EccentricityScaleSpaces {
    private static final int EUCLIDEAN = 1;
    private static final int MANHATTAN = 2;
    private static final int CHISQUARE = 3;

    private static double calculateMomentESS(Image image, double p, double q)
    {
        double moment = 0;
        for(int x = 0; x < image.getXDim(); ++x)
        {
            for(int y = 0; y < image.getYDim(); ++y)
            {
                if(image.getXYByte(x,y) > 150)
                {
                    moment+= Math.pow(x,p) * Math.pow(y,q);
                }


            }
        }
        return moment;
    }

    private static double giveDistanceESS(double first, double second, int distanceType)
    {

        double distance = 0 ;

        if(distanceType == EUCLIDEAN)
        {
            distance += SimilarityMeasurementModule.euclideanDistance(first,second);
        }
        else if(distanceType == MANHATTAN)
        {
            distance += SimilarityMeasurementModule.manhattanDistance(first,second);
        }
        else if (distanceType == CHISQUARE)
        {
            distance += SimilarityMeasurementModule.chiSquareDistance(first,second);
        }
        else
            return 0;


        return distance;
    }

    public static ArrayList<ImageInformation> getSimilarImagesESS(String path, int distanceType)
    {
        Image image = Load.invoke(path);
        double eccentricty = giveEccentrictyESS(image);

        ArrayList<ImageInformation> informations = new ArrayList<ImageInformation>();


        try {
            BufferedReader br = new BufferedReader(new FileReader("FeatureVectors\\Eccentricity\\"+ 1 +".txt"));

            String line;

            while ((line = br.readLine()) != null) {
                String[] information = line.split(",");

                for(int i = 0; i < information.length; ++i)
                {
                    ImageInformation imageInformation = new ImageInformation();
                    String[] inf = information[i].split(" ");
                    imageInformation.setImageName(inf[0]);

                    double distance = giveDistanceESS(eccentricty,Double.parseDouble(inf[1]),distanceType);

                    imageInformation.setDistance(distance);
                    informations.add(imageInformation);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



        Collections.sort(informations, new Comparator<ImageInformation>() {
            @Override
            public int compare(ImageInformation o1, ImageInformation o2) {

                return new Double(o1.getDistance()).compareTo(new Double(o2.getDistance()));
            }
        });


        return informations;
    }

    private static double giveEccentrictyESS(Image image)
    {
        double m20 = calculateMomentESS(image,2,0);
        double m02 = calculateMomentESS(image,0,2);
        double m11 = calculateMomentESS(image,1,1);

        double result = 0;

        result = (Math.pow(m20-m02,2) + 4*Math.pow(m11,2)) / Math.pow(m20+m02,2);

        return result;
    }
    public static   void writeToFileESS()
    {
        File folder = new File("C:\\Users\\muham\\Desktop\\School Files\\Forth Year\\Image Processing\\Lab Zipler\\lab3\\mpeg7shapeB\\");
        File[] listOfFiles = folder.listFiles();
        try {
            Writer writer = new FileWriter("C:\\Users\\muham\\IdeaProjects\\untitled\\FeatureVectors\\Eccentricity\\" + 1 + ".txt");
            for(int i = 0; i < listOfFiles.length; ++i)
            {
                System.out.println(listOfFiles[i].getName() + " " + listOfFiles.length);




                    Image image = Load.invoke(listOfFiles[i].getAbsolutePath());
                    double distace = giveEccentrictyESS(image);
                    writer.write(listOfFiles[i].getName() + " " + distace + ",");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void main(String[] args)
    {
        writeToFileESS();
    }




}
