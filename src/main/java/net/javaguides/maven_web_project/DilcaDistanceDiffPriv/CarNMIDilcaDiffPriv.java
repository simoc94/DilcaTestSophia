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

public class CarNMIDilcaDiffPriv {
	
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
		hc.setNumClusters(4);
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
		
		DataSource ds =  new DataSource("/Users/Simone/eclipse-workspace/Tesi/src/CarDataset/car_evaluation.csv");
		Instances dataSet = ds.getDataSet();
		int[] classLabel = new int[dataSet.numInstances()];
		int j = 6;
		for(int i = 0; i< dataSet.numInstances(); i++) {
			if(dataSet.get(i).stringValue(j).equals("unacc")) {
				classLabel[i] = 1;
			}else if(dataSet.get(i).stringValue(j).equals("acc")) {
				classLabel[i] = 2;
			}else if(dataSet.get(i).stringValue(j).equals("vgood")) {
				classLabel[i] = 3;
			}else if(dataSet.get(i).stringValue(j).equals("good")) {
				classLabel[i] = 4;
			}
			
			System.out.println(classLabel[i]);
		}
		
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		for(int i = 0; i< classLabel.length; i++) {
			if(classLabel[i]==1) {
				a++;
			}else if(classLabel[i]==2) {
				b++;
			}
			else if(classLabel[i]==3) {
				c++;
			}
			else if(classLabel[i]==4) {
				d++;
			}
		}
		System.out.println("cluster a: "+a+", cluster b: "+b+", cluster c: "+c+", cluster d: "+d);
		double totalInstances = dataSet.numInstances();
		double entropyClass;
		//System.out.println("A: "+a/totalInstances);
		entropyClass = (-(a/totalInstances)*log2(a/totalInstances)) + (-(b/totalInstances)*log2(b/totalInstances)) +  (-(c/totalInstances)*log2(c/totalInstances)) +  (-(d/totalInstances)*log2(d/totalInstances));
		double pA = (-(a/totalInstances)*log2(a/totalInstances));
		System.out.println("P A: "+pA);
		System.out.println("Entropia Class Label: "+entropyClass);
		
		
		DilcaDistanceDiffPrivRR dd = new DilcaDistanceDiffPrivRR();
		System.out.println("*******************************************");
		CarNMIDilcaDiffPriv clusterDilca = new CarNMIDilcaDiffPriv();
		clusterDilca.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/CarDataset/car_evaluation.arff");
		
		int[] classCluster = clusterDilca.clusterData(dd);
		
		NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
		double value = nnn.measure(classLabel, classCluster);
		System.out.println("value: "+value);
		int aa = 0;
		int bb = 0;
		int cc = 0;
		int de = 0;
		for(int i = 0; i < classCluster.length; i++) {
			if(classCluster[i] == 0) {
				aa++;
			 }else if(classCluster[i]==1) {
				 bb++;
			 }else if(classCluster[i]==2) {
				 cc++;
			 }else if(classCluster[i]==3) {
				 de++;
			 }
		  }
		  System.out.println("cluster aa: "+aa+", cluster bb: "+bb+", cluster cc: "+cc+", cluster de: "+de);
		  double entropyCluster;
		  entropyCluster = (-(aa/totalInstances)*log2(aa/totalInstances)) + (-(bb/totalInstances)*log2(bb/totalInstances)) + (-(cc/totalInstances)*log2(cc/totalInstances)) +(-(de/totalInstances)*log2(de/totalInstances));
		  System.out.println("entropy Cluster: "+entropyCluster);
		  
		//P(Y=a|C=aa)... a è quando classLabel[i] = 1, mentre aa è quando classCluster è 0
		  int aaa = 0;
		  int aab = 0;
		  int aac = 0;
		  int aad = 0;
		  
		  for(int i=0; i< classCluster.length;i++) {
			  if(classLabel[i] == 1 && classCluster[i] == 0) {
				  aaa++;
			  }else if(classLabel[i] == 2 && classCluster[i] == 0) {
				  aab++;
			  }else if(classLabel[i] == 3 && classCluster[i] == 0) {
				  aac++;
			  }else if(classLabel[i] == 4 && classCluster[i] == 0) {
				  aad++;
			  }
		  }
		  double paaa = aaa/(double)aa;
		  double pbaa = aab/(double)aa;
		  double pcaa = aac/(double)aa;
		  double pdaa = aad/(double)aa;
		  System.out.println("P(Y=a|C=aa): "+paaa+", P(Y=b|C=aa): "+pbaa+", P(Y=c|C=aa): "+pcaa+", P(Y=d|C=aa): "+pdaa);
		  
