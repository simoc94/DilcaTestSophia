package net.javaguides.maven_web_project.DilcaDistanceDiffPriv;

import org.apache.commons.lang3.ArrayUtils;

public class testconcate {
	
	private static double[][] concate(double[][] a, double[][] b){
		/*
		for(int i=0;i<b.length;i++) {
		 for(double element: b[i]){
			  //for(int j = 0; j<b[0].length;j++) {
			    System.out.println(element);
			   //}
		  
		  }
		}
		  */ 
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
	
	public static void main(String[]args) {
		double[][]ciao = new double[2][];
		double[][]ciao1 = {{7,5,4},{1,0,5}};
		concate(ciao, ciao1);
		
		for(int i = 0; i <ciao.length;i++) {
			for(int j = 0; j<ciao[0].length;j++) {
				System.out.println(ciao[i][j]);
			}
		}
		System.out.println("num righe: "+ciao.length+"num colonne: "+ciao[0].length);
		
	}

}
