/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Scanner;

/**
 *
 * @author Marcelo
 * 
 * @version v0(Controle de Estufa)
 */
public class Principal {

    private static Scanner teclado;
    private static int nHifen = 50;

	/**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        teclado = new Scanner(System.in);
        
        //Variaveis de Entrada e Saida
        double IN_UMID_DES, IN_UMID_AMB, OUT_UMID_TEMP, OUT_UMID_BORR;
        
        //Vetores com os Graus de Pertinencia
        double[] MI_IN_UMID_DES = new double[3];
        double[] MI_IN_UMID_AMB = new double[4];
        double[] MI_OUT_UMID_TEMP = new double[4];
        double[] MI_OUT_UMID_BORR = new double[4];
        
        //Limites para a construção do grafico
        double L_OUT_UMID_TEMP, R_OUT_UMID_TEMP;
        double L_OUT_UMID_BORR, R_OUT_UMID_BORR;
        
        //Variavel de controle para continuar laco de repeticao
        String continua;
        
        do { //Laco para controlar a entrada de novos valores
        	
        	System.out.println("*** Novo Calculo de Controle de Umidade ***");
        	System.out.println();

            /**
             * ***********************************************************************
             * 
             * Entrada dos valores
             * 
             * ***********************************************************************
             */
            // Controle: IN_UMID_DES (U:[60, 80])
            do {
                System.out.println("Digite a Umidade Desejada entre 60 e 80: ");
                IN_UMID_DES = teclado.nextDouble();                	
            } while (IN_UMID_DES < 60 || IN_UMID_DES > 80);

            // Controle: IN_UMID_AMB (U:[45, 90])
            do {
                System.out.println("Digite a Umidade Detectada entre 45 e 90: ");
                IN_UMID_AMB = teclado.nextDouble();
            } while (IN_UMID_AMB < 45 || IN_UMID_AMB > 90);
            
            /**
             * ***********************************************************************
             * 
             * FUZZYFICACAO
             * 
             * ***********************************************************************
             */
            
            /******* Umidade Desejada *******/
            
            // Funcao: BAIXA
            if (IN_UMID_DES >= 66) {
            	MI_IN_UMID_DES[0] = 0;
            } else if (IN_UMID_DES <= 62) {
            	MI_IN_UMID_DES[0] = 1;
            } else {
            	MI_IN_UMID_DES[0] = (66.0 - IN_UMID_DES) / 4;
            }
            
            // Funcao: MEDIA
            if (IN_UMID_DES <= 66 || IN_UMID_DES >= 74) {
            	MI_IN_UMID_DES[1] = 0;
            } else if (IN_UMID_DES == 70) {
            	MI_IN_UMID_DES[1] = 1;
            } else if (IN_UMID_DES > 66 && IN_UMID_DES < 70){
            	MI_IN_UMID_DES[1] = (74.0 - IN_UMID_DES) / 4;
            } else {
            	MI_IN_UMID_DES[1] = (IN_UMID_DES - 66.0) / 4;
            }

            // Funcao: ALTA
            if (IN_UMID_DES <= 74) {
            	MI_IN_UMID_DES[2] = 0;
            } else if (IN_UMID_DES >= 78) {
            	MI_IN_UMID_DES[2] = 1;
            } else {
            	MI_IN_UMID_DES[2] = (IN_UMID_DES - 74.0) / 4;
            }

            /******* Umidade Detectada *******/
            // Funcao: MUITO BAIXA
            if (IN_UMID_AMB >= 60) {
            	MI_IN_UMID_AMB[0] = 0;
            } else if (IN_UMID_AMB == 45) {
            	MI_IN_UMID_AMB[0] = 1;
            } else {
            	MI_IN_UMID_AMB[0] = (60.0 - IN_UMID_AMB) / 15;
            }
            
            // Funcao: POUCO BAIXA
            if (IN_UMID_AMB > 66) {
            	MI_IN_UMID_AMB[1] = 0;
            } else if (IN_UMID_AMB == 65) {
            	MI_IN_UMID_AMB[1] = 1;
            } else if (IN_UMID_AMB > 55 && IN_UMID_AMB < 65) {
            	MI_IN_UMID_AMB[1] = (IN_UMID_AMB - 55.0) / 10;
            } else {
            	MI_IN_UMID_AMB[1] = (75 - IN_UMID_AMB) / 10;
            }
            