		//ora calcola P(Y=a|C=bb) ecc
		  int bba = 0;
		  int bbb = 0;
		  int bbc = 0;
		  int bbd = 0;
		  for(int i=0; i< classCluster.length;i++) {
			  if(classLabel[i] == 1 && classCluster[i] == 1) {
				  bba++;
			  }else if(classLabel[i] == 2 && classCluster[i] == 1) {
				  bbb++;
			  }else if(classLabel[i] == 3 && classCluster[i] == 1) {
				  bbc++;
			  }else if(classLabel[i] == 4 && classCluster[i] == 1) {
				  bbd++;
			  }
		  }
		  double pabb = bba/(double)bb;
		  double pbbb = bbb/(double)bb;
		  double pcbb = bbc/(double)bb;
		  double pdbb = bbd/(double)bb;
		  System.out.println("P(Y=a|C=bb): "+pabb+", P(Y=b|C=bb): "+pbbb+", P(Y=c|C=bb): "+pcbb+", P(Y=d|C=bb): "+pdbb);
		  
		//ora calcola P(Y=a|C=cc) ecc
		  int cca = 0;
		  int ccb = 0;
		  int ccc = 0;
		  int ccd = 0;
		  for(int i=0; i< classCluster.length;i++) {
			  if(classLabel[i] == 1 && classCluster[i] == 2) {
				  cca++;
			  }else if(classLabel[i] == 2 && classCluster[i] == 2) {
				  ccb++;
			  }else if(classLabel[i] == 3 && classCluster[i] == 2) {
				  ccc++;
			  }else if(classLabel[i] == 4 && classCluster[i] == 2) {
				  ccd++;
			  }
		  }
		  double pacc = cca/(double)cc;
		  double pbcc = ccb/(double)cc;
		  double pccc = ccc/(double)cc;
		  double pdcc = ccd/(double)cc;
		  System.out.println("P(Y=a|C=cc): "+pacc+", P(Y=b|C=cc): "+pbcc+", P(Y=c|C=cc): "+pccc+", P(Y=d|C=cc): "+pdcc);
		  
		//ora calcola P(Y=a|C=dd) ecc
		  int dda = 0;
		  int ddb = 0;
		  int ddc = 0;
		  int ddd = 0;
		  for(int i=0; i< classCluster.length;i++) {
			  if(classLabel[i] == 1 && classCluster[i] == 3) {
				  dda++;
			  }else if(classLabel[i] == 2 && classCluster[i] == 3) {
				  ddb++;
			  }else if(classLabel[i] == 3 && classCluster[i] == 3) {
				  ddc++;
			  }else if(classLabel[i] == 4 && classCluster[i] == 3) {
				  ddd++;
			  }
		  }
		  double padd = dda/(double)de;
		  double pbdd = ddb/(double)de;
		  double pcdd = ddc/(double)de;
		  double pddd = ddd/(double)de;
		  System.out.println("P(Y=a|C=dd): "+padd+", P(Y=b|C=dd): "+pbdd+", P(Y=c|C=dd): "+pcdd+", P(Y=d|C=dd): "+pddd);
		  
		  
		//double hYaa = (-(1/6)*((paaa*log2(paaa))+(pbaa*log2(pbaa))+ (pcaa*log2(pcaa))+ (pdaa*log2(pdaa))+(peaa*log2(peaa))+(pfaa*log2(pfaa))));
		  double hYaa = (-((aaa+aab+aac+aad)/totalInstances)*((paaa*log2(paaa))+(pbaa*log2(pbaa))+(pcaa*log2(pcaa))+(pdaa*log2(pdaa))));
		  double hYbb = (-((bba+bbb+bbc+bbd)/totalInstances)*((pabb*log2(pabb))+(pbbb*log2(pbbb))+(pcbb*log2(pcbb))+(pdbb*log2(pdbb))));
		  double hYcc = (-((cca+ccb+ccc+ccd)/totalInstances)*((pacc*log2(pacc))+(pbcc*log2(pbcc))+(pccc*log2(pccc))+(pdcc*log2(pdcc))));
		  double hYdd = (-((dda+ddb+ddc+ddd)/totalInstances)*((padd*log2(padd))+(pbdd*log2(pbdd))+(pcdd*log2(pcdd))+(pddd*log2(pddd))));
		  System.out.println("H(Y|C=aa): "+hYaa);
		  System.out.println("H(Y|C=bb): "+hYbb);
		  System.out.println("H(Y|C=cc): "+hYcc);
		  System.out.println("H(Y|C=dd): "+hYdd);
		  
		  double hYC = hYaa + hYbb + hYcc + hYdd;
		  System.out.println("H(Y|C): "+hYC);
		  double iYC = entropyClass - hYC;
		  System.out.println("I(Y,C): "+iYC);
		  
		  double NMI = (2*iYC)/(entropyClass+entropyCluster);
		  System.out.println("Valore di NMI tra cluster definito dalla classe del dataset e cluster definiti da solo Dilca (senza DifferentialPrivacy): "+NMI);

		  System.out.println("cluster a: "+a+", cluster b: "+b);
		    
		
	}

}
