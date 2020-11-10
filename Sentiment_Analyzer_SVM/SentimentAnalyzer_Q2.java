import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.feature.HashingTF;
import scala.Tuple2;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.classification.SVMWithSGD;
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics;
import org.apache.spark.mllib.regression.LabeledPoint;
import java.util.Arrays;
import java.util.List;

public class SentimentAnalyzer_Q2 {

    private static final String SPACE = " ";
    private static final String TAB = "\t";

    public static void main(String args[]) {
        /*
          Solution for question 2 part a and b of Assignment 3
         */

        // Setup parallelism, hadoop directory and memory allocation limits
        System.setProperty("hadoop.home.dir", "C:/winutils");
        SparkConf sparkConf = new SparkConf()
                .setAppName("Sentiment Analysis using SVM")
                .setMaster("local[4]").set("spark.executor.memory", "1g");

        // Initialize JavaSparkContext
        JavaSparkContext ctx = new JavaSparkContext(sparkConf);

        // Note : The database can be downloaded from https://archive.ics.uci.edu/ml/machine-learning-databases/00331/sentiment%20labelled%20sentences.zip -> imdb_labelled.txt
        // Storing imdb dataset line by line in JavaRDDs
        JavaRDD<String> lines = ctx.textFile("D:\\NUIG\\LargeScaleDA-Sem1_DrMatthiasNickles&DrEdCurryCT5105\\Week7\\sentiment labelled sentences\\sentiment labelled sentences\\imdb_labelled.txt");

        // Using HashingTF to create numerical feature vectors from string-form features
        final HashingTF tf = new HashingTF();

        JavaRDD<LabeledPoint> data = lines.map(line -> {
            String[] tokens = line.split(TAB); // Split each line first by TAB to capture text and user's sentiment ( 1 = positive sentiment ) ( 0 = negative sentiment )
            return new LabeledPoint(Integer.parseInt(tokens[1]), tf.transform(Arrays.asList(tokens[0].split(SPACE)))); // Further split each text by space to capture each word of user and transform using HashingTF
        });

        // Split RDD into two parts [60% training data, 40% testing data] according to Machine learning standard
        JavaRDD<LabeledPoint> training = data.sample(false, 0.6, 11L);

        // Cache training set at it will be iterated 1000 time for faster processing
        training.cache();

        // Create test set which is total set - training set
        JavaRDD<LabeledPoint> test = data.subtract(training);

        // Run training algorithm to build the model.
        int numIterations = 1000;

        // Train SVM model using our training dataset
        SVMModel model = SVMWithSGD.train(training.rdd(), numIterations);

        // Clear the default threshold.
        model.clearThreshold();

        // Compute raw scores on the test set.
        JavaRDD<Tuple2<Object, Object>> scoreAndLabels = test.map(p ->new Tuple2<>(model.predict(p.features()), p.label()));

        // Definition  of score of MLLib SVM : https://stackoverflow.com/questions/30029863/what-does-the-score-of-the-spark-mllib-svm-output-mea
        System.out.println("The score of SVM MLLIb represents the margin ie. distance to separating hyperplane");
        List<Tuple2<Object, Object>> testData = scoreAndLabels.collect();
        for(int i=0;i<10;i++){
            Tuple2<Object, Object> xc=testData.get(i);
            System.out.println("Test data number "+i+", score : "+xc._1 +"actual test label : "+xc._2+" predicted svm label : "+Util.getPredictedClassSVM(xc._1));
        }

        // Solution for part b of question 2
        BinaryClassificationMetrics metrics = new BinaryClassificationMetrics(JavaRDD.toRDD(scoreAndLabels));
        double auROC = metrics.areaUnderROC();
        System.out.println("Area under ROC Curve Score for imdb dataset and SVM Model is  " + auROC);

        // Close connections with spark
        ctx.stop();
        ctx.close();
    }
}

