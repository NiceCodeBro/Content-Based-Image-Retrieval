import vpt.BooleanImage;
import vpt.Image;


public class AddBorderToImage {
    public static Image addBorder(Image image)
    {
        Image newImage = new BooleanImage(image.getXDim()+4,image.getYDim()+4);
        for(int i = 0; i < image.getXDim(); ++i)
        {
            for(int j = 0; j < image.getYDim(); ++j)
            {
                newImage.setXYByte(i+2,j+2,image.getXYByte(i,j));
            }
        }
        return newImage;
    }
}
