package ml.classifiers;

import ml.Example;

import java.util.ArrayList;
import java.util.Set;

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
  
      // print the object
      System.out.println(eList(ourData));
      System.out.println(ourData.getData().get(0).getLabel());
    }
}

