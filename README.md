[![Build Status](https://travis-ci.org/eduardocorteslima/bank-slip-api.svg?branch=master)](https://travis-ci.org/eduardocorteslima/bank-slip-api)



# Bank Ticket Generator - API #

This api offers resources for bankroll generation.

**Index**
1. The API
2. Features offered
3. Setup and Execution
 
## 1- The API

Developed in:

- **Java 8**
- **Spring Boot 2.0.3.RELEASE**
- **Maven 3.3.9**
- **Postgresql 10.4**.

## 2- Resources offered
The API uses swagger to document it, available in the url:

`http: // localhost: 8080 / rest / swagger-ui.html`
  

**- Create ticket**

`POST http: // localhost: 8080 / rest / bankslips`


This method generates a new ticket.

Ex:

      {
          "due_date": "2018-01-01",
          "total_in_cents": "100000",
          "customer": "Trillian Company",
          "status": "PENDING"
      }

**- List tickets**
 

`GET http: // localhost: 8080 / rest / bankslips /`
 

This API method returns a list of all created tickets.

Ex:

    [
        {
          "id": "84e8adbf-1a14-403b-ad73-d78ae19b59bf",
          "due_date": "2018-01-01",
          "total_in_cents": "100000",
          "customer": "Ford Prefect Company",
        },
        {
          "id": "c2dbd236-3fa5-4ccc-9c12-bd0ae1d6dd89",
          "due_date": "2018-02-01",
          "total_in_cents": "200000",
          "customer": "Zaphod Company",
        }
    ]

**- View ticket details**

`GET http: // localhost: 8080 / rest / bankslips / {id}`
 

This API method returns details of a ticket filtered by id. Displays fine information as per rule below:


`Up to 10 days: Fine of 0.5% (Simple Interest)`


`Over 10 days: Fine of 1% (Simple Interest)`


Ex:

    {
        "id": "c2dbd236-3fa5-4ccc-9c12-bd0ae1d6dd89",
        "due_date": "2018-01-01",
        "total_in_cents": "100000",
        "customer": "Ford Prefect Company",
        "fine": "1000",
        "status": "PENDING"
    }

**- Pay for a ticket**


`PUT http: // localhost: 8080 / rest / bankslips / {id}`
 

This API method allows ticket status for PAID.


Ex:

       {
         "status": "PAID"
       }

**- Cancel a ticket**


`PUT http: // localhost: 8080 / rest / bankslips / {id}`


This API method allows you to change the status of the ticket to CANCELED.


Ex:

      {
         "status": "CANCELED"
      }

## 3- Setup and Execution

*(Recommended)*

    
      `git clone https://github.com/eduardocorteslima/bank-slip-api.git && cd bank-slip-api / docker`

      `make -f Makefile up`


To track deploy use:

      `docker logs bankslips-service --follow`


*[Docker](https://docs.docker.com/install/) and [Docker Compose](https://docs.docker.com/compose/install/) are required to setup.*
