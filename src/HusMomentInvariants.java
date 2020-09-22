import vpt.Image;
import vpt.algorithms.io.Load;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//source http://www.wseas.us/e-library/conferences/2013/CambridgeUK/AISE/AISE-15.pdf
public class HusMomentInvariants {
    private static final int EUCLIDEAN = 1;
    private static final int MANHATTAN = 2;
    private static final int CHISQUARE = 3;

    private static double calculateMomentHUS(Image image, double p, double q)
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
    private static double calculateCentralMomentHUS(Image image, double p, double q)
    {
        double centralMoment = 0;
        double xAvg = calculateMomentHUS(image,1,0) / calculateMomentHUS(image,0,0);
        double yAvg = calculateMomentHUS(image,0,1) / calculateMomentHUS(image,0,0);

        for(int x = 0; x < image.getXDim(); ++x)
        {
            for(int y = 0; y < image.getYDim(); ++y)
            {
                if(image.getXYByte(x,y) >  150)
                {
                    centralMoment += Math.pow((x-xAvg),p) * Math.pow((y-yAvg),q);
                }
            }
        }
        return centralMoment;
    }

    private static double calculateNormalizedMomentsHUS(Image image,double p, double q)
    {
        double normalizedMoments = 0;
        normalizedMoments = calculateCentralMomentHUS(image,p,q) / (Math.pow(calculateMomentHUS(image,0,0),((p+q)/2)+1));

        return normalizedMoments;
    }

    private static double[] getSevenHuMomentsHUS(Image image)
    {
        double[] huMoments = new  double[7];
        double hu11 = calculateNormalizedMomentsHUS(image,1,1);
        double hu02 = calculateNormalizedMomentsHUS(image,0,2);
        double hu20 = calculateNormalizedMomentsHUS(image,2,0);
        double hu12 = calculateNormalizedMomentsHUS(image,1,2);
        double hu21 = calculateNormalizedMomentsHUS(image,2,1);
        double hu30 = calculateNormalizedMomentsHUS(image,3,0);
        double hu03 = calculateNormalizedMomentsHUS(image,0,3);

        huMoments[0] = hu20 + hu02;
        huMoments[1] = Math.pow((hu20 - hu02), 2) + 4 * Math.pow(hu11, 2);

        huMoments[2] =  Math.pow((hu30 - 3*hu12),2) + Math.pow((3*hu21 - hu03),2);

        huMoments[3] = Math.pow(hu30+hu12, 2) + Math.pow(hu21 + hu03, 2);

        huMoments[4] = ( hu30 - 3 * hu12) * ( hu30 + hu12) * ( Math.pow(hu30 + hu12,2 ) - 3 * Math.pow(( hu21 + hu03 ), 2 )) +
                        (3*hu21 - hu03) * (hu21 * hu03) * (3*Math.pow(hu30+hu12,2) - Math.pow(hu21+hu03,2));
        huMoments[5] = (hu20 - hu02) * (Math.pow(hu30+hu12,2) - Math.pow(hu21 + hu03,2)) + 4 * hu11 * (hu30 + hu12) * (hu21 + hu03);

        huMoments[6] = (3*hu21 - hu03) * (hu21 + hu03) * (3* Math.pow(hu30 + hu12, 2) - Math.pow(hu21+hu03,2)) -
                (hu30 - 3*hu12) * (hu21 + hu03) * (3 * Math.pow(hu30 + hu12, 2) - Math.pow(hu21 + hu03,2));



        return huMoments;

    }

    private static double giveDistance2HUS(Image img1, Image img2)
    {
        double[] huMoments1 = getSevenHuMomentsHUS(img1);
        double[] huMoments2 = getSevenHuMomentsHUS(img2);

        double distance = 0 ;

        for(int i = 0; i < huMoments1.length; ++i)
        {
            distance += Math.sqrt( Math.pow(huMoments1[i] - huMoments2[i], 2) );
        }
        return distance;
    }

    private static double giveDistanceHUS(double[] huMoments1,double[] huMoments2, int distanceType)
    {

        double distance = 0 ;
        for(int i = 0; i < huMoments1.length; ++i)
        {

            if(distanceType == EUCLIDEAN)
            {
                distance += SimilarityMeasurementModule.euclideanDistance(huMoments1[i],huMoments2[i]);
            }
            else if(distanceType == MANHATTAN)
            {
                distance += SimilarityMeasurementModule.manhattanDistance(huMoments1[i],huMoments2[i]);
            }
            else if (distanceType == CHISQUARE)
            {
                distance += SimilarityMeasurementModule.chiSquareDistance(huMoments1[i],huMoments2[i]);
            }
            else
                return 0;
        }

        return distance;
    }

    public static ArrayList<ImageInformation> getSimilarImagesHUS(String path, int distanceType)
    {
        Image image = Load.invoke(path);
        double []huMoments1 = getSevenHuMomentsHUS(image);

        ArrayList<ImageInformation> informations = new ArrayList<ImageInformation>();

        for(int i = 0; i < 1400; i++)
        {
            try {
                BufferedReader br = new BufferedReader(new FileReader("FeatureVectors\\HusMoment\\"+(i+1)+".txt"));

                String line;

                while ((line = br.readLine()) != null) {
                    ImageInformation imageInformation = new ImageInformation();

                    String[] information = line.split(" ");
                    imageInformation.setImageName(information[0]);
                    double[] huMoments2 = new double[7];
                    for(int j = 0; j < huMoments1.length; ++j)
                        huMoments2[j] = Double.parseDouble(information[j+1]);
                    imageInformation.setDistance(giveDistanceHUS(huMoments1,huMoments2,distanceType));

                    informations.add(imageInformation);
                }




            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        Collections.sort(informations, new Comparator<ImageInformation>() {
            @Override
            public int compare(ImageInformation o1, ImageInformation o2) {

                return new Double(o1.getDistance()).compareTo(new Double(o2.getDistance()));
            }
        });



        return  informations;
    }



}