            // Funcao: POUCO ALTA
            if (IN_UMID_AMB <= 65 || IN_UMID_AMB >= 80) {
            	MI_IN_UMID_AMB[2] = 0;
            } else if (IN_UMID_AMB >= 70 && IN_UMID_AMB <= 75) {
            	MI_IN_UMID_AMB[2] = 1;
            } else if (IN_UMID_AMB > 65 && IN_UMID_AMB < 70) {
            	MI_IN_UMID_AMB[2] = (IN_UMID_AMB - 65.0) / 5;
            } else {
            	MI_IN_UMID_AMB[2] = (80 - IN_UMID_AMB) / 5;
            }
            
            // Funcao: MUITO ALTA
            if (IN_UMID_AMB <= 80) {
            	MI_IN_UMID_AMB[3] = 0;
            } else if (IN_UMID_AMB == 90) {
            	MI_IN_UMID_AMB[3] = 1;
            } else {
            	MI_IN_UMID_AMB[3] = (IN_UMID_AMB - 80.0) / 10;
            }
            
            
            System.out.println();
            
            System.out.println("- Graus de pertinencia das ENTRADAS:");
            System.out.println("Umidade de Desejada: BAIXA: \t" 	+ MI_IN_UMID_DES[0]);
            System.out.println("Umidade de Desejada: MEDIA: \t" 	+ MI_IN_UMID_DES[1]);
            System.out.println("Umidade de Desejada: ALTA: \t" 	+ MI_IN_UMID_DES[2]);
            
            System.out.println();

            System.out.println("Umidade de Detectada: MUITO BAIXA: \t"	+ MI_IN_UMID_AMB[0]);
            System.out.println("Umidade de Detectada: POUCO BAIXA: \t"	+ MI_IN_UMID_AMB[1]);
            System.out.println("Umidade de Detectada: POUCO ALTA: \t" 	+ MI_IN_UMID_AMB[2]);
            System.out.println("Umidade de Detectada: MUITO ALTA: \t" 	+ MI_IN_UMID_AMB[3]);

            /**
             * ***********************************************************************
             * 
             * Regras de producao com o conhecimento do especialista
             * 
             * ***********************************************************************
             */
            /*****************************************
             * ** Grau de pertinencia Umidade Dectada
             * MI_IN_UMID_DES[0]: BAIXA
             * MI_IN_UMID_DES[1]: MEDIA
             * MI_IN_UMID_DES[2]: ALTA
             * 
             * ** Grau de pertinencia Umidade Ambiente
             * MI_IN_UMID_AMB[0]: MUITO BAIXA
             * MI_IN_UMID_AMB[1]: POUCO BAIXA
             * MI_IN_UMID_AMB[2]: POUCO ALTA
             * MI_IN_UMID_AMB[3]: MUITO ALTA
             * 
             * ** Grau de pertinencia Saida Aquecedor
             * MI_OUT_UMID_TEMP[0]: NAO AQUECE
             * MI_OUT_UMID_TEMP[1]: AQUECE MUITO POUCO
             * MI_OUT_UMID_TEMP[2]: AQUECE POUCO
             * MI_OUT_UMID_TEMP[3]: AQUECE MUITO
             * 
             * ** Grau de pertinencia Saida Borrifador
             * MI_OUT_UMID_BORR[0]: NAO BORRIFA
             * MI_OUT_UMID_BORR[1]: BORRIFA MUITO POUCO
             * MI_OUT_UMID_BORR[2]: BORRIFA POUCO
             * MI_OUT_UMID_BORR[3]: BORRIFA MUITO
             * 
            *****************************************/
			//01) SE Umidade Detectada = Muito Baixa		 	E Umidade Desejada = Baixa ENTAO Borrifador = Borrifa Pouco e Aquecedor = Não Aquece
            if(MI_OUT_UMID_BORR[2] == 0) MI_OUT_UMID_BORR[2] = Math.min(MI_IN_UMID_AMB[0], MI_IN_UMID_DES[0]);
            if(MI_OUT_UMID_TEMP[0] == 0) MI_OUT_UMID_TEMP[0] = Math.min(MI_IN_UMID_AMB[0], MI_IN_UMID_DES[0]);
			
