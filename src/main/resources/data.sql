-- ========== data.sql - Sample Data for Testing ==========

-- Insert Sample Admin Users
INSERT INTO users (user_type, email, password, name, role, created_at) VALUES
('ADMIN', 'admin@voting.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'System Administrator', 'ADMIN', CURRENT_TIMESTAMP),
('ADMIN', 'election.admin@voting.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'Election Administrator', 'ADMIN', CURRENT_TIMESTAMP);

-- Insert Sample Voters
INSERT INTO users (user_type, email, password, name, role, city, is_assigned, created_at) VALUES
('VOTER', 'john.doe@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'John Doe', 'VOTER', 'New York', false, CURRENT_TIMESTAMP),
('VOTER', 'jane.smith@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'Jane Smith', 'VOTER', 'New York', false, CURRENT_TIMESTAMP),
('VOTER', 'mike.johnson@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'Mike Johnson', 'VOTER', 'Los Angeles', false, CURRENT_TIMESTAMP),
('VOTER', 'sarah.wilson@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'Sarah Wilson', 'VOTER', 'Los Angeles', false, CURRENT_TIMESTAMP),
('VOTER', 'david.brown@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'David Brown', 'VOTER', 'Chicago', false, CURRENT_TIMESTAMP),
('VOTER', 'lisa.davis@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'Lisa Davis', 'VOTER', 'Chicago', false, CURRENT_TIMESTAMP);

-- Insert Sample Elections
INSERT INTO elections (title, description, start_time, end_time, status, admin_id, created_at) VALUES
('Presidential Election 2024', 'Annual presidential election to