# payment-api

EndPoints

METODO GET: http://localhost:8080/payment

Retorno (HTTPStatus 200)
[
    {
        "id": 1,
        "cnp": "12345678901230",
        "paymentType": "PIX",
        "cardNumber": null,
        "paymentValue": 10.02,
        "paymentStatus": "PENDING"
    }
]

METODO GET: http://localhost:8080/payment/findstatus
payload
{
    "paymentStatus": "PENDING"
}
Retorno
[
    {
        "id": 2,
        "cnp": "12345678901230",
        "paymentType": "PIX",
        "cardNumber": null,
        "paymentValue": 10.02,
        "paymentStatus": "PENDING"
    },
    {
        "id": 3,
        "cnp": "12345678901230",
        "paymentType": "PIX",
        "cardNumber": null,
        "paymentValue": 10.02,
        "paymentStatus": "PENDING"
    },
    {
        "id": 4,
        "cnp": "12345678901233",
        "paymentType": "PIX",
        "cardNumber": null,
        "paymentValue": 10.02,
        "paymentStatus": "PENDING"
    }
]

METODO GET: http://localhost:8080/payment/findcnp
Payload
{
     "cnp": "12345678901230"
}

Retorno (HTTPStatus 200)
[
    {
        "id": 2,
        "cnp": "12345678901230",
        "paymentType": "PIX",
        "cardNumber": null,
        "paymentValue": 10.02,
        "paymentStatus": "PENDING"
    },
    {
        "id": 3,
        "cnp": "12345678901230",
        "paymentType": "PIX",
        "cardNumber": null,
        "paymentValue": 10.02,
        "paymentStatus": "PENDING"
    }
]

METODO GET:  http://localhost:8080/payment/findid
Payload
{
    "id": 2
}

Retorno (HTTPStatus 200)
{
    "id": 2,
    "cnp": "12345678901230",
    "paymentType": "PIX",
    "cardNumber": null,
    "paymentValue": 10.02,
    "paymentStatus": "PENDING"
}

-----------


METODO POST: http://localhost:8080/payment
Payload
{
    "cnp": "12345678901230",
    "paymentType": "PIX",
    "paymentValue": 10.02,
    "paymentStatus": "PENDING"
}
Retorno (HTTPStatus 202)
{
    "id": 1,
    "cnp": "12345678901230",
    "paymentType": "PIX",
    "cardNumber": null,
    "paymentValue": 10.02,
    "paymentStatus": "PENDING"
}


----------

METODO PUT: http://localhost:8080/payment/update
Payload
{
    "id":1,
    "paymentStatus": "SUCCESS"
}
Retorno (HTTPStatus 200)
{
    "id": 1,
    "cnp": "12345678901230",
    "paymentType": "PIX",
    "cardNumber": null,
    "paymentValue": 10.02,
    "paymentStatus": "SUCCESS"
}


-----------

METODO DELETE: http://localhost:8080/payment/delete
Payload
{
    "id": 1
}

Retorno (HTTPStatus 200)
#retorna os demais registros
[]


