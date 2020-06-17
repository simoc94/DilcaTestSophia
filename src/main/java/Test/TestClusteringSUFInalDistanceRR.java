package Test;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Random;

import DilcaDistance.DilcaDistanceContTableMean;
import DilcaDistance.DilcaDistanceDiffPrivMeanWithFinalDistance;
import DilcaDistance.DilcaDistanceDiffPrivRRWithFinalDistance;
import net.javaguides.maven_web_project.DilcaDistanceDiffPriv.BalloonNMISoloDilcaTestSuClasseDataset;
import smile.validation.AdjustedRandIndex;
import smile.validation.NormalizedMutualInformation;
import smile.validation.NormalizedMutualInformation.Method;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class TestClusteringSUFInalDistanceRR {
	
	
public static void main(String[]args) throws Exception {
	
		Instances cpu = null;
		DataSource source = new DataSource("/Users/Simone/eclipse-workspace/Tesi/src/MushroomDataset/mushroom.arff");
		Instances data = source.getDataSet();
		data.setClassIndex(data.numAttributes()-1);
		Remove filter = new Remove();
		filter.setAttributeIndices(""+(data.classIndex()+1));
		filter.setInputFormat(data);
		cpu = Filter.useFilter(data, filter);
		
		System.out.println("Please, enter the arff file's path of your database.");
		System.out.println("Sorry, pay attention that the class attribute is the last in your database...Thanks for your help :)");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String s = bufferRead.readLine();
        int[] classe = new int[0];
        classe = BalloonNMISoloDilcaTestSuClasseDataset.loadArffClasse(s);
        double epsilon = 0.0;
        Random rand =new Random();
        FileWriter writer1 = new FileWriter("C:\\Users\\Simone\\Desktop\\TestClusteringSUFinalDIstRR\\MushroomSUFinalDistRR1ARI.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<3;i++) {
				DilcaDistanceDiffPrivRRWithFinalDistance dd = new DilcaDistanceDiffPrivRRWithFinalDistance((0.5*epsilon/(cpu.numAttributes()+binomialCoeff(cpu.numAttributes(), 2))),(0.5*epsilon/(binomialCoeff(cpu.numAttributes(), 2))), rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/MushroomDataset/mushroom.arff");
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
				//NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
				AdjustedRandIndex nnn = new AdjustedRandIndex();
				double value = nnn.measure(classe, classCluster);
				corr = corr+value;
				counter++;
			}
			double result = (double) corr/counter;
			writer1.write("Epsilon: "+epsilon+", ARI: "+result+";   ");		
		}
		writer1.close();
		
		epsilon = 0.5;
		FileWriter writer2 = new FileWriter("C:\\Users\\Simone\\Desktop\\TestClusteringSUFinalDIstRR\\MushroomSUFinalDistRR2ARI.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.5;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<3;i++) {
				DilcaDistanceDiffPrivRRWithFinalDistance dd = new DilcaDistanceDiffPrivRRWithFinalDistance((0.5*epsilon/(cpu.numAttributes()+binomialCoeff(cpu.numAttributes(), 2))),(0.5*epsilon/(binomialCoeff(cpu.numAttributes(), 2))), rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/MushroomDataset/mushroom.arff");
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
				//NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
				AdjustedRandIndex nnn = new AdjustedRandIndex();
				double value = nnn.measure(classe, classCluster);
				corr = corr+value;
				counter++;
			}
			double result = (double) corr/counter;
			writer2.write("Epsilon: "+epsilon+", ARI: "+result+";   ");	
		}
		writer2.close();
		
	}

	//Returns value of Binomial  
	// Coefficient C(n, k) 
	static int binomialCoeff(int n, int k)  
	{ 
	  
	    // Base Cases 
	    if (k == 0 || k == n) 
	        return 1; 
	      
	    // Recur 
	    return binomialCoeff(n - 1, k - 1) +  
	                binomialCoeff(n - 1, k); 
	} 


}
