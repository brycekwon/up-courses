import numpy as np
import matplotlib.pyplot as plt


x = np.linspace(0, 50)
y = 3.5 * x - 2.7

plt.plot(x, y)
plt.xlabel("x")
plt.ylabel("y")
plt.title("Plot of y=3.5x - 2.7")

plt.grid(True)
plt.show()
