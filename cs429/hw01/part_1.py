import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

np.set_printoptions(linewidth=np.inf)


A1_START = 1
A0_START = 1
MU = 0.01
N_EPOCHS = 50
BATCH_SIZES = [1, 5, 10, 15, 19]
DATASETS = ["./dip-har-eff.csv", "./drift-har-eff.csv", "./set-har-eff.csv"]


def read_data(filename: str, normalize: bool = True) -> tuple[int, np.ndarray, np.ndarray]:
    df = pd.read_csv(filename)
    X = df.values[:, 1]   # free variable: days fished (ALWAYS column 1)
    Y = df.values[:, 2]   # dependent variable: fish harvest (ALWAYS column 2)

    if normalize:
        X = np.true_divide(X, np.max(X))
        Y = np.true_divide(Y, np.max(Y))

    return df.shape[0], X, Y


def train_model(X: np.ndarray, Y: np.ndarray, rows: int, batch_size: int, n_epochs: int = N_EPOCHS) -> dict:
    a1 = A1_START
    a0 = A0_START
    r2 = 1
    mse = []

    for i in range(n_epochs):
        batch_idx = np.random.randint(rows, size=(batch_size))

        Y_hat = a1 * X[batch_idx] + a0
        E = Y_hat - Y[batch_idx]
        a1 = a1 - MU * np.true_divide(np.sum(E * X[batch_idx]), batch_size)
        a0 = a0 - MU * np.true_divide(np.sum(E), batch_size)

        mse.append(np.true_divide(np.sum(np.square(E)), batch_size))

        if i == n_epochs - 1:
            r2 = 1 - (np.sum(np.square(Y - (a1 * X + a0))) / np.sum(np.square(Y - np.mean(Y))))
    
    return {
        "a1": a1,
        "a0": a0,
        "r2": r2,
        "MSE": mse,
    }


if __name__ == "__main__":
    fig, axes = plt.subplots(2, 3, figsize=(16, 10))
    fig.suptitle("LR w/ Batch Gradient Descent for Alaska Fishery Harvest")

    for i, filename in enumerate(DATASETS):
        print(f"\n========== {filename} ==========")
        rows, X, Y = read_data(filename)
        _, X_real, Y_real = read_data(filename, normalize=False)
        axes[0,i].scatter(X, Y, color='black', s=15, label="Original")
        results = {}

        # for distinguishing the different batches
        COLORS = iter(['red', 'blue', 'green', 'orange', 'purple', 'brown'])

        for batch_size in BATCH_SIZES:
            results[batch_size] = train_model(X, Y, rows, batch_size, N_EPOCHS)
            axes[1,i].plot(results[batch_size]['MSE'], label=f'Batch Size: {batch_size}')

            # validate model by using 5 random samples of free variables (bounded by the min and max of the dataset)
            test = np.random.uniform(min(X), max(X), 5)
            results[batch_size]['validate'] = results[batch_size]['a1'] * test + results[batch_size]['a0']
            axes[0,i].scatter(test, results[batch_size]['validate'], color=next(COLORS), s=15, alpha=0.75, label=f'Batch Size: {batch_size}')

            # prediction calculation for X = 28375
            prediction = results[batch_size]['a1'] + results[batch_size]['a0'] * np.max(Y_real)

            print(f"batch {batch_size}:\tR^2 = {results[batch_size]['r2']:.4f}\tMSE = {results[batch_size]['MSE'][-1]:.4f}\tPrediction = {prediction:.4f}")
            print(f"a1: {results[batch_size]['a1']:.4f}, a0: {results[batch_size]['a0']:.4f}")


    for ax in axes.flat:
        ax.legend(fontsize=8)

    for ax in axes[0, :]:
        ax.set_xlabel('Days Fishing')
        ax.set_ylabel('Fish Harvets')
    
    for ax in axes[1, :]:
        ax.set_xlabel('Epochs')
        ax.set_ylabel('RMSE')

    axes[0,0].set_title("LR for Dip Dataset")
    axes[0,1].set_title("LR for Drift Dataset")
    axes[0,2].set_title("LR for Set Dataset")
    axes[1,0].set_title("RMSE for Dip Dataset")
    axes[1,1].set_title("RMSE for Drift Dataset")
    axes[1,2].set_title("RMSE for Set Dataset")

    plt.tight_layout()
    plt.show()
