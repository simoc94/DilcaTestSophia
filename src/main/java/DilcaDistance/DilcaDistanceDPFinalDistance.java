package DilcaDistance;

/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 *    DilcaDistance.java
 *    Copyright (C) 1999-2012 University of Waikato, Hamilton, New Zealand
 *
 */


import java.io.Serializable;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.filters.Filter;
import weka.filters.*;
import weka.filters.supervised.attribute.*;
import weka.filters.unsupervised.attribute.*;
import weka.filters.unsupervised.attribute.Remove;
import weka.core.DistanceFunction;
import weka.core.ContingencyTables;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.Range;
import weka.core.TechnicalInformation;
import weka.core.neighboursearch.PerformanceStats;
import weka.core.TechnicalInformation.Field;
import weka.core.TechnicalInformation.Type;
import weka.core.Utils;
import weka.core.neighboursearch.PerformanceStats;
import weka.attributeSelection.FCBFSearch;
import weka.attributeSelection.SymmetricalUncertAttributeSetEval;
import java.util.Vector;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Random;

import weka.attributeSelection.AttributeSelection;


/**
 <!-- globalinfo-start -->
 * Implementing the Dilca function for categorical distance computation.<br/>
 * <br/>
 * In particular this is the implememntation of the non parametric version of the Dilca distance function. This approach allows to
learn value-to-value distances between each pair of values for each attribute of the dataset.
The distance between two values is computed indirectly based on the their distribution w.r.t. a set of related attributes (the context) carefully chosen<br/>
 * <br/>
 <!-- globalinfo-end -->
 *
 <!-- technical-bibtex-start -->
 * BibTeX:
 * <pre>
 * &#64;article{DBLP:journals/tkdd/IencoPM12,
	  author    = {Dino Ienco and
	               Ruggero G. Pensa and
	               Rosa Meo},
	  title     = {From Context to Distance: Learning Dissimilarity for Categorical
	               Data Clustering},
	  journal   = {TKDD},
	  volume    = {6},
	  number    = {1},
	  year      = {2012},
	  pages     = {1}
	}
 * </pre>
 * <p/>
 <!-- technical-bibtex-end -->
 *
 <!-- options-start -->
 * Valid options are: <p/>
 *  * 
 * <pre> -R &lt;col1,col2-col4,...&gt;
 *  Specifies list of columns to used in the calculation of the 
 *  distance. 'first' and 'last' are valid indices.
 *  (default: first-last)</pre>
 * 
 * <pre> -V
 *  Invert matching sense of column indices.</pre>
 * 
 <!-- options-end --> 
 *
 * @author Dino Ienco (dino.ienco@teledetection.fr)
 * @author Ruggero Pensa (pensa@di.unito.it)
 * @version $Revision: 8034 $
 */





public class DilcaDistanceDPFinalDistance implements DistanceFunction, Serializable{

	/** The range of attributes to use for calculating the distance. */
	protected Range m_AttributeIndices = new Range("first-last");
	
	/** The Original dataset. */
	protected Instances m_Data;

	/** The value-to-value distance matrices for each Attribute **/
	protected Vector<double[][]> matricesDilca;
	
  	/** The boolean flags, whether an attribute will be used or not. */
  	protected int[] m_ActiveIndices;
	
	/** The method used to discretize instances if they are represented over continuous attribute*/
	protected Filter m_Disc ;
	
	private boolean supDiscr;
	
	protected ReplaceMissingValues m_RepMissValue;
	
	double epsilon;
	
	long rand;
	
	public double getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	
	public long getRand() {
		return rand;
	}

	public void setRand(long rand) {
		this.rand = rand;
	}

	public DilcaDistanceDPFinalDistance(){
		matricesDilca = new Vector<double[][]>();
		m_Disc = new weka.filters.unsupervised.attribute.Discretize();
		m_RepMissValue = new ReplaceMissingValues();
		supDiscr = false;
	}
	
