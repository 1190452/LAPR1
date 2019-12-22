/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Massas
 */
public class ArrayUtils {
    public static Double[] converterParaArrayDouble(double[] array){
        
        Double[] novoArray = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            novoArray[i] = array[i];
        }
        
        return novoArray;
    }
    
      public static Integer[] converterParaArrayInteger(int[] array){
        
        Integer[] novoArray = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            novoArray[i] = array[i];
        }
        
        return novoArray;
    }
}
