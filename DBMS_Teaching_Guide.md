# DBMS & SQL Server Workshop — Complete 12-Hour Teaching Guide
### CU Lucknow | Department of Career Planning & Development
### For the Trainer — Written in Plain English, Ready to Teach

---

> **How to use this guide:** Read each section once, do the SQL in SSMS yourself, then teach it. Everything is explained as if you're a student first, a teacher second. All code is copy-paste ready and tested.

---

## PRE-CLASS: SQL Server Setup (Do This the Night Before)

### What You're Installing

You need two things: **SQL Server** (the engine that runs the database) and **SSMS** (SQL Server Management Studio, the GUI where you write queries). Think of it like this — SQL Server is the car engine, and SSMS is the dashboard.

### Step 1 — Download SQL Server Developer Edition

Open your browser and go to: `https://www.microsoft.com/en-us/sql-server/sql-server-downloads`

Scroll down to "Developer" edition. It is completely free. Click **Download now**. This gives you a small installer called `SQL2022-SSEI-Dev.exe` (size around 4 MB). Run it.

When it opens, choose **Basic** installation type. It will ask you to accept the license — accept it. Then click Install. This downloads and installs SQL Server in the background (about 1.5 GB). Takes 5–10 minutes depending on your connection.

When it finishes, you'll see a green checkmark and a message saying "SQL Server 2022 installed successfully." Note down the **Instance Name** shown on screen — it usually says `MSSQLSERVER`.

### Step 2 — Download SSMS

On the same screen from Step 1, there's a button that says **Install SSMS**. Click it. It opens the Microsoft SSMS download page. Download the `.exe` file (about 600 MB). Install it like any normal software — Next, Next, Install, Finish.

If SSMS isn't showing on that screen, just search "SSMS download" on Google. The official Microsoft page will be the first result.

### Step 3 — First Connection

Open SSMS from your Start Menu. A "Connect to Server" dialog appears. Fill it exactly like this:

```
Server Type:   Database Engine
Server Name:   localhost   (or just a dot: . )
Authentication: Windows Authentication
```

Click **Connect**. You should see a panel on the left called "Object Explorer" with your server name at the top. This means everything is working.

### Step 4 — Create a Test Database (Verify Everything Works)

Click **New Query** at the top (or press Ctrl+N). In the blank query window that opens, type:

```sql
CREATE DATABASE TestDB;
USE TestDB;
SELECT 'SQL Server is working!' AS Message;
```

Select all of it (Ctrl+A) and press **F5** to run. You should see a result at the bottom that says "SQL Server is working!" — you're good to go.

### Troubleshooting (If It Doesn't Connect)

If you get a connection error, press Windows+R, type `services.msc`, look for "SQL Server (MSSQLSERVER)" and make sure it says "Running". If not, right-click → Start. Then retry connecting.

---

---

# DAY 1 — SESSION 1 (3 Hours)
## Topics: DB Fundamentals → RDBMS → Keys → Constraints → ER Diagrams → Functional Dependencies → Normalization

---

## TOPIC 1: DB Fundamentals (20 minutes)

### How to Open This in Class

Ask your students: "You all use apps like Swiggy, Instagram, or your college portal. Where do you think all that data lives?" Let them answer. Then you connect it.

### The Real-World Architecture

Every application you use is built on three layers working together.

**Frontend (FE)** is what the user sees — the buttons, screens, forms. It's built using HTML, CSS, JavaScript, React, etc. It's just the face of the app. The frontend does not store data. It only shows it.

**Backend (BE)** is the brain behind the frontend. When you click "Place Order" on Swiggy, the backend receives that click, processes it, calculates the bill, talks to the database, and sends back the result. It's built with Java (Spring Boot), Python (Django/Flask), Node.js, etc.

**API (Application Programming Interface)** is the messenger between frontend and backend. The frontend says "I need the menu for restaurant #47" — it sends this as an API request (usually over HTTP). The backend receives it, fetches from the database, and sends back the response (usually as JSON). An API is just a set of agreed-upon URLs with rules.

**Database** is where all the actual data lives permanently. Without a database, every time you close the app, everything is gone. The database stores it forever.

**SQL Developer Roadmap — What SQL People Actually Do**

Students often don't know why they're learning SQL. Give them context. SQL skills lead to:

A **Database Administrator (DBA)** maintains the health of the database — backups, performance, security. A **Database Developer** designs schemas and writes complex queries. A **Data Analyst** uses SQL to pull reports and answer business questions — "How many orders came from Delhi last month?" A **Data Engineer** builds pipelines that move data from one place to another. A **Data Scientist** uses SQL as one of many tools alongside Python and ML.

### Database vs DBMS vs RDBMS

**Database** is just organized data. You could technically have a database in an Excel file — it's data that's organized. The word "database" alone doesn't imply any software.

**DBMS (Database Management System)** is the software that manages a database. It handles storing, retrieving, updating, and deleting data. It gives you a way to interact with the data. Examples: SQL Server, MySQL, PostgreSQL, MongoDB, Oracle.

**RDBMS (Relational DBMS)** is a specific type of DBMS where data is stored in tables (called "relations") and these tables can be connected to each other. Almost everything you'll work with in this workshop is a Relational DBMS.

Think of it this way: Database is the concept, DBMS is the software, RDBMS is the specific kind of software that organizes data as tables with relationships.

**Famous SQL Platforms and When They're Used**

SQL Server (Microsoft) — Used heavily in enterprise India, most colleges teach this. MySQL — Used in web applications (Instagram, Facebook historically). PostgreSQL — Very powerful, popular in modern startups. Oracle — Used in big corporations and banking. SQLite — Lightweight, used in mobile apps.

---

## TOPIC 2: RDBMS Architecture, Keys, Referential Integrity (35 minutes)

### How to Explain a Relational Database

Draw this on the board:

```
Students Table                   Courses Table
+-------+-------+-----+         +----------+---------+----------+
| RollNo| Name  | Dept|         | CourseID | Name    | RollNo   |
+-------+-------+-----+         +----------+---------+----------+
|  101  | Aman  | CS  |         |  C001    | DBMS    |  101     |
|  102  | Priya | IT  |         |  C002    | OS      |  101     |
|  103  | Rohit | CS  |         |  C003    | CN      |  102     |
+-------+-------+-----+         +----------+---------+----------+
```

Tell students: These two tables are **related** through the RollNo column. That's the whole idea of "relational". One student can have many courses. The Courses table refers back to the Students table using the student's RollNo. This is a **relationship** between two tables.

### Keys — How We Uniquely Identify Rows

This is one of the most important concepts. Explain it with the Students table above.

**Super Key** — Any set of columns that can uniquely identify a row. Examples: {RollNo}, {RollNo, Name}, {RollNo, Name, Dept} are all super keys. As long as the combination is unique, it's a super key. There are many super keys — you can always make a super key by just adding more columns to an existing one. That's why they say "maximum number of Super Keys".

**Candidate Key** — A super key with no unnecessary columns. It's the minimal version. You remove any column and it stops being unique. From our example: {RollNo} is a candidate key. {RollNo, Name} is NOT — because if you remove Name, RollNo alone still uniquely identifies rows.

**Primary Key (PK)** — One candidate key chosen as the "official" identifier for the table. A table has exactly one primary key. It cannot be NULL.

**Alternate Key** — All candidate keys that were NOT chosen as the primary key. If a table has two candidate keys (RollNo and Email), and RollNo is chosen as PK, then Email is the alternate key.

**Unique Key** — Similar to primary key in that values must be unique, but it CAN be NULL (once). A table can have multiple unique keys.

**Composite Key** — A key made of more than one column. For example, in an Attendance table (RollNo, Date), neither column alone uniquely identifies a row, but the combination of both does. That combination is a composite key.

**Foreign Key (FK)** — A column in one table that refers to the Primary Key of another table. In our example above, Courses.RollNo is a foreign key that references Students.RollNo. This is what creates the relationship between the two tables.

### Referential Integrity and Why it Matters

Say you add a course record for RollNo = 999, but no student with RollNo 999 exists. That's a problem — your data is inconsistent. Referential Integrity is the rule that says: "the foreign key value must always point to something that actually exists in the parent table."

**The Problems (Anomalies) — Draw This on Board**

When tables are related, certain operations can cause problems. Explore the four scenarios:

On the **Parent Table (Students):**

`INSERT` into Students — No violation. Adding a new student is always fine. The child table (Courses) hasn't been affected.

`UPDATE` a RollNo in Students — May cause violation. If you change RollNo 101 to 201, but the Courses table still has RollNo 101 pointing to it — that reference is now broken.

`DELETE` a row from Students — May cause violation. If you delete RollNo 101 but there are still courses pointing to RollNo 101 in the Courses table, those course records now point to nothing. They become "orphan" records.

On the **Child Table (Courses):**

`INSERT` into Courses with a RollNo that doesn't exist — May cause violation. You're adding a course for a student who doesn't exist.

`UPDATE` the RollNo in Courses to one that doesn't exist — May cause violation.

`DELETE` from Courses — No violation. Removing a course doesn't affect the student.

**The Solution: Cascading**

SQL gives you two powerful options to handle this automatically:

`ON DELETE CASCADE` — If a parent row is deleted, automatically delete all child rows that referenced it. When student 101 is deleted, all their courses are also automatically deleted.

`ON UPDATE CASCADE` — If the parent's primary key is updated, automatically update all child foreign key references. When RollNo 101 becomes 201, all Courses rows with RollNo 101 automatically become 201.

### Problem Statement (PS01 from PPT) — Live Code Demo

```sql
-- Create the Customer (parent) table first
CREATE TABLE Customer (
    C_id INT PRIMARY KEY,
    C_name VARCHAR(50),
    C_city VARCHAR(30)
);

-- Create the Orders (child) table with FK constraint
CREATE TABLE Orders (
    O_id INT PRIMARY KEY,
    O_amount DECIMAL(10,2),
    C_id INT,
    CONSTRAINT fk_customer
        FOREIGN KEY (C_id) REFERENCES Customer(C_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Insert some data
INSERT INTO Customer VALUES (1, 'Aman', 'Delhi');
INSERT INTO Customer VALUES (2, 'Priya', 'Mumbai');

-- This works fine - customer 1 exists
INSERT INTO Orders VALUES (101, 500.00, 1);

-- This will FAIL - customer 99 does not exist
INSERT INTO Orders VALUES (102, 300.00, 99);  -- Error!

-- Now delete customer 1 - CASCADE will auto-delete their orders too
DELETE FROM Customer WHERE C_id = 1;
SELECT * FROM Orders;  -- Order 101 is also gone automatically
```

