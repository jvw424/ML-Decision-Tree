package ml.classifiers;

import ml.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import ml.DataSet;


public class OurClassifier implements Classifier{

    DataSet ourData = new DataSet("titanic-train.csv");

    //Variables for calculating majority label 
    public static int bin0_died = 0;
    public static int bin0_survived = 0;
    public static int bin1_died = 0;
    public static int bin1_survived = 0;
    public static int splitFeature;


// Base Cases 

// If all data belong to the same class, pick that label
// If all the data have the same feature values, pick majority label
// If we’re out of features to examine, pick majority label
// If the we don’t have any data left, pick majority label of parent
// stop at maxDepth - -- from maxDepth every loop through and when 0 stop building tree

    /*
    Calculate for each example if the label is equal to the label of the first example 
    labelEquality checks to see if all the examples have the same class/label value. Returns 
    True if all equal and false if not all equal. 
    */
    public static boolean labelEquality(ArrayList<Example> data){

        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getLabel() != data.get(0).getLabel()){
                return false;
            }
        };
        return true;
    }
    /*
    Calculate for each example if the feature is equal to the label of the first example 
    feature. The function featureEquality checks to see if all the examples have the same feature
    value. Returns True if all equal and false if not all equal. 
    */
    public static boolean featureEquality(ArrayList<Example> data){

        for(int i = 0; i < data.size(); i++){
            if(!data.get(i).equalFeatures( data.get(0))){
                return false;
            }
        };
        return true;
    }

 
	
	
	// return DecisionTreeNode
	public static DecisionTreeNode train(DataSet originalData, ArrayList<Example> data , int maxDepth, DecisionTreeNode parentNode, Set<Integer> featuresLeft) {
        
        //base cases 

<<<<<<< HEAD
        if(data.isEmpty()){
            int splitFeat =parentNode.getFeatureIndex();
=======
        
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
>>>>>>> fbf2bb0a30c4c3c6cc37ca5f4efd6703f7678de9

        }
        // If we’re out of features to examine, pick majority label
        if(featuresLeft.isEmpty()){
<<<<<<< HEAD
            return getMajorityLabel(data);
=======
        double majorityLabelBinaryDouble = Double.valueOf(majorityLabelBinary);
        double minorityLabelBinaryDouble = Double.valueOf(minorityLabelBinary);
        if(majorityLabelBinary == 1){
            rightLeaf = new DecisionTreeNode(majorityLabelBinaryDouble); 
            leftLeaf = new DecisionTreeNode(minorityLabelBinaryDouble);
>>>>>>> fbf2bb0a30c4c3c6cc37ca5f4efd6703f7678de9
        }
        // If all data belong to the same class, pick that label
        if(labelEquality(data)){
            return (new DecisionTreeNode(data.get(0).getLabel()));
        }
        // If all the data have the same feature values, pick majority label
        if(featureEquality(data)){
            return getMajorityLabel(data);
        }
        if(maxDepth == 0){
            return getMajorityLabel(data);
        }

<<<<<<< HEAD

        //Not at a base case:
        int sFeature = getSplitFeature(data, featuresLeft);
        featuresLeft.remove(sFeature);
        Set<Integer> nFeats = featuresLeft;

        ArrayList<Example> leftList = new ArrayList<Example>();
        ArrayList<Example> rightList = new ArrayList<Example>();

        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getFeature(sFeature) == 0){
                leftList.add(data.get(i));
            }
            else{
                rightList.add(data.get(i));
            }
=======
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
>>>>>>> fbf2bb0a30c4c3c6cc37ca5f4efd6703f7678de9
        }

        int newDepth = maxDepth--;
        

        parentNode.setLeft(train(originalData, leftList,  newDepth, parentNode, nFeats));
        parentNode.setRight(train(originalData, rightList, newDepth, parentNode, nFeats));

    }

        /* getSplitFeature takes an arraylist of examples, which is our data, and a Set of Ingegers 
        which contain the index of the features that are left. This returns the minimum value in the errorlist
         */
        public static int getSplitFeature(ArrayList<Example> data , Set<Integer> featuresLeft){
            HashMap<Integer, Double> errorList = eList(data, featuresLeft);
            for(int i = 0; i < errorList.keySet().size(); i++){
                if(errorList.get(i) == (Collections.min(errorList.values()))){
                    return i;
                }}
                return -1;
        }

        /* 
        Takes the data and features left and returns a new internal node with the minimum value in the error list
         */

        public static DecisionTreeNode getRootNode(ArrayList<Example> data , Set<Integer> featuresLeft){
            int splitFeat = getSplitFeature(data, featuresLeft);
            return new DecisionTreeNode(splitFeat);
        }

        /* 
        eList takes the data and the features that are left and tallies up the label value for feature value
        0 and feature value 1. It finds the majority label value for each feature value and calculates the
        training error. The feature and its training error are then added to the results Hasmpa which is returned

         */
        public static HashMap<Integer, Double> eList ( ArrayList<Example> data, Set<Integer> featSet) {
            HashMap<Integer,Double> result = new HashMap<Integer, Double>();
        
            featSet.forEach((feat) -> {
                bin0_died = 0;
                bin0_survived = 0;
                bin1_died = 0;
                bin1_survived = 0;
    
                data.forEach((example) ->{
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
                Integer bin0_majority = Math.max(bin0_died, bin0_survived);
                Integer bin1_majority = Math.max(bin1_died, bin1_survived);

<<<<<<< HEAD
                double error =  (1 - ((bin0_majority + bin1_majority)/ (double)data.size()));
=======
            double error =  (1 - ((bin0_majority + bin1_majority)/listData.size()));

            result.add(error);
        });
        return result;
	}
>>>>>>> fbf2bb0a30c4c3c6cc37ca5f4efd6703f7678de9

                result.put(feat, error);
    
            });
            return result;
        }

        public static DecisionTreeNode getMajorityLabel(ArrayList<Example> data){
            int died = 0;
            int survived = 0;
         
            for(int i = 0; i < data.size(); i++ ){
                if(data.get(i).getLabel() == -1.0){
                    died += 1;
                }
                else{
                    survived += 1;
                }
            }
    
            if(survived > died){
               return new DecisionTreeNode(1.0);
            }
            return new DecisionTreeNode(-1.0);
        }


    



   

	@Override
	public double classify(Example example) {
        return 0;
	}

    @Override
    public void train(DataSet data) {
        // TODO Auto-generated method stub
        
    }




   


    
}
class Main {
<<<<<<< HEAD
    // public static int bin0_died = 0;
    // public static int bin0_survived = 0;
    // public static int bin1_died = 0;
    // public static int bin1_survived = 0;
    // public static int splitFeature;
=======
>>>>>>> fbf2bb0a30c4c3c6cc37ca5f4efd6703f7678de9


