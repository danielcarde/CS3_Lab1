import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.image.DataBufferByte;
 
public class image {
    public static float[] getVector(String imageName) {
        try {
            BufferedImage image = ImageIO.read(new File(imageName));
            byte[] raw = get1DPixelArray(image);

            float[] vector = new float[raw.length];
            for (int i = 0; i < raw.length; i++) {
                vector[i] = (raw[i] & 0xFF) / 255.0f; // convert to 0.0–1.0
            }
            return vector;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] get1DPixelArray(BufferedImage image) {
        return ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    }
}
