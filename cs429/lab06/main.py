from scipy.cluster.vq import whiten, kmeans

X = [
    [.5, .5],
    [1, 1],
    [1, 1.5],
    [1.5, 1],
    [1, 0.5],
    [3, 3],
    [3, 3.5],
    [3.5, 3],
]

Y = whiten(X)

cb, _ = kmeans(Y, 2, iter=1)
print(cb)
