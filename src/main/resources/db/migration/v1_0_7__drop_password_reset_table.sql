-- -- Create this file: src/main/resources/db/migration/U{same_version}__drop_password_reset_tokens_table.sql
-- -- This is for rollback if needed (not automatically applied)

-- -- Remove foreign key constraint first
-- ALTER TABLE password_reset_tokens 
-- DROP FOREIGN KEY fk_password_reset_user;

-- -- Drop the table
-- DROP TABLE IF EXISTS password_reset_tokens;