Explain: Tell students — "This is what your college portal does. When a student is deleted from the system, all their course registrations, marks, and attendance records are automatically cleaned up. That's ON DELETE CASCADE."

---

## TOPIC 3: Constraints in SQL (20 minutes)

Constraints are rules you enforce on columns. Think of them as validations built into the database itself, not just in your application code. Even if a bug in your app sends wrong data, the database will reject it.

```sql
CREATE DATABASE constraint_demo;
USE constraint_demo;

CREATE TABLE Employee (
    Emp_id INT PRIMARY KEY,                         -- PK: unique + not null automatically
    Emp_name VARCHAR(50) NOT NULL,                  -- NOT NULL: this column cannot be empty
    Email VARCHAR(100) UNIQUE,                      -- UNIQUE: no two employees can have same email
    Department VARCHAR(30) DEFAULT 'General',       -- DEFAULT: if not provided, use 'General'
    Salary DECIMAL(10,2) CHECK (Salary >= 15000),   -- CHECK: salary must be >= 15000
    Manager_id INT,
    CONSTRAINT fk_manager
        FOREIGN KEY (Manager_id) REFERENCES Employee(Emp_id)  -- FOREIGN KEY: self-reference
);

-- Test each constraint
INSERT INTO Employee VALUES (1, 'Aman', 'aman@co.com', 'Engineering', 50000, NULL);  -- Works

INSERT INTO Employee VALUES (2, NULL, 'priya@co.com', 'HR', 30000, 1);  -- FAILS: NOT NULL

INSERT INTO Employee VALUES (3, 'Priya', 'aman@co.com', 'HR', 30000, 1); -- FAILS: UNIQUE (email repeat)

INSERT INTO Employee (Emp_id, Emp_name, Email, Salary) VALUES (4, 'Rohit', 'rohit@co.com', 20000);
-- Department will default to 'General' since we didn't provide it

INSERT INTO Employee VALUES (5, 'Neha', 'neha@co.com', 'Sales', 5000, 1);  -- FAILS: CHECK (salary < 15000)

SELECT * FROM Employee;
```

---

## TOPIC 4: ER Diagrams (30 minutes)

### What is an ER Diagram?

Before writing a single line of SQL, a database designer sits down and draws an ER (Entity-Relationship) Diagram. It's a blueprint — like an architect's floor plan before building a house. You design the database on paper first, then convert it to actual tables.

**Entities** are the "things" in your system that you need to store data about. In a college system: Student, Course, Professor, Department are all entities. They become tables.

**Attributes** are the properties of an entity. For a Student: RollNo, Name, DOB, Email. These become columns in the table.

**Relationships** are the connections between entities. "Student enrolls in Course" is a relationship.

**Strong Entity** — Exists on its own. Has its own Primary Key. Can exist independently. Example: Student, Course. Represented by a single rectangle.

**Weak Entity** — Cannot exist without its parent (strong) entity. Has no primary key of its own — only has a "partial key". Example: If you're tracking "Class Sections" for a course — a section like "Section A" of DBMS doesn't mean anything without knowing which course it belongs to. Represented by double rectangle, and the relationship is a double diamond.

### Mapping Constraints (Cardinality)

This is how many records on one side can relate to how many on the other side.

**1:1 (One to One)** — One entity on the left relates to exactly one on the right and vice versa. Example: One Employee has one Employee_ID_Card. One ID card belongs to one employee only.

**1:M (One to Many)** — One on the left, many on the right. Example: One Department has many Employees. Each employee belongs to only one department.

**M:1 (Many to One)** — Same as 1:M but viewed from the other direction. Many Employees report to one Manager.

**M:N (Many to Many)** — Many on both sides. Example: One Student can enroll in many Courses, and one Course can have many Students. This is the most interesting case — when you implement it in SQL, you need a third "junction" table to represent it.

### Converting ER to Tables — The Rules

The ER diagram gives you the design. Now you convert it to actual SQL tables. Here are the rules to follow:

For a **strong entity**, create one table with all its attributes. The primary key becomes the table's PK.

For a **weak entity**, create one table that includes the weak entity's partial key AND the primary key of its parent strong entity. Together these form the composite primary key.

For a **1:1 relationship**, you can either merge the two tables into one, or add the PK of one table as a FK in the other.

For a **1:M relationship**, add the primary key of the "1 side" table as a foreign key in the "M side" table.

For a **M:N relationship**, create a new junction/bridge table with at least two columns: the PK of the first entity and the PK of the second entity. Together they form the composite PK of the junction table.

**Minimum Number of Tables**: For a simple ER with entities and relationships:
- Strong entities = 1 table each.
- Weak entities = 1 table each.
- M:N relationships = 1 additional junction table each.
- 1:1 and 1:M relationships often don't need extra tables (handled by FK).

---

## TOPIC 5: Functional Dependency (20 minutes)

### What Is a Functional Dependency?

Imagine a table with columns: RollNo, Name, Branch, CourseID, CourseName.

You notice something: whenever you see RollNo = 101, Name is always "Aman". Every single time. No matter how many rows. That means RollNo **determines** Name. We write this as: `RollNo → Name`

Read it as: "RollNo functionally determines Name."

The formal definition: If two rows have the same value for column α (the left side), they must also have the same value for column β (the right side). If that's true, then α → β is a functional dependency.

**When does FD NOT hold?** Look at this table:

```
α    β
a    1
a    2   ← Same α (a), different β (1 vs 2)
c    3
d    4
```

Here, `α → β` does NOT hold because for the same value `a` on the left, you get both `1` and `2` on the right.

**PPT Practice Question — Work Through This in Class**

Given the table:
```
A    B    C    D    E
a    2    3    4    5
a    3    4    5    (missing)
a    2    3    6    5
a    2    3    6    6
```

Which of these FDs hold?

`A → BC`: For every row with A=a, is B always 2 and C always 3? Row 2 has B=3. So NO — A→BC does NOT hold.

`DE → C`: Do rows with same D and E always have same C? Rows 3 and 4 both have D=6, E=5 — check C: row 3 has C=3, row 4 has C=6. NO — DE→C does NOT hold.

`C → DE`: For same C value, is D and E always the same? Looking at C=3: rows 1, 3, 4 all have C=3. Row 1: D=4, E=5. Row 3: D=6, E=5. Different D values. NO — C→DE does NOT hold.

`BC → A`: For same B and C, is A always same? All rows have B=2, C=3 for rows 1,3,4 — and A=a for all of them. Row 2 has different B so it's not in this group. YES — BC→A holds for this table.

---

## TOPIC 6: Closure & Candidate Keys (25 minutes)

### What is Attribute Closure?

The closure of an attribute (or set of attributes) is the set of ALL attributes that can be determined starting from that attribute — directly or through a chain of FDs.

We write it as `A+` (read: "A plus" or "closure of A").

**Example:** Relation R(A, B, C, D) with FDs: { A→B, B→C, C→D }

Start with A. What can A directly determine? B. Now what can B determine? C. Now what can C determine? D. So: `A+ = {A, B, C, D}` — A can determine everything!

