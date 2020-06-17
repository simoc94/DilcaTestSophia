package net.javaguides.maven_web_project.DilcaDistanceDiffPriv;

import java.util.HashMap;
import java.util.Map;

public class TestConvertStringToInt {
	
	public static void main(String[]args) {
		
		String[] array = {"pluto","pluto", "pippo", "paperino", "pippo", "topolino", "topolino", "pippo", "paperino", "pluto"};
		
		for(int i = 0; i<array.length;i++) {
			System.out.println(array[i]);
		}
		Map<String, Integer> stringsById = new HashMap<>();
		int q = 0;
		for(int i=0; i<array.length;i++) {
			
			for(int j=1;j<array.length;j++) {
				if(!stringsById.containsKey(array[i])) {
					//System.out.print("sono bello");
					stringsById.put(array[i], q);
				}
				
				if(array[j].equals(array[i])) {
					stringsById.put(array[j],q);
				}
				
			}
			q++;
		}
		/*stringsById.put( array[0],1);
		stringsById.put(array[2],2);
		stringsById.put(array[3],3);
		System.out.print(stringsById.containsKey(array[0]));
		System.out.print(stringsById.containsKey(array[6]));*/
		System.out.print(stringsById);
		//System.out.println(stringsById.get(array[2]));
		
		int[] speranza = new int[array.length];
		for(int i = 0; i<speranza.length;i++) {
			speranza[i] = stringsById.get(array[i]);
		}
		System.out.println();
		for(int i = 0; i<speranza.length;i++) {
			System.out.println(speranza[i]);
		}
	}

}
