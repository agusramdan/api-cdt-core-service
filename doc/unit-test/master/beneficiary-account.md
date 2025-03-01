Beneficiary Account
=============================

Beneficiary Account account yang dimiliki pelanggan sebagai penerima dana.

## Generate

```json
{
  "customer_id": "4720f7b1-a917-46fa-bf6c-22935911b017",
  "account_number": "1234567890",
  "account_name": "John Doe",
  "bank":{
    "code":"014"
  }
}

```

Response

```json
{
    "id": "8d018b92-9967-4a87-a6a7-3c116a25bb35",
    "account_number": "1234567890",
    "account_name": "John Doe",
    "bank": {
        "id": "ff680a39-a835-4f75-9337-95aa89ac4657",
        "code": "014",
        "name": "Bank Central Asia"
    },
    "branch": null,
    "customer_id": "4720f7b1-a917-46fa-bf6c-22935911b017"
}

```

Related :
1. [Branch](../branch.md)
2. [Customer](../master/customer.md)
3. [Bank](../bank.md)

