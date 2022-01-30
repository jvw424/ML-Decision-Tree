package ml.classifiers;

import ml.Example;

import java.util.ArrayList;
import java.util.Set;

import ml.DataSet;


public class OurClassifier implements Classifier{

    DataSet ourData = new DataSet("titanic-train.csv");

    public static Boolean firstCase = false;
    public static Boolean secondCase = false;


// If all data belong to the same class, pick that label
// If all the data have the same feature values, pick majority label
// If we’re out of features to examine, pick majority label
// If the we don’t have any data left, pick majority label of parent
// stop at maxDepth - -- from maxDepth every loop through and when 0 stop building tree

 
	
	
	
	public void train(DataSet data , int maxDepth, DecisionTreeNode parentNode, Set<Integer> featuresLeft) { 

        ArrayList<Example> ourExamples = data.getData(); // all the examples in the dataset 

        Example compareValue = ourExamples.get(0); // First example in the dataset used as a comparison value
        
        
        ArrayList<Double> errorList = eList(data);

        
        //find majority label 
        Integer survived = 0;
        Integer notsurvived = 0;

        //DO WE NEED TO CHANGE TO MAJLABEL OF A SPECIFIC FEATURE VALUE?

        // tally up label for examples that survived or didnt survive 
        for (int i=0; i< ourExamples.size();i++){
            if (ourExamples.get(i).getLabel() == 1){ //calculate number of examples that survived
                survived++;
            }else if (ourExamples.get(i).getLabel() == -1){ //calculate number of examples that didn't survive
                notsurvived++;
            }
        }

        Integer majorityLabelCount = Math.max(survived, notsurvived); 
        Integer minorityLabelCount = Math.min(survived, notsurvived); 
        Integer majorityLabelBinary = 0; 
        Integer minorityLabelBinary = 0; 
        if (majorityLabelCount == notsurvived){//convert majoritylabel from value to class/label binary (-1 or 1)
            majorityLabelBinary = -1; 
            minorityLabelBinary = 1;
        }else{
            majorityLabelBinary = 1; 
            minorityLabelBinary = -1;
        }

        // base cases 
        // ADD IN DEPTH LIMIT BASE CASE
        // 1 will go right for feature and 0 go left for feature 

        // build root with the smallest training error
        DecisionTreeNode root = new DecisionTreeNode(0); // pass an int CHANGE
        // If we’re out of features to examine, pick majority label
        DecisionTreeNode rightLeaf;
        DecisionTreeNode leftLeaf;
        if(featuresLeft.isEmpty()){
        double majorityLabelBinaryDouble = Double.valueOf(majorityLabelBinary);
        double minorityLabelBinaryDouble = Double.valueOf(minorityLabelBinary);
        if(majorityLabelBinary == 1){
            rightLeaf = new DecisionTreeNode(majorityLabelBinaryDouble); 
            leftLeaf = new DecisionTreeNode(minorityLabelBinaryDouble);
        }
        else{
            leftLeaf = new DecisionTreeNode(majorityLabelBinaryDouble); 
            rightLeaf = new DecisionTreeNode(minorityLabelBinaryDouble);
        }
        parentNode.setLeft(leftLeaf);
        parentNode.setRight(rightLeaf);
        }
        // If the we don’t have any data left, pick majority label of parent
        if(ourExamples.isEmpty()){
            

	}

        }
  
        for (int i=1; i< ourExamples.size();i++){
            // If all data belong to the same class, pick that label
            if(compareValue.getLabel() != ourExamples.get(i).getLabel()){
                firstCase = false;
            } // If all the data have the same feature values, pick majority label
            else if(!ourExamples.get(i).equalFeatures(compareValue) ){
                secondCase = false;
            } 
        }

        // recursive alg part 
        /*
        - pick next lowest training error 
        - 
        */
  
        
        // set left node and set right node 
        

