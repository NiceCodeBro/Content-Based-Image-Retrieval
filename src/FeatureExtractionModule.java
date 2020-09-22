import java.util.ArrayList;

public class FeatureExtractionModule {
    public static ArrayList<ImageInformation> getResultByHUSMoments(String path, int distanceType)
    {
        return HusMomentInvariants.getSimilarImagesHUS(path,distanceType);
    }
    public static ArrayList<ImageInformation> getResultByFourierTransform(String path, int distanceType)
    {
        return FourierCoefficents.getSimilarImagesFT(path,distanceType);
    }
    public static ArrayList<ImageInformation> getResultByEccentricity(String path, int distanceType)
    {
        return EccentricityScaleSpaces.getSimilarImagesESS(path,distanceType);
    }

}
