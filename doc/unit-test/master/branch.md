QR Code
=============================

QR Code is a type of matrix barcode (or two-dimensional code) first designed in 1994 for the automotive industry in Japan. A barcode is a machine-readable optical label that contains information about the item to which it is attached. In practice, QR codes often contain data for a locator, identifier, or tracker that points to a website or application. A QR code uses four standardized encoding modes (numeric, alphanumeric, byte/binary, and kanji) to store data efficiently; extensions may also be used.


## QR Code Create minimal generate untuk multiple use

POST http://localhost:8082/api/cdt/core/trx/branch/command

### Request
```json
{
  "code": "BRANCH-001",
  "name": "BRANCH 001",
  "address": {
    "building": "Tower A",
    "street": "Main Street",
    "region": "West Jakarta",
    "state": "Jakarta",
    "city": "Jakarta Selatan",
    "zip_code": "10110",
    "country": "Indonesia"
  }
}

```

System akan generate QR Code yang dapat digunakan
contoh hasilnya adalah sebagai berikut:

```json
{
  "id": "8cdbc2a6-80e2-48d9-8752-7f5a3d2c6cec",
  "name": "BRANCH 001",
  "code": "BRANCH-001",
  "address": {
    "building": "Tower A",
    "street": "Main Street",
    "region": "West Jakarta",
    "city": "Jakarta Selatan",
    "state": "Jakarta",
    "country": "Indonesia",
    "zip_code": "10110"
  }
}
```

TODO FIX
