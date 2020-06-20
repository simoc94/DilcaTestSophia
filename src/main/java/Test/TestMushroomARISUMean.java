package Test;

import java.io.FileWriter;
import java.util.Random;

import DilcaDistance.DilcaDistanceDiffPrivMeanWithFinalDistance;
import net.javaguides.maven_web_project.DilcaDistanceDiffPriv.BalloonNMISoloDilcaTestSuClasseDataset;
import smile.validation.AdjustedRandIndex;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class TestMushroomARISUMean {
	
	public static void main(String[]args) throws Exception {
		
		Instances cpu = null;
		DataSource source = new DataSource("../src/main/java/Test/mushroom.arff");
		Instances data = source.getDataSet();
		data.setClassIndex(data.numAttributes()-1);
		Remove filter = new Remove();
		filter.setAttributeIndices(""+(data.classIndex()+1));
		filter.setInputFormat(data);
		cpu = Filter.useFilter(data, filter);
        String s = "../src/main/java/Test/mushroom.arff";
        int[] classe = new int[0];
        classe = BalloonNMISoloDilcaTestSuClasseDataset.loadArffClasse(s);
        double epsilon = 3.0;
        Random rand =new Random();
        double sigma = 0.0;
        
        for(int sig = 0; sig<10;sig++) {
        	sigma = sigma + 0.1;
        	FileWriter writer3 = new FileWriter("../../test_mushroom_ari_SU_mean/MushroomSUMeanSigmaARI"+sigma+"Epsilon3.txt", true);
			rand.setSeed(11235813);
			double corr2 = 0;
			int counter2 = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceDiffPrivMeanWithFinalDistance dd = new DilcaDistanceDiffPrivMeanWithFinalDistance((0.5*epsilon/(cpu.numAttributes()+binomialCoeff(cpu.numAttributes(), 2))),(0.5*epsilon/(binomialCoeff(cpu.numAttributes(), 2))),sigma, rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff(s);
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
				AdjustedRandIndex nnn = new AdjustedRandIndex();
				double value = nnn.measure(classe, classCluster);
				corr2 = corr2+value;
				counter2++;
			}
			double result2 = (double) corr2/counter2;
			writer3.write("Epsilon: "+epsilon+", sigma:"+sigma+ ", ARI: "+result2+";   ");		
			writer3.close();
			
        }
        
        
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
