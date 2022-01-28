package ml.classifiers;

import ml.Example;

import java.util.ArrayList;

import ml.DataSet;


public class OurClassifier implements Classifier{

    DataSet ourData = new DataSet("titanic-train.csv");


// If all data belong to the same class, pick that label
// If all the data have the same feature values, pick majority label
// If we’re out of features to examine, pick majority label
// If the we don’t have any data left, pick majority label of parent

 
	
	
	@Override
	public void train(DataSet data) {

        ArrayList<Example> ourExamples = data.getData();

        Example compareValue = ourExamples.get(0);

        


        for (int i=1; i< ourExamples.size();i++){
            if(!ourExamples.get(i).equalFeatures(compareValue) ){

                // At a base case TODO 

            }



        }


        

        



	}

    // @Override
	// public ArrayList<double>[] getError(DataSet data) {



	// }

	@Override
	public double classify(Example example) {
        return 0;
	}


    
}
class Main {
    public static void main(String[] args) {

     DataSet ourData = new DataSet("C:\\Users\\jvw42\\ML\\assign2-starter\\code\\ml\\data\\titanic-train.csv");

    
  
      // print the object
      System.out.println(ourData.toString());
    }
}

