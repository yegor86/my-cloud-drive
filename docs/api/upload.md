```bash
curl -i -X POST \
  -d "name=test.txt" \
  -F "file=Test file" \
  -d "email=admin@mail.com" \
  http://localhost:3030/files/upload
```