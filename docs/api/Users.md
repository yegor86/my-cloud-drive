## Users API

#### /users
```bash 
$ curl \
  http://localhost:3030/users
```

#### /users/create
```bash
$ curl -i -X POST \
  -H "Content-Type: application/json" \
  -d '{"userName":"Admin","lastName":null,"userEmail":"admin@mail.com","fileList":[]}' \
  http://localhost:3030/users/create
```
#### /users/${email}
```bash
$ curl -i \
  http://localhost:3030/users/admin@mail.com
```

#### /users/files/${email}
```bash
$ curl -i \
  http://localhost:3030/users/files/admin@mail.com
```

