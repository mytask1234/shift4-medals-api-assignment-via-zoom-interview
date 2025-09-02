
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