        /*
        Should use minimum training error after splitting as the criterion for picking features.
         In the case of a tie, break the tie based on feature number with lower feature numbers 
         being better (i.e. more likely to split).
        */


	}

    	public ArrayList<Double> eList ( DataSet data) {
        ArrayList<Double> result = new ArrayList<Double>();
        ArrayList<Example> listData = data.getData();

        Set<Integer> featSet = listData.get(0).getFeatureSet();

        featSet.forEach((feat) ->{
            ArrayList<Integer> bin0_died = new ArrayList<Integer>();
            ArrayList<Integer> bin0_survived = new ArrayList<Integer>();
            ArrayList<Integer> bin1_died = new ArrayList<Integer>();
            ArrayList<Integer> bin1_survived = new ArrayList<Integer>();
     

            listData.forEach((example) ->{
                if(example.getFeature(feat) == 0.0){
                    if(example.getLabel() == 0.0){
                        bin0_died.add(4);
                    }
                    else{
                        bin0_survived.add(4);
                    }
                }
                else{
                    if(example.getLabel() == 0.0){
                        bin1_died.add(4);
                    }
                    else{
                        bin1_survived.add(4);
                    }
                }
            });
            Integer bin0_majority = Math.max(bin0_died.size(), bin0_survived.size() );
            Integer bin1_majority = Math.max(bin1_died.size(), bin1_survived.size() );

            double error =  (1 - ((bin0_majority + bin1_majority)/listData.size()));

            result.add(error);
        });
        return result;
	}

    
	public ArrayList<Double> eList ( DataSet data) {
        ArrayList<Double> result = new ArrayList<Double>();
        ArrayList<Example> listData = data.getData();

        Set<Integer> featSet = listData.get(0).getFeatureSet();

        featSet.forEach((feat) ->{
            ArrayList<Integer> bin0_died = new ArrayList<Integer>();
            ArrayList<Integer> bin0_survived = new ArrayList<Integer>();
            ArrayList<Integer> bin1_died = new ArrayList<Integer>();
            ArrayList<Integer> bin1_survived = new ArrayList<Integer>();
     

            listData.forEach((example) ->{
                if(example.getFeature(feat) == 0.0){
                    if(example.getLabel() == 0.0){
                        bin0_died.add(4);
                    }
                    else{
                        bin0_survived.add(4);
                    }
                }
                else{
                    if(example.getLabel() == 0.0){
                        bin1_died.add(4);
                    }
                    else{
                        bin1_survived.add(4);
                    }
                }
            });
            Integer bin0_majority = Math.max(bin0_died.size(), bin0_survived.size() );
            Integer bin1_majority = Math.max(bin1_died.size(), bin1_survived.size() );

            double error =  (1 - ((bin0_majority + bin1_majority)/listData.size()));

            result.add(error);
        });
        return result;
	}


   

	@Override
	public double classify(Example example) {
        return 0;
	}


    
}
class Main {

    public static int bin0_died = 0;
    public static int bin0_survived = 0;
    public static int bin1_died = 0;
    public static int bin1_survived = 0;


    public static ArrayList<Double> eList ( DataSet data) {
        ArrayList<Double> result = new ArrayList<Double>();
        ArrayList<Example> listData = data.getData();

        Set<Integer> featSet = listData.get(0).getFeatureSet();

        featSet.forEach((feat) -> {
            
            bin0_died = 0;
            bin0_survived = 0;
            bin1_died = 0;
            bin1_survived = 0;
            
            
     

            listData.forEach((example) ->{
                if(example.getFeature(feat) == 0.0){
                    if(example.getLabel() == -1.0){
                        bin0_died += 1;
                    }
                    else{
                        bin0_survived +=1;
                    }
                }
                else{
                    if(example.getLabel() == -1.0){
                        bin1_died += 1;
                    }
                    else{
                        bin1_survived +=1;
                    }
                }
             
            });
            // System.out.print(total);
            Integer bin0_majority = Math.max(bin0_died, bin0_survived);
            Integer bin1_majority = Math.max(bin1_died, bin1_survived);
         
          
            
            double error =  (1 - ((bin0_majority + bin1_majority)/ (double)listData.size()));

    

            result.add(error);

        });
        return result;
    }
    public static void main(String[] args) {


     DataSet ourData = new DataSet("C:\\Users\\jvw42\\ML\\assign2-starter\\code\\ml\\data\\titanic-train.csv");
  
    public static void main(String[] args) {
    // titanic train dataset
    // DataSet ourData = new DataSet("C:\\Users\\jvw42\\ML\\assign2-starter\\code\\ml\\data\\titanic-train.csv");

    DataSet ourData = new DataSet("/Users/ASW/Documents/GitHub/ML-Decision-Tree/code/ml/data/titanic-train.csv");
   
      // print the object
      System.out.println(eList(ourData));
      System.out.println(ourData.getData().get(0).getLabel());
    }
}


