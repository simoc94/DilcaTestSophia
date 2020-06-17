package Test;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import DilcaDistance.DilcaDistance;
import DilcaDistance.DilcaDistanceContTableRR;
import DilcaDistance.DilcaDistanceDiffPrivMean;
import DilcaDistance.DilcaDistanceDiffPrivRR;
import DilcaDistance.DilcaDistanceMean;
import net.javaguides.maven_web_project.DilcaDistanceDiffPriv.BalloonNMISoloDilcaTestSuClasseDataset;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class TestContextSURR {
	public static void main(String[] args) throws Exception {
		Instances cpu = null;
		DataSource source = new DataSource("/Users/Simone/eclipse-workspace/Tesi/src/MushroomDataset/mushroom.arff");
		Instances data = source.getDataSet();
		data.setClassIndex(data.numAttributes()-1);
		Remove filter = new Remove();
		filter.setAttributeIndices(""+(data.classIndex()+1));
		filter.setInputFormat(data);
		cpu = Filter.useFilter(data, filter);
		
		
		/*
		DilcaDistanceDiffPrivRR de = new DilcaDistanceDiffPrivRR(0.1/(cpu.numAttributes()+binomialCoeff(cpu.numAttributes(),2)));
		System.out.println("*******************************************");
		BalloonNMISoloDilcaTestSuClasseDataset.loadArff("C:\\\\\\\\Users\\\\\\\\Simone\\\\\\\\eclipse-workspace\\\\\\\\Tesi\\\\\\\\src\\\\\\\\SoybeanDataset\\\\\\\\soybean_arff.arff");
		int[] classe = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(de, 19);
		int[] contextDilcaMean = de.context(cpu,10);
		*/
		/*
		DilcaDistanceDiffPrivMean dd = new DilcaDistanceDiffPrivMean((0.1/((2*cpu.numAttributes())-1)), 1, 41778963);
		System.out.println("*******************************************");				
		//somma = somma + dd.SU(data,6,7);
		int[] contextDilcaMeanDP = dd.context(cpu, 1);
		int[] ciao = findCommonElement(contextDilcaMean,contextDilcaMeanDP);
		System.out.println("**************");
		for(int i = 0;i<ciao.length;i++) {
			System.out.println(ciao[i]);
		}
		System.out.println("numero comuni: "+ciao.length);
		*/
		/*
		Random rand =new Random();
		double epsilon = 0.0;
		FileWriter writer = new FileWriter("C:\\Users\\Simone\\Desktop\\TestSU\\TitanicSUDistorta33333.txt", true);
		for(int ciclo = 1; ciclo<10; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.001;
			double somma = 0;
			int counter = 0;
			for(int i = 0; i<100;i++) {
		        DilcaDistanceDiffPrivRR dd = new DilcaDistanceDiffPrivRR(epsilon, rand.nextLong());
				System.out.println("*******************************************");				
				somma = somma + dd.SU(data,0,5);
				
				counter++;
			}
			writer.write("Su DilcaRR con epsilon: "+epsilon+" e': " + somma/counter);
			//writer.write(" "+somma/counter);
			System.out.println("counter: "+counter);
		}
        writer.close();
        */
		/*
		DilcaDistance de = new DilcaDistance();
		de.SU(data, 0, 5);
		Random rand =new Random();
        rand.setSeed(11235813);
		DilcaDistanceDiffPrivRR dd = new DilcaDistanceDiffPrivRR(0.1, rand.nextLong());
		dd.SU(data, 0, 5);
		*/
        
		
		
		
		
		Random rand =new Random();
		double epsilon = 0.0;
		FileWriter writer = new FileWriter("C:\\Users\\Simone\\Desktop\\TestCONTEXTOverlap\\SURR\\Mushroom.txt", true);
		for(int ciclo = 1; ciclo<500; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.005;
			int attributo = 0;
			double correlation = 0.0;
			for(int cc = 0; cc < cpu.numAttributes();cc++) {
				DilcaDistance de = new DilcaDistance();
				System.out.println("*******************************************");
				//BalloonNMISoloDilcaTestSuClasseDataset.loadArff("C:\\\\Users\\\\Simone\\\\eclipse-workspace\\\\Tesi\\\\src\\\\HepatitisDataset\\\\dataset_55_hepatitis.arff");
				//int[] classe = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(de, 2);
				int[] contextDilcaRR = de.context(cpu,attributo);
				double overlap = 0;
				int counter = 0;
				for(int i = 0; i<100;i++) {
					DilcaDistanceDiffPrivRR dd = new DilcaDistanceDiffPrivRR(epsilon/(cpu.numAttributes()+binomialCoeff(cpu.numAttributes(),2)),rand.nextLong());	
					System.out.println("*******************************************");				
					int[] contextDilcaRRDP = dd.context(cpu, attributo);
					int[] arr = findCommonElement(contextDilcaRR, contextDilcaRRDP);
					double over = 0;
					
					if(contextDilcaRR.length<=contextDilcaRRDP.length) {
						over = (double)arr.length/contextDilcaRR.length;
					}else {
						over = (double)arr.length/contextDilcaRRDP.length;
					}
					
					//over = (double)arr.length/(contextDilcaRR.length+contextDilcaRRDP.length- arr.length);
					overlap = overlap + over;
					
					counter++;
				}
				correlation = correlation + (double)overlap/counter;
				//writer.write(result+" ");
				//System.out.println("counter: "+counter);
				attributo++;
			}
			double valDatabase = (double)correlation/(cpu.numAttributes());
			writer.write(valDatabase+" ");
			System.out.println("attributo: "+attributo);
			System.out.println("epsilon: "+epsilon);
		}
        writer.close();
		
		
		/*
        Random rand =new Random();
		double epsilon = 0.0;
		FileWriter writer = new FileWriter("C:\\Users\\Simone\\Desktop\\TestContextSURRTrueJaccard\\Mushroom4.txt", true);
		for(int ciclo = 1; ciclo<500; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.005;
			double overlap = 0;
			int counter = 0;
			for(int i = 0; i<100;i++) {
				DilcaDistanceDiffPrivRR dd = new DilcaDistanceDiffPrivRR(epsilon/(cpu.numAttributes()+binomialCoeff(cpu.numAttributes(),2)),rand.nextLong());				
				System.out.println("*******************************************");				
				int[] contextDilcaRRDP = dd.context(cpu, 3);
				int[] arr = findCommonElement(contextDilcaRR, contextDilcaRRDP);
				double over = 0;
				
				if(contextDilcaRR.length<=contextDilcaRRDP.length) {
					over = (double)arr.length/contextDilcaRR.length;
				}else {
					over = (double)arr.length/contextDilcaRRDP.length;
				}
				
				over = (double)arr.length/(contextDilcaRR.length+contextDilcaRRDP.length- arr.length);
				overlap = overlap + over;
				
				counter++;
			}
			double result = (double)overlap/counter;
			writer.write(result+" ");
			System.out.println("counter: "+counter);
		}
        writer.close();
       */
		
		
      
	}
	
	
	public static int[] findCommonElement(int[] a, int[] b){
	    List<Integer> array = new LinkedList<Integer>();
	    Set<Integer> set = new HashSet<Integer>();
	    for(int ele:a){
	        set.add(ele);
	    }

	    for(int ele:b){
	        if(set.contains(ele)){
	            array.add(ele);
	        }
	    }

	    int[] arr = new int[array.size()];
	    for(int i = 0; i < array.size();i++){
	        arr[i] = array.get(i);
	    }
	    return arr;
	}
	
    // Returns value of Binomial  
    // Coefficient C(n, k) 
    static int binomialCoeff(int n, int k)  
    { 
      
        // Base Cases 
        if (k == 0 || k == n) 
            return 1; 
          
        // Recur 
        return binomialCoeff(n - 1, k - 1) +  
                    binomialCoeff(n - 1, k); 
    } 
}
