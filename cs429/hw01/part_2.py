import pandas as pd
import numpy as np
import matplotlib.pyplot as plt


data = pd.read_csv('albacore_metal.dat', header=None)
correlation_matrix = data.iloc[:, 1:4].corr()

plt.figure(figsize=(6, 5))
cax = plt.imshow(correlation_matrix, cmap='coolwarm', vmin=-1, vmax=1)
plt.colorbar(cax)

for i in range(len(correlation_matrix.columns)):
    for j in range(len(correlation_matrix.columns)):
        plt.text(j, i, f'{correlation_matrix.iloc[i, j]:.4f}', ha='center', va='center', color='black')

plt.xticks(np.arange(len(correlation_matrix.columns)), ['Cd', 'Hg', 'Pb'], rotation=45)
plt.yticks(np.arange(len(correlation_matrix.columns)), ['Cd', 'Hg', 'Pb'])

plt.title('Correlation of Albacore Metal Content')

plt.tight_layout()
plt.show()