	public DilcaDistanceDPFinalDistance(double epsilon){
		matricesDilca = new Vector<double[][]>();
		m_Disc = new weka.filters.unsupervised.attribute.Discretize();
		m_RepMissValue = new ReplaceMissingValues();
		supDiscr = false;
		this.epsilon = epsilon;
	}
	
	public DilcaDistanceDPFinalDistance(double epsilon, long rand){
		matricesDilca = new Vector<double[][]>();
		m_Disc = new weka.filters.unsupervised.attribute.Discretize();
		m_RepMissValue = new ReplaceMissingValues();
		supDiscr = false;
		this.epsilon = epsilon;
		this.rand = rand;
	}

  	/**
   	 * Returns an enumeration describing the available options.
     *
     * @return 		an enumeration of all the available options.
    */
  	public Enumeration listOptions() {
    	Vector<Option> result = new Vector<Option>();

    	result.addElement(new Option(
			"\tSpecifies list of columns to used in the calculation of the \n"
			+ "\tdistance. 'first' and 'last' are valid indices.\n"
			+ "\t(default: first-last)",
			"R", 1, "-R <col1,col2-col4,...>"));

    	result.addElement(new Option(
			"\tInvert matching sense of column indices.",
			"V", 0, "-V"));
			
		result.addElement(new Option(
			"\tUse Supervised Discretization instead of Unsupervised Discretization if the class value is available",
			"D", 0, "-D"));	

    	return result.elements();
  }

  /**
   * Gets the current settings. Returns empty array.
   *
   * @return 		an array of strings suitable for passing to setOptions()
   */
  	public String[] getOptions() {
    	Vector<String>	result;
    
    	result = new Vector<String>();
    
    	result.add("-R");
    	result.add(getAttributeIndices());
    
    	if (getInvertSelection())
      		result.add("-V");

		if (getSupervisedDiscretization())
      		result.add("-D");

    	return result.toArray(new String[result.size()]);
  	}

  	/**
   	 * Parses a given list of options.
     *
   	 * @param options 	the list of options as an array of strings
     * @throws Exception 	if an option is not supported
    */
  	public void setOptions(String[] options) throws Exception {
    	String	tmpStr;
    
    	tmpStr = Utils.getOption('R', options);
    	if (tmpStr.length() != 0)
      		setAttributeIndices(tmpStr);
    	else
      		setAttributeIndices("first-last");

    	setInvertSelection(Utils.getFlag('V', options));
		setSupervisedDiscretization(Utils.getFlag('D', options));
//		System.out.println("setto impostazioni :"+tmpStr);
  	}

  	/**
   	 * Returns the tip text for this property.
   	 *
   	 * @return 		tip text for this property suitable for
     * 			displaying in the explorer/experimenter gui
     */
  	public String attributeIndicesTipText() {
    	return 
        	"Specify range of attributes to act on. "
      		+ "This is a comma separated list of attribute indices, with "
      		+ "\"first\" and \"last\" valid values. Specify an inclusive "
      		+ "range with \"-\". E.g: \"first-3,5,6-10,last\".";
  	}

	/**
   * Sets the range of attributes to use in the calculation of the distance.
   * The indices start from 1, 'first' and 'last' are valid as well. 
   * E.g.: first-3,5,6-last
   * 
   * @param value	the new attribute index range
   */
  	public void setAttributeIndices(String value) {
    	m_AttributeIndices.setRanges(value);
    	//invalidate();
  	}

	/**
   * Gets the range of attributes used in the calculation of the distance.
   * 
   * @return		the attribute index range
   */
  	public String getAttributeIndices() {
    	return m_AttributeIndices.getRanges();
  	}

  	/**
   	 * Returns the tip text for this property.
     *
     * @return 		tip text for this property suitable for
     * 			displaying in the explorer/experimenter gui
     */
  	public String invertSelectionTipText() {
    	return 
        	"Set attribute selection mode. If false, only selected "
      		+ "attributes in the range will be used in the distance calculation; if "
      		+ "true, only non-selected attributes will be used for the calculation.";
  	}

