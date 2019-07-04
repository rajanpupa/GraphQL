# GraphQL with SpringBoot

GraphQL is a query language to power rest api's

It provides the consumer of a rest endpoint to filter the data that they want using a generic query language over a single endpoint

GraphQL can be used to query data, map to the response that the user needs (only the fields that user needs)
You can avoid creating different api's for different results

## Book Store
- `/rest/books` is the REST resource which can fetch Books information
- DataFetchers are Interfaces for RuntimeWiring of GraphQL with JpaRepository

## How to create entities in the database
- Normally using the JpaRepositories. These have nothing to do with the GraphQL

## Sample GraphQL Scalar Queries
- Accessible under `http://localhost:8091/rest/books`



- Sample Query (Request body of the POST)
```
{
   allAuthors {
     fname
     lname
   }
   allBooks {
     isn
     title
     authors {
    	fname
    	lname
     }
     publisher
   }
   book(id: "123") {
     title
     authors {
    	fname
    	lname
     }
     publisher
   }
 }
```


### References
https://www.youtube.com/watch?v=zX2I7-aIldE