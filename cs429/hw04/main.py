import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.decomposition import PCA


np.random.seed(2222)

data = pd.read_table("./input/histograms.txt", sep=" ", header=None)

L = data[0].str.extract(r'([-\d.]+)').astype(float).to_numpy().flatten()
X = data.drop(columns=[0, 769, 770]).to_numpy()
X = (X- X.min(axis=0)) / (X.max(axis=0) - X.min(axis=0))

idx = np.random.permutation(len(L))
L = L[idx]
X = X[idx]

pca = PCA()
pca.fit(X)
explained_variance = pca.explained_variance_ratio_

sorted_indicies = np.argsort(explained_variance)[::-1]
sorted_features = X[:, sorted_indicies]

plt.figure(figsize=(10, 6))
plt.plot(np.cumsum(explained_variance[sorted_indicies]), marker='o')
plt.title('Cumulative Explained Variance by PCA Components')
plt.xlabel('Number of Components')
plt.ylabel('Cumulative Explained Variance')
plt.grid()
plt.show()

n_components = np.argmax(np.cumsum(explained_variance) >= 0.99) + 1
print(f'Number of components to keep for 99% variance: {n_components}')
X = sorted_features[:, :n_components]

print(X.shape)

split = int(0.8 * len(L))

train_L, test_L = L[:split], L[split:]
train_X, test_X = X[:split], X[split:]

with open('./input/train.txt', 'w') as f:
    for i, l in enumerate(train_L):
        f.write(f"{int(l)} ")
        for j, v in enumerate(train_X[i]):
            f.write(f"{j+1}:{v} ")
        f.write("\n")

with open('./input/test.txt', 'w') as f:
    for i, l in enumerate(test_L):
        f.write(f"{int(l)} ")
        for j, v in enumerate(test_X[i]):
            f.write(f"{j+1}:{v} ")
        f.write("\n")
