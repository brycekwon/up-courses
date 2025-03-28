import numpy as np


dataset = np.array([[1, 10], [2, 15], [3, 20], [4, 30]])
print("Original Dataset", dataset.shape)
print(dataset, end="\n\n")

X = dataset[:, 0]
Y = dataset[:, 1]
print("X and Y Vectors", X.shape, Y.shape)
print(X)
print(Y, end="\n\n")

Y_hat = 0.4 * np.square(X) + 2.13 * X + 23
E = Y - Y_hat

sum_E = np.sum(E)

print("Y_hat:", Y_hat)
print("E:", E)
print("E Sum:", sum_E)