			//02) SE Umidade Detectada = Pouco Baixa			E Umidade Desejada = Baixa ENTAO Borrifador = Não Borrifa 	e Aquecedor = Não Aquece
            if(MI_OUT_UMID_BORR[0] == 0) MI_OUT_UMID_BORR[0] = Math.min(MI_IN_UMID_AMB[1], MI_IN_UMID_DES[0]);
            if(MI_OUT_UMID_TEMP[0] == 0) MI_OUT_UMID_TEMP[0] = Math.min(MI_IN_UMID_AMB[1], MI_IN_UMID_DES[0]);
			
			//03) SE Umidade Detectada = Pouco Alta				E Umidade Desejada = Baixa ENTAO Borrifador = Não Borrifa 	e Aquecedor = Aquece Pouco
            if(MI_OUT_UMID_BORR[0] == 0) MI_OUT_UMID_BORR[0] = Math.min(MI_IN_UMID_AMB[2], MI_IN_UMID_DES[0]);
            if(MI_OUT_UMID_TEMP[2] == 0) MI_OUT_UMID_TEMP[2] = Math.min(MI_IN_UMID_AMB[2], MI_IN_UMID_DES[0]);
			
			//04) SE Umidade Detectada = Muito Alta				E Umidade Desejada = Baixa ENTAO Borrifador = Não Borrifa 	e Aquecedor = Aquece Muito
            if(MI_OUT_UMID_BORR[0] == 0) MI_OUT_UMID_BORR[0] = Math.min(MI_IN_UMID_AMB[3], MI_IN_UMID_DES[0]);
            if(MI_OUT_UMID_TEMP[3] == 0) MI_OUT_UMID_TEMP[3] = Math.min(MI_IN_UMID_AMB[3], MI_IN_UMID_DES[0]);
            
			//05) SE Umidade Detectada = Muito Baixa			E Umidade Desejada = Média ENTAO Borrifador = Borrifa Pouco e Aquecedor = Não Aquece
            if(MI_OUT_UMID_BORR[2] == 0) MI_OUT_UMID_BORR[2] = Math.min(MI_IN_UMID_AMB[0], MI_IN_UMID_DES[1]);
            if(MI_OUT_UMID_TEMP[0] == 0) MI_OUT_UMID_TEMP[0] = Math.min(MI_IN_UMID_AMB[0], MI_IN_UMID_DES[1]);
			
			//06) SE Umidade Detectada = Pouco Baixa			E Umidade Desejada = Média ENTAO Borrifador = Borrifa Pouco e Aquecedor = Não Aquece
            if(MI_OUT_UMID_BORR[2] == 0) MI_OUT_UMID_BORR[2] = Math.min(MI_IN_UMID_AMB[1], MI_IN_UMID_DES[1]);
            if(MI_OUT_UMID_TEMP[0] == 0) MI_OUT_UMID_TEMP[0] = Math.min(MI_IN_UMID_AMB[1], MI_IN_UMID_DES[1]);
			
			//07) SE Umidade Detectada = Pouco Alta				E Umidade Desejada = Média ENTAO Borrifador = Não Borrifa 	e Aquecedor = Aquece Pouco
            if(MI_OUT_UMID_BORR[0] == 0) MI_OUT_UMID_BORR[0] = Math.min(MI_IN_UMID_AMB[2], MI_IN_UMID_DES[1]);
            if(MI_OUT_UMID_TEMP[2] == 0) MI_OUT_UMID_TEMP[2] = Math.min(MI_IN_UMID_AMB[2], MI_IN_UMID_DES[1]);
			
			//08) SE Umidade Detectada = Muito Alta				E Umidade Desejada = Média ENTAO Borrifador = Não Borrifa 	e Aquecedor = Aquece Pouco
            if(MI_OUT_UMID_BORR[0] == 0) MI_OUT_UMID_BORR[0] = Math.min(MI_IN_UMID_AMB[3], MI_IN_UMID_DES[1]);
            if(MI_OUT_UMID_TEMP[2] == 0) MI_OUT_UMID_TEMP[2] = Math.min(MI_IN_UMID_AMB[3], MI_IN_UMID_DES[1]);
			
			//09) SE Umidade Detectada = Muito Baixa			E Umidade Desejada = Alta  ENTAO Borrifador = Borrifa Muito e Aquecedor = Não Aquece
            if(MI_OUT_UMID_BORR[3] == 0) MI_OUT_UMID_BORR[3] = Math.min(MI_IN_UMID_AMB[0], MI_IN_UMID_DES[2]);
            if(MI_OUT_UMID_TEMP[0] == 0) MI_OUT_UMID_TEMP[0] = Math.min(MI_IN_UMID_AMB[0], MI_IN_UMID_DES[2]);
			
