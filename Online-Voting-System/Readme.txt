# Person 1 Deliverables - Online Voting System

## 📋 Overview
This document covers Person 1's deliverables for the Online Voting System project, including project setup, entity design, repository implementation, and ERD documentation.

## 🚀 Project Setup

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Dependencies Included
```xml
<!-- Core Spring Boot -->
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-security
- spring-boot-starter-validation

<!-- Database -->
- mysql-connector-java (for production)
- h2database (for development)

<!-- JWT Authentication -->
- jjwt-api, jjwt-impl, jjwt-jackson

<!-- Testing -->
- spring-boot-starter-test
- spring-security-test
```

### Running the Application
```bash
# Clone the repository
git clone <repository-url>
cd online-voting-system

# Run with Maven
./mvnw spring-boot:run

# Or run with Java
./mvnw clean package
java -jar target/online-voting-system-1.0.0.jar
```

### Database Access
- **H2 Console**: http://localhost:8080/api/h2-console
- **JDBC URL**: `jdbc:h2:mem:votingdb`
- **Username**: `sa`
- **Password**: `password`

## 🏗️ Architecture & Design

### Component Scanning Configuration
```java
@ComponentScan(
    basePackages = "com.votingsystem",
    includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Component.class),
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Service.class),
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class),
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = RestController.class)
    }
)
```

## 📊 Entity Relationship Diagram (ERD)

### Database Tables Structure

#### 1. **USER (Base Table - Single Table Inheritance)**
- **Primary Key**: `id` (Auto-generated)
- **Unique Key**: `email`
- **Discriminator**: `user_type` (ADMIN/VOTER)
- **Common Fields**: id, email, password, name, role, created_at

#### 2. **ADMIN (Inherits from USER)**
- **Discriminator Value**: 'ADMIN'
- **Relationships**:
  - One-to-Many with ELECTION (manages elections)
  - One-to-Many with CANDIDATE (registers candidates)

#### 3. **VOTER (Inherits from USER)**
- **Discriminator Value**: 'VOTER'
- **Additional Fields**: city, is_assigned
- **Relationships**:
  - One-to-Many with VOTE (casts votes)
  - Many-to-Many with ELECTION (eligible elections)

#### 4. **ELECTION**
- **Primary Key**: `id` (Auto-generated)
- **Foreign Key**: `admin_id` → ADMIN(id)
- **Fields**: title, description, start_time, end_time, status
- **Relationships**:
  - Many-to-One with ADMIN (managed by admin)
  - One-to-Many with CANDIDATE (has candidates)
  - One-to-Many with VOTE (receives votes)
  - Many-to-Many with VOTER (eligible voters)

#### 5. **CANDIDATE**
- **Primary Key**: `id` (Auto-generated)
- **Foreign Keys**:
  - `election_id` → ELECTION(id)
  - `registered_by` → ADMIN(id)
- **Fields**: candidate_name, party, biography
- **Relationships**:
  - Many-to-One with ELECTION (belongs to election)
  - Many-to-One with ADMIN (registered by admin)
  - One-to-Many with VOTE (receives votes)

#### 6. **VOTE**
- **Primary Key**: `id` (Auto-generated)
- **Foreign Keys**:
  - `voter_id` → VOTER(id)
  - `candidate_id` → CANDIDATE(id)
  - `election_id` → ELECTION(id)
- **Unique Constraint**: (voter_id, election_id) - Prevents duplicate votes
- **Fields**: voted_at
- **Relationships**:
  - Many-to-One with VOTER (cast by voter)
  - Many-to-One with CANDIDATE (vote for candidate)
  - Many-to-One with ELECTION (vote in election)

#### 7. **ELECTION_ELIGIBLE_VOTERS (Join Table)**
- **Composite Primary Key**: (election_id, voter_id)
- **Purpose**: Many-to-Many relationship between ELECTION and VOTER

### Key Design Decisions

#### 1. **Single Table Inheritance**
- Used for USER → ADMIN/VOTER hierarchy
- Efficient queries, single table for authentication
- Discriminator column `user_type` differentiates entity types

#### 2. **Unique Constraints**
- `email` in USER table (unique across all users)
- `(voter_id, election_id)` in VOTE table (one vote per voter per election)

#### 3. **Cascade Operations**
- `CascadeType.ALL` on parent entities (ADMIN → ELECTION, ELECTION → CANDIDATE)
- Ensures data consistency when deleting parent records

#### 4. **Lazy Loading**
- All relationships use `FetchType.LAZY` for performance
- Prevents N+1 query problems

## 🗄️ Repository Layer

### Custom Finder Methods

#### VoterRepository
```java
- findByEmail(String email)
- findByCity(String city)
- findByCityIgnoreCase(String city)
- findUnassignedVotersByCity(String city)
- findAssignedVoters()
```

#### ElectionRepository
```java
- findByStatus(ElectionStatus status)
- findByAdminId(Long adminId)
- findActiveElections(LocalDateTime now, ElectionStatus status)
- findByTitleContaining(String title)
- findCompletedElections(LocalDateTime now)
- findUpcomingElections(LocalDateTime now)
```

