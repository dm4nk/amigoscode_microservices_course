# Ban

---

### JOOQ

To generate jooq sources:

1. first go to `ban/src/main/resources/db/migration/V1__Initial.sql`
   and replace `uuid_generate_v4()` to `random_uuid()`. This step is required
   because jooq uses _H2_ database to generate tables, and it is not
   fully compatible with _PostgeSQL_.
2. run mvn clean install
3. If you face any issues, use mvn plugin flyway:clean and flyway:repair
