//Classe que representa uma célula (indivíduo) no autômato celular.
public class Celula {
	private char estado;
    private int tempoInfect;
    
    // Construtor sem parâmetros.
	public Celula() {
		
	}    
    
	//Construtor que define o estado e tempo infecção inicial da célula.
    public Celula(char estado) {
		this.estado = estado;
		this.tempoInfect = 0;
	}

    //Métodos getters e setters para estado e tempo infectado
    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }
    
    public int getTempoInfect() {
		return tempoInfect;
	}
    
	public void setTempoInfect(int tempoInfect) {
		this.tempoInfect = tempoInfect;
	}
	
	//incremente e reseta tempo infectado
    public void incrementarTempoInfect() {
		this.tempoInfect++;
	}
    
	public void resetTempoInfect() {
		this.tempoInfect = 0;
	}
}
