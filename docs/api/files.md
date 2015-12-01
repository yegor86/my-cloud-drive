## Files API

#### /files
```bash 
$ curl \
  http://localhost:3030/files
```

#### /files/createfolder
```bash
$ curl -i -X POST \
  -F "path=/testfolder" \
  -F "email=admin@mail.com" \
  http://localhost:3030/files/createfolder
```

#### /files/upload
```bash
$ curl -i -X POST \
  -F "filePath=/file1.txt" \
  -F "file=@file1.txt" \
  -F "email=admin@mail.com" \
  http://localhost:3030/files/upload
```

#### /files/list/${email}/${dirPath}
```bash
$ curl -i \
  http://localhost:3030/files/list/admin@mail.com/folder
```
#### /files/download/${email}/${filePath}
```bash
$ curl -i \
  http://localhost:3030/files/download/admin@mail.com/file
````
