## About this repo

At my second Zoom interview with Shift4, I was asked by the technical interviewer, to share my screen and open my Java IDE.  
He then sent me the Medals API assignment (via the Zoom chat). You can find that assignment below.  
He also asked me not to use the Internet at all (no Google, ChatGPT, ech), which I think is totally unfair.  
In this Github repo you can find my solution to this assignment.  

## The Medals API assignment instructions (sent by the interviewer)

### 1. Registering medals

Create an API to register medals of different types (bronze, silver, gold). Each medal will contribute to the overall medal rating:

- The **bronze** medal contributes **1 point** to the total bronze rating.
- The **silver** medal contributes **2 points** to the total silver rating.
- The **gold** medal contributes **3 points** to the total gold rating.
- Unsupported medals (e.g., **platinum** or **diamond**) throws an error message indicating that the specified medal type is not supported.

### 2. Getting the overall medal rating

Create an API to get the overall medal rating where total accumulated points will be shown for each medal type (bronze, silver and gold).

- Provide the current total points for each medal type, showing how much each type has accumulated based on the registered medals.

#### Example user actions and expected results:

1. User registers a **bronze** medal.
2. User registers a **silver** medal.
3. User registers another **bronze** medal.
4. User registers a **gold** medal.
5. User registers another **gold** medal.
6. User requests the **overall medal rating**:

```json
{
  "bronze": 2,
  "silver": 2,
  "gold": 6
}
```

## How to run and play with this app

#### How to run the app

in your terminal, go to the project root directory, and run the command:

```sh
./mvnw spring-boot:run
```

#### Register some medals

to register some medals, open another terminal tab, and run these commands:

```sh
curl -X POST \
  http://localhost:8080/api/medals/register \
  -H 'Content-Type: application/json' \
  -d '{"medalType": "BRONZE"}' -i

curl -X POST \
  http://localhost:8080/api/medals/register \
  -H 'Content-Type: application/json' \
  -d '{"medalType": "SILVER"}' -i

curl -X POST \
  http://localhost:8080/api/medals/register \
  -H 'Content-Type: application/json' \
  -d '{"medalType": "BRONZE"}' -i

curl -X POST \
  http://localhost:8080/api/medals/register \
  -H 'Content-Type: application/json' \
  -d '{"medalType": "GOLD"}' -i

curl -X POST \
  http://localhost:8080/api/medals/register \
  -H 'Content-Type: application/json' \
  -d '{"medalType": "GOLD"}' -i
```

#### Get the medals rating

```sh
curl -X GET \
  http://localhost:8080/api/medals/rating -i
```

#### Check error handling

you can also issue some invalid requests, to test the error handling:

```sh
curl -X POST \
  http://localhost:8080/api/medals/register \
  -H 'Content-Type: application/json' \
  -d '{"medalType": "INVALID"}' -i

curl -X POST \
  http://localhost:8080/api/medals/register \
  -H 'Content-Type: application/json' \
  -d '{"medalType": null}' -i

curl -X POST \
  http://localhost:8080/api/medals/register \
  -H 'Content-Type: application/json' \
  -d '{"medalType": }' -i
```

Enjoy, and Good Luck :)  