			//10) SE Umidade Detectada = Pouco Baixa			E Umidade Desejada = Alta  ENTAO Borrifador = Borrifa Pouco e Aquecedor = Não Aquece
            if(MI_OUT_UMID_BORR[2] == 0) MI_OUT_UMID_BORR[2] = Math.min(MI_IN_UMID_AMB[1], MI_IN_UMID_DES[2]);
            if(MI_OUT_UMID_TEMP[0] == 0) MI_OUT_UMID_TEMP[0] = Math.min(MI_IN_UMID_AMB[1], MI_IN_UMID_DES[2]);
			
			//11) SE Umidade Detectada = Pouco Alta				E Umidade Desejada = Alta  ENTAO Borrifador = Não Borrifa 	e Aquecedor = Não Aquece
            if(MI_OUT_UMID_BORR[0] == 0) MI_OUT_UMID_BORR[0] = Math.min(MI_IN_UMID_AMB[2], MI_IN_UMID_DES[2]);
            if(MI_OUT_UMID_TEMP[0] == 0) MI_OUT_UMID_TEMP[0] = Math.min(MI_IN_UMID_AMB[2], MI_IN_UMID_DES[2]);
			
			//12) SE Umidade Detectada = Muito Alta				E Umidade Desejada = Alta  ENTAO Borrifador = Não Borrifa 	e Aquecedor = Aquece Pouco
            if(MI_OUT_UMID_BORR[0] == 0) MI_OUT_UMID_BORR[0] = Math.min(MI_IN_UMID_AMB[3], MI_IN_UMID_DES[2]);
            if(MI_OUT_UMID_TEMP[2] == 0) MI_OUT_UMID_TEMP[2] = Math.min(MI_IN_UMID_AMB[3], MI_IN_UMID_DES[2]);
            
			// mostrando os valores de pertinencia calculados
            System.out.println();
            System.out.println("- Graus de pertinencia da SAIDA:");
            
            System.out.println("Saida Aquecedor: NAO AQUECE: \t\t" 			+ MI_OUT_UMID_TEMP[0]);
            System.out.println("Saida Aquecedor: AQUECE MUITO POUCO: \t"	+ MI_OUT_UMID_TEMP[1]); 
            System.out.println("Saida Aquecedor: AQUECE POUCO: \t\t"		+ MI_OUT_UMID_TEMP[2]);
            System.out.println("Saida Aquecedor: AQUECE MUITO: \t\t"		+ MI_OUT_UMID_TEMP[3]);
            
            System.out.println();
            
            System.out.println("- Saida Borrifador: NAO BORRIFA: \t"		+ MI_OUT_UMID_BORR[0]);
            System.out.println("Saida Borrifador: BORRIFA MUITO POUCO: \t"	+ MI_OUT_UMID_BORR[1]);
            System.out.println("Saida Borrifador: BORRIFA POUCO: \t"		+ MI_OUT_UMID_BORR[2]);
            System.out.println("Saida Borrifador: BORRIFA MUITO: \t"		+ MI_OUT_UMID_BORR[3]);
            
            
            /**
             * ***********************************************************************
             * 
             * DEFUZZYFICACAO
             *
             * ***********************************************************************
             */
            System.out.println();
            System.out.println("ENTRADAS:");
            System.out.println("- Umidade Desejada:\t"		+ IN_UMID_DES + "%");
            System.out.println("- Umidade Detectada:\t" 	+ IN_UMID_AMB + "%");
            
