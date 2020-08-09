# Derivation of Activation Function

As a warmup, we will differentiate our sigmoid activation function, as we will need to reference this later.

<p align="center"> <img source="https://i.imgur.com/8B3hULK.png" alt=""></p>

![Activation Function: Sigmoid](https://i.imgur.com/8B3hULK.png)

# Derivation of Error Function with respect to Output Weights

Similar to modifiying the notation in the error function, the notation used to derive the following equations will also be modified. Instead of a superscript to represent the layer, i, j, and k will now represent the input layer, hidden layer, and output layer respectively. There will no longer be any subscript to represent every neuron, and instead, it can be imagined that there only exists one neuron per layer.

![BackPropagation: Output Neurons](https://i.imgur.com/fA56yct.png)

*Author's note: The "∑ disappears" because the other output neurons don't affect the current neuron. This allows you to treat the other terms like a constant.

# Derivation of Error Function with respect to Hidden Weights

![BackPropagation: Hidden Neurons](https://i.imgur.com/U7b8zUX.png)

*Author's note: A lot of steps happened here, but ultimately, since you can't directly differentiate x[k] with respect to W[i][j], you will need to modify the derivative so that you can differentiate the value. On another note, ∂a[j] / ∂W[ij] is allowed to go outside of the ∑ since it is completely unrelated to K.

# Derivation of Bias with respect to weights 

The process for differentiating this value is very similiar to the previous two examples, so this will be left as an excercise for the viewer. It will be helpful to note that the partial derivative of the output neuron with respect to the bias will always be one, which means the partial derivative of the error function with respect to the bias is delta.

![Bias](https://i.imgur.com/O7EAF5J.png)

# An Overview

![Summary](https://i.imgur.com/tIg5tvW.png)

This graphic summarizes the process of backpropagation. Instead of feeding information from left to right, backpropagation finds the delta for each layer and modifies the weight's and biases' value from right to left accordingly. To mimic this in code, backpropagation will be split into two: finding the delta for each layer and updating the values. To actually modify the weight's and biases', a negative constant value will be multiplied (also known as the learning rate). Rinse and repeat this cycle a couple hundred thousand times and you will have yourself a working model.