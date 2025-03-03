Branch
=============================

Branch adalah cabang dari perusahaan akan mekalukan manage terhadap mesin EDM dan nasabah.

## Create Branch

POST http://localhost:8082/api/cdt/core/master/branch/command

### Request
```json
{
  "code": "1234567",
  "name":"Test Name Machine",
  "description":"Description"
}

```

Response
```json
{
  "id": "11f30c88-038b-4832-9b74-267f4a2f6465",
  "code": "1234567",
  "name": "TEST 001"
  
}
```

TODO FIX
