CREATE TABLE tasks (
    id uniqueidentifier NOT NULL,
    title varchar(150) NOT NULL,
    description varchar(1000) NULL,
    completed bit NOT NULL CONSTRAINT df_tasks_completed DEFAULT 0,
    created_at datetime2 NOT NULL,
    completed_at datetime2 NULL,
    CONSTRAINT pk_tasks PRIMARY KEY (id)
);
