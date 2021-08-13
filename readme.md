# Boundry value analysis of video streaming service  

This repo contains a fictional video streaming service through which user can register and rent movies. Different movie types have different cost of movie rental. Main aim behind this project is to test the register and rent services through JUnit drivers. 

Equivalence partitioning and boundry value test cases have been generated with the aim to maximize the test code coverage and capture the edge case bugs. 

## Directory structure

```
├── build.xml: build file for project
├── lib: required libraries
├── programs
│   ├── mutant-1: code with the user generated bug 1
│   ├── mutant-2: code with the user generated bug 2
│   ├── mutant-3: code with the user generated bug 3
│   ├── mutant-4: code with the user generated bug 4
│   ├── mutant-5: code with the user generated bug 5
│   └── original: original code for video streaming service
│       └── swen90006
│           └── xilften
└── tests
    ├── Boundary
    │   └── swen90006
    │       └── xilften
    │           └── BoundaryTests.java: boundry value test cases with on-off points
    └── Partitioning
        └── swen90006
            └── xilften
                └── PartitioningTests.java: input partitioning classes test cases
```


## Compile & Run:

* Run JUnit test cases on original implementation:

```
ant compile_orig
ant test -Dprogram="original" -Dtest="Partitioning"
ant test -Dprogram="original" -Dtest="Boundary"
```
Dtest="Partioning" : Runs equivalence partitioning test cases\
Dtest="Boundry" : Runs boundry value test cases
 
* Run JUnit test cases on buggy mutants:

```
ant test -Dprogram="mutant-1" -Dtest="Boundary"
```
* Clean all class files:
```
ant clean
```
