package Test;

import java.io.FileWriter;
import java.util.Random;

import DilcaDistance.DilcaDistanceContTableMean;
import DilcaDistance.DilcaDistanceContTableRR;
import DilcaDistance.DilcaDistanceDiffPrivMeanWithFinalDistance;
import DilcaDistance.DilcaDistanceDiffPrivRRWithFinalDistance;
import net.javaguides.maven_web_project.DilcaDistanceDiffPriv.BalloonNMISoloDilcaTestSuClasseDataset;
import smile.validation.AdjustedRandIndex;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class TestTitanicARIEps3 {
	
public static void main(String[]args) throws Exception {
		
		Instances cpu = null;
		DataSource source = new DataSource("C:\\Users\\Simone\\eclipse-workspace\\DilcaDistanceDiffPriv\\src\\main\\java\\Test\\titanicTrue.arff");
		Instances data = source.getDataSet();
		data.setClassIndex(data.numAttributes()-1);
		Remove filter = new Remove();
		filter.setAttributeIndices(""+(data.classIndex()+1));
		filter.setInputFormat(data);
		cpu = Filter.useFilter(data, filter);
        String s = "C:\\\\Users\\\\Simone\\\\eclipse-workspace\\\\DilcaDistanceDiffPriv\\\\src\\\\main\\\\java\\\\Test\\\\titanicTrue.arff";
        int[] classe = new int[0];
        classe = BalloonNMISoloDilcaTestSuClasseDataset.loadArffClasse(s);
        double epsilon = 3.0;
        Random rand =new Random();
        double sigma = 0.0;
        for(int sig = 0; sig<10;sig++) {
        	sigma = sigma + 0.1;
        	FileWriter writer1 = new FileWriter("C:\\\\Users\\\\Simone\\\\Desktop\\\\TitanicAri\\\\TitanicMatrIniARI"+sigma+".txt", true);
			rand.setSeed(11235813);
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceContTableMean dd = new DilcaDistanceContTableMean(epsilon/(binomialCoeff(cpu.numAttributes(), 2)),sigma, rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff(s);
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
				AdjustedRandIndex nnn = new AdjustedRandIndex();
				double value = nnn.measure(classe, classCluster);
				corr = corr+value;
				counter++;
			}
			double result = (double) corr/counter;
			writer1.write("Epsilon: "+epsilon+", sigma:"+sigma+ ", ARI: "+result+";   ");		
			writer1.close();
			
        }
        
        sigma = 0.0;
        for(int sig = 0; sig<10;sig++) {
        	sigma = sigma + 0.1;
        	FileWriter writer1 = new FileWriter("C:\\Users\\Simone\\Desktop\\TitanicAri\\TitanicSUMeanSigmaARI"+sigma+"Epsilon3.txt", true);
			rand.setSeed(11235813);
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceDiffPrivMeanWithFinalDistance dd = new DilcaDistanceDiffPrivMeanWithFinalDistance((0.5*epsilon/(cpu.numAttributes()+binomialCoeff(cpu.numAttributes(), 2))),(0.5*epsilon/(binomialCoeff(cpu.numAttributes(), 2))),sigma, rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff(s);
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
				AdjustedRandIndex nnn = new AdjustedRandIndex();
				double value = nnn.measure(classe, classCluster);
				corr = corr+value;
				counter++;
			}
			double result = (double) corr/counter;
			writer1.write("Epsilon: "+epsilon+", sigma:"+sigma+ ", ARI: "+result+";   ");		
			writer1.close();
			
        }
        
        
        FileWriter writer1 = new FileWriter("C:\\Users\\Simone\\Desktop\\TitanicAri\\TitanicMatrIniRRARIEps3.txt", true);
		rand.setSeed(11235813);
		double corr = 0;
		int counter = 0;
		for(int i = 0; i<20;i++) {
			DilcaDistanceContTableRR dd = new DilcaDistanceContTableRR(epsilon/(binomialCoeff(cpu.numAttributes(), 2)), rand.nextLong());
			System.out.println("*******************************************");
			BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
			BalloonNMISoloDilcaTestSuClasseDataset.loadArff(s);
			int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
			AdjustedRandIndex nnn = new AdjustedRandIndex();
			double value = nnn.measure(classe, classCluster);
			corr = corr+value;
			counter++;
		}
		double result = (double) corr/counter;
		writer1.write("Epsilon: "+epsilon+", ARI: "+result+";   ");		
		writer1.close();
		
		FileWriter writer2 = new FileWriter("C:\\Users\\Simone\\Desktop\\TitanicAri\\TitanicSUFinalDistRRARIEps3.txt", true);
		rand.setSeed(11235813);
		double corr1 = 0;
		int counter1 = 0;
		for(int i = 0; i<20;i++) {
			DilcaDistanceDiffPrivRRWithFinalDistance dd = new DilcaDistanceDiffPrivRRWithFinalDistance((0.5*epsilon/(cpu.numAttributes()+binomialCoeff(cpu.numAttributes(), 2))),(0.5*epsilon/(binomialCoeff(cpu.numAttributes(), 2))), rand.nextLong());
			System.out.println("*******************************************");
			BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
			BalloonNMISoloDilcaTestSuClasseDataset.loadArff(s);
			int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
			AdjustedRandIndex nnn = new AdjustedRandIndex();
			double value = nnn.measure(classe, classCluster);
			corr1 = corr1+value;
			counter1++;
		}
		double result1 = (double) corr1/counter1;
		writer2.write("Epsilon: "+epsilon+", ARI: "+result1+";   ");		
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
