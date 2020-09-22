import vpt.ByteImage;
import vpt.Image;


public class BoundaryDetermination {




    public static Image giveBoundaryOfImage(Image image)
    {
        Image img = AddBorderToImage.addBorder(image);
        //Image img = Load.invoke("C:\\Users\\muham\\Desktop\\School Files\\Forth Year\\Image Processing\\Lab Zipler\\lab3\\mpeg7shapeB\\camel-12.png");
        Image tempImage = new ByteImage(img.getXDim(),img.getYDim());
        Image tempImage2 = new ByteImage(img.getXDim(),img.getYDim());

        Image boundImg = new ByteImage(img.getXDim(),img.getYDim());

        for(int i = 0; i < img.getYDim(); ++i)
        {
            boolean flag = false;
            for(int j = 0; j < img.getXDim(); ++j)
            {
                if(flag == false & img.getXYByte(j,i) > 200)
                {
                    flag = true;
                    tempImage.setXYByte(j,i,255);
                }
                else if(flag == true && img.getXYByte(j,i) < 200)
                {
                    flag = false;
                    try {
                        tempImage.setXYByte(j,i-1,255);

                    }catch (Exception e)
                    {                    }
                }
                else
                    tempImage.setXYByte(j,i,0);
            }
        }

        for(int i = 0; i < img.getXDim(); ++i)
        {
            boolean flag = false;
            for(int j = 0; j < img.getYDim(); ++j)
            {
                if(flag == false & img.getXYByte(i,j) > 200)
                {
                    flag = true;
                    tempImage2.setXYByte(i,j,255);
                }
                else if(flag == true && img.getXYByte(i,j) < 200)
                {
                    flag = false;
                    tempImage2.setXYByte(i,j-1,255);
                }
                else
                    tempImage2.setXYByte(i,j,0);
            }
        }

        for(int i = 0; i < boundImg.getXDim(); ++i)
        {
            for(int j = 0; j < boundImg.getYDim(); ++j)
            {
                if(tempImage.getXYByte(i,j) > 200 || tempImage2.getXYByte(i,j) > 200)
                {
                    boundImg.setXYByte(i,j,255);
                }
                else
                    boundImg.setXYByte(i,j,0);
            }
        }
        return  boundImg;
    }
}
