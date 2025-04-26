# from mpl_toolkits import mplot3d
import numpy as np
import matplotlib.pyplot as plt
# from mpl_toolkits.mplot3d import Axes3D
# import pandas as pd
from sklearn import decomposition

data = np.loadtxt("PCA_data.csv", delimiter=",", dtype=float)

print(np.shape(data))
X = data[:,0]
Y = data[:,1]
Z = data[:,2]

fig = plt.figure()
ax = plt.axes(projection ='3d')

ax.set_xlabel('X')
ax.set_ylabel('Y')
ax.set_zlabel('Z')
ax.scatter(X,Y,Z, c='r', edgecolor='k')

pca_model = decomposition.PCA(n_components=3)

variance = pca_model.fit(data)
print(pca_model.explained_variance_)
print(pca_model.explained_variance_ratio_)
print(pca_model.explained_variance_ratio_.cumsum())

pca_data = pca_model.transform(data)
print(pca_data)


ax.scatter(pca_data[:, 0], pca_data[:, 1], pca_data[:, 2], c='b', edgecolor='y')
ax.scatter(pca_data[:, 0], pca_data[:, 1], zs=-6, zdir='z', label='data in x,y')
ax.scatter(pca_data[:, 0], pca_data[:, 2], zs=12, zdir='y', label='data in x,z')
ax.scatter(pca_data[:, 1], pca_data[:, 2], zs=12, zdir='x', label='data in y,z')

plt.show()
