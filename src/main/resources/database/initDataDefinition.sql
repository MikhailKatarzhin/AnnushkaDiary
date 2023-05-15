-- -----------------------------------------------------
-- Schema psychodiary
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Table `psychodiary`.`Role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "Role" (
    id BIGSERIAL NOT NULL,
    name VARCHAR(20) NOT NULL,
    CONSTRAINT "PK_Role_Id" PRIMARY KEY (id),
    CONSTRAINT "UQ_Role_Name" UNIQUE (name)
);

-- -----------------------------------------------------
-- Table `psychodiary`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "User" (
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    hash_password VARCHAR(60) NOT NULL,
    id BIGSERIAL NOT NULL,
    CONSTRAINT "PK_User_Id" PRIMARY KEY (id),
    CONSTRAINT "UQ_User_Username" UNIQUE (name),
    CONSTRAINT "UQ_Email" UNIQUE (email),
    CONSTRAINT "CK_User_Name" CHECK (name::text ~ '^[A-Za-z0-9 А-Яа-яЁё_-]{2,50}'::text),
    CONSTRAINT "CK_Email" CHECK (email::text ~ '^[a-zA-Z0-9.!#$%&''*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$'::text)
);

-- -----------------------------------------------------
-- Table `psychodiary`.`User_Role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "User_Role"
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT "PK_User_Role" PRIMARY KEY (user_id, role_id),
    CONSTRAINT "FK_User_has_User_role" FOREIGN KEY (user_id)
        REFERENCES "User" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE NO ACTION,
    CONSTRAINT "FK_User_role_Occupies_Role" FOREIGN KEY (role_id)
        REFERENCES "Role" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE NO ACTION
);

-- -----------------------------------------------------
-- Table `psychodiary`.`Test`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "Test" (
    id BIGSERIAL NOT NULL,
    title VARCHAR(150) NOT NULL,
    description VARCHAR(5000) NOT NULL,
    CONSTRAINT "PK_Test_Id" PRIMARY KEY (id),
    CONSTRAINT "UQ_Test_Title" UNIQUE (title)
);

-- -----------------------------------------------------
-- Table `psychodiary`.`Training`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "Training" (
    id BIGSERIAL NOT NULL,
    title VARCHAR(150) NOT NULL,
    description VARCHAR(5000) NOT NULL,
    CONSTRAINT "PK_Training_Id" PRIMARY KEY (id),
    CONSTRAINT "UQ_Training_Title" UNIQUE (title)
);

