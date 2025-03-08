Branch
=============================

Branch adalah cabang dari perusahaan akan mekalukan manage terhadap mesin EDM dan nasabah.

## Create Branch

POST http://localhost:8082/api/cdt/core/trx/branch/command

### Request
```json
{
  "code": "014",
  "name": "Bank Central Asia",
  "online_transfer":  true,
  "bi_fast_transfer": true,
  "wallet": true,
  "virtual_account":  true,
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

Response
```json
{
  "id": "11f30c88-038b-4832-9b74-267f4a2f6465",
  "code": "014",
  "name": "Bank Central Asia",
  "online_transfer":  true,
  "bi_fast_transfer": true,
  "wallet": true,
  "virtual_account":  true,
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
}
```

TODO FIX
