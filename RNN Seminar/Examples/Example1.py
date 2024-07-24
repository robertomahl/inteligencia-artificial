import tensorflow as tf
import matplotlib.pyplot as plt
import pandas as pd
from pathlib import Path
import numpy as np


# Garante a reprodutibilidade dos resultados
tf.random.set_seed(42)


# Faz o download do dataset e extrai os arquivos
tf.keras.utils.get_file(
    "ridership.tgz",
    "https://github.com/ageron/data/raw/main/ridership.tgz",
    cache_dir=".",
    extract=True
)


# Carrega o dataset e faz o preprocessamento
path = Path("datasets/ridership/CTA_-_Ridership_-_Daily_Boarding_Totals.csv")
df = pd.read_csv(path, parse_dates=["service_date"])
df.columns = ["date", "day_type", "bus", "rail", "total"]  # Nomes mais curtos para as colunas
df = df.sort_values("date").set_index("date")
df = df.drop("total", axis=1)  # Remove a coluna 'total' pois é apenas a soma de 'bus' e 'rail'
df = df.drop_duplicates()  # Remove meses duplicados 


# Exibe os dados de 3 meses de 2019
df["2019-03":"2019-05"].plot(grid=True, marker=".", figsize=(8, 3.5))
plt.show()


# Separa os dados em conjuntos de treino, validação e teste
# Dividimos os dados de 'rail' (metrô) por 1e6 para normalizar os valores
rail_train = df["rail"]["2016-01":"2018-12"] / 1e6
rail_valid = df["rail"]["2019-01":"2019-05"] / 1e6
rail_test = df["rail"]["2019-06":] / 1e6


# Separa os dados em sequências de 56 dias
seq_length = 56
train_ds = tf.keras.utils.timeseries_dataset_from_array(
    rail_train.to_numpy(),
    targets=rail_train[seq_length:],  # Alvos a partir do índice seq_length
    sequence_length=seq_length,
    batch_size=32,
    shuffle=True,
    seed=42
)
valid_ds = tf.keras.utils.timeseries_dataset_from_array(
    rail_valid.to_numpy(),
    targets=rail_valid[seq_length:],  # Alvos a partir do índice seq_length
    sequence_length=seq_length,
    batch_size=32
)


# ----------- Modelo de RNN simples ------------


# Cria um modelo de RNN simples
tf.random.set_seed(42)  # extra code – ensures reproducibility
univar_model = tf.keras.Sequential([
    tf.keras.layers.SimpleRNN(32, input_shape=[None, 1]),
    tf.keras.layers.Dense(1)  # no activation function by default
])

# Função para treinar e avaliar o modelo
def fit_and_evaluate(model, train_set, valid_set, learning_rate, epochs=500):
    # Callback para parar o treinamento cedo se não houver melhora na validação
    early_stopping_cb = tf.keras.callbacks.EarlyStopping(
        monitor="val_mae", patience=50, restore_best_weights=True)
    
    # Otimizador com taxa de aprendizado e momentum
    opt = tf.keras.optimizers.SGD(learning_rate=learning_rate, momentum=0.9)
    
    # Compila o modelo com a perda de Huber e métrica MAE (Erro médio absoluto)
    model.compile(loss=tf.keras.losses.Huber(), optimizer=opt, metrics=["mae"])
    
    # Treina o modelo
    model.fit(train_set, validation_data=valid_set, epochs=epochs,
                        callbacks=[early_stopping_cb])
    
    # Avalia o modelo no conjunto de validação
    valid_loss, valid_mae = model.evaluate(valid_set)
    
    # Retorna o MAE em uma escala maior (multiplicado por 1e6)
    return valid_mae * 1e6


# Avalia a performance do modelo simples
avaliacao = fit_and_evaluate(univar_model, train_ds, valid_ds, learning_rate=0.05)
print("\n\nErro absoluto médio do modelo simples:", avaliacao)


# ----------- Modelo de RNN profunda ------------


# Cria um modelo de RNN profunda
deep_model = tf.keras.Sequential([
    tf.keras.layers.SimpleRNN(32, return_sequences=True, input_shape=[None, 1]),
    tf.keras.layers.SimpleRNN(32, return_sequences=True),
    tf.keras.layers.SimpleRNN(32),
    tf.keras.layers.Dense(1)
])


# Avalia a performance do modelo profundo
avaliacao_prof = fit_and_evaluate(deep_model, train_ds, valid_ds, learning_rate=0.01)
print("\n\nErro absoluto médio do modelo profundo:", avaliacao_prof)


# ----------- Prevendo vários passos à frente com modelo simples ------------


# Cria uma sequência inicial de 56 dias dos dados de validação
# np.newaxis é usado para adicionar novas dimensões para compatibilidade com a entrada do modelo
X = rail_valid.to_numpy()[np.newaxis, :seq_length, np.newaxis]


# Gera previsões para os próximos 14 dias
for step_ahead in range(14):
    # Faz a previsão para um passo à frente
    y_pred_one = univar_model.predict(X)
  
    # Adiciona a previsão à sequência X para a próxima previsão
    X = np.concatenate([X, y_pred_one.reshape(1, 1, 1)], axis=1)


# Converte as previsões para uma série Pandas com as datas corretas
Y_pred = pd.Series(X[0, -14:, 0],
                   index=pd.date_range("2019-02-26", "2019-03-11"))


# Plota os valores reais e as previsões
fig, ax = plt.subplots(figsize=(8, 3.5))
(rail_valid * 1e6)["2019-02-01":"2019-03-11"].plot(
    label="True", marker=".", ax=ax)  # Plota os valores reais
(Y_pred * 1e6).plot(
    label="Predictions", grid=True, marker="x", color="r", ax=ax)  # Plota as previsões
ax.vlines("2019-02-25", 0, 1e6, color="k", linestyle="--", label="Today")  # Linha vertical indicando a data atual
ax.set_ylim([200_000, 800_000])  # Define os limites do eixo y
plt.legend(loc="center left")  # Adiciona a legenda
plt.show()  # Exibe o gráfico


# ----------- Prevendo vários passos à frente com modelo simples ------------


# Cria uma sequência inicial de 56 dias dos dados de validação
# np.newaxis é usado para adicionar novas dimensões para compatibilidade com a entrada do modelo
X = rail_valid.to_numpy()[np.newaxis, :seq_length, np.newaxis]


# Gera previsões para os próximos 14 dias
for step_ahead in range(14):
    # Faz a previsão para um passo à frente
    y_pred_one = deep_model.predict(X)
  
    # Adiciona a previsão à sequência X para a próxima previsão
    X = np.concatenate([X, y_pred_one.reshape(1, 1, 1)], axis=1)


# Converte as previsões para uma série Pandas com as datas corretas
Y_pred = pd.Series(X[0, -14:, 0],
                   index=pd.date_range("2019-02-26", "2019-03-11"))


# Plota os valores reais e as previsões
fig, ax = plt.subplots(figsize=(8, 3.5))
(rail_valid * 1e6)["2019-02-01":"2019-03-11"].plot(
    label="True", marker=".", ax=ax)  # Plota os valores reais
(Y_pred * 1e6).plot(
    label="Predictions", grid=True, marker="x", color="r", ax=ax)  # Plota as previsões
ax.vlines("2019-02-25", 0, 1e6, color="k", linestyle="--", label="Today")  # Linha vertical indicando a data atual
ax.set_ylim([200_000, 800_000])  # Define os limites do eixo y
plt.legend(loc="center left")  # Adiciona a legenda
plt.show()  # Exibe o gráfico