What about B? B determines C, C determines D. B cannot determine A (there's no arrow pointing backward to A). So: `B+ = {B, C, D}` — B cannot determine A, so B is not a candidate key.

Similarly: `C+ = {C, D}` and `D+ = {D}`.

**Why does this matter?** An attribute (or group of attributes) is a **Candidate Key** if its closure equals the FULL set of all attributes in the relation. Because a key must be able to uniquely identify everything.

From above: Only A+ = ABCD = the full relation. Therefore, **A is the only Candidate Key**.

**Prime vs Non-Prime Attributes**

**Prime attributes** are those that appear in at least one candidate key.
**Non-prime attributes** are everything else — attributes that don't appear in any candidate key.

From the example: CK = {A}. Prime: {A}. Non-prime: {B, C, D}.

### Shortcut for Finding Candidate Keys (Teach This!)

This trick saves a lot of time: "Any attribute that **never appears on the RIGHT-hand side** of any FD must be part of every candidate key."

Why? Because if X never appears on the right of any FD, then no other attribute can determine X. So X can never be 'derived' — it must be given. Therefore every candidate key must include X.

**Problem Sets from PPT — Solutions**

**Problem 1:** R(ABCD), FDs: AB→C, C→D, D→A

Step 1: Find attributes never on RHS. RHS values are: C, D, A. The attribute **B** never appears on any RHS. So B is in every candidate key.

Step 2: Try combinations starting with B.

`(AB)+`: Start with AB → AB→C gives C → ABCD → C→D gives D (already have) → ABCD. Yes! AB+ = ABCD ✓. Is it minimal? A alone: A→? Nothing from A alone fires (D→A but that's the other direction). B alone: B→? Nothing. So removing either A or B breaks uniqueness. AB is a **Candidate Key**.

`(BC)+`: Start with BC → C→D gives D → BCD → D→A gives A → ABCD ✓. Is it minimal? B alone ≠ ABCD. C alone → CD → DA → ACD ≠ ABCD (missing B). Minimal! BC is a **Candidate Key**.

`(BD)+`: Start with BD → D→A gives A → ABD → AB→C gives C → ABCD ✓. Minimal? B alone ≠ ABCD. D→A→ ACD ≠ ABCD. Minimal! BD is a **Candidate Key**.

**Answer: CK = {AB, BC, BD}. Prime = {A,B,C,D} (all!). Non-Prime = none.**

**Problem 2:** R(ABCDE), FDs: A→D, B→A, BC→D, AC→BE

Step 1: RHS values: D, A, D, B, E. Never on RHS: **C** (and checking B — wait, B is on RHS of BC→D? No, B→A has B on LHS, and AC→BE has B on RHS. So B appears on RHS. C is never on RHS). Actually let me check: A is on RHS (B→A, AC→BE has B on RHS not A... wait AC→BE means B and E are on RHS). Let me list: A→D: RHS=D. B→A: RHS=A. BC→D: RHS=D. AC→BE: RHS=B,E. So RHS = {D, A, D, B, E} = {A, B, D, E}. **C** is never on RHS. C must be in every CK.

Step 2: Try AC and BC (since C must be there):

`(AC)+`: AC → A→D: ACD → AC→BE: ABCDE ✓. Minimal? A alone = {A,D}. C alone = {C}. Minimal! **AC is a CK**.

`(BC)+`: BC → B→A: ABC → A→D: ABCD → AC→BE: ABCDE ✓. Minimal? B alone = {A,B,D}. C alone = {C}. Minimal! **BC is a CK**.

**Answer: CK = {AC, BC}. Prime = {A,B,C}. Non-Prime = {D,E}.**

**Problem 3:** R(ABCDE), FDs: B→A, A→C, BC→D, AC→BE

Step 1: Attributes never on RHS. Let's check each: A appears on RHS of B→A. B appears on RHS of AC→BE. C appears on RHS of A→C. D appears on RHS of BC→D. E appears on RHS of AC→BE. All attributes appear on some RHS! So no guaranteed attribute.

Note: The PPT gives CK={BC}. Let's verify: `(BC)+`: BC → B→A: ABC → A→C: already have C → AC→BE: ABCDE ✓. BC is a CK. Now check if anything smaller works: B alone: B→A: AB → A→C: ABC → BC→D: ABCD → AC→BE: ABCDE ✓. Actually B alone also determines everything, making B also a candidate key. The PPT answer appears to have a minor error here — technically {B} is also a candidate key. In class, if a student asks, acknowledge this. Teach the process, not just the answer.

**Problem 4:** R(ABCDEF), FDs: A→BCD, BC→DE, B→D, D→A

Step 1: Never on RHS: RHS = {B,C,D, D,E, D, A} = {A,B,C,D,E}. **F** is never on RHS. F is in every CK.

Step 2: Check A, B, D with F:

`(A)+`: A→BCD: ABCD → BC→DE: ABCDE. A+ = ABCDE. So AF+ = ABCDEF ✓. AF minimal? A+ without F = ABCDE ≠ ABCDEF. **AF is a CK**.

`(B)+`: B→D: BD → D→A: ABD → A→BCD: ABCD → BC→DE: ABCDE. B+ = ABCDE. So BF+ = ABCDEF ✓. **BF is a CK**.

`(D)+`: D→A: AD → A→BCD: ABCD → BC→DE: ABCDE. D+ = ABCDE. So DF+ = ABCDEF ✓. **DF is a CK**.

**Answer: CK = {AF, BF, DF}. Prime = {A,B,D,F}. Non-Prime = {C,E}.**

---

## TOPIC 7: Database Normalization (30 minutes)

### Why Normalize? The Problem

Imagine this bad table:

```
StudentID | StudentName | CourseID | CourseName | ProfessorID | ProfessorName | Grade
101       | Aman        | C001     | DBMS       | P01         | Dr. Sharma    | A
101       | Aman        | C002     | OS         | P02         | Dr. Gupta     | B
102       | Priya       | C001     | DBMS       | P01         | Dr. Sharma    | A+
```

**Problem 1 (Redundancy):** "Dr. Sharma teaches C001" is written twice. And if there are 500 students in DBMS, it's written 500 times. Waste of space AND risk.

**Problem 2 (Update Anomaly):** If Dr. Sharma changes their name, you need to update 500 rows. Miss even one, and your data is inconsistent.

**Problem 3 (Insertion Anomaly):** What if you want to add a new Professor who hasn't been assigned a course yet? You can't — there's no StudentID or CourseID to put.

**Problem 4 (Deletion Anomaly):** If Priya drops Course C002, you delete that row. But C002 is now taught only in that deleted row, so you lose the fact that P02 teaches OS.

Normalization fixes all of this by **splitting large tables into smaller, well-structured tables**.

### 1NF (First Normal Form)

**Rule:** Every column must contain **atomic** (indivisible) values. No lists, no repeating groups in a single cell.

**Violating 1NF:**
```
StudentID | Name  | Courses
101       | Aman  | DBMS, OS, CN     ← Multiple values in one cell!
```

**Fixed (1NF):**
```
StudentID | Name  | Course
101       | Aman  | DBMS
101       | Aman  | OS
101       | Aman  | CN
```

### 2NF (Second Normal Form)

**Rule:** Must be in 1NF, PLUS no **partial dependency** — every non-prime attribute must depend on the FULL primary key, not just part of it.

This only matters when you have a **composite primary key**.

**Example:** Table: (StudentID, CourseID, StudentName, CourseName, Grade)

Primary Key = (StudentID, CourseID) [composite]

Now look at the dependencies:
- StudentName depends only on StudentID (not on the full PK). This is a PARTIAL DEPENDENCY — violation!
- CourseName depends only on CourseID (not on the full PK). Another PARTIAL DEPENDENCY — violation!
- Grade depends on both StudentID AND CourseID. This is fine — full dependency.

**Fixed (2NF): Split into separate tables**
```
Student(StudentID, StudentName)           -- StudentName fully depends on StudentID
Course(CourseID, CourseName)              -- CourseName fully depends on CourseID
Enrollment(StudentID, CourseID, Grade)    -- Grade fully depends on both
```

### 3NF (Third Normal Form)

**Rule:** Must be in 2NF, PLUS no **transitive dependency** — a non-prime attribute should not depend on another non-prime attribute.

**Example:** Table: (StudentID, StudentName, DeptID, DeptName)

Primary Key = StudentID

- StudentName depends on StudentID → fine.
- DeptID depends on StudentID → fine.
- DeptName depends on DeptID → and DeptID depends on StudentID → DeptName depends on StudentID **transitively** through DeptID. Violation!

**Quick Check Rule for 3NF:** For every FD X→Y: either X is a superkey OR Y is a prime attribute. If both conditions fail, it's not in 3NF.

**Fixed (3NF):**
```
Student(StudentID, StudentName, DeptID)   -- DeptID is a FK
Department(DeptID, DeptName)              -- DeptName directly depends on DeptID
```

### BCNF (Boyce-Codd Normal Form)

**Rule:** Stricter version of 3NF. For every FD X→Y: X must be a superkey. No exceptions.

The difference from 3NF: In 3NF, "Y is a prime attribute" can save you from violation. In BCNF, that escape route is closed — X must ALWAYS be a superkey.

Most tables in 3NF are also in BCNF. BCNF violations only happen when there are multiple overlapping candidate keys.

### Quick Normal Form Tips (From PPT)

**Is it NOT in 2NF?** If there exists X→Y where X is a proper subset (part) of a candidate key AND Y is a non-prime attribute. If this condition is found → not in 2NF.

**Is it in 3NF?** For every FD X→Y, at least one of these is true: X is a superkey/candidate key OR Y is a prime attribute. If all FDs satisfy this → in 3NF.

**Is it in BCNF?** For every FD X→Y, X must be a superkey/candidate key. No other escape.

### Normalization Practice Set — Solutions

**Problem 1:** R(ABCDE), CE→D, D→B, C→A

Finding CK: C and E never appear on RHS, so both must be in every CK. CE+ = CE → +D → +B → +A = ABCDE ✓. CK = {CE}. Prime = {C,E}. Non-prime = {A,B,D}.

Highest NF: Check 2NF — is there X→Y where X is a proper subset of CE AND Y is non-prime? C→A: C is a subset of CK {CE}, A is non-prime. PARTIAL DEPENDENCY! **Not in 2NF. Highest NF = 1NF.**

**Problem 2:** R(ABCDEF), AB→C, DC→AE, E→F

Finding CK: Never on RHS → B and D. Try ABD: AB→C: ABCD → DC→AE: ABCDE → E→F: ABCDEF ✓. Try BCD: BC+D → DC→AE: ABCDE → E→F: ABCDEF ✓. CK = {ABD, BCD}. Prime = {A,B,C,D}. Non-prime = {E,F}.

Highest NF: AB→C — AB is a proper subset of CK {ABD}, C is non-prime. PARTIAL DEPENDENCY! **Highest NF = 1NF.**

**Problem 3:** R(ABCDEFGHI), AB→C, BD→EF, AD→GH, A→I

Never on RHS: A, B, D (check carefully — none appear on any RHS). Try ABD: AB→C: +C → BD→EF: +EF → AD→GH: +GH → A→I: +I = ABCDEFGHI ✓. CK = {ABD}. Prime = {A,B,D}. Non-prime = {C,E,F,G,H,I}.

A→I: A is a subset of CK {ABD}, I is non-prime. PARTIAL DEPENDENCY! **Highest NF = 1NF.**

**Problem 4:** R(ABCDE), AB→CD, D→A, BC→DE

Never on RHS: B (check: RHS = {C,D, A, D,E} = {A,C,D,E}. B never on RHS). Try with B:
AB: AB→CD: ABCD → D→A: already there → BC→DE? Need C, have C from AB: ABCDE ✓. CK!
BC: BC→DE: BCDE → D→A: ABCDE ✓. CK!
BD: BD → D→A: ABD → AB→CD: ABCD → BC→DE: ABCDE ✓. CK!
CK = {AB, BC, BD}. Prime = {A,B,C,D}. Non-prime = {E}.

2NF: Is E partially dependent on any CK? E is only determined by BC (a full CK). No partial dep. In 2NF ✓.

3NF: Any X→Y where X is not a superkey AND Y is non-prime? E is non-prime. Is there X→E where X is not a superkey? Only BC→DE, and BC is a CK (superkey) ✓. D→A: D is not a superkey, but A is prime (in CK AB and BD) ✓. **In 3NF ✓**.

BCNF: D→A: D is not a superkey. Violation! **Highest NF = 3NF.**

**Problem 5:** R(ABCDE), BC→ADE, D→B

Never on RHS: C (RHS = {A,D,E, B} = {A,B,D,E}. C never on RHS). C must be in every CK.
BC: BC→ADE: ABCDE ✓. CK!
CD: D→B: BCD → BC→ADE: ABCDE ✓. CK!
CK = {BC, CD}. Prime = {B,C,D}. Non-prime = {A,E}.

2NF: Is there subset of CK determining non-prime? Non-primes are A and E. Both determined by BC or CD (full CKs). **In 2NF ✓**.

3NF: D→B: D is not a superkey. B is prime (in CK BC). Condition: Y is prime ✓. **In 3NF ✓**.

BCNF: D→B: D is not a superkey. Violation! **Highest NF = 3NF.**

**Problem 6:** R(ABC), A→B, B→C, C→A

A+: A→B: AB → B→C: ABC ✓. A is a CK.
B+: B→C: BC → C→A: ABC ✓. B is a CK.
C+: C→A: CA → A→B: ABC ✓. C is a CK.
CK = {A, B, C}. ALL attributes are prime! Non-prime = none.

BCNF: For each FD: A→B (A is CK ✓), B→C (B is CK ✓), C→A (C is CK ✓). **Highest NF = BCNF ✓.**

---

---

# DAY 1 — SESSION 2 (3 Hours)
## Topics: SQL Commands → GROUP BY → ORDER BY → JOINs → Sub-Queries → Set Operations

---

## TOPIC 8: SQL Commands — DDL, DML, DQL, DCL, TCL (50 minutes)

### Setup — Create Your Practice Database

```sql
CREATE DATABASE workshop_db;
USE workshop_db;
```

### DDL — Data Definition Language

DDL is used to define the structure (schema) of the database. It deals with tables themselves, not the data inside. The main commands are CREATE, ALTER, and DROP.

```sql
-- CREATE: Build the table structure
CREATE TABLE Student (
    StudentID INT PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Branch VARCHAR(30),
    CGPA DECIMAL(3,1) CHECK (CGPA BETWEEN 0.0 AND 10.0),
    Email VARCHAR(100) UNIQUE,
    Enrollment_Year INT DEFAULT 2024
);

-- ALTER: Modify an existing table structure
ALTER TABLE Student ADD PhoneNumber VARCHAR(15);      -- Add a new column
ALTER TABLE Student DROP COLUMN PhoneNumber;          -- Remove a column
ALTER TABLE Student ALTER COLUMN Name VARCHAR(100);   -- Change column size

-- DROP: Delete the table entirely (structure + all data)
-- DROP TABLE Student;   -- Be careful! This is permanent.
```

### DML — Data Manipulation Language

DML works with the actual data — INSERT, UPDATE, DELETE.

```sql
-- INSERT: Add rows
INSERT INTO Student VALUES (101, 'Aman Singh', 'CSE', 8.5, 'aman@uni.com', 2022);
INSERT INTO Student VALUES (102, 'Priya Sharma', 'IT', 9.1, 'priya@uni.com', 2022);
INSERT INTO Student VALUES (103, 'Rohit Verma', 'ECE', 7.8, 'rohit@uni.com', 2023);
INSERT INTO Student VALUES (104, 'Neha Gupta', 'CSE', 8.9, 'neha@uni.com', 2022);
INSERT INTO Student VALUES (105, 'Arjun Mehta', 'CSE', 6.5, 'arjun@uni.com', 2023);
INSERT INTO Student VALUES (106, 'Kavya Nair', 'IT', 9.5, 'kavya@uni.com', 2022);

-- UPDATE: Modify existing rows
UPDATE Student SET CGPA = 9.0 WHERE StudentID = 103;
UPDATE Student SET Branch = 'CS' WHERE Branch = 'CSE';  -- Updates multiple rows

-- DELETE: Remove rows (WHERE is very important — without it, ALL rows are deleted)
DELETE FROM Student WHERE StudentID = 106;
-- DELETE FROM Student;  -- Deletes everything! Don't do this accidentally.
```

### DQL — Data Query Language (The Most Important Part)

This is where most of your time will be spent. SELECT is the only command here.

```sql
-- Basic SELECT
SELECT * FROM Student;                         -- All columns
SELECT Name, Branch, CGPA FROM Student;        -- Specific columns
SELECT Name AS 'Student Name', CGPA FROM Student;  -- Alias

-- WHERE: Filter rows by condition
SELECT * FROM Student WHERE Branch = 'CS';
SELECT * FROM Student WHERE CGPA >= 8.0;
SELECT * FROM Student WHERE CGPA BETWEEN 7.5 AND 9.0;    -- Inclusive
SELECT * FROM Student WHERE Branch IN ('CS', 'IT');       -- Multiple values
SELECT * FROM Student WHERE Name LIKE 'A%';               -- Starts with A
SELECT * FROM Student WHERE Name LIKE '%a';               -- Ends with a
SELECT * FROM Student WHERE Name LIKE '%oh%';             -- Contains 'oh'

-- ORDER BY: Sort the results
SELECT * FROM Student ORDER BY CGPA DESC;          -- Highest CGPA first
SELECT * FROM Student ORDER BY Branch, CGPA DESC;  -- Sort by Branch first, then by CGPA within each branch

-- DISTINCT: Remove duplicates
SELECT DISTINCT Branch FROM Student;

-- TOP: Limit number of rows returned (SQL Server syntax)
SELECT TOP 3 * FROM Student ORDER BY CGPA DESC;   -- Top 3 students by CGPA
```

### GROUP BY and Aggregate Functions

This is one of the most important concepts in SQL. Students always find this confusing, so spend extra time here.

**Aggregate functions** reduce many rows to one value: COUNT, SUM, AVG, MAX, MIN.

```sql
-- Aggregate functions without GROUP BY (entire table as one group)
SELECT COUNT(*) AS TotalStudents FROM Student;
SELECT AVG(CGPA) AS AverageCGPA FROM Student;
SELECT MAX(CGPA) AS TopCGPA, MIN(CGPA) AS LowestCGPA FROM Student;
SELECT COUNT(DISTINCT Branch) AS NumberOfBranches FROM Student;
```

**GROUP BY** divides rows into groups and applies an aggregate to EACH group.

Think of it like this: "For each branch separately, calculate the average CGPA."

```sql
-- How many students are in each branch?
SELECT Branch, COUNT(*) AS StudentCount
FROM Student
GROUP BY Branch;

-- Average CGPA per branch
SELECT Branch, AVG(CGPA) AS AvgCGPA
FROM Student
GROUP BY Branch;

-- Highest CGPA in each branch
SELECT Branch, MAX(CGPA) AS TopCGPA
FROM Student
GROUP BY Branch;
```

**HAVING** — this is like WHERE but for groups. You use WHERE to filter rows BEFORE grouping, and HAVING to filter groups AFTER grouping.

```sql
-- Branches where average CGPA is above 8.0
SELECT Branch, AVG(CGPA) AS AvgCGPA
FROM Student
GROUP BY Branch
HAVING AVG(CGPA) > 8.0;

-- Branches with more than 2 students
SELECT Branch, COUNT(*) AS StudentCount
FROM Student
GROUP BY Branch
HAVING COUNT(*) > 2;

-- Combined: branches with > 1 student AND avg CGPA > 8.0
SELECT Branch, COUNT(*) AS StudentCount, AVG(CGPA) AS AvgCGPA
FROM Student
WHERE Enrollment_Year = 2022          -- Filter rows first (only 2022 batch)
GROUP BY Branch                        -- Then group
HAVING COUNT(*) > 1 AND AVG(CGPA) > 8.0;  -- Then filter groups
```

**The ORDER of Execution in SQL** (This is conceptual — very important for understanding!)

SQL doesn't execute in the order you write it. The actual order is:
1. FROM (which table)
2. WHERE (filter rows)
3. GROUP BY (create groups)
4. HAVING (filter groups)
5. SELECT (choose columns)
6. ORDER BY (sort output)

This is why you CANNOT use a column alias (defined in SELECT) inside WHERE or GROUP BY — the alias hasn't been created yet when WHERE runs!

### Problem Statements from PPT — Working Through Them

**PS01 — Create and query this table:**

```sql
CREATE TABLE Tbl_Employee_PS (Emp_Id INT);
INSERT INTO Tbl_Employee_PS VALUES (2),(5),(6),(6),(7),(8),(8);
SELECT * FROM Tbl_Employee_PS;
```

Task: Find the maximum Emp_Id excluding the overall maximum.
```sql
SELECT MAX(Emp_Id) AS Second_Max
FROM Tbl_Employee_PS
WHERE Emp_Id < (SELECT MAX(Emp_Id) FROM Tbl_Employee_PS);
-- Output: 7
```

**PS03 — Extract email domain:**

```sql
CREATE TABLE Tbl_Employee_Email (E_ID INT, E_EMAIL VARCHAR(100));
INSERT INTO Tbl_Employee_Email VALUES (1,'AMAN@GMAIL.COM'),(2,'SHREYA@OUTLOOK.COM'),(3,'PIYUSH@HOTMAIL.COM');

SELECT SUBSTRING(E_EMAIL, CHARINDEX('@', E_EMAIL) + 1, LEN(E_EMAIL)) AS Domains
FROM Tbl_Employee_Email;
-- CHARINDEX('@', E_EMAIL) finds the position of '@'
-- SUBSTRING starts from one position after '@', until end of string
```

**PS — Authors who viewed their own article:**

```sql
CREATE TABLE View_table (Article_id INT, Author_id INT, Viewer_id INT, View_date DATE);
INSERT INTO View_table VALUES
(1,3,5,'2019-08-01'),(1,3,6,'2019-08-02'),(2,7,7,'2019-08-01'),
(2,7,6,'2019-08-02'),(4,7,1,'2019-07-22'),(3,4,4,'2019-07-21'),(3,4,4,'2019-07-21');

SELECT DISTINCT Author_id AS ID
FROM View_table
WHERE Author_id = Viewer_id  -- Author and viewer are the same person
ORDER BY ID;
-- Output: 4, 7
```

---

## TOPIC 9: SQL Server JOINs (40 minutes)

### Why Do We Need JOINs?

In a normalized database, data is split across multiple tables. JOINs let you combine them back together in your query results, based on a matching column.

**Setup: Create two tables for all JOIN examples:**

```sql
CREATE TABLE Dept (
    DeptID INT PRIMARY KEY,
    DeptName VARCHAR(50)
);

CREATE TABLE Emp (
    EmpID INT PRIMARY KEY,
    EName VARCHAR(50),
    DeptID INT,  -- FK reference to Dept
    Salary INT
);

INSERT INTO Dept VALUES (1,'Engineering'),(2,'HR'),(3,'Sales'),(4,'Marketing');

INSERT INTO Emp VALUES
(1,'Aman',1,50000),
(2,'Priya',2,40000),
(3,'Rohit',1,70000),
(4,'Neha',3,55000),
(5,'Arjun',NULL,45000);  -- Arjun is not in any department
-- Note: Department 4 (Marketing) has no employees
```

### INNER JOIN — Only Matching Rows

Returns rows where there's a match in BOTH tables. If a row in either table has no match, it's excluded.

```sql
SELECT e.EmpID, e.EName, d.DeptName, e.Salary
FROM Emp e
INNER JOIN Dept d ON e.DeptID = d.DeptID;

-- Arjun (DeptID=NULL) is NOT shown — no match in Dept
-- Marketing department is NOT shown — no employees have DeptID=4
```

### LEFT JOIN (LEFT OUTER JOIN) — All From Left, Match From Right

Returns ALL rows from the left table. For rows in the left table that have no match in the right table, the right side columns show NULL.

```sql
SELECT e.EmpID, e.EName, d.DeptName, e.Salary
FROM Emp e
LEFT JOIN Dept d ON e.DeptID = d.DeptID;

-- Arjun IS shown (left table row) but DeptName = NULL
-- Marketing is still NOT shown (it's in the RIGHT table)
```

### RIGHT JOIN (RIGHT OUTER JOIN) — All From Right, Match From Left

Returns ALL rows from the right table. Left side shows NULL for non-matching rows.

```sql
SELECT e.EmpID, e.EName, d.DeptName
FROM Emp e
RIGHT JOIN Dept d ON e.DeptID = d.DeptID;

-- Marketing IS shown (right table row) but EName = NULL
-- Arjun is NOT shown (he's in the left table with no match)
```

### FULL OUTER JOIN — Everything From Both

Returns ALL rows from both tables. NULL fills in where there's no match.

```sql
SELECT e.EmpID, e.EName, d.DeptName
FROM Emp e
FULL OUTER JOIN Dept d ON e.DeptID = d.DeptID;

-- Both Arjun (no dept) AND Marketing (no emp) are shown
```

### SELF JOIN — A Table Joins With Itself

Used when you have a hierarchical relationship inside one table (like Employee and Manager where both are in the same table).

```sql
-- PS06: Employee table with Manager relationship
CREATE TABLE Employee_Self (
    EMPID INT, ENAME VARCHAR(30), SALARY INT, MANAGERID INT
);
INSERT INTO Employee_Self VALUES
(1,'AMAN',50000,NULL),(2,'SHREYA',40000,1),(3,'PIYUSH',70000,1),
(4,'NEHA',55000,NULL),(5,'NITIKA',65000,4),(6,'MANISH',50000,4);

-- PS06: Find employees whose salary is greater than their manager's
SELECT e.EMPID, e.ENAME
FROM Employee_Self e
INNER JOIN Employee_Self m ON e.MANAGERID = m.EMPID
WHERE e.SALARY > m.SALARY;
-- Output: Piyush (70000 > Aman's 50000), Nitika (65000 > Neha's 55000)

-- PS07: Show each employee with their manager's name
-- Employee table where EMPID 1 has manager 2 and EMPID 2 has manager 1
CREATE TABLE Employee_Manager (EMPID INT, ENAME VARCHAR(10), MANAGERID INT);
INSERT INTO Employee_Manager VALUES (1,'A',2),(2,'B',1);

SELECT e.EMPID, e.ENAME, m.ENAME AS Manager_name
FROM Employee_Manager e
INNER JOIN Employee_Manager m ON e.MANAGERID = m.EMPID;
```

### PS08: LEFT JOIN for NPV Problem

```sql
-- Create the tables
CREATE TABLE NPV_tbl (ID INT, YEAR INT, NPV INT);
CREATE TABLE Queries_tbl (ID INT, YEAR INT);

INSERT INTO NPV_tbl VALUES (1,2018,100),(7,2020,30),(13,2019,40),(1,2019,113),
                            (2,2008,121),(3,2009,12),(11,2020,99);
INSERT INTO Queries_tbl VALUES (1,2019),(2,2008),(3,2009),(7,2018),(7,2019),(7,2020),(13,2019);

-- For each query, find NPV or 0 if not available
SELECT q.ID, q.YEAR, ISNULL(n.NPV, 0) AS NPV   -- ISNULL replaces NULL with 0
FROM Queries_tbl q
LEFT JOIN NPV_tbl n ON q.ID = n.ID AND q.YEAR = n.YEAR
ORDER BY q.ID, q.YEAR;
-- The join condition uses BOTH ID AND YEAR to match
```

---

## TOPIC 10: Sub-Queries (25 minutes)

A sub-query (also called a nested query or inner query) is a query written inside another query. The inner query runs first, and its result is used by the outer query.

Think of it like a function call in programming — the inner function executes and returns a value, which the outer function uses.

```sql
-- Setup: using the Tbl_Employee table from before
CREATE TABLE Tbl_Emp_Sub (Emp_Id INT);
INSERT INTO Tbl_Emp_Sub VALUES (2),(5),(6),(6),(7),(8),(8);

-- Find max Emp_Id that appears only once (appears exactly 1 time)
-- The inner query finds IDs that appear MORE than once
SELECT MAX(Emp_Id) AS Result
FROM Tbl_Emp_Sub
WHERE Emp_Id NOT IN (
    SELECT Emp_Id
    FROM Tbl_Emp_Sub
    GROUP BY Emp_Id
    HAVING COUNT(*) > 1     -- IDs that appear more than once: 6, 8
);
-- After excluding 6 and 8, the remaining IDs are 2, 5, 7. MAX = 7
```

**PS02 — Update scores to max per department:**

```sql
CREATE TABLE Tbl_Employee_Dept (EID INT, DEPT VARCHAR(5), SCORES DECIMAL(5,2));
INSERT INTO Tbl_Employee_Dept VALUES
(1,'D1',1),(2,'D1',5.28),(3,'D1',4),(4,'D2',8),(5,'D1',2.5),(6,'D2',7),(7,'D3',9),(8,'D4',10.2);

-- Update each row's SCORE to the max score in its department
UPDATE Tbl_Employee_Dept
SET SCORES = (
    SELECT MAX(t2.SCORES)
    FROM Tbl_Employee_Dept t2
    WHERE t2.DEPT = Tbl_Employee_Dept.DEPT   -- Correlated: inner query references outer query's row
);
-- This is a CORRELATED sub-query — it runs once per row of the outer query

SELECT * FROM Tbl_Employee_Dept;  -- Now all D1 rows show 5.28, etc.
```

**PS03 — Customers who never ordered (LEFT JOIN approach + subquery approach):**

```sql
CREATE TABLE Tbl_Customers (ID INT, NAME VARCHAR(30));
CREATE TABLE Tbl_Orders (ID INT, CustomerID INT);
INSERT INTO Tbl_Customers VALUES (1,'Joe'),(2,'Henry'),(3,'Sam'),(4,'Max');
INSERT INTO Tbl_Orders VALUES (1,3),(2,1);

-- Method 1: Using NOT IN subquery
SELECT NAME AS CustomerNames
FROM Tbl_Customers
WHERE ID NOT IN (SELECT CustomerID FROM Tbl_Orders);

-- Method 2: Using LEFT JOIN (generally preferred for performance)
SELECT c.NAME AS CustomerNames
FROM Tbl_Customers c
LEFT JOIN Tbl_Orders o ON c.ID = o.CustomerID
WHERE o.CustomerID IS NULL;   -- NULL means this customer had no match in Orders
```

**PS04 — Customers who bought ALL products:**

```sql
CREATE TABLE Tbl_Purchases (customer_id INT, product_key INT);
CREATE TABLE Tbl_Products2 (product_key INT);
INSERT INTO Tbl_Purchases VALUES (1,5),(2,6),(3,5),(3,6),(1,6);
INSERT INTO Tbl_Products2 VALUES (5),(6);

-- Find customers who have bought EVERY product in Tbl_Products2
SELECT customer_id
FROM Tbl_Purchases
GROUP BY customer_id
HAVING COUNT(DISTINCT product_key) = (SELECT COUNT(*) FROM Tbl_Products2);
-- Total products = 2. Customers with 2 distinct products: 1 and 3
```

---

## TOPIC 11: Set Operations (20 minutes)

Set operations combine the results of two SELECT queries. The key rule is: **both queries must return the same number of columns with compatible data types.**

**Setup:**

```sql
CREATE TABLE Tbl_Emp_A (EmpID INT, Ename VARCHAR(20), Salary INT);
CREATE TABLE Tbl_Emp_B (EmpID INT, Ename VARCHAR(20), Salary INT);
INSERT INTO Tbl_Emp_A VALUES (1,'AA',1000),(2,'BB',300);
INSERT INTO Tbl_Emp_B VALUES (2,'BB',400),(3,'CC',100);
```

**UNION** — Combines results from both queries and removes duplicates.

**UNION ALL** — Same but keeps duplicates.

**INTERSECT** — Returns only rows that appear in BOTH queries.

**EXCEPT** — Returns rows from the first query that do NOT appear in the second. (Called MINUS in Oracle.)

```sql
-- UNION: All employees from either table, no duplicates
SELECT EmpID, Ename, Salary FROM Tbl_Emp_A
UNION
SELECT EmpID, Ename, Salary FROM Tbl_Emp_B;
-- Result: (1,AA,1000), (2,BB,300), (2,BB,400), (3,CC,100) → 4 rows (BB appears twice with different salaries)

-- UNION ALL: Same but keeps true duplicates (if identical rows existed)
SELECT EmpID, Ename, Salary FROM Tbl_Emp_A
UNION ALL
SELECT EmpID, Ename, Salary FROM Tbl_Emp_B;

-- INTERSECT: EmpIDs in both tables
SELECT EmpID FROM Tbl_Emp_A
INTERSECT
SELECT EmpID FROM Tbl_Emp_B;
-- Only EmpID 2 appears in both tables

-- EXCEPT: EmpIDs in A but not in B
SELECT EmpID FROM Tbl_Emp_A
EXCEPT
SELECT EmpID FROM Tbl_Emp_B;
-- EmpID 1 (AA) is in A but not B
```

**PS01 — Find EmpIDs in both tables with their lowest salary:**

```sql
-- This requires UNION ALL (to see all records combined) and MIN per group
SELECT EmpID, Ename, MIN(Salary) AS Salary
FROM (
    SELECT EmpID, Ename, Salary FROM Tbl_Emp_A
    UNION ALL
    SELECT EmpID, Ename, Salary FROM Tbl_Emp_B
) AS CombinedTable
GROUP BY EmpID, Ename
ORDER BY EmpID;
-- EmpID 1: only in A → salary 1000
-- EmpID 2: in both → min(300, 400) = 300
-- EmpID 3: only in B → salary 100
```

---

---

# DAY 2 — SESSION 1 (3 Hours)
## Topics: Views → Stored Procedures → Triggers (Automation + Security)

---

## TOPIC 12: Views — Virtual Tables (45 minutes)

### What is a View?

A **view** is a saved SELECT query that you can use like a table. It doesn't store data — it stores the query definition. Every time you select from a view, it runs the underlying query and gives you fresh data.

**Analogy:** Think of a view like a shortcut on your desktop. The shortcut isn't the actual file — it just points to it. Similarly, a view isn't actual data — it's a window into the data from one or more underlying tables.

**Why Use Views?**

Security: You can give a user access to a view without giving them access to the underlying table. For example, show HR only the employee's name and department, not their salary.

Simplicity: A complex query joining 5 tables can be hidden behind a simple view. Users just `SELECT * FROM EmployeeSummaryView` without knowing the complexity.

Reusability: Write the complex query once as a view, use it many times.

### Creating and Using Views

```sql
USE workshop_db;

-- Basic view: show only relevant student columns
CREATE VIEW vw_StudentSummary AS
SELECT StudentID, Name, Branch, CGPA
FROM Student
WHERE CGPA >= 8.0;  -- Only show high-performing students

-- Now use the view exactly like a table
SELECT * FROM vw_StudentSummary;
SELECT * FROM vw_StudentSummary WHERE Branch = 'CS';

-- View joining multiple tables
CREATE VIEW vw_EmpDeptDetails AS
SELECT e.EmpID, e.EName, e.Salary, d.DeptName
FROM Emp e
INNER JOIN Dept d ON e.DeptID = d.DeptID;

SELECT * FROM vw_EmpDeptDetails;
SELECT DeptName, AVG(Salary) AS AvgSalary FROM vw_EmpDeptDetails GROUP BY DeptName;

-- Drop a view when no longer needed
DROP VIEW vw_StudentSummary;

-- Update a view definition
ALTER VIEW vw_StudentSummary AS
SELECT StudentID, Name, Branch, CGPA, Enrollment_Year
FROM Student;
```

**Security Demonstration:**

```sql
-- Create a "masked" view for sensitive data
-- HR can see names and departments but NOT salaries
CREATE VIEW vw_EmpPublic AS
SELECT EmpID, EName, DeptID
FROM Emp;

-- If you grant access only to this view, the user can never see Salary even if they try:
-- SELECT Salary FROM vw_EmpPublic → Error: column 'Salary' does not exist in this view
```

---

## TOPIC 13: Stored Procedures — Modularity in SQL (40 minutes)

### What is a Stored Procedure?

A **stored procedure** is a saved block of SQL code that you can execute by name. It's like writing a function in Java or Python — you write it once and call it many times with different parameters.

**Analogy:** Think of a stored procedure like pressing a button on a vending machine. The machine has complex internal logic — check if item is available, calculate change, dispense item. You don't see any of that. You just press "B3" and get your chips. The stored procedure is "B3".

### Basic Stored Procedure (No Parameters)

```sql
-- A procedure that gives a report of branches and their student counts
CREATE PROCEDURE usp_BranchReport
AS
BEGIN
    SELECT Branch, COUNT(*) AS StudentCount, AVG(CGPA) AS AvgCGPA
    FROM Student
    GROUP BY Branch
    ORDER BY AvgCGPA DESC;
END;

-- Execute (call) it
EXECUTE usp_BranchReport;
-- Or shorter:
EXEC usp_BranchReport;
```

### Stored Procedure With Parameters

```sql
-- A procedure that finds students by branch (user provides the branch name)
CREATE PROCEDURE usp_GetStudentsByBranch
    @BranchName VARCHAR(30)     -- Input parameter
AS
BEGIN
    SELECT StudentID, Name, CGPA
    FROM Student
    WHERE Branch = @BranchName
    ORDER BY CGPA DESC;
END;

-- Calling it with different parameters
EXEC usp_GetStudentsByBranch 'CS';
EXEC usp_GetStudentsByBranch 'IT';
```

### Stored Procedure With Multiple Parameters

```sql
-- Get students filtered by both branch and minimum CGPA
CREATE PROCEDURE usp_GetTopStudents
    @BranchName VARCHAR(30),
    @MinCGPA DECIMAL(3,1)
AS
BEGIN
    SELECT StudentID, Name, CGPA
    FROM Student
    WHERE Branch = @BranchName AND CGPA >= @MinCGPA
    ORDER BY CGPA DESC;
END;

EXEC usp_GetTopStudents 'CS', 8.0;
```

### Using Variables Inside Stored Procedures

```sql
CREATE PROCEDURE usp_StudentCGPACheck
    @StudentID INT
AS
BEGIN
    DECLARE @StudentCGPA DECIMAL(3,1);   -- Declare a variable
    DECLARE @StudentName VARCHAR(50);

    -- Fetch the values into variables
    SELECT @StudentCGPA = CGPA, @StudentName = Name
    FROM Student
    WHERE StudentID = @StudentID;

    -- Use IF/ELSE logic
    IF @StudentCGPA >= 9.0
        PRINT @StudentName + ' is a TOP performer with CGPA: ' + CAST(@StudentCGPA AS VARCHAR);
    ELSE IF @StudentCGPA >= 7.5
        PRINT @StudentName + ' is doing GOOD with CGPA: ' + CAST(@StudentCGPA AS VARCHAR);
    ELSE
        PRINT @StudentName + ' needs improvement. CGPA: ' + CAST(@StudentCGPA AS VARCHAR);
END;

EXEC usp_StudentCGPACheck 101;
EXEC usp_StudentCGPACheck 105;
```

### Modify and Delete Stored Procedures

```sql
-- Modify
ALTER PROCEDURE usp_BranchReport
AS
BEGIN
    SELECT Branch, COUNT(*) AS TotalStudents, MAX(CGPA) AS TopCGPA, AVG(CGPA) AS AvgCGPA
    FROM Student
    GROUP BY Branch;
END;

-- Delete
DROP PROCEDURE usp_StudentCGPACheck;
```

---

## TOPIC 14: Triggers — Automation and Security (50 minutes)

### What is a Trigger?

A **trigger** is code that automatically runs when a specific event happens on a table. You don't call it — it fires on its own when data is inserted, updated, or deleted.

**Analogy:** A trigger is exactly like an event listener in Java/JavaScript. `element.addEventListener('click', handleClick)` — when a click happens, handleClick fires automatically. Similarly, `AFTER INSERT ON Orders` fires your trigger code automatically every time a row is inserted into Orders. You just wrote the code once; the database takes care of calling it.

### The Three Trigger Events

`AFTER INSERT` — Fires after a row is inserted into the table.
`AFTER UPDATE` — Fires after a row is updated in the table.
`AFTER DELETE` — Fires after a row is deleted from the table.

### The Magic Tables: INSERTED and DELETED

Inside every trigger, SQL Server automatically creates two special virtual tables:

**INSERTED** — Contains the new data. Available in INSERT triggers (the rows just added) and UPDATE triggers (the NEW values of the updated rows).

**DELETED** — Contains the old data. Available in DELETE triggers (the rows just removed) and UPDATE triggers (the OLD values before the update).

For an UPDATE trigger, DELETED has the "before" values and INSERTED has the "after" values. This lets you compare what changed.

### Simple Trigger Example — Auto-Logging

```sql
-- We'll log every time a student is added
CREATE TABLE Student_AuditLog (
    LogID INT IDENTITY(1,1) PRIMARY KEY,
    StudentID INT,
    Name VARCHAR(50),
    Action VARCHAR(20),
    ActionTime DATETIME DEFAULT GETDATE()
);

-- Trigger: after every INSERT into Student, add a log entry
CREATE TRIGGER trg_StudentInsertLog
ON Student
AFTER INSERT
AS
BEGIN
    INSERT INTO Student_AuditLog (StudentID, Name, Action)
    SELECT StudentID, Name, 'INSERTED'
    FROM INSERTED;   -- INSERTED table contains the just-added rows
END;

-- Test it
INSERT INTO Student VALUES (107, 'Vikram Das', 'CS', 7.5, 'vikram@uni.com', 2024);
SELECT * FROM Student_AuditLog;  -- Automatically logged!
```

### Security Trigger — Preventing Bad Data

```sql
-- Trigger: Prevent salary from being reduced
CREATE TRIGGER trg_NoSalaryReduction
ON Emp
AFTER UPDATE
AS
BEGIN
    IF EXISTS (
        SELECT 1
        FROM INSERTED i
        INNER JOIN DELETED d ON i.EmpID = d.EmpID
        WHERE i.Salary < d.Salary   -- New salary (INSERTED) is less than old salary (DELETED)
    )
    BEGIN
        PRINT 'ERROR: Salary cannot be reduced!';
        ROLLBACK TRANSACTION;  -- Cancel the entire UPDATE
    END
END;

-- Test it
UPDATE Emp SET Salary = 60000 WHERE EmpID = 1;  -- Allowed (increase)
SELECT * FROM Emp WHERE EmpID = 1;

UPDATE Emp SET Salary = 10000 WHERE EmpID = 1;  -- REJECTED by trigger!
SELECT * FROM Emp WHERE EmpID = 1;  -- Salary unchanged
```

### Trigger for Security with Views

```sql
-- INSTEAD OF trigger: runs INSTEAD OF the actual INSERT into the view
-- This lets you make views "updatable" with custom logic
CREATE VIEW vw_StudentEditable AS
SELECT StudentID, Name, Branch FROM Student;

CREATE TRIGGER trg_StudentViewInsert
ON vw_StudentEditable
INSTEAD OF INSERT
AS
BEGIN
    -- Only allow inserts where the branch is a valid one
    IF EXISTS (SELECT 1 FROM INSERTED WHERE Branch NOT IN ('CS','IT','ECE','ME'))
    BEGIN
        PRINT 'Invalid branch name! Only CS, IT, ECE, ME allowed.';
        ROLLBACK TRANSACTION;
    END
    ELSE
    BEGIN
        INSERT INTO Student (StudentID, Name, Branch)
        SELECT StudentID, Name, Branch
        FROM INSERTED;
    END
END;

-- Test
INSERT INTO vw_StudentEditable VALUES (108, 'Test User', 'MBA');  -- Rejected!
INSERT INTO vw_StudentEditable VALUES (108, 'Test User', 'CS');   -- Allowed!
```

### DROP Trigger

```sql
DROP TRIGGER trg_StudentInsertLog;
```

---

---

# DAY 2 — SESSION 2 (3 Hours)
## Full SQL Server Backend Project: Bank Management System

---

## Project Overview

This project ties together EVERYTHING from the two days into one realistic system. You'll build a backend for a bank — without any application code. Just SQL Server.

The system has four tables and uses triggers and stored procedures to automate everything. Before writing a single line, explain the flow to students:

**The story:** A person walks into a bank and fills a physical form (that's the `account_opening_form` table). A bank officer reviews the form and marks the KYC status as Approved, Pending, or Rejected. If Approved → the system automatically (trigger!) creates a bank account and stores personal details. The customer then does transactions (debit/credit). A stored procedure lets them print their bank statement.

```
account_opening_form  ──[KYC=Approved]──► bank (account created automatically via trigger)
                                    └───► AccountHolderDetail (personal info stored automatically)

TransactionDetail ──[INSERT]──► bank.CurrentBalance updated automatically via trigger

bank ──[Stored Procedure]──► Payment statement for last N months
```

### STEP 1: Create the Database and All Tables

```sql
-- Create a fresh database
CREATE DATABASE banking_system_project;
USE banking_system_project;

-- TABLE 1: Account Opening Form
-- This is the "physical form" a customer fills when they come to the bank
CREATE TABLE account_opening_form (
    [date]                   DATE DEFAULT GETDATE(),        -- Default: today's date
    Account_type             VARCHAR(20) DEFAULT 'saving',  -- Default: savings account
    Account_HolderName       VARCHAR(50),
    DOB                      DATE,
    AadharNumber             VARCHAR(12) UNIQUE,            -- No duplicate Aadhaar cards
    MobileNumber             VARCHAR(15),
    Account_opening_balance  DECIMAL(10,2)                  CHECK (Account_opening_balance >= 1000),
                             -- Minimum balance = ₹1000
    FullAddress              VARCHAR(100),
    KYC_Status               VARCHAR(20) DEFAULT 'pending'  -- pending / Approved / Rejected
);

-- TABLE 2: Bank (The actual bank account, created only after KYC approval)
-- AccountNumber uses IDENTITY — starts at 1000000000, increments by 1 automatically
CREATE TABLE bank (
    AccountNumber       BIGINT IDENTITY(1000000000, 1),  -- Auto-generated, starting from 1 billion
    AccountType         VARCHAR(20),
    AccountOpeningDate  DATE DEFAULT GETDATE(),
    CurrentBalance      DECIMAL(10,2)
);

-- TABLE 3: Account Holder Details (Personal info, auto-populated after KYC approval)
CREATE TABLE AccountHolderDetail (
    AccountNumber    BIGINT IDENTITY(1000000000, 1),  -- Same starting point as bank table
    Account_HolderName  VARCHAR(50),
    DOB              DATE,
    AadharNumber     VARCHAR(12),
    MobileNumber     VARCHAR(15),
    FullAddress      VARCHAR(100)
);

-- TABLE 4: Transaction Details (Customer deposits and withdrawals)
CREATE TABLE TransactionDetail (
    AccountNumber       BIGINT,
    PaymentType         VARCHAR(20),        -- 'credit' or 'debit'
    TransactionAmount   DECIMAL(10,2),
    DateofTransaction   DATE DEFAULT GETDATE()
);

-- Verify all tables are created
SELECT * FROM account_opening_form;
SELECT * FROM bank;
SELECT * FROM AccountHolderDetail;
SELECT * FROM TransactionDetail;
```

**Explain the design to students:** Why is there no explicit `ID` column in `account_opening_form`? The original design accepts the form by Aadhaar number (which is unique). The bank account number is auto-generated by `IDENTITY` — the bank assigns this, not the customer.

### STEP 2: Fill the Application Form (Simulating a Customer)

```sql
-- Scenario: Navin comes to the bank and fills the form
INSERT INTO account_opening_form (Account_type, Account_HolderName, DOB, AadharNumber, MobileNumber, Account_opening_balance, FullAddress)
VALUES ('saving', 'Navin', '1999-08-24', '575854562826', '9568226569', 1000, 'delhi');

-- Verify
SELECT * FROM account_opening_form;
-- You'll see KYC_Status = 'pending' (default) and [date] = today's date
-- The bank table is still EMPTY — no account yet
```

### STEP 3: Create Trigger 1 — Auto-Create Account on KYC Approval

This is the most important trigger. It watches the `account_opening_form` table. When a bank officer updates the KYC_Status to 'Approved', the trigger fires and automatically creates entries in the `bank` and `AccountHolderDetail` tables.

```sql
CREATE TRIGGER tr_forinsertdata
ON account_opening_form
AFTER UPDATE           -- Fires when any UPDATE happens on this table
AS
BEGIN
    -- Step 1: Declare variables to hold the data from the updated row
    DECLARE @status               VARCHAR(20),
            @Account_type         VARCHAR(20),
            @Account_HolderName   VARCHAR(50),
            @DOB                  DATE,
            @AadharNumber         VARCHAR(12),
            @MobileNumber         VARCHAR(15),
            @Account_opening_balance DECIMAL(10,2),
            @FullAddress          VARCHAR(100);

    -- Step 2: Read the updated row from the INSERTED virtual table
    -- INSERTED contains the NEW values after the update
    SELECT
        @status                  = KYC_Status,
        @Account_type            = Account_type,
        @Account_HolderName      = Account_HolderName,
        @DOB                     = DOB,
        @AadharNumber            = AadharNumber,
        @MobileNumber            = MobileNumber,
        @Account_opening_balance = Account_opening_balance,
        @FullAddress             = FullAddress
    FROM INSERTED;

    -- Step 3: Only do something if KYC was approved
    IF @status = 'Approved'
    BEGIN
        -- Create the bank account (AccountNumber auto-generated by IDENTITY)
        INSERT INTO bank (AccountType, CurrentBalance)
        VALUES (@Account_type, @Account_opening_balance);

        -- Store personal details
        INSERT INTO AccountHolderDetail (Account_HolderName, DOB, AadharNumber, MobileNumber, FullAddress)
        VALUES (@Account_HolderName, @DOB, @AadharNumber, @MobileNumber, @FullAddress);
    END
    -- If status is 'Rejected' or anything else, nothing happens — no account is created

END;
```

**Explain the trigger to students:** "Imagine a bank manager sitting at a computer. Whenever any row in the application form table is updated, this trigger is like a watchdog that wakes up and checks: 'Was the KYC status just changed to Approved?' If yes, it immediately creates the bank account without the manager having to do anything else. This is automation."

### STEP 4: Test the KYC Approval Trigger

```sql
-- Bank officer approves Navin's KYC
UPDATE account_opening_form
SET KYC_Status = 'Approved'
WHERE AadharNumber = '575854562826';

-- Now check all tables
SELECT * FROM account_opening_form;    -- KYC_Status = 'Approved'
SELECT * FROM bank;                    -- Account created! AccountNumber = 1000000000, Balance = 1000
SELECT * FROM AccountHolderDetail;     -- Personal details populated!
```

**Now test rejection — add another customer whose KYC gets rejected:**

```sql
INSERT INTO account_opening_form (Account_type, Account_HolderName, DOB, AadharNumber, MobileNumber, Account_opening_balance, FullAddress)
VALUES ('saving', 'Tuntun', '1999-08-20', '545854562887', '9875645545', 1000, 'delhi');

-- Reject this one
UPDATE account_opening_form
SET KYC_Status = 'Rejected'
WHERE AadharNumber = '545854562887';

SELECT * FROM bank;  -- Still only 1 account — Tuntun's application was rejected, no account created
SELECT * FROM AccountHolderDetail;  -- Tuntun's details are NOT here
```

### STEP 5: Set Up Foreign Keys

Now that the bank accounts are created, we set up foreign key relationships. The `bank` table's `AccountNumber` becomes the primary key. Both `TransactionDetail` and `AccountHolderDetail` will reference it.

**Why didn't we add FK from the start?** Because the tables were being populated by triggers automatically. FK constraints during initial setup would have complicated the trigger logic. This is a common real-world pattern — design first, add constraints after.

```sql
-- Make AccountNumber in bank the Primary Key
ALTER TABLE bank
ADD CONSTRAINT PK_Bank PRIMARY KEY (AccountNumber);

-- Make TransactionDetail's AccountNumber a FK referencing bank
ALTER TABLE TransactionDetail
ADD CONSTRAINT FK_Transaction_Bank
    FOREIGN KEY (AccountNumber) REFERENCES bank(AccountNumber);

-- Make AccountHolderDetail's AccountNumber a FK referencing bank
ALTER TABLE AccountHolderDetail
ADD CONSTRAINT FK_AccountHolder_Bank
    FOREIGN KEY (AccountNumber) REFERENCES bank(AccountNumber);
```

**Effect:** Now if someone tries to insert a transaction for an account number that doesn't exist in the `bank` table, the database will reject it with a foreign key violation error.

### STEP 6: Create Trigger 2 — Auto-Update Balance on Transaction

Every time a customer deposits (credit) or withdraws (debit), the balance in the `bank` table must be updated automatically.

```sql
CREATE TRIGGER dbo.updatecurrentbalance
ON TransactionDetail
AFTER INSERT        -- Fires when a new transaction is inserted
AS
BEGIN
    -- Read the transaction details from the just-inserted row
    DECLARE @paymenttype    VARCHAR(20),
            @Amount         DECIMAL(10,2),
            @accountnumber  BIGINT;

    SELECT
        @paymenttype    = PaymentType,
        @Amount         = TransactionAmount,
        @accountnumber  = AccountNumber
    FROM INSERTED;

    -- For a credit (deposit): add to balance
    IF @paymenttype = 'credit'
    BEGIN
        UPDATE bank
        SET CurrentBalance = CurrentBalance + @Amount
        WHERE AccountNumber = @accountnumber;
    END

    -- For a debit (withdrawal): subtract from balance
    IF @paymenttype = 'debit'
    BEGIN
        UPDATE bank
        SET CurrentBalance = CurrentBalance - @Amount
        WHERE AccountNumber = @accountnumber;
    END

END;
```

### STEP 7: Test Transactions

```sql
-- Navin's account number is 1000000000

-- Navin deposits ₹5000
INSERT INTO TransactionDetail (AccountNumber, PaymentType, TransactionAmount)
VALUES (1000000000, 'credit', 5000);

SELECT * FROM bank;  -- Balance should now be 1000 + 5000 = 6000

-- Navin withdraws ₹3000
INSERT INTO TransactionDetail (AccountNumber, PaymentType, TransactionAmount)
VALUES (1000000000, 'debit', 3000);

SELECT * FROM bank;  -- Balance should now be 6000 - 3000 = 3000

-- Try inserting a transaction for a non-existent account → FK violation error!
INSERT INTO TransactionDetail (AccountNumber, PaymentType, TransactionAmount)
VALUES (9999999, 'credit', 500);  -- Error: FK constraint violated
```

### STEP 8: Create Stored Procedure — Bank Statement / Passbook

A customer comes to the bank and asks "show me my last 3 months of transactions." This is like a bank passbook update. We create a stored procedure that takes two parameters: how many months back to go, and the account number.

```sql
CREATE PROCEDURE DBO.PAYMENT_STATEMENT
    @MONTH          INT,         -- How many months back (e.g., 3 = last 3 months)
    @ACCOUNT_NUMBER BIGINT       -- Whose account
AS
BEGIN
    SELECT *
    FROM TransactionDetail
    WHERE DateofTransaction >= DATEADD(MONTH, -@MONTH, GETDATE())  -- from N months ago to now
    AND AccountNumber = @ACCOUNT_NUMBER
    ORDER BY DateofTransaction DESC;
END;

-- Get last 3 months of transactions for account 1000000000
EXECUTE DBO.PAYMENT_STATEMENT 3, 1000000000;

-- Get last 1 month
EXECUTE DBO.PAYMENT_STATEMENT 1, 1000000000;
```

**Explain DATEADD:** `DATEADD(MONTH, -3, GETDATE())` calculates a date that is 3 months before today. So the WHERE clause says "only show transactions where the date is after [3 months ago]".

### STEP 9: Add More Customers and Transactions — Full Demonstration

```sql
-- Add another customer
INSERT INTO account_opening_form (Account_type, Account_HolderName, DOB, AadharNumber, MobileNumber, Account_opening_balance, FullAddress)
VALUES ('current', 'Anjali Sharma', '1995-03-15', '123456789012', '9876543210', 5000, 'Lucknow');

-- Approve her
UPDATE account_opening_form
SET KYC_Status = 'Approved'
WHERE AadharNumber = '123456789012';

-- Check accounts
SELECT * FROM bank;
-- Anjali gets AccountNumber = 1000000001 automatically

-- Do some transactions for Anjali
INSERT INTO TransactionDetail (AccountNumber, PaymentType, TransactionAmount)
VALUES (1000000001, 'credit', 20000);

INSERT INTO TransactionDetail (AccountNumber, PaymentType, TransactionAmount)
VALUES (1000000001, 'debit', 5000);

INSERT INTO TransactionDetail (AccountNumber, PaymentType, TransactionAmount)
VALUES (1000000001, 'credit', 3000);

-- Check final balances
SELECT * FROM bank;

-- Get Anjali's statement for last 3 months
EXECUTE DBO.PAYMENT_STATEMENT 3, 1000000001;

-- Get all transactions combined view with account holder name
SELECT t.AccountNumber, h.Account_HolderName, t.PaymentType, t.TransactionAmount, t.DateofTransaction
FROM TransactionDetail t
INNER JOIN AccountHolderDetail h ON t.AccountNumber = h.AccountNumber
ORDER BY t.DateofTransaction DESC;
```

### Complete Project Summary

Write this on the board and walk through it with students:

**Table 1 — account_opening_form:** Customer fills this. Bank officer updates KYC_Status. This is the only table manipulated directly.

**Table 2 — bank:** Never filled manually. Created automatically by Trigger 1 when KYC is approved. Updated automatically by Trigger 2 on every transaction.

**Table 3 — AccountHolderDetail:** Never filled manually. Created automatically by Trigger 1 when KYC is approved.

**Table 4 — TransactionDetail:** Bank teller inserts rows here when customer deposits or withdraws. Trigger 2 watches this and auto-updates bank balance.

**Trigger 1 (tr_forinsertdata):** Watches account_opening_form. On every UPDATE, checks if KYC_Status = 'Approved'. If yes, auto-creates entries in bank and AccountHolderDetail.

**Trigger 2 (updatecurrentbalance):** Watches TransactionDetail. On every INSERT, reads the payment type and amount, then updates the CurrentBalance in the bank table accordingly.

**Stored Procedure (PAYMENT_STATEMENT):** Takes account number and month count as parameters. Returns the transaction history for that period — like a passbook.

### Discussion Questions for the Class

Ask these to wrap up the project:

"What happens if the same Aadhaar number tries to register twice?" (The UNIQUE constraint on AadharNumber in account_opening_form will reject it.)

"What would happen if we tried to delete an account from the bank table that still has transactions?" (FK constraint from TransactionDetail to bank would prevent deletion — to delete, you'd need to delete all transactions first, or use CASCADE.)

"Can we enhance this system to reject a transaction if the debit amount is more than the current balance?" (Yes — we'd add an IF condition inside the debit section of Trigger 2 to check `CurrentBalance >= @Amount` before subtracting, and ROLLBACK if not.)

"Can the PAYMENT_STATEMENT procedure be enhanced to show the running balance?" (Great question — yes, using window functions like `SUM() OVER (ORDER BY DateofTransaction)` added to the query.)

---

## QUICK REFERENCE CARD

### Essential T-SQL Syntax (SQL Server Specific)

```sql
-- Get today's date
SELECT GETDATE();

-- Add/subtract from a date
SELECT DATEADD(MONTH, -3, GETDATE());   -- 3 months ago
SELECT DATEADD(DAY, 7, GETDATE());      -- 7 days from now

-- Date difference
SELECT DATEDIFF(DAY, '2024-01-01', GETDATE());   -- Days since Jan 1 2024

-- String functions
SELECT LEN('Hello');                              -- Length: 5
SELECT SUBSTRING('Hello World', 7, 5);           -- 'World'
SELECT CHARINDEX('@', 'test@gmail.com');          -- Position of '@': 5
SELECT UPPER('hello');                            -- 'HELLO'
SELECT LOWER('HELLO');                            -- 'hello'
SELECT LTRIM(RTRIM('  hello  '));                 -- 'hello' (trim spaces)

-- Handle NULLs
SELECT ISNULL(NULL, 0);                           -- Returns 0 if NULL
SELECT COALESCE(NULL, NULL, 'first non-null');    -- Returns 'first non-null'

-- Top N rows (SQL Server uses TOP, not LIMIT)
SELECT TOP 5 * FROM Student ORDER BY CGPA DESC;

-- Case expression (like if-else in SQL)
SELECT Name, CGPA,
    CASE
        WHEN CGPA >= 9.0 THEN 'Excellent'
        WHEN CGPA >= 8.0 THEN 'Very Good'
        WHEN CGPA >= 7.0 THEN 'Good'
        ELSE 'Average'
    END AS Performance
FROM Student;

-- Auto-increment in SQL Server (not AUTO_INCREMENT like MySQL)
AccountNumber BIGINT IDENTITY(1000000000, 1)
-- IDENTITY(start_value, increment_by)
```

### Join Quick Reference

```
Type            | Left Table | Right Table | Notes
----------------|------------|-------------|------
INNER JOIN      | Match only | Match only  | Most common
LEFT JOIN       | ALL rows   | Match only  | NULLs for non-matching right rows
RIGHT JOIN      | Match only | ALL rows    | NULLs for non-matching left rows
FULL OUTER JOIN | ALL rows   | ALL rows    | NULLs on both sides where no match
SELF JOIN       | Table joins itself | Use aliases (e, m) to differentiate
```

### Normalization Quick Reference

```
1NF  → Atomic values only (no comma-separated lists in cells)
2NF  → 1NF + No partial dependency (non-prime depends on FULL CK only)
       Violation: X→Y where X is PART of CK and Y is non-prime
3NF  → 2NF + No transitive dependency
       Check: for every X→Y, either X is superkey OR Y is prime
BCNF → 3NF + For every X→Y, X must be a superkey (stricter)
```

### Trigger Quick Reference

```
Event              | INSERTED has    | DELETED has
-------------------|-----------------|-------------------
AFTER INSERT       | New rows        | (empty)
AFTER UPDATE       | New values      | Old values
AFTER DELETE       | (empty)         | Deleted rows
INSTEAD OF         | Proposed data   | Existing data (for views)
```

---

*End of 12-Hour DBMS Workshop Teaching Guide*
*Prepared for CU Lucknow DCPD Workshop*
