-- Fix: "operator does not exist: bigint = uuid" on todo_lists / tags
-- Cause: user_id was created as BIGINT while the app maps it as java.util.UUID (PostgreSQL uuid).
-- Run as a superuser or table owner after backing up. Adjust if you have FKs or data to preserve.

-- Empty or dev DB (drops dependent objects if needed — review first):
-- ALTER TABLE todo_lists ALTER COLUMN user_id TYPE uuid USING user_id::text::uuid;  -- only if values are valid UUID strings cast via text

-- Safer when tables are empty or you can truncate:
-- TRUNCATE task_tags, tasks, subtasks, tags, todo_lists RESTART IDENTITY CASCADE;
-- Then drop and recreate user_id, or recreate tables from JPA with correct types.

-- Example: recreate user_id column on todo_lists (no data):
-- ALTER TABLE todo_lists DROP COLUMN user_id;
-- ALTER TABLE todo_lists ADD COLUMN user_id uuid NOT NULL;

-- If user_id already stores UUIDs as text/varchar:
-- ALTER TABLE todo_lists ALTER COLUMN user_id TYPE uuid USING user_id::uuid;
