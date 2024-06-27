# Redes Neurais Recorrentes

## Vídeo

- Utilização em dados sequênciais
    - Temperatura ao longo do tempo
- Prever a pŕoxima ação
    - Em vídeos, previsão de frame atual com base nos frames anteriores
- Processamento de linguagem natural
    - Previsão da próxima palavra em um texto
    - Tradução automática (text to speech)
    - Geração de poemas
- Geração de legendas em vídeos
- Séries temporais (time-series)
    - Preço de ações na bolsa de valores
    - Temperatura
    - Crescimento populacional
    - Nível de poluição

"Para entender o final de uma frase, você precisa saber o que foi dito antes."

- Redes Neurais tradicionais não armazenam informação no tempo
- Redes Neurais Recorrentes utilizam loops que permitem que a informação persista -- passa a informação à frente e manda para ele mesmo
- Desenrrolar a rede neural -- recursão 
- t número de neurônios
- Múltiplas cópias de si mesmo -- implica em processamento mais demorado

## Livro

- Podem trabalhar com inputs de variados, como frases, documentos ou áudio
- Não são as únicas que podem lidar com dados sequenciais
    - Para sequências menores, redes densas tradicionais podem ser o suficiente
    - Para sequências muito longas, convolucionais podem ser adequadas

### Neurônios recorrentes e camadas

- Se parecem muito com redes feedforward, exceto que também têm conexões apontando para trás
- Cada time step pode ser chamado de frame (cada execução do loop)
- A cada frame, o neurônio recorrente recebe uma entrada x(t) bem como a saída do último passo
    - Como não há saída anterior para a primeira execução, geralmente ela é setada como 0
    - [Imagem]
- Ambos entrada e saída são vetores, ao passo que a saída era um escalar antes
- Cada neurônio recorrente tem dois vetores de pesos, um para as entradas e outro para as saídas dos passos anteriores
- ReLU ou hyperbolic tangent (tanh) como funções de ativação

### Células de memória

- Como a saída de um neurônio recorrente em um dado tempo t é função das entradas dos frames anteriores, pode-se dizer que ela tem um tipo de memória.
- A parte de uma rede neural que preserva algum estado através dos frames é chamada de célula de memória (ou apenas célula)
- Um único neurônio recorrente, ou uma camada deles, é uma célula muito básica
- O estado de um neurônio é função de alguns inputs naquele frame e do estado no frame anterior h(t) = f(x(t), h(t–1)).

### Input and Output Sequences

- Uma RNN pode tomar uma sequência de inputs e retornar uma sequência de outputs
    - Útil para prever séries temporais
        - Dado consumo energético de x dias, prever o consumo 
- Dada uma sequência de inputs, pode-se também ignorar todos os outputs exceto o último
    - Alimentada uma RNN com uma sequência de palavras de review, receber como saída um score
- Alternativamente, pode-se alimentar a rede sempre com o mesmo input e ter como resultado uma sequência
    - Input: imagem. Output: legenda
- Por último, existe a abordagem encoder-decoder, em que é feita primeira a transição sequence-vector pelo encoder e depois a vector-sequence pelo decoder
    - Abordagem útil para traduções, especialmente porque as últimas palavras de uma sequência podem afetar as primeiras de uma tradução -- o que não seria considerado numa abordagem sequence-sequence

### Forecasting a Time Series

- Time-series: dados com valores em diferentes tempos, geralmente em intervalos regulares
- Multivariate time series: time series com múltiplos valores em cada tempo
- Univariate time series: time series com um único valor em cada tempo
- A atividade mais comum com time-series é a *predição* do próximo valor, mas outras atividades também podem ser feitas:
    - Imputação
    - Classificação
    - Detecção de anomalias
- Naive forecasting: quando a predição do próximo valor é uma cópia de um valor do passado
    - Produz resultado satisfatório quando a sazonalidade é muito forte


## Seminário

- Bias geralmente começa com 0
- Os pesos geralmente começam com valores pequenos e são ajustados durante o treinamento


#### Rede Neural Densa

Totalmente conectada ou feedforward, cada neurônio em uma camada está conectado a cada neurônio da camada seguinte.

#### Neurônio

1. Recebe uma série de entradas
2. Cada entrada é associada a um peso que determina sua importância (pesos são ajustados durante treinamento)
3. É calculada a soma ponderada das entradas e pesos, adicionando um termo de viés (bias) para ajustar a saída
4. A soma ponderada é passada a uma função de ativação que introduz não-linearidade na rede e decide se o neurônio deve "disparar" (produzir uma saída significativa)

## Dúvidas

Função de ativação?
Rede neural densa?