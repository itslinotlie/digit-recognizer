# Artificial Neural Networks (ANN)

![Structure of a Neural Network](https://i.imgur.com/zYBMAV1.png)

Neural networks come in all shapes and sizes but they all share a similar feature: to mimic the human brain. There are plenty of networks, each with their unique uses and complexity, with the most basic being the feedforward ANN. Information is passed to the input layer and then processed in the hidden layers (if any) and then a value is given in the output layer. This model feeds the information forwards and hence the name, feedforward.

Given enough time, a large enough sample set, and the proper outputs, weights, and bias' values, a neural network is able to accurately determine the output of any input similar to the training data (eg. determine the number from any given image). To better explain this process, defining some terminology and commonly used notation will help.

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
| Error Function | A function that calculates the gradient (the steepest direction) of a network's error with respect to the weights/bias'|


## Notation*

| <h3>Term<h2>    | <h3>Explanation<h3> |
|:--------------------------------------------------------:|:----------------------:|
| W[L][i][j]<br>![Weight](https://i.imgur.com/eBgXthq.png) | The weight from layer L-1 neuron i to layer L neuron j |
| Θ[L][j]<br>![Bias](https://i.imgur.com/FLdwm57.png)      | The bias of layer L neuron j |
| x[L][j]<br>![Input](https://i.imgur.com/0xPdXGE.png)     | The input of layer L neuron j |
| a[L][j]<br>![Output](https://i.imgur.com/CjyuCHE.png)    | The output of layer L neuron j |
| σ(x)   | The sigmoid activation function <br> ![Sigmoid Function](https://i.imgur.com/GpibRqI.png) |
| ∂      | The sign used for partial derivatives <br> (the derivative of a multi-variable function) |
##### *The images will be used during the derivation process while the text will be used in code

---

# The Feedforward Process

![Structure of Feedforward](https://i.imgur.com/KjiEv42.png)

With the terminology and notation aside, explaining the feedforward process becomes much cleaner. Updating the values of the neurons start at the first hidden layer and ends at the output layer. In each layer, each neuron's value updates to become the sum of all the previous layer's neurons multipled by their respective weight and finally, this value is added by a bias value. After this, the activation function is applied and the neuron gets the updated value. Rinse and repeat with all other neurons and the entire network has been updated. This however, is only half of the learning process. The magic shines when you implement the backpropagation process.

---

# Backpropagation

You might be wondering, okay I vaguely understand what a neural network is, but how does a network learn? Isn't a neural network just numbers? How do you teach numbers to ouput x, y, or z? Great question. Similiar to disciplining someone, we need to know when and how off we are from our predictions. We can do this by comparing our output with the expected output with our error function. We adjust our values accordingly, which means that the larger of an error our network makes, the more we need to adjust our values. The error function that I will be using is a variant of the commonly used *Mean Squared Error (MSE)* function.

![Error Function](https://i.imgur.com/sc5PBr8.png)

In the equation, E represents the error and it is equal of the sum of all the differences between the actual output and the expected output all squared. Once we have calculated our error, we will then propagate this error back and adjust our weights and bias' accordingly. This is done all the way to the first hidden layer, hence the name backprogatation. If you're ever interested in the proof, head on over to 2-Derivation.