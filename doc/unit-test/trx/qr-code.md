QR Code
=============================

QR Code is a type of matrix barcode (or two-dimensional code) first designed in 1994 for the automotive industry in Japan. A barcode is a machine-readable optical label that contains information about the item to which it is attached. In practice, QR codes often contain data for a locator, identifier, or tracker that points to a website or application. A QR code uses four standardized encoding modes (numeric, alphanumeric, byte/binary, and kanji) to store data efficiently; extensions may also be used.


## QR Code Create minimal generate untuk multiple use


### Minimal Request

POST http://localhost:8082/api/cdt/core/trx/qr-code/command

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
Lihat [Branch](../master/branch.md) untuk informasi lebih lanjut

POST http://localhost:8082/api/cdt/core/trx/qr-code/command
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

Lihat [Service Product](../master/service-product.md) untuk informasi lebih lanjut
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


### Minimal Request dengan Beneficary Account

Lihat [Beneficiary Account](../master/beneficiary-account.md)

```json
{
  "type": "MULTIPLE_TRX_USE",
  "beneficiary_account": {
    "id": "8b1ceb67-0710-4499-892c-d739aabef22c"
  }
}
```

Response

```json
{
  "id": "8bef075c-b93f-4b5e-a019-b56ddcdce01b",
  "code": "fm0au68zkgp7pdhnr15o",
  "type": "MULTIPLE_TRX_USE",
  "status": "PENDING",
  "active": false,
  "expired_time": "2026-03-01T14:48:29.6419795",
  "user": null,
  "branch": null,
  "beneficiary_account": {
    "id": "8b1ceb67-0710-4499-892c-d739aabef22c",
    "account_number": "1234567890",
    "account_name": "John Doe",
    "bank": {
      "id": "ff9389fa-243d-486b-90fb-242b1a98357f",
      "code": "014",
      "name": "Bank Central Asia"
    },
    "branch": null
  },
  "service_transaction": null,
  "service_product": null
}
```

### Minimal Request dengan user

Lihat [Customer Crew](../master/customer-crew.md) untuk infomasi lebih lanjut.
```json
{
  "type": "MULTIPLE_TRX_USE",
  "user": {
    "username": "johndoeexamplecom"
  }
}
```

Response
```json
{
    "id": "77ab9eb2-0d01-4355-ad89-bdb94a37d72c",
    "code": "sdxsl1tltz007ik3lteg",
    "type": "MULTIPLE_TRX_USE",
    "status": "PENDING",
    "active": false,
    "expired_time": "2026-03-01T16:41:39.1470316",
    "user": {
        "id": "cade2fbf-39fa-4f2f-8464-57bbf2b9ef52",
        "name": "John Doe",
        "username": "johndoeexamplecom",
        "email": "john.doe@example.com",
        "msisdn": "+6281234567890",
        "customer_id": null
    },
    "branch": null,
    "beneficiary_account": null,
    "service_transaction": null,
    "service_product": null
}
```

### Lengkapi data agar bisa di aktivasi

PUT http://localhost:8082/api/cdt/core/trx/qr-code/command/77ab9eb2-0d01-4355-ad89-bdb94a37d72c

```json
{
  "type": "MULTIPLE_TRX_USE",
  "user": {
    "username": "johndoeexamplecom"
  },
  "beneficiary_account": {
    "id": "8b1ceb67-0710-4499-892c-d739aabef22c"
  },
  "service_product": {
    "code": "MUL-ST-TR"
  },
  "branch": {
    "code": "BRANCH-001"
  }
}
```
Response

```json
{
    "id": "77ab9eb2-0d01-4355-ad89-bdb94a37d72c",
    "code": "sdxsl1tltz007ik3lteg",
    "type": "MULTIPLE_TRX_USE",
    "status": "PENDING",
    "active": false,
    "expired_time": "2026-03-01T16:41:39.147032",
    "user": {
        "id": "cade2fbf-39fa-4f2f-8464-57bbf2b9ef52",
        "name": "John Doe",
        "username": "johndoeexamplecom",
        "email": "john.doe@example.com",
        "msisdn": "+6281234567890",
        "customer_id": null
    },
    "branch": {
        "id": "0da9c773-6e20-4d0b-ad94-c9ef0560b862",
        "code": "BRANCH-001",
        "name": "BRANCH 001"
    },
    "beneficiary_account": {
        "id": "8b1ceb67-0710-4499-892c-d739aabef22c",
        "account_number": "1234567890",
        "account_name": "John Doe",
        "bank": {
            "id": "ff9389fa-243d-486b-90fb-242b1a98357f",
            "code": "014",
            "name": "Bank Central Asia"
        },
        "branch": null
    },
    "service_transaction": null,
    "service_product": {
        "id": "96712ce1-e315-4d46-b8bf-97ed2f659a4a",
        "code": "MUL-ST-TR",
        "name": "Stor Tunai dan Transfer ke rekening menggunakan QR multiple use"
    }
}
```

### Aktivasi QR Code
Setelah data lengkap bisa dilakukan aktifasi

PUT http://localhost:8082/api/cdt/core/trx/qr-code/command/77ab9eb2-0d01-4355-ad89-bdb94a37d72c

```json
{
  "status": "ACTIVE"
}
```

Response :

```json
{
    "id": "77ab9eb2-0d01-4355-ad89-bdb94a37d72c",
    "code": "sdxsl1tltz007ik3lteg",
    "type": "MULTIPLE_TRX_USE",
    "status": "ACTIVE",
    "active": true,
    "expired_time": "2026-03-01T16:41:39.147032",
    "user": {
        "id": "cade2fbf-39fa-4f2f-8464-57bbf2b9ef52",
        "name": "John Doe",
        "username": "johndoeexamplecom",
        "email": "john.doe@example.com",
        "msisdn": "+6281234567890",
        "customer_id": null
    },
    "branch": {
        "id": "0da9c773-6e20-4d0b-ad94-c9ef0560b862",
        "code": "BRANCH-001",
        "name": "BRANCH 001"
    },
    "beneficiary_account": {
        "id": "8b1ceb67-0710-4499-892c-d739aabef22c",
        "account_number": "1234567890",
        "account_name": "John Doe",
        "bank": {
            "id": "ff9389fa-243d-486b-90fb-242b1a98357f",
            "code": "014",
            "name": "Bank Central Asia"
        },
        "branch": null
    },
    "service_transaction": null,
    "service_product": {
        "id": "96712ce1-e315-4d46-b8bf-97ed2f659a4a",
        "code": "MUL-ST-TR",
        "name": "Stor Tunai dan Transfer ke rekening menggunakan QR multiple use"
    }
}
```


## Related dengan
1. [Branch](../master/branch.md) 
2. [Service Product](../master/service-product.md)
2. [Customer](../master/customer.md)
4. [Customer Crew](../master/customer-crew.md)
3. [Beneficiary Account](../master/beneficiary-account.md)