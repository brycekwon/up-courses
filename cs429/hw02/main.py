import os
from concurrent.futures import ThreadPoolExecutor

import cv2
import numpy as np


def derive_color_histogram(filename: str) -> np.ndarray:
    image = cv2.imread(filename, cv2.IMREAD_COLOR)

    hist_r = cv2.calcHist([image], [0], None, [256], [0, 256]).flatten()
    hist_g = cv2.calcHist([image], [1], None, [256], [0, 256]).flatten()
    hist_b = cv2.calcHist([image], [2], None, [256], [0, 256]).flatten()

    return np.concatenate((hist_r, hist_g, hist_b), axis=0)


def process_images(images: str, output: str, pre: str) -> None:
    with ThreadPoolExecutor(max_workers=os.cpu_count()) as executor:
        results = executor.map(
            lambda filename: derive_color_histogram(
                os.path.join(images, filename)
            ), 
            os.listdir(images)
        )

    with open(output, 'a') as output:
        for hist in results:
            output.write(pre + ','.join(map(str, hist)) + '\n')


def parser(output: str) -> tuple[np.ndarray, np.ndarray]:
    with open(output, 'r') as file:
        lines = file.readlines()

        labels = np.zeros(len(lines))
        histograms = np.zeros((len(lines), 256 * 3))

        for i, line in enumerate(lines):
            label, data = line.strip().split(":", 1)

            labels[i] = int(label)
            histograms[i] = np.array(list(map(float, data.split(','))))

    return labels, histograms


def train_perceptron(X, L, N, mu):
    rows = X.shape[0]
    cols = X.shape[1]
    W = np.random.random(cols + 1)

    CA = np.ones(N, dtype=float) # classification accuracy
    TE = np.ones(N, dtype=float) # training error

    for i in range(N):
        acc = 0
        for j in range(rows):
            change = W[0] + np.sum(np.multiply(X[j, 0:], W[1:]))
            prediction = 1 if change >= 0 else -1

            if prediction == L[j]:
                acc += 1
            else:
                E = prediction - L[j]
                X_t = np.concatenate(([1], X[j, 0:]))
                W_t = np.multiply(np.multiply(E, X_t), mu)
                W = np.subtract(W, W_t)

        CA[i] = acc / rows * 100
        TE[i] = (rows - acc) / rows * 100

    return {
        "W": W,
        "CA": CA,
        "TE": TE,
    }


def test_perceptron(weights: np.ndarray, hist: np.ndarray) -> int:
    hist /= hist.max()

    P = weights[0] + np.sum(np.multiply(hist[0:], weights[1:]))
    if P >= 0:
        # aurora is present
        return 1
    else:
        # aurora is not present
        return -1


def use_perceptron(weights: np.ndarray, filename: str) -> int:
    X = derive_color_histogram(filename)
    X /= X.max()

    P = weights[0] + np.sum(np.multiply(X[0:], weights[1:]))
    if P >= 0:
        # aurora is present
        return 1
    else:
        # aurora is not present
        return 0


if __name__ == "__main__":
    ###########################################################################
    # Preprocessing Data                                                      #
    ###########################################################################

    if os.path.exists("output.txt"):
        os.remove("output.txt")

    process_images("./no_aurora", "output.txt", "-1:")
    process_images("./yes_aurora", "output.txt", "1:")

    L, X = parser("output.txt")
    X_norm = np.ones(X.shape)
    for i, e in enumerate(X):
        X_norm[i] = e / e.max()

    # shuffle data (IMPORTANT)
    idx = np.random.permutation(X_norm.shape[0])
    X_norm = X_norm[idx]
    L = L[idx]

    # partition data roughly to 80% training and 20% test
    partition = int(X_norm.shape[0] * 0.8)
    X_norm, X_norm_test = X_norm[:partition, :], X_norm[partition:, :]
    L, L_test = L[:partition], L[partition:]

    ###########################################################################
    # Training Data                                                           #
    ###########################################################################

    Z = train_perceptron(X_norm, L, 20, 0.01)

    print(Z["CA"])
    print(Z["TE"])

    ###########################################################################
    # Testing Data                                                            #
    ###########################################################################

    K = np.ones(L_test.shape)
    for i, e in enumerate(X_norm_test):
        K[i] = (test_perceptron(Z["W"], e) == L_test[i])
    print(f"{np.sum(K == True)} correct\t{np.sum(K == False)} wrong")

    ###########################################################################
    # Using Data                                                              #
    ###########################################################################

    # K = np.zeros(len(os.listdir("./no_aurora")))
    # with ThreadPoolExecutor(max_workers=os.cpu_count()) as executor:
    #     results = executor.map(lambda filename: use_perceptron(Z["W"], os.path.join("./no_aurora", filename)), os.listdir("./no_aurora"))
    #     for i, x in enumerate(results):
    #         K[i] = x
    # print(f"{np.sum(K == 0)} / {len(os.listdir("./no_aurora"))} ({np.sum(K == 0) / len(os.listdir("./no_aurora")) * 100}%)")

    # K = np.zeros(len(os.listdir("./yes_aurora")))
    # with ThreadPoolExecutor(max_workers=os.cpu_count()) as executor:
    #     results = executor.map(lambda filename: use_perceptron(Z["W"], os.path.join("./yes_aurora", filename)), os.listdir("./yes_aurora"))
    #     for i, x in enumerate(results):
    #         K[i] = x
    # print(f"{np.sum(K == 1)} / {len(os.listdir("./yes_aurora"))} ({np.sum(K == 1) / len(os.listdir("./yes_aurora")) * 100}%)")

    # K = np.zeros(len(os.listdir("./not_known")), dtype=int)
    # for i, e in enumerate(os.listdir("./not_known")):
    #     K[i] = (use_perceptron(Z["W"], os.path.join("./not_known/" + e)))

    # with open("not_known.txt", "w") as f:
    #     f.write(','.join(map(str, K)) + '\n')
