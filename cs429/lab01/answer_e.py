import numpy as np


dataset = np.random.rand(2,4)
print("Original Dataset", dataset.shape)
print(dataset, end="\n\n")

normalized = np.true_divide(dataset, np.max(dataset))
print("Normalized Dataset", normalized.shape)
print(normalized, end="\n\n")

vector = np.array([0.1, 0.2, 0.3, 0.4])
print("Original Vector", vector.shape)
print(vector, end="\n\n")

dot_prod = np.dot(normalized, vector)
print("Dot Product", dot_prod.shape)
print(dot_prod, end="\n\n")
