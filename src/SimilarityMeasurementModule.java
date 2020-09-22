public class SimilarityMeasurementModule {
    public static double euclideanDistanceForComplexNumbers(Point first, Point second) {

        return Math.sqrt(Math.pow(first.getX() - second.getX(), 2) + Math.pow(first.getY() - second.getY(), 2));
    }

    public static double euclideanDistanceForPoint(Point first, Point second) {

        return Math.sqrt(Math.pow(first.getX() - second.getX() , 2) + Math.pow(first.getY() - second.getY() , 2));
    }
    public static double manhattanDistanceForComplexNumbers(Point first, Point second) {
        Double xDiff = first.getX() - second.getX();
        Double yDiff = first.getY() - second.getY();
        return Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));


    }

    public static double chiSquareDistanceForComplexNumbers(Point first, Point second) {
        Double realDiff = first.getX() - second.getX();
        Double imagDiff = first.getY() - second.getY();

        Double readTotal = first.getX() + second.getX();
        Double imagTotal = first.getY() + second.getY();

        return (Math.pow(realDiff, 2) + Math.pow(imagDiff, 2)) /
                Math.sqrt((Math.pow(readTotal, 2) + Math.pow(imagTotal, 2)));

    }


    public static double euclideanDistance(Double first, Double second) {
        double distance = 0;

        distance = Math.sqrt(Math.pow(first - second, 2));

        return distance;
    }

    public static double manhattanDistance(Double first, Double second) {
        return Math.abs(first - second);
    }

    public static double chiSquareDistance(Double first, Double second) {
        Double diff = first - second;

        Double total = first + second;

        return Math.pow(diff, 2) / Math.sqrt(Math.pow(total, 2));

    }
}