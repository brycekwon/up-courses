import numpy as np
import pandas as pd

np.set_printoptions(linewidth=np.inf, precision=2, suppress=True)


# CHANGE THESE PARAMETERS #
DEFAULT_PARITION = 0.2 # 20% testing, 80% training
DEFAULT_EPOCHS = 30
DEFAULT_LEARNING_RATE = 0.5

INPUT_LAYER_SIZE = 16
HIDDEN_LAYER_1_SIZE = 20
HIDDEN_LAYER_2_SIZE = 20
OUTPUT_LAYER_SIZE = 26


def sigmoid(X: np.ndarray, W: np.ndarray) -> np.ndarray:
    return 1 / (1 + np.exp(-np.dot(W.T, X)))


def train_model(X: np.ndarray, L: np.ndarray, mu: float, epochs: int) -> tuple[np.ndarray, np.ndarray, np.ndarray, np.ndarray]:
    # normalize features between 0-1 (rescaled by feature range, columnar)
    X = (X - X.min(axis=0)) / (X.max(axis=0) - X.min(axis=0))

    # define neurons as a matrix of vectors (# perceptrons, # weights per perceptron)
    W_IN_H1 = np.random.uniform(-1, 1, (INPUT_LAYER_SIZE + 1, HIDDEN_LAYER_1_SIZE))
    W_H1_H2 = np.random.uniform(-1, 1, (HIDDEN_LAYER_1_SIZE + 1, HIDDEN_LAYER_2_SIZE))
    W_H2_OT = np.random.uniform(-1, 1, (HIDDEN_LAYER_2_SIZE + 1, OUTPUT_LAYER_SIZE))

    training_acc = np.ones(epochs)

    for run in range(epochs):
        print(f"\n========== EPOCH RUN {run+1} ({len(X)} inputs) ==========")
        acc = 0

        for i in range(X.shape[0]):
            # add an extra node with value 1 for bias (all except output layer)
            b_inputs = np.append([1], X[i, :])
            b_H1 = np.append([1], sigmoid(b_inputs, W_IN_H1))
            b_H2 = np.append([1], sigmoid(b_H1, W_H1_H2))
            outputs = sigmoid(b_H2, W_H2_OT)

            # predicted value is determined through softmax
            if outputs.argmax() == L[i]-1:
                acc += 1

            expected = np.zeros(OUTPUT_LAYER_SIZE, dtype=int)
            expected[L[i]-1] = 1

            # the first index of the deltas for hidden/input layers are for the bias and
            # can be ignored since they will always be 0.
            delta_3 = (expected - outputs) * outputs * (1 - outputs)
            delta_2 = b_H2 * (1 - b_H2) * np.dot(W_H2_OT, delta_3)
            delta_1 = b_H1 * (1 - b_H1) * np.dot(W_H1_H2, delta_2[1:])

            W_H2_OT += b_H2[:, np.newaxis] * (mu * delta_3)
            W_H1_H2 += b_H1[:, np.newaxis] * (mu * delta_2[1:])
            W_IN_H1 += b_inputs[:, np.newaxis] * (mu * delta_1[1:])

        training_acc[run] = acc
        print(f"{acc} / {X.shape[0]} Training Accuracy ({round(acc / X.shape[0] * 100, 2)}%)")

    return W_IN_H1, W_H1_H2, W_H2_OT, training_acc


def test_model(X: np.ndarray, L: np.ndarray, W1: np.ndarray, W2: np.ndarray, W3: np.ndarray) -> tuple[np.ndarray, int]:
    X = (X - X.min(axis=0)) / (X.max(axis=0) - X.min(axis=0))

    conf_matrix = np.zeros((26, 26), dtype=int)
    acc = 0

    for i in range(X.shape[0]):
        b_inputs = np.append([1], X[i, :])
        b_H1 = np.append([1], sigmoid(b_inputs, W1))
        b_H2 = np.append([1], sigmoid(b_H1, W2))
        outputs = sigmoid(b_H2, W3)

        if outputs.argmax() == L[i]-1:
            acc += 1
        conf_matrix[L[i]-1][outputs.argmax()] += 1

    print(f"{acc} / {X.shape[0]} Accuracy ({round(acc / X.shape[0] * 100, 2)}%)")

    return conf_matrix, acc


def count_letters(arr: np.ndarray) -> np.ndarray:
    count_array = np.zeros(26, dtype=int)
    for number in arr:
        count_array[number - 1] += 1
    return count_array


def percent_letters(arr: np.ndarray) -> np.ndarray:
    count_array = np.zeros(26, dtype=float)
    for number in arr:
        count_array[number - 1] += 1
    return (count_array / arr.shape[0]) * 100


if __name__ == "__main__":
    data = pd.read_csv("./LetterData.csv", header=None).to_numpy(dtype=int)
    partition = round(len(data) * DEFAULT_PARITION)
    TRAINING = data[:-partition]
    TESTING = data[-partition:]

    print(percent_letters(TRAINING[:, 0]))
    print(percent_letters(TESTING[:, 0]))

    L = TRAINING[:, 0]
    X = TRAINING[:, 1:]
    W1, W2, W3, training_acc = train_model(X, L, DEFAULT_LEARNING_RATE, DEFAULT_EPOCHS)

    L = TESTING[:, 0]
    X = TESTING[:, 1:]
    conf_matix, acc = test_model(X, L, W1, W2, W3)
