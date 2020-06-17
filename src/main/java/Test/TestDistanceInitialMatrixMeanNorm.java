package Test;

import java.io.FileWriter;
import java.util.Random;
import java.util.Vector;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import DilcaDistance.DilcaDistanceContTableMean;
import DilcaDistance.DilcaDistanceDiffPrivMeanWithFinalDistance;
import DilcaDistance.DilcaDistanceMean;
import net.javaguides.maven_web_project.DilcaDistanceDiffPriv.BalloonNMISoloDilcaTestSuClasseDataset;
import smile.math.kernel.PearsonKernel;
import weka.attributeSelection.CorrelationAttributeEval;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class TestDistanceInitialMatrixMeanNorm {
	
	public static void main(String[] args) throws Exception {
		Instances cpu = null;
		DataSource source = new DataSource("C:\\\\Users\\\\Simone\\\\eclipse-workspace\\\\Tesi\\\\src\\\\TitanicDataset\\\\titanicTrue.arff");
		Instances data = source.getDataSet();
		data.setClassIndex(data.numAttributes()-1);
		Remove filter = new Remove();
		filter.setAttributeIndices(""+(data.classIndex()+1));
		filter.setInputFormat(data);
		cpu = Filter.useFilter(data, filter);
		double sigma = 1;
		
		Random rand =new Random();
		double epsilon = 0.0;
		FileWriter writer = new FileWriter("C:\\Users\\Simone\\Desktop\\TestDISTANZANORMA\\MatrInizialeMean\\TitanicSigma1.txt", true);
		for(int ciclo = 1; ciclo<500; ciclo++) {
			epsilon = epsilon+0.005;
			int attributo = 0;
			double correlation = 0.0;
			for(int cc = 0; cc < cpu.numAttributes();cc++) {
				rand.setSeed(11235813);
				DilcaDistanceMean de = new DilcaDistanceMean(sigma);
				System.out.println("*******************************************");
				double[][] matrixOr = de.calculateDistanceAttr(cpu, attributo, de.contextVectorforDistance(cpu, attributo));
				double corr = 0;
				int counter = 0;
				for(int i = 0; i<100;i++) {
					DilcaDistanceContTableMean dd = new DilcaDistanceContTableMean((epsilon/(cpu.numAttributes()-1)),sigma, rand.nextLong());
					System.out.println("*******************************************");
					//BalloonNMISoloDilcaTestSuClasseDataset.loadArff("C:\\\\Users\\\\Simone\\\\eclipse-workspace\\\\Tesi\\\\src\\\\SoybeanDataset\\\\soybean_arff.arff");
					//int[] classedd = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 19);
					double[][] matrixDP =dd.calculateDistanceAttr(cpu, attributo, dd.contextVectorforDistance(cpu, attributo));
					double result = 0.0;
					for(int p = 0; p<matrixDP.length;p++) {
						for(int j = 0; j<matrixDP[0].length;j++) {
							result = (double)result + (Math.abs(matrixOr[p][j]-matrixDP[p][j]));
						}
					}
					result = (double)result/2;
					result = (double)result/((double)((double)matrixOr.length*(matrixOr.length-1))/2);
					//System.out.println("matrice originale: "+valOr/2+" matrice DP: "+valDP/2);
					//double result = (double)(Math.abs(valOr-valDP))/2;
					corr = corr + result;
					counter++;
				}
				correlation = correlation + (double)corr/counter;
				attributo++;
			}
			double valDatabase = (double)correlation/cpu.numAttributes();
			writer.write(valDatabase+" ");
			System.out.println("attributo: "+attributo);
			System.out.println("epsilon: "+epsilon);
			
		}
		writer.close();
		
		/* TEST SINGOLA SIMULAZIONE
		 * 
		 */
		/*
		double corr = 0.0;
		DilcaDistanceDiffPrivMeanWithFinalDistance dd = new DilcaDistanceDiffPrivMeanWithFinalDistance(0.5*(0.1/((2*cpu.numAttributes())-1)),0.5*(0.1/(cpu.numAttributes()-1)),sigma, 454354897);
		System.out.println("*******************************************");
		BalloonNMISoloDilcaTestSuClasseDataset.loadArff("C:\\\\Users\\\\Simone\\\\eclipse-workspace\\\\Tesi\\\\src\\\\SoybeanDataset\\\\soybean_arff.arff");
		//int[] classedd = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 19);
		double[][] matrixDP =dd.calculateDistanceAttr(cpu, 0, dd.contextVectorforDistance(cpu, 0));
		double valDP = 0.0;
		for(int p = 0; p<matrixDP.length;p++) {
			for(int j = 0; j<matrixDP[0].length;j++) {
				valDP = valDP + matrixDP[p][j];
			}
		}
		
		//System.out.println("matrice originale: "+valOr/2+" matrice DP: "+valDP/2);
		PearsonsCorrelation gg = new PearsonsCorrelation();
		double[] arrOr = new double[matrixOr.length];
		double[] arrDP = new double[matrixDP.length];
		double result = 0.0;
		for(int z = 0; z<matrixOr.length;z++) {
			for(int  j = 0; j<matrixOr[0].length;j++) {
				arrOr[j] = matrixOr[z][j];
				arrDP[j] = matrixDP[z][j];
			}
			
			result = result + (gg.correlation(arrOr, arrDP));
		}
		corr = corr + (result/matrixDP.length);
		System.out.println(corr+"corrr");
		System.out.println(result/matrixDP.length+"result");
		*/
		/*	
		for (int i = 0;i<arrDP.length;i++) {
			System.out.println(arrDP[i]);
		}
		*/
		//System.out.println(gg.correlation(arrOr, arrDP));
		//System.out.println("result: "+result);
		
	}

}
