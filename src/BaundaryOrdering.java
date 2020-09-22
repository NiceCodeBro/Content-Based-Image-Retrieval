import vpt.Image;

import java.util.ArrayList;

public class BaundaryOrdering {


    public static ArrayList<Point> giveOrderedBoundaries(Image img)
    {
        Image newImg = BoundaryDetermination.giveBoundaryOfImage(img);
        ArrayList<Point> orderBoundaries = orderBoundaries(getBoundaries(newImg));

        return orderBoundaries;
    }
    private static ArrayList<Point> orderBoundaries(ArrayList<Point> unorderedBoundaries)
    {
        ArrayList<Point> orderedBoundaries = new ArrayList<Point>();


        orderedBoundaries.add(unorderedBoundaries.get(0));
        unorderedBoundaries.remove(0);


        while (unorderedBoundaries.size() > 0)
        {
            int nearestIndex = 0;
            double minDistance = Double.MAX_VALUE;
            for(int i = 0; i < unorderedBoundaries.size(); ++i)
            {
                Double distance = SimilarityMeasurementModule.euclideanDistanceForPoint(orderedBoundaries.get(orderedBoundaries.size()-1),unorderedBoundaries.get(i));
                if(distance < minDistance)
                {
                    minDistance = distance;
                    nearestIndex = i;
                }
            }
            orderedBoundaries.add(unorderedBoundaries.get(nearestIndex));
            unorderedBoundaries.remove(nearestIndex);

        }
        return  orderedBoundaries;

    }


    private static ArrayList<Point> getBoundaries(Image image)
    {
        ArrayList<Point> boundaries = new ArrayList<Point>();
        for(int i = 0; i < image.getXDim(); ++i)
        {
            for(int j = 0; j < image.getYDim(); ++j)
            {
                if(image.getXYByte(i,j) == 255)
                {
                    boundaries.add(new Point(i,j));
                }
            }
        }
        return boundaries;
    }
}
