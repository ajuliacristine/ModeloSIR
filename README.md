# Trabalho de Conclusão — Modelo SIR com Autômatos Celulares
Objetivo

Desenvolver um simulador do modelo SIR (Susceptível-Infectado-Recuperado) usando Autômatos Celulares em Java, aplicando as regras de transição probabilísticas apresentadas no artigo de referência. O objetivo é compreender a dinâmica de propagação de doenças infecciosas e aplicar conceitos de programação orientada a objetos, simulação e visualização de dados.

Passo a Passo
1. Estudo Inicial
Leia o artigo de referência para compreender o modelo SIR e as regras de transição probabilísticas baseadas em PCA (Probabilistic Cellular Automata).

Consulte o guia em Java para entender a estrutura de classes sugerida: Celula, AutomatoCelular e Simulacao.

Revise o Capítulo 7 do livro The Nature of Code para entender conceitos de autômatos celulares.

2. Definição das Classes (Estrutura do Projeto)
Celula.java

Representa um indivíduo da população:

Estado: 'S', 'I' ou 'R'.

Tempo infectado: contador para determinar quando a célula se recupera.

AutomatoCelular.java

Gerencia a grade 2D de células.

Implementa as regras de transição probabilísticas (detalhadas no passo 4).

Utiliza as taxas de transição indicadas na Figura 1 do artigo (detalhadas no passo 3).

Aplica atualização simultânea usando uma grade auxiliar.

Simulacao.java

Gerencia o ciclo de simulação.

Coleta dados de S, I e R em cada passo.

Exibe gráficos com XChart.

(Opcional) Exporta dados para CSV.

3. Parametrização
Antes da simulação, solicitar ao usuário os seguintes parâmetros — valores sugeridos (da Figura 1) para uso inicial:

Parâmetro	Valor sugerido
Pv (vacinação)	3%
Ps (importados)	1%
Pc (cura)	60%
Pd (morte infectado)	30%
Po (morte recuperado)	10%
k (infectividade)	1
Tamanho da grade	200 x 200
Tempo de recuperação	≥ 1 iteração (ajustável conforme necessário)
Valores da ODE (referência teórica):

v = 0.03,

ac = 0.0097,

b = 0.6,

c = 0.12,

h = 0.1,

aN ≈ 2.83.

✤ No PCA, considere que cada célula representa um indivíduo e que as mortes são substituídas por suscetíveis (população constante).

4. Algoritmo de Simulação (por iteração)
Para cada célula:

Se Suscetível (S):

Sorteia r1: se r1 < Pv, muda para Recuperado (R) (vacinação).

Senão, sorteia r2: se r2 < Ps, muda para Infectado (I) (caso importado).

Senão:

Conta infectados vizinhos (v).

Calcula Pi = 1 - exp(-k * v).

Sorteia r3: se r3 < Pi, muda para Infectado (I).

Se Infectado (I):
4. Sorteia r4: se r4 < Pc, muda para Recuperado (R).
5. Senão, sorteia r5: se r5 < Pd, morre e vira Suscetível (S).

Se Recuperado (R):
6. Sorteia r6: se r6 < Po, morre e vira Suscetível (S).

Atualizar a grade simultaneamente usando uma grade auxiliar.

Registrar as contagens de S, I e R.

5. Inicialização do Reticulado (Grade)
População total: N = 200 x 200 (40.000 indivíduos).

Estados iniciais:

Suscetíveis: 99% → 39.600 células.

Infectados: 1% → 400 células.

Recuperados: 0% → 0 células.

Como distribuir:

Colocar uma célula infectada no centro (simples) ou sortear aleatoriamente 1% das células para o estado I.

Todas as demais células começam como S.

Contorno: Toroidal (conexão nas bordas) para evitar efeitos de borda.

6. Visualização e Análise
Plotar os gráficos de evolução de S, I e R usando XChart.

(Opcional) Exportar dados para arquivo CSV para análise posterior.

7. Considerações Finais
Comentar o código para facilitar a leitura.

Discutir os resultados obtidos:

Como as taxas de vacinação e infectividade influenciam a dinâmica da epidemia?

Qual o impacto das taxas de morte e de casos importados?

Entrega Esperada
Código-fonte Java.

Comentários explicando a lógica de cada parte.

Gráfico(s) da evolução de S, I e R.

(Opcional) CSV com dados de simulação.

Relatório breve explicando a simulação e discutindo os resultados.
