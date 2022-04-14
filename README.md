# vl-internship-master

Application's only REST endpoint is:
```
/api/receipt
```
It accepts POST requests and requires a JSON body in the format:
```
{
  "products": [
    {
      "name": "name1"
    },
    {
      "name": "name2"
    },
    ...
  ]
}
```
The names correspond to any existing products in database (ProductDb class). Product list cannot be empty or null and all its entries must be in correct format - they need to have a name.
