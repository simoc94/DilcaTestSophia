package net.javaguides.maven_web_project.DilcaDistanceDiffPriv;

import DilcaDistance.DilcaDistance;
import DilcaDistance.DilcaDistanceMean;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class TestIntroduzione {
	
	public static void main (String[]args) throws Exception {
		
		DilcaDistanceMean dilca = new DilcaDistanceMean(1);
		Instances cpu = null;
		DataSource source = new DataSource("/Users/Simone/Desktop/databaseTest.arff");
		Instances data = source.getDataSet();
		data.setClassIndex(data.numAttributes()-1);
		Remove filter = new Remove();
		filter.setAttributeIndices(""+(data.classIndex()+1));
		filter.setInputFormat(data);
		cpu = Filter.useFilter(data, filter);
		dilca.calculateDistanceAttr(cpu, 1, dilca.contextVectorforDistance(cpu, 1));
	}

}
