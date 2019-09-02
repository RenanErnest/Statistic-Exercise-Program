package grupo5.parte2;
import java.io.*;
import java.util.Scanner;

// TRI: Math.exp(a[i]*(teta[j] - b[i]))/(1 + Math.exp(a[i]*(teta[j] - b[i])));
//TRI': a[i]*Math.exp(a[i]*(teta[j] + b[i]))/Math.pow((Math.exp(a[i]*b[i]) + Math.exp(a[i]*teta[j])),2);

public class EPIE2 {
    
    public static void main(String[] args) {
        int[][] respostas = new int[100][2000];
        double[] aluno = {-1.0,-0.5,0.0,0.5,1.0}; //teta
        double[] a = new double[100];
        double[] b = new double[100];
        
        //Leitura do arquivo
        try {
            FileReader arq = new FileReader("questoes.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            int i = 0;
            while (linha != null) {
                linha = linha.replaceAll("\\.", ",");
                Scanner scan = new Scanner(linha);
                a[i] = scan.nextDouble();
                b[i] = scan.nextDouble();
                linha = lerArq.readLine(); // lê da segunda até a última linha
                i++;
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",e.getMessage());
            System.exit(1);
        }
        
        //Leitura do arquivo
        try {
            FileReader arq = new FileReader("respostas.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            int i = 0;
            while (linha != null) {
                Scanner scan = new Scanner(linha);
                for(int j = 0; j < 2000; j++) {
                    respostas[i][j] = scan.nextInt();
                }
                linha = lerArq.readLine(); // lê da segunda até a última linha
                i++;
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",e.getMessage());
            System.exit(1);
        }
        
        //Objeto da classe Tasks
        Tasks2 task = new Tasks2();
        
        //Resolução do problema II1
        String s = task.II1(respostas,a,b);
        
        //Escrita do arquivo
        try { 
            BufferedWriter fWrite = new BufferedWriter(new FileWriter("II1.txt"));
            fWrite.append(s);
            fWrite.close();
        } catch (IOException e) { 
            System.err.printf("Erro na criação do arquivo de saida: %s.\n",e.getMessage()); 
            System.exit(1);
        }
        
        
        //Resolução do problema II2
        s = task.II2(10000,aluno,a,b);
        
        //Escrita do arquivo
        try { 
            BufferedWriter fWrite = new BufferedWriter(new FileWriter("II2.txt"));
            fWrite.append(s);
            fWrite.close();
        } catch (IOException e) { 
            System.err.printf("Erro na criação do arquivo de saida: %s.\n",e.getMessage()); 
            System.exit(1);
        }
        
        //Resolução do problema II3
        s = task.II3(10000,0.1,task.miTeta,task.varTeta);
        
        //Escrita do arquivo
        try { 
            BufferedWriter fWrite = new BufferedWriter(new FileWriter("II3.txt"));
            fWrite.append(s);
            fWrite.close();
        } catch (IOException e) { 
            System.err.printf("Erro na criação do arquivo de saida: %s.\n",e.getMessage()); 
            System.exit(1);
        }
        
        //Resolução do problema II4
        s = task.II4(10000, aluno, 0.1, a, b);
        
        //Escrita do arquivo
        try { 
            BufferedWriter fWrite = new BufferedWriter(new FileWriter("II4.txt"));
            fWrite.append(s);
            fWrite.close();
        } catch (IOException e) { 
            System.err.printf("Erro na criação do arquivo de saida: %s.\n",e.getMessage()); 
            System.exit(1);
        }
        
    }
    
    
}