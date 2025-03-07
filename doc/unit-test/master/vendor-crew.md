Vendor Crew
=============================

Vendor Crew adalah user yang nasabah yang menggunakan service perusahaan.

## Create Vendor Crew

POST http://localhost:8082/api/cdt/core/trx/vendor-crew/command

### Request
```json
{
  "vendor_id": "93a347c1-5f87-40ee-a7a5-071cfad6ef40",
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
  "id": "1cec3e96-8fa5-4acc-a764-8035692282ec",
  "name": "John Doe",
  "ktp": "1234567890123456",
  "npwp": "1234567890123456",
  "username": "johndoeexamplecom",,
  "email": "john.doe@example.com",
  "msisdn": "+6281234567890",
  "vendor_id": "93a347c1-5f87-40ee-a7a5-071cfad6ef40",
  "user_id": null
}
```
