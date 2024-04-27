We will be using our two-day late pass for this project

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
- **buglist.pdf**: This file documents the bugs and issues found during testing.

## Running
After the files are downloaded, compile all of the program files in the "src" folder using the command `javac src/*.java`. Then use the command `java src/Main` to run the program. If you wish to run the program with one of the test csv files in "testing", type in your file name when prompted. Otherwise, type the file path to your csv file when prompted. If you are a tester, pass your file path as a command line argument when running the program.

# Authors
Team 16
- Crystal Wen wen00015
- Shunichi Sawamura sawam002
- Fumisato Teranishi teran015
- Lysong Seang seang006