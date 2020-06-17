package net.javaguides.maven_web_project.DilcaDistanceDiffPriv;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

import DilcaDistance.DilcaDistance;
import DilcaDistance.DilcaDistanceContTableRR;
import DilcaDistance.DilcaDistanceDPFinalDistance;
import DilcaDistance.DilcaDistanceDiffPrivRR;
import smile.validation.NormalizedMutualInformation;
import smile.validation.NormalizedMutualInformation.Method;

public class TestPerDilcaDPFinalDistanceWithDilcaRR {
	
	public static void main(String[]args) throws Exception {
		/*
		System.out.println("Please, enter the arff file's path of your database.");
		System.out.println("Sorry, pay attention that the class attribute is the last in your database...Thanks for your help :)");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String s = bufferRead.readLine();
        */
		DilcaDistance de = new DilcaDistance();
		System.out.println("*******************************************");
		BalloonNMISoloDilcaTestSuClasseDataset.loadArff("C:\\\\Users\\\\Simone\\\\eclipse-workspace\\\\Tesi\\\\src\\\\TitanicDataset\\\\titanic_arff.arff");
		int[] classe = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(de, 2);
        double epsilon = 0.0;
        FileWriter writer1 = new FileWriter("C:\\Users\\Simone\\Desktop\\testDilcaFInalDistanceWithDilcaRR\\TitanicRRDistance1.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			epsilon = epsilon+0.01;
			DilcaDistanceDPFinalDistance dd = new DilcaDistanceDPFinalDistance(epsilon);
			dd.setEpsilon(epsilon);
			System.out.println("*******************************************");
			BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
			BalloonNMISoloDilcaTestSuClasseDataset.loadArff("C:\\\\Users\\\\Simone\\\\eclipse-workspace\\\\Tesi\\\\src\\\\TitanicDataset\\\\titanic_arff.arff");
			int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
			NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
			double value = nnn.measure(classe, classCluster);
			Double.toString(value);
			System.out.println("value: "+value);
			writer1.write("Epsilon: "+epsilon+ " NMI: "+value+";   ");		
		}
		writer1.close();
		epsilon = 0.0;
		FileWriter writer2 = new FileWriter("C:\\Users\\Simone\\Desktop\\testDilcaFInalDistanceWithDilcaRR\\TitanicRRDistance2.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			epsilon = epsilon+0.1;
			DilcaDistanceDPFinalDistance dd = new DilcaDistanceDPFinalDistance(epsilon);
			dd.setEpsilon(epsilon);
			System.out.println("*******************************************");
			BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
			BalloonNMISoloDilcaTestSuClasseDataset.loadArff("C:\\\\Users\\\\Simone\\\\eclipse-workspace\\\\Tesi\\\\src\\\\TitanicDataset\\\\titanic_arff.arff");
			int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
			NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
			double value = nnn.measure(classe, classCluster);
			Double.toString(value);
			System.out.println("value: "+value);
			writer2.write("Epsilon: "+epsilon+ " NMI: "+value+";   ");		
		}
		writer2.close();
		epsilon = 0.5;
		FileWriter writer3 = new FileWriter("C:\\Users\\Simone\\Desktop\\testDilcaFInalDistanceWithDilcaRR\\TitanicRRDistance3.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			epsilon = epsilon+0.5;
			DilcaDistanceDPFinalDistance dd = new DilcaDistanceDPFinalDistance(epsilon);
			dd.setEpsilon(epsilon);
			System.out.println("*******************************************");
			BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
			BalloonNMISoloDilcaTestSuClasseDataset.loadArff("C:\\\\Users\\\\Simone\\\\eclipse-workspace\\\\Tesi\\\\src\\\\TitanicDataset\\\\titanic_arff.arff");
			int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
			NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
			double value = nnn.measure(classe, classCluster);
			Double.toString(value);
			System.out.println("value: "+value);
			writer3.write("Epsilon: "+epsilon+ " NMI: "+value+";   ");		
		}

		writer3.close();
		  

		  
		
	}

}
