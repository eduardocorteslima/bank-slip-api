[![Build Status](https://travis-ci.org/eduardocorteslima/bank-slip-api.svg?branch=master)](https://travis-ci.org/eduardocorteslima/bank-slip-api)


# Gerador de Boletos Bancários - API #

Esta api oferece recursos para geração de boletos bancários.

**Índice**
 1. A API
 2. Recursos oferecidos
 3. Setup e Execução
 
## 1- A API

Desenvolvida em:

 - **Java 8**   
 - **Spring Boot 2.0.3.RELEASE** 
 - **Maven 3.3.9** 
 - **Postgresql 10.4**.

## 2- Recursos oferecidos
A API utiliza o swagger para documentá-la, disponível na url:

 `http://localhost:8080/rest/swagger-ui.html`
 
 
 **- Criar boleto**
 
`POST http://localhost:8080/rest/bankslips`

Esse método gera um novo boleto.

Ex:
    
	 {
	     "due_date":"2018-01-01", 
		 "total_in_cents":"100000",
		 "customer":"Trillian Company",
		 "status":"PENDING" 
	 }

 **- Listar boletos**
 
 `GET http://localhost:8080/rest/bankslips/`
 
Esse método da API retorna uma lista de todos os boletos criados.

Ex:

    [
    	{
	    	"id":"84e8adbf-1a14-403b-ad73-d78ae19b59bf",
	    	"due_date":"2018-01-01",
	    	"total_in_cents":"100000",
	    	"customer":"Ford Prefect Company",
    	},
	    {
	    	"id":"c2dbd236-3fa5-4ccc-9c12-bd0ae1d6dd89",
	    	"due_date":"2018-02-01",
	    	"total_in_cents":"200000",
	    	"customer":"Zaphod Company",
	    }
	 ]

 **- Ver detalhes de um boleto**
 
 `GET http://localhost:8080/rest/bankslips/{id}`
 
Esse método da API retorna detalhes de um boleto filtrado pelo id. Exibe informação de multa conforme regra abaixo:

`Até 10 dias: Multa de 0,5% (Juros Simples)`

`Acima de 10 dias: Multa de 1% (Juros Simples)`


Ex:

	{
	    "id":"c2dbd236-3fa5-4ccc-9c12-bd0ae1d6dd89",
	    "due_date":"2018-01-01",
	    "total_in_cents":"100000",
	    "customer":"Ford Prefect Company",
	    "fine":"1000",
	    "status":"PENDING"
    }

 **- Pagar um boleto**
 
 `PUT http://localhost:8080/rest/bankslips/{id}`
 
Esse método da API permite o status do boleto para PAID.

Ex:

     {
       "status":"PAID"
     }
         


 **- Cancelar um boleto**

`PUT http://localhost:8080/rest/bankslips/{id}`

Esse método da API permite alterar o status do boleto para CANCELED.

Ex:

    {
       "status":"CANCELED"
    }

## 3- Setup e Execução

*(Recomendado)*

    git clone https://github.com/eduardocorteslima/bank-slip-api.git && cd bank-slip-api/docker
	
	make -f Makefile up
	

Para acompanhar o deploy utilize:
	
`docker logs bankslips-service  --follow`

*[Docker](https://docs.docker.com/install/) e [Docker compose](https://docs.docker.com/compose/install/)  são necessários para setup.*


