Customer
=============================

Customer Crew adalah user yang nasabah yang menggunakan service perusahaan.

## Create Customer Crew

POST http://localhost:8082/api/cdt/core/trx/customer-crew/command

### Request
```json
{
  "customer_id": "4720f7b1-a917-46fa-bf6c-22935911b017",
  "name": "John Doe",
  "username": "John Doe @example.com",
  "email": "john.doe@example.com",
  "msisdn": "+6281234567890",
  "ktp": "1234567890123456",
  "npwp": "1234567890123456"
}

```

Response
```json
{
  "id": "cade2fbf-39fa-4f2f-8464-57bbf2b9ef52",
  "name": "John Doe",
  "ktp": "1234567890123456",
  "npwp": "1234567890123456",
  "username": "johndoeexamplecom",
  "email": "john.doe@example.com",
  "msisdn": "+6281234567890",
  "customer_id": "3f935696-08d9-466d-baf1-4a76644a4a0d"
}
```
