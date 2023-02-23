# kdGG

### Course

Programming 5

### Created by

- Name: Peter Buschenreiter
- Email: peter.buschenreiter@student.kdg.be
- Student ID: 0152955-83

### Domain

The domain describes a typical chat application. It is based on the following entities:

- User
- Channel
- Post

Users can be members of many channels. A channel can have many members. A post is always associated with a channel.
A post is associated with a user and the channel in which the post was created.

### Necessary setup (Profile & Database)

##### JDBC template based implementation

- change profile in `aplication.yaml` to `dev`

##### Spring data based implementation

- create a postgres db called `kdGG`
- create a `.env` file in the root of the project with the following content:

```env
POSTGRES_PASSWORD=password
```

- replace `password` with your postgres password
- change profile in `aplication.yaml` to `prod`

## Week 1

### Channel API

#### Getting all channels

##### 200 OK

```http request
GET http://localhost:8081/api/channels
```

Response

```json
[
  {
    "channelID": 1,
    "name": "DuckiesGang",
    "description": "The coolest gang in town, no spaghett allowed!"
  },
  {
    "channelID": 2,
    "name": "ACS",
    "description": "Applied Computer Science at KdG"
  }
]
```

##### 204 No Content

```http request
GET http://localhost:8081/api/channels
```

Response

```json
[]
```

#### Getting a channel by ID

##### 200 OK

```http request
GET http://localhost:8081/api/channels/1
```

Response

```json
{
  "channelID": 1,
  "name": "DuckiesGang",
  "description": "The coolest gang in town, no spaghett allowed!"
}
```

##### 404 Not Found

```http request
GET http://localhost:8081/api/channels/300
```

#### Getting a channels users

##### 200 OK

```http request
GET http://localhost:8081/api/channels/1/users
```

Response

```json
[
  {
    "userID": 1,
    "name": "Peter",
    "birthdate": "1992-11-19",
    "role": "Admin"
  }
]
```

##### 204 No Content

```http request
GET http://localhost:8081/api/channels/3/users
```

Response

```json
[]
```

##### 404 Not Found

```http request
GET http://localhost:8081/api/channels/300/users
```

#### Delete a channel by ID

##### 204 No Content

```http request
DELETE http://localhost:8081/api/channels/1
```

##### 404 Not Found

```http request
DELETE http://localhost:8081/api/channels/3
```

#### Patch a channel by ID

##### 200 OK

```http request
PATCH http://localhost:8081/api/channels/1
Content-Type: application/json
Accept: application/json

{
  "description": "This description changed!"
}
```

Response

```json
{
  "channelID": 1,
  "name": "DuckiesGang",
  "description": "This description changed!"
}
```

##### 404 Not Found

```http request
PATCH http://localhost:8081/api/channels/3
Content-Type: application/json
Accept: application/json

{
  "description": "This channel does not exist!"
}
```

### User API

#### Getting all users

##### 200 OK

```http request
GET http://localhost:8081/api/users
```

Response

```json
[
  {
    "userID": 1,
    "name": "Peter",
    "birthdate": "1992-11-19",
    "role": "Admin"
  },
  {
    "userID": 2,
    "name": "Seif",
    "birthdate": "2003-10-12",
    "role": "Mod"
  },
  {
    "userID": 3,
    "name": "Filip",
    "birthdate": "2001-06-15",
    "role": "Mod"
  },
  {
    "userID": 4,
    "name": "Elina",
    "birthdate": "2003-04-15",
    "role": "User"
  }
]
```

##### 204 No Content

```http request
GET http://localhost:8081/api/users
```

Response

```json
[]
```

#### Getting a user by ID

##### 200 OK

```http request
GET http://localhost:8081/api/users/1
```

Response

```json
{
  "userID": 1,
  "name": "Peter",
  "birthdate": "1992-11-19",
  "role": "Admin"
}
```

##### 404 Not Found

```http request
GET http://localhost:8081/api/users/500
```

#### Getting a users channels

##### 200 OK

```http request
GET http://localhost:8081/api/users/1/channels
```

Response

```json
[
  {
    "channelID": 1,
    "name": "DuckiesGang",
    "description": "The coolest gang in town, no spaghett allowed!"
  }
]
```

##### 204 No Content

```http request
GET http://localhost:8081/api/users/2/channels
```

Response

```json
[]
```

##### 404 Not Found

```http request
GET http://localhost:8081/api/users/500/channels
```

#### Delete a user by ID

##### 204 No Content

```http request
DELETE http://localhost:8081/api/users/1
```

##### 404 Not Found

```http request
DELETE http://localhost:8081/api/users/500
```

#### Update a user by ID

##### 200 OK

```http request
PUT http://localhost:8081/api/users/1
Content-Type: application/json
Accept: application/json

{
  "name": "new name",
  "birthdate": "2000-01-01",
  "role": "Mod"
}
```

Response

```json
{
  "userID": 1,
  "name": "new name",
  "birthdate": "2000-01-01",
  "role": "Mod"
}
```

##### 404 Not Found

```http request
PUT http://localhost:8081/api/users/500
Content-Type: application/json
Accept: application/json

{
  "name": "new name",
  "birthdate": "2000-01-01",
  "role": "Mod"
}
```