  	/**
   	 * Returns the tip text for this property.
     *
     * @return 		tip text for this property suitable for
     * 			displaying in the explorer/experimenter gui
     */
  	public String supervisedDiscretizationTipText() {
    	return 
        	"Set the discretization method as supervised. If false, the  "
      		+ "unsupervised discretization is used by default. Take attention set it true for supervised "
      		+ "discretization if the class information is available ";
  	}
	
	/**
   * Sets whether the matching sense of attribute indices is inverted or not.
   * 
   * @param value	if true the matching sense is inverted
   */
  	public void setInvertSelection(boolean value) {
    	m_AttributeIndices.setInvert(value);
    	//invalidate();
  	}

	/**
   * Sets whether the discretization method need to be supervised or not.
   * 
   * @param value	if true the supervised Discretization is used
   */
  	public void setSupervisedDiscretization(boolean value) {
    	if (value) 
			m_Disc = new weka.filters.supervised.attribute.Discretize();
		else
			m_Disc = new weka.filters.unsupervised.attribute.Discretize();
		supDiscr = value;
	}

	
	/**
   * Gets whether the matching sense of attribute indices is inverted or not.
   * 
   * @return		true if the matching sense is inverted
   */
  	public boolean getInvertSelection() {
    	return m_AttributeIndices.getInvert();
  	}

	/**
   * Gets whether the supervised Discretization is used or not
   * 
   * @return		true if the supervised Discretization is used
   */
  	public boolean getSupervisedDiscretization() {
    	return supDiscr;
  	}



  	/**
   	 * initializes the attribute indices.
   	 */
  	protected void initializeAttributeIndices() {
    	m_AttributeIndices.setUpper(m_Data.numAttributes() - 1);
    	m_ActiveIndices = new int[m_Data.numAttributes()];
    	for (Integer i = 0; i < m_ActiveIndices.length; i++)
      		m_ActiveIndices[i] = (m_AttributeIndices.isInRange(i))?1:0;
  	}


  	/**
   	 * Sets the instances.
   	 * 
   	 * @param insts 	the instances to use
   	*/
  	public void setInstances(Instances insts) {
    	m_Data = insts;
		initializeAttributeIndices();
		
		if (supDiscr && m_Data.classIndex() < 0)
			throw new RuntimeException("Trying to use Supervised Discretization over a dataset without an assigned class attribute");
			
		try{ 
			m_RepMissValue.setInputFormat(m_Data);
			m_Data = Filter.useFilter(m_Data,m_RepMissValue);
			m_Disc.setInputFormat(m_Data);
			m_Data = Filter.useFilter(m_Data,m_Disc);
		}catch(Exception e){
			System.out.println("Problem to clean the data with ReplaceMissingValues and Discretize Filter");
		}
		computeMatrix();
  	}

	public double distance(Instance first, Instance second) {
		return distance( first,  second, Double.POSITIVE_INFINITY);
	}
	
	public double distance(Instance first, Instance second, double cutOffValue) {

		m_RepMissValue.input(first);
		first = m_RepMissValue.output();

		m_RepMissValue.input(second);
		second = m_RepMissValue.output();
		try{
			m_Disc.input(first);
      		first = m_Disc.output();
		
			m_Disc.input(second);
      		second = m_Disc.output();
		}catch (Exception e){System.out.println(e.getMessage()); }
        /*		if (first.hasMissingValue()){
			for (int i=0; i<first.numAttributes(); ++i){
				if (first.isMissing(i) && i != first.classIndex()){				 
					first.setValue(i, (double) (Utils.maxIndex(m_Data.attributeStats(i).nominalCounts)) );
				}
			}
		}
		
		if (second.hasMissingValue()){
			for (int i=0; i<second.numAttributes(); ++i){
				if (second.isMissing(i) && i != second.classIndex()){
					second.setValue(i, (double) (Utils.maxIndex(m_Data.attributeStats(i).nominalCounts)) );
				}
			}
			
                        }*/
	
		double ris = 0.0;
		int indexMatrix=0;
		for (int i=0;i<first.numAttributes();++i){
			if (m_ActiveIndices[i] == 1 && i != first.classIndex()){
				double[][] weightDist = matricesDilca.get(indexMatrix);
				ris += weightDist[(int)first.value(i)][(int)second.value(i)] * weightDist[(int)first.value(i)][(int)second.value(i)] ; 
				indexMatrix++;
			}
		}
		double dist = Math.sqrt(ris);
		return (dist > cutOffValue)?Double.POSITIVE_INFINITY:dist;
	}