    // public static boolean labelEquality(ArrayList<Example> data){

    //     for(int i = 0; i < data.size(); i++){
    //         if(data.get(i).getLabel() != data.get(0).getLabel()){
    //             return false;
    //         }
    
    //     };
        
    //     return true;
    // }

    // public static boolean featureEquality(ArrayList<Example> data){

    //     for(int i = 0; i < data.size(); i++){
    //         if(!data.get(i).equalFeatures(data.get(0))){
    //             return false;
    //         }
    //     };
        
    //     return true;
    // }

    // public static int getSplitFeature(ArrayList<Example> data , Set<Integer> featuresLeft){
    //     HashMap<Integer, Double> errorList = eList(data, featuresLeft);
    //     for(int i = 0; i < errorList.keySet().size(); i++){
    //         if(errorList.get(i) == (Collections.min(errorList.values()))){
    //             return i;
    //         }}
    //         return -1;
    // }

    // public static DecisionTreeNode getMajorityLabel(ArrayList<Example> data){
    //     int died = 0;
    //     int survived = 0;
     
    //     for(int i = 0; i < data.size(); i++ ){
    //         if(data.get(i).getLabel() == -1.0){
    //             died += 1;
    //         }
    //         else{
    //             survived += 1;
    //         }
    //     }

    //     if(survived > died){
    //        return new DecisionTreeNode(1.0);
    //     }
    //     return new DecisionTreeNode(-1.0);
    // }

    // public static DecisionTreeNode getParentLabel(DataSet originalData, int feature, int isLeft){

        
    //     int died0 = 0;
    //     int survived0 = 0;
    //     int died1 = 0;
    //     int survived1= 0;

    //     for(int i = 0; i < originalData.getData().size(); i++ ){
    //         if(originalData.getData().get(i).getFeature(feature) == 0){
    //             if(originalData.getData().get(i).getLabel() == -1.0){
    //                 died0 += 1;