            //************************************************************************
            // MEDIA DOS MAXIMOS
            //************************************************************************
            /*****************************************
             * ** Grau de pertinencia Umidade Dectada
             * MI_IN_UMID_DES[0]: BAIXA
             * MI_IN_UMID_DES[1]: MEDIA
             * MI_IN_UMID_DES[2]: ALTA
             * 
             * ** Grau de pertinencia Umidade Ambiente
             * MI_IN_UMID_AMB[0]: MUITO BAIXA
             * MI_IN_UMID_AMB[1]: POUCO BAIXA
             * MI_IN_UMID_AMB[2]: POUCO ALTA
             * MI_IN_UMID_AMB[3]: MUITO ALTA
             * 
             * ** Grau de pertinencia Saida Aquecedor
             * MI_OUT_UMID_TEMP[0]: NAO AQUECE
             * MI_OUT_UMID_TEMP[1]: AQUECE MUITO POUCO
             * MI_OUT_UMID_TEMP[2]: AQUECE POUCO
             * MI_OUT_UMID_TEMP[3]: AQUECE MUITO
             * 
             * ** Grau de pertinencia Saida Borrifador
             * MI_OUT_UMID_BORR[0]: NAO BORRIFA
             * MI_OUT_UMID_BORR[1]: BORRIFA MUITO POUCO
             * MI_OUT_UMID_BORR[2]: BORRIFA POUCO
             * MI_OUT_UMID_BORR[3]: BORRIFA MUITO
             * 
            *****************************************/
            
            double naoAquece		= MI_OUT_UMID_TEMP[0];
            double aqueceMtoPouco	= MI_OUT_UMID_TEMP[1];
            double aquecePouco		= MI_OUT_UMID_TEMP[2];
            double aqueceMuito		= MI_OUT_UMID_TEMP[3];

            double naoBorrifa		= MI_OUT_UMID_BORR[0];
            double borrifaMtoPouco	= MI_OUT_UMID_BORR[1];
            double borrifaPouco		= MI_OUT_UMID_BORR[2];
            double borrifaMuito		= MI_OUT_UMID_BORR[3];
            
            
            // calculando os limites esquerdo e direito do grafico
            System.out.println();
            System.out.println("Calculo da saida usando MEDIA DOS MAXIMOS");
            separador();
            
            /** Aquecimento **/
            if(naoAquece == aqueceMtoPouco && aqueceMtoPouco == aquecePouco && aquecePouco == aqueceMuito){
            	//Todos Iguais
            	L_OUT_UMID_TEMP = 0.0;
            	R_OUT_UMID_TEMP = 45.0;
            	
            }else if(naoAquece > aqueceMtoPouco && naoAquece > aquecePouco && naoAquece > aqueceMuito){
            	//naoAquece eh o maior
            	L_OUT_UMID_TEMP = 0.0;
            	R_OUT_UMID_TEMP = 5.0 - 5.0 * naoAquece;
            	
            }else if(aqueceMtoPouco > naoAquece && aqueceMtoPouco > aquecePouco && aqueceMtoPouco > aqueceMuito){
            	//aqueceMtoPouco eh o maior
            	L_OUT_UMID_TEMP = 5.0 * aqueceMtoPouco;
            	R_OUT_UMID_TEMP = 20.0 - 15.0 * aqueceMtoPouco;
            	
            }else if(aquecePouco > naoAquece && aquecePouco > aqueceMtoPouco && aquecePouco > aqueceMuito){
            	//aquecePouco eh o maior
            	L_OUT_UMID_TEMP = 15.0 + 10.0 * aquecePouco;
            	R_OUT_UMID_TEMP = 15.0 - 10.0 * aquecePouco;
            	
            }else if(aqueceMuito > naoAquece && aqueceMuito > aquecePouco && aqueceMuito > aqueceMtoPouco){
            	//aqueceMuito eh o maior
            	L_OUT_UMID_TEMP = 30.0 + 15.0 * aqueceMuito;
            	R_OUT_UMID_TEMP = 45.0;
            	
            }else if(naoAquece == aqueceMtoPouco){
            	//preenche nao aquece e aquecemtopouco
            	L_OUT_UMID_TEMP = 0.0;
            	R_OUT_UMID_TEMP = 20.0 - 15.0 * aqueceMtoPouco;
            	
            }else if(aqueceMtoPouco == aquecePouco){
            	//preenche aquecemtoPouco e aquecepouco
            	L_OUT_UMID_TEMP = 5.0 * aqueceMtoPouco;
            	R_OUT_UMID_TEMP = 15.0 - 10.0 * aquecePouco;
            	
            }else if(aquecePouco == aqueceMuito){
            	//preenche aquecepouco e aquecemto
            	L_OUT_UMID_TEMP = 15.0 + 10.0 * aquecePouco;
            	R_OUT_UMID_TEMP = 45.0;
            
            }else{
            	L_OUT_UMID_TEMP = 0.0;
            	R_OUT_UMID_TEMP = 45.0;
            }
            
