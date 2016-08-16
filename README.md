# Red Tide Detection Based on Twitter Services
### Downloading data from Twitter

* This is implemented in Python, file twitterGapDownload.py. The search terms and time period over which the search must be done is entered in "url" variable. The results are stored in timestamped html files.
* The html data is then parsed into JSON files using Python file, twitterGapParse.py. The tweet text, stream type and tweet id are extracted into JSON files. The JSON files are then renamed as "<name>_train.txt" or "<name>_test.txt" before geotagging and classification are done.
### Geo-tagging
The geotagging component performs the following actions:

1. extracts "location" entity from texts

2. determines geographic coordinates (latitude and longitude) based on locations

3. computes cell based on geographic coordinates

Step 1) is implemented by the geoNLP project written in Java. It uses Stanford CoreNLP library to extract "location" entity from texts. The input files should be named as "<name>_train.txt" or "<name>_test.txt". Each line is a JSON formatted string of the data returned by social networks API, plus an additional field "stream_type", which should be equal to "Twitter", "Instagram" or "YouTube". See "sample_train.txt" and "sample_test.txt" for examples of the input files. The program generates output files named as "<name>_nlp.txt".

Steps 2) and 3) are implemented in Python, file geocode.py. The input files should be named as "<name>_nlp.txt". The program will generate output files named as "<name>_geo.txt". geocode.py uses Google Maps Geocoding API, which requires an API key. Follow its documentation to get a key and specify it in the "config.json" file accordingly.
### Annotation
For training, the "<name>_labels.txt" files are needed that specify whether the tweet is relevant or irrelevant. The Java Annotation files may be useful for easy labelling of tweets. For prediction, the labels can be left blank for the tweets.
### Classification
The classification component performs the following actions:

1. converts texts to Word2Vec vectors

2. converts TXT files to ARFF format

3. builds an SVM model based the training data

4. classifies Word2Vec vectors based on the given model.

Step 1) is implemented in Python, file generate_w2v.py. It expects GoogleNews-vectors-negative300.bin from the word2vec project available here: https://code.google.com/p/word2vec/. Note, that the GoogleNews-vectors-negative300.bin.gz file should be uncompressed. This module uses word2vec implementation from the gensim library, which is available here: https://radimrehurek.com/gensim/models/word2vec.html. It expects input files named as "<name>_labels.txt", e.g. "sample_train_labels.txt" and "sample_test_labels.txt". It also expects "<name>.txt" containing the JSON data, e.g. "sample_train.txt" and the geo-tagged data in "<name>_geo.txt", e.g. "sample_train_geo.txt". The program generates output files named as "<name>_w2v.txt".

Step 2) is implemented in Java, file txt2arff.java. It makes use of the Weka library, which should be automatically downloaded based on the pom.xml. It expects input files named as "<name>_w2v.txt", e.g. "sample_train_w2v.txt" and "sample_test_w2v.txt". The program generates output files named as "<name>.arff".

Step 3) is implemented in Java, file BuildModel.java. It makes use of the Weka library, which should be automatically downloaded based on the pom.xml. It expects input files named as "<name>_train_w2v.arff", e.g. "sample_train_w2v.arff". The program generates output files named as "<name>.model", e.g. "sample_train_w2v.model".

Step 4) is implemented in Java, file Classify.java. It makes use of the Weka library, which should be automatically downloaded based on the pom.xml. It expects files named as "<name>_w2v.arff", e.g. "sample_test_w2v.arff". The program generates output files named as "<name>_class.txt", e.g. "sample_test_w2v_class.txt".

