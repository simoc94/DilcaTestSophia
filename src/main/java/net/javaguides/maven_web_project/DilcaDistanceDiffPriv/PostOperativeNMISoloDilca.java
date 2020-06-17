package net.javaguides.maven_web_project.DilcaDistanceDiffPriv;

import java.io.FileWriter;

import DilcaDistance.DilcaDistance;
import DilcaDistance.DilcaDistanceDiffPrivMean;
import DilcaDistance.DilcaDistanceMean;
import smile.validation.NormalizedMutualInformation;
import smile.validation.NormalizedMutualInformation.Method;
import weka.clusterers.HierarchicalClusterer;
import weka.core.DistanceFunction;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.ManhattanDistance;
import weka.core.SelectedTag;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

/*
 * Con Dilca Distance Diff Priv  che identifica il context tramite mean value, l'alfa migliore è 0.9 , che da un valore di NMI di 0.854010807
 * ATTENZIONE quando si usa contextSelection tramite Mean Value e i vari alfa a modificare i vari hYaa, hYbb, hYcc, hYdd, hYee, hYff
 */

public class PostOperativeNMISoloDilca {
	
	
	public static int[] addOneIntToArray(int[] initialArray , int newValue) {

	    int[] newArray = new int[initialArray.length + 1];
	    for (int index = 0; index < initialArray.length; index++) {
	        newArray[index] = initialArray[index];
	    }

	    newArray[newArray.length - 1] = newValue;
	    return newArray;
	}

	static Instances cpu = null;
	static HierarchicalClusterer hc;
	public int[] clusterData(DistanceFunction df) throws Exception{
		hc = new HierarchicalClusterer();
		//kmeans.setSeed(10);
		hc.setLinkType(new SelectedTag(5, hc.TAGS_LINK_TYPE));
		hc.setDistanceFunction(df);
		//kmeans.setPreserveInstancesOrder(true);
		//kmeans.setMaxIterations(1000);
		hc.setNumClusters(3);
		hc.buildClusterer(cpu);
		int[] assignments = new int[0];
		for(Instance inst: cpu) {
			int cluster = hc.clusterInstance(inst);
			assignments = addOneIntToArray(assignments, cluster);
		}
		int i = 0;
		for(int clusterNum : assignments) {
			System.out.printf("Instance %d -> Cluster %d\n", (i+1), clusterNum);
			i++;
		}

		for (String option: hc.getOptions()) {
		    
	    	System.out.println(option);
	    }

		return assignments;
	}
	
	
	
/*	public static int[] getCluster(DistanceFunction df) throws Exception {
		kmeans = new SimpleKMeans2();
		kmeans.setSeed(10);
		kmeans.setDistanceFunction(df);
		kmeans.setPreserveInstancesOrder(true);
		kmeans.setMaxIterations(1000);
		kmeans.setNumClusters(6);
		kmeans.buildClusterer(cpu);
		int[] assignments = kmeans.getAssignments();
		return assignments;
	
	} */
	
	public void loadArff(String arffInput){
		DataSource source = null;
		try {
			source = new DataSource(arffInput);
			Instances data = source.getDataSet();
			data.setClassIndex(data.numAttributes()-1);
			Remove filter = new Remove();
			filter.setAttributeIndices(""+(data.classIndex()+1));
			filter.setInputFormat(data);
			cpu = Filter.useFilter(data, filter);
		} catch (Exception e1) {
		}
	}
	
	public static double log2(double x){
	    return (Math.log(x) / Math.log(2));
	}
	
	
	
