/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetov1;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utils.ArrayUtils;

/**
 *
 * @author Massas
 */
public class ProjetoV1Test {

    public ProjetoV1Test() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of guardarDados method, of class ProjetoV1.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGuardarDados() throws Exception {
        System.out.println("guardarDados");
        int[][] dadosFicheiro = null;
        String nomeFicheiro = "DAYTON.csv";
        int expResult = 22680;
        int result = ProjetoV1.guardarDados(nomeFicheiro);
        assertEquals(expResult, result);

    }

    @Test
    public void testGuardarDadosV2() throws Exception {
        System.out.println("guardarDados");
        int[][] dadosFicheiro = null;
        String nomeFicheiro = "";
        int expResult = 0;
        int result = (Integer) ProjetoV1.guardarDados(nomeFicheiro);
        assertEquals(expResult, result);

    }

    /**
     * Test of analisarDados method, of class ProjetoV1.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testAnalisarDados() throws Exception {
        System.out.println("analisarDados");
        int[][] dadosFicheiro = null;
        int linhas = 0;
        Integer[] expResult = null;
        String time = "";
        String tipoOrdenacao = "";
        Integer[] resultArray = null;
        
        int[] result = ProjetoV1.analisarDados(dadosFicheiro, linhas, time, tipoOrdenacao);
        if(result != null){
           resultArray= ArrayUtils.converterParaArrayInteger(result); 
        }
        
        assertArrayEquals(expResult, resultArray);

    }

    /**
     * Test of analisarDia method, of class ProjetoV1.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testAnalisarDia() throws Exception {
        System.out.println("analisarDia");
        int[][] dadosFicheiro = null;
        int linhas = 0;
        int[] expResult = null;
        int[] result = ProjetoV1.analisarDia(dadosFicheiro, linhas);
        assertArrayEquals(expResult, result);

    }

    /**
     * Test of analisarMes method, of class ProjetoV1.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testAnalisarMes() throws Exception {
        System.out.println("analisarMes");
        int[][] dadosFicheiro = null;
        int linhas = 0;
        int[] expResult = null;
        int[] result = ProjetoV1.analisarMes(dadosFicheiro, linhas);
        assertArrayEquals(expResult, result);

    }

    /**
     * Test of analisarAno method, of class ProjetoV1.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testAnalisarAno() throws Exception {
        System.out.println("analisarAno");
        int[][] dadosFicheiro = null;
        int linhas = 0;
        int[] expResult = null;
        int[] result = ProjetoV1.analisarAno(dadosFicheiro, linhas);
        assertArrayEquals(expResult, result);

    }

    /**
     * Test of analisarPeriodo method, of class ProjetoV1.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testAnalisarPeriodo() throws Exception {
        System.out.println("analisarPeriodo");
        int[][] dadosFicheiro = null;
        int lowerLimit = 0;
        int upperLimit = 0;
        int linhas = 0;
        int[] expResult = null;
        int[] result = ProjetoV1.analisarPeriodo(dadosFicheiro, lowerLimit, upperLimit, linhas);
        assertArrayEquals(expResult, result);

    }

    /**
     * Test of cont method, of class ProjetoV1.
     */
    @Test
    public void testCont() {
        System.out.println("cont");
        int[] serieTemp = new int[1];
        int expResult = 0;
        int result = ProjetoV1.cont(serieTemp);
        assertEquals(expResult, result);

    }

    /**
     * Test of mediaGlobal method, of class ProjetoV1.
     */
    @Test
    public void testMediaGlobal() {
        System.out.println("mediaGlobal");
        int[] serieTemp = new int[1];
        int qtdlinhas = 1;
        double expResult = 0.0;
        double result = ProjetoV1.mediaGlobal(serieTemp, qtdlinhas);
        assertEquals(expResult, result, 0.0);

    }

    /**
     * Test of mediaPesada method, of class ProjetoV1.
     */
    @Test
    public void testMediaPesada() {
        System.out.println("mediaPesada");
        int[] energia = null;
        int linhas = 0;
        double nAlpha = 0;
        Double[] expResult = null;
        Double[] resultArray = null;
        double[] result = ProjetoV1.mediaPesada(energia, linhas, nAlpha);
        if (result != null) {
            resultArray = ArrayUtils.converterParaArrayDouble(result);
        }

        assertArrayEquals(expResult, resultArray);

    }

    /**
     * Test of MediaMovelSimples method, of class ProjetoV1.
     */
    @Test
    public void testMediaMovelSimples() {
        System.out.println("MediaMovelSimples");
        int[] energia = null;
        int linhas = 0;
        double n = 0;
        Double[] expResult = null;
        Double[] resultArray = null;
        double[] result = ProjetoV1.MediaMovelSimples(energia, linhas, n);
        if (resultArray != null) {
            resultArray = ArrayUtils.converterParaArrayDouble(result);
        }
        assertArrayEquals(expResult, resultArray);

    }

}
