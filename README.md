### Keycloak

```
docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:21.1.1 start-dev
```

then create Realm, Client, Role and User (localhost:8080)

---

### Access token & Refresh token

> http://localhost:8080/realms/${reals_name}/protocol/openid-connect/token

```
client_id: ${client_id}
username: ${username}
password: ${password}
grant_type: password
```

### Get access token with refresh token

> http://localhost:8080/realms/${reals_name}/protocol/openid-connect/token

```
client_id: ${client_id}
refresh_token: ${refresh_token}
grant_type: refresh_token
```

---

### Test

http://localhost:8081/api/customers (with access token)

---