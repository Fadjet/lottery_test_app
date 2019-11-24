create table IF NOT EXISTS TEST_USER(ID SERIAL primary key,
                  FIRST_NAME VARCHAR(50) not null,
                  LAST_NAME VARCHAR(50) not null,
                  BIRTHDAY date
                  );
