import numpy as np

np.set_printoptions(linewidth=np.inf, precision=4, suppress=True)
np.random.seed(2222)

def sigmoid(x):
    return 1 / (1 + np.exp(-x))

MU = 0.1

X = np.array([1, 0.1, 0.9])

W_IN_H1 = np.array([
    [0.1, 0.2, 0.3], #bias
    [0.2, 0.3, 0.4],
    [0.3, 0.4, 0.5],
])

W_H1_H2 = np.array([
    [0.4, 0.5, 0.6], #bias
    [0.5, 0.6, 0.7],
    [0.6, 0.7, 0.3],
    [0.4, 0.7, 0.6],
])

W_H2_OT = np.array([
    [0.2, 0.1], #bias
    [0.4, 0.7],
    [0.2, 0.6],
    [0.2, 0.5],
])

print(W_H2_OT.shape, X.shape)
print(X)

# FORWARDS #
print("=== FORWARD ===")
# Y_H1 = sigmoid(np.dot(X.T, W_IN_H1))
Y_H1 = sigmoid(np.dot(W_IN_H1.T, X))
print(Y_H1)

bY_H1 = np.append([1], Y_H1)
Y_H2 = sigmoid(np.dot(W_H1_H2.T, bY_H1))
print(Y_H2)

bY_H2 = np.append([1], Y_H2)
Y_OT = sigmoid(np.dot(W_H2_OT.T, bY_H2))
print(Y_OT)

# BACKWARDS #
print("\n\n=== BACKWARD ===")
ERR = np.zeros(2)
ERR[0] = 1

delta_3 = (ERR - Y_OT) * Y_OT * (1 - Y_OT)
print(delta_3)

delta_2 = bY_H2 * (1 - bY_H2) * np.dot(W_H2_OT, delta_3)
print(delta_2)

delta_1 = bY_H1 * (1 - bY_H1) * np.dot(W_H1_H2, delta_2[1:])
print(delta_1)

print("")

W_H2_OT -= np.array([bY_H2 * value for value in (MU * delta_3)]).T
print(W_H2_OT)

W_H1_H2 -= np.array([bY_H1 * value for value in (MU * delta_2[1:])]).T
print(W_H1_H2)

W_IN_H1 -= np.array([X * value for value in (MU * delta_1[1:])]).T
print(W_IN_H1)
