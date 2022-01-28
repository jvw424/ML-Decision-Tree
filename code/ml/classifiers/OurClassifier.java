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

        ArrayList<Example> ourExamples = data.getData(); // all the examples in the dataset 

        Example compareValue = ourExamples.get(0); // First example in the dataset used as a comparison value
        
        //find majority label 
        Integer survived = 0;
        Integer notsurvived = 0;
       
        for (int i=0; i< ourExamples.size();i++){
            if (ourExamples.get(i).getLabel() == 1){ //calculate number of examples that survived
                survived++;
            }else if (ourExamples.get(i).getLabel() == -1){ //calculate number of examples that didn't survive
                notsurvived++;
            }
        }
        Integer majorityLabelCount = Math.max(survived, notsurvived); 
        Integer majorityLabelBinary = 0; 
        if (majorityLabelCount == notsurvived){//convert majoritylabel from value to class/label binary (-1 or 1)
            majorityLabelBinary = -1; 
        }else{
            majorityLabelBinary = 1; 
        }


        //System.out.println("survived number" + survived);
        //System.out.println("not survived number" + notsurvived);
        //System.out.println("majority number" + majorityLabel);

    
        for (int i=1; i< ourExamples.size();i++){
            if(!ourExamples.get(i).equalFeatures(compareValue) ){ // If all the data have the same feature values, pick majority label
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
    // titanic train dataset
    // DataSet ourData = new DataSet("C:\\Users\\jvw42\\ML\\assign2-starter\\code\\ml\\data\\titanic-train.csv");

    DataSet ourData = new DataSet("/Users/ASW/Documents/GitHub/ML-Decision-Tree/code/ml/data/titanic-train.csv");
   
      // print the object
      System.out.println(ourData.toString());
    }
}


