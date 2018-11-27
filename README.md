# SCOIR Technical Interview for Back-End Engineers
This repo contains an exercise intended for Back-End Engineers.

## Instructions
1. Fork this repo.
1. Using technology of your choice, complete [the assignment](./Assignment.md).
1. Update this README with
    * a `How-To` section containing any instructions needed to execute your program.
    * an `Assumptions` section containing documentation on any assumptions made while interpreting the requirements.
1. Before the deadline, submit a pull request with your solution.

## Expectations
1. Please take no more than 8 hours to work on this exercise. Complete as much as possible and then submit your solution.
1. This exercise is meant to showcase how you work. With consideration to the time limit, do your best to treat it like a production system.



====HOW-TO====

There are 2 methods to run this assignment. The 'Easy Way', which I recommend you use, is to run the jar file I have included as it only requires the jdk, and depends on no other environmental variables. The 'Hard Way' is for you to compile the sourse with the dependent libraries in your own build environement, which I can only advise on how you would do. These are the instructions: 

Easy Way:

1. Download the the file 'CSVtoJSON.jar' to the location of your choice.

2. Use command 'java -jar CSVtoJSON.jar C:\absolute\path\to\your\input\directory' inserting your own input own directory

3. Output and Error directories will be created in the parent directory of your input directory. If they already exist, their contents will be deleted.

4. add files to the input directory at your leisure

5. Use keyboard interrupt to stop the program. 

Hard Way: 
	
1. Download the src from the repo as well as the 2 dependent libraries (contained in the jars folder). 
	
2. Configure your own build environment (I used eclispe) and compile the source with the 2 libraries mentioned. 
	
3. The commands for running the project depend your build tools, but once compiled, the only arguement needed should be the path the the input folder, same as the easy way. 


===ASSUMPTIONS===

1. External Libraries are permissible

2. Each CSV can contain more that one person

3. Column header names will not change

4. CSV input files are zero indexed on the header row. Therefore the first row of data is at line 1. 

5. Error files do not require headers
	
+++++NOTES+++++

I appriciate you taking the time to review my submission. I look forward to hearing your critiques. I wish I had had more time to do extensive testing, but I would have probably gone beyond the 8 hour limit to design a full sweet of tests. Thanks again!

						- Francis Peabody


	