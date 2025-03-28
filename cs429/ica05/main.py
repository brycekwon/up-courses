import numpy as np
import math

def sigmoid(x):
    return 1 / (1 + math.exp(-x))

sigmoid_v = np.vectorize(sigmoid)


wih1 = np.asarray([[0.1, 0.2, 0.3],[0.2,0.3,0.4], [0.3, 0.4, 0.5]])
wh1h2 = np.asarray([[0.4, 0.5, 0.6],[0.5, 0.6, 0.7],[0.6, 0.7, 0.3],[0.4, 0.7, 0.6]])
wh2o =np.asarray([[0.2, 0.1],[0.4, 0.7],[0.2, 0.6],[0.2, 0.5]])
x = np.asarray([1, 0.1, 0.9])
#x = np.asarray([1, 0.9, 0.05])

yh1 = np.dot(x, wih1.transpose())
print(yh1)

z = sigmoid_v(yh1)
print(z)

yh2 = np.dot(z, wh1h2.transpose())
print(yh2)
