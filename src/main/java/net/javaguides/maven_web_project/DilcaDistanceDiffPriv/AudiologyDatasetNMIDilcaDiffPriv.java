package net.javaguides.maven_web_project.DilcaDistanceDiffPriv;

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

public class AudiologyDatasetNMIDilcaDiffPriv {
	
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
		hc.setNumClusters(24);
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
		
		DataSource ds =  new DataSource("C:\\Users\\Simone\\eclipse-workspace\\Tesi\\src\\AudiologyDataset\\audiology_csv.csv");
		Instances dataSet = ds.getDataSet();
		int[] classLabel = new int[dataSet.numInstances()];
		int j = 70;
		for(int i = 0; i< dataSet.numInstances(); i++) {
			if(dataSet.get(i).stringValue(j).equals("acoustic_neuroma")) {
				classLabel[i] = 1;
			}else if(dataSet.get(i).stringValue(j).equals("bells_palsy")) {
				classLabel[i] = 2;
			}else if(dataSet.get(i).stringValue(j).equals("cochlear_age")) {
				classLabel[i] = 3;
			}else if(dataSet.get(i).stringValue(j).equals("cochlear_age_and_noise")) {
				classLabel[i] = 4;
			}else if(dataSet.get(i).stringValue(j).equals("cochlear_age_plus_poss_menieres")) {
				classLabel[i] = 5;
			}else if(dataSet.get(i).stringValue(j).equals("cochlear_noise_and_heredity")) {
				classLabel[i] = 6;
			}else if(dataSet.get(i).stringValue(j).equals("cochlear_poss_noise")) {
				classLabel[i] = 7;
			}else if(dataSet.get(i).stringValue(j).equals("cochlear_unknown")) {
				classLabel[i] = 8;
			}else if(dataSet.get(i).stringValue(j).equals("conductive_discontinuity")) {
				classLabel[i] = 9;
			}else if(dataSet.get(i).stringValue(j).equals("conductive_fixation")) {
				classLabel[i] = 10;
			}else if(dataSet.get(i).stringValue(j).equals("mixed_cochlear_age_fixation")) {
				classLabel[i] = 11;
			}else if(dataSet.get(i).stringValue(j).equals("mixed_cochlear_age_otitis_media")) {
				classLabel[i] = 12;
			}else if(dataSet.get(i).stringValue(j).equals("mixed_cochlear_age_s_om")) {
				classLabel[i] = 13;
			}else if(dataSet.get(i).stringValue(j).equals("mixed_cochlear_unk_discontinuity")) {
				classLabel[i] = 14;
			}else if(dataSet.get(i).stringValue(j).equals("mixed_cochlear_unk_fixation")) {
				classLabel[i] = 15;
			}else if(dataSet.get(i).stringValue(j).equals("mixed_cochlear_unk_ser_om")) {
				classLabel[i] = 16;
			}else if(dataSet.get(i).stringValue(j).equals("mixed_poss_central_om")) {
				classLabel[i] = 17;
			}else if(dataSet.get(i).stringValue(j).equals("mixed_poss_noise_om")) {
				classLabel[i] = 18;
			}else if(dataSet.get(i).stringValue(j).equals("normal_ear")) {
				classLabel[i] = 19;
			}else if(dataSet.get(i).stringValue(j).equals("otitis_media")) {
				classLabel[i] = 20;
			}else if(dataSet.get(i).stringValue(j).equals("poss_central")) {
				classLabel[i] = 21;
			}else if(dataSet.get(i).stringValue(j).equals("possible_brainstem_disorder")) {
				classLabel[i] = 22;
			}else if(dataSet.get(i).stringValue(j).equals("possible_menieres")) {
				classLabel[i] = 23;
			}else if(dataSet.get(i).stringValue(j).equals("retrocochlear_unknown")) {
				classLabel[i] = 24;
			}

			System.out.println(classLabel[i]);
		}
		
		
		
		
		DilcaDistanceDiffPrivRR dd = new DilcaDistanceDiffPrivRR(0.1);
		System.out.println("*******************************************");
		AudiologyDatasetNMIDilcaDiffPriv clusterDilca = new AudiologyDatasetNMIDilcaDiffPriv();
		clusterDilca.loadArff("C:\\Users\\Simone\\eclipse-workspace\\Tesi\\src\\AudiologyDataset\\audiology_arff.arff");
		
		int[] classCluster = clusterDilca.clusterData(dd);
		
		NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
		double value = nnn.measure(classLabel, classCluster);
		System.out.println("value: "+value);
		
		  
		
		  

		  
		    
		
	}

}
