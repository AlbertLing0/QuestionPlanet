# UserController

UserController


---
## query

> BASIC

**Path:** api/users

**Method:** GET

> REQUEST



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
|  | array |  |
|  | object |  |
| &ensp;&ensp;&#124;─id | integer | 用户id |
| &ensp;&ensp;&#124;─username | string | 用户名 |
| &ensp;&ensp;&#124;─password | string | 用户密码 |
| &ensp;&ensp;&#124;─email | string | 用户邮箱 |

**Response Demo:**

```json
[
  {
    "id": 0,
    "username": "",
    "password": "",
    "email": ""
  }
]
```




---
## register

> BASIC

**Path:** api/register

**Method:** POST

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| username |  | NO |  |
| password |  | NO |  |
| email |  | NO |  |
| code |  | NO |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
|  | string |  |

**Response Demo:**

```json

```




---
## login

> BASIC

**Path:** api/login

**Method:** GET

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| username |  | NO |  |
| password |  | NO |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
|  | string |  |

**Response Demo:**

```json

```



