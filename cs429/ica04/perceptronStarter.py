import matplotlib.pyplot as plt
import numpy as np

plt.xlabel('x1')
plt.ylabel('x2')
plt.title('Preceptron learning')

# don't forget to normalize data when working with "real data set"
data = np.array([
    [1,1,1],
    [1,1,2],
    [-1,2,-2],
    [1,-1,1],
    [-1,-1,-2],
    [1,-2,1],
    [-1,-2,-1]
])

rows = data.shape[0]
cols = data.shape[1]

L  = data[:, 0]	# labels for each data point is in column 0
X1 = data[:, 1] # x1 data
X2 = data[:, 2] # x2 data
W  = np.array([0.5,1,0.5]) 	#model weights w0,w1,w2
mu = 0.01					#learning rate

ax = plt.gca()  # gca stands for 'get current axis' and plot setup
ax.spines['right'].set_color('none')
ax.spines['top'].set_color('none')
ax.xaxis.set_ticks_position('bottom')
ax.spines['bottom'].set_position(('data',0))
ax.yaxis.set_ticks_position('left')
ax.spines['left'].set_position(('data',0))

print(X1)
print(X2)

plt.scatter(X1, X2, c=L)
plt.plot(np.arange(-2.5, 2.5, 0.1), -W[0]/W[2] - W[1]/W[2] * np.arange(-2.5, 2.5, 0.1))

for j in range(12):
    accuracy = 0 
    for i in range(rows):
        charge = W[0] + np.sum(np.multiply(data[i, 1:], W[1:]))
        
        predict = 1 if charge >= 0 else -1
        if predict == L[i]:
            accuracy += 1
        else:
            # learn if did not get correct
            E = predict - L[i]
            X_t = np.concatenate((np.array([1]), data[i,1:]))
            W_t = np.multiply(np.multiply(E, X_t), mu) # weight delta adjustments
            W = np.subtract(W, W_t) # adjust the weights
            # print(W)
            # print(W_t)

            plt.pause(0.05)
            plt.plot(np.arange(-2.5, 2.5, 0.1), -W[0]/W[2]-W[1]/W[2] * np.arange(-2.5, 2.5, 0.1))

    print(f"accuracy: {accuracy}")
plt.show()
