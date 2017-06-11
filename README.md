
Project Details 
1. load DT ML Pipeline
2. predict binary class for each recods from dynamic table in read time
3. test using BDD
4. output to console avg probability of prediction for each class and their occurencies count

+-----+-----------------+-------+
|Class|          AvgProb|  Count|
+-----+-----------------+-------+
|    1|23.71851851851852|23171.0|
|    0|84.34080717488789|48649.0|
+-----+-----------------+-------+

*   FileWriter.scala appends input file

*  FileStream.scala executes prediction

* Spark 2.1.1 built using Kubernetes 

Decsion Tree Classifer build details: https://www.zepl.com/karolsudol/repositories/personal/5521e2d1935f407ab97584da2f210a37

 
