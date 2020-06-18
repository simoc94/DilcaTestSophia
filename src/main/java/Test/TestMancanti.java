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

public class TestMancanti {
	
	
	public static void mushroomAriMatriciInizialiMean() throws Exception {
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
	    double epsilon = 0.0;
	    Random rand =new Random();
	    double sigma = 0.0;
	    for(int sig = 0; sig<10;sig++) {
	    	sigma = sigma + 0.1;
	    	epsilon = 0.0;
	    	FileWriter writer1 = new FileWriter("../../test_mushroom_ari_matrini_mean/MushroomMatrIniMean1Sigma"+sigma+"ARI.txt", true);
	    	for(int ciclo = 1; ciclo<50; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceContTableMean dd = new DilcaDistanceContTableMean(epsilon/(binomialCoeff(cpu.numAttributes(), 2)),sigma, rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("../src/main/java/Test/mushroom.arff");
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
	    
	    epsilon = 0.0;
	    rand =new Random();
	    sigma = 0.0;
	    for(int sig = 0; sig<10;sig++) {
	    	sigma = sigma + 0.1;
	    	epsilon = 0.0;
	    FileWriter writer1 = new FileWriter("../../test_mushroom_ari_SU_mean/MushroomSUFinalDistMean1Sigma"+sigma+"ARI.txt", true);
		for(int ciclo = 1; ciclo<50; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceDiffPrivMeanWithFinalDistance dd = new DilcaDistanceDiffPrivMeanWithFinalDistance((0.5*epsilon/(cpu.numAttributes()+binomialCoeff(cpu.numAttributes(), 2))),(0.5*epsilon/(binomialCoeff(cpu.numAttributes(), 2))),sigma, rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("../src/main/java/Test/mushroom.arff");
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
		
	    /*
	    sigma = 0.0;
		epsilon = 0.5;
		 for(int sig = 0; sig<10;sig++) {
	        	sigma = sigma + 0.1;
	        	epsilon = 0.5;
				FileWriter writer2 = new FileWriter("test_mushroom_ari_matrini_mean/MushroomMatrIniMean2Sigma"+sigma+"ARI.txt", true);
				for(int ciclo = 1; ciclo<10; ciclo++) {
					rand.setSeed(11235813);
					epsilon = epsilon+0.5;
					double corr = 0;
					int counter = 0;
					for(int i = 0; i<100;i++) {
						DilcaDistanceContTableMean dd = new DilcaDistanceContTableMean(epsilon/(binomialCoeff(cpu.numAttributes(), 2)),sigma, rand.nextLong());
						System.out.println("*******************************************");
						BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
						BalloonNMISoloDilcaTestSuClasseDataset.loadArff("DilcaTestSophia/src/main/java/Test/mushroom.arff");
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
	 */
	}
	
	
	public static void mushroomAriRR() throws Exception {
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
	    double epsilon = 0.0;
	    Random rand =new Random();
	    FileWriter writer1 = new FileWriter("../../test_mushroom_ari_matrini_rr/MushroomMatrIniRR1ARI.txt", true);
		for(int ciclo = 1; ciclo<50; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceContTableRR dd = new DilcaDistanceContTableRR(epsilon/(binomialCoeff(cpu.numAttributes(), 2)), rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("../src/main/java/Test/mushroom.arff");
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
				AdjustedRandIndex nnn = new AdjustedRandIndex();
				double value = nnn.measure(classe, classCluster);
				corr = corr+value;
				counter++;
			}
			double result = (double) corr/counter;
			writer1.write("Epsilon: "+epsilon+", ARI: "+result+";   ");		
		}
		writer1.close();
	    
	    epsilon = 0.0;
	    FileWriter writer2 = new FileWriter("../../test_mushroom_ari_SU_rr/MushroomSUFinalDistRR1ARI.txt", true);
		for(int ciclo = 1; ciclo<50; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceDiffPrivRRWithFinalDistance dd = new DilcaDistanceDiffPrivRRWithFinalDistance((0.5*epsilon/(cpu.numAttributes()+binomialCoeff(cpu.numAttributes(), 2))),(0.5*epsilon/(binomialCoeff(cpu.numAttributes(), 2))), rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("../src/main/java/Test/mushroom.arff");
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
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
	
	
	
	
	
	
	public static void mushroomAllNMI() throws Exception {
		
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
	    double epsilon = 0.0;
	    Random rand =new Random();
	    double sigma = 0.0;
	    for(int sig = 0; sig<10;sig++) {
	    	sigma = sigma + 0.1;
	    	epsilon = 0.0;
	    	FileWriter writer1 = new FileWriter("../../test_mushroom_nmi_matrini_mean/MushroomMatrIniMean1Sigma"+sigma+"NMI.txt", true);
	    	for(int ciclo = 1; ciclo<50; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceContTableMean dd = new DilcaDistanceContTableMean(epsilon/(binomialCoeff(cpu.numAttributes(), 2)),sigma, rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("../src/main/java/Test/mushroom.arff");
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
				NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
				double value = nnn.measure(classe, classCluster);
				corr = corr+value;
				counter++;
			}
			double result = (double) corr/counter;
			writer1.write("Epsilon: "+epsilon+", sigma:"+sigma+ ", NMI: "+result+";   ");		
		}
			writer1.close();
	    }
	    
	    epsilon = 0.0;
	    rand =new Random();
	    sigma = 0.0;
	    for(int sig = 0; sig<10;sig++) {
	    	sigma = sigma + 0.1;
	    	epsilon = 0.0;
	    FileWriter writer1 = new FileWriter("../../test_mushroom_nmi_SU_mean/MushroomSUFinalDistMean1Sigma"+sigma+"NMI.txt", true);
		for(int ciclo = 1; ciclo<50; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceDiffPrivMeanWithFinalDistance dd = new DilcaDistanceDiffPrivMeanWithFinalDistance((0.5*epsilon/(cpu.numAttributes()+binomialCoeff(cpu.numAttributes(), 2))),(0.5*epsilon/(binomialCoeff(cpu.numAttributes(), 2))),sigma, rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("../src/main/java/Test/mushroom.arff");
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
				NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
				double value = nnn.measure(classe, classCluster);
				corr = corr+value;
				counter++;
			}
			double result = (double) corr/counter;
			writer1.write("Epsilon: "+epsilon+", sigma:"+sigma+ ", NMI: "+result+";   ");		
		}
		writer1.close();
	    }
		
	    
	    
	    epsilon = 0.0;
	    FileWriter writer1 = new FileWriter("../../test_mushroom_nmi_matrini_rr/MushroomMatrIniRR1NMI.txt", true);
		for(int ciclo = 1; ciclo<50; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceContTableRR dd = new DilcaDistanceContTableRR(epsilon/(binomialCoeff(cpu.numAttributes(), 2)), rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("../src/main/java/Test/mushroom.arff");
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
				NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
				double value = nnn.measure(classe, classCluster);
				corr = corr+value;
				counter++;
			}
			double result = (double) corr/counter;
			writer1.write("Epsilon: "+epsilon+", NMI: "+result+";   ");		
		}
		writer1.close();
	    
	    epsilon = 0.0;
	    FileWriter writer2 = new FileWriter("../../test_mushroom_nmi_SU_rr/MushroomSUFinalDistRR1NMI.txt", true);
		for(int ciclo = 1; ciclo<50; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceDiffPrivRRWithFinalDistance dd = new DilcaDistanceDiffPrivRRWithFinalDistance((0.5*epsilon/(cpu.numAttributes()+binomialCoeff(cpu.numAttributes(), 2))),(0.5*epsilon/(binomialCoeff(cpu.numAttributes(), 2))), rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("../src/main/java/Test/mushroom.arff");
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
				NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
				double value = nnn.measure(classe, classCluster);
				corr = corr+value;
				counter++;
			}
			double result = (double) corr/counter;
			writer2.write("Epsilon: "+epsilon+", NMI: "+result+";   ");		
		}
		writer2.close();
		
		
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void titanicARIAll() throws Exception{
		Instances cpu = null;
		DataSource source = new DataSource("../src/main/java/Test/titanicTrue.arff");
		Instances data = source.getDataSet();
		data.setClassIndex(data.numAttributes()-1);
		Remove filter = new Remove();
		filter.setAttributeIndices(""+(data.classIndex()+1));
		filter.setInputFormat(data);
		cpu = Filter.useFilter(data, filter);
		
        String s = "../src/main/java/Test/titanicTrue.arff";
        int[] classe = new int[0];
        classe = BalloonNMISoloDilcaTestSuClasseDataset.loadArffClasse(s);
        double epsilon = 0.0;
        Random rand =new Random();
        double sigma = 0.0;
        for(int sig = 0; sig<10;sig++) {
        	sigma = sigma + 0.1;
        	epsilon = 0.0;
        	FileWriter writer1 = new FileWriter("../../test_titanic_ari_matrini_mean/TitanicMatrIniMean1Sigma"+sigma+"ARI.txt", true);
        	for(int ciclo = 1; ciclo<50; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceContTableMean dd = new DilcaDistanceContTableMean(epsilon/(binomialCoeff(cpu.numAttributes(), 2)),sigma, rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("../src/main/java/Test/titanicTrue.arff");
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
		epsilon = 0.0;
        for(int sig = 0; sig<10;sig++) {
        	sigma = sigma + 0.1;
        	epsilon = 0.0;
        FileWriter writer1 = new FileWriter("../../test_titanic_ari_SU_mean/TitanicSUFinalDistMean1Sigma"+sigma+"ARI.txt", true);
		for(int ciclo = 1; ciclo<50; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceDiffPrivMeanWithFinalDistance dd = new DilcaDistanceDiffPrivMeanWithFinalDistance((0.5*epsilon/(cpu.numAttributes()+binomialCoeff(cpu.numAttributes(), 2))),(0.5*epsilon/(binomialCoeff(cpu.numAttributes(), 2))),sigma, rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("../src/main/java/Test/titanicTrue.arff");
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
        
        epsilon = 0.0;
        FileWriter writer1 = new FileWriter("../../test_titanic_ari_matrini_rr/TitanicMatrIniRR1ARI.txt", true);
		for(int ciclo = 1; ciclo<50; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceContTableRR dd = new DilcaDistanceContTableRR(epsilon/(binomialCoeff(cpu.numAttributes(), 2)), rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("../src/main/java/Test/titanicTrue.arff");
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
				AdjustedRandIndex nnn = new AdjustedRandIndex();
				double value = nnn.measure(classe, classCluster);
				corr = corr+value;
				counter++;
			}
			double result = (double) corr/counter;
			writer1.write("Epsilon: "+epsilon+", ARI: "+result+";   ");		
		}
		writer1.close();
		
		epsilon = 0.0;
		FileWriter writer2 = new FileWriter("../../test_titanic_ari_SU_rr/TitanicSUFinalDistRR1ARI.txt", true);
		for(int ciclo = 1; ciclo<50; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceDiffPrivRRWithFinalDistance dd = new DilcaDistanceDiffPrivRRWithFinalDistance((0.5*epsilon/(cpu.numAttributes()+binomialCoeff(cpu.numAttributes(), 2))),(0.5*epsilon/(binomialCoeff(cpu.numAttributes(), 2))), rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("../src/main/java/Test/titanicTrue.arff");
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void titanicNMIAll() throws Exception{
		Instances cpu = null;
		DataSource source = new DataSource("../src/main/java/Test/titanicTrue.arff");
		Instances data = source.getDataSet();
		data.setClassIndex(data.numAttributes()-1);
		Remove filter = new Remove();
		filter.setAttributeIndices(""+(data.classIndex()+1));
		filter.setInputFormat(data);
		cpu = Filter.useFilter(data, filter);
		
        String s = "../src/main/java/Test/titanicTrue.arff";
        int[] classe = new int[0];
        classe = BalloonNMISoloDilcaTestSuClasseDataset.loadArffClasse(s);
        double epsilon = 0.0;
        Random rand =new Random();
        double sigma = 0.0;
        for(int sig = 0; sig<10;sig++) {
        	sigma = sigma + 0.1;
        	epsilon = 0.0;
        	FileWriter writer1 = new FileWriter("../../test_titanic_nmi_matrini_mean/TitanicMatrIniMean1Sigma"+sigma+"NMI.txt", true);
        	for(int ciclo = 1; ciclo<50; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceContTableMean dd = new DilcaDistanceContTableMean(epsilon/(binomialCoeff(cpu.numAttributes(), 2)),sigma, rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("../src/main/java/Test/titanicTrue.arff");
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
				NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
				double value = nnn.measure(classe, classCluster);
				corr = corr+value;
				counter++;
			}
			double result = (double) corr/counter;
			writer1.write("Epsilon: "+epsilon+", sigma:"+sigma+ ", NMI: "+result+";   ");		
		}
			writer1.close();
        }
		
        
        sigma = 0.0;
		epsilon = 0.0;
        for(int sig = 0; sig<10;sig++) {
        	sigma = sigma + 0.1;
        	epsilon = 0.0;
        FileWriter writer1 = new FileWriter("../../test_titanic_nmi_SU_mean/TitanicSUFinalDistMean1Sigma"+sigma+"NMI.txt", true);
		for(int ciclo = 1; ciclo<50; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceDiffPrivMeanWithFinalDistance dd = new DilcaDistanceDiffPrivMeanWithFinalDistance((0.5*epsilon/(cpu.numAttributes()+binomialCoeff(cpu.numAttributes(), 2))),(0.5*epsilon/(binomialCoeff(cpu.numAttributes(), 2))),sigma, rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("../src/main/java/Test/titanicTrue.arff");
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
				NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
				double value = nnn.measure(classe, classCluster);
				corr = corr+value;
				counter++;
			}
			double result = (double) corr/counter;
			writer1.write("Epsilon: "+epsilon+", sigma:"+sigma+ ", NMI: "+result+";   ");		
		}
		writer1.close();
        }
        
        epsilon = 0.0;
        FileWriter writer1 = new FileWriter("../../test_titanic_nmi_matrini_rr/TitanicMatrIniRR1NMI.txt", true);
		for(int ciclo = 1; ciclo<50; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceContTableRR dd = new DilcaDistanceContTableRR(epsilon/(binomialCoeff(cpu.numAttributes(), 2)), rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("../src/main/java/Test/titanicTrue.arff");
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
				NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
				double value = nnn.measure(classe, classCluster);
				corr = corr+value;
				counter++;
			}
			double result = (double) corr/counter;
			writer1.write("Epsilon: "+epsilon+", NMI: "+result+";   ");		
		}
		writer1.close();
		
		epsilon = 0.0;
		FileWriter writer2 = new FileWriter("../../test_titanic_nmi_SU_rr/TitanicSUFinalDistRR1NMI.txt", true);
		for(int ciclo = 1; ciclo<50; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceDiffPrivRRWithFinalDistance dd = new DilcaDistanceDiffPrivRRWithFinalDistance((0.5*epsilon/(cpu.numAttributes()+binomialCoeff(cpu.numAttributes(), 2))),(0.5*epsilon/(binomialCoeff(cpu.numAttributes(), 2))), rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("../src/main/java/Test/titanicTrue.arff");
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
				NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
				double value = nnn.measure(classe, classCluster);
				corr = corr+value;
				counter++;
			}
			double result = (double) corr/counter;
			writer2.write("Epsilon: "+epsilon+", NMI: "+result+";   ");		
		}
		writer2.close();
        
		 
	}
	
	
	
	public static void PostOperativeTest() throws Exception {
		Instances cpu = null;
		DataSource source = new DataSource("C:\\\\Users\\\\Simone\\\\eclipse-workspace\\\\DilcaDistanceDiffPriv\\\\src\\\\main\\\\java\\\\Test\\\\postoperative-patient-data.arff");
		Instances data = source.getDataSet();
		data.setClassIndex(data.numAttributes()-1);
		Remove filter = new Remove();
		filter.setAttributeIndices(""+(data.classIndex()+1));
		filter.setInputFormat(data);
		cpu = Filter.useFilter(data, filter);
	    String s = "C:\\\\Users\\\\Simone\\\\eclipse-workspace\\\\DilcaDistanceDiffPriv\\\\src\\\\main\\\\java\\\\Test\\\\postoperative-patient-data.arff";
	    int[] classe = new int[0];
	    classe = BalloonNMISoloDilcaTestSuClasseDataset.loadArffClasse(s);
	    double epsilon = 0.0;
	    Random rand =new Random();
	    double sigma = 0.0;
	    for(int sig = 0; sig<10;sig++) {
	    	sigma = sigma + 0.1;
	    	epsilon = 0.0;
	    	FileWriter writer1 = new FileWriter("C:\\\\Users\\\\Simone\\\\Desktop\\PostOpMatrIniMean1Sigma"+sigma+"ARI.txt", true);
	    	for(int ciclo = 1; ciclo<50; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceContTableMean dd = new DilcaDistanceContTableMean(epsilon/(binomialCoeff(cpu.numAttributes(), 2)),sigma, rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("C:\\Users\\Simone\\eclipse-workspace\\DilcaDistanceDiffPriv\\src\\main\\java\\Test\\postoperative-patient-data.arff");
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 3);
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
	    
	    epsilon = 0.0;
	    rand =new Random();
	    sigma = 0.0;
	    for(int sig = 0; sig<10;sig++) {
	    	sigma = sigma + 0.1;
	    	epsilon = 0.0;
	    FileWriter writer1 = new FileWriter("C:\\\\Users\\\\Simone\\\\Desktop\\PostOpSUFinalDistMean1Sigma"+sigma+"ARI.txt", true);
		for(int ciclo = 1; ciclo<50; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double corr = 0;
			int counter = 0;
			for(int i = 0; i<20;i++) {
				DilcaDistanceDiffPrivMeanWithFinalDistance dd = new DilcaDistanceDiffPrivMeanWithFinalDistance((0.5*epsilon/(cpu.numAttributes()+binomialCoeff(cpu.numAttributes(), 2))),(0.5*epsilon/(binomialCoeff(cpu.numAttributes(), 2))),sigma, rand.nextLong());
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("C:\\\\Users\\\\Simone\\\\eclipse-workspace\\\\DilcaDistanceDiffPriv\\\\src\\\\main\\\\java\\\\Test\\\\postoperative-patient-data.arff");
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 3);
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
		
	    /*
	    sigma = 0.0;
		epsilon = 0.5;
		 for(int sig = 0; sig<10;sig++) {
	        	sigma = sigma + 0.1;
	        	epsilon = 0.5;
				FileWriter writer2 = new FileWriter("test_mushroom_ari_matrini_mean/MushroomMatrIniMean2Sigma"+sigma+"ARI.txt", true);
				for(int ciclo = 1; ciclo<10; ciclo++) {
					rand.setSeed(11235813);
					epsilon = epsilon+0.5;
					double corr = 0;
					int counter = 0;
					for(int i = 0; i<100;i++) {
						DilcaDistanceContTableMean dd = new DilcaDistanceContTableMean(epsilon/(binomialCoeff(cpu.numAttributes(), 2)),sigma, rand.nextLong());
						System.out.println("*******************************************");
						BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
						BalloonNMISoloDilcaTestSuClasseDataset.loadArff("DilcaTestSophia/src/main/java/Test/mushroom.arff");
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
	 */
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
