import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

/**
 * Classe principal que executa a simulação SIR e exibe os resultados em um gráfico.
 */
public class Simulacao {
	public static void main(String[] args) {
		
		// Parâmetros da simulação (valores padrão)
	     double pv = 0.03;   // Probabilidade de vacinação (S → R)
	     double ps = 0.01; 	// Probabilidade de infecção espontânea (S → I)
	     double pc = 0.6;	// Probabilidade de cura (I → R)
	     double pd = 0.3;	// Probabilidade de morte (I → S)
	     double po = 0.1;	// Probabilidade de morte de recuperados (R → S)
	     double k = 1.0;	// Fator de infectividade
	     int tamanhoGrade = 200;	// Tamanho da grade (200x200)
	     int iterações = 200; // Número de iterações
	     int tempoRec = 1;	// Tempo mínimo para recuperação

	    // Inicializa o autômato celular
		AutomatoCelular automato = new AutomatoCelular(pv, ps, pc, pd, po, k, tamanhoGrade, tempoRec);
		
		// Lista para armazenar as estatísticas de cada iteração
		List<Estatisticas> estatisticas = new ArrayList<>();
		
		// Executa a simulação
		for (int i = 0; i < iterações; i++) {
			automato.proximaGrade();	
			estatisticas.add(automato.contarEstados());
		}
		
		// Prepara os dados para o gráfico
		List<Integer> dadosS = new ArrayList<>();
		List<Integer> dadosI = new ArrayList<>();
		List<Integer> dadosR = new ArrayList<>();

		for (Estatisticas estat : estatisticas) {
		    dadosS.add(estat.getS());
		    dadosI.add(estat.getI());
		    dadosR.add(estat.getR());
		}
		
		// Cria e exibe o gráfico
		XYChart chart = new XYChartBuilder().width(800).height(600)
		           .title("Simulação SIR").xAxisTitle("Iterações").yAxisTitle("População").build();
		 	chart.addSeries("Susceptíveis", dadosS);
	        chart.addSeries("Infectados", dadosI);
	        chart.addSeries("Recuperados", dadosR);
	        
	        new SwingWrapper<>(chart).displayChart();
	    }
}
