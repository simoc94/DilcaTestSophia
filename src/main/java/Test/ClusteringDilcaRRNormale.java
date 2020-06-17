package Test;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Random;

import DilcaDistance.DilcaDistance;
import DilcaDistance.DilcaDistanceDiffPrivRRWithFinalDistance;
import net.javaguides.maven_web_project.DilcaDistanceDiffPriv.BalloonNMISoloDilcaTestSuClasseDataset;
import net.javaguides.maven_web_project.DilcaDistanceDiffPriv.PostOperativeNMISoloDilca;
import smile.validation.AdjustedRandIndex;
import smile.validation.NormalizedMutualInformation;
import smile.validation.NormalizedMutualInformation.Method;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class ClusteringDilcaRRNormale {
	
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

        DilcaDistance dd = new DilcaDistance();
        BalloonNMISoloDilcaTestSuClasseDataset.loadArff("/Users/Simone/eclipse-workspace/Tesi/src/MushroomDataset/mushroom.arff");
        int[] classCluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, 2);
        NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
        //AdjustedRandIndex nnn = new AdjustedRandIndex();
		double value = nnn.measure(classe, classCluster);
		System.out.println("value: "+value);

	}
	//Audiology   	NMI: 0.5397459072840991			ARI: 0.1980818361221749
	//breastCancer 	NMI: 0.04681066178793333		ARI: -0.033210761412516415
	//Hepatitis 	NMI: 0.07026004836288367		ARI: -0.06740074790946875
	//Mushroom		NMI: 0.24604875999369769		ARI: 0.2635610097239746
	//postOperative NMI: 0.03913821958077182		ARI: -0.001840484683114447
	//Soybean 		NMI: 0.7735143374471516			ARI: 0.48919728002798013
	//Titanic		NMI: 0.023485646790122425		ARI: 2.0596928552129372E-4
	

}
