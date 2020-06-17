package net.javaguides.maven_web_project.DilcaDistanceDiffPriv;

import java.io.FileWriter;

import DilcaDistance.DilcaDistanceDiffPrivMean;
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

public class SoybeanNMIDilcaDiffPrivMean {
	
	
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
		hc.setNumClusters(19);
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
		
		DataSource ds =  new DataSource("C:\\Users\\Simone\\eclipse-workspace\\Tesi\\src\\SoybeanDataset\\soybean_csv.csv");
		Instances dataSet = ds.getDataSet();
		int[] classLabel = new int[dataSet.numInstances()];
		int j = 35;
		for(int i = 0; i< dataSet.numInstances(); i++) {
			if(dataSet.get(i).stringValue(j).equals("diaporthe-stem-canker")) {
				classLabel[i] = 1;
			}else if(dataSet.get(i).stringValue(j).equals("charcoal-rot")) {
				classLabel[i] = 2;
			}else if(dataSet.get(i).stringValue(j).equals("rhizoctonia-root-rot")) {
				classLabel[i] = 3;
			}else if(dataSet.get(i).stringValue(j).equals("phytophthora-rot")) {
				classLabel[i] = 4;
			}else if(dataSet.get(i).stringValue(j).equals("brown-stem-rot")) {
				classLabel[i] = 5;
			}else if(dataSet.get(i).stringValue(j).equals("powdery-mildew")) {
				classLabel[i] = 6;
			}else if(dataSet.get(i).stringValue(j).equals("downy-mildew")) {
				classLabel[i] = 7;
			}else if(dataSet.get(i).stringValue(j).equals("brown-spot")) {
				classLabel[i] = 8;
			}else if(dataSet.get(i).stringValue(j).equals("bacterial-blight")) {
				classLabel[i] = 9;
			}else if(dataSet.get(i).stringValue(j).equals("bacterial-pustule")) {
				classLabel[i] = 10;
			}else if(dataSet.get(i).stringValue(j).equals("purple-seed-stain")) {
				classLabel[i] = 11;
			}else if(dataSet.get(i).stringValue(j).equals("anthracnose")) {
				classLabel[i] = 12;
			}else if(dataSet.get(i).stringValue(j).equals("phyllosticta-leaf-spot")) {
				classLabel[i] = 13;
			}else if(dataSet.get(i).stringValue(j).equals("alternarialeaf-spot")) {
				classLabel[i] = 14;
			}else if(dataSet.get(i).stringValue(j).equals("frog-eye-leaf-spot")) {
				classLabel[i] = 15;
			}else if(dataSet.get(i).stringValue(j).equals("diaporthe-pod-&-stem-blight")) {
				classLabel[i] = 16;
			}else if(dataSet.get(i).stringValue(j).equals("cyst-nematode")) {
				classLabel[i] = 17;
			}else if(dataSet.get(i).stringValue(j).equals("2-4-d-injury")) {
				classLabel[i] = 18;
			}else if(dataSet.get(i).stringValue(j).equals("herbicide-injury")) {
				classLabel[i] = 19;
			}
			
			System.out.println(classLabel[i]);
		}
		
		int a = 0;
		int b = 0;
		for(int i = 0; i< classLabel.length; i++) {
			if(classLabel[i]==1) {
				a++;
			}else if(classLabel[i]==2) {
				b++;
			}
		}
		System.out.println("cluster a: "+a+", cluster b: "+b);
		double totalInstances = dataSet.numInstances();
		double entropyClass;
		//System.out.println("A: "+a/totalInstances);
		entropyClass = (-(a/totalInstances)*log2(a/totalInstances)) + (-(b/totalInstances)*log2(b/totalInstances));
		double pA = (-(a/totalInstances)*log2(a/totalInstances));
		System.out.println("P A: "+pA);
		System.out.println("Entropia Class Label: "+entropyClass);
		
		
		
		// SINGOLA STAMPA
		
		DilcaDistanceDiffPrivMean dd = new DilcaDistanceDiffPrivMean();
		dd.setEpsilon(0.4);
		dd.setSigma(0.3);
		System.out.println("*******************************************");
		SoybeanNMIDilcaDiffPrivMean clusterDilca = new SoybeanNMIDilcaDiffPrivMean();
		clusterDilca.loadArff("C:\\Users\\Simone\\eclipse-workspace\\Tesi\\src\\SoybeanDataset\\soybean_arff.arff");
		//clusterDilca.clusterData(dd);
		//System.out.println(getCluster(dd));
		  
		//CLUSTER DEFINITI CON DILCA APPLICANDO DIFF PRIVACY
		int[] classCluster = clusterDilca.clusterData(dd);
		NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
		double value = nnn.measure(classLabel, classCluster);
		System.out.println("value: "+value);
		
		
		/*
		double sigma = 0.0;
		double epsilon = 0.00;
		FileWriter writer = new FileWriter("/Users/Simone/eclipse-workspace/Tesi/src/SoybeanDataset/SoybeanMean.txt", true);
		for(int cicloeps = 1; cicloeps<10; cicloeps++) {
			epsilon = epsilon+0.01;
			sigma = 0.0;
			for(int ciclosig = 0; ciclosig<10;ciclosig++) {
				sigma = sigma+0.1;
				DilcaDistanceDiffPrivMean dd = new DilcaDistanceDiffPrivMean();
				dd.setEpsilon(epsilon);
				dd.setSigma(sigma);
				System.out.println("*******************************************");
				SoybeanNMIDilcaDiffPrivMean clusterDilca = new SoybeanNMIDilcaDiffPrivMean();
				clusterDilca.loadArff("C:\\Users\\Simone\\eclipse-workspace\\Tesi\\src\\SoybeanDataset\\soybean_arff.arff");
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
		double epsilon = 0.0;
		double sigma = 0.0;
		FileWriter writer = new FileWriter("/Users/Simone/eclipse-workspace/Tesi/src/SoybeanDataset/SoybeanMEAN2.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			epsilon = epsilon+0.1;
			sigma = 0.0;
			for(int ciclosig = 0; ciclosig<10;ciclosig++) {
				sigma = sigma+0.1;
				DilcaDistanceDiffPrivMean dd = new DilcaDistanceDiffPrivMean();
				dd.setEpsilon(epsilon);
				dd.setSigma(sigma);
				System.out.println("*******************************************");
				SoybeanNMIDilcaDiffPrivMean clusterDilca = new SoybeanNMIDilcaDiffPrivMean();
				clusterDilca.loadArff("C:\\Users\\Simone\\eclipse-workspace\\Tesi\\src\\SoybeanDataset\\soybean_arff.arff");
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
		FileWriter writer = new FileWriter("/Users/Simone/eclipse-workspace/Tesi/src/SoybeanDataset/SoybeanMEAN3.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			epsilon = epsilon+0.5;
			sigma = 0.0;
			for(int ciclosig = 0; ciclosig<10;ciclosig++) {
				sigma = sigma+0.1;
				DilcaDistanceDiffPrivMean dd = new DilcaDistanceDiffPrivMean();
				dd.setEpsilon(epsilon);
				dd.setSigma(sigma);
				System.out.println("*******************************************");
				SoybeanNMIDilcaDiffPrivMean clusterDilca = new SoybeanNMIDilcaDiffPrivMean();
				clusterDilca.loadArff("C:\\Users\\Simone\\eclipse-workspace\\Tesi\\src\\SoybeanDataset\\soybean_arff.arff");
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
