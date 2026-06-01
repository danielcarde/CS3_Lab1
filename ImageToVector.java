import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageToVector {

    public static float[] getVector(String imageName) {
        try {
            // Step 1: Load image
            BufferedImage image = ImageIO.read(new File(imageName));

            int width = image.getWidth();
            int height = image.getHeight();

            // Step 2: Create a vector (1D array) to store pixel intensities
            float[] vector = new float[width * height];

            // Step 3: Loop through every pixel
            int index = 0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = image.getRGB(x, y);

                    // Extract red, green, blue components
                    int red   = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue  = rgb & 0xFF;

                    // Convert to grayscale (average method)
                    int gray = (red + green + blue) / 3;

                    // Normalize (0.0 to 1.0)
                    vector[index++] = gray / 255.0f;
                }
            }
            //print length
            return vector;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
