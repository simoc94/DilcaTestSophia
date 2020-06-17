package net.javaguides.maven_web_project.DilcaDistanceDiffPriv;
import java.io.File;
import java.util.Random;

import DilcaDistance.DilcaDistance;
import DilcaDistance.DilcaDistanceDPFinalDistance;
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

public class DermatologyNMISoloDilcaDPFinalDistance {
	
	
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
	
	public static double log2(double x){
	    return (Math.log(x) / Math.log(2));
	}
	
	
	
	public static void main(String[] args) throws Exception {
		
		DataSource ds =  new DataSource("/Users/Simone/eclipse-workspace/Tesi/src/DermatologyDataset/dermatologyUCI.csv");
		Instances dataSet = ds.getDataSet();;
		int[] classLabel = new int[dataSet.numInstances()];
		int j = 34;
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
		
		Random rand = new Random();
		DilcaDistanceDPFinalDistance dd = new DilcaDistanceDPFinalDistance(0.8);
		  System.out.println("*******************************************");
		  DermatologyNMISoloDilcaDPFinalDistance clusterDilca = new DermatologyNMISoloDilcaDPFinalDistance();
		  clusterDilca.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/DermatologyDataset/dermatologyUCI.arff");
		  //clusterDilca.clusterData(dd);
		  //System.out.println(getCluster(dd));
		  
		  //CLUSTER DEFINITI CON SOLO DILCA 
		  int[] classCluster = clusterDilca.clusterData(dd);
		  NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
		  double value = nnn.measure(classLabel, classCluster);
		  System.out.println("value: "+value);
		  System.out.println("value joint: "+nnn.joint(classLabel, classCluster) + "value sum: "+nnn.sum(classLabel, classCluster));
		  int aa = 0;
		  int bb = 0;
		  int cc = 0;
		  int de = 0;
		  int ee = 0;
		  int ff = 0;
		  for(int i = 0; i < classCluster.length; i++) {
			  if(classCluster[i] == 0) {
				  aa++;
			  }else if(classCluster[i]==1) {
				  bb++;
			  }else if(classCluster[i]==2) {
				  cc++;
			  }else if(classCluster[i]==3) {
				  de++;
			  }else if(classCluster[i]==4) {
				  ee++;
			  }else if(classCluster[i]==5) {
				  ff++;
			  }
		  }
		  System.out.println("cluster aa: "+aa+", cluster bb: "+bb+", cluster cc: "+cc+", cluster dd: "+de+", cluster ee: "+ee+", cluster ff: "+ff);
		  double entropyCluster;
		  entropyCluster = (-(aa/totalInstances)*log2(aa/totalInstances)) + (-(bb/totalInstances)*log2(bb/totalInstances)) +(-(cc/totalInstances)*log2(cc/totalInstances))+ (-(de/totalInstances)*log2(de/totalInstances))+ (-(ee/totalInstances)*log2(ee/totalInstances))+ (-(ff/totalInstances)*log2(ff/totalInstances));
		  System.out.println("entropy Cluster: "+entropyCluster);
		  
		  
		  
		  
		  
		  
		  //P(Y=a|C=aa)... a è quando classLabel[i] = 1, mentre aa è quando classCluster è 0
		  int aaa = 0;
		  int aab = 0;
		  int aac = 0;
		  int aad = 0;
		  int aae = 0;
		  int aaf = 0;
		  for(int i=0; i< classCluster.length;i++) {
			  if(classLabel[i] == 1 && classCluster[i] == 0) {
				  aaa++;
			  }else if(classLabel[i] == 2 && classCluster[i] == 0) {
				  aab++;
			  }else if(classLabel[i] == 3 && classCluster[i] == 0) {
				  aac++;
			  }else if(classLabel[i] == 4 && classCluster[i] == 0) {
				  aad++;
			  }else if(classLabel[i] == 5 && classCluster[i] == 0) {
				  aae++;
			  }else if(classLabel[i] == 6 && classCluster[i] == 0) {
				  aaf++;
			  }
		  }
		  double paaa = aaa/(double)aa;
		  double pbaa = aab/(double)aa;
		  double pcaa = aac/(double)aa;
		  double pdaa = aad/(double)aa;
		  double peaa = aae/(double)aa;
		  double pfaa = aaf/(double)aa;
		  System.out.println("P(Y=a|C=aa): "+paaa+", P(Y=b|C=aa): "+pbaa+", P(Y=c|C=aa): "+pcaa+", P(Y=d|C=aa): "+pdaa+", P(Y=e|C=aa): "+peaa+", P(Y=f|C=aa): "+pfaa);
		  
		  
		  
		  //ora calcola P(Y=a|C=bb) ecc
		  int bba = 0;
		  int bbb = 0;
		  int bbc = 0;
		  int bbd = 0;
		  int bbe = 0;
		  int bbf = 0;
		  for(int i=0; i< classCluster.length;i++) {
			  if(classLabel[i] == 1 && classCluster[i] == 1) {
				  bba++;
			  }else if(classLabel[i] == 2 && classCluster[i] == 1) {
				  bbb++;
			  }else if(classLabel[i] == 3 && classCluster[i] == 1) {
				  bbc++;
			  }else if(classLabel[i] == 4 && classCluster[i] == 1) {
				  bbd++;
			  }else if(classLabel[i] == 5 && classCluster[i] == 1) {
				  bbe++;
			  }else if(classLabel[i] == 6 && classCluster[i] == 1) {
				  bbf++;
			  }
		  }
		  double pabb = bba/(double)bb;
		  double pbbb = bbb/(double)bb;
		  double pcbb = bbc/(double)bb;
		  double pdbb = bbd/(double)bb;
		  double pebb = bbe/(double)bb;
		  double pfbb = bbf/(double)bb;
		  System.out.println("P(Y=a|C=bb): "+pabb+", P(Y=b|C=bb): "+pbbb+", P(Y=c|C=bb): "+pcbb+", P(Y=d|C=bb): "+pdbb+", P(Y=e|C=bb): "+pebb+", P(Y=f|C=bb): "+pfbb);
		  
		  
		//ora calcola P(Y=a|C=cc) ecc
		  int cca = 0;
		  int ccb = 0;
		  int ccc = 0;
		  int ccd = 0;
		  int cce = 0;
		  int ccf = 0;
		  for(int i=0; i< classCluster.length;i++) {
			  if(classLabel[i] == 1 && classCluster[i] == 2) {
				  cca++;
			  }else if(classLabel[i] == 2 && classCluster[i] == 2) {
				  ccb++;
			  }else if(classLabel[i] == 3 && classCluster[i] == 2) {
				  ccc++;
			  }else if(classLabel[i] == 4 && classCluster[i] == 2) {
				  ccd++;
			  }else if(classLabel[i] == 5 && classCluster[i] == 2) {
				  cce++;
			  }else if(classLabel[i] == 6 && classCluster[i] == 2) {
				  ccf++;
			  }
		  } 
		  double pacc = cca/(double)cc;
		  double pbcc = ccb/(double)cc;
		  double pccc = ccc/(double)cc;
		  double pdcc = ccd/(double)cc;
		  double pecc = cce/(double)cc;
		  double pfcc = ccf/(double)cc;
		  System.out.println("P(Y=a|C=cc): "+pacc+", P(Y=b|C=cc): "+pbcc+", P(Y=c|C=cc): "+pccc+", P(Y=d|C=cc): "+pdcc+", P(Y=e|C=cc): "+pecc+", P(Y=f|C=cc): "+pfcc);
		  
		//ora calcola P(Y=a|C=dd) ecc
		  int dda = 0;
		  int ddb = 0;
		  int ddc = 0;
		  int ddd = 0;
		  int dde = 0;
		  int ddf = 0;
		  for(int i=0; i< classCluster.length;i++) {
			  if(classLabel[i] == 1 && classCluster[i] == 3) {
				  dda++;
			  }else if(classLabel[i] == 2 && classCluster[i] == 3) {
				  ddb++;
			  }else if(classLabel[i] == 3 && classCluster[i] == 3) {
				  ddc++;
			  }else if(classLabel[i] == 4 && classCluster[i] == 3) {
				  ddd++;
			  }else if(classLabel[i] == 5 && classCluster[i] == 3) {
				  dde++;
			  }else if(classLabel[i] == 6 && classCluster[i] == 3) {
				  ddf++;
			  }
		  }
		  double padd = dda/(double)de;
		  double pbdd = ddb/(double)de;
		  double pcdd = ddc/(double)de;
		  double pddd = ddd/(double)de;
		  double pedd = dde/(double)de;
		  double pfdd = ddf/(double)de;
		  System.out.println("P(Y=a|C=dd): "+padd+", P(Y=b|C=dd): "+pbdd+", P(Y=c|C=dd): "+pcdd+", P(Y=d|C=dd): "+pddd+", P(Y=e|C=dd): "+pedd+", P(Y=f|C=dd): "+pfdd);
		
		  //ora calcola P(Y=a|C=ee) ecc
		  int eea = 0;
		  int eeb = 0;
		  int eec = 0;
		  int eed = 0;
		  int eee = 0;
		  int eef = 0;
		  for(int i=0; i< classCluster.length;i++) {
			  if(classLabel[i] == 1 && classCluster[i] == 4) {
				  eea++;
			  }else if(classLabel[i] == 2 && classCluster[i] == 4) {
				  eeb++;
			  }else if(classLabel[i] == 3 && classCluster[i] == 4) {
				  eec++;
			  }else if(classLabel[i] == 4 && classCluster[i] == 4) {
				  eed++;
			  }else if(classLabel[i] == 5 && classCluster[i] == 4) {
				  eee++;
			  }else if(classLabel[i] == 6 && classCluster[i] == 4) {
				  eef++;
			  }
		  } 
		  double paee = eea/(double)ee;
		  double pbee = eeb/(double)ee;
		  double pcee = eec/(double)ee;
		  double pdee = eed/(double)ee;
		  double peee = eee/(double)ee;
		  double pfee = eef/(double)ee;
		  System.out.println("P(Y=a|C=ee): "+paee+", P(Y=b|C=ee): "+pbee+", P(Y=c|C=ee): "+pcee+", P(Y=d|C=ee): "+pdee+", P(Y=e|C=ee): "+peee+", P(Y=f|C=ee): "+pfee);
		
		//ora calcola P(Y=a|C=ff) ecc
		  int ffa = 0;
		  int ffb = 0;
		  int ffc = 0;
		  int ffd = 0;
		  int ffe = 0;
		  int fff = 0;
		  for(int i=0; i< classCluster.length;i++) {
			  if(classLabel[i] == 1 && classCluster[i] == 5) {
				  ffa++;
			  }else if(classLabel[i] == 2 && classCluster[i] == 5) {
				  ffb++;
			  }else if(classLabel[i] == 3 && classCluster[i] == 5) {
				  ffc++;
			  }else if(classLabel[i] == 4 && classCluster[i] == 5) {
				  ffd++;
			  }else if(classLabel[i] == 5 && classCluster[i] == 5) {
				  ffe++;
			  }else if(classLabel[i] == 6 && classCluster[i] == 5) {
				  fff++;
			  }
		  }
		  double paff = ffa/(double)ff;
		  double pbff = ffb/(double)ff;
		  double pcff = ffc/(double)ff;
		  double pdff = ffd/(double)ff;
		  double peff = ffe/(double)ff;
		  double pfff = fff/(double)ff;
		  System.out.println("P(Y=a|C=ff): "+paff+", P(Y=b|C=ff): "+pbff+", P(Y=c|C=ff): "+pcff+", P(Y=d|C=ff): "+pdff+", P(Y=e|C=ff): "+peff+", P(Y=f|C=ff): "+pfff);
		
		  
		  System.out.println("totalInstances: "+totalInstances);
		  System.out.println(" cluster aa: "+(aaa+aab+aac+aad+aae+aaf)/totalInstances);
		  //double hYaa = (-(1/6)*((paaa*log2(paaa))+(pbaa*log2(pbaa))+ (pcaa*log2(pcaa))+ (pdaa*log2(pdaa))+(peaa*log2(peaa))+(pfaa*log2(pfaa))));
		  
		  
		  double Yaa = 0;
		  if(paaa!= 0) {
			  Yaa = Yaa + ((paaa*log2(paaa)));
		  }
		  if(pbaa!= 0) {
			  Yaa = Yaa + ((pbaa*log2(pbaa)));
		  }
		  if(pcaa!= 0) {
			  Yaa = Yaa + ((pcaa*log2(pcaa)));
		  }
		  if(pdaa!= 0) {
			  Yaa = Yaa + ((pdaa*log2(pdaa)));
		  }
		  if(peaa!= 0) {
			  Yaa = Yaa + ((peaa*log2(peaa)));
		  }
		  if(pfaa!= 0) {
			  Yaa = Yaa + ((pfaa*log2(pfaa)));
		  }
		  double hYaa = (-((aaa+aab+aac+aad+aae+aaf)/totalInstances)*Yaa);
		  // 		  double hYaa = (-((aaa+aab+aac+aad+aae+aaf)/totalInstances)*((paaa*log2(paaa))+(pbaa*log2(pbaa))+(pdaa*log2(pdaa))+(peaa*log2(peaa))));

		  
		  double Ybb = 0;
		  if(pabb!= 0) {
			  Ybb = Ybb + ((pabb*log2(pabb)));
		  }
		  if(pbbb!= 0) {
			  Ybb = Ybb + ((pbbb*log2(pbbb)));
		  }
		  if(pcbb!= 0) {
			  Ybb = Ybb + ((pcbb*log2(pcbb)));
		  }
		  if(pdbb!= 0) {
			  Ybb = Ybb + ((pdbb*log2(pdbb)));
		  }
		  if(pebb!= 0) {
			  Ybb = Ybb + ((pebb*log2(pebb)));
		  }
		  if(pfbb!= 0) {
			  Ybb = Ybb + ((pfbb*log2(pfbb)));
		  }
		  double hYbb = (-((bba+bbb+bbc+bbd+bbe+bbf)/totalInstances)*Ybb);
		//double hYbb = (-((bba+bbb+bbc+bbd+bbe+bbf)/totalInstances)*((pabb*log2(pabb))));
		  
		  double Ycc = 0;
		  if(pacc!= 0) {
			  Ycc = Ycc + ((pacc*log2(pacc)));
		  }
		  if(pbcc!= 0) {
			  Ycc = Ycc + ((pbcc*log2(pbcc)));
		  }
		  if(pccc!= 0) {
			  Ycc = Ycc + ((pccc*log2(pccc)));
		  }
		  if(pdcc!= 0) {
			  Ycc = Ycc + ((pdcc*log2(pdcc)));
		  }
		  if(pecc!= 0) {
			  Ycc = Ycc + ((pecc*log2(pecc)));
		  }
		  if(pfcc!= 0) {
			  Ycc = Ycc + ((pfcc*log2(pfcc)));
		  }
		  double hYcc = (-((cca+ccb+ccc+ccd+cce+ccf)/totalInstances)*Ycc);
		  //double hYcc = (-((cca+ccb+ccc+ccd+cce+ccf)/totalInstances)*((pccc*log2(pccc))));
		  
		  double Ydd = 0;
		  if(padd!= 0) {
			  Ydd = Ydd + ((padd*log2(padd)));
		  }
		  if(pbdd!= 0) {
			  Ydd = Ydd + ((pbdd*log2(pbdd)));
		  }
		  if(pcdd!= 0) {
			  Ydd = Ydd + ((pcdd*log2(pcdd)));
		  }
		  if(pddd!= 0) {
			  Ydd = Ydd + ((pddd*log2(pddd)));
		  }
		  if(pedd!= 0) {
			  Ydd = Ydd + ((pedd*log2(pedd)));
		  }
		  if(pfdd!= 0) {
			  Ydd = Ydd + ((pfdd*log2(pfdd)));
		  }
		  double hYdd = (-((dda+ddb+ddc+ddd+dde+ddf)/totalInstances)*Ydd);
		  //double hYdd = (-((dda+ddb+ddc+ddd+dde+ddf)/totalInstances)*((pedd*log2(pedd))));
		  
		  double Yee = 0;
		  if(paee!= 0) {
			  Yee = Yee + ((paee*log2(paee)));
		  }
		  if(pbee!= 0) {
			  Yee = Yee + ((pbee*log2(pbee)));
		  }
		  if(pcee!= 0) {
			  Yee = Yee + ((pcee*log2(pcee)));
		  }
		  if(pdee!= 0) {
			  Yee = Yee + ((pdee*log2(pdee)));
		  }
		  if(peee!= 0) {
			  Yee = Yee + ((peee*log2(peee)));
		  }
		  if(pfee!= 0) {
			  Yee = Yee + ((pfee*log2(pfee)));
		  }
		  double hYee = (-((eea+eeb+eec+eed+eee+eef)/totalInstances)*Yee);
		  //double hYee = (-((eea+eeb+eec+eed+eee+eef)/totalInstances)*((paee*log2(paee))+(pbee*log2(pbee))+(pcee*log2(pcee))+(pdee*log2(pdee))));
		  
		  double Yff = 0;
		  if(paff!= 0) {
			  Yff = Yff + ((paff*log2(paff)));
		  }
		  if(pbff!= 0) {
			  Yff = Yff + ((pbff*log2(pbff)));
		  }
		  if(pcff!= 0) {
			  Yff = Yff + ((pcff*log2(pcff)));
		  }
		  if(pdff!= 0) {
			  Yff = Yff + ((pdff*log2(pdff)));
		  }
		  if(peff!= 0) {
			  Yff = Yff + ((peff*log2(peff)));
		  }
		  if(pfff!= 0) {
			  Yff = Yff + ((pfff*log2(pfff)));
		  }
		  double hYff = (-((ffa+ffb+ffc+ffd+ffe+fff)/totalInstances)*Yff);
		  //double hYff = (-((ffa+ffb+ffc+ffd+ffe+fff)/totalInstances)*((pfff*log2(pfff))));
		  System.out.println("H(Y|C=aa): "+hYaa);
		  System.out.println("H(Y|C=bb): "+hYbb);
		  System.out.println("H(Y|C=cc): "+hYcc);
		  System.out.println("H(Y|C=dd): "+hYdd);
		  System.out.println("H(Y|C=ee): "+hYee);
		  System.out.println("H(Y|C=ff): "+hYff);
		  
		  double hYC = hYaa + hYbb + hYcc + hYdd + hYee + hYff;
		  System.out.println("H(Y|C): "+hYC);
		  double iYC = entropyClass - hYC;
		  System.out.println("I(Y,C): "+iYC);
		  
		  double NMI = (2*iYC)/(entropyClass+entropyCluster);
		  System.out.println("Valore di NMI tra cluster definito dalla classe del dataset e cluster definiti da solo Dilca (senza DifferentialPrivacy): "+NMI);


	}
	
}
