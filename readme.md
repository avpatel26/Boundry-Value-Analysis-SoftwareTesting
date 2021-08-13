# Boundry value analysis of video streaming service  

This repo contains a fictional video streaming service through which user can register and rent movies. Different movie types have different cost of movie rental.


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
