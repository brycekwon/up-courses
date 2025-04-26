import numpy as np
import matplotlib.pyplot as plt
# from scipy import signal
# import pandas as pd

from sklearn.decomposition import FastICA, PCA

X = np.loadtxt("SensorMeasurements.csv", delimiter=",",dtype=float)

print(np.shape(X))


plt.figure()

plt.subplot(3,1,1)
plt.scatter(np.arange(2000), X[:,2], c='r')
plt.scatter(np.arange(2000), X[:,3], c='r')
plt.scatter(np.arange(2000), X[:,4], c='r')

ica = FastICA(n_components=4)
I_ = ica.fit_transform(X)


plt.subplot(3, 1, 2)
plt.plot(np.arange(2000), I_[:,0], c='b', linewidth=0.75)
plt.plot(np.arange(2000), I_[:,1], c='y', linewidth=0.75)
plt.plot(np.arange(2000), I_[:,2], c='c', linewidth=0.75)
plt.plot(np.arange(2000), I_[:,3], c='m', linewidth=0.75)
# plt.plot(np.arange(2000), I_[:,4], c='k')

pca = PCA(n_components=4)
P_ = pca.fit_transform(X)

plt.subplot(3, 1, 3)
plt.plot(np.arange(2000), P_[:,0], c='b', linewidth=0.75)
plt.plot(np.arange(2000), P_[:,1], c='y', linewidth=0.75)
plt.plot(np.arange(2000), P_[:,2], c='c', linewidth=0.75)
plt.plot(np.arange(2000), P_[:,3], c='m', linewidth=0.75)

plt.show()
