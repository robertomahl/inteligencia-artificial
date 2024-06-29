# h(inicial) = 0

# 1. x(0) e h(inicial) são passados para o neurônio
# 2. a soma ponderada é calculada e adicionada ao bias
# 3. a função de ativação é aplicada ao resultado -- tanh por padrão (hyperbolic tangent)
# 4. o resultado é y(0) e h(0)
# 5. h(0) é passado para a próxima iteração junto a x(1)s

# Por padrão, camadas recorrentes no Keras só retornam o estado final da sequência
# Para retornar uma saída por frame, deve-se configurar com return_sequences=True

# sequence-to-vector. Como há um único neurônio, a saída é um vetor de dimensão 1

model = tf.keras.Sequential([
    # [batch size, time steps, dimensionality]
    # batch size: 
    # time steps: None = any number of time steps
    # dimensionality: 1 = univariate time series
    tf.keras.layers.SimpleRNN(32, input_shape=[None, 1])
    tf.keras.layers.Dense(1) # no activation function by default
])

deep_model = tf.keras.Sequential([
    tf.keras.layers.SimpleRNN(32, return_sequences=True, input_shape=[None, 1]),
    tf.keras.layers.SimpleRNN(32, return_sequences=True),
    tf.keras.layers.SimpleRNN(32),
    tf.keras.layers.Dense(1)
])

