Vendor
=============================

Vendor adalah nasabah yang menggunakan service perusahaan.

## Create Vendor

POST http://localhost:8082/api/cdt/core/trx/vendor/command

### Request
```json
{
  "name": "PT. Vendor Indonesia",
  "npwp": "1221212212121212",
  "email": "vendor4@example.com",
  "phone": "+62221232233",
  "supplier": true,
  "address": {
    "street": "Jl. Sudirman No. 1",
    "region": "Pasirkaliki",
    "city": "Bandung",
    "state": "Jawa Barat",
    "zip_code": "40111",
    "country": "Indonesia"
  }
}
```

System akan generate QR Code yang dapat digunakan
contoh hasilnya adalah sebagai berikut:

```json
{
  "id": "93a347c1-5f87-40ee-a7a5-071cfad6ef40",
  "name": "PT. Vendor Indonesia",
  "npwp": "1221212212121212",
  "email": "vendor4@example.com",
  "phone": "+62221232233",
  "supplier": true,
  "maintenance": null,
  "pjpur": null,
  "gateway": null,
  "address": {
    "building": null,
    "street": "Jl. Sudirman No. 1",
    "region": "Pasirkaliki",
    "city": "Bandung",
    "state": "Jawa Barat",
    "country": "Indonesia",
    "zip_code": 40111
  }
}
```
