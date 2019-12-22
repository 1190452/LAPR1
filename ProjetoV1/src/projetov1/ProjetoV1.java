/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetov1;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.style.NamedPlotColor;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;
import com.panayotis.gnuplot.terminal.ImageTerminal;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class ProjetoV1 {

    static boolean haInteracao = true;
    static int[][] dadosFicheiro;

    public static void main(String[] args) {
        String resolucaoTemporal = "";
        String nomeDoFicheiro = "";
        String modeloStr = "";
        String tipoOrdenacao = "";
        double nAlpha = 0;
        int dia = 0;
        int mes = 0;
        int ano = 0;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-nome":
                    nomeDoFicheiro = args[i + 1];
                    System.out.println("nome do ficheiro: " + nomeDoFicheiro);
                    break;
                case "-resolucao":
                    haInteracao = false;
                    int periodoTemporal = Integer.parseInt(args[i + 1]);
                    resolucaoTemporal = getPeriodoTemporal(periodoTemporal);

                    break;

                case "-modelo":
                    haInteracao = false;

                    int modelo = Integer.parseInt(args[i + 1]);
                    switch (modelo) {
                        case 1:
                            modeloStr = "media movel simples";
                            break;
                        case 2:
                            modeloStr = "media movel exponencialmente pesada";
                            break;
                        default:
                            System.out.println("modelo inválido");
                            return;

                    }
                    break;

                case "-tipoOrdenacao":
                    haInteracao = false;
                    try {
                        int ordenacao = Integer.parseInt(args[i + 1]);
                        switch (ordenacao) {
                            case 1:
                                tipoOrdenacao = "crescente";
                                break;
                            case 2:
                                tipoOrdenacao = "decrescente";
                                break;
                            default:
                                System.out.println("tipo de ordenação inválida");
                                return;

                        }
                    } catch (Exception e) {
                        System.out.println("parametro tipo de ordenacao não inserido ou não válido");
                        return;
                    }

                    break;

                case "-parModelo":
                    haInteracao = false;
                    nAlpha = Integer.parseInt(args[i + 1]);

                    break;

                case "-momentoPrevisao":

                    haInteracao = false;
                    try {

                        dia = Integer.parseInt(args[i + 1]);
                        mes = Integer.parseInt(args[i + 2]);
                        ano = Integer.parseInt(args[i + 3]);

                    } catch (Exception e) {
                        System.out.println("parametros do momento previsao não inseridos ou não válidos");
                        return;
                    }

                    break;

            }
        }

        menu(nomeDoFicheiro, resolucaoTemporal, modeloStr, tipoOrdenacao, nAlpha, dia, mes, ano);

    }

    public static String getPeriodoTemporal(int periodoTemporal) {
        String resolucaoTemporal = "";
        switch (periodoTemporal) {
            case 11:
                resolucaoTemporal = "manha";
                break;
            case 12:
                resolucaoTemporal = "tarde";
                break;

            case 13:
                resolucaoTemporal = "noite";
                break;
            case 14:
                resolucaoTemporal = "madrugada";
                break;

            case 2:
                resolucaoTemporal = "diario";

                break;
            case 3:
                resolucaoTemporal = "mensal";
                break;

            case 4:
                resolucaoTemporal = "anual";
                break;
            default:
                System.out.println("Periodo temporal não válido");
                return "";

        }
        return resolucaoTemporal;
    }

    public static void menu(String nomeFicheiro, String resolucaoTemporal, String modelo, String tipoOrdenacao, double nAlpha, int dia, int mes, int ano) {

        int linhas = 0;
        int[] serieTemp = null;

        Scanner ler = new Scanner(System.in);
        System.out.println("|-----------|------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|");
        System.out.println("|  Função   |                              Descrição                                 |                                  Funcionalidades                                              |");
        System.out.println("|-----------|------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|");
        System.out.println("|     1     | Leitura do Ficheiro                                                    |    Leitura do ficheiro CSV e carregamento do mesmo para a aplicação                           |");
        System.out.println("|-----------|------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|");
        System.out.println("|           |                                                                        |    Escolha de uma resolução temporal                                                          |");
        System.out.println("|           |                                                                        |    Cálculo da média global da série                                                           |");
        System.out.println("|     2     | Análise de séries temporais utilizando diferentes resoluções           |    Definição dos intervalos para apresentação do histograma                                   |");
        System.out.println("|           |                                                                        |    Cálculo do número de observações                                                           |");
        System.out.println("|           |                                                                        |    Ordenação da série temporal                                                                |");
        System.out.println("|-----------|------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|");
        System.out.println("|     3     | Filtragem/Suavização de séries temporais                               |    Filtragem através da Média Móvel Simples                                                   |");
        System.out.println("|           |                                                                        |    Filtragem através da Média Móvel Exponencialmente Pesada                                   |");
        System.out.println("|-----------|------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|");
        System.out.println("|           |                                                                        |    Escolha de uma resolução temporal                                                          |");
        System.out.println("|     4     | Previsão de valores futuros a partir de uma série temporal             |    Cálculo do previsão através da Média Móvel Simples                                         |");
        System.out.println("|           |                                                                        |    Cálculo do previsão através da Média Móvel Exponencialmente Pesada                         |");
        System.out.println("|-----------|------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|");
        System.out.println("|     5     | Introdução de um novo ficheiro                                         |    Leitura de um novo ficheiro CSV                                                            |");
        System.out.println("|           |                                                                        |    Apagar da memória toda a informação antiga                                                 |");
        System.out.println("|-----------|------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|");
        System.out.println("|     0     | Sair                                                                   |    Fechar a aplicação                                                                         |");
        System.out.println("|-----------|------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|");
        System.out.println();
        System.out.println("Qual o exercício que pretende executar?");
        int N = ler.nextInt();
        while (N > 0) {
            switch (N) {
                case 1:
                    linhas = guardarDados(nomeFicheiro);
                    System.out.println("INFORMAÇÃO DO FICHEIRO ARMAZENADA EM MEMÓRIA!!");
                    break;
                case 2:
                    serieTemp = analisarDados(dadosFicheiro, linhas, resolucaoTemporal, tipoOrdenacao);

                    if (serieTemp != null && serieTemp.length > 0) {
                        int qtdlinhas = cont(serieTemp);
                        double mgs = mediaGlobal(serieTemp, qtdlinhas);
                        definirIntervalos(mgs, serieTemp);
                    } else {
                        System.out.println("Sem dados carregados para análise");

                    }

                    break;
                case 3:
                    if (dadosFicheiro != null) {
                        EscolhaFiltragem(dadosFicheiro, linhas, resolucaoTemporal, modelo, nAlpha);
                    } else {
                        System.out.println("Sem dados para efetuar análise");

                    }

                    break;
                case 4:
                    if (dadosFicheiro != null && dadosFicheiro.length > 0) {
                        Previsao(dadosFicheiro, linhas, resolucaoTemporal, modelo, nAlpha, dia, mes, ano);
                    } else {
                        System.out.println("Sem dados para efetuar análise");

                    }

                    break;
                case 5:
                    lerFicheiroCSV();
                default:
                    break;
            }
            System.out.println("");
            System.out.println("Que outra funcionalidade pretende utilizar?");
            N = ler.nextInt();
        }
    }

    //Redimensiona o array de acordo com o tamanho que vai ter   
    //https://stackoverflow.com/questions/13197702/resize-an-array-while-keeping-current-elements-in-java
    public static int[][] redimensionarMatriz(int[][] dadosFicheiro) {

        if (dadosFicheiro == null) {
            return null;
        }

        //duplica o tamanho da matriz em linhas
        int novoTamanho = dadosFicheiro.length * 2;
        int[][] dadosFicheiroNovo = new int[novoTamanho][5];

        System.arraycopy(dadosFicheiro, 0, dadosFicheiroNovo, 0, dadosFicheiro.length);

        return dadosFicheiroNovo;
    }

    public static void imprimirResultados(int[] array) {
        int x = 0;

        while (x < array.length && array[x] != 0) {
            System.out.println(array[x]);
            x++;
        }
    }

    //le os dados do ficheiro
    public static int guardarDados(String ficheiro) {

        int qtdlinhas = 0;

        if (dadosFicheiro == null) {
            dadosFicheiro = new int[50][5];
        }

        Scanner lerFicheiro;
        try {
            lerFicheiro = new Scanner(new File(ficheiro));

            String header = lerFicheiro.nextLine();

            while (lerFicheiro.hasNextLine()) {

                if (dadosFicheiro.length <= qtdlinhas) {
                    dadosFicheiro = redimensionarMatriz(dadosFicheiro);
                }

                String line = lerFicheiro.nextLine();
                String[] espacos = line.split("[-| |:|,|]");
                dadosFicheiro[qtdlinhas][0] = Integer.parseInt(espacos[0]);
                dadosFicheiro[qtdlinhas][1] = Integer.parseInt(espacos[1]);
                dadosFicheiro[qtdlinhas][2] = Integer.parseInt(espacos[2]);
                dadosFicheiro[qtdlinhas][3] = Integer.parseInt(espacos[3]);
                dadosFicheiro[qtdlinhas][4] = Integer.parseInt(espacos[6]);
                qtdlinhas++;

            }
            lerFicheiro.close();
            return qtdlinhas;
        } catch (FileNotFoundException ex) {
            System.out.println("Ficheiro não encontrado ou não válido");
            return 0;
        }

    }

    //analisar dados
    public static int[] analisarDados(int[][] dadosFicheiro, int linhas, String time, String tipoOrdenacao) {

        if (dadosFicheiro == null) {
            return null;
        }

        System.out.println("ANÁLISE");
        int lowerLimit, upperLimit;

        if (haInteracao) {
            Scanner ler = new Scanner(System.in);
            System.out.println("Introduza a resolução temporal(diario, mensal, anual, madrugada, manha, tarde, ): ");
            time = ler.nextLine();

        }

        switch (time.toLowerCase()) {
            case "diario":
                int[] energiasD;
                energiasD = analisarDia(dadosFicheiro, linhas);
                ordenarValores(energiasD, linhas, tipoOrdenacao);

                try {
                    guardarCSV(energiasD);
                } catch (Exception e) {
                    System.out.println("Não foi possivel guardar o ficheiro CSV");
                }
                return energiasD;
            case "mensal":
                int[] energiasM;
                energiasM = analisarMes(dadosFicheiro, linhas);

                if (energiasM != null && energiasM.length > 0) {
                    ordenarValores(energiasM, linhas, tipoOrdenacao);

                    guardarCSV(energiasM);
                }

                return energiasM;
            case "anual":
                int[] energiasA;
                energiasA = analisarAno(dadosFicheiro, linhas);

                if (energiasA != null && energiasA.length > 0) {
                    ordenarValores(energiasA, linhas, tipoOrdenacao);

                    guardarCSV(energiasA);

                }

                return energiasA;

            case "madrugada":
                int[] energiasMadrugada;
                lowerLimit = 00;
                upperLimit = 05;

                energiasMadrugada = analisarPeriodo(dadosFicheiro, lowerLimit, upperLimit, linhas);

                if (energiasMadrugada != null && energiasMadrugada.length > 0) {
                    ordenarValores(energiasMadrugada, linhas, tipoOrdenacao);
                    guardarCSV(energiasMadrugada);
                }

                return energiasMadrugada;
            case "manha":
                int[] energiasManha;
                lowerLimit = 06;
                upperLimit = 11;

                energiasManha = analisarPeriodo(dadosFicheiro, lowerLimit, upperLimit, linhas);

                if (energiasManha != null && energiasManha.length > 0) {
                    ordenarValores(energiasManha, linhas, tipoOrdenacao);

                    guardarCSV(energiasManha);
                }

                return energiasManha;
            case "tarde":
                int[] energiasTarde;
                lowerLimit = 12;
                upperLimit = 17;

                energiasTarde = analisarPeriodo(dadosFicheiro, lowerLimit, upperLimit, linhas);
                if (energiasTarde != null && energiasTarde.length > 0) {
                    ordenarValores(energiasTarde, linhas, tipoOrdenacao);

                    guardarCSV(energiasTarde);
                }

                return energiasTarde;
            case "noite":
                int[] energiasNoite;
                lowerLimit = 18;
                upperLimit = 23;

                energiasNoite = analisarPeriodo(dadosFicheiro, lowerLimit, upperLimit, linhas);
                if (energiasNoite != null && energiasNoite.length > 0) {
                    ordenarValores(energiasNoite, linhas, tipoOrdenacao);

                    guardarCSV(energiasNoite);

                }

                return energiasNoite;

            case "default":
                break;
        }
        return null;
    }

    public static int[] analisarDia(int[][] dadosFicheiro, int linhas) {
        if (dadosFicheiro == null) {
            return null;
        }

        int p = 0;
        int soma = dadosFicheiro[0][4];
        int[] energiasD = new int[linhas];
        for (int posicao = 0; posicao < linhas; posicao++) {
            if (dadosFicheiro[posicao][2] == dadosFicheiro[posicao + 1][2] && dadosFicheiro[posicao + 1][4] != 0) {
                soma = soma + dadosFicheiro[posicao + 1][4];
            } else {
                energiasD[p] = soma;
                soma = dadosFicheiro[posicao + 1][4];
                p++;
            }
        }

        imprimirResultados(energiasD);

        visualizarGraficoAnalise(energiasD);

        return energiasD;
    }

    public static int[] analisarMes(int[][] dadosFicheiro, int linhas) {

        if (dadosFicheiro == null) {
            return null;
        }

        int p = 0;
        int soma = dadosFicheiro[0][4];
        int[] energiasM = new int[linhas];
        for (int posicao = 0; posicao < linhas; posicao++) {
            if (dadosFicheiro[posicao][1] == dadosFicheiro[posicao + 1][1] && dadosFicheiro[posicao + 1][1] != 0) {
                soma = soma + dadosFicheiro[posicao + 1][4];
            } else {
                energiasM[p] = soma;
                soma = dadosFicheiro[posicao + 1][4];
                p++;
            }
        }

        imprimirResultados(energiasM);

        visualizarGraficoAnalise(energiasM);
        return energiasM;
    }

    public static int[] analisarAno(int[][] dadosFicheiro, int linhas) {
        if (dadosFicheiro == null) {
            return null;
        }

        int p = 0;
        int soma = dadosFicheiro[0][4];
        int[] energiasA = new int[linhas];
        for (int posicao = 0; posicao < linhas; posicao++) {
            if (dadosFicheiro[posicao][0] == dadosFicheiro[posicao + 1][0]) {
                soma = soma + dadosFicheiro[posicao + 1][4];
            } else {
                energiasA[p] = soma;
                soma = dadosFicheiro[posicao + 1][4];
                p++;
            }
        }

        imprimirResultados(energiasA);
        visualizarGraficoAnalise(energiasA);
        return energiasA;
    }

    public static int[] analisarPeriodo(int[][] dadosFicheiro, int lowerLimit, int upperLimit, int linhas) {

        if (dadosFicheiro == null) {
            return null;
        }

        int p = 0;
        int soma = 0;
        int[] energiasP = new int[linhas];
        for (int posicao = 0; posicao < linhas; posicao++) {
            if (dadosFicheiro[posicao][3] >= lowerLimit && dadosFicheiro[posicao][3] <= upperLimit && dadosFicheiro[posicao][2] == dadosFicheiro[posicao + 1][2] && dadosFicheiro[posicao + 1][4] != 0) {
                soma = soma + dadosFicheiro[posicao][4];
            } else {
                if (soma != 0) {
                    energiasP[p] = soma;
                    p++;
                    soma = 0;
                }
            }
        }

        imprimirResultados(energiasP);
        visualizarGraficoAnalise(energiasP);
        return energiasP;
    }

    public static void visualizarGraficoAnalise(int[] array) {

        Scanner ler = new Scanner(System.in);
        String escolha;
        JavaPlot p = new JavaPlot();
        PlotStyle myPlotStyle = new PlotStyle();
        myPlotStyle.setStyle(Style.DOTS);
        myPlotStyle.setLineWidth(3);
        myPlotStyle.setLineType(NamedPlotColor.BLUE);
        myPlotStyle.setLineType(2);
        double tab[][];
        tab = new double[22700][1];
        passarDados(array, tab);
        DataSetPlot s = new DataSetPlot(tab);
        s.setTitle("Análise Temporal");

        s.setPlotStyle(myPlotStyle);
        p.addPlot(s);
        p.newGraph();
        p.plot();
        if (haInteracao) {
            System.out.println("Pretende guardar o gráfico em ficheiro PNG?");
            escolha = ler.nextLine();
            if (escolha.equalsIgnoreCase("sim")) {
                guardarPng(p);
            }
        } else {
            guardarPng(p);
        }

    }

    public static int cont(int[] serieTemp) {
        int cont = 0;
        for (int i = 0; i < serieTemp.length; i++) {
            if (serieTemp[i] > 0) {
                cont++;
            }
        }
        return cont;
    }

    public static double mediaGlobal(int[] serieTemp, int qtdlinhas) {

        if (serieTemp == null) {
            return 0;
        }

        int somaFinal = 0;
        double media;
        for (int i = 0; i < serieTemp.length; i++) {
            somaFinal = serieTemp[i] + somaFinal;
        }
        media = somaFinal / qtdlinhas;
        return media;
    }

    public static void definirIntervalos(double mgs, int[] serieTemp) {
        double mediaInferior = mgs - (0.2 * mgs);
        double mediaSuperior = mgs + (0.2 * mgs);
        numeroObservacoes(serieTemp, mediaInferior, mediaSuperior);
    }

    public static void numeroObservacoes(int[] serieTemp, double mediaInferior, double mediaSuperior) {
        int contadorInf = 0, contadorSup = 0, contadorMeio = 0;
        for (int posicao = 0; posicao < serieTemp.length; posicao++) {
            if (serieTemp[posicao] > 0) {
                if (serieTemp[posicao] <= mediaInferior) {
                    contadorInf++;
                } else if (serieTemp[posicao] > mediaInferior && serieTemp[posicao] < mediaSuperior) {
                    contadorMeio++;
                } else {
                    contadorSup++;
                }
            }
        }
        visualizarGraficoIntervalos(contadorInf, contadorMeio, contadorSup, mediaInferior, mediaSuperior);
    }

    public static void visualizarGraficoIntervalos(int contadorInf, int contadorMeio, int contadorSup, double mediaInferior, double mediaSuperior) {
        Scanner ler = new Scanner(System.in);
        String escolha;

        JavaPlot p = new JavaPlot();
        PlotStyle myPlotStyle = new PlotStyle();
        myPlotStyle.setStyle(Style.HISTOGRAMS);
        myPlotStyle.setLineWidth(5);
        myPlotStyle.setLineType(NamedPlotColor.BLACK);
        myPlotStyle.setLineType(2);

        double tab[][];
        tab = new double[3][1];
        tab[0][0] = contadorInf;
        tab[1][0] = contadorMeio;
        tab[2][0] = contadorSup;

        DataSetPlot s = new DataSetPlot(tab);
        s.setTitle("Histograma exemplo");
        s.setPlotStyle(myPlotStyle);
        p.addPlot(s);
        p.newGraph();
        p.plot();

        if (haInteracao) {
            System.out.println("Pretende guardar o gráfico em ficheiro PNG?");
            escolha = ler.nextLine();
            if (escolha.equalsIgnoreCase("sim")) {
                guardarPng(p);
            }
        } else {
            guardarPng(p);
        }
    }

    public static void ordenarValores(int[] energia, int linhas, String tipoOrdenacao) {
        Scanner utilizador = new Scanner(System.in);
        if (haInteracao) {
            System.out.println("Pretende ordenar os valores de forma crescente ou decrescente?");
            tipoOrdenacao = utilizador.nextLine();
        }
        if (tipoOrdenacao.equalsIgnoreCase("crescente") || tipoOrdenacao.equalsIgnoreCase("decrescente")) {
            if (tipoOrdenacao.equalsIgnoreCase("crescente")) {
                int start = 0, end = linhas - 1;
                mergeSortCrescente(energia, start, end);

            } else if (tipoOrdenacao.equalsIgnoreCase("decrescente")) {
                int start = 0, end = linhas - 1;
                mergeSortDecrescente(energia, start, end);
            }
            for (int z = 0; z < linhas; z++) {
                if (energia[z] != 0) {
                    System.out.println(energia[z]);
                }
            }
        }
    }

    public static void merge(int[] energias, int start, int mid, int end) {
        int temp[] = new int[end - start + 1];
        int i = start, j = mid + 1, k = 0;
        while (i <= mid && j <= end) {
            if (energias[i] <= energias[j]) {
                temp[k] = energias[i];
                k += 1;
                i += 1;
            } else {
                temp[k] = energias[j];
                k += 1;
                j += 1;
            }
        }
        while (i <= mid) {
            temp[k] = energias[i];
            k += 1;
            i += 1;
        }
        while (j <= end) {
            temp[k] = energias[j];
            k += 1;
            j += 1;
        }
        for (i = start; i <= end; i += 1) {
            energias[i] = temp[i - start];
        }

    }

    public static void mergeSortCrescente(int[] energias, int start, int end) {

        if (start < end) {
            int mid = (start + end) / 2;
            mergeSortCrescente(energias, start, mid);
            mergeSortCrescente(energias, mid + 1, end);
            merge(energias, start, mid, end);
        }

    }

    public static void mergeDecrescente(int[] energias, int start, int mid, int end) {
        int temp[] = new int[end - start + 1];
        int i = start, j = mid + 1, k = 0;
        while (i <= mid && j <= end) {
            if (energias[i] >= energias[j]) {
                temp[k] = energias[i];
                k += 1;
                i += 1;
            } else {
                temp[k] = energias[j];
                k += 1;
                j += 1;
            }
        }
        while (i <= mid) {
            temp[k] = energias[i];
            k += 1;
            i += 1;
        }
        while (j <= end) {
            temp[k] = energias[j];
            k += 1;
            j += 1;
        }
        for (i = start; i <= end; i += 1) {
            energias[i] = temp[i - start];
        }

    }

    public static void mergeSortDecrescente(int[] energias, int start, int end) {

        if (start < end) {
            int mid = (start + end) / 2;
            mergeSortDecrescente(energias, start, mid);
            mergeSortDecrescente(energias, mid + 1, end);
            mergeDecrescente(energias, start, mid, end);
        }
    }

    public static void EscolhaFiltragem(int[][] dadosFicheiro, int linhas, String time, String modelo, double nAlpha) { //Adicionei nAlpha!
        int lowerLimit, upperLimit;

        if (haInteracao) {
            System.out.println("Suavização/Filtragem da série orginal");
            Scanner ler = new Scanner(System.in);
            System.out.println("Introduza a resolução temporal(diario, mensal, anual, periodo do dia): ");
            time = ler.nextLine();
        }

        switch (time.toLowerCase()) {
            case "diario":
                int[] energiaD2 = analisarDia(dadosFicheiro, linhas);
                verGraficoSerieOriginal(energiaD2, linhas);
                EscolhaMedia(energiaD2, linhas, modelo, nAlpha);
                verGraficoSerieFiltrada(energiaD2);

                guardarCSV(energiaD2);

                break;
            case "mensal":
                int[] energiaM2 = analisarMes(dadosFicheiro, linhas);
                verGraficoSerieOriginal(energiaM2, linhas);
                EscolhaMedia(energiaM2, linhas, modelo, nAlpha);
                verGraficoSerieFiltrada(energiaM2);

                guardarCSV(energiaM2);

                break;
            case "anual":
                int[] energiaA2 = analisarAno(dadosFicheiro, linhas);
                verGraficoSerieOriginal(energiaA2, linhas);
                EscolhaMedia(energiaA2, linhas, modelo, nAlpha);
                verGraficoSerieFiltrada(energiaA2);

                guardarCSV(energiaA2);

                break;
            case "madrugada":
                lowerLimit = 00;
                upperLimit = 05;
                int[] energiasMadrugada2 = analisarPeriodo(dadosFicheiro, lowerLimit, upperLimit, linhas);
                verGraficoSerieOriginal(energiasMadrugada2, linhas);
                EscolhaMedia(energiasMadrugada2, linhas, modelo, nAlpha);
                verGraficoSerieFiltrada(energiasMadrugada2);

                guardarCSV(energiasMadrugada2);

                break;
            case "manha":
                lowerLimit = 06;
                upperLimit = 11;
                int[] energiasManha2 = analisarPeriodo(dadosFicheiro, lowerLimit, upperLimit, linhas);
                verGraficoSerieOriginal(energiasManha2, linhas);
                EscolhaMedia(energiasManha2, linhas, modelo, nAlpha);
                verGraficoSerieFiltrada(energiasManha2);

                guardarCSV(energiasManha2);

                break;
            case "tarde":
                lowerLimit = 12;
                upperLimit = 17;
                int[] energiasTarde2 = analisarPeriodo(dadosFicheiro, lowerLimit, upperLimit, linhas);

                verGraficoSerieOriginal(energiasTarde2, linhas);

                EscolhaMedia(energiasTarde2, linhas, modelo, nAlpha);

                verGraficoSerieFiltrada(energiasTarde2);

                guardarCSV(energiasTarde2);

                break;
            case "noite":
                lowerLimit = 18;
                upperLimit = 23;
                int[] energiasNoite2 = analisarPeriodo(dadosFicheiro, lowerLimit, upperLimit, linhas);

                verGraficoSerieOriginal(energiasNoite2, linhas);

                EscolhaMedia(energiasNoite2, linhas, modelo, nAlpha);

                verGraficoSerieFiltrada(energiasNoite2);

                guardarCSV(energiasNoite2);

                break;

        }
    }

    public static void verGraficoSerieOriginal(int[] aux, int linhas) {
        Scanner ler = new Scanner(System.in);
        String escolha;

        JavaPlot p = new JavaPlot();
        PlotStyle myPlotStyle = new PlotStyle();
        myPlotStyle.setStyle(Style.DOTS);
        myPlotStyle.setLineWidth(3);
        myPlotStyle.setLineType(NamedPlotColor.BLUE);
        myPlotStyle.setLineType(2);
        double tab[][];
        tab = new double[23000][1];
        passarDados(aux, tab);
        DataSetPlot s = new DataSetPlot(tab);
        s.setTitle("Gráfico Série Original");
        s.setPlotStyle(myPlotStyle);
        p.addPlot(s);
        p.newGraph();
        p.plot();

        if (haInteracao) {
            System.out.println("Pretende guardar o gráfico em ficheiro PNG?");
            escolha = ler.nextLine();
            if (escolha.equalsIgnoreCase("sim")) {
                guardarPng(p);
            }
        } else {
            guardarPng(p);
        }
    }

    public static void EscolhaMedia(int[] energia, int linhas, String modelo, double nAlpha) {
        if (haInteracao) {

            Scanner ler = new Scanner(System.in);
            System.out.println("Introduza o tipo de média a utilizar (Média Móvel Simples |OU| Média Móvel Exponencialmente Pesada): ");
            modelo = ler.nextLine();
        }
        double[] mms;
        double[] mmep;
        switch (modelo.toLowerCase()) {
            case "media movel simples":
                mms = MediaMovelSimples(energia, linhas, nAlpha);
                ErroMediaAbsoluto(energia, linhas, mms);
                break;
            case "media movel exponencialmente pesada":
                mmep = mediaPesada(energia, linhas, nAlpha);
                ErroMediaAbsoluto(energia, linhas, mmep);
                break;
            default:
                break;
        }

    }

    public static double[] mediaPesada(int[] energia, int linhas, double nAlpha) {

        if (energia == null) {
            return null;
        }

        Scanner ordem = new Scanner(System.in);
        String escolha;
        double mmep[] = new double[linhas];
        mmep[0] = energia[0];
        int x = 0;

        if (haInteracao) {

            System.out.println("Introduza o valor de α (]0,1])");

            do {
                nAlpha = ordem.nextDouble();
            } while (nAlpha <= 0 || nAlpha > 1);
        } else if (nAlpha <= 0 || nAlpha > 1) {
            System.out.println("Valor de nAlpha Inválido.Valide o valor introduzido");
            return mmep;
        }

        for (int i = 0; i < linhas; i++) {
            if ((x - 1) >= 0) {
                mmep[x] = nAlpha * energia[i] + (1 - nAlpha) * mmep[x - 1];
                if (mmep[x] > 0) {
                    System.out.println(mmep[x]);
                }
            }
            x++;
        }

        System.out.println("Pretende gravar a série temporal em ficheiro .csv?");
        escolha = ordem.nextLine();

        guardarCSV(mmep);

        return mmep;
    }

    public static double[] MediaMovelSimples(int[] energia, int linhas, double n) {
        if (energia == null) {
            return null;
        }

        if (haInteracao) {
            Scanner ordem = new Scanner(System.in);
            System.out.println("Introduza o valor de n");
            n = ordem.nextDouble();
        }

        double[] mms = new double[linhas];
        double soma = 0;
        for (int i = 0; i < energia.length; i++) {
            for (int k = 0; k < n && k <= i; k++) {
                soma += (energia[i - k]);
            }
            mms[i] = soma / n;
            soma = 0;
            if (mms[i] > 0) {
                System.out.printf("%.1f\n", mms[i]);
            }
        }

        guardarCSV(mms);

        return mms;
    }

    public static void ErroMediaAbsoluto(int[] serieTemp, int linhas, double[] media) {
        double erro = 0, error;
        System.out.println("Erro Médio Absoluto");
        for (int x = 0; x < linhas - 1; x++) {
            erro = erro + Math.abs(media[x] - serieTemp[x]);
        }
        error = erro / linhas;
        System.out.println(error);
    }

    public static void verGraficoSerieFiltrada(int[] array2) {
        Scanner ler = new Scanner(System.in);
        String escolha;

        JavaPlot p = new JavaPlot();
        PlotStyle myPlotStyle = new PlotStyle();
        myPlotStyle.setStyle(Style.DOTS);
        myPlotStyle.setLineWidth(5);
        myPlotStyle.setLineType(NamedPlotColor.BLUE);
        myPlotStyle.setLineType(2);
        double tab[][];
        tab = new double[23000][1];
        passarDados(array2, tab);
        DataSetPlot s = new DataSetPlot(tab);
        s.setTitle("Gráfico Série Filtrada");
        s.setPlotStyle(myPlotStyle);
        p.addPlot(s);
        p.newGraph();
        p.plot();

        if (haInteracao) {
            System.out.println("Pretende guardar o gráfico em ficheiro PNG?");
            escolha = ler.nextLine();
            if (escolha.equalsIgnoreCase("sim")) {
                guardarPng(p);
            }
        } else {
            guardarPng(p);
        }
    }

    public static void passarDados(int[] array2, double[][] tab) {
        for (int i = 0; i < array2.length; i++) {
            tab[i][0] = array2[i];
        }
    }

    public static void Previsao(int[][] dadosFicheiro, int linhas, String periodo, String modelo, double nAlpha, int dia, int mes, int ano) {
        Scanner ler = new Scanner(System.in);
        int lowerLimit, upperLimit;
        System.out.println("PREVISÃO");

        if (haInteracao) {

            System.out.println("Que modelo pretende utilizar? Média Móvel Simples (ou) Média Móvel Exponencialmente Pesada");
            modelo = ler.nextLine();
        }

        switch (modelo.toLowerCase()) {
            case "media movel simples":
                if (haInteracao) {
                    System.out.println("Introduza o espaço temporal que pretende analisar (madrugada, manha,tarde noite, dia ou mes)");
                    periodo = ler.nextLine();
                }
                switch (periodo.toLowerCase()) {

                    case "madrugada":
                        lowerLimit = 00;
                        upperLimit = 05;
                        if (haInteracao) {
                            System.out.println("Introduza o dia que pretende analisar");
                            dia = ler.nextInt();
                            System.out.println("Introduza o mes que pretende analisar");
                            mes = ler.nextInt();
                            System.out.println("Introduza o ano");
                            ano = ler.nextInt();
                        }
                        previsaoMMS(dia, mes, ano, upperLimit, lowerLimit, dadosFicheiro, linhas, nAlpha);
                        break;

                    case "manha":
                        lowerLimit = 06;
                        upperLimit = 11;
                        if (haInteracao) {
                            System.out.println("Introduza o dia que pretende analisar");
                            dia = ler.nextInt();
                            System.out.println("Introduza o mes que pretende analisar");
                            mes = ler.nextInt();
                            System.out.println("Introduza o ano");
                            ano = ler.nextInt();
                        }
                        previsaoMMS(dia, mes, ano, upperLimit, lowerLimit, dadosFicheiro, linhas, nAlpha);
                        break;

                    case "tarde":
                        lowerLimit = 12;
                        upperLimit = 17;
                        if (haInteracao) {
                            System.out.println("Introduza o dia que pretende analisar");
                            dia = ler.nextInt();
                            System.out.println("Introduza o mes que pretende analisar");
                            mes = ler.nextInt();
                            System.out.println("Introduza o ano");
                            ano = ler.nextInt();
                        }
                        previsaoMMS(dia, mes, ano, upperLimit, lowerLimit, dadosFicheiro, linhas, nAlpha);
                        break;

                    case "noite":
                        lowerLimit = 18;
                        upperLimit = 23;
                        if (haInteracao) {
                            System.out.println("Introduza o dia que pretende analisar");
                            dia = ler.nextInt();
                            System.out.println("Introduza o mes que pretende analisar");
                            mes = ler.nextInt();
                            System.out.println("Introduza o ano");
                            ano = ler.nextInt();
                        }
                        previsaoMMS(dia, mes, ano, upperLimit, lowerLimit, dadosFicheiro, linhas, nAlpha);
                        break;
                    case "dia":
                        if (haInteracao) {
                            System.out.println("Introduza o dia que pretende analisar");
                            dia = ler.nextInt();
                            System.out.println("Introduza o mes");
                            mes = ler.nextInt();
                            System.out.println("Introduza o ano");
                            ano = ler.nextInt();
                        }
                        previsaoMMSDIA(dia, mes, ano, dadosFicheiro, linhas, nAlpha);
                        break;
                    case "mes":
                        if (haInteracao) {
                            System.out.println("Introduza o mes que pretende analisar");
                            mes = ler.nextInt();
                            System.out.println("Introduza o ano");
                            ano = ler.nextInt();
                        }
                        previsaoMMSMES(mes, ano, dadosFicheiro, linhas, nAlpha);
                        break;
                    case "ano":
                        if (haInteracao) {
                            System.out.println("Introduza o ano que pretende analisar");
                            ano = ler.nextInt();
                        }
                        previsaoMMSANO(ano, dadosFicheiro, linhas, nAlpha);
                    default:
                        break;

                }
                break;
            case "media movel exponencialmente pesada":
                if (haInteracao) {
                    System.out.println("Introduza o espaço temporal que pretende analisar (madrugada, manha,tarde noite, dia ou mes)");
                    periodo = ler.nextLine();
                }
                switch (periodo.toLowerCase()) {
                    case "madrugada":
                        lowerLimit = 00;
                        upperLimit = 05;
                        if (haInteracao) {
                            System.out.println("Introduza o dia que pretende analisar");
                            dia = ler.nextInt();
                            System.out.println("Introduza o mes que pretende analisar");
                            mes = ler.nextInt();
                            System.out.println("Introduza o ano");
                            ano = ler.nextInt();
                        }
                        previsaoMMEP(dia, mes, ano, upperLimit, lowerLimit, dadosFicheiro, linhas, nAlpha);
                        break;
                    case "manha":
                        lowerLimit = 06;
                        upperLimit = 11;
                        if (haInteracao) {
                            System.out.println("Introduza o dia que pretende analisar");
                            dia = ler.nextInt();
                            System.out.println("Introduza o mes que pretende analisar");
                            mes = ler.nextInt();
                            System.out.println("Introduza o ano");
                            ano = ler.nextInt();
                        }
                        previsaoMMEP(dia, mes, ano, upperLimit, lowerLimit, dadosFicheiro, linhas, nAlpha);
                        break;
                    case "tarde":
                        lowerLimit = 12;
                        upperLimit = 17;
                        if (haInteracao) {
                            System.out.println("Introduza o dia que pretende analisar");
                            dia = ler.nextInt();
                            System.out.println("Introduza o mes que pretende analisar");
                            mes = ler.nextInt();
                            System.out.println("Introduza o ano");
                            ano = ler.nextInt();
                        }
                        previsaoMMEP(dia, mes, ano, upperLimit, lowerLimit, dadosFicheiro, linhas, nAlpha);
                        break;
                    case "noite":
                        lowerLimit = 18;
                        upperLimit = 23;
                        if (haInteracao) {
                            System.out.println("Introduza o dia que pretende analisar");
                            dia = ler.nextInt();
                            System.out.println("Introduza o mes que pretende analisar");
                            mes = ler.nextInt();
                            System.out.println("Introduza o ano");
                            ano = ler.nextInt();
                        }
                        previsaoMMEP(dia, mes, ano, upperLimit, lowerLimit, dadosFicheiro, linhas, nAlpha);
                        break;

                    case "dia":
                        if (haInteracao) {
                            System.out.println("Introduza o dia que pretende analisar");
                            dia = ler.nextInt();
                            System.out.println("Introduza o mes");
                            mes = ler.nextInt();
                            System.out.println("Introduza o ano");
                            ano = ler.nextInt();
                        }
                        previsaoMMEPDIA(dia, mes, ano, dadosFicheiro, linhas, nAlpha);
                        break;
                    case "mes":
                        if (haInteracao) {
                            System.out.println("Introduza o mes que pretende analisar");
                            mes = ler.nextInt();
                            System.out.println("Introduza o ano");
                            ano = ler.nextInt();
                        }
                        previsaoMMEPMES(mes, ano, dadosFicheiro, linhas, nAlpha);
                        break;
                    case "ano":
                        if (haInteracao) {
                            System.out.println("Introduza o mes que pretende analisar");
                            ano = ler.nextInt();
                        }
                        previsaoMMEPANO(ano, dadosFicheiro, linhas, nAlpha);
                    default:
                        break;

                }
            default:
                break;
        }
    }

    public static void previsaoMMS(int dia, int mes, int ano, int upperLimit, int lowerLimit, int[][] dadosFicheiro, int linhas, double n) {
        Scanner ler = new Scanner(System.in);
        double prev = 0.0, resultado;
        if (haInteracao) {
            System.out.println("Introduza a ordem da Média Móvel");
            n = ler.nextInt();
        }
        for (int i = 0; i < linhas; i++) {
            for (int k = 0; k < n && k <= i; k++) {
                if (dadosFicheiro[i][0] == ano && dadosFicheiro[i][1] == mes && dadosFicheiro[i][2] == dia - 1 && dadosFicheiro[i][3] >= lowerLimit && dadosFicheiro[i][3] <= upperLimit) {
                    prev = (dadosFicheiro[i - k][4]) + prev;
                }
            }
        }
        if (prev == 0) {
            System.out.println("Não existe momento anterior ao introduzido pelo utilizador!");
        } else {
            resultado = prev / n;
            System.out.println(resultado);
        }
    }

    public static void previsaoMMSDIA(int dia, int mes, int ano, int[][] dadosFicheiro, int linhas, double n) {

        if (dadosFicheiro == null) {
            return;
        }

        Scanner ler = new Scanner(System.in);
        double prev = 0.0, resultado;
        if (haInteracao) {
            System.out.println("Introduza a ordem da Média Móvel");
            n = ler.nextInt();
        }
        for (int i = 0; i < linhas; i++) {
            for (int k = 0; k < n && k <= i; k++) {
                if (dadosFicheiro[i][0] == ano && dadosFicheiro[i][1] == mes && dadosFicheiro[i][2] == dia - 1) {
                    prev = (double) (dadosFicheiro[i - k][4]) + prev;
                }
            }
        }
        if (prev == 0) {
            System.out.println("Não existe momento anterior ao introduzido pelo utilizador!");
        } else {
            resultado = prev / n;
            System.out.println(resultado);
        }
    }

    public static void previsaoMMSMES(int mes, int ano, int[][] dadosFicheiro, int linhas, double n) {
        Scanner ler = new Scanner(System.in);
        if (haInteracao) {
            System.out.println("Introduza a ordem da média móvel");
            n = ler.nextInt();
        }
        double prev = 0, resultado;
        for (int i = 0; i < linhas; i++) {
            for (int k = 0; k < n && k <= i; k++) {
                if (dadosFicheiro[i][0] == ano && dadosFicheiro[i][1] == mes - 1) {
                    prev = (dadosFicheiro[i - k][4]) + prev;
                }
            }
        }
        if (prev == 0) {
            System.out.println("Não existe momento anterior ao introduzido pelo utilizador!");
        } else {
            resultado = prev / n;
            System.out.println(resultado);
        }
    }

    public static void previsaoMMSANO(int ano, int[][] dadosFicheiro, int linhas, double n) {
        Scanner ler = new Scanner(System.in);
        if (haInteracao) {
            System.out.println("Introduza a ordem da média móvel");
            n = ler.nextInt();
        }
        double prev = 0, resultado;
        for (int i = 0; i < linhas; i++) {
            for (int k = 0; k < n && k <= i; k++) {
                if (dadosFicheiro[i][0] == ano - 1) {
                    prev = (dadosFicheiro[i - k][4]) + prev;
                }
            }
        }
        if (prev == 0) {
            System.out.println("Não existe momento anterior ao introduzido pelo utilizador!");
        } else {
            resultado = prev / n;
            System.out.println(resultado);
        }
    }

    public static void previsaoMMEP(int dia, int mes, int ano, int upperLimit, int lowerLimit, int[][] dadosFicheiro, int linhas, double nAlpha) {
        double prev = 0;
        Scanner ordem = new Scanner(System.in);
        if (haInteracao) {
            System.out.println("Introduza o valor de α (]0,1])");
            do {
                nAlpha = ordem.nextDouble();
            } while (nAlpha <= 0 || nAlpha > 1);
        } else if (nAlpha <= 0 || nAlpha > 1) {
            System.out.println("Valor de nAlpha Inválido.Valide o valor introduzido");
            return;
        }

        for (int i = 0; i < linhas; i++) {
            if (dadosFicheiro[i][0] == ano && dadosFicheiro[i][1] == mes && dadosFicheiro[i][2] == dia - 1 && dadosFicheiro[linhas][3] >= lowerLimit && dadosFicheiro[linhas][3] <= upperLimit) {
                prev = (nAlpha * dadosFicheiro[i][4] + (1 - nAlpha) * (prev - 1)) + prev;

            }
        }
        if (prev == 0) {
            System.out.println("Não existe momento anterior ao introduzido pelo utilizador!");
        } else {
            System.out.println(prev);
        }
    }

    public static void previsaoMMEPDIA(int dia, int mes, int ano, int[][] dadosFicheiro, int linhas, double nAlpha) {
        double prev = 0;
        Scanner ordem = new Scanner(System.in);
        if (haInteracao) {
            System.out.println("Introduza o valor de α (]0,1])");
            do {
                nAlpha = ordem.nextDouble();
            } while (nAlpha <= 0 || nAlpha > 1);
        } else if (nAlpha <= 0 || nAlpha > 1) {
            System.out.println("Valor de nAlpha Inválido.Valide o valor introduzido");
            return;
        }

        for (int i = 0; i < linhas; i++) {
            if (dadosFicheiro[i][0] == ano && dadosFicheiro[i][1] == mes && dadosFicheiro[i][2] == dia - 1) {
                prev = (nAlpha * dadosFicheiro[i][4] + (1 - nAlpha) * (prev - 1)) + prev;

            }
        }
        if (prev == 0) {
            System.out.println("Não existe momento anterior ao introduzido pelo utilizador!");
        } else {
            System.out.println(prev);
        }
    }

    public static void previsaoMMEPMES(int mes, int ano, int[][] dadosFicheiro, int linhas, double nAlpha) {
        double prev = 0;
        Scanner ordem = new Scanner(System.in);
        if (haInteracao) {
            System.out.println("Introduza o valor de α (]0,1])");
            do {
                nAlpha = ordem.nextDouble();
            } while (nAlpha <= 0 || nAlpha > 1);
        } else if (nAlpha <= 0 || nAlpha > 1) {
            System.out.println("Valor de nAlpha Inválido.Valide o valor introduzido");
            return;
        }
        for (int i = 0; i < linhas; i++) {
            if (dadosFicheiro[i][0] == ano && dadosFicheiro[i][1] == mes - 1) {
                prev = (nAlpha * dadosFicheiro[i][4] + (1 - nAlpha) * (prev - 1)) + prev;

            }
        }
        if (prev == 0) {
            System.out.println("Não existe momento anterior ao introduzido pelo utilizador!");
        } else {
            System.out.println(prev);
        }
    }

    public static void previsaoMMEPANO(int ano, int[][] dadosFicheiro, int linhas, double nAlpha) {
        double prev = 0;
        Scanner ordem = new Scanner(System.in);
        if (haInteracao) {
            System.out.println("Introduza o valor de α (]0,1])");
            do {
                nAlpha = ordem.nextDouble();
            } while (nAlpha <= 0 || nAlpha > 1);
        } else if (nAlpha <= 0 || nAlpha > 1) {
            System.out.println("Valor de nAlpha Inválido.Valide o valor introduzido");
            return;
        }
        for (int i = 0; i < linhas; i++) {
            if (dadosFicheiro[i][0] == ano - 1) {
                prev = (nAlpha * dadosFicheiro[i][4] + (1 - nAlpha) * (prev - 1)) + prev;

            }
        }
        if (prev == 0) {
            System.out.println("Não existe momento anterior ao introduzido pelo utilizador!");
        } else {
            System.out.println(prev);
        }
    }

    public static int lerFicheiroCSV() {
        AnularMemoriaArr(dadosFicheiro);
        int colunas = 0;
        String filename;
        Scanner ler = new Scanner(System.in);
        System.out.println("Introduza o nome do ficheiro: ");
        filename = ler.nextLine();
        Scanner input;
        try {
            input = new Scanner(new File(filename));
            String lixo = input.nextLine();
            while (input.hasNext()) {
                String linha = input.nextLine();
                String[] separador;
                separador = linha.split(",");
                dadosFicheiro[colunas][0] = Integer.valueOf(separador[0]);
                dadosFicheiro[colunas][1] = Integer.valueOf(separador[1]);
                dadosFicheiro[colunas][2] = Integer.valueOf(separador[2]);
                dadosFicheiro[colunas][3] = Integer.valueOf(separador[3]);
                colunas++;
            }
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++) {
                    System.out.println(dadosFicheiro[i][j]);
                }
            }
            input.close();
            return colunas;

        } catch (FileNotFoundException ex) {
            System.out.println("Erro ao ler ficheiro csv");
            return 0;
        }

    }

    public static void AnularMemoriaArr(int[][] dadosFicheiro) {
        dadosFicheiro = null;
    }

    public static void guardarPng(JavaPlot p) {

        ImageTerminal png = new ImageTerminal();
        File file = new File("/Users/Massas/NetBeansProjects/ProjetoV1/plot.png");
        try {
            file.createNewFile();
            png.processOutput(new FileInputStream(file));
        } catch (FileNotFoundException ex) {
            System.err.print(ex);
        } catch (IOException ex) {
            System.err.print(ex);
        }

        p.setTerminal(png);

        try {
            ImageIO.write(png.getImage(), "png", file);
        } catch (IOException ex) {
            System.err.print(ex);
        }
    }

    public static void guardarCSV(int[] arrayS) {

        try (FileWriter csvWriter = new FileWriter("data.csv")) {
            csvWriter.append("MW");
            csvWriter.append("\n");
            for (int i = 0; i < arrayS.length; i++) {
                if (arrayS[i] > 0) {
                    csvWriter.append(String.join(",", Double.toString(arrayS[i])));
                    csvWriter.append("\n");
                }
            }
            csvWriter.flush();
        } catch (Exception e) {
            System.out.println("Não foi possivel gravar o ficheiro csv");
        }
    }

    public static void guardarCSV(double[] arrayS) {
        try (FileWriter csvWriter = new FileWriter("data.csv")) {
            csvWriter.append("MW");
            csvWriter.append("\n");
            for (int i = 0; i < arrayS.length; i++) {
                if (arrayS[i] > 0) {
                    csvWriter.append(String.join(",", Double.toString(arrayS[i])));
                    csvWriter.append("\n");
                }
            }
            csvWriter.flush();
        } catch (Exception e) {
            System.out.println("Não foi possivel gravar o ficheiro csv");
        }
    }
}
