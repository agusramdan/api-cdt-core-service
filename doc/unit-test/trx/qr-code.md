QR Code
=============================

QR Code is a type of matrix barcode (or two-dimensional code) first designed in 1994 for the automotive industry in Japan. A barcode is a machine-readable optical label that contains information about the item to which it is attached. In practice, QR codes often contain data for a locator, identifier, or tracker that points to a website or application. A QR code uses four standardized encoding modes (numeric, alphanumeric, byte/binary, and kanji) to store data efficiently; extensions may also be used.


## QR Code Create minimal generate untuk multiple use

POST http://localhost:8082/api/cdt/core/trx/qr-code/command

### Minimal Request
```json
{
  "type": "MULTIPLE_TRX_USE"
}
```

System akan generate QR Code yang dapat digunakan
contoh hasilnya adalah sebagai berikut:

```json
{
  "id": "92ac6b5b-89e7-4619-949e-e38f4400e1c4",
  "code": "qbg596ooribt88v4g5p1",
  "type": "MULTIPLE_TRX_USE",
  "status": "PENDING",
  "active": false,
  "expired_time": "2026-03-01T10:02:56.4277587",
  "user": null,
  "branch": null,
  "beneficiary_account": null,
  "service_transaction": null,
  "service_product": null
}
```
Agar bisa digunakan perlu di lengkapi data yang dibutuhkan, seperti user, branch, beneficiary_account, service_product.


### Minimal Request dengan Branch information
```json
{
  "type": "MULTIPLE_TRX_USE",
  "branch": {
    "code": "BRANCH-001"
  }
}
```

Response
```json
{
    "id": "ffa3d583-ffb7-4041-97f9-bfbef1201052",
    "code": "mbprzgv50f1ezxdj8344",
    "type": "MULTIPLE_TRX_USE",
    "status": "PENDING",
    "active": false,
    "expired_time": "2026-03-01T11:07:08.0230409",
    "user": null,
    "branch": {
        "id": "82305210-6f32-4738-809d-503a1c4a8e49",
        "code": "BRANCH-001",
        "name": "BRANCH 001"
    },
    "beneficiary_account": null,
    "service_transaction": null,
    "service_product": null
}
```

### Minimal Request dengan Service Product information

```json
{
  "type": "MULTIPLE_TRX_USE",
  "service_product": {
    "code": "MUL-ST-TR"
  }
}
```

Response

```json
{
    "id": "63235ea7-81a0-4fc1-899c-dbfe1c46274e",
    "code": "exkx13z6knzc5yii514h",
    "type": "MULTIPLE_TRX_USE",
    "status": "PENDING",
    "active": false,
    "expired_time": "2026-03-01T11:51:50.6913797",
    "user": null,
    "branch": null,
    "beneficiary_account": null,
    "service_transaction": null,
    "service_product": {
        "id": "2011a8ac-de41-4725-8a2f-1adedd642cca",
        "code": "MUL-ST-TR",
        "name": "Stor Tunai dan Transfer ke rekening menggunakan QR multiple use"
    }
}
```

