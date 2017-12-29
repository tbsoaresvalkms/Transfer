### Creating a new transfer
Resource: `POST /transfer`

##### Request
``` json
{
	"sender": "123",
	"receiver": "456",
	"transfer": "2018-11-11",
	"value": "100000"
}
```


##### Response

``` json
{
    "sender": 123,
    "receiver": 456,
    "value": 333333,
    "transfer": "2018-11-11",
    "scheduling": "2017-12-29",
    "rate": 6666.66
}
```

### Listing transfers by account
Resource: `GET /transfer/{account}`

##### Response

``` json
[
    {
        "sender": 123,
        "receiver": 444,
        "value": 100,
        "transfer": "2017-12-29",
        "scheduling": "2017-12-29",
        "rate": 6
    },
    {
        "sender": 123,
        "receiver": 123,
        "value": 333333,
        "transfer": "2018-11-11",
        "scheduling": "2017-12-29",
        "rate": 6666.66
    },
    {
        "sender": 123,
        "receiver": 456,
        "value": 333333,
        "transfer": "2018-11-11",
        "scheduling": "2017-12-29",
        "rate": 6666.66
    }
]
```