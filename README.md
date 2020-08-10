# Digit Recognizer

<p align="center"><img src="https://i.imgur.com/4mWJDBw.jpg" alt="The Superior Pizza"></p>

With a quick glance, it is easy to identify this image as a pizza—for the ~~cultured~~ sensible ones at the very least. We can accomplish such by transmitting and communicating information across different areas of the brain with the help of 85+ billion neurons. Image recognition is something we never think twice about, but how would a computer recognize images?

It is possible to hardcode what each picture represents with conditional statements, but what happens when there are modifications to the image? What if the image is tilted or is a different colour? At a certain point, there will be too many unknown variables to account for and conditional statements won't be viable anymore. Conditional statements work well when there aren't too many possibilities, such as in a Tic-Tac-Toe game, but in this case, something stronger than conditional statements is needed. Enter neural networks.

Our vast system of intertwined neurons is the inspiration for the widely used machine learning technique called *Artificial Neural Networks (ANN)*, the technique which builds upon the foundation for computer vision.

---

After learning the theory of backtracking and itching to code a small-scale project, I decided to set foot into creating a Sudoku solver from scratch, completely underestimating the complexity of optical character recognition—the technology used to decipher values from images.

In this repoistory, I will document my journey through understanding neural networks, deriving the backpropagation algorithm, and training my model through the MNIST digit database.

--- 

## The GUI

<p align="center"><img src="https://imgur.com/YQbY6xM.jpg" alt="GUI Screen"></p>

Neural networks function similarly to a black box: knowledge about the inputs and outputs is understood, but what happens within is not. Training the network for days only to see the "accuracy" displayed at the end was far from helpful. Without the tools to verify the accuracy, the number had no meaning. The solution? Create a GUI that allows images to be drawn and recognized on-demand, showcasing the network's supposed accuracy. Bonus: visualize what the MNIST digits look like, so when the predictions are off, you understand why (some of the digits are funky).