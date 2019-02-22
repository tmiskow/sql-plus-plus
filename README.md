# sql-plus-plus

Simple lexer, parser and interpeter of a subset of SQL++. It is written in Scala and its standard parser combinators library.

## Usage

### Building executable jar

```
./gradlew shadowJar
```

### Running command line tool

```
./interpreter
```

### Running command line tool with example datasets

```
./interpreter examples/datasets/Users.json examples/datasets/Messages.json
```

## Example queries

```
> SELECT VALUE 1;
[ 1 ]
```

```
> SELECT DISTINCT * FROM [1, 2, 2, 3] AS foo;
[ {
  "foo" : 1
}, {
  "foo" : 2
}, {
  "foo" : 3
} ]
```

```
> SELECT VALUE user FROM Users user WHERE user.id = 1;
[ {
  "name" : "MargaritaStoddard",
  "friendIds" : [ 2, 3, 6, 10 ],
  "alias" : "Margarita",
  "id" : 1,
  "userSince" : "2012-08-20T10:10:00",
  "employment" : [ {
    "organizationName" : "Codetechno",
    "start-date" : "2006-08-06"
  }, {
    "organizationName" : "geomedia",
    "start-date" : "2010-06-17",
    "end-date" : "2010-01-26"
  } ],
  "gender" : "F",
  "nickname" : "Mags"
} ]

```

```
> SELECT u.id AS userId, e.organizationName AS orgName FROM Users u UNNEST u.employment e WHERE u.id = 1;
[ {
  "userId" : 1,
  "orgName" : "Codetechno"
}, {
  "userId" : 1,
  "orgName" : "geomedia"
} ]
```
