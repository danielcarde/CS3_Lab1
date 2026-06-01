import java.io.*;
import java.util.*;


public class lab1Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        boolean exit=false;
        while(!exit){
            mainMenu();
            int choice = input.nextInt();
            switch(choice){
                case 1:
                    System.out.println();
                    System.out.println("Hash Table Selected");
                    hashTableChosen();
                    break;
                case 2:
                    System.out.println();
                    System.out.println("B-Tree Selected");
                    System.out.print("Please chose maximum number of items per node: ");
                    int m = input.nextInt();
                    bTreeChosen(m);
                    break;
                case 3:
                    exit = true;
					System.out.println("Ending program.");
                    break;
                default:
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Error: Invalid Input");
					input.nextLine();
					System.out.println();
            }
        }
    }
    
    public static void mainMenu(){
        System.out.println("----------Image Feature Similarity----------");
        System.out.println("-------------- (1) Hash table --------------");
        System.out.println("--------------   (2) B-Tree   --------------");
        System.out.println("--------------    (3) Exit    --------------");
        System.out.println("--------------------------------------------");
        System.out.print("              Chose Option:");
    }

    public static void hashTableChosen() {
        try {
            long buildStart = System.nanoTime(); //start of making of hash

            BufferedReader br = new BufferedReader(new FileReader("image_features.txt"));
            HashTable ht = new HashTable(100);
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");//new item every space
                String name = parts[0];//name will be the first item
                float[] vector = new float[parts.length - 1];//the rest will be part of the vector
                for (int i = 1; i < parts.length; i++) {
                    vector[i - 1] = Float.parseFloat(parts[i]);//will make it into a float
                }
                ht.insert(new ImageFeature(name, vector));//put the new image w/ feature in the hash table
            }
            br.close();

            long buildEnd = System.nanoTime(); // <end of making hash
            double buildTimeMillis = (buildEnd - buildStart) / 1_000_000.0; //math ot determin total time
            System.out.println("Hash Table build time: " + buildTimeMillis + " ms");

            long queryStart = System.nanoTime(); // start of comparing pairs

            br = new BufferedReader(new FileReader("image_pairs.txt"));
            while ((line = br.readLine()) != null) {
                String[] pair = line.split(" ");//makes the pairs based on the files
                ImageFeature f1 = ht.returnFeature(pair[0]);//pair 1
                ImageFeature f2 = ht.returnFeature(pair[1]);// pair 2
                if (f1 != null && f2 != null) {//compare using cosine
                    float similarity = cosineSimilarity(f1.vector, f2.vector);
                    System.out.println(pair[0] + "<->" + pair[1] + ": " + Math.round(similarity * 100.0f) / 100.0f);
                }
            }
            br.close();

            long queryEnd = System.nanoTime(); // end of comparing pairs
            double queryTimeMillis = (queryEnd - queryStart) / 1_000_000.0; //math calc time
            System.out.println("Hash Table query time: " + queryTimeMillis + " ms");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


   public static void bTreeChosen(int maxItems) {
        try {
            long buildStart = System.nanoTime(); //start of making of btree

            BufferedReader br = new BufferedReader(new FileReader("image_features.txt"));
            BTree btree = new BTree(maxItems);
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");//new item every space
                String name = parts[0];//name will be the first item
                float[] vector = new float[parts.length - 1];//the rest will be part of the vector
                for (int i = 1; i < parts.length; i++) {
                    vector[i - 1] = Float.parseFloat(parts[i]);//will make it into a float
                }
                btree.insert(new ImageFeature(name, vector));//put the new image w/ feature in the b tree
            }
            br.close();

            long buildEnd = System.nanoTime(); //end of making of btree
            double buildTimeMillis = (buildEnd - buildStart) / 1_000_000.0; //math
            System.out.println("B-Tree build time: " + buildTimeMillis + " ms");

            long queryStart = System.nanoTime(); // start of comparing pairs 

            br = new BufferedReader(new FileReader("image_pairs.txt"));
            while ((line = br.readLine()) != null) {
                String[] pair = line.split(" ");//makes the pairs based on the file
                ImageFeature f1 = btree.returnFeature(pair[0]);//pair 1
                ImageFeature f2 = btree.returnFeature(pair[1]);//pair 2
                if (f1 != null && f2 != null) {//compare using the cosine similarity
                    float similarity = cosineSimilarity(f1.vector, f2.vector);
                    System.out.println(pair[0] + "<->" + pair[1] + ": " + Math.round(similarity * 100.0f) / 100.0f);
                }
            }
            br.close();

            long queryEnd = System.nanoTime(); // end of comparing pairs
            double queryTimeMillis = (queryEnd - queryStart) / 1_000_000.0; //math
            System.out.println("B-Tree query time: " + queryTimeMillis + " ms");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public static float cosineSimilarity(float[] a, float[] b) {
            if (a == null || b == null) return 0f;
            int len = Math.min(a.length, b.length);
            float dot=0, sumA=0, sumB=0;
            for (int i=0; i<len; i++) {
                dot += a[i]*b[i];
                sumA += a[i]*a[i];
                sumB += b[i]*b[i];
            }
            if (sumA == 0 || sumB == 0) return 0f;
            return dot/(float)(Math.sqrt(sumA)*Math.sqrt(sumB));
        }
}
