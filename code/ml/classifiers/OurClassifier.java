// Allison Sullivan Wu & Joe Williams Assignment 2 
package ml.classifiers;

import ml.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.Scanner;
import ml.DataSet;


 public class OurClassifier implements Classifier{

    
    public static int bin0_died = 0;
    public static int bin0_survived = 0;
    public static int bin1_died = 0;
    public static int bin1_survived = 0;
    public static int splitFeature;
    public static int treeDepth = 1000000000;


// Base Cases 

// If all data belong to the same class, pick that label
// If all the data have the same feature values, pick majority label
// If we’re out of features to examine, pick majority label
// If the we don’t have any data left, pick majority label of parent
// stop at maxDepth - -- from maxDepth every loop through and when 0 stop building tree

    /*
    Calculate for each example if the label is equal to the label of the first example 
    labelEquality checks to see if all the examples have the same class/label value. Takes an arraylist
    of examples from the data and returns True if all equal and false if not all equal. 
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
    value. Take an arraylist of examples and returns True if all equal and false if not all equal. 
    */
    public static boolean featureEquality(ArrayList<Example> data){

        for(int i = 0; i < data.size(); i++){
            if(!data.get(i).equalFeatures( data.get(0))){
                return false;
            }
        };
        return true;
    }

 
	/*
    Take int depth and set it to the depth limit for the tree
     */
    public static void setDepthLimit(int depth){
        treeDepth = depth;
    }
	
	
	/*
    Parameter: originalData, ArrayList of Examples (data), maxDepth, parentNode, 
    featuresLeft,ArrayList of Examples of prevData
    Returns: A DecisionTreeNode
     */
    
	public static DecisionTreeNode train(DataSet originalData, ArrayList<Example> data , int maxDepth, DecisionTreeNode parentNode, Set<Integer> featuresLeft,ArrayList<Example> prevData ) {


        System.out.println("Data Size:");
        System.out.println(data.size());
        System.out.println("Depth:");
        System.out.println(maxDepth);
        System.out.println("Feats Left:");
        System.out.println(featuresLeft);
        System.out.println("Prev Data");
        System.out.println(prevData.size());
        
        
        // If the we don’t have any data left, pick majority label of parent
        if(data.isEmpty()){
            System.out.println("data is empty pick majority label of parent");
            return getMajorityLabel(prevData);

        }
        // If we’re out of features to examine, pick majority label
        if(featuresLeft.isEmpty()){
             System.out.println("out of features to examine pick majority label");
            return getMajorityLabel(data);
        }
        // If all data belong to the same class, pick that label
        if(labelEquality(data)){
             System.out.println("all data belong to the same class, pick that label");
            return (new DecisionTreeNode(data.get(0).getLabel()));
        }
        // If all the data have the same feature values, pick majority label
        if(featureEquality(data)){
            System.out.println("all the data have the same feature values, pick majority label");
            return getMajorityLabel(data);
        } // If we have reached maxDepth
        if(maxDepth == 0){
            System.out.println("reached maxdepth of " + maxDepth);
            return getMajorityLabel(data);
        }


        //Not at a base case:
        int sFeature = getSplitFeature(data, featuresLeft);
        parentNode = new DecisionTreeNode(sFeature);
        System.out.println("sFeature " + sFeature);
       
        
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

        int newDepth = maxDepth - 1;

        
        featuresLeft.remove(sFeature);
        Set<Integer> nFeats = featuresLeft;
        



        System.out.println("Left List");
        System.out.println(leftList.size());
        System.out.println("Right List");
        System.out.println(rightList.size());

        System.out.println(" \n \n Next Iteration\n \n \n");
        

        parentNode.setRight(train(originalData, rightList, newDepth, new DecisionTreeNode(0), nFeats, data));
        parentNode.setLeft(train(originalData, leftList, newDepth, new DecisionTreeNode(0), nFeats, data));
    
        return parentNode;
        
    }

        /* getSplitFeature takes an arraylist of examples, which is our data, and a Set of Ingegers 
        which contain the index of the features that are left. This returns the minimum value in the errorlist
         */
        public static int getSplitFeature(ArrayList<Example> data , Set<Integer> featuresLeft){
            HashMap<Integer, Double> errorList = eList(data, featuresLeft);
            System.out.println("errorList:");
            System.out.println(errorList);
            for(int i = 0; i < errorList.keySet().size(); i++){
                
                if(errorList.get(i) == (Collections.min(errorList.values()))){
                    return i;
                }}
                return errorList.keySet().iterator().next();
        }

        /* 
        Takes an arraylist of examples from the data and a set of integers of the features left 
        and returns a new internal node with the minimum value in the error list
         */

        public static DecisionTreeNode getRootNode(ArrayList<Example> data , Set<Integer> featuresLeft){
            int splitFeat = getSplitFeature(data, featuresLeft);
            System.out.println("Split feature");
            System.out.println(splitFeat);
            return new DecisionTreeNode(splitFeat);
        }

        /* 
        eList Takes an arraylist of examples from the data and a set of integers of the features left
        and tallies up the label value for feature value
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

        /* 
        getMajorityLabel takes an arraylist of examples from the data and gets the majority label. Returns a 
        new node made from the majority label. 
         */
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

   
        /* 
        classify takes an arraylist of examples from the data and the final tree of type DecisionTreeNode. 
        Returns a double that represents the label prediction  
         */
	public static double classify(Example example, DecisionTreeNode finalTree) {

        if (finalTree.isLeaf()){
            return finalTree.prediction();
        }

        if(example.getFeature(finalTree.getFeatureIndex()) == 0){
            return classify(example, finalTree.getLeft());
        }
        else{
            return classify(example, finalTree.getRight());
        }


	}


    // @Override
    // public void train(DataSet data) {
    //     // TODO Auto-generated method stub
    // }
        
   public static ArrayList<Double> accuracy(DataSet data){

    

		ArrayList<Double> resultList = new ArrayList<Double>();
		Double sum = 0.0;

        ArrayList<Double> newResultList = new ArrayList<Double>();
		
        
            for(int j = 0; j< 19; j++){for(int k = 0; k <100; k++){
            double splitSize = .05*(j+1);
			DataSet[] splits = data.split(splitSize);
			int correctCount = 0;
            DecisionTreeNode dTree = train(data, splits[0].getData(), 10000, new DecisionTreeNode(0), splits[0].getAllFeatureIndices(),  splits[0].getData());

                for(int i = 0; i < splits[1].getData().size(); i++){

                    
                    if( splits[1].getData().get(i).getLabel() == classify(splits[1].getData().get(i), dTree)){
                        correctCount +=1;
                    }
                }
			resultList.add(correctCount / Double.valueOf(data.getData().size()));	
		}
		
		for (Double i : resultList) {
			 sum += i;
    	}
          newResultList.add(sum/ resultList.size());

          sum = 0.0;}
    

    return newResultList;

	}
@Override
public void train(DataSet data) {
    // TODO Auto-generated method stub
    
}
@Override
public double classify(Example example) {
    // TODO Auto-generated method stub
    return 0;
}

    
 }