    //             }
    //             else{
    //                 survived0 += 1;
    //             }
    //         }
    //         else{
    //             if(originalData.getData().get(i).getLabel() == -1.0){
    //                 died1 += 0;
    //             }
    //             else{
    //                 survived1 += 1;
    //             }
    //         }
            
    //     }

    //     if(isLeft == 0){
    //         if(survived0 > died0){
    //             return new DecisionTreeNode(1.0);
    //         }
    //         else{
    //             return new DecisionTreeNode(-1.0);
    //         }

    //     }

    //     else{
    //         if(survived1 > died1){
    //             return new DecisionTreeNode(1.0);
    //         }
    //         else{
    //             return new DecisionTreeNode(-1.0);
    //         }

    //     }
        

        
    

    // }

 
	
	
	// //int maxDepth, DecisionTreeNode parentNode,
	// public static DecisionTreeNode train(DataSet originalData, ArrayList<Example> data, DecisionTreeNode parentNode, Set<Integer> featuresLeft) {
        
 

    //     // If the we don’t have any data left, pick majority label of parent
    //     if(data.isEmpty()){
           



            

    //     }
    //     // If we’re out of features to examine, pick majority label
    //     if(featuresLeft.isEmpty()){
    //         return getMajorityLabel(data);
            
    //         //base case
    //     }
    //     // If all data belong to the same class, pick that label
    //     if(labelEquality(data)){
    //         // At base case
    //     }
    //     // If all the data have the same feature values, pick majority label
    //     if(featureEquality(data)){
    //         //another base case
    //     }
    //     // if(maxDepth == 0){
    //     //     //at a base case
    //     // }


    //     int sFeature = getSplitFeature(data, featuresLeft);

    //     ArrayList<Example> leftList = new ArrayList<Example>();
    //     ArrayList<Example> rightList = new ArrayList<Example>();

    //     for(int i = 0; i < data.size(); i++){
          
    //         if(data.get(i).getFeature(sFeature) == 0){
    //             leftList.add(data.get(i));
    //         }
    //         else{
    //             rightList.add(data.get(i));
    //         }

    //     }
    //     System.out.println(leftList.size());

    //     System.out.println(rightList.size());
      






        
        
    //     }


    //     public static HashMap<Integer, Double> eList ( ArrayList<Example> data, Set<Integer> featSet) {
    //         HashMap<Integer,Double> result = new HashMap<Integer, Double>();
            
    
    
    //         featSet.forEach((feat) -> {
    //             bin0_died = 0;
    //             bin0_survived = 0;
    //             bin1_died = 0;
    //             bin1_survived = 0;
    
    //             data.forEach((example) ->{
    //                 if(example.getFeature(feat) == 0.0){
    //                     if(example.getLabel() == -1.0){
    //                         bin0_died += 1;
    //                     }
    //                     else{
    //                         bin0_survived +=1;
    //                     }
    //                 }
    //                 else{
    //                     if(example.getLabel() == -1.0){
    //                         bin1_died += 1;
    //                     }
    //                     else{
    //                         bin1_survived +=1;
    //                     }
    //                 }
                 
    //             });
    //             Integer bin0_majority = Math.max(bin0_died, bin0_survived);
    //             Integer bin1_majority = Math.max(bin1_died, bin1_survived);

    //             double error =  (1 - ((bin0_majority + bin1_majority)/ (double)data.size()));

    //             result.put(feat, error);
    
    //         });
    //         return result;
    //     }


    
    
    public static void main(String[] args) {
        

        


     DataSet ourData = new DataSet("C:\\Users\\jvw42\\ML\\assign2-starter\\code\\ml\\data\\titanic-train.csv");
  
<<<<<<< HEAD
=======
    public static void main(String[] args) {
    // titanic train dataset
    // DataSet ourData = new DataSet("C:\\Users\\jvw42\\ML\\assign2-starter\\code\\ml\\data\\titanic-train.csv");

    DataSet ourData = new DataSet("/Users/ASW/Documents/GitHub/ML-Decision-Tree/code/ml/data/titanic-train.csv");
   
>>>>>>> fbf2bb0a30c4c3c6cc37ca5f4efd6703f7678de9
      // print the object
      System.out.println(ourData.getAllFeatureIndices());
      //train(ourData.getData(), ourData.getAllFeatureIndices());
    }
}


