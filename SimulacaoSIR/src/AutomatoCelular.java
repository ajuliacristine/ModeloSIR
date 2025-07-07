import java.util.Random;

// Classe que implementa o autômato celular para a simulação SIR.
public class AutomatoCelular {
	public Random random = new Random();
	public Celula[][] grade;
	private Celula[][] gradeAuxiliar;
	private double pv, ps, pc, pd, po, k; //paramentros da simulação.
	private int tamanhoGrade;
	private int tempoRec;
	
	/**
     * Construtor que inicializa o autômato celular com os parâmetros fornecidos.
     * @param pv Probabilidade de vacinação (S → R).
     * @param ps Probabilidade de infecção espontânea (casos importados, S → I).
     * @param pc Probabilidade de cura (I → R).
     * @param pd Probabilidade de morte (I → S).
     * @param po Probabilidade de morte de recuperados (R → S).
     * @param k Fator de infectividade.
     * @param tamanhoGrade Tamanho da grade (N x N).
     * @param tempoRec Tempo mínimo para recuperação.
     */
	public AutomatoCelular(double pv, double ps, double pc, double pd, double po, double k, int tamanhoGrade, int tempoRec) {
		this.pv = pv;
		this.ps = ps;
		this.pc = pc;
		this.pd = pd;
		this.po = po;
		this.k = k;
		this.tamanhoGrade = tamanhoGrade; 
		this.tempoRec = tempoRec;
		this.grade = new Celula[tamanhoGrade][tamanhoGrade];
		this.gradeAuxiliar = new Celula[tamanhoGrade][tamanhoGrade];
		
		inicializarGrade();
	}
	
	//Inicializa a grade com 99% de células Susceptíveis (S) e 1% de Infectadas (I).
	public void inicializarGrade() {
		
		// Inicializa todas as células como Susceptiveis
		for (int i = 0 ; i < tamanhoGrade; i++) {
			for (int j = 0; j < tamanhoGrade; j++) {
				grade[i][j] = new Celula('S');			
			}
		}
		
		// Define aleatoriamente 1% das células como Infectadas
		double umPorcentInfect = tamanhoGrade * tamanhoGrade * 0.01;
		int infectados = 0;
		
		while (umPorcentInfect > infectados) {
			int linha = random.nextInt(tamanhoGrade);
			int coluna = random.nextInt(tamanhoGrade);
				
			if (grade[linha][coluna].getEstado() == 'S') {
				grade[linha][coluna].setEstado('I');
				grade[linha][coluna].resetTempoInfect();
				infectados++;
			}
		}
		
	}
	
	//Atualiza a grade para a próxima iteração
	public void proximaGrade() {
		
		 // Copia o estado atual para a grade auxiliar
		for (int i = 0 ; i < tamanhoGrade; i++) {
			for (int j = 0; j < tamanhoGrade; j++) {
				gradeAuxiliar[i][j] = new Celula();
				gradeAuxiliar[i][j].setEstado(grade[i][j].getEstado());
				gradeAuxiliar[i][j].setTempoInfect( grade[i][j].getTempoInfect());
				
			}
		}
		
		// Aplica as regras de transição para cada célula
		for (int i = 0; i < tamanhoGrade; i++) {
			for (int j = 0; j < tamanhoGrade; j++) {
				
				// Regras para células Susceptíveis (S)
				if (gradeAuxiliar[i][j].getEstado() == 'S') {
					double r1 = random.nextDouble();
					double r2 = random.nextDouble();
					
					if(r1 < pv) { // Vacinação (S → R)
						grade[i][j].setEstado('R');
						
					} else if (r2 < ps) {  // Caso importado (S → I)
						grade[i][j].setEstado('I');
						grade[i][j].resetTempoInfect();;
						
					} else {
						// Infecção por vizinhança
						double r3 = random.nextDouble();
						int v = contarVizinhosInfect(i, j);
						double pi = 1 - Math.pow(Math.E, -k * v);
						
						if(r3 < pi) {
							grade[i][j].setEstado('I');
							grade[i][j].resetTempoInfect();
						}
						
					}
				
			    // Regras para células Infectadas (I)	
				} else if ( gradeAuxiliar[i][j].getEstado() == 'I') {
					grade[i][j].incrementarTempoInfect();
					
					double r4 = random.nextDouble();
					double r5 = random.nextDouble();
					if(r4 < pc) { // Cura (I → R)
					    grade[i][j].setEstado('R');
					} else if (r5 < pd) { // Morte (I → S)
					    grade[i][j].setEstado('S');
					}
					grade[i][j].resetTempoInfect();
					
				// Regras para células Recuperadas (R)	
				} else if (gradeAuxiliar[i][j].getEstado() == 'R') {
					grade[i][j].resetTempoInfect();
					
					double r6 = random.nextDouble();
					
					if ( r6 < po) { // Morte de recuperado (R → S)
					grade[i][j].setEstado('S');
					}
				}
			}
		}

	}
	
	/**
     * Conta o número de vizinhos infectados de uma célula.
     * @param linha Linha da célula.
     * @param coluna Coluna da célula.
     * @return Número de vizinhos infectados.
     */
	public int contarVizinhosInfect(int linha, int coluna) {
		int infectados = 0;
		
		//conta vizinhos infectados, ignorando a própria célula
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
	
				if(i == 0 && j == 0) {
					continue;
				}
				
				//Regra toroiadal (bordas se conectam)
				int vizinhoLinha = (linha + i + tamanhoGrade) % tamanhoGrade;
				int vizinhoColuna = (coluna + j + tamanhoGrade) % tamanhoGrade;
				
				if (gradeAuxiliar[vizinhoLinha][vizinhoColuna].getEstado() == 'I') {
					infectados++;
				}		
			}
		}
		return infectados;
	}
	
	 /**
     * Conta o número de células em cada estado (S, I, R).
     * @return Objeto Estatisticas com as contagens.
     */
	public Estatisticas contarEstados() {
		int s = 0;
		int i = 0;
		int r = 0;
		
		for (Celula[] linha : grade) {
			for (Celula celula : linha) {
				if(celula.getEstado() == 'S') {
					s++;
				} else if(celula.getEstado() == 'I') {
					i++;
				} else if(celula.getEstado() == 'R') {
					r++;
				}
			}
		}
		return new Estatisticas(s, i, r);
	}	
}	
	           


