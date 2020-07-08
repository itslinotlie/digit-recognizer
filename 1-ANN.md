# Artificial Neural Networks (ANN)

![Structure of a Neural Network](https://i.imgur.com/zYBMAV1.png)

Neural networks come in all shapes and sizes, but they all share a similar feature: to mimic the human brain. There are plenty of networks, each with their different uses and complexity, with the most basic being the feedforward ANN. Information is passed to the input layer, and then processed in the hidden layers (if any) and then a value is given in the output layer. This model feeds the information forwards and hence the name, feed-forward.

Referencing the graphic above, each circle represents a neuron, and the lines between neurons represent a weight. When there is a column of neurons, it is referred to as a layer. The three main types of layers are the input, hidden, and output layers. The input layer is responsible for taking in the input of the data and passing it to the hidden layers. The information gets processed, and this gets passed to the output layer, where the answer is finally given.

Given enough time, a large enough sample set, and the proper values, a neural network can determine the output of any input similar to the training data it was given to a relatively high degree of certainty (if trained properly). To better explain how this process works, defining some terminology and commonly used notation will help.

--- 
<!--- <br> to create new line --->

## Terminology

| <h3>Word<h3>       |  <h3>Definition<h3>  |
|:-----------:|:------------:|
| Layer       | Refers to the neurons in a specific column|
| Node/Neuron | The basic unit of a neural network. It receives input from previous nodes and through it's weight, bias, and activation function, the neuron gets updated| 
| Weight      | A modifiable value that is multipled to the input of a node | 
| Bias        | A modifiable value that is added to the input of a node |
| Activation Function | A function that calculates the weighted sum of an output (i.e. the sigmoid function scales -inf and inf to 0+ and 1- respectively)
| Error Function | A function that calculates the gradient (the steepest direction) of a network's error with respect to the weights/biases|


## Notation

| Term<h2>    | <h3>Explanation<h3> |
|:--------------------------------:|:----------------------:|
| W[L][i][j]<br>![Weight](https://i.imgur.com/eBgXthq.png) | The weight from layer L-1 neuron i to layer L neuron j |
| Θ[L][j]<br>![Bias](https://i.imgur.com/FLdwm57.png)      | The bias of layer L neuron j |
| x[L][j]<br>![Input](https://i.imgur.com/0xPdXGE.png)     | The input of layer L neuron j |
| a[L][j]<br>![Output](https://i.imgur.com/CjyuCHE.png)    | The output of layer L neuron j |
| σ(x)   | The sigmoid activation function <br> ![Sigmoid Function](https://i.imgur.com/GpibRqI.png) |
| ∂      | The sign used for partial derivatives <br> (the derivative of a multi-variable function) |

\*Author's notes: the images will be used in the derivation while the array-like structure will be used in the actual code. It's good to be comfortable with both formats as they're sometimes used interchangably.

---

# The Feed-forward Process

![Structure of Feed-forward](https://i.imgur.com/KjiEv42.png)

With the terminology and notation aside, explaining the feedforward process becomes much cleaner. Updating the values of the neurons start at the first hidden layer and ends at the output layer. In each layer, every neuron's value updates to become the sum of all the previous layer's neurons' output multiplied by their respective weight. This value is then finally added by a bias value, which can be seen as another neuron with a weight of 1. After this, the activation function is applied, and the neuron gets assigned the updated value. Rinse and repeat with all the other neurons, and the entire network has been updated. This process, however, is only half of the learning process. The magic shines when you implement the backpropagation process.

---

# Backpropagation

You might be wondering, okay, I vaguely understand what a neural network is, but Michael, how does a network learn? Isn't a neural network just numbers? How do you teach numbers to output whether an image is a cat or a dog?? Great question. Similar to disciplining someone, we need to know when and how off we are from our predictions. We can do this by comparing our output with the expected output with the error function. With our error function, we have the tools to quantify how far off our program deviated from the target. This value tells us how much we need to tweak the weights and biases—the larger the error, the greater the adjustment. The error function that I will be using is a variant of the commonly used *Mean Squared Error (MSE)*.

![Error Function](https://i.imgur.com/sc5PBr8.png)

In the equation above, E represents the error and it is equal to the sum of all the differences between our output and the expected output all squared. Although it is squared, the order will matter when we start implementing our knowledge of calculus. Once our error is calculated, we will then propagate this error back and adjust our weights and biases accordingly, hence the name backpropagation. If you're interested in the proof or equations, head on over to 2-Derivation.