-- -----------------------------------------------------
-- Table `psychodiary`.`Task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "Task" (
    id BIGSERIAL NOT NULL,
    description VARCHAR(5000) NOT NULL,
    CONSTRAINT "PK_Task_Id" PRIMARY KEY (id),
    CONSTRAINT "UQ_Task_Description" UNIQUE (description)
);

-- -----------------------------------------------------
-- Table `psychodiary`.`Training_Task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "Training_Task"
(
    training_id bigint NOT NULL,
    task_id bigint NOT NULL,
    CONSTRAINT "PK_Training_Task" PRIMARY KEY (training_id, task_id),
    CONSTRAINT "FK_Training_has_Task" FOREIGN KEY (task_id)
        REFERENCES "Task" (id) MATCH SIMPLE
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    CONSTRAINT "FK_Task_occupies_Training" FOREIGN KEY (training_id)
        REFERENCES "Training" (id) MATCH SIMPLE
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table `psychodiary`.`Test_Task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "Test_Task"
(
    test_id bigint NOT NULL,
    task_id bigint NOT NULL,
    CONSTRAINT "PK_Test_Task" PRIMARY KEY (test_id, task_id),
    CONSTRAINT "FK_Test_has_Task" FOREIGN KEY (task_id)
        REFERENCES "Task" (id) MATCH SIMPLE
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    CONSTRAINT "FK_Task_occupies_Test" FOREIGN KEY (test_id)
        REFERENCES "Test" (id) MATCH SIMPLE
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table `psychodiary`.`Answer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "Answer"
(
    id BIGSERIAL NOT NULL,
    description VARCHAR(5000) NOT NULL,
    CONSTRAINT PK_Answer_Id PRIMARY KEY (id),
    CONSTRAINT "UQ_Answer_Description" UNIQUE (description)
);

-- -----------------------------------------------------
-- Table `psychodiary`.`Task_Answer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "Task_Answer" (
    answer_id bigint NOT NULL,
    task_id bigint NOT NULL,
    CONSTRAINT "PK_Answer_Task" PRIMARY KEY (answer_id, task_id),
    CONSTRAINT "FK_Task_has_Answer" FOREIGN KEY (answer_id)
        REFERENCES "Answer" (id) MATCH SIMPLE
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    CONSTRAINT "FK_Answer_occupies_Task" FOREIGN KEY (task_id)
        REFERENCES "Task" (id) MATCH SIMPLE
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table `psychodiary`.`Task_Correct_Answer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "Task_Correct_Answer" (
    answer_id bigint NOT NULL,
    task_id bigint NOT NULL,
    CONSTRAINT "PK_Correct_Answer_Task" PRIMARY KEY (answer_id, task_id),
    CONSTRAINT "FK_Task_has_Correct_Answer" FOREIGN KEY (answer_id)
    REFERENCES "Answer" (id) MATCH SIMPLE
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    CONSTRAINT "FK_Correct_Answer_occupies_Task" FOREIGN KEY (task_id)
    REFERENCES "Task" (id) MATCH SIMPLE
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table `psychodiary`.`Training_try`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "Training_try" (
   user_id bigint NOT NULL,
   training_id bigint NOT NULL,
   start_date date NOT NULL DEFAULT now(),
   finished boolean NOT NULL DEFAULT false,
   CONSTRAINT "PK_Training_try" PRIMARY KEY (user_id, training_id, start_date),
   CONSTRAINT "FK_Training_was_solved_by_User" FOREIGN KEY (user_id)
       REFERENCES "User" (id) MATCH SIMPLE
       ON DELETE NO ACTION
       ON UPDATE CASCADE,
   CONSTRAINT "FK_User_trying_Training" FOREIGN KEY (training_id)
       REFERENCES "Training" (id) MATCH SIMPLE
       ON DELETE NO ACTION
       ON UPDATE CASCADE,
   CONSTRAINT "CH_Training_started_at_date" CHECK ("Training_try".start_date <= now())
);

-- -----------------------------------------------------
-- Table `psychodiary`.`Test_try`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "Test_try" (
    user_id bigint NOT NULL,
    test_id bigint NOT NULL,
    start_date date NOT NULL DEFAULT now(),
    finished boolean NOT NULL DEFAULT false,
    CONSTRAINT "PK_Test_try" PRIMARY KEY (user_id, test_id, start_date),
    CONSTRAINT "FK_Test_was_solved_by_User" FOREIGN KEY (user_id)
    REFERENCES "User" (id) MATCH SIMPLE
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
    CONSTRAINT "FK_User_trying_Test" FOREIGN KEY (test_id)
    REFERENCES "Test" (id) MATCH SIMPLE
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
    CONSTRAINT "CH_Test_started_at_date" CHECK ("Test_try".start_date <= now())
);

-- -----------------------------------------------------
-- Table `psychodiary`.`Training_Answer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "Training_Answer"
(
     user_id bigint NOT NULL,
     training_id bigint NOT NULL,
     start_date date NOT NULL,
     task_id bigint NOT NULL,
     answer_id bigint NOT NULL,
     CONSTRAINT "PK_Training_Answer" PRIMARY KEY (user_id, training_id, start_date, task_id, answer_id),
     CONSTRAINT "FK_Training_Answer_occupies_Training_try" FOREIGN KEY (user_id, training_id, start_date)
         REFERENCES "Training_try" (user_id, training_id, start_date) MATCH SIMPLE
         ON DELETE NO ACTION
         ON UPDATE CASCADE,
     CONSTRAINT "FK_Training_try_has_Task_Answer" FOREIGN KEY (answer_id, task_id)
         REFERENCES "Task_Answer" (answer_id, task_id) MATCH SIMPLE
         ON DELETE NO ACTION
         ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table `psychodiary`.`Test_Answer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "Test_Answer"
(
    user_id bigint NOT NULL,
    test_id bigint NOT NULL,
    start_date date NOT NULL,
    task_id bigint NOT NULL,
    answer_id bigint NOT NULL,
    CONSTRAINT "PK_Test_Answer" PRIMARY KEY (user_id, test_id, start_date, task_id, answer_id),
    CONSTRAINT "FK_Test_Answer_occupies_Training_try" FOREIGN KEY (user_id, test_id, start_date)
        REFERENCES "Test_try" (user_id, test_id, start_date) MATCH SIMPLE
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    CONSTRAINT "FK_Test_try_has_Task_Answer" FOREIGN KEY (answer_id, task_id)
        REFERENCES "Task_Answer" (answer_id, task_id)  MATCH SIMPLE
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table `psychodiary`.`Report`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "Report" (
    id BIGSERIAL NOT NULL,
    test_id BIGINT NOT NULL,
    title VARCHAR(150) NOT NULL,
    description VARCHAR(5000) NOT NULL,
    CONSTRAINT "PK_Report_Id" PRIMARY KEY (id),
    CONSTRAINT "UQ_Report_Title" UNIQUE (title),
    CONSTRAINT "FK_Report_summary_Test" FOREIGN KEY (test_id)
        REFERENCES "Test" (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table `psychodiary`.`Report_Task_Answer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "Report_Task_Answer" (
    report_id BIGINT NOT NULL,
    task_id BIGINT NOT NULL,
    answer_id BIGINT NOT NULL,
    CONSTRAINT "PK_Report_Task_Answer" PRIMARY KEY (report_id, task_id, answer_id),
    CONSTRAINT "FK_Report_has_Task_Answer" FOREIGN KEY (answer_id, task_id)
        REFERENCES "Task_Answer" (answer_id, task_id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    CONSTRAINT "FK_Task_Answer_occupies_Report" FOREIGN KEY (report_id)
        REFERENCES "Report" (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table `psychodiary`.`Diary`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "Diary" (
    id BIGSERIAL NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT "PK_Diary_Id" PRIMARY KEY (id),
    CONSTRAINT "FK_Diary_belongs_User" FOREIGN KEY (user_id)
        REFERENCES "User" (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table `psychodiary`.`Page`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "Page" (
    id BIGSERIAL NOT NULL,
    diary_id bigint NOT NULL,
    date date NOT NULL DEFAULT now(),
    content VARCHAR(10000) NOT NULL,
    CONSTRAINT "PK_Page_id" PRIMARY KEY (id),
    CONSTRAINT "FK_Page_occupies_Diary" FOREIGN KEY (diary_id)
        REFERENCES "Diary" (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT "CH_Page_was_wrote_at_date" CHECK ("Page".date <= now())
);


-- -----------------------------------------------------
-- Table `psychodiary`.`Image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "Image" (
    id      BIGSERIAL    NOT NULL,
    page_id bigint       NOT NULL,
    path    VARCHAR(200) NOT NULL,
    CONSTRAINT "PK_Image_id" PRIMARY KEY (id),
    CONSTRAINT "UQ_Image_Path" UNIQUE (path),
    CONSTRAINT "FK_Image_occupies_Page" FOREIGN KEY (page_id)
       REFERENCES "Page" (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table `psychodiary`.`Day_mark`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "Day_mark" (
    id BIGSERIAL NOT NULL,
    user_id bigint NOT NULL,
    date date NOT NULL DEFAULT now(),
    mark bigint NOT NULL DEFAULT 0,
    CONSTRAINT "PK_Mark_id" PRIMARY KEY (id),
    CONSTRAINT "FK_Mark_belongs_User" FOREIGN KEY (user_id)
        REFERENCES "User" (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT "CH_Mark_Date" CHECK ( date <= now() ),
    CONSTRAINT "CH_Mark_from_0_to_10" CHECK ( ("Day_mark".mark < 11) AND ("Day_mark".mark > (-1)) )
);

-- -----------------------------------------------------
-- Table `psychodiary`.`Phrase`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS "Phrase" (
    id BIGSERIAL NOT NULL,
    content VARCHAR(1000) NOT NULL,
    CONSTRAINT "PK_Phrase_ID" PRIMARY KEY (id),
    CONSTRAINT "UQ_Phrase_Content" UNIQUE (content)
);
