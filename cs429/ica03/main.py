# LINEAR REGRESSION


import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

########################################
# Preparation                          #
########################################

data = pd.read_csv("birthrate.dat")

rows = data.shape[0]
cols = data.shape[1]

# for consistency, make vectors all upper-case and scalars lowercase
data = data.values
X = data[:,2]
Y = data[:,1]

# pseudorandom values for model training
a1 = 1
a0 = 1
lr = 0.01 # learning rate
batch_size = 4

# normalize data vectors
X_norm = np.true_divide(X, np.max(X))
Y_norm = np.true_divide(Y, np.max(Y))


########################################
# Model                                #
########################################


# Y_hat = a1 * X_norm + a0
# E = Y_hat - Y_norm
#
# MSE = np.true_divide(np.sum(np.square(E)), rows)
#
# a0 = a0 - lr * np.true_divide(np.sum(E), rows)
# a1 = a1 - lr * np.true_divide(np.sum(E * X_norm), rows)

plt.scatter(X_norm, Y_norm)
plt.plot(X_norm, a1 * X_norm + a0)
plt.title("LR of Country Income vs Birth Rate")
plt.xlabel("Income")
plt.ylabel("Births")

for i in np.arange(50):
    batch_idx = np.random.randint(rows, size=(batch_size))

    Y_hat = a1 * X_norm[batch_idx] + a0
    E = Y_hat - Y_norm[batch_idx]

    MSE = np.true_divide(np.sum(np.square(E)), batch_size)
    # print(MSE)

    a0 = a0 - lr * np.true_divide(np.sum(E), batch_size)
    a1 = a1 - lr * np.true_divide(np.sum(E * X_norm[batch_idx]), batch_size)

    plt.pause(0.005)
    plt.plot(X_norm, a1 * X_norm + a0)

plt.show()
