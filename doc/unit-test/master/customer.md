Customer
=============================

Customer adalah nasabah yang menggunakan service perusahaan.

## Create Customer

POST http://localhost:8082/api/cdt/core/trx/customer/command

### Request
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "msisdn": "+6281234567890",
  "ktp": "1234567890123456",
  "npwp": "1234567890123456",
  "customer_type":"INDIVIDUAL",
  "address": {
    "building": "Tower A",
    "street": "Main Street",
    "region": "West Jakarta",
    "city": "Jakarta Selatan",
    "state": "Jakarta",
    "zip_code": "10110",
    "country": "Indonesia"
  }
}

```

System akan generate QR Code yang dapat digunakan
contoh hasilnya adalah sebagai berikut:

```json
{
  "id": "13c2c1e5-327f-431d-87f3-7cfb1fa9151e",
  "name": "John Doe",
  "email": "john.doe@example.com",
  "msisdn": "+6281234567890",
  "address": {
    "building": "Tower A",
    "street": "Main Street",
    "region": "West Jakarta",
    "city": "Jakarta Selatan",
    "state": "Jakarta",
    "country": "Indonesia",
    "zip_code": "10110"
  },
  "customer_type": "INDIVIDUAL",
  "ktp": "1234567890123456",
  "npwp": "1234567890123456"
}
```
