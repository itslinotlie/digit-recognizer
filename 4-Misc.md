# Troubles

## Error Function Plateau

<p align="center"><img src="https://i.imgur.com/ET6eaiq.png" alt="Degree 4 Polynomial"></p>

When training machine learning models, the error function is expected to decrease over time; however, this value occasionally plateaus very early during the training process and the model does not recover. If the weights and biases were all positive in the graph above, no matter how many iterations of training, the error function will never see the global minimum. Generally, modifying the initial range of the weights and biases will suffice, but if this step does not work, there is usually a problem elsewhere. In my case, I improperly generated my range, which limited my model's ability to learn accurately.

## Optimizing the Learning

<p align="center"><img src="https://imgur.com/vevtdTU.png" alt="Under/Overfitting"></p>

When training models take upwards of days, every optimization counts. The parameters which influence the learning process are known as hyperparameters, and these values can make or break the training efficiency. If the learning rate is too low, vanishing gradient can occur and training takes forever; however, if the learning rate is too high, exploding gradient occurs and the values near inf and the model becomes useless. If the network width (the number of layers) is too high, there is the risk of overfitting the data, but the opposite can also be true with underfitting the data with minimal layers. Long story short, there is a lot to take into consideration during the training process. In my situation, I have settled with an accuracy of ~97% after a week's worth of fine-tuning (there are models that have reached ~99.7% on the MNIST dataset).

## The Learning Curve

Machine learning is a relatively new field with many resources online written formally. Through hours of surfing the internet, I will include the resources I found useful in my journey.

[3Blue1Brown](https://www.youtube.com/watch?v=aircAruvnKk&list=PLZHQObOWTQDNU6R1_67000Dx_ZCJB-3pi) --> Brief and visual overview into neural networks<br>
[Andrew Ng's coursera](https://www.coursera.org/learn/machine-learning) --> Beginner's essential handbook into ML<br>
[Finn Eggers NN](https://www.youtube.com/watch?v=d3OtgsGcMLw&list=PLgomWLYGNl1dL1Qsmgumhcg4HOcWZMd3k) --> Java overview of ANNs <br>
[Ryan Harris Derivation](https://www.youtube.com/watch?v=aVId8KMsdUU) --> Explanation of the backpropagation derivation<br>
[Machine Learning Guide Podcast](https://podcasts.google.com/search/Machine%20Learning%20GUide) --> High level audio explanation of ML<br>