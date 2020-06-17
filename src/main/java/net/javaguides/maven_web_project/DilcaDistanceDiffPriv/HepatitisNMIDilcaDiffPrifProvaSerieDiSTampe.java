package net.javaguides.maven_web_project.DilcaDistanceDiffPriv;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;

import DilcaDistance.DilcaDistanceDiffPrivRR;
import smile.validation.NormalizedMutualInformation;
import smile.validation.NormalizedMutualInformation.Method;
import weka.clusterers.HierarchicalClusterer;
import weka.core.DistanceFunction;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class HepatitisNMIDilcaDiffPrifProvaSerieDiSTampe {
	
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
		hc.setNumClusters(2);
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
	
public static void main(String[]args) throws Exception {
		
		DataSource ds =  new DataSource("C:\\Users\\Simone\\eclipse-workspace\\Tesi\\src\\HepatitisDataset\\csv_result-dataset_55_hepatitis.csv");
		Instances dataSet = ds.getDataSet();
		int[] classLabel = new int[dataSet.numInstances()];
		int j = 20;
		for(int i = 0; i< dataSet.numInstances(); i++) {
			if(dataSet.get(i).stringValue(j).equals("LIVE")) {
				classLabel[i] = 1;
			}else if(dataSet.get(i).stringValue(j).equals("DIE")) {
				classLabel[i] = 2;
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
		double epsilon = 0.00;
		FileWriter writer = new FileWriter("C:\\Users\\Simone\\eclipse-workspace\\Tesi\\src\\HepatitisDataset\\HepatitisRR.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			epsilon = epsilon+0.01;
			DilcaDistanceDiffPrivRR dd = new DilcaDistanceDiffPrivRR();
			dd.setEpsilon(epsilon);
			System.out.println("*******************************************");
			HepatitisNMIDilcaDiffPrifProvaSerieDiSTampe clusterDilca = new HepatitisNMIDilcaDiffPrifProvaSerieDiSTampe();
			clusterDilca.loadArff("C:\\Users\\Simone\\eclipse-workspace\\Tesi\\src\\HepatitisDataset\\dataset_55_hepatitis.arff");
			int[] classCluster = clusterDilca.clusterData(dd);
			NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
			double value = nnn.measure(classLabel, classCluster);
			Double.toString(value);
			System.out.println("value: "+value);
			writer.write("Epsilon: "+epsilon+ " NMI: "+value+";   ");		
		}
		
		epsilon = 0.0;
		for(int ciclo = 1; ciclo<10; ciclo++) {
			epsilon = epsilon+0.1;
			DilcaDistanceDiffPrivRR dd = new DilcaDistanceDiffPrivRR();
			dd.setEpsilon(epsilon);
			System.out.println("*******************************************");
			HepatitisNMIDilcaDiffPrifProvaSerieDiSTampe clusterDilca = new HepatitisNMIDilcaDiffPrifProvaSerieDiSTampe();
			clusterDilca.loadArff("C:\\Users\\Simone\\eclipse-workspace\\Tesi\\src\\HepatitisDataset\\dataset_55_hepatitis.arff");
			int[] classCluster = clusterDilca.clusterData(dd);
			NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
			double value = nnn.measure(classLabel, classCluster);
			Double.toString(value);
			System.out.println("value: "+value);
			writer.write("Epsilon: "+epsilon+ " NMI: "+value+";   ");		
		}
		
		epsilon = 0.5;
		for(int ciclo = 1; ciclo<10; ciclo++) {
			epsilon = epsilon+0.5;
			DilcaDistanceDiffPrivRR dd = new DilcaDistanceDiffPrivRR();
			dd.setEpsilon(epsilon);
			System.out.println("*******************************************");
			HepatitisNMIDilcaDiffPrifProvaSerieDiSTampe clusterDilca = new HepatitisNMIDilcaDiffPrifProvaSerieDiSTampe();
			clusterDilca.loadArff("C:\\Users\\Simone\\eclipse-workspace\\Tesi\\src\\HepatitisDataset\\dataset_55_hepatitis.arff");
			int[] classCluster = clusterDilca.clusterData(dd);
			NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
			double value = nnn.measure(classLabel, classCluster);
			Double.toString(value);
			System.out.println("value: "+value);
			writer.write("Epsilon: "+epsilon+ " NMI: "+value+";   ");		
		}

		writer.close();
		  

		  
		    
		
	}

}
