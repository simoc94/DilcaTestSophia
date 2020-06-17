package net.javaguides.maven_web_project.DilcaDistanceDiffPriv;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Random;

import DilcaDistance.DilcaDistanceContTableRR;
import DilcaDistance.DilcaDistanceDPFinalDistance;
import DilcaDistance.DilcaDistanceDiffPrivRR;
import smile.validation.NormalizedMutualInformation;
import smile.validation.NormalizedMutualInformation.Method;

public class testSetRandom {
	
	public static void main(String[]args) throws Exception {
		
		System.out.println("Please, enter the arff file's path of your database.");
		System.out.println("Sorry, pay attention that the class attribute is the last in your database...Thanks for your help :)");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String s = bufferRead.readLine();
        int[] classe = new int[0];
        classe = BalloonNMISoloDilcaTestSuClasseDataset.loadArffClasse(s);
        double epsilon = 0.0;
        Random rand = new Random();
        
        
        FileWriter writer1 = new FileWriter("C:\\Users\\Simone\\Desktop\\TestMatriciInizialiConDilcaOriginale\\DermatologyRRDistance1.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.001;
			double value = 0;
			int counter = 0;
			for(int i = 0; i<100; i++) {
				DilcaDistanceContTableRR dd = new DilcaDistanceContTableRR(epsilon, rand.nextLong());
				//dd.setEpsilon(epsilon);
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/DermatologyDataset/dermatologyUCI.arff");
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 6);
				NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
				value = value + nnn.measure(classe, classCluster);
				Double.toString(value);
				System.out.println("value: "+value);
				counter++;
			}
			double result = value/counter;
			writer1.write("Epsilon: "+epsilon+ " NMI: "+result+";   ");		
		}
		writer1.close();
		
		
        epsilon = 0.0;
        FileWriter writer2 = new FileWriter("C:\\Users\\Simone\\Desktop\\TestMatriciInizialiConDilcaOriginale\\DermatologyRRDistance2.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.01;
			double value = 0;
			int counter = 0;
			for(int i = 0; i<100; i++) {
				DilcaDistanceContTableRR dd = new DilcaDistanceContTableRR(epsilon, rand.nextLong());
				//dd.setEpsilon(epsilon);
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/DermatologyDataset/dermatologyUCI.arff");
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 6);
				NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
				value = value + nnn.measure(classe, classCluster);
				Double.toString(value);
				System.out.println("value: "+value);
				counter++;
			}
			double result = value/counter;
			writer2.write("Epsilon: "+epsilon+ " NMI: "+result+";   ");		
		}
		writer2.close();
		
		
        epsilon = 0.0;
		FileWriter writer3 = new FileWriter("C:\\Users\\Simone\\Desktop\\TestMatriciInizialiConDilcaOriginale\\DermatologyRRDistance3.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.1;
			double value = 0;
			int counter = 0;
			for(int i = 0; i<100; i++) {
				DilcaDistanceContTableRR dd = new DilcaDistanceContTableRR(epsilon, rand.nextLong());
				//dd.setEpsilon(epsilon);
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/DermatologyDataset/dermatologyUCI.arff");
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 6);
				NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
				value = value + nnn.measure(classe, classCluster);
				Double.toString(value);
				System.out.println("value: "+value);
				counter++;
			}
			double result = value/counter;
			writer3.write("Epsilon: "+epsilon+ " NMI: "+result+";   ");		
		}
		writer3.close();
		
        
        epsilon = 0.5;
        
		FileWriter writer4 = new FileWriter("C:\\Users\\Simone\\Desktop\\TestMatriciInizialiConDilcaOriginale\\DermatologyRRDistance4.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.5;
			double value = 0;
			int counter = 0;
			for(int i = 0; i<100; i++) {
				DilcaDistanceContTableRR dd = new DilcaDistanceContTableRR(epsilon, rand.nextLong());
				//dd.setEpsilon(epsilon);
				System.out.println("*******************************************");
				BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
				BalloonNMISoloDilcaTestSuClasseDataset.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/DermatologyDataset/dermatologyUCI.arff");
				int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 6);
				NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
				value = value + nnn.measure(classe, classCluster);
				Double.toString(value);
				System.out.println("value: "+value);
				counter++;
			}
			double result = value/counter;
			writer4.write("Epsilon: "+epsilon+ " NMI: "+result+";   ");		
		}
		writer4.close();
		
		  
		
	}

}