	public static void main(String[] args) throws Exception {
		
		DataSource ds =  new DataSource("/Users/Simone/eclipse-workspace/Tesi/src/PostOperationDataset/postoperative-patient-data.csv");
		Instances dataSet = ds.getDataSet();
		int[] classLabel = new int[dataSet.numInstances()];
		int j = 8;
		for(int i = 0; i< dataSet.numInstances(); i++) {
			if(dataSet.get(i).stringValue(j).equals("A")) {
				classLabel[i] = 1;
			}else if(dataSet.get(i).stringValue(j).equals("S")) {
				classLabel[i] = 2;
			}else if(dataSet.get(i).stringValue(j).equals("I")) {
				classLabel[i] = 3;
			}
			
			//System.out.println(classLabel[i]);
		}
		
		int count = 0;
		for(int labelNum : classLabel) {
			System.out.printf("Instance %d -> OriginalLabel %d\n", (count+1), labelNum);
			count++;
		}
		
		int a = 0;
		int b = 0;
		int c = 0;
		for(int i = 0; i< classLabel.length; i++) {
			if(classLabel[i]==1) {
				a++;
			}else if(classLabel[i]==2) {
				b++;
			}
			else if(classLabel[i]==3) {
				c++;
			}
		}
		System.out.println("cluster a: "+a+", cluster b: "+b+", cluster c: "+c);
		double totalInstances = dataSet.numInstances();
		double entropyClass;
		//System.out.println("A: "+a/totalInstances);
		entropyClass = (-(a/totalInstances)*log2(a/totalInstances)) + (-(b/totalInstances)*log2(b/totalInstances)) + (-(c/totalInstances)*log2(c/totalInstances));
		double pA = (-(a/totalInstances)*log2(a/totalInstances));
		double pB = (-(b/totalInstances)*log2(b/totalInstances));
		double pC = (-(c/totalInstances)*log2(c/totalInstances));
		System.out.println("P A: "+pA);
		System.out.println("P B: "+pB);
		System.out.println("P C: "+pC);
		System.out.println("Entropia Class Label: "+entropyClass);
		
		
		// SINGOLA STAMPA
		
		DilcaDistance dd = new DilcaDistance();
		System.out.println("*******************************************");
		PostOperativeNMISoloDilca clusterDilca = new PostOperativeNMISoloDilca();
		clusterDilca.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/PostOperationDataset/postoperative-patient-data.arff");
		//clusterDilca.clusterData(dd);
		//System.out.println(getCluster(dd));
		  
		//CLUSTER DEFINITI CON DILCA APPLICANDO DIFF PRIVACY
		int[] classCluster = clusterDilca.clusterData(dd);
		NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
		double value = nnn.measure(classLabel, classCluster);
		System.out.println("value: "+value);
		
		
		
		double sigma = 0.0;
		/*
		FileWriter writer = new FileWriter("/Users/Simone/eclipse-workspace/Tesi/src/PostOperationDataset/PostOperativeMeanOriginal.txt", true);
		
			for(int ciclosig = 0; ciclosig<10;ciclosig++) {
				sigma = sigma+0.1;
				DilcaDistanceMean dd = new DilcaDistanceMean();
				
				dd.setSigma(sigma);
				System.out.println("*******************************************");
				PostOperativeNMISoloDilca clusterDilca = new PostOperativeNMISoloDilca();
				clusterDilca.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/PostOperationDataset/postoperative-patient-data.arff");
				int[] classCluster = clusterDilca.clusterData(dd);
				NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
				double value = nnn.measure(classLabel, classCluster);
				Double.toString(value);
				System.out.println("value: "+value);
				writer.write("Sigma: "+sigma+" NMI: "+value+";   "+System.getProperty("line.separator"));	
			}
		*/
		
		
		
		/*
		double epsilon = 0.0;
		double sigma = 0.0;
		FileWriter writer = new FileWriter("/Users/Simone/eclipse-workspace/Tesi/src/PostOperationDataset/PostOperativeMEAN2.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			epsilon = epsilon+0.1;
			sigma = 0.0;
			for(int ciclosig = 0; ciclosig<10;ciclosig++) {
				sigma = sigma+0.1;
				DilcaDistanceDiffPrivMean dd = new DilcaDistanceDiffPrivMean();
				dd.setEpsilon(epsilon);
				dd.setSigma(sigma);
				System.out.println("*******************************************");
				PostOperativeNMIDilcaDiffPrivMean clusterDilca = new PostOperativeNMIDilcaDiffPrivMean();
				clusterDilca.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/PostOperationDataset/postoperative-patient-data.arff");
				int[] classCluster = clusterDilca.clusterData(dd);
				NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
				double value = nnn.measure(classLabel, classCluster);
				Double.toString(value);
				System.out.println("value: "+value);
				writer.write("Epsilon: "+epsilon+ ", sigma: "+sigma+" NMI: "+value+";   "+System.getProperty("line.separator"));	
			}
		}
		*/
		
		
		/*
		double epsilon = 0.5;
		double sigma = 0.0;
		FileWriter writer = new FileWriter("/Users/Simone/eclipse-workspace/Tesi/src/PostOperationDataset/PostOperativeMEAN3.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			epsilon = epsilon+0.5;
			sigma = 0.0;
			for(int ciclosig = 0; ciclosig<10;ciclosig++) {
				sigma = sigma+0.1;
				DilcaDistanceDiffPrivMean dd = new DilcaDistanceDiffPrivMean();
				dd.setEpsilon(epsilon);
				dd.setSigma(sigma);
				System.out.println("*******************************************");
				PostOperativeNMIDilcaDiffPrivMean clusterDilca = new PostOperativeNMIDilcaDiffPrivMean();
				clusterDilca.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/PostOperationDataset/postoperative-patient-data.arff");
				int[] classCluster = clusterDilca.clusterData(dd);
				NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
				double value = nnn.measure(classLabel, classCluster);
				Double.toString(value);
				System.out.println("value: "+value);
				writer.write("Epsilon: "+epsilon+ ", sigma: "+sigma+" NMI: "+value+";  "+System.getProperty("line.separator"));	
			}		
		}
		*/
		
		
		

		//writer.close();
	}
	
}
