package Test;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Random;

import DilcaDistance.DilcaDistanceContTableMean;
import DilcaDistance.DilcaDistanceDiffPrivMeanWithFinalDistance;
import net.javaguides.maven_web_project.DilcaDistanceDiffPriv.BalloonNMISoloDilcaTestSuClasseDataset;
import smile.validation.AdjustedRandIndex;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class TestMancanti {
	
	
	public static void mushroomAriMatriciInizialiMean() throws Exception {
		Instances cpu = null;
		DataSource source = new DataSource("/home/Celano/Desktop/DilcaTestSophia/src/main/java/Test/mushroom.arff");
		Instances data = source.getDataSet();
		data.setClassIndex(data.numAttributes()-1);
		Remove filter = new Remove();
		filter.setAttributeIndices(""+(data.classIndex()+1));
		filter.setInputFormat(data);
		cpu = Filter.useFilter(data, filter);
		
	    String s = "/home/Celano/Desktop/DilcaTestSophia/src/main/java/Test/mushroom.arff";
	    int[] classe = new int[0];
	    classe = BalloonNMISoloDilcaTestSuClasseDataset.loadArffClasse(s);
	    double epsilon = 0.0;
	    Random rand =new Random();
	    double sigma = 0.0;
	    for(int sig = 0; sig<10;sig++) {
	    	sigma = sigma + 0.1;
	    	epsilon = 0.0;
	    	FileWriter writer1 = new FileWriter("home/Celano/test_mushroom_ari_matrini_mean/MushroomMatrIniMean1Sigma"+sigma+"ARI.txt", true);
	    	for(int ciclo = 1; ciclo<10; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceContTableMean dd = new DilcaDistanceContTableMean(epsilon/(binomialCoeff(cpu.numAttributes(), 2)),sigma, rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("home/Celano/Desktop/DilcaTestSophia/src/main/java/Test/mushroom.arff");
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
				AdjustedRandIndex nnn = new AdjustedRandIndex();
				double value = nnn.measure(classe, classCluster);
				corr = corr+value;
				counter++;
			}
			double result = (double) corr/counter;
			writer1.write("Epsilon: "+epsilon+", sigma:"+sigma+ ", ARI: "+result+";   ");		
		}
			writer1.close();
	    }
		
	    
	    sigma = 0.0;
		epsilon = 0.5;
		 for(int sig = 0; sig<10;sig++) {
	        	sigma = sigma + 0.1;
	        	epsilon = 0.5;
				FileWriter writer2 = new FileWriter("home/Celano/test_mushroom_ari_matrini_mean/MushroomMatrIniMean2Sigma"+sigma+"ARI.txt", true);
				for(int ciclo = 1; ciclo<10; ciclo++) {
					rand.setSeed(11235813);
					epsilon = epsilon+0.5;
					double corr = 0;
					int counter = 0;
					for(int i = 0; i<100;i++) {
						DilcaDistanceContTableMean dd = new DilcaDistanceContTableMean(epsilon/(binomialCoeff(cpu.numAttributes(), 2)),sigma, rand.nextLong());
						System.out.println("*******************************************");
						BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
						BalloonNMISoloDilcaTestSuClasseDataset.loadArff("home/Celano/Desktop/DilcaTestSophia/src/main/java/Test/mushroom.arff");
						int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
						AdjustedRandIndex nnn = new AdjustedRandIndex();
						double value = nnn.measure(classe, classCluster);
						corr = corr+value;
						counter++;
					}
					double result = (double) corr/counter;
					writer2.write("Epsilon: "+epsilon+", sigma:"+sigma+ ", ARI: "+result+";   ");	
				}
				writer2.close();
		 }
	 
	}
	
	
	
	
	public static void mushroomARISUMean() throws Exception {
		Instances cpu = null;
		DataSource source = new DataSource("/home/Celano/Desktop/DilcaTestSophia/src/main/java/Test/mushroom.arff");
		Instances data = source.getDataSet();
		data.setClassIndex(data.numAttributes()-1);
		Remove filter = new Remove();
		filter.setAttributeIndices(""+(data.classIndex()+1));
		filter.setInputFormat(data);
		cpu = Filter.useFilter(data, filter);
	    String s = "/home/Celano/Desktop/DilcaTestSophia/src/main/java/Test/mushroom.arff";
	    int[] classe = new int[0];
	    classe = BalloonNMISoloDilcaTestSuClasseDataset.loadArffClasse(s);
	    double epsilon = 0.0;
	    Random rand =new Random();
	    double sigma = 0.0;
	    for(int sig = 0; sig<10;sig++) {
	    	sigma = sigma + 0.1;
	    	epsilon = 0.0;
	    FileWriter writer1 = new FileWriter("home/Celano/test_mushroom_ari_matrini_mean/MushroomSUFinalDistMean1Sigma"+sigma+"ARI.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<100;i++) {
				DilcaDistanceDiffPrivMeanWithFinalDistance dd = new DilcaDistanceDiffPrivMeanWithFinalDistance((0.5*epsilon/(cpu.numAttributes()+binomialCoeff(cpu.numAttributes(), 2))),(0.5*epsilon/(binomialCoeff(cpu.numAttributes(), 2))),sigma, rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("/home/Celano/Desktop/DilcaTestSophia/src/main/java/Test/mushroom.arff");
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
				AdjustedRandIndex nnn = new AdjustedRandIndex();
				double value = nnn.measure(classe, classCluster);
				corr = corr+value;
				counter++;
			}
			double result = (double) corr/counter;
			writer1.write("Epsilon: "+epsilon+", sigma:"+sigma+ ", ARI: "+result+";   ");		
		}
		writer1.close();
	    }
	    
	    sigma = 0.0;
		epsilon = 0.5;
		 for(int sig = 0; sig<10;sig++) {
	        	sigma = sigma + 0.1;
	        	epsilon = 0.5;
				FileWriter writer2 = new FileWriter("home/Celano/test_mushroom_ari_matrini_mean/MushroomSUFinalDistMean2Sigma"+sigma+"ARI.txt", true);
				for(int ciclo = 1; ciclo<10; ciclo++) {
					rand.setSeed(11235813);
					epsilon = epsilon+0.5;
					double corr = 0;
					int counter = 0;
					for(int i = 0; i<100;i++) {
						DilcaDistanceDiffPrivMeanWithFinalDistance dd = new DilcaDistanceDiffPrivMeanWithFinalDistance((0.5*epsilon/(cpu.numAttributes()+binomialCoeff(cpu.numAttributes(), 2))),(0.5*epsilon/(binomialCoeff(cpu.numAttributes(), 2))),sigma, rand.nextLong());
						System.out.println("*******************************************");
						BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
						BalloonNMISoloDilcaTestSuClasseDataset.loadArff("/home/Celano/Desktop/DilcaTestSophia/src/main/java/Test/mushroom.arff");
						int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
						AdjustedRandIndex nnn = new AdjustedRandIndex();
						double value = nnn.measure(classe, classCluster);
						corr = corr+value;
						counter++;
					}
					double result = (double) corr/counter;
					writer2.write("Epsilon: "+epsilon+", sigma:"+sigma+ ", ARI: "+result+";   ");	
				}
				writer2.close();
		 }
	}
	
	

	//Returns value of Binomial  
	// Coefficient C(n, k) 
	static int binomialCoeff(int n, int k)  { 
	    // Base Cases 
	    if (k == 0 || k == n) 
	        return 1; 
	    // Recur 
	    return binomialCoeff(n - 1, k - 1) +  binomialCoeff(n - 1, k); 
		} 

}
