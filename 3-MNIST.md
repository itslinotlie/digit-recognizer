# MNIST Dataset

<p align="center"><img src="https://imgur.com/90pp7sa.jpg" alt="MNIST Dataset" width=75%></p>

###### Digits from the MNIST dataset

With almost all the necessary components required to start training the model, there is one last step to consider: the data. The most common issue with machine learning is finding a large enough dataset while ensuring that it is appropriately properly cleaned and formatted. Enter the MNIST Dataset. Containing a total of 70 000 handwritten digits from adults and students, this dataset is perfect for training any model. With just enough variance in the digits, the dataset provides an accurate scope of every digit.

Similar to the artificial neural network page, it will help to provide a brief explanation of some common keywords that will be discussed later.

## Terminology

| Word | Definition |
|:-:|:-:|
| Training Data | The data the network repeatedly trains on |
| Testing/Evaluation Data | The data that the network is tested on, but never trained—usually used to determine the accuracy of the model |
| Accuracy | The number of correct predictions over the total predictions |
| Batch Size | When training data exceeds the capacity of the RAM (such as having TBs of data), it is common to train the network on a portion of the data. This portion is commonly referred to as the batch size | 
| Epochs | The number of cycles to train the network. Normally, a network does not sufficiently learn through one iteration of data so multiple epochs are needed (ranging from thousands to millions and beyond) |

## How to Read MNIST Files

[On the website](http://yann.lecun.com/exdb/mnist/), there are downloadable files for the testing and training data, each with their image and label files. In the case of the MNIST dataset, image files contain the grayscale value of each 28x28 pixel, and the label file contains the digit which the image represents. Intuitively, it makes sense to train the network against the majority of the data for training purposes, while saving a portion for the testing phase. This is done to improve accuracy while still reserving data to evaluate the model. In the case of the MNIST dataset, this is done at a 6:1 split for a result of 60k training data and 10k testing data. Now, all that is left is optimizing the batch size and epoch to maximize the model's training. 

Except... sadly, the files are not written in .txt, which means that a simple reader will have to be created. The website explains the file format, but I will review it here as well.

Every MNIST file contains (in this order):
1. magic number (used to ensure you don't mismatch the label and image files)
2. number of items (60k for training, 10k for testing)
3. row and column size (for image files only)
4. the data (different for image and label files)

For image files, the 28x28 grayscaled image will be given in a stream of numbers (similar to compressing a 28x28 image to 1x784). For every image, there will be a label to match it.

## The Catch

You may have downloaded the MNIST files, eager to train your network with data; however, using the Scanner object to read the input will result in a message along the lines of:

> java.util.InputMismatchException

Long story short, the problem with reading input with the Scanner class is that it scans the next token (analogy: a word in a sentence). The problem is that MNIST files are in a binary format, containing purely binary numbers (0s and 1s). Since there is no space separating the data, technically, the entire file is one token. The Scanner class is also ineffective in converting binary to decimal. What's the fix? Introduce the RandomAccessFile.

By inheriting this class, we can read everything, byte for byte, in the file. Using the built-in method, readInt(), we can finally start our program.

> java.lang.ArrayIndexOutOfBoundsException: Index 50660102 out of bounds

NaNI? Index 50660102 out of bounds... I thought you said the labels represented the digits, meaning the value should range from 0-9. How did I end up with 50660102?

## What is a Bit?

Down to the nitty-gritty, a bit represents the smallest unit of data, a value of either 0 or 1. When you group 8 bits, you have a byte. Group 4 bytes together, and you have an integer. Reading binary files requires familiarity in the various data types, as reading the wrong data type will result in unexpected numbers.

<p align="center"><img src="https://i.imgur.com/DFueqeO.png" alt="Integers and Bytes"></p>
<!-- ![Integers and Bytes](https://i.imgur.com/DFueqeO.png) -->

With this knowledge, our error becomes evident. When we used readInt(), we were reading an entire 32-bit integer while what we wanted was just an 8-bit byte. In the RandomAccesFile class, this can be done with the read() method. With this knowledge, you now have all the tools to train your model correctly. 