#### CandidateRepository
```java
- findByElectionId(Long electionId)
- findByCandidateNameContaining(String candidateName)
- findByParty(String party)
- findByElectionIdOrderByName(Long electionId)
- findCandidatesInActiveElections()
```

#### VoteRepository
```java
- findByVoterIdAndElectionId(Long voterId, Long electionId)
- findByElectionId(Long electionId)
- countByCandidateId(Long candidateId)
- countByElectionId(Long electionId)
- countVotesByCandidateInElection(Long electionId)
- existsByVoterIdAndElectionId(Long voterId, Long electionId)
```

### Custom Queries with @Query

#### Update Operations with @Modifying
```java
@Modifying
@Query("UPDATE Voter v SET v.isAssigned = true WHERE v.id IN :voterIds")
void assignVoters(@Param("voterIds") List<Long> voterIds);

@Modifying
@Query("UPDATE Voter v SET v.isAssigned = false WHERE v.id = :voterId")
void unassignVoter(@Param("voterId") Long voterId);
```

#### Complex Aggregation Queries
```java
@Query("SELECT v.candidate.id, COUNT(v) FROM Vote v WHERE v.election.id = :electionId GROUP BY v.candidate.id ORDER BY COUNT(v) DESC")
List<Object[]> countVotesByCandidateInElection(@Param("electionId") Long electionId);
```

## 🔧 Configuration

### Database Configuration
- **Development**: H2 in-memory database
- **Production**: MySQL (configuration ready)
- **JPA Settings**:
  - `hibernate.ddl-auto=create-drop` (development)
  - SQL logging enabled for debugging

### Sample Data
- 2 Admin users (default password: 'password')
- 6 Voter users across different cities
- 2 Sample elections with time windows
- 4 Sample candidates
- Pre-configured relationships

## 📁 Project Structure
```
src/main/java/com/votingsystem/
├── OnlineVotingSystemApplication.java
├── config/
│   └── DatabaseConfig.java
├── entity/
│   ├── User.java (Abstract base class)
│   ├── Admin.java
│   ├── Voter.java
│   ├── Election.java
│   ├── Candidate.java
│   ├── Vote.java
│   ├── Role.java (Enum)
│   └── ElectionStatus.java (Enum)
└── repository/
    ├── UserRepository.java
    ├── AdminRepository.java
    ├── VoterRepository.java
    ├── ElectionRepository.java
    ├── CandidateRepository.java
    └── VoteRepository.java

src/main/resources/
├── application.properties
└── data.sql
```

## ✅ Deliverables Checklist

- [x] **Spring Boot Project Initialization**
  - [x] Maven configuration with all required dependencies
  - [x] Main application class with @ComponentScan filters

- [x] **Entity Classes (@Entity)**
  - [x] User (base class with inheritance)
  - [x] Admin (extends User)
  - [x] Voter (extends User)
  - [x] Election
  - [x] Candidate
  - [x] Vote
  - [x] All relationships defined (@OneToMany, @ManyToOne, @ManyToMany)
  - [x] @Table, @Id, @GeneratedValue annotations
  - [x] Validation constraints

- [x] **Repository Layer (@Repository)**
  - [x] All repositories extend CrudRepository
  - [x] Custom finder methods (findByEmail, findByCandidateNameContaining, etc.)
  - [x] @Query annotations for custom queries
  - [x] @Modifying annotations for update/delete operations
  - [x] Proper parameter binding with @Param

- [x] **Database Configuration**
  - [x] H2 configuration for development
  - [x] MySQL configuration ready for production
  - [x] JPA/Hibernate settings
  - [x] Sample data initialization

- [x] **Entity Relationship Diagram (ERD)**
  - [x] Complete visual representation of all entities
  - [x] All relationships clearly shown
  - [x] Primary keys, foreign keys, and constraints documented
  - [x] Cardinality indicators

## 🔗 Integration Points for Other Team Members

### For Person 2 (Admin Features):
- Use `AdminRepository`, `CandidateRepository`, `VoterRepository`
- Entity relationships are ready for candidate registration and voter assignment
- Custom queries available for filtering and searching

### For Person 3 (Voting Logic):
- Use `VoteRepository`, `CandidateRepository`, `ElectionRepository`
- Vote entity has unique constraint to prevent duplicate votes
- `Election.isVotingActive()` method ready for time window validation

### For Person 4 (Authentication):
- Use `UserRepository` for login verification
- User entity has email/password fields and Role enum
- Admin/Voter inheritance ready for role-based access

### For Person 5 (Results & Exception Handling):
- Use `VoteRepository.countVotesByCandidateInElection()` for results
- All repositories have exception-prone operations ready
- Entity constraints will throw appropriate exceptions

## 🎯 Next Steps
1. **Person 2** can start implementing admin service layer using the repositories
2. **Person 3** can begin voting logic with vote validation
3. **Person 4** can implement JWT authentication using User entities
4. **Person 5** can prepare exception handling for repository operations

The foundation is solid and ready for the next phases of development! 🚀