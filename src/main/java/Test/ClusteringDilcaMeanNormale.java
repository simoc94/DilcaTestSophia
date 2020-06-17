package Test;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

import DilcaDistance.DilcaDistance;
import DilcaDistance.DilcaDistanceMean;
import net.javaguides.maven_web_project.DilcaDistanceDiffPriv.BalloonNMISoloDilcaTestSuClasseDataset;
import smile.validation.AdjustedRandIndex;
import smile.validation.NormalizedMutualInformation;
import smile.validation.NormalizedMutualInformation.Method;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class ClusteringDilcaMeanNormale {
	
public static void main(String[]args) throws Exception {
		
		Instances cpu = null;
		DataSource source = new DataSource("/Users/Simone/eclipse-workspace/Tesi/src/MushroomDataset/mushroom.arff");
		Instances data = source.getDataSet();
		data.setClassIndex(data.numAttributes()-1);
		Remove filter = new Remove();
		filter.setAttributeIndices(""+(data.classIndex()+1));
		filter.setInputFormat(data);
		cpu = Filter.useFilter(data, filter);
		
		System.out.println("Please, enter the arff file's path of your database.");
		System.out.println("Sorry, pay attention that the class attribute is the last in your database...Thanks for your help :)");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String s = bufferRead.readLine();
        int[] classe = new int[0];
        classe = BalloonNMISoloDilcaTestSuClasseDataset.loadArffClasse(s);
        double sigma = 0.0;
        
    	FileWriter writer1 = new FileWriter("C:\\Users\\Simone\\Desktop\\MushroomARIDilcaMean.txt", true);
    	for(int i = 0; i<10;i++) {
	    	sigma = sigma + 0.1;
	        DilcaDistanceMean dd = new DilcaDistanceMean(sigma);
	        BalloonNMISoloDilcaTestSuClasseDataset.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/MushroomDataset/mushroom.arff");
	        int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
	        //NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
	        AdjustedRandIndex nnn = new AdjustedRandIndex();
			double value = nnn.measure(classe, classCluster);
			System.out.println("value: "+value);
			writer1.write("sigma: "+sigma+", value: "+value+".  ");
    	}
    	writer1.close();
	}
	
	//audiology: 0.5338094343712684 sigma: 0.7
	//breastCancer: 0.07135564866822983 sigma: 0.1
	//hepatitis: 0.09718431529500843 sigma: 0.4
	//postOperative: 0.0352565028001631 sigma: 1.0
	//Soybean: 0.8006204476854136 sigma:: 0.8
	//titanic: 0.02348564679012242 sigma: 1.0
	
	

}