	public double distance(Instance first, Instance second, double cutOffValue, PerformanceStats stats) {
		return distance( first,  second,  cutOffValue);
	}
	
	public double distance(Instance first, Instance second, PerformanceStats stats) {
		return distance( first,  second);
	}

  	/**
   	 * returns the instances currently set.
     * 
     * @return 		the current instances
     */
  	public Instances getInstances() {
    	return m_Data;
  	}
	
	public void postProcessDistances(double[] distances) {  }
  
	/**
     * Update the distance function (if necessary) for the newly added instance.
     * 
     * @param ins		the instance to add
     */
  	public void update(Instance ins) {
		//System.out.println("====>UPDATE");
    	//validate();
    	//m_Ranges = updateRanges(ins, m_Ranges);
  		//m_Data.add(ins);
/*
		try{ 
			Filter filter = new ReplaceMissingValues();
			filter.setInputFormat(m_Data);
			m_Data = Filter.useFilter(m_Data,filter);
			Filter filter2 = new Discretize();
			filter2.setInputFormat(m_Data);
			m_Data = Filter.useFilter(m_Data,filter2);
		}catch(Exception e){
			System.out.println("Problem to clean the data with ReplaceMissingValues and Discretize Filter");
		}
		computeMatrix();
*/
	}

	/**
	 * Compute the distance matrices for each attribute. 
	 * Each distance matrix contains distances between each pair of values of the same attribute
	 * This is the main step of the DILCA procedure
	 */

