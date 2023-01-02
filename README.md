# kdGG

### Created by

Peter Buschenreiter

### Domain

The domain describes a typical chat application. It is based on the following entities:

- User
- Channel
- Post

Users can be members of many channels. A channel can have many members. A post is always associated with a channel.
A post is associated with a user and the channel in which the post was created.

### Necessary setup (Profile & Database)

##### Collection based implementation

- change profile in `aplication.yaml` to `list`

##### JDBC template based implementation

- change profile in `aplication.yaml` to `dev`

##### JPA based implementation

- change profile in `aplication.yaml` to `em`

##### Spring data based implementation

- create a postgres db called `kdGG`
- create a `.env` file in the root of the project with the following content:

```env
POSTGRES_PASSWORD=password
```

- replace `password` with your postgres password
- change profile in `aplication.yaml` to `prod`
