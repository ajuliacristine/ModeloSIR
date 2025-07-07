//Classe que armazena as estatísticas da simulação SIR (Susceptíveis, Infectados, Recuperados).
public class Estatisticas {
	private final int s; 
	private final int i; 
	private final int r; 
	
	//Construtor padrão que inicializa todas as estatísticas com zero.
	public Estatisticas() {
		this.s = 0;
		this.i = 0;
		this.r = 0;	
	}
	
	 /**
     * Construtor que inicializa as estatísticas com valores específicos.
     * @param s Quantidade de susceptíveis.
     * @param i Quantidade de infectados.
     * @param r Quantidade de recuperados.
     */
	public Estatisticas(int s, int i, int r) {
		this.s = s;
		this.i = i;
		this.r = r;	
	}
	
	// Métodos getters para acessar as estatísticas
	public int getI() {
		return i;
	}
	
	public int getR() {
		return r;
	}
	public int getS() {
		return s;
	}
}


