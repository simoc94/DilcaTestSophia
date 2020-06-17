package net.javaguides.maven_web_project.DilcaDistanceDiffPriv;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

import DilcaDistance.DilcaDistance;
import DilcaDistance.DilcaDistanceDPFinalDistance;
import DilcaDistance.DilcaDistanceDiffPrivRR;
import DilcaDistance.DilcaDistanceDiffPrivRRWithFinalDistance;
import smile.validation.NormalizedMutualInformation;
import smile.validation.NormalizedMutualInformation.Method;

public class TestPerDilcaRRSUandFinalDistanceWithDilcaRR {
	
	public static void main(String[]args) throws Exception {
		
		DilcaDistance de = new DilcaDistance();
		System.out.println("*******************************************");
		BalloonNMISoloDilcaTestSuClasseDataset.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/VoteDataset/house-votes-84.arff");
		int[] classe = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(de, 2);
        double epsilon = 0.0;
        FileWriter writer1 = new FileWriter("C:\\Users\\Simone\\Desktop\\testDilcaSUFinalDistanceWithDilcaRR\\VoteRRDistance1.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			epsilon = epsilon+0.01;
			DilcaDistanceDiffPrivRRWithFinalDistance dd = new DilcaDistanceDiffPrivRRWithFinalDistance(epsilon,epsilon);
			dd.setEpsilonContext(epsilon);
			dd.setEpsilonDistance(epsilon);
			System.out.println("*******************************************");
			BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
			BalloonNMISoloDilcaTestSuClasseDataset.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/VoteDataset/house-votes-84.arff");
			int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
			NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
			double value = nnn.measure(classe, classCluster);
			Double.toString(value);
			System.out.println("value: "+value);
			writer1.write("Epsilon: "+epsilon+ " NMI: "+value+";   ");		
		}
		writer1.close();
		epsilon = 0.0;
		FileWriter writer2 = new FileWriter("C:\\Users\\Simone\\Desktop\\testDilcaSUFinalDistanceWithDilcaRR\\VoteRRDistance2.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			epsilon = epsilon+0.1;
			DilcaDistanceDiffPrivRRWithFinalDistance dd = new DilcaDistanceDiffPrivRRWithFinalDistance(epsilon,epsilon);
			dd.setEpsilonContext(epsilon);
			dd.setEpsilonDistance(epsilon);
			System.out.println("*******************************************");
			BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
			BalloonNMISoloDilcaTestSuClasseDataset.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/VoteDataset/house-votes-84.arff");
			int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
			NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
			double value = nnn.measure(classe, classCluster);
			Double.toString(value);
			System.out.println("value: "+value);
			writer2.write("Epsilon: "+epsilon+ " NMI: "+value+";   ");		
		}
		writer2.close();
		epsilon = 0.5;
		FileWriter writer3 = new FileWriter("C:\\Users\\Simone\\Desktop\\testDilcaSUFinalDistanceWithDilcaRR\\VoteRRDistance3.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			epsilon = epsilon+0.5;
			DilcaDistanceDiffPrivRRWithFinalDistance dd = new DilcaDistanceDiffPrivRRWithFinalDistance(epsilon,epsilon);
			dd.setEpsilonContext(epsilon);
			dd.setEpsilonDistance(epsilon);
			System.out.println("*******************************************");
			BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
			BalloonNMISoloDilcaTestSuClasseDataset.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/VoteDataset/house-votes-84.arff");
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
