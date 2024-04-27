# Voting System
The Voting System is a stand-alone product to be used after the voting and a CSV file holding the ballots is created. This system is to count the number of votes each party or candidate recieved. This system runs two types of elections: a Closed-Party listing and an Open-Party listing. It will also display the results and statistics of the election and save this information in an audit file.

# Instructions
The following are instructions on downloading and running the system.

## Prerequisites
You must install Java on your local machine to be able to use the Voting System. Additionally, the ballot file must be valid and in csv format, as it is assumed that the file is without errors. For testing, we used JUnit Jupiter.

## Downloading
Download the files from https://github.umn.edu/umn-csci-5801-01-S24/repo-Team16/tree/main/Project1 and save it to your local machine. Here are the following folders and files:
- **./src**: This folder holds all of the program files. All files that have a "Test" in the beginning are test files.
- **./testing**: This folder holds the test logs and all of the test files.
- **./documentation**: This folder holds all of the documentation documents.
- **ReadMe.md**: This file provides the instructions to use the software.

## Running
After the files are downloaded, compile all of the program files in the "src" folder using the command `javac src/*.java`. Then use the command `java src/Main` to run the program. If you wish to run the program with one of the test csv files in "testing", type in your file name when prompted. Otherwise, type the file path to your csv file when prompted. If you are a tester, pass your file path as a command line argument when running the program. Additionally, if you are testing multiple files, there directories that store the ballot files for multiple file processing (multipleCPL, multipleOPL, multipleMPO, and multipleMV). If you are running multiple files, please add a space in between each file path. Please ensure that there are no spaces within your file path, for example, your folder names cannot have spaces in them. This program assumes the following preconditions:
- There must be at least 1 ballot in the given file(s).
- There must be at least 1 party in the given file(s).
- There must be at least 1 candidate in the given file(s).
- There must be more ballots than seats in the given file.
- The quota must never become 1.
- All provided CSV files are correctly formatted ballot files.
- All files have different names or paths when the program recieves multiple ballot files.
- All testing files should move to the directory where the tester runs.

# Authors
Team 16
- Crystal Wen wen00015
- Shunichi Sawamura sawam002
- Fumisato Teranishi teran015
- Lysong Seang seang006
