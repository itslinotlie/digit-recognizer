# Artificial Neural Networks (ANN)

![Structure of a Neural Network](https://i.imgur.com/zYBMAV1.png)

Neural networks come in all shapes and sizes but they all share a similar feature: to mimic the human brain. There are plenty of networks, each with their unique uses and complexity, with the most basic being the feedforward ANN. Information is passed to the input layer and then processed in the hidden layers (if any) and then a value is given in the output layer. This model feeds the information forwards and hence the name, feedforward.

Given enough time, a large enough sample set, and the proper outputs, weights, and bias' values, a neural network is able to accurately determine the output of any input similar to the training data (eg. determine the number from any given image). To better explain this process, defining some terminology and commonly used notation will help.

--- 
<!--- <br> to create new line --->

> ## Terminology

| `Word`      | `Definition` |
|:-----------:|:------------:|
| Layer       | Refers to the neurons in a specific column|
| Node/Neuron | The basic unit of a neural network. It receives input from previous nodes and through it's weight, bias, and activation function, the neuron gets updated| 
| Weight      | A modifiable value that is multipled to the input of a node | 
| Bias        | A modifiable value that is added to the input of a node |
| Activation Function | A function that calculates the weighted sum of an output (i.e. the sigmoid function scales -inf and inf to 0+ and 1- respectively)
| Error Function | A function that calculates the gradient (the steepest direction) of a network's error with respect to the weights/bias'|


> ## Notation

| `Term`     | `Explanation` |
|:----------:|:-------------:|
| W[L][i][j] | The weight from layer L-1 neuron i to layer L neuron j |
| Θ[L][j]    | The bias of layer L neuron j |
| x[L][j]    | The input of layer L neuron j |
| a[L][j]    | The output of layer L neuron j |
| σ(x)       | The sigmoid activation function <br> ![Sigmoid Function](https://i.imgur.com/GpibRqI.png) |
| ∂      | The sign used for partial derivatives <br> (the derivative of a multi-variable function)

---

# The Feedforward Process

![Structure of Feedforward](https://i.imgur.com/KjiEv42.png)

With the terminology and notation aside, explaining the feedforward process becomes much cleaner. Updating the values of the neurons start at the first hidden layer and ends at the output layer. In each layer, each neuron's value updates to become the sum of all the previous layer's neurons multipled by their respective weight and finally, this value is added by a bias value. After this, the activation function is applied and the neuron gets the updated value. Rinse and repeat with all other neurons and the entire network has been updated. This however, is only half of the learning process. The magic shines when you implement the backpropagation process.
