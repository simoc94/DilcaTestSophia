package net.javaguides.maven_web_project.DilcaDistanceDiffPriv;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

import com.google.common.math.IntMath;

import DilcaDistance.DilcaDistance;
import DilcaDistance.DilcaDistanceContTableRR;
import DilcaDistance.DilcaDistanceDPFinalDistance;
import DilcaDistance.DilcaDistanceDiffPrivRR;
import smile.validation.NormalizedMutualInformation;
import smile.validation.NormalizedMutualInformation.Method;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class TestPerDilcaDPContextWithDilcaRR {
	
	public static void main(String[]args) throws Exception {
		/*
		System.out.println("Please, enter the arff file's path of your database.");
		System.out.println("Sorry, pay attention that the class attribute is the last in your database...Thanks for your help :)");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String s = bufferRead.readLine();
        */
		DataSource source = new DataSource("/Users/Simone/eclipse-workspace/Tesi/src/VoteDataset/house-votes-84.arff");
		Instances data = source.getDataSet();
		DilcaDistance de = new DilcaDistance();
		System.out.println("*******************************************");
		BalloonNMISoloDilcaTestSuClasseDataset.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/VoteDataset/house-votes-84.arff");
		int[] classe = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(de, 2);
        double epsilon = 0.0;
        FileWriter writer1 = new FileWriter("C:\\Users\\Simone\\Desktop\\testDilcaContextConDilcaRR\\VoteRRDistance1.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			epsilon = epsilon+0.01;
			DilcaDistanceContTableRR dd = new DilcaDistanceContTableRR((epsilon/IntMath.binomial(data.numInstances(),2)));
			dd.setEpsilon(epsilon);
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
		FileWriter writer2 = new FileWriter("C:\\Users\\Simone\\Desktop\\testDilcaContextConDilcaRR\\VoteRRDistance2.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			epsilon = epsilon+0.1;
			DilcaDistanceContTableRR dd = new DilcaDistanceContTableRR(epsilon);
			dd.setEpsilon(epsilon);
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
		FileWriter writer3 = new FileWriter("C:\\Users\\Simone\\Desktop\\testDilcaContextConDilcaRR\\VoteRRDistance3.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			epsilon = epsilon+0.5;
			DilcaDistanceContTableRR dd = new DilcaDistanceContTableRR(epsilon);
			dd.setEpsilon(epsilon);
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
