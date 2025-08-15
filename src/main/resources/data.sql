-- ========== data.sql - Sample Data for Testing ==========

-- Insert Sample Admin Users
INSERT INTO users (user_type, email, password, name, role, city, created_at) VALUES
('ADMIN', 'admin@voting.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'System Administrator', 'ADMIN', 'Default City', CURRENT_TIMESTAMP),
('ADMIN', 'election.admin@voting.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'Election Administrator', 'ADMIN', 'Default City', CURRENT_TIMESTAMP);

-- Insert Sample Voters
INSERT INTO users (user_type, email, password, name, role, city, is_assigned, created_at) VALUES
('VOTER', 'john.doe@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'John Doe', 'VOTER', 'New York', FALSE, CURRENT_TIMESTAMP),
('VOTER', 'jane.smith@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'Jane Smith', 'VOTER', 'New York', FALSE, CURRENT_TIMESTAMP),
('VOTER', 'mike.johnson@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'Mike Johnson', 'VOTER', 'Los Angeles', FALSE, CURRENT_TIMESTAMP),
('VOTER', 'sarah.wilson@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'Sarah Wilson', 'VOTER', 'Los Angeles', FALSE, CURRENT_TIMESTAMP),
('VOTER', 'david.brown@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'David Brown', 'VOTER', 'Chicago', FALSE, CURRENT_TIMESTAMP),
('VOTER', 'lisa.davis@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'Lisa Davis', 'VOTER', 'Chicago', FALSE, CURRENT_TIMESTAMP);

-- Insert Sample Elections
INSERT INTO elections (title, description, start_time, end_time, status, admin_id, created_at) VALUES
('Presidential Election 2024', 'Annual presidential election to elect the president for the year 2024', '2024-11-01 08:00:00', '2024-11-01 18:00:00', 'ACTIVE', 1, CURRENT_TIMESTAMP),
('City Council Election 2024', 'Election for city council representatives', '2024-09-15 09:00:00', '2024-09-15 17:00:00', 'COMPLETED', 2, CURRENT_TIMESTAMP);
