QR Code
=============================
### Minimal Request

POST http://localhost:8082/api/cdt/core/trx/deposit/command

```json
{
  "token": "qbg596ooribt88v4g5p1",
  "signature": "2026-03-01T10:02:56.4277587"
}
```
Result

```json
{
    "id": "0dd87728-dd03-4c78-b402-69b2cb692d88",
    "token": "fm0au68zkgp7pdhnr15o",
    "status": "SUCCESS",
    "machine": {
        "id": "3ba72514-c1a2-412f-bc00-2073bdc1a2c9",
        "code": "1234567",
        "name": "Test Name Machine",
        "description": "Description",
        "branch_id": null,
        "service_location_id": null
    },
    "cdm_trx_no": null,
    "cdm_trx_date": null,
    "cdm_trx_time": null,
    "amount": 10000000,
    "denominations": null,
    "service_transaction": {
        "id": "0735a646-38ed-41f5-a4d6-99e01416b9a4",
        "no": "17409590169213890409"
    },
    "service_product": null
}
```

