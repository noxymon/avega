package web.id.noxymon.averageimage.processes;

import org.springframework.stereotype.Service;
import web.id.noxymon.averageimage.utils.Constant;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ColorProcess {

    public Map<String, Double> averageColor(File file){
        Map<String, Double> averageColor = new HashMap<>();
        try {
            BufferedImage imageFile = ImageIO.read(file);
            int sumRed = 0;
            int sumGreen = 0;
            int sumBlue = 0;
            for (int i = 0; i < imageFile.getWidth(); i++) {
                for (int j = 0; j < imageFile.getHeight(); j++) {
                    int pixelColor = imageFile.getRGB(i,j);
                    sumRed += getRed(pixelColor);
                    sumGreen += getGreen(pixelColor);
                    sumBlue += getBlue(pixelColor);
                }
            }

            int imageArea = imageFile.getHeight()*imageFile.getWidth();
            double avgRed = sumRed / imageArea;
            double avgGreen = sumGreen / imageArea;
            double avgBlue = sumBlue / imageArea;

            averageColor.put(Constant.Color.RED_PROPERTY_VALUE, avgRed);
            averageColor.put(Constant.Color.GREEN_PROPERTY_VALUE, avgGreen);
            averageColor.put(Constant.Color.BLUE_PROPERTY_VALUE, avgBlue);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return averageColor;
    }

    private int getRed(int pixel){
        return (pixel >> 16) & 0xff;
    }

    private int getGreen(int pixel){
        return (pixel >> 8) & 0xff;
    }

    private int getBlue(int pixel){
        return (pixel) & 0xff;
    }

    private int getAlpha(int pixel){
        return (pixel >> 24) & 0xff;
    }

}
