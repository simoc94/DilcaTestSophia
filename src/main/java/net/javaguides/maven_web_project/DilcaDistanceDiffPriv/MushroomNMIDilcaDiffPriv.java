package net.javaguides.maven_web_project.DilcaDistanceDiffPriv;
import DilcaDistance.DilcaDistance;
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

public class MushroomNMIDilcaDiffPriv {
	
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
		
		DataSource ds =  new DataSource("/Users/Simone/eclipse-workspace/Tesi/src/MushroomDataset/mushroom.csv");
		Instances dataSet = ds.getDataSet();
		int[] classLabel = new int[dataSet.numInstances()];
		int j = 22;
		for(int i = 0; i< dataSet.numInstances(); i++) {
			if(dataSet.get(i).stringValue(j).equals("p")) {
				classLabel[i] = 1;
			}else if(dataSet.get(i).stringValue(j).equals("e")) {
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
		
		
		DilcaDistance dd = new DilcaDistance();
		System.out.println("*******************************************");
		MushroomNMIDilcaDiffPriv clusterDilca = new MushroomNMIDilcaDiffPriv();
		clusterDilca.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/MushroomDataset/mushroom.arff");
		
		int[] classCluster = clusterDilca.clusterData(dd);
		NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
		double value = nnn.measure(classLabel, classCluster);
		System.out.println("value: "+value);
		
		int aa = 0;
		int bb = 0;
		for(int i = 0; i < classCluster.length; i++) {
			if(classCluster[i] == 0) {
				aa++;
			 }else if(classCluster[i]==1) {
				 bb++;
			 }
		  }
		  System.out.println("cluster aa: "+aa+", cluster bb: "+bb);
		  double entropyCluster;
		  entropyCluster = (-(aa/totalInstances)*log2(aa/totalInstances)) + (-(bb/totalInstances)*log2(bb/totalInstances));
		  System.out.println("entropy Cluster: "+entropyCluster);
		  
		//P(Y=a|C=aa)... a è quando classLabel[i] = 1, mentre aa è quando classCluster è 0
		  int aaa = 0;
		  int aab = 0;
		  
		  for(int i=0; i< classCluster.length;i++) {
			  if(classLabel[i] == 1 && classCluster[i] == 0) {
				  aaa++;
			  }else if(classLabel[i] == 2 && classCluster[i] == 0) {
				  aab++;
			  }
		  }
		  double paaa = aaa/(double)aa;
		  double pbaa = aab/(double)aa;
		  System.out.println("P(Y=a|C=aa): "+paaa+", P(Y=b|C=aa): "+pbaa);
		  
		//ora calcola P(Y=a|C=bb) ecc
		  int bba = 0;
		  int bbb = 0;
		  for(int i=0; i< classCluster.length;i++) {
			  if(classLabel[i] == 1 && classCluster[i] == 1) {
				  bba++;
			  }else if(classLabel[i] == 2 && classCluster[i] == 1) {
				  bbb++;
			  }
		  }
		  double pabb = bba/(double)bb;
		  double pbbb = bbb/(double)bb;
		  System.out.println("P(Y=a|C=bb): "+pabb+", P(Y=b|C=bb): "+pbbb);
		  
		//double hYaa = (-(1/6)*((paaa*log2(paaa))+(pbaa*log2(pbaa))+ (pcaa*log2(pcaa))+ (pdaa*log2(pdaa))+(peaa*log2(peaa))+(pfaa*log2(pfaa))));
		  double Yaa = 0;
		  if(paaa!= 0) {
			  Yaa = Yaa + ((paaa*log2(paaa)));
		  }
		  if(pbaa!= 0) {
			  Yaa = Yaa + ((pbaa*log2(pbaa)));
		  }
		  double hYaa = (-((aaa+aab)/totalInstances)*Yaa);
		  //double hYaa = (-((aaa+aab)/totalInstances)*((paaa*log2(paaa))));
		  
		  double Ybb = 0;
		  if(pabb!= 0) {
			  Ybb = Ybb + ((pabb*log2(pabb)));
		  }
		  if(pbbb!= 0) {
			  Ybb = Ybb + ((pbbb*log2(pbbb)));
		  }
		  double hYbb = (-((bba+bbb)/totalInstances)*Ybb);
		  //double hYbb = (-((bba+bbb)/totalInstances)*((pbbb*log2(pbbb))));
		  System.out.println("H(Y|C=aa): "+hYaa);
		  System.out.println("H(Y|C=bb): "+hYbb);
		  
		  double hYC = hYaa + hYbb;
		  System.out.println("H(Y|C): "+hYC);
		  double iYC = entropyClass - hYC;
		  System.out.println("I(Y,C): "+iYC);
		  
		  double NMI = (2*iYC)/(entropyClass+entropyCluster);
		  System.out.println("Valore di NMI tra cluster definito dalla classe del dataset e cluster definiti da solo Dilca (senza DifferentialPrivacy): "+NMI);

		  System.out.println("cluster a: "+a+", cluster b: "+b);
		  System.out.println("entropy class: "+entropyClass);
		    
		
	}


}
