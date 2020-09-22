import vpt.Image;
import vpt.algorithms.io.Load;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FourierCoefficents  {
    private static final int EUCLIDEAN = 1;
    private static final int MANHATTAN = 2;
    private static final int CHISQUARE = 3;

    //Fourier formula description source: https://www.youtube.com/watch?v=mkGsMWi_j4Q
    private static ArrayList<Point> calculate1DFT(ArrayList<Point> orderedBoundaries)
    {
        int totalSize = orderedBoundaries.size();
        ArrayList<Point> coefficents = new ArrayList<Point>();
        for(int i = 0; i < totalSize; ++i)
            coefficents.add(new Point(orderedBoundaries.get(i).getX(),orderedBoundaries.get(i).getY()));

        ArrayList<Point> fourierCoefficents = new ArrayList<Point>();

        for(int i = 0; i < totalSize; ++i)
        {
            double sumReal = 0;
            double sumImaginary = 0;
            for(int j = 0; j < totalSize ; ++j)
            {
                Double eulersX = ( -1 * Math.PI * 2 * i * j ) / totalSize; // that is represent x in eular formula ( e^(jx) = cosx + j sinx )
                sumReal += ( coefficents.get(j).getX() * Math.cos(eulersX) +  coefficents.get(j).getY() * Math.sin(eulersX) );
                sumImaginary += (  coefficents.get(j).getY() * Math.cos(eulersX)  - coefficents.get(j).getX() * Math.sin(eulersX)  );
            }
            sumReal = sumReal / Math.sqrt(totalSize);
            sumImaginary = sumImaginary / Math.sqrt(totalSize);

            fourierCoefficents.add(new Point(sumReal,sumImaginary));
        }

        return fourierCoefficents;
    }


    private static double giveDistanceFT(ArrayList<Point> targetCoef, ArrayList<Point> coef2, int distanceType)
    {

        double distance = 0 ;
        int size = targetCoef.size();

        if(targetCoef.size() > coef2.size())
            size = coef2.size();

        for(int i = 0; i < size; ++i)
        {
            Point first = targetCoef.get(i);
            Point second = coef2.get(i);
            if(distanceType == EUCLIDEAN)
            {
                distance += SimilarityMeasurementModule.euclideanDistanceForComplexNumbers(first,second);
            }
            else if(distanceType == MANHATTAN)
            {
                distance += SimilarityMeasurementModule.manhattanDistanceForComplexNumbers(first,second);
            }
            else if (distanceType == CHISQUARE)
            {
                distance += SimilarityMeasurementModule.chiSquareDistanceForComplexNumbers(first,second);
            }
            else
                return 0;
        }

        return distance;


    }


    private static void writeToFileFT()
    {
        File folder = new File("C:\\Users\\muham\\Desktop\\School Files\\Forth Year\\Image Processing\\Lab Zipler\\lab3\\mpeg7shapeB\\");
        File[] listOfFiles = folder.listFiles();

        for(int i = 0; i < 1400; ++i)
        {
            try {
                Writer writer = new FileWriter("C:\\Users\\muham\\IdeaProjects\\untitled\\FeatureVectors\\Fourier\\" + (i+1) + ".txt");
                Image image = Load.invoke(listOfFiles[i].getAbsolutePath());
                ArrayList<Point> coef  = calculate1DFT( BaundaryOrdering.giveOrderedBoundaries( BoundaryDetermination.giveBoundaryOfImage(image) ));
                writer.write(listOfFiles[i].getName() + " ");
                for(int j = 0; j  < coef.size(); ++j)
                {
                    writer.write(coef.get(j).getX() + "," + coef.get(j).getY() + " ");
                }
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();

            }
        }

    }



    public static ArrayList<ImageInformation> getSimilarImagesFT(String path, int distanceType)
    {
        Image image = Load.invoke(path);
        ArrayList<Point> coef1  = calculate1DFT( BaundaryOrdering.giveOrderedBoundaries( BoundaryDetermination.giveBoundaryOfImage(image) ));

        ArrayList<ImageInformation> informations = new ArrayList<ImageInformation>();

        for(int i = 0; i < 1400; i++)
        {

            try {
                BufferedReader br = new BufferedReader(new FileReader("FeatureVectors\\Fourier\\"+(i+1)+".txt"));

                String line;

                while ((line = br.readLine()) != null)
                {
                    ImageInformation imageInformation = new ImageInformation();

                    String[] information = line.split(" ");
                    imageInformation.setImageName(information[0]);
                    ArrayList<Point> coef2 = new ArrayList<Point>();
                    for(int j = 1; j < information.length; ++j)
                    {
                        String[] temp = information[j].split(",");
                        coef2.add(new Point(Double.parseDouble(temp[0]), Double.parseDouble(temp[1]) ));
                    }
                    imageInformation.setDistance(giveDistanceFT(coef1,coef2,distanceType));
                    informations.add(imageInformation);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        Collections.sort(informations, new Comparator<ImageInformation>() {
            @Override
            public int compare(ImageInformation o1, ImageInformation o2) {

                return new Double(o2.getDistance()).compareTo(new Double(o1.getDistance()));
            }
        });





        return  informations;

    }

}
