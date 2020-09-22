import java.util.Comparator;

public class ImageInformation   {
    private String imageName="";
    private String imageAbsPath="a";
    private double distance = 0;

    public ImageInformation()
    {}




    public ImageInformation(String imageName, String imageAbsPath) {
        this.imageName = imageName;
        this.imageAbsPath = imageAbsPath;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageAbsPath() {
        return imageAbsPath;
    }

    public void setImageAbsPath(String imageAbsPath) {
        this.imageAbsPath = imageAbsPath;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

}
