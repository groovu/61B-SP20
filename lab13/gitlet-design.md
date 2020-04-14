# Gitlet Design Document
Authors: Cherish Truong, Jason Bustani

# Classes and Data Structures
## HashingBase

This class hashes files and/or commits to be identified as the base class for the other classes that
require a hashing function.

## **Fields**
1. Hash: Returns the SHA-1 hash of the files
2. ToString: Returns the string value of the hashed file
3. ReverseHash: Returns the reverse hash.
## Blobs implements Serializable

Class that represents a file.

## **Fields**
1. File file: The individual file of the current program.
2. ToString: Returns the string value of the file.
## Tree implements Serializable

This class keeps track of the file structure in a given repo.
keeps track of history of repo.

## Fields
1. Hashmap commitStructure: A hashmap with key value of commit, and blob
2. List commitHistory: A list that keeps track of the commit history in sorted order by date.
## Commits

Contains Blobs, overall container that has current status of directory.
This class keeps track of logs and other metadata.

## Fields
1. ToString: Returns the string value of the log.
## Git Command Interface, Main?

This class handles and controls passed into gitlet.

# Algorithms
# Persistence

(What is tracked between runs?)
.gitlet directory: Place that stores old copies of files and other metadata.

    - files and sub-directories in .gitlet?

working directory: Files that aren’t in .gitlet directory (which are copies of files from the repo that you are using and editing)

    - name of working directory?


Data Structure Ideas
        Hashmap that contains ALL commits.
        Object that contains current/any commit (blob/commit)
        Someway to track history? log, but type of data structure?
        Object that contains entire structure of commit (directory, files, etc) [like a zip file]
        




