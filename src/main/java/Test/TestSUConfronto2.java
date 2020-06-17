package Test;

import java.io.FileWriter;
import java.util.Random;

import DilcaDistance.DilcaDistance;
import DilcaDistance.DilcaDistanceContTableRR;
import DilcaDistance.DilcaDistanceDiffPrivMean;
import DilcaDistance.DilcaDistanceDiffPrivRR;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class TestSUConfronto2 {
	public static void main(String[] args) throws Exception {
		Instances cpu = null;
		DataSource source = new DataSource("C:\\\\\\\\Users\\\\\\\\Simone\\\\\\\\eclipse-workspace\\\\\\\\Tesi\\\\\\\\src\\\\\\\\SoybeanDataset\\\\\\\\soybean_arff.arff");
		Instances data = source.getDataSet();
		data.setClassIndex(data.numAttributes()-1);
		Remove filter = new Remove();
		filter.setAttributeIndices(""+(data.classIndex()+1));
		filter.setInputFormat(data);
		cpu = Filter.useFilter(data, filter);
		
		/*
		DilcaDistance de = new DilcaDistance();
		System.out.println("*******************************************");
		//BalloonNMISoloDilcaTestSuClasseDataset.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/BreastCancerDataset/breast-cancer.arff");
		//int[] classe = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(de, 2);
		de.SU(cpu, 4,5);
		*/
		
	
		Random rand =new Random();
		double epsilon = 0.0;
		FileWriter writer = new FileWriter("C:\\Users\\Simone\\Desktop\\TestSU\\Confronto\\Tutti\\SoybeanTuttiConfronto.txt", true);
		FileWriter writer1 = new FileWriter("C:\\Users\\Simone\\Desktop\\TestSU\\Tutti\\SoybeanAttributiUsatiConfronto.txt", true);
		Random randTarget = new Random();
			for(int ciclo = 1; ciclo<500; ciclo++) {
				rand.setSeed(11235813);
				epsilon = epsilon+0.005;
				double valSU = 0.0;
				int attributoTarget = 0;
				for(int po = 0; po< 5;po++) {
					attributoTarget = randTarget.nextInt(35);
					int attributo = 0;
					double correlation = 0;
					for(int cc = 0; cc<cpu.numAttributes();cc++) {
						if(attributo!= attributoTarget) {
						DilcaDistance de = new DilcaDistance();
						System.out.println("*******************************************");
						double somma = 0;
						int counter = 0;
						for(int i = 0; i<100;i++) {
					        DilcaDistanceDiffPrivRR dd = new DilcaDistanceDiffPrivRR((epsilon/3), rand.nextLong());
							System.out.println("*******************************************");
							if(dd.SU(cpu,attributo,attributoTarget)>1.0) {
								somma = somma + 1.0;
							}else if(dd.SU(cpu,attributo,attributoTarget)<0.0) {
								somma = somma + 0.0;
							}else {
								somma = somma + dd.SU(cpu,attributo,attributoTarget);
							}
							counter++;
						}
						correlation = correlation + (double)(somma/counter);
						attributo++;
						}else {
							attributo++;
						}
					}
					valSU = valSU + (double)correlation/(cpu.numAttributes()-1);
					
					
					System.out.println("attributo: "+attributo);
					System.out.println("epsilon: "+epsilon);
					//writer.write("Epsilon: "+epsilon+": "+Math.abs(((somma/counter)-0.12809573193676807)));
					//writer.write(" "+somma/counter);
					//System.out.println("counter: "+counter);
					writer1.write("attributo usato: "+attributoTarget);
			}
			double result = (double) valSU/cpu.numAttributes();
			writer.write(" "+result);
		}
        writer.close();
        writer1.close();
      
        
        
		/*
        Random rand =new Random();
		double epsilon = 0.0;
		FileWriter writer = new FileWriter("C:\\Users\\Simone\\Desktop\\TestSU\\Confronto\\PostOperativeTuttiConfronto.txt", true);
		for(int ciclo = 1; ciclo<500; ciclo++) {
			rand.setSeed(11235813);
			epsilon = epsilon+0.005;
			int attributo = 0;
			double correlation = 0;
			for(int cc = 0; cc<cpu.numAttributes();cc++) {
				if(attributo!= 0) {
				double somma = 0;
				int counter = 0;
				for(int i = 0; i<100;i++) {
			        DilcaDistanceDiffPrivRR dd = new DilcaDistanceDiffPrivRR((epsilon/3), rand.nextLong());
					System.out.println("*******************************************");
					if(dd.SU(cpu,attributo,0)>1.0) {
						somma = somma + 1.0;
					}else if(dd.SU(cpu,attributo,0)<0.0) {
						somma = somma + 0.0;
					}else {
						somma = somma + dd.SU(cpu,attributo,0);
					}
					counter++;
				}
				correlation = correlation + (double)(somma/counter);
				attributo++;
				}else {
					attributo++;
				}
			}
			double valSU = (double)correlation/(cpu.numAttributes()-1);
			writer.write(" "+valSU);
			System.out.println("attributo: "+attributo);
			System.out.println("epsilon: "+epsilon);
			//writer.write("Epsilon: "+epsilon+": "+Math.abs(((somma/counter)-0.12809573193676807)));
			//writer.write(" "+somma/counter);
			//System.out.println("counter: "+counter);
		}
        writer.close();
		*/
		
        
        
        double risultatoFinale = 0.0;
        double parz = 0.0;
        int attr = 0;
        int attrTarg = 0;
        double val = 0.0;
        for(int ciclo1 = 0; ciclo1<cpu.numAttributes();ciclo1++) {
	        attr = 0;
	        parz = 0;
	        for(int ciclo = 0; ciclo<cpu.numAttributes();ciclo++) {
	        	if(attr!= attrTarg) {
		        	DilcaDistance de = new DilcaDistance();
		        	parz = parz + de.SU(cpu, attr,attrTarg);
		        	attr++;
	        	}else {
	        		attr++;
	        	}
	        }
	        val = val + (double)(parz/(cpu.numAttributes()-1));
	        System.out.println("valore " + val);
	        System.out.println("val" + attrTarg);
	        attrTarg++;
        }
        risultatoFinale= val/(cpu.numAttributes());
        System.out.println("valore SU originale media: "+ risultatoFinale);
        
        
        //for soybean SU RR: 0.19762954946063274, attributi: 1,34
		//for titanic SU RR: 0.3027471122659928 attributi: 0,5
		//for dermatology RR: 0.12809573193676807 attributi: 0,1
		//for balloons RR: 0.02057065945069182 attributi: 0,1
		//for postoperative RR: 0.05118109854019373 attributi: 4,5
		//for mushroom RR: 0.8147876454550914 attributi: 16,5
		//for breastcancer RR: 0.06295591702007212 attributi: 6,7
		
		
		//for mushroom RR: 0.09615266005697466 attributi: 19,5
		//for soybean SU RR: 0.07767747238948762, attributi: 0,34
        //for postoperative RR: 0.0446701033424243 attributi: 0,7
        //for audiology: RR 0.13147608440305833 attributi: 1, 63
        
        
        //for audiology: 0.015392635776521542 attributo 1
        //for soybean: 0.030775555562902013  attributo 1
        //for mushroom:	0.05539308748329679  attributo 1
        //for breastcancer: 0.061073293225292286 attributo 1
        //for postOperative: 0.03184536508868778 attributo 1
        //for titanic: 0.07500432195730596 attributo 1
        //for hepatitis:  0.05959084215849172	attributo 1
        
        
        //valori medi considerando tutti gli attributi:
        //breastCancer: 0.046032178047078584
        //postOperative: 0.026729298224467634
        //hepatitis: 0.05119193366799356
        //Titanic: 0.054929737660820786
	}
}