	protected void computeMatrix(){
		matricesDilca = new Vector<double[][]>(); 
		Instances reducedData = null;
		Remove remove = new Remove();
		try{
			int toDel = 0;
			for (int i=0; i<m_ActiveIndices.length;++i){	
				if (m_ActiveIndices[i] == 0) toDel++;
			}
			int[] attributeToBeDeleted = new int[toDel];
			int j=0;			
			for (int i=0; i<m_ActiveIndices.length;++i){	
				if (m_ActiveIndices[i] == 0){
					attributeToBeDeleted[j] = i;
					j++;
				} 
			}
//			System.out.println("attributi da cancellare: ");
//			for (int i=0; i< attributeToBeDeleted.length;++i){
//				System.out.println(attributeToBeDeleted[i]+" ");
//			}
			
			remove.setAttributeIndicesArray(attributeToBeDeleted);
			remove.setInputFormat(m_Data);
			reducedData = Filter.useFilter(new Instances(m_Data),remove);
//			System.out.println(reducedData);
		}catch(Exception e){
			System.out.println("Problem to reduce the data using the active indices choosen by the user: "+e.getMessage());
		}
		Vector<Vector<Integer>> nearestV = contextVector(reducedData);	
		for (int i=0;i<nearestV.size();i++){
			System.out.println("\tfeature "+reducedData.attribute(i)+" con context lungo: "+nearestV.get(i).size());
			for (Integer o: nearestV.get(i)){
				System.out.println("\t\t "+ reducedData.attribute(o.intValue()));
			}
			double [][]matrix = calculateFeatureDistance(reducedData, i,nearestV.get(i));
			/*
			for (int k=0; k< matrix.length; ++k){
				for (int j=0; j< matrix[k].length; ++j) System.out.print(matrix[k][j]+" ");
				System.out.println();
			}
			*/
			matricesDilca.add(matrix);
		}	
//		System.out.println();
//		System.out.println("====================");
//		System.out.println();			
		
	}
	
	
	private double[][] matrixBetAttrNoised(Instances data, int indexTarget, int indexAttrCont){
		double[][] matrix = new double[data.attribute(indexTarget).numValues()][data.attribute(indexAttrCont).numValues()];
		//System.out.println("BBBBB "+data.attribute(indexTarget).numValues() + data.attribute(indexAttrCont).numValues());	
		for (int i=0;i<data.numInstances();i++){
			int firstValue= (int) data.instance(i).value(indexTarget);
			int secondValue = (int) data.instance(i).value(indexAttrCont);
			matrix[firstValue][secondValue]++;
		}
		
		
		double sensitivity = (2.0)/(epsilon);
		//System.out.println("sensitivity: "+sensitivity);
		LaplacianNoiseGenerator noise = new LaplacianNoiseGenerator(rand, 0, (sensitivity));
		
		for (int p = 0; p<matrix.length;p++) {
			for(int q = 0; q<matrix[0].length;q++) {
				//System.out.print("noise di "+ counts[p][q] +": "+noise.nextLaplacian() + ",  ");
				matrix[p][q] = matrix[p][q] + noise.nextLaplacian();
				if(matrix[p][q]<0) {
					matrix[p][q] = 0;
				}
				matrix[p][q] = (int)Math.round(matrix[p][q]);
				
	 		}
		}
		
		/*
		for (int k=0; k< matrix.length; ++k){
			for (int j=0; j< matrix[0].length; ++j) System.out.print(matrix[k][j]+" ");
			System.out.println();
		}
		*/
	
		return matrix;
		
	}
	
	private double[][] transpose(double[][] matrix) {
		double[][] temp = new double[matrix[0].length][matrix.length]; 
		for (int i = 0; i < matrix.length; i++) { 
			for (int j = 0; j < matrix[0].length; j++) { 
				temp[j][i] = matrix[i][j]; 
			} 
		} 
		matrix = temp;
		/*
		for (int k=0; k< temp.length; ++k){
			for (int j=0; j< temp[0].length; ++j) System.out.print(temp[k][j]+" ");
			System.out.println();
		}
		*/
		
		return temp;
	}

	private double[][] concate(double[][] a, double[][] b){
		/*
		for(int i=0;i<b.length;i++) {
		 for(double element: b[i]){
			  //for(int j = 0; j<b[0].length;j++) {
			    System.out.println(element);
			   //}
		  
		  }
		}
		  */ 
		//System.out.println("lungh a: "+a.length);
		//System.out.println("lungh b: "+b.length);
		  for(int i=0;i<a.length;i++){
		    a[i] = ArrayUtils.addAll(a[i],b[i]); 
		   }
		  /*
		  for(int i=0;i<b.length;i++) {
				 for(double element: b[i]){
					  //for(int j = 0; j<b[0].length;j++) {
					    System.out.println(element);
					   //}
				  
				  }
				}
		  
		  for(int i=0;i<a.length;i++){
			  for(int j = 0; j<a[0].length;j++) {
			    System.out.println("pippoo"+a[i][j]);
			   }
		  }
		  */
		  return a;
		     
		
	}
	
	public double[] columnSum(double [][] array){
	    int index = 0;
	    double temp[] = new double[array[index].length];
	    int col = array[0].length;
	    for (int i = 0; i < col; i++){
	        double sum = 0;

	        for (int j = 0; j < array.length; j++){
	            sum += array[j][i];

	        }
	        temp[index] = sum;
	        //System.out.println("Index is: " + index + " Sum is: "+sum);
	        index++;

	    }

	    return temp;
	}


