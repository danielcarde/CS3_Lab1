import java.io.*;
import java.util.*;

public class FeatureFileGenerator {
    public static void main(String[] args) throws IOException {
        String[] images = {"dog.jpg", "cat.jpg", "wolf.jpg"};  // your images
        BufferedWriter writer = new BufferedWriter(new FileWriter("image_features.txt"));

        for (String imageName : images) {
            float[] vector = ImageToVector.getVector(imageName);
            if (vector == null) {
                System.out.println("Skipping " + imageName + " (failed to load)");
                continue;
            }
            writer.write(imageName);
            for (float f : vector) {
                writer.write(" " + f);
            }
            writer.newLine();
        }
        writer.close();
        System.out.println("image_features.txt created!");
    }
}
