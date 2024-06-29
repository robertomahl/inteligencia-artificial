np.random.seed(0)

# Define the model architecture
model = Sequential()
model.add(SimpleRNN(units=32, input_shape=(None, 1)))
model.add(Dense(units=1, activation='sigmoid'))

# Compile the model
model.compile(loss='binary_crossentropy', optimizer='adam', metrics=['accuracy'])
# Train the model
model.fit(x_train, y_train, epochs=10, batch_size=32)
# Evaluate the model
loss, accuracy = model.evaluate(x_test, y_test)
# Make predictions using the model
predictions = model.predict(x_new)
