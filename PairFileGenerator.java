import java.io.*;

public class PairFileGenerator {
    public static void main(String[] args) throws IOException {
        String[] images = {"dog.jpg", "cat.jpg", "wolf.jpg"};
        BufferedWriter writer = new BufferedWriter(new FileWriter("image_pairs.txt"));

        for (int i = 0; i < images.length; i++) {
            for (int j = i + 1; j < images.length; j++) {
                writer.write(images[i] + " " + images[j]);
                writer.newLine();
            }
        }
        writer.close();
        System.out.println("image_pairs.txt created!");
    }
}
