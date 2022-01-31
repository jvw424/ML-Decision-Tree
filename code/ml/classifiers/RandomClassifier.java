// Allison Sullivan Wu & Joe Williams Assignment 2 
package ml.classifiers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import ml.Example;
import ml.DataSet;

/**
 * A classifier that randomly labels examples as either -1 or 1.
 * 
 * @author dkauchak
 *
 */
public class RandomClassifier implements Classifier{
	public static Random rand = new Random();
	
	@Override
	public void train(DataSet data) {
		// easiest training method ever!
	}

	@Override
	public double classify(Example example) {
            return rand.nextInt(2) == 0 ? 1 : -1;
	}


	public static double errorRate(DataSet data){

		ArrayList<Double> resultList = new ArrayList<Double>();
		Double sum = 0.0;
		for(int j = 0; j <100; j++){
			DataSet[] splits = data.split(0.8);

			int correctCount = 0;
			for(int i = 0; i < splits[0].getData().size(); i++){
				int randNum =
				rand.nextInt(2) == 0 ? 1 : -1;
				if( splits[0].getData().get(i).getLabel() == randNum){
					correctCount +=1;
				}
			}
			resultList.add(correctCount / Double.valueOf(data.getData().size()));
			
		}
		
		for (Double i : resultList) {
			 sum += i;
    	}
		return  sum/ resultList.size();
	}


	public static void main(String[] args) {

   

    

		DataSet ourData = new DataSet("C:\\Users\\jvw42\\ML\\assign2-starter\\code\\ml\\data\\titanic-train.csv");


		
		System.out.println(errorRate(ourData));

		
   
	   
		 
   
	   }
}