	/**
	 * Compute the distance matrices for a specified attribute. 
	 * @param reducedData	reduced data erasing attribute not specified in the selection
	 * @param indexF	index of the attribute from which the valut-to-value distance matrix is extracted
	 * @param nearest	the context (attribute set) of the associated attribute indexed by indexF 
	 * This is the main step of the DILCA procedure
	 */

	private double [][] calculateFeatureDistance(Instances data, int indexF,Vector<Integer> nearest){
		
		//vector that contains in each position the sum of the number of the previous attribute values
		int[] featValNum = new int[nearest.size()];
		int totNumFeature = 0;
		//to manage the case of unary attribute
		if (nearest.size() > 0){
			totNumFeature = data.attribute(nearest.get(0)).numValues();
			featValNum[0]=0;
		}
		//System.out.println("nearest size: "+nearest.size());
		for (Integer i=0;i<nearest.size()-1;i++){
			featValNum[i+1] = featValNum[i] + data.attribute(nearest.get(i)).numValues(); 
			totNumFeature+=data.attribute(nearest.get(i+1)).numValues();;
		}
		/*
		for(int i = 0; i<featValNum.length;i++) {
			System.out.println(featValNum[i]);	//serve per completare l'unica matrice di contingenza che crea per l'attributo target con attributi context
		}
		*/
		int numValuesF = data.attribute(indexF).numValues(); 
		//System.out.println("num values: "+numValuesF); 	//totale valori attributo target
		//System.out.println("totNumFeature: "+totNumFeature); //totale valori degli attributi del context
		double [] normalization = new double[totNumFeature];
		double[][]matrixComputation = new double[numValuesF][];
		for(int j =0; j<nearest.size();j++){
			double[][] matr = new double[numValuesF][data.attribute(nearest.get(j)).numValues()];
			if(indexF<nearest.get(j)) {
				 matr = matrixBetAttrNoised(data, indexF, nearest.get(j));
				 //System.out.println("NOT traspose");
			}else {
				matr = transpose(matrixBetAttrNoised(data,nearest.get(j),indexF));
				//System.out.println("num righe "+matr.length);
				//System.out.println("traspose");
			}
			concate(matrixComputation, matr);
			
			//System.out.println("Aggiunto");
		}
		normalization = columnSum(matrixComputation);
		for (int k=0; k< matrixComputation.length; ++k){
			for (int j=0; j< matrixComputation[0].length; ++j) System.out.print(matrixComputation[k][j]+" ");
			System.out.println();
		}
		/*
		for (int k=0; k< matrixComputation.length; ++k){
			for (int j=0; j< matrixComputation[0].length; ++j) System.out.print(matrixComputation[k][j]+" ");
			System.out.println();
		}
		*/
		for (int i=0;i<matrixComputation.length;i++){
			for (Integer j=0;j<matrixComputation[i].length;j++){
				matrixComputation[i][j]=(normalization[j]==0)?0:matrixComputation[i][j]/normalization[j];
			}
		}
		
		for (int k=0; k< matrixComputation.length; ++k){
			for (int j=0; j< matrixComputation[0].length; ++j) System.out.print(matrixComputation[k][j]+" ");
			System.out.println();
		}
		
		double[][] result = new double[numValuesF][numValuesF];
		if (numValuesF == 1) result[0][0] = 0;
		
		for (int i=0;i<numValuesF;i++){
			for (int j=i+1;j<numValuesF;j++){
				result[i][j]=result[j][i]=eucl(matrixComputation[i],matrixComputation[j]);
			}
		}
		
		
		for (int k=0; k< result.length; ++k){
			for (int j=0; j< result[0].length; ++j) System.out.print(result[k][j]+" ");
			System.out.println();
		}
		
		
		return result;
	}

