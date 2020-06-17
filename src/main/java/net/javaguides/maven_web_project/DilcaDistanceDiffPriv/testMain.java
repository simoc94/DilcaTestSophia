package net.javaguides.maven_web_project.DilcaDistanceDiffPriv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import DilcaDistance.DilcaDistance;
import DilcaDistance.DilcaDistanceDiffPrivMean;
import DilcaDistance.DilcaDistanceDiffPrivRR;
import DilcaDistance.DilcaDistanceMean;
import smile.validation.NormalizedMutualInformation;
import smile.validation.NormalizedMutualInformation.Method;

public class testMain {
	
	static void NMI() throws Exception{
		try {
		System.out.println("Tell me what you want to compare! Digit 1 to compare Original Database Class with Dilca Distance, or digit 2 if You want to compare the Dilca Distance themselves!");
		BufferedReader bufferChoice = new BufferedReader(new InputStreamReader(System.in));
        int choice = Integer.parseInt(bufferChoice.readLine());
        if(choice==1) { 
			System.out.println("Please, enter the arff file's path of your database.");
			System.out.println("Sorry, pay attention that the class attribute is the last in your database...Thanks for your help :)");
	        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	        String s = bufferRead.readLine();
	        int[] classe = new int[0];
	        classe = BalloonNMISoloDilcaTestSuClasseDataset.loadArffClasse(s);
	        if(classe !=null) {
		        System.out.println("What you want compare?");
		        System.out.println("Digit 1, to compare original Database Class with Dilca Distance Relevance and Redondant?");
		        System.out.println("Digit 2, to compare original Database Class with Dilca Distance Mean?");
		        System.out.println("Digit 3, to compare original Database Class with Dilca Distance Relevance and Redondant DIFFERENTIALLY PRIVATE?");
		        System.out.println("Digit 4, to compare original Database Class with Dilca Distance MEAN DIFFERENTIALLY PRIVATE?");
		        BufferedReader bufferInt = new BufferedReader(new InputStreamReader(System.in));
		        int typeOfDilca = Integer.parseInt(bufferInt.readLine());
		        int[] cluster = new int[0];
		        if(typeOfDilca==1) {
		        	System.out.println("You have chosen to compare Original Database Class with Cluster defined by Dilca Distance RR");
		        	BalloonNMISoloDilcaTestSuClasseDataset.loadArff(s);
		        	DilcaDistance dd = new DilcaDistance();
		        	System.out.println("Enter the number of cluster you want to create: ");
		        	System.out.println("If you want a number of cluster that is the same of that of Original Database Class, plese digit 0. Otherwise enter the number of cluster you are interested: ");
		        	BufferedReader bufferCluster = new BufferedReader(new InputStreamReader(System.in));
		 	        int numCluster = Integer.parseInt(bufferCluster.readLine());
		        	if(numCluster==0) {
		        		numCluster = BalloonNMISoloDilcaTestSuClasseDataset.diffValues(classe);
		        		cluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, numCluster);
		        	}else if(numCluster < 0) {
		        		System.out.println("You can't choose a number of cluster smaller than 0!");
		        	}else {
		        		cluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, numCluster);
		        	}
		        	NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
		    		double value = nnn.measure(classe, cluster);
		    		System.out.println("NMI value between Original Database Class and Dilca Distance RR Cluster is: "+value);
		        }else if(typeOfDilca==2) {
		        	System.out.println("You have chosen the compare between Original Database Class with Cluster defined by Dilca Distance Mean");
		        	BalloonNMISoloDilcaTestSuClasseDataset.loadArff(s);
		        	double sigma= 0.1;
		        	DilcaDistanceMean dd = new DilcaDistanceMean(sigma);
		        	System.out.println("Now, tell me what value of sigma you want to use for the Mean?");
		        	System.out.println("You can use a number between (0.0, 1.0]:");
		        	BufferedReader bufferSigma = new BufferedReader(new InputStreamReader(System.in));
			        sigma = Double.parseDouble(bufferSigma.readLine());
			        if(sigma == 0 || sigma < 0.0 || sigma > 1.0) {
			        	System.out.println("You chose a sigma value that make me crazy :(");
			        	return;
			        }else {
			        	dd.setSigma(sigma);
			        }
			        System.out.println("Enter the number of cluster you want to create.");
		        	System.out.println("If you want a number of cluster that is the same of that of Original Database Class, plese digit 0. Otherwise enter the number of cluster you are interested: ");
		        	BufferedReader bufferCluster = new BufferedReader(new InputStreamReader(System.in));
		 	        int numCluster = Integer.parseInt(bufferCluster.readLine());
		        	if(numCluster==0) {
		        		numCluster = BalloonNMISoloDilcaTestSuClasseDataset.diffValues(classe);
		        		cluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, numCluster);
		        	}else if(numCluster < 0) {
		        		System.out.println("You can't choose a number of cluster smaller than 0!");
		        		return;
		        	}else {
		        		cluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, numCluster);
		        	}
		        	NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
		    		double value = nnn.measure(classe, cluster);
		    		System.out.println("NMI value between Original Database Class and Dilca Distance Mean Cluster, with Sigma="+sigma+", is: "+value);
		        }else if(typeOfDilca==3) {
		        	System.out.println("You have chosen the compare between Original Database Class with Cluster defined by Dilca Distance RR Differentially Private");
		        	System.out.println("So let me know what is the epsilon that you want to use for the Differential Privacy?");
		        	System.out.println("If you allow me, I suggest you a value between 0.001 and 1, but you could choose every number greater than 0");
		        	BalloonNMISoloDilcaTestSuClasseDataset.loadArff(s);
		        	double epsilon = 0.01;
		        	DilcaDistanceDiffPrivRR dd = new DilcaDistanceDiffPrivRR(epsilon);
		        	BufferedReader bufferEps = new BufferedReader(new InputStreamReader(System.in));
			        epsilon = Double.parseDouble(bufferEps.readLine());
			        if(epsilon > 0) {
			        	dd.setEpsilon(epsilon);
			        }else {
			        	System.out.println("You chose a number for epsilon ugual or smaller than 0. This is so bad :(");
			        	return;
			        }
			        System.out.println("Enter the number of cluster you want to create.");
		        	System.out.println("If you want a number of cluster that is the same of that of Original Database Class, plese digit 0. Otherwise enter the number of cluster you are interested: ");
		        	BufferedReader bufferCluster = new BufferedReader(new InputStreamReader(System.in));
		 	        int numCluster = Integer.parseInt(bufferCluster.readLine());
		        	if(numCluster==0) {
		        		numCluster = BalloonNMISoloDilcaTestSuClasseDataset.diffValues(classe);
		        		cluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, numCluster);
		        	}else if(numCluster < 0) {
		        		System.out.println("You can't choose a number of cluster smaller than 0!");
		        		return;
		        	}else {
		        		cluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, numCluster);
		        	}
		        	NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
		    		double value = nnn.measure(classe, cluster);
		    		System.out.println("NMI value between Original Database Class and Dilca Distance RR Differentially Private Cluster, with Epsilon="+epsilon+", is: "+value);
		        }else if(typeOfDilca==4) {
		        	System.out.println("You have chosen the compare between Original Database Class with Cluster defined by Dilca Distance Mean Differentially Private");
		        	BalloonNMISoloDilcaTestSuClasseDataset.loadArff(s);
		        	double epsilon = 0.01;
		        	double sigma= 0.1;
		        	DilcaDistanceDiffPrivMean dd = new DilcaDistanceDiffPrivMean(epsilon, sigma);
		        	System.out.println("So let me know what is the epsilon that you want to use for the Differential Privacy?");
		        	System.out.println("If you allow me, I suggest you a value between 0.001 and 1, but you could choose every number greater than 0");
		        	BufferedReader bufferEps = new BufferedReader(new InputStreamReader(System.in));
			        epsilon = Double.parseDouble(bufferEps.readLine());
			        if(epsilon > 0) {
			        	dd.setEpsilon(epsilon);
			        }else {
			        	System.out.println("You chose a number for epsilon ugual or smaller than 0. This is so bad :(");
			        	return;
			        }
			        System.out.println("Now, tell me what value of sigma you want to use for the Mean?");
		        	System.out.println("You can use a number between (0.0, 1.0]:");
		        	BufferedReader bufferSigma = new BufferedReader(new InputStreamReader(System.in));
			        sigma = Double.parseDouble(bufferSigma.readLine());
			        if(sigma == 0 || sigma < 0.0 || sigma > 1.0) {
			        	System.out.println("You chose a sigma value that make me crazy :(");
			        	return;
			        }else {
			        	dd.setSigma(sigma);
			        }
			        System.out.println("Enter the number of cluster you want to create.");
		        	System.out.println("If you want a number of cluster that is the same of that of Original Database Class, plese digit 0. Otherwise enter the number of cluster you are interested: ");
		        	BufferedReader bufferCluster = new BufferedReader(new InputStreamReader(System.in));
		 	        int numCluster = Integer.parseInt(bufferCluster.readLine());
		        	if(numCluster==0) {
		        		numCluster = BalloonNMISoloDilcaTestSuClasseDataset.diffValues(classe);
		        		cluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, numCluster);
		        	}else if(numCluster < 0) {
		        		System.out.println("You can't choose a number of cluster smaller than 0!");
		        		return;
		        	}else {
		        		cluster = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, numCluster);
		        	}
		        	NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
		    		double value = nnn.measure(classe, cluster);
		    		System.out.println("NMI value between Original Database Class and Dilca Distance Mean Differentially Private Cluster, with Epsilon="+epsilon+", and Sigma="+sigma+", is: "+value);
			        
		        }else {
		        	System.out.println("You must write one of this number!");
		        	return;
		        }
	        }else {
	        	//wrong path
	        }
        }else if(choice==2){
        	System.out.println("You decided to compare Dilca Distance method between themselves!");
        	System.out.println("Digit 1 to compare Dilca Distance RR and Dilca Distance RR Differenially Private");
        	System.out.println("Digit 2 to compare Dilca Distance RR and Dilca Distance Mean");
        	System.out.println("Digit 3 to compare Dilca Distance RR and Dilca Distance Mean Differenially Private");
        	System.out.println("Digit 4 to compare Dilca Distance RR Differenially Private and Dilca Distance Mean");
        	System.out.println("Digit 5 to compare Dilca Distance RR Differenially Private and Dilca Distance Mean Differentially Private");
        	System.out.println("Digit 6 to compare Dilca Distance Mean and Dilca Distance Mean Differentially Private");
        	BufferedReader bufferChoiceDilca = new BufferedReader(new InputStreamReader(System.in));
            int choiceBetDilca = Integer.parseInt(bufferChoiceDilca.readLine());
            
            if(choiceBetDilca==1){
            	System.out.println("Good choice! We're going to compare Dilca Distance RR and Dilca Distance RR Differenially Private");
            	System.out.println("Please, enter the arff file's path of your database.");
    			System.out.println("Sorry, pay attention that the class attribute is the last in your database...Thanks for your help :)");
    	        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
    	        String s = bufferRead.readLine();
    	        BalloonNMISoloDilcaTestSuClasseDataset.loadArff(s);
    	        int[] classe = BalloonNMISoloDilcaTestSuClasseDataset.loadArffClasse(s);
    	        int[] clusterRR = new int[0];
	        	DilcaDistance dd = new DilcaDistance();
	        	System.out.println("Enter the number of cluster you want to create with Dilca Distance method: ");
	        	System.out.println("If you want a number of cluster that is the same of that of Original Database Class, plese digit 0. Otherwise enter the number of cluster you are interested: ");
	        	BufferedReader bufferCluster = new BufferedReader(new InputStreamReader(System.in));
	 	        int numCluster = Integer.parseInt(bufferCluster.readLine());
	        	if(numCluster==0) {
	        		numCluster = BalloonNMISoloDilcaTestSuClasseDataset.diffValues(classe);
	        		clusterRR = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, numCluster);
	        	}else if(numCluster < 0) {
	        		System.out.println("You can't choose a number of cluster smaller than 0!");
	        		return;
	        	}else {
	        		clusterRR = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, numCluster);
	        	}
	        	
	        	System.out.println("Now we think about Dilca Distance RR Differentially Private method, so... let me know what is the epsilon that you want to use for the Differential Privacy?");
	        	System.out.println("If you allow me, I suggest you a value between 0.001 and 1, but you could choose every number greater than 0");
	        	double epsilon = 0.01;
	        	DilcaDistanceDiffPrivRR de = new DilcaDistanceDiffPrivRR(epsilon);
	        	BufferedReader bufferEps = new BufferedReader(new InputStreamReader(System.in));
	        	int[] clusterRRDP = new int[0];
		        epsilon = Double.parseDouble(bufferEps.readLine());
		        if(epsilon > 0) {
		        	de.setEpsilon(epsilon);
		        }else {
		        	System.out.println("You chose a number for epsilon ugual or smaller than 0. This is so bad :(");
		        	return;
		        }	
		        System.out.println("Enter the number of cluster you want to create for the Dilca Distance RR Differentially Private.");
	        	System.out.println("If you want a number of cluster that is the same of that of Original Database Class, plese digit 0. Otherwise enter the number of cluster you are interested: ");
	        	BufferedReader bufferCluster2 = new BufferedReader(new InputStreamReader(System.in));
	 	        int numCluster2 = Integer.parseInt(bufferCluster2.readLine());
	        	if(numCluster2==0) {
	        		numCluster2 = BalloonNMISoloDilcaTestSuClasseDataset.diffValues(classe);
	        		clusterRRDP = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(de, numCluster2);
	        	}else if(numCluster2 < 0) {
	        		System.out.println("You can't choose a number of cluster smaller than 0!");
	        		return;
	        	}else {
	        		clusterRRDP = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(de, numCluster2);
	        	}
	        	NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
	    		double value = nnn.measure(clusterRR, clusterRRDP);
	    		System.out.println("NMI value between Dilca Distance RR method and Dilca Distance RR Differentially Private Cluster, with Epsilon="+epsilon+", is: "+value);
		        
            }else if(choiceBetDilca==2) {
            	System.out.println("Interesting choice! We're going to compare Dilca Distance RR and Dilca Distance Mean");
            	System.out.println("Please, enter the arff file's path of your database.");
    			System.out.println("Sorry, pay attention that the class attribute is the last in your database...Thanks for your help :)");
    	        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
    	        String s = bufferRead.readLine();
    	        BalloonNMISoloDilcaTestSuClasseDataset.loadArff(s);
    	        int[] classe = BalloonNMISoloDilcaTestSuClasseDataset.loadArffClasse(s);
    	        int[] clusterRR = new int[0];
	        	DilcaDistance dd = new DilcaDistance();
	        	System.out.println("Enter the number of cluster you want to create with Dilca Distance method: ");
	        	System.out.println("If you want a number of cluster that is the same of that of Original Database Class, plese digit 0. Otherwise enter the number of cluster you are interested: ");
	        	BufferedReader bufferCluster = new BufferedReader(new InputStreamReader(System.in));
	 	        int numCluster = Integer.parseInt(bufferCluster.readLine());
	        	if(numCluster==0) {
	        		numCluster = BalloonNMISoloDilcaTestSuClasseDataset.diffValues(classe);
	        		clusterRR = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, numCluster);
	        	}else if(numCluster < 0) {
	        		System.out.println("You can't choose a number of cluster smaller than 0!");
	        	}else {
	        		clusterRR = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, numCluster);
	        	}
	        	
	        	
	        	double sigma= 0.1;
	        	DilcaDistanceMean de = new DilcaDistanceMean(sigma);
	        	System.out.println("Talk about Dilca Distance Mean method...");
	        	System.out.println("So let me know what is the epsilon that you want to use for the Differential Privacy?");
	        	System.out.println("If you allow me, I suggest you a value between 0.001 and 1, but you could choose every number greater than 0");
	        	System.out.println("Now, tell me what value of sigma you want to use for the Mean?");
	        	System.out.println("You can use a number between (0.0, 1.0]:");
	        	BufferedReader bufferSigma = new BufferedReader(new InputStreamReader(System.in));
		        sigma = Double.parseDouble(bufferSigma.readLine());
		        int[] clusterMean = new int[0];
		        if(sigma == 0 || sigma < 0.0 || sigma > 1.0) {
		        	System.out.println("You chose a sigma value that make me crazy :(");
		        	return;
		        }else {
		        	de.setSigma(sigma);
	        	}
		        System.out.println("Enter the number of cluster you want to create.");
	        	System.out.println("If you want a number of cluster that is the same of that of Original Database Class, plese digit 0. Otherwise enter the number of cluster you are interested: ");
	        	BufferedReader bufferCluster2 = new BufferedReader(new InputStreamReader(System.in));
	 	        int numCluster2 = Integer.parseInt(bufferCluster2.readLine());
	        	if(numCluster2==0) {
	        		numCluster2 = BalloonNMISoloDilcaTestSuClasseDataset.diffValues(classe);
	        		clusterMean = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(de, numCluster2);
	        	}else if(numCluster2 < 0) {
	        		System.out.println("You can't choose a number of cluster smaller than 0!");
	        		return;
	        	}else {
	        		clusterMean = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(de, numCluster2);
	        	}
	        	NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
	    		double value = nnn.measure(clusterRR, clusterMean);
	    		System.out.println("NMI value between Dilca Distance RR and Dilca Distance Mean Cluster, with Sigma="+sigma+", is: "+value);
	    		
	        }else if(choiceBetDilca==3) {
	        	System.out.println("Wow weird choice! You decided to compare Dilca Distance RR and Dilca Distance Mean Differentially Private");
            	System.out.println("Please, enter the arff file's path of your database.");
    			System.out.println("Sorry, pay attention that the class attribute is the last in your database...Thanks for your help :)");
    	        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
    	        String s = bufferRead.readLine();
    	        BalloonNMISoloDilcaTestSuClasseDataset.loadArff(s);
    	        int[] classe = BalloonNMISoloDilcaTestSuClasseDataset.loadArffClasse(s);
    	        int[] clusterRR = new int[0];
	        	DilcaDistance dd = new DilcaDistance();
	        	System.out.println("Enter the number of cluster you want to create with Dilca Distance method: ");
	        	System.out.println("If you want a number of cluster that is the same of that of Original Database Class, plese digit 0. Otherwise enter the number of cluster you are interested: ");
	        	BufferedReader bufferCluster = new BufferedReader(new InputStreamReader(System.in));
	 	        int numCluster = Integer.parseInt(bufferCluster.readLine());
	        	if(numCluster==0) {
	        		numCluster = BalloonNMISoloDilcaTestSuClasseDataset.diffValues(classe);
	        		clusterRR = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, numCluster);
	        	}else if(numCluster < 0) {
	        		System.out.println("You can't choose a number of cluster smaller than 0!");
	        		return;
	        	}else {
	        		clusterRR = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, numCluster);
	        	}
	        	
	        	
	        	double epsilon = 0.01;
	        	double sigma= 0.1;
	        	DilcaDistanceDiffPrivMean de = new DilcaDistanceDiffPrivMean(epsilon, sigma);
	        	System.out.println("So let me know what is the epsilon that you want to use for the Differential Privacy?");
	        	System.out.println("If you allow me, I suggest you a value between 0.001 and 1, but you could choose every number greater than 0");
	        	BufferedReader bufferEps = new BufferedReader(new InputStreamReader(System.in));
		        epsilon = Double.parseDouble(bufferEps.readLine());
		        int[] clusterMeanDP = new int[0];
		        if(epsilon > 0) {
		        	de.setEpsilon(epsilon);
		        }else {
		        	System.out.println("You chose a number for epsilon ugual or smaller than 0. This is so bad :(");
		        	return;
		        }
		        System.out.println("Now, tell me what value of sigma you want to use for the Mean?");
	        	System.out.println("You can use a number between (0.0, 1.0]:");
	        	BufferedReader bufferSigma = new BufferedReader(new InputStreamReader(System.in));
		        sigma = Double.parseDouble(bufferSigma.readLine());
		        if(sigma == 0 || sigma < 0.0 || sigma > 1.0) {
		        	System.out.println("You chose a sigma value that make me crazy :(");
		        	return;
		        }else {
		        	de.setSigma(sigma);
		        }
		        System.out.println("Enter the number of cluster you want to create.");
	        	System.out.println("If you want a number of cluster that is the same of that of Original Database Class, plese digit 0. Otherwise enter the number of cluster you are interested: ");
	        	BufferedReader bufferCluster2 = new BufferedReader(new InputStreamReader(System.in));
	 	        int numCluster2 = Integer.parseInt(bufferCluster2.readLine());
	        	if(numCluster2==0) {
	        		numCluster2 = BalloonNMISoloDilcaTestSuClasseDataset.diffValues(classe);
	        		clusterMeanDP = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(de, numCluster2);
	        	}else if(numCluster2 < 0) {
	        		System.out.println("You can't choose a number of cluster smaller than 0!");
	        		return;
	        	}else {
	        		clusterMeanDP = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(de, numCluster2);
	        	}
	        	NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
	    		double value = nnn.measure(clusterRR, clusterMeanDP);
	    		System.out.println("NMI value between Dilca Distance RR and Dilca Distance Mean Differentially Private Cluster, with Epsilon="+epsilon+", and Sigma="+sigma+", is: "+value);
		        
	        }else if(choiceBetDilca==4) {
	        	System.out.println("Great choice! You decided to compare Dilca Distance RR Differentially Private and Dilca Distance Mean");
            	System.out.println("Please, enter the arff file's path of your database.");
    			System.out.println("Sorry, pay attention that the class attribute is the last in your database...Thanks for your help :)");
    	        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
    	        String s = bufferRead.readLine();
    	        BalloonNMISoloDilcaTestSuClasseDataset.loadArff(s);
    	        int[] classe = BalloonNMISoloDilcaTestSuClasseDataset.loadArffClasse(s);
    	        System.out.println("So let me know what is the epsilon that you want to use for the Differential Privacy?");
	        	System.out.println("If you allow me, I suggest you a value between 0.001 and 1, but you could choose every number greater than 0");
	        	double epsilon = 0.01;
	        	DilcaDistanceDiffPrivRR dd = new DilcaDistanceDiffPrivRR(epsilon);
	        	BufferedReader bufferEps = new BufferedReader(new InputStreamReader(System.in));
		        epsilon = Double.parseDouble(bufferEps.readLine());
		        int[] clusterRRDP = new int[0];
		        if(epsilon > 0) {
		        	dd.setEpsilon(epsilon);
		        }else {
		        	System.out.println("You chose a number for epsilon ugual or smaller than 0. This is so bad :(");
		        	return;
		        }
		        
		        System.out.println("Enter the number of cluster you want to create.");
	        	System.out.println("If you want a number of cluster that is the same of that of Original Database Class, plese digit 0. Otherwise enter the number of cluster you are interested: ");
	        	BufferedReader bufferCluster = new BufferedReader(new InputStreamReader(System.in));
	 	        int numCluster = Integer.parseInt(bufferCluster.readLine());
	        	if(numCluster==0) {
	        		numCluster = BalloonNMISoloDilcaTestSuClasseDataset.diffValues(classe);
	        		clusterRRDP = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, numCluster);
	        	}else if(numCluster < 0) {
	        		System.out.println("You can't choose a number of cluster smaller than 0!");
	        		return;
	        	}else {
	        		clusterRRDP = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, numCluster);
	        	}
	        	
	        	double sigma= 0.1;
	        	DilcaDistanceMean de = new DilcaDistanceMean(sigma);
	        	System.out.println("Talk about Dilca Distance Mean method...");
	        	System.out.println("So let me know what is the epsilon that you want to use for the Differential Privacy?");
	        	System.out.println("If you allow me, I suggest you a value between 0.001 and 1, but you could choose every number greater than 0");
	        	System.out.println("Now, tell me what value of sigma you want to use for the Mean?");
	        	System.out.println("You can use a number between (0.0, 1.0]:");
	        	BufferedReader bufferSigma = new BufferedReader(new InputStreamReader(System.in));
		        sigma = Double.parseDouble(bufferSigma.readLine());
		        int[] clusterMean = new int[0];
		        if(sigma == 0 || sigma < 0.0 || sigma > 1.0) {
		        	System.out.println("You chose a sigma value that make me crazy :(");
		        	return;
		        }else {
		        	de.setSigma(sigma);
	        	}
		        System.out.println("Enter the number of cluster you want to create.");
	        	System.out.println("If you want a number of cluster that is the same of that of Original Database Class, plese digit 0. Otherwise enter the number of cluster you are interested: ");
	        	BufferedReader bufferCluster2 = new BufferedReader(new InputStreamReader(System.in));
	 	        int numCluster2 = Integer.parseInt(bufferCluster2.readLine());
	        	if(numCluster2==0) {
	        		numCluster2 = BalloonNMISoloDilcaTestSuClasseDataset.diffValues(classe);
	        		clusterMean = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(de, numCluster2);
	        	}else if(numCluster2 < 0) {
	        		System.out.println("You can't choose a number of cluster smaller than 0!");
	        		return;
	        	}else {
	        		clusterMean = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(de, numCluster2);
	        	}
	        	NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
	    		double value = nnn.measure(clusterRRDP, clusterMean);
	    		System.out.println("NMI value between Dilca Distance RR and Dilca Distance Mean Cluster, with Sigma="+sigma+", is: "+value);
		        
	        }
	        else if(choiceBetDilca==5) {
	        	System.out.println("Differential choice! ;D You decided to compare Dilca Distance RR Differenially Private and Dilca Distance Mean Differentially Private");
            	System.out.println("Please, enter the arff file's path of your database.");
    			System.out.println("Sorry, pay attention that the class attribute is the last in your database...Thanks for your help :)");
    			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
    	        String s = bufferRead.readLine();
    	        BalloonNMISoloDilcaTestSuClasseDataset.loadArff(s);
    	        int[] classe = BalloonNMISoloDilcaTestSuClasseDataset.loadArffClasse(s);
    	        System.out.println("So let me know what is the epsilon that you want to use for the Differential Privacy?");
	        	System.out.println("If you allow me, I suggest you a value between 0.001 and 1, but you could choose every number greater than 0");
	        	double epsilonRR = 0.01;
	        	DilcaDistanceDiffPrivRR dd = new DilcaDistanceDiffPrivRR(epsilonRR);
	        	BufferedReader bufferEps = new BufferedReader(new InputStreamReader(System.in));
		        epsilonRR = Double.parseDouble(bufferEps.readLine());
		        int[] clusterRRDP = new int[0];
		        if(epsilonRR > 0) {
		        	dd.setEpsilon(epsilonRR);
		        }else {
		        	System.out.println("You chose a number for epsilon ugual or smaller than 0. This is so bad :(");
		        	return;
		        }
		        
		        System.out.println("Enter the number of cluster you want to create.");
	        	System.out.println("If you want a number of cluster that is the same of that of Original Database Class, plese digit 0. Otherwise enter the number of cluster you are interested: ");
	        	BufferedReader bufferCluster = new BufferedReader(new InputStreamReader(System.in));
	 	        int numCluster = Integer.parseInt(bufferCluster.readLine());
	        	if(numCluster==0) {
	        		numCluster = BalloonNMISoloDilcaTestSuClasseDataset.diffValues(classe);
	        		clusterRRDP = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, numCluster);
	        	}else if(numCluster < 0) {
	        		System.out.println("You can't choose a number of cluster smaller than 0!");
	        		return;
	        	}else {
	        		clusterRRDP = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, numCluster);
	        	}
	        	
	        	
	        	double epsilonMean = 0.01;
	        	double sigma= 0.1;
	        	DilcaDistanceDiffPrivMean de = new DilcaDistanceDiffPrivMean(epsilonMean, sigma);
	        	System.out.println("So let me know what is the epsilon that you want to use for the Differential Privacy?");
	        	System.out.println("If you allow me, I suggest you a value between 0.001 and 1, but you could choose every number greater than 0");
	        	BufferedReader bufferEpsMean = new BufferedReader(new InputStreamReader(System.in));
	        	epsilonMean = Double.parseDouble(bufferEpsMean.readLine());
		        int[] clusterMeanDP = new int[0];
		        if(epsilonMean > 0) {
		        	de.setEpsilon(epsilonMean);
		        }else {
		        	System.out.println("You chose a number for epsilon ugual or smaller than 0. This is so bad :(");
		        	return;
		        }
		        System.out.println("Now, tell me what value of sigma you want to use for the Mean?");
	        	System.out.println("You can use a number between (0.0, 1.0]:");
	        	BufferedReader bufferSigma = new BufferedReader(new InputStreamReader(System.in));
		        sigma = Double.parseDouble(bufferSigma.readLine());
		        if(sigma == 0 || sigma < 0.0 || sigma > 1.0) {
		        	System.out.println("You chose a sigma value that make me crazy :(");
		        	return;
		        }else {
		        	de.setSigma(sigma);
		        }
		        System.out.println("Enter the number of cluster you want to create.");
	        	System.out.println("If you want a number of cluster that is the same of that of Original Database Class, plese digit 0. Otherwise enter the number of cluster you are interested: ");
	        	BufferedReader bufferCluster2 = new BufferedReader(new InputStreamReader(System.in));
	 	        int numCluster2 = Integer.parseInt(bufferCluster2.readLine());
	        	if(numCluster2==0) {
	        		numCluster2 = BalloonNMISoloDilcaTestSuClasseDataset.diffValues(classe);
	        		clusterMeanDP = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(de, numCluster2);
	        	}else if(numCluster2 < 0) {
	        		System.out.println("You can't choose a number of cluster smaller than 0!");
	        		return;
	        	}else {
	        		clusterMeanDP = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(de, numCluster2);
	        	}
	        	NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
	    		double value = nnn.measure(clusterRRDP, clusterMeanDP);
	    		System.out.println("NMI value between Dilca Distance RR DifferentiallyPrivate with epsilon: "+epsilonRR+", and Dilca Distance Mean Differentially Private Cluster with Epsilon="+epsilonMean+" and Sigma="+sigma+", is: "+value);
	        }else if (choiceBetDilca==6) {
	        	System.out.println("Fantastic choice! You decided to compare Dilca Distance Mean and Dilca Distance Mean Differentially Private");
            	System.out.println("Please, enter the arff file's path of your database.");
    			System.out.println("Sorry, pay attention that the class attribute is the last in your database...Thanks for your help :)");
    			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
    	        String s = bufferRead.readLine();
    	        BalloonNMISoloDilcaTestSuClasseDataset.loadArff(s);
    	        int[] classe = BalloonNMISoloDilcaTestSuClasseDataset.loadArffClasse(s);
    	        double sigmaMean= 0.1;
	        	DilcaDistanceMean dd = new DilcaDistanceMean(sigmaMean);
	        	System.out.println("Talk about Dilca Distance Mean method...");
	        	System.out.println("Tell me what value of sigma you want to use for the Mean?");
	        	System.out.println("You can use a number between (0.0, 1.0]:");
	        	BufferedReader bufferSigmaMean = new BufferedReader(new InputStreamReader(System.in));
	        	sigmaMean = Double.parseDouble(bufferSigmaMean.readLine());
		        int[] clusterMean = new int[0];
		        if(sigmaMean == 0 || sigmaMean < 0.0 || sigmaMean > 1.0) {
		        	System.out.println("You chose a sigma value that make me crazy :(");
		        	return;
		        }else {
		        	dd.setSigma(sigmaMean);
	        	}
		        System.out.println("Enter the number of cluster you want to create.");
	        	System.out.println("If you want a number of cluster that is the same of that of Original Database Class, plese digit 0. Otherwise enter the number of cluster you are interested: ");
	        	BufferedReader bufferCluster = new BufferedReader(new InputStreamReader(System.in));
	 	        int numCluster = Integer.parseInt(bufferCluster.readLine());
	        	if(numCluster==0) {
	        		numCluster = BalloonNMISoloDilcaTestSuClasseDataset.diffValues(classe);
	        		clusterMean = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, numCluster);
	        	}else if(numCluster < 0) {
	        		System.out.println("You can't choose a number of cluster smaller than 0!");
	        		return;
	        	}else {
	        		clusterMean = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(dd, numCluster);
	        	}
	        	
	        	double epsilon = 0.01;
	        	double sigmaMeanDP= 0.1;
	        	DilcaDistanceDiffPrivMean de = new DilcaDistanceDiffPrivMean(epsilon, sigmaMeanDP);
	        	System.out.println("So let me know what is the epsilon that you want to use for the Differential Privacy?");
	        	System.out.println("If you allow me, I suggest you a value between 0.001 and 1, but you could choose every number greater than 0");
	        	BufferedReader bufferEpsMean = new BufferedReader(new InputStreamReader(System.in));
	        	epsilon = Double.parseDouble(bufferEpsMean.readLine());
		        int[] clusterMeanDP = new int[0];
		        if(epsilon > 0) {
		        	de.setEpsilon(epsilon);
		        }else {
		        	System.out.println("You chose a number for epsilon ugual or smaller than 0. This is so bad :(");
		        	return;
		        }
		        System.out.println("Now, tell me what value of sigma you want to use for the Mean?");
	        	System.out.println("You can use a number between (0.0, 1.0]:");
	        	BufferedReader bufferSigmaMeanDP = new BufferedReader(new InputStreamReader(System.in));
	        	sigmaMeanDP = Double.parseDouble(bufferSigmaMeanDP.readLine());
		        if(sigmaMeanDP == 0 || sigmaMeanDP < 0.0 || sigmaMeanDP > 1.0) {
		        	System.out.println("You chose a sigma value that make me crazy :(");
		        	return;
		        }else {
		        	de.setSigma(sigmaMeanDP);
		        }
		        System.out.println("Enter the number of cluster you want to create.");
	        	System.out.println("If you want a number of cluster that is the same of that of Original Database Class, plese digit 0. Otherwise enter the number of cluster you are interested: ");
	        	BufferedReader bufferCluster2 = new BufferedReader(new InputStreamReader(System.in));
	 	        int numCluster2 = Integer.parseInt(bufferCluster2.readLine());
	        	if(numCluster2==0) {
	        		numCluster2 = BalloonNMISoloDilcaTestSuClasseDataset.diffValues(classe);
	        		clusterMeanDP = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(de, numCluster2);
	        	}else if(numCluster2 < 0) {
	        		System.out.println("You can't choose a number of cluster smaller than 0!");
	        		return;
	        	}else {
	        		clusterMeanDP = BalloonNMISoloDilcaTestSuClasseDataset.clusterData(de, numCluster2);
	        	}
	        	NormalizedMutualInformation nnn = new NormalizedMutualInformation(Method.SUM);
	    		double value = nnn.measure(clusterMean, clusterMeanDP);
	    		System.out.println("NMI value between Dilca Distance Mean with sigma: "+sigmaMean+", and Dilca Distance Mean Differentially Private Cluster with Epsilon="+epsilon+" and Sigma="+sigmaMeanDP+", is: "+value);
	        	
    	        
	        }
        	
        }else{
        	System.out.println("Please enter correct number!");
        	return;
        }
		}catch(NumberFormatException e){
			System.out.println("Input is not a valid integer");
		}
		
		
	}
		
}
