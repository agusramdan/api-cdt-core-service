Branch
=============================

Branch adalah cabang dari perusahaan akan mekalukan manage terhadap mesin EDM dan nasabah.

## Create Branch

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