	/**
	 * Compute the distance matrices for a specified attribute. 
	 * @param data		data to be analyzed
	 * @param indexF	index of the attribute from which the valut-to-value distance matrix is extracted
	 * @param nearest	the context (attribute set) of the associated attribute indexed by indexF 
	 * This is the main step of the DILCA procedure
	 */
	private Vector<Vector<Integer>> contextVector(Instances data) {
		int n_attributes = data.numAttributes();
		Vector<Vector<Integer>> nearIndex = new Vector<Vector<Integer>>(n_attributes);
		int originalClass = data.classIndex(); 
		AttributeSelection attSel = new AttributeSelection();  // package weka.filters.supervised.attribute!
		//AttributeSelectionMean attSel = new AttributeSelectionMean();
		
		for (int i=0; i< n_attributes; i++){
			try{
				
				Vector<Integer> actual = new Vector<Integer>();
				if (data.attribute(i).numValues() > 1){
					data.setClassIndex(i);
					SymmetricalUncertAttributeSetEval eval = new SymmetricalUncertAttributeSetEval();
					FCBFSearch search = new FCBFSearch();
					//MeanSearch search = new MeanSearch();
					attSel.setEvaluator(eval);
					attSel.setSearch(search);
					attSel.SelectAttributes(data);
					
				/*
				 * 
					double[][] rankAtt = attSel.rankedAttributes();
					System.out.println("rankatt: ");
					for (double [] row: rankAtt) {
				    	System.out.println(Arrays.toString(row));
				    }
					System.out.println("fine rankatt");
				*/
					int[] indices = attSel.selectedAttributes();
				/*
					for (int j=0;j<indices.length;++j) {
						System.out.println("indices: "+indices[j]);
					}
				*/
					
					for (int j=0;j<indices.length;++j) {
						if ( indices[j] != i ) actual.add(indices[j]);
					}
				}	
					nearIndex.add(actual);	
					System.out.println("Attributo: "+i+nearIndex);
			}catch(Exception e){
				System.out.println("problem to select attribute for the context: "+e.getMessage()+" a suggestion: try to replaceMissingValues in the original dataset");
			}
		}
		
		data.setClassIndex(originalClass);
		return nearIndex;
	}


  /**
   * Returns a string describing this object.
   * 
   * @return 		a description of the evaluator suitable for
   * 			displaying in the explorer/experimenter gui
   */	
	public String globalInfo() {
    return 
        "This is the implememntation of the non parametric version of the Dilca distance function.\n\n" 
	  + "This approach allows to learn value-to-value distances between each pair of values for each attribute of the dataset."
	  +	"The distance between two values is computed indirectly based on the their distribution w.r.t. a set of related attributes (the context) carefully choosen.\n\n"
      + "For more information, see:\n\n"
      + getTechnicalInformation().toString();
  }
  
	/**
   * Returns an instance of a TechnicalInformation object, containing 
   * detailed information about the technical background of this class,
   * e.g., paper reference or book this class is based on.
   * 
   * @return 		the technical information about this class
   */
  public TechnicalInformation getTechnicalInformation() {
    TechnicalInformation 	result;
    result = new TechnicalInformation(Type.ARTICLE);
    result.setValue(Field.AUTHOR, "Dino Ienco, Ruggero G. Pensa and Rosa Meo");
    result.setValue(Field.TITLE, "From Context to Distance: Learning Dissimilarity for Categorical Data Clustering");
    result.setValue(Field.JOURNAL, "TKDD");
	result.setValue(Field.VOLUME, "6");
	result.setValue(Field.NUMBER, "1");
	result.setValue(Field.YEAR, "2012");
	result.setValue(Field.PAGES, "1");
    return result;
  }

  public String toString(){
	String res = "";
	for (double[][] p : matricesDilca){
		for (int i=0; i< p.length;++i){
			for (int j=0; j< p[i].length;++j){
				res += p[i][j]+" ";
			}
			res += "\n";
		}
		res += "\n\n\n";
	}
	return res;
  }

	private double eucl(double[] a, double[]b){
		double ris=0;
		for (Integer i=0;i<a.length;i++){
			ris += (a[i]-b[i]) * (a[i]-b[i]);
		}
		return Math.sqrt(ris/a.length);
	}

  public void clean() {
  }

}