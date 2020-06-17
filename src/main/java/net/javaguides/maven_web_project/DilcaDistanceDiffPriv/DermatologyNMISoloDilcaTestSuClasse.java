package net.javaguides.maven_web_project.DilcaDistanceDiffPriv;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import DilcaDistance.DilcaDistance;
import DilcaDistance.DilcaDistanceDiffPrivRR;
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
 * Con Dilca Distance che identifica il context tramite mean value, l'alfa migliore è 0.8 , che da un valore di NMI di 0.8538501
 * ATTENZIONE quando si usa contextSelection tramite Mean Value e i vari alfa a modificare i vari hYaa, hYbb, hYcc, hYdd, hYee, hYff
 */

public class DermatologyNMISoloDilcaTestSuClasse {
	
	
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
	public int[] clusterData(DistanceFunction df) throws Exception{
		hc = new HierarchicalClusterer();
		//kmeans.setSeed(10);
		hc.setLinkType(new SelectedTag(5, hc.TAGS_LINK_TYPE)); //HO MESSO DIRETTAMENTE WARD NELLA CLASSE HIERARCHICALCLUSTERER PERCHE NON ANDAVA PER NON SO QUALE MOTIVO
		hc.setDistanceFunction(df);
		//kmeans.setPreserveInstancesOrder(true);
		//kmeans.setMaxIterations(1000);
		hc.setNumClusters(6);
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
	
	}
*/	
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
	
	
	
	public int[] loadArffClasse(String arffInput) throws Exception{
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
		
		
		
		
	}

	
	public static double log2(double x){
	    return (Math.log(x) / Math.log(2));
	}
	
	
	
	public static void main(String[] args) throws Exception {
		
		DataSource ds =  new DataSource("/Users/Simone/eclipse-workspace/Tesi/src/DermatologyDataset/dermatologyUCI.csv");
		Instances dataSet = ds.getDataSet();;
		int[] classLabel = new int[dataSet.numInstances()];
	/*	int j = 34;
		for(int i = 0; i< dataSet.numInstances(); i++) {
			classLabel[i] = (int) dataSet.get(i).value(j);
			//System.out.println(classLabel[i]);
		}

		
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		int e = 0;
		int f = 0;
		for(int i = 0; i< classLabel.length; i++) {
			if(classLabel[i] == 1) {
				a++;
			}else if(classLabel[i] == 2) {
				b++;
			}else if(classLabel[i] == 3) {
				c++;
			}else if(classLabel[i] == 4) {
				d++;
			}else if(classLabel[i] == 5) {
				e++;
			}else if(classLabel[i] == 6) {
				f++;
			}
		}
		System.out.println("cluster a: "+a+", cluster b: "+b+", cluster c: "+c+", cluster d: "+d+", cluster e: "+e+", cluster f: "+f);
		double totalInstances = dataSet.numInstances();
		double entropyClass;
		entropyClass = (-(a/totalInstances)*log2(a/totalInstances)) + (-(b/totalInstances)*log2(b/totalInstances)) +(-(c/totalInstances)*log2(c/totalInstances))+ (-(d/totalInstances)*log2(d/totalInstances))+ (-(e/totalInstances)*log2(e/totalInstances))+ (-(f/totalInstances)*log2(f/totalInstances));
		double pA = (-(a/totalInstances)*log2(a/totalInstances));
		System.out.println("P A: "+pA);
		System.out.println("Entropia Class Label: "+entropyClass);
		*/
		
		DermatologyNMISoloDilcaTestSuClasse clusterClass = new DermatologyNMISoloDilcaTestSuClasse();
		classLabel = clusterClass.loadArffClasse("/Users/Simone/eclipse-workspace/Tesi/src/DermatologyDataset/dermatologyUCI.arff");
		for(int i = 0; i<3;i++) {
		double epsilon = 0.1;
		DilcaDistanceDiffPrivRR dd = new DilcaDistanceDiffPrivRR(epsilon);
		  System.out.println("*******************************************");
		  DermatologyNMISoloDilcaTestSuClasse clusterDilca = new DermatologyNMISoloDilcaTestSuClasse();
		  clusterDilca.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/DermatologyDataset/dermatologyUCI.arff");
		  //clusterDilca.clusterData(dd);
		  //System.out.println(getCluster(dd));
		  
		  //CLUSTER DEFINITI CON SOLO DILCA 
		  int[] classCluster = clusterDilca.clusterData(dd);
		  NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
		  double value = nnn.measure(classLabel, classCluster);
		  System.out.println("value: "+value);
		  System.out.println("value joint: "+nnn.joint(classLabel, classCluster) + "value sum: "+nnn.sum(classLabel, classCluster));
		  
		}
	}
	
}
