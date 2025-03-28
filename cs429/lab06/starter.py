import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

NUM_CENTROIDS=8

data = pd.read_csv('synthetic.txt', delimiter=' ', header=None, names=['X','Y'])

X = data['X']
Y = data['Y']
print(X)
print(Y)
#randomly generate centroid indexes
C = np.random.choice(np.shape(X)[0], NUM_CENTROIDS)
print(C)

plt.xlabel('x')
plt.xlabel('y')
plt.title('Clustering')

#plot data (black) and centroids (yellow)
plt.scatter(X,Y)
plt.scatter(X[C],Y[C],c='y')
plt.show()