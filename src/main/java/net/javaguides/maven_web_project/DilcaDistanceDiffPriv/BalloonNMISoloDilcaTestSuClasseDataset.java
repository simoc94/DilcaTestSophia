package net.javaguides.maven_web_project.DilcaDistanceDiffPriv;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DilcaDistance.DilcaDistance;
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

public class BalloonNMISoloDilcaTestSuClasseDataset {
	
	public static String[] addOneIntToArrayString(String[] assignments , String value) {

	    String[] newArray = new String[assignments.length + 1];
	    for (int index = 0; index < assignments.length; index++) {
	        newArray[index] = assignments[index];
	    }

	    newArray[newArray.length - 1] = value;
	    return newArray;
	}
	
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
	public static int[] clusterData(DistanceFunction df, int numCluster) throws Exception{
		hc = new HierarchicalClusterer();
		//kmeans.setSeed(10);
		hc.setLinkType(new SelectedTag(5, hc.TAGS_LINK_TYPE));
		hc.setDistanceFunction(df);
		//kmeans.setPreserveInstancesOrder(true);
		//kmeans.setMaxIterations(1000);
		hc.setNumClusters(numCluster);
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
	
		
	public static void loadArff(String arffInput){
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
	
	public static int[] loadArffClasse(String arffInput) throws Exception{
		try {
		DataSource source = null;
		source = new DataSource(arffInput);
		Instances data = source.getDataSet();
		int classe = data.numAttributes()-1;
		String[] assignments = new String[0];
		for(int i = 0; i<data.numInstances();i++) {
			String value = data.get(i).stringValue(classe);
			assignments = addOneIntToArrayString(assignments, value);
		}
		int pippo= 0;
		for(String classeNum : assignments) {
			System.out.printf("Instance %d -> Cluster %s\n", (pippo+1), classeNum);
			pippo++;
		}
		
		Map<String, Integer> stringsById = new HashMap<>();
		int q = 0;
		for(int i=0; i<assignments.length;i++) {
			for(int j=1;j<assignments.length;j++) {
				if(!stringsById.containsKey(assignments[i])) {
					//System.out.print("sono bello");
					stringsById.put(assignments[i], q);
				}
				
				if(assignments[j].equals(assignments[i])) {
					stringsById.put(assignments[j],q);
				}
				
			}
			q++;
		}
		
		int[] speranza = new int[assignments.length];
		for(int i = 0; i<speranza.length;i++) {
			speranza[i] = stringsById.get(assignments[i]);
		}
		System.out.println();
		for(int i = 0; i<speranza.length;i++) {
			System.out.println(speranza[i]);
		}
	
		return speranza;
		
		
		}catch(IllegalArgumentException e){
			System.out.println("Wrong path! Please enter one correct");
		}
		return null;
		
	}
	
	public static int diffValues(int[] numArray){
	    int numOfDifferentVals = 0;

	    ArrayList<Integer> diffNum = new ArrayList<>();

	    for(int i=0; i<numArray.length; i++){
	        if(!diffNum.contains(numArray[i])){
	            diffNum.add(numArray[i]);
	        }
	    }

	    if(diffNum.size()==1){
	            numOfDifferentVals = 0;
	    }
	    else{
	          numOfDifferentVals = diffNum.size();
	        } 

	   return numOfDifferentVals;
	}
	
	public static double log2(double x){
	    return (Math.log(x) / Math.log(2));
	}
	
public static void main(String[]args) throws Exception {
		
		DataSource ds =  new DataSource("C:\\Users\\Simone\\eclipse-workspace\\Tesi\\src\\BalloonDataset\\balloons20.csv");
		Instances dataSet = ds.getDataSet();
		int[] classLabelInt = new int[dataSet.numInstances()];
		//String[] classLabel = new String[dataSet.numInstances()];
	/*	int j = 4;
		for(int i = 0; i< dataSet.numInstances(); i++) {
			if(dataSet.get(i).stringValue(j).equals("T")) {
				classLabel[i] = 1;
			}else if(dataSet.get(i).stringValue(j).equals("F")) {
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
		double totalInstances = dataSet.numInstances();
		double entropyClass;
		//System.out.println("A: "+a/totalInstances);
		entropyClass = (-(a/totalInstances)*log2(a/totalInstances)) + (-(b/totalInstances)*log2(b/totalInstances));
		double pA = (-(a/totalInstances)*log2(a/totalInstances));
		System.out.println("P A: "+pA);
		System.out.println("Entropia Class Label: "+entropyClass);
		*/
		BalloonNMISoloDilcaTestSuClasseDataset clusterClass = new BalloonNMISoloDilcaTestSuClasseDataset();
		classLabelInt = clusterClass.loadArffClasse("C:\\Users\\Simone\\eclipse-workspace\\Tesi\\src\\BalloonDataset\\balloons20.arff");
				
		
		
		DilcaDistance dd = new DilcaDistance();
		System.out.println("*******************************************");
		BalloonNMISoloDilcaTestSuClasseDataset clusterDilca = new BalloonNMISoloDilcaTestSuClasseDataset();
		clusterDilca.loadArff("C:\\Users\\Simone\\eclipse-workspace\\Tesi\\src\\BalloonDataset\\balloons20.arff");
		
		int[] classCluster = clusterDilca.clusterData(dd, 2);
		
		NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
		double value = nnn.measure(classLabelInt, classCluster);
		System.out.println("value: "+value);
		System.out.println("numero di valori nella classe:" +diffValues(classLabelInt));
		  
		    
		
	}

}
