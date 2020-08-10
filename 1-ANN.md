# Artificial Neural Networks (ANN)

<p align="center"><img src="https://i.imgur.com/zYBMAV1.png" alt="Structure of a Neural Network"></p>

Neural networks can come in all shapes and sizes, but they all share one similar feature: to mimic the human brain. There are plenty of networks, each with their different uses and complexity, but the most basic is the feedforward ANN. Information is passed to the input layer, where it gets processed in the hidden layers (if any), and then a final value is given in the output layer. The name, feed-forward, is derived from the way information is fed through the network.

Referencing the graphic above, each circle represents a neuron, and the lines between neurons represent a weight. When multiple neurons are grouped together, it is referred to as a layer. Bundle many layers together and a network is born. Given sufficient time and data, a network can learn the proper output to any input (when trained properly). To thoroughly explain this process, defining some terminology and commonly used notation will help.

--- 

## Terminology

| Word | Definition |
|:-:|:-:|
| Node/Neuron | The basic unit of a neural network. It receives input from previous nodes and through weights, a bias, and an activation function, it outputs a value to other neurons| 
| Layer | Refers to the neurons in a column|
| Weight | A value that is multipled with the output of a node| 
| Bias | A value that is added to the input of a node |
| Activation Function | A function that calculates the weighted sum of an output (i.e. the sigmoid function scales -inf and inf to 0+ and 1- respectively)
| Error Function | A function that calculates a network's error with respect to the weights/biases. This will be used for finding the gradient (the steepest direction) of a network|


## Notation

| Term | Explanation |
|:-:|:-:|
| W[L][i][j]<br>![Weight](https://i.imgur.com/eBgXthq.png) | The weight from layer *L-1* neuron *i* to layer *L* neuron *j* |
| Θ[L][j]<br>![Bias](https://i.imgur.com/FLdwm57.png) | The bias of layer *L* neuron *j* |
| x[L][j]<br>![Input](https://i.imgur.com/0xPdXGE.png) | The input of layer *L* neuron *j* |
| a[L][j]<br>![Output](https://i.imgur.com/CjyuCHE.png) | The output of layer *L* neuron *j* |
| σ(x) | The sigmoid activation function <br>![Sigmoid Function](https://i.imgur.com/GpibRqI.png) |
| ∂ | The sign used for partial derivatives <br> (the derivative of a multi-variable function) |

*Author's notes: the images shown will be used in the derivation while the array-like style will be used in the actual code. It's good to be comfortable with both formats as they're used interchangably.

# The Feed-forward Process

<p align="center"><img src="https://i.imgur.com/KjiEv42.png" alt="Structure of Feed-forward"></p>

With terminology and notation aside, explaining the feedforward process becomes much cleaner. The feedforwared process starts at the input layer and ends at the output layer. At each layer, every neuron's value updates to the sum of all the previous layers neuron's output multiplied by their respective weight. This sum is then finally added by a bias value, which can be seen as an individual neuron with a weight of 1. After this, the activation function is applied, and the neuron passes it's value to the next neuron. Rinse and repeat until this process until the entire network is updated. 

# Backpropagation

You might be wondering, okay, I vaguely understand what a neural network is, but Michael, how does a network learn? Aren't neural networks fundamentally just numbers? How do you teach numbers to classify and understand data? Great question. When we make a prediction, we need to how far off we are from the answer. We can do this by comparing our output against the expected output with an error function. The error function quantifies how far our network deviated from the target, allowing us to tweak the weights and biases—the larger the error, the greater the adjustment. We will modify every bias and weight in the network until all values have been modifed. The error function that will be used in the program is a variant of the commonly used *Mean Squared Error (MSE)*.

<p align="center"><img src="https://i.imgur.com/XyHCkid.png" alt="MSE Error Function"></p>

The notation used is very simliar to what was discussed previously with some minor adjustments. There is no superscript for the layer as the error function measures the error of the output (the last layer). For those unfamiliar with the sigma and set notation, the equation essentially quantifies the error of the output neurons with the target values across the output layer. If you're interested in the proof of the backpropagation equations, head on over to 2-Derivation.