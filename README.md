# User Api - REST API with Spring Boot, JWT, h2, JPA

## Steps to Setup

**1. Clone the application**

```bash
https://github.com/pablojunin/spring-boot-rest-user-api.git
```

**2. Run application**

```bash
mvnw spring-boot:run
```

**3. Run test**

```bash
mvnw test
```

**4. Username and password for to get token**

```bash
{
	"username":"email",
	"password":"password"
}
```

**5. Steps to execute the app**

    a. Create an user with POST /api/v1/register/
        ```bash
            {
                "name": "Pablo",
                "email": "poyarzabal@gmail.com",
                "password": "passpass",
                "isactive": true,
                "phones": [
                        {
                            "number": "65656565",
                            "citycode": "2632",
                            "countrycode": "57"
                        },
                        {
                            "number": "65656565",
                            "citycode": "2632",
                            "countrycode": "57"
                        },
                        {
                            "number": "65656565",
                            "citycode": "2632",
                            "countrycode": "57"
                        }
                    ]
            }
        ```

    b. Authenticate with POST /authenticate/
        ```bash
            {
            "username":"poyarzabal@gmail.com",
            "password":"passpass"
            }
        ```

    c. Use the endpoints with "Authorization: Bearer *token*" in the header

The app will start running at <http://localhost:8080> on the context 'api/v1' <http://localhost:8080/api/v1>


## Explore Rest APIs

The app defines following APIs.

    POST /authenticate/

    GET /api/v1/users/

    GET /api/v1/users/:uuid
    
    POST /api/v1/register/
    
    PUT /api/v1/users/:uuid

    DELETE /api/v1/users/:uuid

    DELETE /api/v1/users/
    
    
## Execute with CURL

Get all users:

```bash
curl -X GET "http://localhost:8080/api/v1/users/" -H "accept: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyZXN0YXVyYW50IiwiZXhwIjoxNjExMDQ3Nzg0LCJpYXQiOjE2MTEwMjk3ODR9.6HSBNHjNIAds8JgZSaXhHygp1l9QcpGbYlY0ocFpj9PHfgWQx-SAPvQ6ZxYJhsacMJ4XmF4QFDyW4Y_HMB--BA"
```

Get an user
 
```bash
curl -X GET "http://localhost:8080/api/v1/users/:uuid" -H "accept: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyZXN0YXVyYW50IiwiZXhwIjoxNjExMDQ3Nzg0LCJpYXQiOjE2MTEwMjk3ODR9.6HSBNHjNIAds8JgZSaXhHygp1l9QcpGbYlY0ocFpj9PHfgWQx-SAPvQ6ZxYJhsacMJ4XmF4QFDyW4Y_HMB--BA"
```

Create a new user

```bash
curl -X POST "http://localhost:8080/api/v1/register/" -H "accept: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyZXN0YXVyYW50IiwiZXhwIjoxNjExMDE5NjYxLCJpYXQiOjE2MTEwMDE2NjF9.dlr-korT8Lqmlz5iyqYJunCF4AcOKQUqZ9Bbv2ISdO7RkK6UaZvJEB7ekoZYsMkAm0gQ5YHusHe4xPO6eeu48g" -H "Content-Type: application/json" -d "{\"name\": \"Pablo\",\"email\": \"poyarzagbatld4g5@gmail.com\",\"password\": \"fffrtyhddsdfsdgsdgsgg\",\"isactive\": true,\"phones\": [{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"},{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"},{\"number\": \"65656565\",\"citycode\": \"2632\",\"countrycode\": \"57\"}]}"
```

## Explore with swagger

To see the documentation with swagger: <http://localhost:8080/swagger-ui/> 

To see the documentation with api-docs: <http://localhost:8080/v2/api-docs>


## Explore with database

To see the h2 db: <http://localhost:8080/h2-console>
Conf: Driver Class=org.h2.Driver, JDBC URL=jdbc:h2:mem:testdb, User Name=sa, password=

## Import json file to Postman

Include the json file to import into Postman with all endpoints to run

file: /postman/userapi.postman_collection.json