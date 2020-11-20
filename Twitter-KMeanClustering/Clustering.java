import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.clustering.KMeans;
import java.nio.file.Paths;
import java.util.HashMap;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.lang.*;

public class Clustering {

    private static final String COMMA = ","; //  Used for scrapping commas separated values
    private static final String FILENAME ="D:\\NUIG\\LargeScaleDA-Sem1_DrMatthiasNickles&DrEdCurryCT5105\\Week8\\twitter2D.txt";

    public static void main(String args[]) throws IOException {
        /*
        References: https://www.tutorialkart.com/apache-spark/kmeans-classification-using-spark-mllib-in-java/
                    https://spark.apache.org/docs/latest/mllib-clustering.html
         */

        // Setup parallelism, hadoop directory and memory allocation limits
        System.setProperty("hadoop.home.dir", "C:/winutils");
        SparkConf sparkConf = new SparkConf()
                .setAppName("Sentiment Analysis using SVM")
                .setMaster("local[4]").set("spark.executor.memory", "1g");

        // Initialize JavaSparkContext
        JavaSparkContext ctx = new JavaSparkContext(sparkConf);

        // Format of twitter dataset : coordinate_1, coordinate_2, timestamp, user_id, optional flag 1=spam/0=no spam , tweet message
        JavaRDD<String> lines = ctx.textFile(FILENAME);

        JavaRDD<Vector> parsedData=lines.map(
                (String s)->{
                    String[] content =s.split(COMMA);
                    double[] values = new double[2];
                    for (int i = 0; i < 2; i++) {
                        values[i] = Double.parseDouble(content[i]); // Only coordinate_1 and coordinate_2 are considered for clustering
                    }
                    return Vectors.dense(values);
                }
        );

        // Cache parsed dataset as it will be iterated multiple time for faster processing
        parsedData.cache();

        int numClusters = 4; // Number of clusters for KMeans Clustering
        int numIterations = 20; // Number of Iterations

        // Train KMeans Clustering algorithm on our parsed dataset
        KMeansModel clusters = KMeans.train(parsedData.rdd(), numClusters, numIterations);

        System.out.println("Cluster centers:");
        for (Vector center: clusters.clusterCenters()) {
            System.out.println(" " + center);
        }

        // Evaluate clustering cost
        double cost = clusters.computeCost(parsedData.rdd());
        System.out.println("Cost: " + cost);
        /*
         Sample result cost on various k ( number of iterations = 20): ( Might vary due to random nature of algorithm)
         K = 4 Cost: 21733.15718254337
         K = 5 Cost: 10983.373432841141
         K = 3 Cost: 27846.739864667565
         K = 2 Cost: 60171.788758729264
         */

        // Evaluate clustering by computing Within Set Sum of Squared Errors
        double WSSSE = clusters.computeCost(parsedData.rdd());
        System.out.println("Sum of Squared Errors : " + WSSSE);

        // Hashmap to store Tweets and there respective clusters
        HashMap<String,Integer> tweets =  new HashMap<String,Integer>();

        Files.lines(Paths.get(FILENAME))
                .map(l->l.split(COMMA)) // Split line based on commas
                .forEach(
                        a->{
                            Double coordinate1 = Double.parseDouble(a[0]); // Coordinate 1
                            Double coordinate2 = Double.parseDouble(a[1]); // Coordinate 2
                            int prediction = clusters.predict(Vectors.dense(coordinate1,coordinate2)); // Predicted cluster according to model
                            tweets.put(a[a.length-1],prediction); // Insert actual tweet and its predicted cluster number in map
                        }
                );

        Map<String, Integer> hm1 = sortByValue(tweets); // Sort based on cluster number (value)
        hm1.entrySet().forEach(entry->{
            System.out.println("Tweet : \""+ entry.getKey() + "\" is in cluster " + entry.getValue());
        });

        // Prediction for test vectors - Uncomment and write your own coordinate to find cluster number
        // System.out.println("-56.544541,-29.089541 belongs to "+clusters.predict(Vectors.dense(-56.544541,-29.089541)));
        // System.out.println("69.922686,18.462675 belongs to "+clusters.predict(Vectors.dense(69.922686,18.462675)));
    }

    // function to sort hashmap by values - Reference : https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
