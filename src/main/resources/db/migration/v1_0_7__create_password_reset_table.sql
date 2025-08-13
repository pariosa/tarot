-- Create this file: src/main/resources/db/migration/V{next_version}__create_password_reset_tokens_table.sql
-- Replace {next_version} with your next version number (e.g., V1.2.0, V2.0.0, etc.)

CREATE TABLE password_reset_tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Foreign key constraint
    CONSTRAINT fk_password_reset_user 
        FOREIGN KEY (user_id) 
        REFERENCES users(id) 
        ON DELETE CASCADE,
    
    -- Index for performance
    INDEX idx_password_reset_token (token),
    INDEX idx_password_reset_user_id (user_id),
    INDEX idx_password_reset_expiry (expiry_date)
);

-- Add comment for documentation
ALTER TABLE password_reset_tokens 
COMMENT = 'Stores password reset tokens with expiration and usage tracking';