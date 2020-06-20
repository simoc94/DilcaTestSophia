package Test;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Random;

import DilcaDistance.DilcaDistanceContTableMean;
import DilcaDistance.DilcaDistanceContTableRR;
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

public class TestMushroomNMIEps3 {
	
	public static void main(String[]args) throws Exception {
		
		Instances cpu = null;
		DataSource source = new DataSource("C:\\\\Users\\\\Simone\\\\eclipse-workspace\\\\DilcaDistanceDiffPriv\\\\src\\\\main\\\\java\\\\Test\\\\mushroom.arff");
		Instances data = source.getDataSet();
		data.setClassIndex(data.numAttributes()-1);
		Remove filter = new Remove();
		filter.setAttributeIndices(""+(data.classIndex()+1));
		filter.setInputFormat(data);
		cpu = Filter.useFilter(data, filter);
        String s = "C:\\\\\\\\Users\\\\\\\\Simone\\\\\\\\eclipse-workspace\\\\\\\\DilcaDistanceDiffPriv\\\\\\\\src\\\\\\\\main\\\\\\\\java\\\\\\\\Test\\\\\\\\mushroom.arff";
        int[] classe = new int[0];
        classe = BalloonNMISoloDilcaTestSuClasseDataset.loadArffClasse(s);
        double epsilon = 3.0;
        Random rand =new Random();
        double sigma = 0.0;
        /*
        for(int sig = 0; sig<10;sig++) {
        	sigma = sigma + 0.1;
        	FileWriter writer1 = new FileWriter("../../test_mushroom_nmi_matrini_mean/MushroomMatrIniMeanSigmaNMI"+sigma+"Epsilon3.txt", true);
			rand.setSeed(11235813);
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceContTableMean dd = new DilcaDistanceContTableMean(epsilon/(binomialCoeff(cpu.numAttributes(), 2)),sigma, rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff(s);
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
				NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
				double value = nnn.measure(classe, classCluster);
				corr = corr+value;
				counter++;
			}
			double result = (double) corr/counter;
			writer1.write("Epsilon: "+epsilon+", sigma:"+sigma+ ", NMI: "+result+";   ");		
			writer1.close();
			
        }
        
        sigma = 0.0;
        */
        
        
        FileWriter writer1 = new FileWriter("C:\\Users\\Simone\\Desktop\\MushroomMatrIniRRNMIEps3.txt", true);
		rand.setSeed(11235813);
		double corr = 0;
		int counter = 0;
		for(int i = 0; i<20;i++) {
			DilcaDistanceContTableRR dd = new DilcaDistanceContTableRR(epsilon/(binomialCoeff(cpu.numAttributes(), 2)), rand.nextLong());
			System.out.println("*******************************************");
			BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
			BalloonNMISoloDilcaTestSuClasseDataset.loadArff(s);
			int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
			NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
			double value = nnn.measure(classe, classCluster);
			corr = corr+value;
			counter++;
		}
		double result = (double) corr/counter;
		writer1.write("Epsilon: "+epsilon+", NMI: "+result+";   ");		
		writer1.close();
		
		FileWriter writer2 = new FileWriter("C:\\\\Users\\\\Simone\\\\Desktop\\\\MushroomSUFinalDistRRNMIEps3.txt", true);
		rand.setSeed(11235813);
		double corr1 = 0;
		int counter1 = 0;
		for(int i = 0; i<20;i++) {
			DilcaDistanceDiffPrivRRWithFinalDistance dd = new DilcaDistanceDiffPrivRRWithFinalDistance((0.5*epsilon/(cpu.numAttributes()+binomialCoeff(cpu.numAttributes(), 2))),(0.5*epsilon/(binomialCoeff(cpu.numAttributes(), 2))), rand.nextLong());
			System.out.println("*******************************************");
			BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
			BalloonNMISoloDilcaTestSuClasseDataset.loadArff(s);
			int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
			NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
			double value = nnn.measure(classe, classCluster);
			corr1 = corr1+value;
			counter1++;
		}
		double result1 = (double) corr1/counter1;
		writer2.write("Epsilon: "+epsilon+", NMI: "+result1+";   ");		
		writer2.close();
		
		
		for(int sig = 0; sig<10;sig++) {
        	sigma = sigma + 0.1;
        	FileWriter writer3 = new FileWriter("C:\\\\Users\\\\Simone\\\\Desktop\\\\MushroomSUMeanSigmaNMI"+sigma+"Epsilon3.txt", true);
			rand.setSeed(11235813);
			double corr2 = 0;
			int counter2 = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceDiffPrivMeanWithFinalDistance dd = new DilcaDistanceDiffPrivMeanWithFinalDistance((0.5*epsilon/(cpu.numAttributes()+binomialCoeff(cpu.numAttributes(), 2))),(0.5*epsilon/(binomialCoeff(cpu.numAttributes(), 2))),sigma, rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff(s);
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
				NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
				double value = nnn.measure(classe, classCluster);
				corr2 = corr2+value;
				counter2++;
			}
			double result2 = (double) corr2/counter2;
			writer3.write("Epsilon: "+epsilon+", sigma:"+sigma+ ", NMI: "+result2+";   ");		
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
