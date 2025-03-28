import numpy as np
import pandas as pd
import matplotlib.pyplot as plt


########################################
# Data Preparation                     #
########################################

data_test = pd.read_csv("test.dat")
print("Original Test Dataset", data_test.shape)
print(data_test.values, end="\n\n")

rows = data_test.shape[0]
cols = data_test.shape[1]

X = data_test.values[:, 2]
Y = data_test.values[:, 1]
print("Original Test Vectors", X.shape, Y.shape)
print("X:", X, "\nY:", Y, end="\n\n")

X_norm = np.true_divide(X, np.max(X))
Y_norm = np.true_divide(Y, np.max(Y))
print("Normalized Test Vectors", X_norm.shape, Y_norm.shape)
print("X_norm:", X_norm, "\nY_norm:", Y_norm, end="\n\n")

########################################
# Model Preparation                    #
########################################

b1 = -1
b0 = -1
mu = 0.01
batch_size = 3
n_epochs = 50
print("Starting Variables")
print("b1:", b1, "\nb0:", b0, "\nlearning rate:", mu, "\nbatch size:", batch_size, "\nn_epochs:", n_epochs, end="\n\n")

plt.scatter(X_norm, Y_norm)
plt.plot(X_norm, b1 * X_norm + b0)
plt.title("LR of Country Income vs Birth Rate")
plt.xlabel("Income")
plt.ylabel("Births")

########################################
# Model Training                       #
########################################

for i in range(n_epochs):
    print(f"===== Epoch {i+1} =====")

    batch_idx = np.random.randint(rows, size=(batch_size))

    Y_hat = b1 * X_norm[batch_idx] + b0
    E = Y_hat - Y_norm[batch_idx]
    print("Y_hat:", Y_hat)
    print("E:", E)

    MSE = np.true_divide(np.sum(np.square(E)), batch_size)
    print("MSE:", MSE)

    b1 = b1 - mu * np.true_divide(np.sum(E * X_norm[batch_idx]), batch_size)
    b0 = b0 - mu * np.true_divide(np.sum(E), batch_size)
    print("b1:", b1)
    print("b0:", b0, end="\n\n")

    # plt.pause(0.05)
    # plt.plot(X_norm, b1 * X_norm + b0)

print("b0: " + str(b0) + " b1: " + str(b1) + " Error: " + str(MSE), end="\n\n\n")
# plt.show()


data_validate = pd.read_csv("validate.dat")
print("Original Validation Dataset", data_test.shape)
print(data_validate)

vX = data_validate.values[:, 2]
vY = data_validate.values[:, 1]
print("Original Validation Vectors", vX.shape, vY.shape)
print("vX:", vX, "\nvY:", vY, end="\n\n")

vX_norm = np.true_divide(vX, np.max(vX))
vY_norm = np.true_divide(vY, np.max(vY))
print("Normalized Validation Vectors", vX_norm.shape, vY_norm.shape)
print("X_norm:", vX_norm, "\nY_norm:", vY_norm, end="\n\n")

for i in range(len(vX_norm)):
    calculated = b1 * vX_norm[i] + b0
    expected = vY_norm[i]

    calculated_str = f"{calculated:.4f}"
    expected_str = f"{expected:.4f}"

    space_between = 10 - len(calculated_str)

    print(f"Calculated: {calculated_str}" + " " * space_between + f"Expected: {expected_str}")

Y_hat_test = b1 * vX_norm + b0
E_test = Y_hat_test - vY_norm
MSE = np.true_divide(np.sum(np.square(E_test)), data_validate.shape[0])
print("b0: " + str(b0) + " b1: " + str(b1) + " MSE: " + str(MSE))