            /** Borrifacao **/
            if(naoBorrifa == borrifaMtoPouco && borrifaMtoPouco == borrifaPouco && borrifaPouco == borrifaMuito){
            	//Todos Iguais
            	L_OUT_UMID_BORR = 0.0;
            	R_OUT_UMID_BORR = 10.0;
            	
            }else if(naoBorrifa > borrifaMtoPouco && naoBorrifa > borrifaPouco && naoBorrifa > borrifaMuito){
            	//naoBorrifa eh o maior
            	L_OUT_UMID_BORR = 0.0;
            	R_OUT_UMID_BORR = 1.0 - naoBorrifa;
            	
            }else if(borrifaMtoPouco > naoBorrifa && borrifaMtoPouco > borrifaPouco && borrifaMtoPouco > borrifaMuito){
            	//borrifaMtoPouco eh o maior
            	L_OUT_UMID_BORR = borrifaMtoPouco;
            	R_OUT_UMID_BORR = 4.0 - 3.0 * borrifaMtoPouco;
            	
            }else if(borrifaPouco > naoBorrifa && borrifaPouco > borrifaMtoPouco && borrifaPouco > borrifaMuito){
            	//borrifaPouco eh o maior
            	L_OUT_UMID_BORR = 6.0 + 3.0 * borrifaPouco;
            	R_OUT_UMID_BORR = 7.0 - borrifaPouco;
            	
            }else if(borrifaMuito > naoBorrifa && borrifaMuito > borrifaPouco && borrifaMuito > borrifaMtoPouco){
            	//borrifaMuito eh o maior
            	L_OUT_UMID_BORR = 6.0 + 4.0 * borrifaMuito;
            	R_OUT_UMID_BORR = 10.0;
            	
            }else if(naoBorrifa == borrifaMtoPouco){
            	//preenche nao aquece e borrifaMtoPouco
            	L_OUT_UMID_BORR = 0.0;
            	R_OUT_UMID_BORR = 20.0 - 15.0 * borrifaMtoPouco;
            	
            }else if(borrifaMtoPouco == borrifaPouco){
            	//preenche borrifaMtoPouco e borrifaPouco
            	L_OUT_UMID_BORR = 5.0 * borrifaMtoPouco;
            	R_OUT_UMID_BORR = 15.0 - 10.0 * borrifaPouco;
            	
            }else if(borrifaPouco == borrifaMuito){
            	//preenche borrifaPouco e aquecemto
            	L_OUT_UMID_BORR = 15.0 + 10.0 * borrifaPouco;
            	R_OUT_UMID_BORR = 45.0;
            
            }else{
            	L_OUT_UMID_BORR = 0.0;
            	R_OUT_UMID_BORR = 10.0;
            }
            
            //MEDIA
            OUT_UMID_TEMP = (L_OUT_UMID_TEMP + R_OUT_UMID_TEMP)/2;
            OUT_UMID_BORR = (L_OUT_UMID_BORR + R_OUT_UMID_BORR)/2;

            
            // usando a tecnica media dos maximos para calcular o valor da vazao
            System.out.println("Limite Esquerdo (Aquecedor):\t" + L_OUT_UMID_TEMP);
            System.out.println("Limite Direito (Aquecedor):\t" + R_OUT_UMID_TEMP);
            
            System.out.println("Limite Esquerdo (Borrifador):\t" + L_OUT_UMID_BORR);
            System.out.println("Limite Direito (Borrifador):\t" + R_OUT_UMID_BORR);
            
            System.out.println();
            
            separador();
            System.out.println("SAIDAS:");
            
            // mostrando o valor da vazao calculado para as entradas digitadas
            System.out.println("Aquecedor(ºC): \t "	+ OUT_UMID_TEMP);
            System.out.println("Borrifador(L/h): "	+ OUT_UMID_BORR);

            // verificando se o usuario quer digitar mais valores
            System.out.println();
            System.out.println("Deseja digitar mais valores? (S/N): ");
            continua = teclado.next();

        } while (!continua.toUpperCase().equals("N")); // retorna ao primeiro DO
        
        separador();
        System.out.println("Obrigado por utilizar nossos servcos de Calculo de Estufa!");
        System.out.println();
        System.out.println("That's all Folks!");

    }
    
    public static void separador(){
    	System.out.println(String.format(String.format("%%0%dd", nHifen), 0).replace("0","-"));
    }
    
    
}
