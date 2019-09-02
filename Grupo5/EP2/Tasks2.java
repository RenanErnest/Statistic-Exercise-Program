package grupo5.parte2;
import org.apache.commons.math3.distribution.*;
import grupo5.parte1.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tasks2 {
    
    public double[] teta_argmax = new double[2000];
    public double[][] miTeta = new double[4][5];
    public double[][] varTeta = new double[4][5];
    
    public List<Integer> questoes = new ArrayList<Integer>();
    
    public Tasks2() {
        for (int i = 0; i < 100; i++) { //adicionando as questoes
            questoes.add(i);
        }
    }
    
    //Função que faz MLE
    public String II1(int[][] respostas, double[] a, double[] b) {
        
        for(int j = 0; j < 2000; j++) { //em todos os alunos
            this.teta_argmax[j] = bisseccao(respostas,a,b,100,j);
        }
        
        String s = "";
        for(int i = 0; i < 2000; i++) {
            s += this.teta_argmax[i] + "\n";
        }
        
        return s;
    }
    
    public String II2(int x, double[] aluno, double[] a, double[] b) {
        
        int[] questions = new int[100];
        for(int i = 0; i < questions.length; i++) { //inicializando o array em ordem crescente: 1, 2, 3, ...
            questions[i] = i;
        }
    
        //As questoes serão colocadas em ordem das melhores que diferenciam o aluno 5 do aluno 4
        //bubblesort
        for(int i = questions.length-1; i >= 0 ; i--) {
            for(int j = 0; j < i; j++) {
                //Se a diferença da prob. de acerto do aluno 5 e 4 na questao j é menor que a diferença da prob. de acerto do aluno 5 e 4 na questao j+1 troca
                if((p(aluno[4],a[questions[j]],b[questions[j]]) - p(aluno[3],a[questions[j]],b[questions[j]])) < (p(aluno[4],a[questions[j+1]],b[questions[j+1]]) - p(aluno[3],a[questions[j+1]],b[questions[j+1]]))) {
                    int aux = questions[j];
                    questions[j] = questions[j+1];
                    questions[j+1] = aux;
                }
            }
        }
        
        questoes.clear(); //limpando a antiga lista de questoes
        for (int i = 0; i < 100; i++) { //adicionando as questoes ordenadas na lista de questoes
            questoes.add(questions[i]);
        }
        
        double[][][] tetasX = new double[4][5][x];
        double[][] pFiveBetter = new double[4][4]; //probabilidade do aluno 5 ser melhor que o restante
        for(int i = 0; i < x; i++) {
            
            double[][] tetas = new double[4][5]; //prova k, aluno j
            
            //aplicando a prova de 10 questoes
            int respostas[][] = prova(10,aluno,a,b);
            for(int j = 0; j < 5; j++) {
                tetas[0][j] = bisseccao(respostas, a, b, 10, j);
                miTeta[0][j] += tetas[0][j];
                tetasX[0][j][i] = tetas[0][j];
            }
            for(int j = 0; j < 4; j++) { //contagem das vezes que o 5 é melhor
                if (tetas[0][j] < tetas[0][4]) pFiveBetter[0][j]++;
            }
            
            //aplicando a prova de 20 questoes
            respostas = prova(20,aluno,a,b);
            for(int j = 0; j < 5; j++) {
                tetas[1][j] = bisseccao(respostas, a, b, 20, j);
                miTeta[1][j] += tetas[1][j];
                tetasX[1][j][i] = tetas[1][j];
            }
            for(int j = 0; j < 4; j++) { //contagem das vezes que o 5 é melhor
                if (tetas[1][j] < tetas[1][4]) pFiveBetter[1][j]++;
            }
            
            //aplicando a prova de 50 questoes
            respostas = prova(50,aluno,a,b);
            for(int j = 0; j < 5; j++) {
                tetas[2][j] = bisseccao(respostas, a, b, 50, j);
                miTeta[2][j] += tetas[2][j];
                tetasX[2][j][i] = tetas[2][j];
            }
            for(int j = 0; j < 4; j++) { //contagem das vezes que o 5 é melhor
                if (tetas[2][j] < tetas[2][4]) pFiveBetter[2][j]++;
            }
            
            //aplicando a prova de 100 questoes
            respostas = prova(100,aluno,a,b);
            for(int j = 0; j < 5; j++) {
                tetas[3][j] = bisseccao(respostas, a, b, 100, j);
                miTeta[3][j] += tetas[3][j];
                tetasX[3][j][i] = tetas[3][j];
            }
            for(int j = 0; j < 4; j++) { //contagem das vezes que o 5 é melhor
                if (tetas[3][j] < tetas[3][4]) pFiveBetter[3][j]++;
            }
            
            //for(int k = 0; k < 100; k++) {
            //    for(int j = 0; j < 5; j++) System.out.print(respostas[k][j] + " ");
            //    System.out.println();
            //}
            
            //System.out.println();
            //for(int j = 0; j < 4; j++) {;
            //    for(int k = 0; k < 5; k++) System.out.print(aluno[k] + " = " + tetas[j][k] + " ");
            //    System.out.println();
            //}
            
        }
        String s = "";
        
        //gerando a probabilidade do aluno 5 ser melhor que os demais em todas as provas
        for(int j = 0; j < 4; j++) { //prova j
            for(int k = 0; k < 4; k++) { //aluno k
                pFiveBetter[j][k] /= x;
                s += pFiveBetter[j][k] + " ";
                miTeta[j][k] /= x;
            }
            s += "\n";
        }
        for(int i = 0; i < 4; i++) miTeta[i][4] /= x; //calculando a media
        
        //calculando a variancia
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 5; j++) {
                for(int k = 0; k < x; k++) {
                    varTeta[i][j] += Math.pow(tetasX[i][j][k] - miTeta[i][j],2);
                }
                varTeta[i][j] /= (x-1);
                System.out.print("mi=" + miTeta[i][j] + " var=" + varTeta[i][j] + "\t");
            }
            System.out.println();
        }
        
        return s;
    }
    
    public String II3(int n, double alfa, double[][] media, double[][] variancia) {
        NormalDistribution z = new NormalDistribution();
        String s = "";
        
        for(int i = 0; i < media.length; i++) {
            for(int j = 0; j < media[0].length; j++) {
                double InterConf = z.inverseCumulativeProbability(alfa/2.0) * Math.sqrt(varTeta[i][j]/n);
                s += InterConf + " " + -InterConf + " ";
            }
            s += "\n";
        }
        
        return s;
    }
    
    public double[] nota(int[][] respostas){
        double resultado[] = new double[5];
        for(int coluna = 0; coluna < 5; coluna++){
            resultado[coluna] = 0;
            for(int linha=0; linha < respostas.length; linha++){
                resultado[coluna] += respostas[linha][coluna];
            }
        }
        
        for(int i = 0; i < 5; i++){
            resultado[i] /= respostas.length;
        }
        
        return resultado;
    }
    
    public String II4(int x, double[] aluno, double alfa, double[] a, double[] b) {
        NormalDistribution z = new NormalDistribution();
	double notas[][][] = new double[4][5][x];
	
	 for(int i = 0; i < x; i++) { //loop de estimativa         
            
            //aplicando a prova de 10 questoes
            int[][] respostas = prova(10,aluno,a,b);
            double[] nota = nota(respostas);
            for(int k = 0; k < 5; k++) { 
                notas[0][k][i] = nota[k];
            }
            
            //aplicando a prova de 20 questoes
            respostas = prova(20,aluno,a,b);
            nota = nota(respostas);
            for(int k = 0; k < 5; k++) { 
                notas[1][k][i] = nota[k];
            }
            
            //aplicando a prova de 50 questoes
            respostas = prova(50,aluno,a,b);
            nota = nota(respostas);
            for(int k = 0; k < 5; k++) { 
                notas[2][k][i] = nota[k];
            }
            
            //aplicando a prova de 100 questoes
            respostas = prova(100,aluno,a,b);
            nota = nota(respostas);
            for(int k = 0; k < 5; k++) { 
                notas[3][k][i] = nota[k];
            }
        }

	double[][] media = new double[4][5];

	//calculando a média das notas de cada aluno mediante cada prova (soma)
	
	for(int j = 0; j < 4; j++){
            for(int k = 0; k < 5; k++){
                for(int i = 0; i < x; i++){
                    media[j][k] += notas[j][k][i];
		}
            }
	}

	//calculando a média das notas de cada aluno mediante cada prova (dividindo a soma por x)
	for(int j = 0; j < 4; j++){
		for(int k = 0; k < 5; k++){
			media[j][k] /= x;
		}
	}

	
	//calculando a variancia das notas de cada aluno mediante cada prova
	double[][] variancia = new double[4][5];

	//calculando a variancia
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 5; j++) {
                for(int k = 0; k < x; k++) {
                    variancia[i][j] += Math.pow(notas[i][j][k] - media[i][j], 2);
                }
                variancia[i][j] /= (x-1);
                System.out.print("mi=" + media[i][j] + " var=" + variancia[i][j] + "\t");
            }
            System.out.println();
        }
        String s = "";

	//calculando extremos do intervalo de confiança
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 5; j++) {
                double InterConf = z.inverseCumulativeProbability(alfa/2.0) * Math.sqrt(variancia[i][j]/x);
                s += InterConf + " " + -InterConf + " ";
            }
            s += "\n";
        }
        return s;
    }
    
    //j - aluno, ate a questao k
    public double bisseccao(int[][] respostas, double[] a, double[] b, int k, int j) {
        //Bissecção
        double epsilon = 0.0000001; //precisao ate a 6 casa
        double ini = -10.0; //teta de inicio
        double fim = 10.0; //teta final
        double x;
        double x1;
        int contIni = 0; //verificador de funções sem ponto crítico
        int contFim = 0;
        do {
            x = (ini + fim)/2;
            double gIni = derivative(ini,respostas,a,b,k,j);
            double gX = derivative(x,respostas,a,b,k,j);

            if (gIni * gX <= 0) {
                fim = x;
                contFim++;
            }
            else {
                ini = x;
                contIni++;
            }

            x1 = (ini + fim)/2;
        }
        while(Math.abs(x1 - x) >= epsilon);
        
        if (contIni == 0) return 6.0;
        if (contFim == 0) return -6.0;
        
        return x;
    }
    
    //Derivada dos logs
    //teta a ser testado, respostas, discriminante, dificuldade, até questao k, aluno j
    public double derivative(double teta, int[][] respostas, double[] a, double[] b, int k, int j) {
        double somatorio = 0;
        for(int i = 0; i < k; i++) { //somatorio da derivada do log das funções: Sum (log(tri))'
            int z = questoes.get(i);
            somatorio += respostas[i][j] == 1 ? a[z]*Math.exp(a[z]*b[z]) / (Math.exp(a[z]*b[z]) + Math.exp(a[z]*teta)) : -a[z]*Math.exp(a[z]*teta) / (Math.exp(a[z]*b[z]) + Math.exp(a[z]*teta));
        }
        return somatorio;
    }
    
    //teta a ser testado, respostas, discriminante, dificuldade, passo (inicia em 1), aluno j
    public double derivativeProdutorio(double teta, int[][] respostas, double[] a, double[] b, int k, int j) {
        double f = 1; //f
        for(int i = 0; i < 100-k; i++) { //produtorio das funções
            double tri = Math.exp(a[i]*(teta - b[i]))/(1 + Math.exp(a[i]*(teta - b[i]))); //TRI da questao i
            f *= respostas[i][j] == 1 ? tri : (1 - tri);
        }
        //g
        double triK = Math.exp(a[100-k]*(teta - b[100-k]))/(1 + Math.exp(a[100-k]*(teta - b[100-k]))); //tri da questao 100-k
        //g'
        double triKD = a[100-k]*Math.exp(a[100-k]*b[100-k] + a[100-k]*teta)/Math.pow(Math.exp(a[100-k]*b[100-k]) + Math.exp(a[100-k]*teta),2);

        if (respostas[100-k][j] == 0) {
            triK = (1 - triK);
            triKD = -1 * triKD;
        }

        //condicao de parada, a ultima derivada
        if(k == 100) return triKD;
        
        //retorna f'g * fg' onde f é o dos logTri até k e g é o logTri de 100-k
        return derivativeProdutorio(teta,respostas,a,b,++k,j)*triK + f*triKD;
    }
    
    public void testeII1(double[] teta_argmax, int[][] respostas, double[] a, double[] b) {
        
        grupo5.parte1.Tasks task1 = new grupo5.parte1.Tasks();
        
        int[] nota_min = new int[2000]; //nota mínima obtível por determinado aluno
        for(int i = 0; i < 2000; i++) nota_min[i] = 100;
        int[] nota_max = new int[2000]; //nota máxima obtível por determinado aluno
        int[] acertos = new int[2000]; //acertos de cada aluno do txt de reposta
        
        for(int x = 0; x < 1000; x++) {
            
            int[] nota = new int[2000];
            for(int i = 0; i < 2000; i++) {
                for(int j = 0; j < 100; j++) {
                    nota[i] += task1.bernoulli(teta_argmax[i],a[j],b[j]);
                    if (respostas[j][i] == 1) acertos[i]++;
                }
                
                if (nota[i] > nota_max[i]) nota_max[i] = nota[i];
                if (nota[i] < nota_min[i]) nota_min[i] = nota[i];
            }   
        }
        
        //for(int i = 0; i < 2000; i++) {
        //    System.out.println(teta_argmax[i] + " min: " + nota_min[i] + " max: " + nota_max[i]);
        //}
        
        for(int i = 0; i < 2000; i++) {
            if(acertos[i] <= nota_max[i] && acertos[i] >= nota_min[i]) System.out.println("true");
            else System.out.println("false");
        }
    }
    
    //Bernoulli de acordo com a questao e o aluno
    //teta - habilidade do aluno ; a - discriminação da questão ; b - dificuldade da questão
    public int bernoulli(double aluno, double a, double b) {
        double p = p(aluno,a,b);
        double z = Math.random();
        if (z <= p) return 1;
        return 0;
    }
    
    //teta - habilidade do aluno ; a - discriminação da questão ; b - dificuldade da questão
    public double p(double teta, double a, double b) {
        return Math.exp(a*(teta - b))/(1 + Math.exp(a*(teta - b))); //parametro da bernoulli
    }
    
    //retorna as notas dos alunos na prova de n questões aleatórias
    //parametros: numero de questões, tetas dos alunos, discrimador da questao, dificuldade da questao
    public int[][] prova(int n, double[] aluno, double[] a, double[] b) {
        
        int[][] respostas = new int[n][5];

        // questao i aluno j
        for(int i = 0; i < n; i++) {
            int z = questoes.get(i); //(int) Math.random() * 100; //aleatorio entre 0 e 99 (questao 1 a 100)
            for(int j = 0; j < 5; j++) { //aplicando a questao z a cada aluno
                respostas[i][j] = bernoulli(aluno[j],a[z],b[z]);
            }
        }
            
        return respostas;
    }
}
