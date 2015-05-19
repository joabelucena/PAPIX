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
        
        double tempBaixa, tempMedia, tempAlta, pressaoPouca, pressaoMuita;
        String continua;

        do { //Laco para controlar a entrada de novos valores

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
            
            
            System.out.println("\nGraus de pertinencia das ENTRADAS:");
            System.out.println("Umidade de Desejada: BAIXA = " 	+ MI_IN_UMID_DES[0]);
            System.out.println("Umidade de Desejada: MEDIA = " 	+ MI_IN_UMID_DES[1]);
            System.out.println("Umidade de Desejada: ALTA = " 	+ MI_IN_UMID_DES[2]);

            System.out.println("\nUmidade de Detectada: MUITO BAIXA = "	+ MI_IN_UMID_AMB[0]);
            System.out.println("Umidade de Detectada: POUCO BAIXA = "	+ MI_IN_UMID_AMB[1]);
            System.out.println("Umidade de Detectada: POUCO ALTA = " 	+ MI_IN_UMID_AMB[2]);
            System.out.println("Umidade de Detectada: MUITO ALTA = " 	+ MI_IN_UMID_AMB[3]);

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
			MI_OUT_UMID_BORR[2] = Math.min(Math.min(MI_IN_UMID_AMB[0], MI_IN_UMID_DES[0]), MI_OUT_UMID_BORR[2]);
			MI_OUT_UMID_TEMP[0] = Math.min(Math.min(MI_IN_UMID_AMB[0], MI_IN_UMID_DES[0]), MI_OUT_UMID_TEMP[0]);
			
			//02) SE Umidade Detectada = Pouco Baixa			E Umidade Desejada = Baixa ENTAO Borrifador = Não Borrifa 	e Aquecedor = Não Aquece
			MI_OUT_UMID_BORR[0] = Math.min(Math.min(MI_IN_UMID_AMB[1], MI_IN_UMID_DES[0]), MI_OUT_UMID_BORR[0]);
			MI_OUT_UMID_TEMP[0] = Math.min(Math.min(MI_IN_UMID_AMB[1], MI_IN_UMID_DES[0]), MI_OUT_UMID_TEMP[0]);
			
			//03) SE Umidade Detectada = Pouco Alta				E Umidade Desejada = Baixa ENTAO Borrifador = Não Borrifa 	e Aquecedor = Aquece Pouco
			MI_OUT_UMID_BORR[0] = Math.min(Math.min(MI_IN_UMID_AMB[2], MI_IN_UMID_DES[0]), MI_OUT_UMID_BORR[0]);
			MI_OUT_UMID_TEMP[2] = Math.min(Math.min(MI_IN_UMID_AMB[2], MI_IN_UMID_DES[0]), MI_OUT_UMID_TEMP[2]);
			
			//04) SE Umidade Detectada = Muito Alta				E Umidade Desejada = Baixa ENTAO Borrifador = Não Borrifa 	e Aquecedor = Aquece Muito
			MI_OUT_UMID_BORR[0] = Math.min(Math.min(MI_IN_UMID_AMB[3], MI_IN_UMID_DES[0]), MI_OUT_UMID_BORR[0]);
			MI_OUT_UMID_TEMP[3] = Math.min(Math.min(MI_IN_UMID_AMB[3], MI_IN_UMID_DES[0]), MI_OUT_UMID_TEMP[3]);
			
			//05) SE Umidade Detectada = Muito Baixa			E Umidade Desejada = Média ENTAO Borrifador = Borrifa Pouco e Aquecedor = Não Aquece
			MI_OUT_UMID_BORR[2] = Math.min(Math.min(MI_IN_UMID_AMB[0], MI_IN_UMID_DES[1]), MI_OUT_UMID_BORR[2]);
			MI_OUT_UMID_TEMP[0] = Math.min(Math.min(MI_IN_UMID_AMB[0], MI_IN_UMID_DES[1]), MI_OUT_UMID_TEMP[0]);
			
			//06) SE Umidade Detectada = Pouco Baixa			E Umidade Desejada = Média ENTAO Borrifador = Borrifa Pouco e Aquecedor = Não Aquece
			MI_OUT_UMID_BORR[2] = Math.min(Math.min(MI_IN_UMID_AMB[1], MI_IN_UMID_DES[1]), MI_OUT_UMID_BORR[2]);
			MI_OUT_UMID_TEMP[0] = Math.min(Math.min(MI_IN_UMID_AMB[1], MI_IN_UMID_DES[1]), MI_OUT_UMID_TEMP[0]);
			
			//07) SE Umidade Detectada = Pouco Alta				E Umidade Desejada = Média ENTAO Borrifador = Não Borrifa 	e Aquecedor = Aquece Pouco
			MI_OUT_UMID_BORR[0] = Math.min(Math.min(MI_IN_UMID_AMB[2], MI_IN_UMID_DES[1]), MI_OUT_UMID_BORR[0]);
			MI_OUT_UMID_TEMP[2] = Math.min(Math.min(MI_IN_UMID_AMB[2], MI_IN_UMID_DES[1]), MI_OUT_UMID_TEMP[2]);
			
			//08) SE Umidade Detectada = Muito Alta				E Umidade Desejada = Média ENTAO Borrifador = Não Borrifa 	e Aquecedor = Aquece Pouco
			MI_OUT_UMID_BORR[0] = Math.min(Math.min(MI_IN_UMID_AMB[3], MI_IN_UMID_DES[1]), MI_OUT_UMID_BORR[0]);
			MI_OUT_UMID_TEMP[2] = Math.min(Math.min(MI_IN_UMID_AMB[3], MI_IN_UMID_DES[1]), MI_OUT_UMID_TEMP[2]);
			
			//09) SE Umidade Detectada = Muito Baixa			E Umidade Desejada = Alta  ENTAO Borrifador = Borrifa Muito e Aquecedor = Não Aquece
			MI_OUT_UMID_BORR[3] = Math.min(Math.min(MI_IN_UMID_AMB[0], MI_IN_UMID_DES[2]), MI_OUT_UMID_BORR[3]);
			MI_OUT_UMID_TEMP[0] = Math.min(Math.min(MI_IN_UMID_AMB[0], MI_IN_UMID_DES[2]), MI_OUT_UMID_TEMP[0]);
			
			//10) SE Umidade Detectada = Pouco Baixa			E Umidade Desejada = Alta  ENTAO Borrifador = Borrifa Pouco e Aquecedor = Não Aquece
			MI_OUT_UMID_BORR[2] = Math.min(Math.min(MI_IN_UMID_AMB[1], MI_IN_UMID_DES[2]), MI_OUT_UMID_BORR[2]);
			MI_OUT_UMID_TEMP[0] = Math.min(Math.min(MI_IN_UMID_AMB[1], MI_IN_UMID_DES[2]), MI_OUT_UMID_TEMP[0]);
			
			//11) SE Umidade Detectada = Pouco Alta				E Umidade Desejada = Alta  ENTAO Borrifador = Não Borrifa 	e Aquecedor = Não Aquece
			MI_OUT_UMID_BORR[0] = Math.min(Math.min(MI_IN_UMID_AMB[2], MI_IN_UMID_DES[2]), MI_OUT_UMID_BORR[0]);
			MI_OUT_UMID_TEMP[0] = Math.min(Math.min(MI_IN_UMID_AMB[2], MI_IN_UMID_DES[2]), MI_OUT_UMID_TEMP[0]);
			
			//12) SE Umidade Detectada = Muito Alta				E Umidade Desejada = Alta  ENTAO Borrifador = Não Borrifa 	e Aquecedor = Aquece Pouco
			MI_OUT_UMID_BORR[0] = Math.min(Math.min(MI_IN_UMID_AMB[3], MI_IN_UMID_DES[2]), MI_OUT_UMID_BORR[0]);
			MI_OUT_UMID_TEMP[2] = Math.min(Math.min(MI_IN_UMID_AMB[3], MI_IN_UMID_DES[2]), MI_OUT_UMID_TEMP[2]);
            
			// mostrando os valores de pertinencia calculados
            System.out.println("\nGraus de pertinencia da SAIDA:");
            
            System.out.println("Saida Aquecedor: NAO AQUECE = " 			+ MI_OUT_UMID_TEMP[0]);
            System.out.println("Saida Aquecedor: AQUECE MUITO POUCO = "		+ MI_OUT_UMID_TEMP[1]); 
            System.out.println("Saida Aquecedor: AQUECE POUCO = "			+ MI_OUT_UMID_TEMP[2]);
            System.out.println("Saida Aquecedor: AQUECE MUITO = "			+ MI_OUT_UMID_TEMP[3]);
            
            System.out.println("\nSaida Borrifador: NAO BORRIFA = "			+ MI_OUT_UMID_BORR[0]);
            System.out.println("Saida Borrifador: BORRIFA MUITO POUCO = "	+ MI_OUT_UMID_BORR[1]);
            System.out.println("Saida Borrifador: BORRIFA POUCO = "			+ MI_OUT_UMID_BORR[2]);
            System.out.println("Saida Borrifador: BORRIFA MUITO = "			+ MI_OUT_UMID_BORR[3]);
            
            
            /**
             * ***********************************************************************
             * 
             * DEFUZZYFICACAO
             *
             * ***********************************************************************
             */
            System.out.println("\nENTRADAS:");
            System.out.println("- Umidade Desejada " 	+ IN_UMID_DES + "%");
            System.out.println("- Umidade Detectada " 	+ IN_UMID_AMB + "%");
            
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
            double limDireito, limEsquerdo;
            System.out.println("\nCalculo da saida usando MEDIA DOS MAXIMOS");
            
            /** Aquecimento **/
            if(naoAquece == aqueceMtoPouco && aqueceMtoPouco == aquecePouco && aquecePouco == aqueceMuito){
            	//Todos Iguais
            	limEsquerdo = 0;
            	limDireito = 45;
            	
            }else if(naoAquece > aqueceMtoPouco && naoAquece > aquecePouco && naoAquece > aqueceMuito){
            	//naoAquece eh o maior
            	limEsquerdo = 0;
            	limDireito = 5.0 - 5.0 * naoAquece;
            	
            }else if(aqueceMtoPouco > naoAquece && aqueceMtoPouco > aquecePouco && aqueceMtoPouco > aqueceMuito){
            	//aqueceMtoPouco eh o maior
            	limEsquerdo = 5 * aqueceMtoPouco;
            	limDireito = 20 - 15 * aqueceMtoPouco;
            	
            }else if(aquecePouco > naoAquece && aquecePouco > aqueceMtoPouco && aquecePouco > aqueceMuito){
            	//aquecePouco eh o maior
            	limEsquerdo = 15 + 10 * aquecePouco;
            	limDireito = 15 - 10 * aquecePouco;
            	
            }else if(aqueceMuito > naoAquece && aqueceMuito > aquecePouco && aqueceMuito > aqueceMtoPouco){
            	//aquecePouco eh o maior
            	limEsquerdo = 30 + 15 * aqueceMuito;
            	limDireito = 45;
            	
            }else if(naoAquece == aqueceMtoPouco){
            	//preenche nao aquece e aquecemtopouco
            	limEsquerdo = 0;
            	limDireito = 0;
            	
            }else if(aqueceMtoPouco == aquecePouco){
            	//preenche aquecemtoPouco e aquecepouco
            	limEsquerdo = 0;
            	limDireito = 0;
            	
            }else if(aquecePouco == aqueceMuito){
            	//preenche aquecepouco e aquecemto
            	limEsquerdo = 0;
            	limDireito = 0;
            
            }else{
            	limEsquerdo = 0;
            	limDireito = 45;
            }
            /*
            
            if (vazaoPequena == vazaoRegular && vazaoRegular == vazaoGrande) {
                limEsquerdo = 0;
                limDireito = 5;
            } else if (vazaoPequena > vazaoRegular && vazaoPequena > vazaoGrande) {
                limDireito = 2.5 - 2.0 * vazaoPequena;
                limEsquerdo = 0;
            } else if (vazaoGrande > vazaoPequena && vazaoGrande > vazaoRegular) {
                limEsquerdo = 2.0 * vazaoGrande + 2.5;
                limDireito = 5;
            } else if (vazaoRegular > vazaoPequena && vazaoRegular > vazaoGrande) {
                limEsquerdo = 2.0 * vazaoRegular + 0.5;
                limDireito = 4.5 - 2.0 * vazaoRegular;
            } else if (vazaoPequena == vazaoRegular) {
                limEsquerdo = 0;
                limDireito = 4.5 - 2.0 * vazaoRegular;
            } else if (vazaoRegular == vazaoGrande) {
                limEsquerdo = 2.0 * vazaoRegular + 0.5;
                limDireito = 5;
            } else { //if(vazaoPequena == vazaoGrande){
                limEsquerdo = 0;
                limDireito = 5;
            }
            */

            // usando a tecnica media dos maximos para calcular o valor da vazao
            System.out.println("limEsquerdo = " + limEsquerdo);
            System.out.println("limDireito = " + limDireito);
            //valVazao = (limEsquerdo + limDireito) / 2;

            // mostrando o valor da vazao calculado para as entradas digitadas
            System.out.print("Aquecimento = ");
            System.out.printf("%.2f", (limEsquerdo + limDireito) / 2);
            System.out.println(" graus");

            // verificando se o usuario quer digitar mais valores
            System.out.println("\nDeseja digitar mais valores? (S/N): ");
            continua = teclado.next();

        } while (!continua.toUpperCase().equals("N")); // retorna ao primeiro DO

    }
}
