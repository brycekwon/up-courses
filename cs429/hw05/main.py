import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

np.random.seed(2222)


K_VALUES = [2, 3, 4, 5, 6]


def kmeans(X: np.ndarray, k: int = 4, n: int = 20) -> tuple[np.ndarray, np.ndarray, np.ndarray]:
    entropies = np.zeros(n)
    centroids = X[np.random.choice(X.shape[0], k, replace=False)]
    for i in range(n):
        labels = np.argmin(np.linalg.norm(X[:, np.newaxis] - centroids, axis=2), axis=1)
        centroids = np.array([X[labels == j].mean(axis=0) for j in range(k)])
        entropies[i] = cluster_entropy(labels, k)
    return centroids, labels, entropies


# H = -Σ(p_i * log2(p_i)) for i = 1 to k
# https://en.wikipedia.org/wiki/Entropy_(information_theory)
def cluster_entropy(labels: np.ndarray, k: int) -> float:
    entropy = 0.0
    for i in range(k):
        size = np.sum(labels == i)
        if size > 0:
            p = size / len(labels)
            entropy -= p * np.log2(p)
    return entropy


if __name__ == "__main__":
    data_finland = pd.read_csv("./ClusteringData/FinlandWhole.txt", header=None).to_numpy()
    data_joensuu = pd.read_csv("./ClusteringData/JoensuuRegion.txt", header=None).to_numpy()

    entropies_finland = np.zeros(len(K_VALUES))
    entropies_joensuu = np.zeros(len(K_VALUES))
    for i, k in enumerate(K_VALUES):
        plt.figure(figsize=(10, 10), dpi=200)

        centroids_finland, labels_finland, entropy_finland = kmeans(data_finland, k)
        centroids_joensuu, labels_joensuu, entropy_joensuu = kmeans(data_joensuu, k)
        entropies_finland[i] = cluster_entropy(labels_finland, k)
        entropies_joensuu[i] = cluster_entropy(labels_joensuu, k)

        plt.subplot(2, 2, 1)
        plt.title(f"Finland Data Clustering (Modified: k={k}, n={100})")
        plt.scatter(data_finland[:, 0], data_finland[:, 1], s=10, c=labels_finland)
        plt.scatter(centroids_finland[:, 0], centroids_finland[:, 1], s=10, c="r")

        plt.subplot(2, 2, 2)
        plt.title(f"Joensuu Data Clustering (Modified: k={k}, n={100})")
        plt.scatter(data_joensuu[:, 0], data_joensuu[:, 1], s=10, c=labels_joensuu)
        plt.scatter(centroids_joensuu[:, 0], centroids_joensuu[:, 1], s=10, c="r")

        plt.subplot(2, 1, 2)
        plt.title("Iteration v Entropy")
        plt.xlabel("Iterations")
        plt.ylabel("Mean Clustering Entropy")
        plt.plot(range(entropy_finland.shape[0]), entropy_finland, label="Finland", marker="o", c="g")
        plt.plot(range(entropy_joensuu.shape[0]), entropy_joensuu, label="Joensuu", marker="o", c="b")
        plt.xticks(ticks=range(0, entropy_finland.shape[0], 1))
        plt.legend()
        plt.grid()

        plt.tight_layout()
        plt.savefig(f"results/kmeans_{k}.png")

    plt.figure(figsize=(12, 6), dpi=200)
    plt.title("Number of Clusters v Mean Clustering Entropy")
    plt.xlabel("Number of Clusters (k)")
    plt.ylabel("Mean Clustering Entropy")
    plt.xticks(ticks=range(min(K_VALUES), max(K_VALUES)+1, 1))
    plt.plot(K_VALUES, entropies_finland, label='Finland', marker="o", c="g")
    plt.plot(K_VALUES, entropies_joensuu, label='Joensuu', marker="o", c="b")
    plt.legend()
    plt.grid()
    plt.tight_layout()
    plt.savefig("results/entropy.png")