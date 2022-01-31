package ml.classifiers;

import ml.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.Scanner;
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
	public static DecisionTreeNode train(DataSet originalData, ArrayList<Example> data , int maxDepth, DecisionTreeNode parentNode, Set<Integer> featuresLeft,ArrayList<Example> prevData ) {
        

        // If the we don’t have any data left, pick majority label of parent
        if(data.isEmpty()){
            return getMajorityLabel(prevData);

        }
        // If we’re out of features to examine, pick majority label
        if(featuresLeft.isEmpty()){
            return getMajorityLabel(data);
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
        }

        int newDepth = maxDepth--;

        // parentNode.setRight(new DecisionTreeNode(sFeature));
        
        // parentNode.setLeft(new DecisionTreeNode(sFeature));


        // return train(originalData, data, maxDepth, parentNode.getLeft(), featuresLeft, prevData);
        // return train(originalData, data, maxDepth, parentNode.getLeft(), featuresLeft, prevData);

        parentNode.setRight(train(originalData, rightList, newDepth, new DecisionTreeNode(sFeature), nFeats, data));
        parentNode.setLeft(train(originalData, leftList, newDepth, new DecisionTreeNode(sFeature), nFeats, data));

        return parentNode;


        // return (parentNode.setLeft(train(originalData, leftList,  newDepth, parentNode, nFeats, data));
        // parentNode.setRight(train(originalData, rightList, newDepth, parentNode, nFeats, data));

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

                double error =  (1 - ((bin0_majority + bin1_majority)/ (double)data.size()));

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
	public static DecisionTreeNode train(DataSet originalData, ArrayList<Example> data , int maxDepth, DecisionTreeNode parentNode, Set<Integer> featuresLeft,ArrayList<Example> prevData ) {
        

        // If the we don’t have any data left, pick majority label of parent
        if(data.isEmpty()){
            return getMajorityLabel(prevData);

        }
        // If we’re out of features to examine, pick majority label
        if(featuresLeft.isEmpty()){
            return getMajorityLabel(data);
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
        }

        int newDepth = maxDepth--;

        // parentNode.setRight(new DecisionTreeNode(sFeature));
        
        // parentNode.setLeft(new DecisionTreeNode(sFeature));


        // return train(originalData, data, maxDepth, parentNode.getLeft(), featuresLeft, prevData);
        // return train(originalData, data, maxDepth, parentNode.getLeft(), featuresLeft, prevData);

        parentNode.setRight(train(originalData, rightList, newDepth, new DecisionTreeNode(sFeature), nFeats, data));
        parentNode.setLeft(train(originalData, leftList, newDepth, new DecisionTreeNode(sFeature), nFeats, data));

        return parentNode;


        // return (parentNode.setLeft(train(originalData, leftList,  newDepth, parentNode, nFeats, data));
        // parentNode.setRight(train(originalData, rightList, newDepth, parentNode, nFeats, data));

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

                double error =  (1 - ((bin0_majority + bin1_majority)/ (double)data.size()));

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


    



   

	// @Override
	// public double classify(Example example) {
    //     return 0;
	// }

    // @Override
    // public void train(DataSet data) {
    //     // TODO Auto-generated method stub
    // }
        
   


    
    
    public static void main(String[] args) {
        

        


     DataSet ourData = new DataSet("C:\\Users\\jvw42\\ML\\assign2-starter\\code\\ml\\data\\titanic-train.csv");

     ArrayList<Example> listData = ourData.getData();
     DecisionTreeNode root =  getRootNode(listData, ourData.getAllFeatureIndices());
     
     Set<Integer> nextIndices =   ourData.getAllFeatureIndices();
     nextIndices.remove(root.getFeatureIndex());
     
     Scanner myObj = new Scanner(System.in);
     System.out.println("Enter in a maxDepth");
     String maxDepthStr = myObj.nextLine();
     Integer maxDepth = Integer.valueOf(maxDepthStr);
     

      // print the object
      System.out.println(ourData.getAllFeatureIndices());
      train(ourData, listData, maxDepth, root, nextIndices, listData);
      myObj.close();
      

    }
}


