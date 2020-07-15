# Derivation

As a refresher, for our neural network to learn, we need to be able to modify our values to correspond to the desired output. If we differentiate our error function, we will have a numeric value to adjust our values. Simply adjust the values proportional to the current derivative, but in the opposite direction.

## Derivation of Activation Function

Before we start applying a derivative to our error function, it will be helpful if we first find the derivative of our activation function—the sigmoid in our case.

![Activation Function: Sigmoid](https://i.imgur.com/8B3hULK.png)

## Derivation of Error Function with respect to Output Weights

Although notation was discussed earlier, the notation that will be used to derive the equations will be simplifed. The current layer will be removed and i, j, and k will represent the input layer, hidden layer, and output layer respectively.

![BackPropagation: Output Neurons](https://i.imgur.com/fA56yct.png)


*Author's note: The "∑ disappears" because the other output neurons don't affect the current neuron. This allows you to treat it like a constant.
## Derivation of Error Function with respect to Hidden Weights

![BackPropagation: Hidden Neurons](https://i.imgur.com/U7b8zUX.png)

*Author's note: A lot of steps happened here, but ultimately, since you can't directly differentiate x[k] with respect to W[i][j], you will need to modify the derivative so that you can differentiate the value. On a side note, ∂a[j] / ∂W[ij] is allowed to go outside of the ∑ since it is completely unrelated to K.

## Derivation of Bias with respect to weights 

The process for differentiating this value is very similiar to the previous two examples, so this will be left as an excercise for the viewer. It will be helpful to note that the partial derivative of the output neuron with respect to the bias will always be one. Knowing this, the process becomes trivial. 

![Bias](https://i.imgur.com/O7EAF5J.png)

## An Overview

![Summary](https://i.imgur.com/tIg5tvW.png)

This graphic is in essence what backpropagation is. Instead of moving from left to right with our feedforward algorithm, we are now moving from right to left with the backpropagation algorithm. Rinse and repeat this cycle a couple hundred thousand times and you will have yourself a working model.