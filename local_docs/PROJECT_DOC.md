# Document Collaboration Platform
## CSYE 6220 - Enterprise Software Design Final Project

**Student:** Qing Mi  
**Institution:** Northeastern University  
**Course:** CSYE 6220 - JavaWeb: Enterprise Software Design  
**Academic Year:** Fall 2025

---

## Table of Contents

1. [Project Statement](#project-statement)
2. [Technology Stack](#technology-stack)
3. [System Architecture](#system-architecture)
4. [Database Schema](#database-schema)
5. [Entity Relationships](#entity-relationships)
6. [Feature Breakdown](#feature-breakdown)
7. [API Endpoints](#api-endpoints)
8. [Project Structure](#project-structure)
9. [Configuration Files](#configuration-files)
10. [Implementation Guidelines](#implementation-guidelines)

---

## Project Statement

### Overview

A document collaboration platform that enables users to create, organize, and share hierarchical collections of pages. The system provides structured content management with version control and permission-based sharing capabilities.

### Core Functionality

**Document Management:**
- Create pages with rich text content
- Organize pages in hierarchical structures (parent-child relationships)
- Navigate through nested page structures with breadcrumb trails
- Search across all owned and shared pages

**Collaboration:**
- Share pages with other users
- Assign role-based permissions (VIEWER, EDITOR, ADMIN)
- Permission inheritance through page hierarchies
- Collaborative editing with version tracking

**Version Control:**
- Automatic version snapshots on save
- View complete version history
- Restore previous versions
- Track who made changes and when

### Academic Requirements

**Course Mandates:**
- ✅ Spring Boot framework
- ✅ Hibernate ORM with annotated POJOs (NO XML mapping)
- ✅ DAO Pattern implementation (NO Spring Data JPA)
- ✅ Service layer with dependency injection
- ✅ MVC architecture with JSP views
- ✅ Session management
- ✅ CRUD operations for all entities
- ✅ Jakarta EE specifications

**Project Goals:**
- Demonstrate enterprise software design patterns
- Implement complex data relationships (self-referencing, many-to-many)
- Show transaction management with Hibernate
- Build scalable, maintainable architecture
- Apply security best practices

---

## Technology Stack

### Runtime Environment

**Java:**
- Version: 21 LTS
- JDK: OpenJDK 21 or Oracle JDK 21

**Build Tool:**
- Maven: 3.9+

### Spring & Jakarta Ecosystem

**Spring Boot:** 3.5.7
- Manages all Spring Framework dependencies
- Includes Spring Framework 6.2.x
- Embedded Tomcat configuration
- Auto-configuration for Hibernate

**Spring Framework:** 6.2.x (via Boot)
- Spring MVC for web layer
- Spring ORM for Hibernate integration
- Spring Transaction Management

**Jakarta EE Specifications:**
- Jakarta Servlet: 6.0
- Jakarta Server Pages (JSP): 3.1
- Jakarta Standard Tag Library (JSTL): 3.0
- Jakarta Persistence (JPA): 3.1 API
- Jakarta Validation: 3.0.x API
- Jakarta Annotations: 2.1

### Web Server

**Apache Tomcat:** 10.1.x (embedded)
- Managed by Spring Boot
- Servlet Container for JSP rendering
- No external Tomcat installation needed

### Persistence Layer

**Hibernate ORM:** 6.6.x
- JPA 3.1 implementation
- Managed by Spring Boot 3.5.7
- Annotation-based entity mapping

**Database:**
- PostgreSQL: 16.x
- JDBC Driver: org.postgresql:postgresql (Boot managed)

**Connection Pooling:**
- HikariCP (Boot default)
- Configuration via `application.properties`

### Validation & Security

**Bean Validation:**
- Jakarta Validation API: 3.0.x
- Hibernate Validator: 8.x (Boot managed)

**Password Security:**
- BCrypt: org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
- Or: org.mindrot:jbcrypt:0.4

### Logging

**SLF4J:** 2.0.x (via Boot)
- Simple Logging Facade for Java
- Abstraction layer for logging

**Logback:** 1.4.x / 1.5.x (Boot default)
- Implementation of SLF4J
- Configured via `logback-spring.xml`

### Testing

**JUnit:** 5 (Jupiter)
- `spring-boot-starter-test` (version 3.5.7)
- Includes: JUnit 5, Mockito, AssertJ, Hamcrest

**Test Scope:**
- Unit tests for DAO and Service layers
- Integration tests for controllers
- Repository tests with @DataJpaTest

### Frontend

**JSP & JSTL:**
- Jakarta Server Pages 3.1
- Jakarta Standard Tag Library 3.0
- Expression Language 5.0

**CSS Framework:**
- Bootstrap 5.3
- Custom CSS for application-specific styling

**JavaScript:**
- Vanilla JS for form validation
- Optional: jQuery 3.7 for AJAX (if needed)

---

## System Architecture

### 3-Tier MVC Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                        │
│  ┌─────────────┐  ┌──────────────┐  ┌──────────────┐       │
│  │   JSP Views │  │ JSTL/EL Tags │  │  Bootstrap   │       │
│  │  Templates  │  │   Fragments  │  │     CSS      │       │
│  └──────┬──────┘  └──────┬───────┘  └──────────────┘       │
│         │                 │                                  │
└─────────┼─────────────────┼──────────────────────────────────┘
          │                 │
┌─────────▼─────────────────▼──────────────────────────────────┐
│                     WEB LAYER (Controllers)                   │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐       │
│  │ HomeController│  │PageController│  │ShareController│      │
│  │  @Controller  │  │  @Controller │  │  @Controller │       │
│  │   /          │  │   /pages/*   │  │   /shares/*  │       │
│  │  /dashboard  │  │              │  │              │       │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘       │
│         │                  │                  │               │
│  ┌──────▼───────┐                                            │
│  │UserController│                                            │
│  │  @Controller │                                            │
│  │   /users/*   │                                            │
│  └──────┬───────┘                                            │
└─────────┼──────────────────┼──────────────────┼───────────────┘
          │                  │                  │
┌─────────▼──────────────────▼──────────────────▼───────────────┐
│                SERVICE LAYER (Business Logic)                  │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐       │
│  │UserServiceImpl│  │PageServiceImpl│  │ShareServiceImpl│     │
│  │   @Service    │  │   @Service   │  │   @Service   │       │
│  │@Transactional │  │@Transactional│  │@Transactional│       │
│  │               │  │              │  │              │       │
│  │ + validate()  │  │ +checkPerm() │  │ +applyRole() │       │
│  │ + search()    │  │ +createVer() │  │              │       │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘       │
│         │                  │                  │               │
└─────────┼──────────────────┼──────────────────┼───────────────┘
          │                  │                  │
┌─────────▼──────────────────▼──────────────────▼───────────────┐
│                   DAO LAYER (Data Access)                      │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐       │
│  │  UserDAOImpl │  │  PageDAOImpl │  │PageShareDAO  │       │
│  │@Repository   │  │@Repository   │  │@Repository   │       │
│  │EntityManager │  │EntityManager │  │EntityManager │       │
│  │              │  │              │  │              │       │
│  │ + findById() │  │+findByUser() │  │+findByPage() │       │
│  │ + save()     │  │+findChildren()│  │+save()       │       │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘       │
│         │                  │                  │               │
└─────────┼──────────────────┼──────────────────┼───────────────┘
          │                  │                  │
┌─────────▼──────────────────▼──────────────────▼───────────────┐
│                PERSISTENCE LAYER (Hibernate 6.6.x)             │
│  ┌──────────────────────────────────────────────────────┐     │
│  │  Entity Classes (Jakarta Persistence Annotations)     │     │
│  │  - User.java                                          │     │
│  │  - Page.java (self-referencing parent/children)      │     │
│  │  - PageVersion.java                                   │     │
│  │  - PageShare.java (many-to-many User <-> Page)       │     │
│  └──────────────────────────────────────────────────────┘     │
│                                                                 │
│  PostgreSQL 16.x Database                                      │
└─────────────────────────────────────────────────────────────────┘
```

### Key Design Patterns

**DAO Pattern:**
- Interface-based DAO contracts
- EntityManager for database operations
- Transaction management via @Transactional

**Service Pattern:**
- Business logic encapsulation
- Transaction boundary management
- Permission checking and validation

**MVC Pattern:**
- Controllers handle HTTP requests
- Services process business logic
- Views render data with JSP/JSTL

**Dependency Injection:**
- Constructor-based injection (preferred)
- Spring manages all beans
- No manual object instantiation

---

## Database Schema

### Database Configuration

**Database Name:** `csye6220_finalproject`  
**Database User:** `finalproject_user`  
**Database Password:** `password123`  
**Host:** `localhost`  
**Port:** `5432`

**Setup Commands:**
```sql
-- Create database
CREATE DATABASE csye6220_finalproject;

-- Create user
CREATE USER finalproject_user WITH PASSWORD 'password123';

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE csye6220_finalproject TO finalproject_user;

-- Connect to database and grant schema privileges
\c csye6220_finalproject
GRANT ALL ON SCHEMA public TO finalproject_user;
```

---

### Table: users

Stores user account information with authentication credentials.

```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_email ON users(email);
```

**Columns:**
- `id`: Primary key, auto-increment
- `email`: Unique identifier for login, indexed
- `password_hash`: BCrypt hashed password (never store plaintext)
- `full_name`: Display name
- `created_at`: Account creation timestamp
- `updated_at`: Last profile update timestamp

---

### Table: pages

Stores page content with hierarchical relationships.

```sql
CREATE TABLE pages (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    parent_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_page_owner FOREIGN KEY (user_id) 
        REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_page_parent FOREIGN KEY (parent_id) 
        REFERENCES pages(id) ON DELETE CASCADE
);

CREATE INDEX idx_pages_user_id ON pages(user_id);
CREATE INDEX idx_pages_parent_id ON pages(parent_id);
CREATE INDEX idx_pages_title ON pages(title);
```

**Columns:**
- `id`: Primary key, auto-increment
- `title`: Page title (max 200 chars)
- `content`: Page content (unlimited text)
- `user_id`: Foreign key to owner (NOT NULL)
- `parent_id`: Self-referencing FK for hierarchy (nullable for root pages)
- `created_at`: Page creation timestamp
- `updated_at`: Last modification timestamp

**Relationships:**
- **Owner:** Many pages → One user (ON DELETE CASCADE)
- **Parent:** One page → Many child pages (ON DELETE CASCADE)

**Cascade Behavior:**
- Deleting a user deletes all their pages
- Deleting a parent page deletes all child pages

---

### Table: page_versions

Stores historical versions of page content.

```sql
CREATE TABLE page_versions (
    id BIGSERIAL PRIMARY KEY,
    page_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    version_number INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT NOT NULL,
    
    CONSTRAINT fk_version_page FOREIGN KEY (page_id) 
        REFERENCES pages(id) ON DELETE CASCADE,
    CONSTRAINT fk_version_creator FOREIGN KEY (created_by) 
        REFERENCES users(id) ON DELETE SET NULL,
    CONSTRAINT uk_page_version UNIQUE (page_id, version_number)
);

CREATE INDEX idx_page_versions_page_id ON page_versions(page_id);
CREATE INDEX idx_page_versions_created_by ON page_versions(created_by);
```

**Columns:**
- `id`: Primary key, auto-increment
- `page_id`: Foreign key to page (NOT NULL)
- `content`: Content snapshot at this version
- `version_number`: Sequential version (1, 2, 3...)
- `created_at`: Version creation timestamp
- `created_by`: User who created this version

**Relationships:**
- **Page:** Many versions → One page (ON DELETE CASCADE)
- **Creator:** Many versions → One user (ON DELETE SET NULL)

**Unique Constraint:**
- `(page_id, version_number)` ensures no duplicate version numbers per page

**Cascade Behavior:**
- Deleting a page deletes all its versions
- Deleting a user sets `created_by` to NULL (preserve history)

---

### Table: page_shares

Stores page sharing relationships with role-based permissions.

```sql
CREATE TABLE page_shares (
    id BIGSERIAL PRIMARY KEY,
    page_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('VIEWER', 'EDITOR', 'ADMIN')),
    shared_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_share_page FOREIGN KEY (page_id) 
        REFERENCES pages(id) ON DELETE CASCADE,
    CONSTRAINT fk_share_user FOREIGN KEY (user_id) 
        REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT uk_page_user UNIQUE (page_id, user_id)
);

CREATE INDEX idx_page_shares_page_id ON page_shares(page_id);
CREATE INDEX idx_page_shares_user_id ON page_shares(user_id);
```

**Columns:**
- `id`: Primary key, auto-increment
- `page_id`: Foreign key to page (NOT NULL)
- `user_id`: Foreign key to user being granted access (NOT NULL)
- `role`: Permission level (VIEWER, EDITOR, ADMIN)
- `shared_at`: When access was granted

**Relationships:**
- **Page:** Many shares → One page (ON DELETE CASCADE)
- **User:** Many shares → One user (ON DELETE CASCADE)

**Unique Constraint:**
- `(page_id, user_id)` ensures one share record per user per page

**Role Hierarchy:**
- **VIEWER:** Read-only access
- **EDITOR:** Read + Write access
- **ADMIN:** Read + Write + Share + Delete access

**Cascade Behavior:**
- Deleting a page removes all shares
- Deleting a user removes all their shares

---

### Database Initialization Script

**File:** `src/main/resources/schema.sql`

```sql
-- Enable UUID extension (optional, for future use)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Pages table
CREATE TABLE IF NOT EXISTS pages (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    parent_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_page_owner FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_page_parent FOREIGN KEY (parent_id) REFERENCES pages(id) ON DELETE CASCADE
);

-- Page versions table
CREATE TABLE IF NOT EXISTS page_versions (
    id BIGSERIAL PRIMARY KEY,
    page_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    version_number INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT NOT NULL,
    CONSTRAINT fk_version_page FOREIGN KEY (page_id) REFERENCES pages(id) ON DELETE CASCADE,
    CONSTRAINT fk_version_creator FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL,
    CONSTRAINT uk_page_version UNIQUE (page_id, version_number)
);

-- Page shares table
CREATE TABLE IF NOT EXISTS page_shares (
    id BIGSERIAL PRIMARY KEY,
    page_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('VIEWER', 'EDITOR', 'ADMIN')),
    shared_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_share_page FOREIGN KEY (page_id) REFERENCES pages(id) ON DELETE CASCADE,
    CONSTRAINT fk_share_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT uk_page_user UNIQUE (page_id, user_id)
);

-- Indexes
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_pages_user_id ON pages(user_id);
CREATE INDEX IF NOT EXISTS idx_pages_parent_id ON pages(parent_id);
CREATE INDEX IF NOT EXISTS idx_pages_title ON pages(title);
CREATE INDEX IF NOT EXISTS idx_page_versions_page_id ON page_versions(page_id);
CREATE INDEX IF NOT EXISTS idx_page_versions_created_by ON page_versions(created_by);
CREATE INDEX IF NOT EXISTS idx_page_shares_page_id ON page_shares(page_id);
CREATE INDEX IF NOT EXISTS idx_page_shares_user_id ON page_shares(user_id);
```

---

## Entity Relationships

### Entity-Relationship Diagram

```
┌─────────────────┐
│      User       │
│─────────────────│
│ PK: id          │
│    email        │◄──────────────────┐
│    passwordHash │                   │
│    fullName     │                   │
│    createdAt    │                   │
│    updatedAt    │                   │
└────────┬────────┘                   │
         │                            │
         │ 1:N (owns)                 │
         │                            │
         ▼                            │
┌─────────────────┐                   │
│      Page       │                   │
│─────────────────│                   │
│ PK: id          │                   │
│    title        │                   │
│    content      │◄──────┐           │
│ FK: userId      │       │           │
│ FK: parentId    │───────┘           │
│    createdAt    │   (self-ref)      │
│    updatedAt    │                   │
└────────┬────────┘                   │
         │                            │
         │ 1:N                        │
         │                            │
         ▼                            │
┌─────────────────┐                   │
│  PageVersion    │                   │
│─────────────────│                   │
│ PK: id          │                   │
│ FK: pageId      │                   │
│    content      │                   │
│    versionNumber│                   │
│    createdAt    │                   │
│ FK: createdBy   │───────────────────┘
└─────────────────┘

┌─────────────────┐
│   PageShare     │
│─────────────────│
│ PK: id          │
│ FK: pageId      │──────► Page
│ FK: userId      │──────► User
│    role (ENUM)  │
│    sharedAt     │
└─────────────────┘
```

### Relationship Details

**User ↔ Page (One-to-Many):**
- One user owns many pages
- Cascade: DELETE user → DELETE all owned pages
- Mapped via: `Page.userId` → `User.id`

**Page ↔ Page (Self-Referencing One-to-Many):**
- One page can have many child pages
- One page has zero or one parent page
- Cascade: DELETE parent → DELETE all children
- Mapped via: `Page.parentId` → `Page.id`

**Page ↔ PageVersion (One-to-Many):**
- One page has many versions
- Cascade: DELETE page → DELETE all versions
- Mapped via: `PageVersion.pageId` → `Page.id`

**User ↔ PageVersion (Many-to-One):**
- One user creates many versions
- Cascade: DELETE user → SET NULL on `createdBy` (preserve history)
- Mapped via: `PageVersion.createdBy` → `User.id`

**Page ↔ User via PageShare (Many-to-Many):**
- One page can be shared with many users
- One user can have access to many pages
- Join table: `PageShare` with role attribute
- Cascade: DELETE page OR user → DELETE share record
- Unique constraint: One share per (page, user) pair

---

## Feature Breakdown

### MVP Features (Must Have)

#### 1. User Management
**Authentication:**
- ✅ User registration with email/password
- ✅ Login with session management
- ✅ Logout functionality
- ✅ Password hashing with BCrypt

**Profile Management:**
- ✅ View user profile
- ✅ Update profile (name, email)
- ✅ Change password
- ✅ Delete account (with cascade deletion of pages)

**User Search:**
- ✅ Search users by name or email
- ✅ Display search results with pagination

---

#### 2. Page Management
**CRUD Operations:**
- ✅ Create new page (root or nested)
- ✅ View page content
- ✅ Edit page content
- ✅ Delete page (with cascade to children)

**Hierarchical Structure:**
- ✅ Create child pages under parent
- ✅ Navigate parent-child relationships
- ✅ Breadcrumb navigation
- ✅ List child pages

**Search & Discovery:**
- ✅ Search pages by title
- ✅ Filter owned pages vs. shared pages
- ✅ View all root pages (dashboard)

---

#### 3. Version Control
**Version Management:**
- ✅ Auto-create version on page save
- ✅ View version history (list all versions)
- ✅ View specific version content (read-only)
- ✅ Restore previous version (creates new version)

**Version Metadata:**
- ✅ Version number (sequential)
- ✅ Created timestamp
- ✅ Creator information

---

#### 4. Sharing & Permissions
**Sharing Operations:**
- ✅ Share page with user by email
- ✅ Assign role (VIEWER, EDITOR, ADMIN)
- ✅ Update user's role on shared page
- ✅ Revoke access (delete share)

**Permission Enforcement:**
- ✅ VIEWER: Read-only access
- ✅ EDITOR: Read + Write access
- ✅ ADMIN: Read + Write + Share + Delete
- ✅ Owner: All permissions automatically

**Permission Views:**
- ✅ View pages shared with me
- ✅ View who has access to my page
- ✅ Manage permissions (owners only)

---

### Enhanced Features (Should Have)

#### 5. Advanced Hierarchy
**Tree View:**
- 📌 Collapsible tree navigation
- 📌 Drag-and-drop to reorganize
- 📌 Move pages to different parents

**Bulk Operations:**
- 📌 Delete page and all descendants
- 📌 Copy page structure
- 📌 Export page hierarchy to PDF/Markdown

---

#### 6. Rich Content
**Rich Text Editor:**
- 📌 TinyMCE or similar integration
- 📌 Formatting (bold, italic, lists)
- 📌 Code blocks with syntax highlighting
- 📌 Tables and images

**Content Types:**
- 📌 Plain text
- 📌 Markdown rendering
- 📌 Code blocks with language detection

---

#### 7. Enhanced Search
**Advanced Search:**
- 📌 Full-text search in content
- 📌 Filter by date range
- 📌 Filter by shared status
- 📌 Tag-based filtering

**Search Results:**
- 📌 Highlight matching text
- 📌 Show page hierarchy context
- 📌 Sort by relevance

---

### Nice-to-Have Features (Optional)

#### 8. Collaboration Enhancements
**Real-time Features:**
- ⭐ Show who's currently viewing a page
- ⭐ Conflict detection for concurrent edits
- ⭐ Activity feed (recent changes)

**Comments:**
- ⭐ Add comments to pages
- ⭐ Reply to comments
- ⭐ Mention users (@username)

---

#### 9. Organization Features
**Tags & Categories:**
- ⭐ Add tags to pages
- ⭐ Browse by tag
- ⭐ Create custom categories

**Templates:**
- ⭐ Create page templates
- ⭐ Apply template to new page
- ⭐ Template library

---

#### 10. Export & Integration
**Export Options:**
- ⭐ Export page to PDF
- ⭐ Export to Markdown
- ⭐ Export entire hierarchy

**Import:**
- ⭐ Import from Markdown files
- ⭐ Bulk import pages

---

## API Endpoints

### User Management (`/users`)

| Method | Endpoint | Action | View/Redirect | Description |
|--------|----------|--------|---------------|-------------|
| GET | `/users/register` | showRegistration() | `register.jsp` | Display registration form |
| POST | `/users/register` | register(UserDTO) | `redirect:/users/login` | Create new user |
| GET | `/users/login` | showLogin() | `login.jsp` | Display login form |
| POST | `/users/login` | login(LoginDTO) | `redirect:/dashboard` | Authenticate user |
| POST | `/users/logout` | logout() | `redirect:/users/login` | End session |
| GET | `/users/{userId}/profile` | showProfile() | `profile.jsp` | View profile |
| GET | `/users/{userId}/edit` | showEditProfile() | `edit-profile.jsp` | Edit profile form |
| POST | `/users/{userId}/update` | updateProfile() | `redirect:/users/{userId}/profile` | Update profile |
| POST | `/users/{userId}/delete` | deleteAccount() | `redirect:/users/login` | Delete account |
| GET | `/users/search` | searchUsers(query) | `search-users.jsp` | Search users |

---

### Page Management (`/pages`, `/dashboard`)

| Method | Endpoint | Action | View/Redirect | Description |
|--------|----------|--------|---------------|-------------|
| GET | `/dashboard` | dashboard() | `dashboard.jsp` | List root pages |
| GET | `/pages/new` | showCreatePage() | `create-page.jsp` | Create page form |
| GET | `/pages/new?parentId={id}` | showCreatePage(parentId) | `create-page.jsp` | Create nested page |
| POST | `/pages/create` | createPage(PageDTO) | `redirect:/pages/{id}` | Save new page |
| GET | `/pages/{pageId}` | viewPage(pageId) | `view-page.jsp` | Display page |
| GET | `/pages/{pageId}/edit` | showEditPage(pageId) | `edit-page.jsp` | Edit page form |
| POST | `/pages/{pageId}/update` | updatePage(pageId, PageDTO) | `redirect:/pages/{pageId}` | Update page |
| POST | `/pages/{pageId}/delete` | deletePage(pageId) | `redirect:/dashboard` | Delete page |
| GET | `/pages/{pageId}/children` | viewChildren(pageId) | `page-children.jsp` | List children |
| GET | `/pages/search` | searchPages(query) | `search-results.jsp` | Search pages |
| GET | `/pages/shared` | viewSharedPages() | `shared-pages.jsp` | Pages shared with me |

---

### Version History (`/pages/{pageId}/versions`)

| Method | Endpoint | Action | View/Redirect | Description |
|--------|----------|--------|---------------|-------------|
| GET | `/pages/{pageId}/versions` | viewVersionHistory() | `version-history.jsp` | List versions |
| GET | `/pages/{pageId}/versions/{versionId}` | viewVersion() | `view-version.jsp` | View version |
| POST | `/pages/{pageId}/versions/{versionId}/restore` | restoreVersion() | `redirect:/pages/{pageId}` | Restore version |

---

### Sharing & Permissions (`/shares`)

| Method | Endpoint | Action | View/Redirect | Description |
|--------|----------|--------|---------------|-------------|
| GET | `/shares/page/{pageId}` | showSharePage() | `share-page.jsp` | Manage shares |
| POST | `/shares/add` | sharePage(ShareDTO) | `redirect:/shares/page/{pageId}` | Share page |
| POST | `/shares/{shareId}/update-role` | updatePermission() | `redirect:/shares/page/{pageId}` | Update role |
| POST | `/shares/{shareId}/revoke` | revokeAccess() | `redirect:/shares/page/{pageId}` | Revoke access |
| GET | `/shares/my-shared` | viewMyShares() | `my-shares.jsp` | Pages I shared |
| GET | `/shares/received` | viewReceivedShares() | `received-shares.jsp` | Pages shared with me |

---

### Error Handling (`/error`)

| Status | Endpoint | View | Description |
|--------|----------|------|-------------|
| 403 | `/error/403` | `403.jsp` | Permission denied |
| 404 | `/error/404` | `404.jsp` | Resource not found |
| 500 | `/error/500` | `500.jsp` | Internal server error |

---

## Project Structure

```
finalproject/
├── pom.xml
├── README.md
├── .gitignore
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── neu/
│   │   │           └── csye6220/
│   │   │               └── qingmi/
│   │   │                   └── finalproject/
│   │   │                       │
│   │   │                       ├── FinalProjectApplication.java (Spring Boot main)
│   │   │                       │
│   │   │                       ├── config/
│   │   │                       │   ├── WebConfig.java              (MVC config)
│   │   │                       │   ├── SecurityConfig.java         (BCrypt bean)
│   │   │                       │   └── DataSourceConfig.java       (Optional custom datasource)
│   │   │                       │
│   │   │                       ├── controller/
│   │   │                       │   ├── HomeController.java
│   │   │                       │   ├── UserController.java
│   │   │                       │   ├── PageController.java
│   │   │                       │   ├── ShareController.java
│   │   │                       │   └── ErrorController.java
│   │   │                       │
│   │   │                       ├── service/
│   │   │                       │   ├── UserService.java
│   │   │                       │   ├── UserServiceImpl.java
│   │   │                       │   ├── PageService.java
│   │   │                       │   ├── PageServiceImpl.java
│   │   │                       │   ├── ShareService.java
│   │   │                       │   └── ShareServiceImpl.java
│   │   │                       │
│   │   │                       ├── dao/
│   │   │                       │   ├── UserDAO.java
│   │   │                       │   ├── UserDAOImpl.java
│   │   │                       │   ├── PageDAO.java
│   │   │                       │   ├── PageDAOImpl.java
│   │   │                       │   ├── PageVersionDAO.java
│   │   │                       │   ├── PageVersionDAOImpl.java
│   │   │                       │   ├── PageShareDAO.java
│   │   │                       │   └── PageShareDAOImpl.java
│   │   │                       │
│   │   │                       ├── entity/
│   │   │                       │   ├── User.java
│   │   │                       │   ├── Page.java
│   │   │                       │   ├── PageVersion.java
│   │   │                       │   ├── PageShare.java
│   │   │                       │   └── RoleEnum.java
│   │   │                       │
│   │   │                       ├── dto/
│   │   │                       │   ├── UserRegistrationDTO.java
│   │   │                       │   ├── LoginDTO.java
│   │   │                       │   ├── UserProfileDTO.java
│   │   │                       │   ├── PageDTO.java
│   │   │                       │   └── ShareDTO.java
│   │   │                       │
│   │   │                       ├── exception/
│   │   │                       │   ├── PermissionDeniedException.java
│   │   │                       │   ├── PageNotFoundException.java
│   │   │                       │   ├── UserNotFoundException.java
│   │   │                       │   ├── InvalidCredentialsException.java
│   │   │                       │   └── GlobalExceptionHandler.java
│   │   │                       │
│   │   │                       ├── validator/
│   │   │                       │   ├── UserValidator.java
│   │   │                       │   ├── PageValidator.java
│   │   │                       │   └── PasswordValidator.java
│   │   │                       │
│   │   │                       ├── interceptor/
│   │   │                       │   ├── AuthenticationInterceptor.java
│   │   │                       │   └── PermissionInterceptor.java
│   │   │                       │
│   │   │                       └── util/
│   │   │                           ├── PasswordUtil.java
│   │   │                           ├── SessionUtil.java
│   │   │                           ├── ValidationUtil.java
│   │   │                           └── DateUtil.java
│   │   │
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   ├── application-dev.properties
│   │   │   ├── application-prod.properties
│   │   │   ├── logback-spring.xml
│   │   │   │
│   │   │   └── sql/
│   │   │       ├── schema.sql
│   │   │       └── data.sql
│   │   │
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   └── views/
│   │       │       ├── common/
│   │       │       │   ├── header.jsp
│   │       │       │   ├── footer.jsp
│   │       │       │   ├── head.jsp
│   │       │       │   └── breadcrumb.jsp
│   │       │       │
│   │       │       ├── error/
│   │       │       │   ├── 403.jsp
│   │       │       │   ├── 404.jsp
│   │       │       │   └── 500.jsp
│   │       │       │
│   │       │       ├── user/
│   │       │       │   ├── register.jsp
│   │       │       │   ├── login.jsp
│   │       │       │   ├── profile.jsp
│   │       │       │   ├── edit-profile.jsp
│   │       │       │   └── search-users.jsp
│   │       │       │
│   │       │       ├── page/
│   │       │       │   ├── dashboard.jsp
│   │       │       │   ├── create-page.jsp
│   │       │       │   ├── view-page.jsp
│   │       │       │   ├── edit-page.jsp
│   │       │       │   ├── page-children.jsp
│   │       │       │   ├── search-results.jsp
│   │       │       │   ├── shared-pages.jsp
│   │       │       │   ├── version-history.jsp
│   │       │       │   └── view-version.jsp
│   │       │       │
│   │       │       ├── share/
│   │       │       │   ├── share-page.jsp
│   │       │       │   ├── my-shares.jsp
│   │       │       │   └── received-shares.jsp
│   │       │       │
│   │       │       └── home/
│   │       │           └── index.jsp
│   │       │
│   │       └── resources/
│   │           ├── static/
│   │           │   ├── css/
│   │           │   │   ├── main.css
│   │           │   │   ├── dashboard.css
│   │           │   │   ├── page-view.css
│   │           │   │   └── forms.css
│   │           │   │
│   │           │   ├── js/
│   │           │   │   ├── main.js
│   │           │   │   ├── form-validation.js
│   │           │   │   ├── page-tree.js
│   │           │   │   └── search.js
│   │           │   │
│   │           │   └── images/
│   │           │       ├── logo.png
│   │           │       └── icons/
│   │           │
│   │           └── lib/
│   │               └── bootstrap-5.3/
│   │
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── neu/
│       │           └── csye6220/
│       │               └── qingmi/
│       │                   └── finalproject/
│       │                       ├── FinalProjectApplicationTests.java
│       │                       ├── dao/
│       │                       │   ├── UserDAOTest.java
│       │                       │   ├── PageDAOTest.java
│       │                       │   ├── PageVersionDAOTest.java
│       │                       │   └── PageShareDAOTest.java
│       │                       ├── service/
│       │                       │   ├── UserServiceTest.java
│       │                       │   ├── PageServiceTest.java
│       │                       │   └── ShareServiceTest.java
│       │                       └── controller/
│       │                           ├── UserControllerTest.java
│       │                           ├── PageControllerTest.java
│       │                           └── ShareControllerTest.java
│       │
│       └── resources/
│           ├── application-test.properties
│           └── test-data.sql
│
└── docs/
    ├── API_DOCUMENTATION.md
    ├── DATABASE_SCHEMA.md
    ├── SETUP_GUIDE.md
    └── USER_GUIDE.md
```

---

## Configuration Files

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.7</version>
        <relativePath/>
    </parent>
    
    <groupId>com.neu.csye6220.qingmi</groupId>
    <artifactId>finalproject</artifactId>
    <version>1.0.0</version>
    <packaging>war</packaging>
    
    <name>Document Collaboration Platform</name>
    <description>CSYE 6220 Final Project - Document Management System</description>
    
    <properties>
        <java.version>21</java.version>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    
    <dependencies>
        <!-- Spring Boot Web Starter (includes Spring MVC, Tomcat, Jackson) -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        
        <!-- Spring Boot Data JPA (includes Hibernate, Spring ORM) -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        
        <!-- Spring Boot Validation (includes Jakarta Validation, Hibernate Validator) -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        
        <!-- PostgreSQL Driver -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        
        <!-- Tomcat Embed Jasper (for JSP support) -->
        <dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-jasper</artifactId>
            <scope>provided</scope>
        </dependency>
        
        <!-- Jakarta Servlet JSP JSTL -->
        <dependency>
            <groupId>jakarta.servlet.jsp.jstl</groupId>
            <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
            <version>3.0.0</version>
        </dependency>
        
        <!-- Glassfish JSTL Implementation -->
        <dependency>
            <groupId>org.glassfish.web</groupId>
            <artifactId>jakarta.servlet.jsp.jstl</artifactId>
            <version>3.0.1</version>
        </dependency>
        
        <!-- BCrypt Password Encoder -->
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-crypto</artifactId>
        </dependency>
        
        <!-- Apache Commons Lang (utility) -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
        </dependency>
        
        <!-- Lombok (optional, for reducing boilerplate) -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        
        <!-- Spring Boot Test Starter -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        
        <!-- H2 Database (for testing) -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <build>
        <finalName>finalproject</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

---

### application.properties

```properties
# Application Name
spring.application.name=finalproject

# Server Configuration
server.port=8080
server.servlet.context-path=/

# JSP Configuration
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/csye6220_finalproject
spring.datasource.username=finalproject_user
spring.datasource.password=password123
spring.datasource.driver-class-name=org.postgresql.Driver

# HikariCP Connection Pool
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.idle-timeout=600000
spring.datasource.hikari.max-lifetime=1800000

# JPA / Hibernate Configuration
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.hibernate.ddl-auto=validate

# Initialize schema from schema.sql
spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:sql/schema.sql
spring.sql.init.data-locations=classpath:sql/data.sql
spring.sql.init.continue-on-error=false

# Logging Configuration
logging.level.root=INFO
logging.level.com.neu.csye6220.qingmi.finalproject=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

# Session Configuration
server.servlet.session.timeout=30m
server.servlet.session.cookie.http-only=true
server.servlet.session.cookie.secure=false

# File Upload Configuration (for future use)
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Error Handling
server.error.whitelabel.enabled=false
server.error.path=/error
```

---

### application-dev.properties

```properties
# Development Profile

# Database Configuration (Dev)
spring.datasource.url=jdbc:postgresql://localhost:5432/csye6220_finalproject_dev
spring.datasource.username=dev_user
spring.datasource.password=dev_password

# JPA Configuration (Dev)
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.hibernate.ddl-auto=create-drop

# Logging (Dev)
logging.level.com.neu.csye6220.qingmi.finalproject=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

# Hot Reload
spring.devtools.restart.enabled=true
spring.devtools.livereload.enabled=true
```

---

### application-prod.properties

```properties
# Production Profile

# Database Configuration (Prod)
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}

# JPA Configuration (Prod)
spring.jpa.show-sql=false
spring.jpa.hibernate.ddl-auto=validate

# Logging (Prod)
logging.level.root=WARN
logging.level.com.neu.csye6220.qingmi.finalproject=INFO

# Session Security (Prod)
server.servlet.session.cookie.secure=true

# Error Pages (Prod)
server.error.include-message=never
server.error.include-stacktrace=never
```

---

### logback-spring.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <include resource="org/springframework/boot/logging/logback/defaults.xml"/>
    
    <property name="LOG_FILE" value="${LOG_FILE:-${LOG_PATH:-${LOG_TEMP:-${java.io.tmpdir:-/tmp}}/}spring.log}"/>
    
    <!-- Console Appender -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <!-- File Appender -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOG_FILE}</file>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${LOG_FILE}.%d{yyyy-MM-dd}.gz</fileNamePattern>
            <maxHistory>7</maxHistory>
        </rollingPolicy>
    </appender>
    
    <!-- Root Logger -->
    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="FILE"/>
    </root>
    
    <!-- Application Logger -->
    <logger name="com.neu.csye6220.qingmi.finalproject" level="DEBUG"/>
    
    <!-- Hibernate SQL Logger -->
    <logger name="org.hibernate.SQL" level="DEBUG"/>
    <logger name="org.hibernate.type.descriptor.sql.BasicBinder" level="TRACE"/>
    
    <!-- Spring Framework Logger -->
    <logger name="org.springframework.web" level="DEBUG"/>
</configuration>
```

---

## Implementation Guidelines

### Jakarta EE Namespace Migration

**CRITICAL: Use Jakarta namespaces, NOT javax**

**Correct Imports:**
```java
// Entity annotations
import jakarta.persistence.*;

// Validation annotations
import jakarta.validation.constraints.*;

// Servlet/JSP
import jakarta.servlet.*;
import jakarta.servlet.http.*;
```

**Incorrect (Old javax):**
```java
// DO NOT USE
import javax.persistence.*;
import javax.validation.*;
import javax.servlet.*;
```

---

### Entity Class Template

```java
package com.neu.csye6220.qingmi.finalproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "entity_name")
public class EntityName {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 255)
    @NotBlank(message = "Field is required")
    private String fieldName;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors, getters, setters
}
```

---

### DAO Implementation Template

```java
package com.neu.csye6220.qingmi.finalproject.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EntityDAOImpl implements EntityDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public void save(Entity entity) {
        entityManager.persist(entity);
    }
    
    @Override
    public Entity findById(Long id) {
        return entityManager.find(Entity.class, id);
    }
    
    @Override
    @Transactional
    public void update(Entity entity) {
        entityManager.merge(entity);
    }
    
    @Override
    @Transactional
    public void delete(Long id) {
        Entity entity = findById(id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}
```

---

### Service Implementation Template

```java
package com.neu.csye6220.qingmi.finalproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EntityServiceImpl implements EntityService {
    
    private final EntityDAO entityDAO;
    
    @Autowired
    public EntityServiceImpl(EntityDAO entityDAO) {
        this.entityDAO = entityDAO;
    }
    
    @Override
    public Entity createEntity(EntityDTO dto) {
        // Business logic
        Entity entity = new Entity();
        entity.setField(dto.getField());
        
        entityDAO.save(entity);
        return entity;
    }
    
    @Override
    public Entity getEntity(Long id) {
        Entity entity = entityDAO.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException("Entity not found: " + id);
        }
        return entity;
    }
}
```

---

### Controller Implementation Template

```java
package com.neu.csye6220.qingmi.finalproject.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/entities")
public class EntityController {
    
    private final EntityService entityService;
    
    @Autowired
    public EntityController(EntityService entityService) {
        this.entityService = entityService;
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("entityDTO", new EntityDTO());
        return "entity/create";
    }
    
    @PostMapping("/create")
    public String createEntity(
            @ModelAttribute @Valid EntityDTO entityDTO,
            BindingResult result,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "entity/create";
        }
        
        Long userId = (Long) session.getAttribute("userId");
        Entity entity = entityService.createEntity(entityDTO, userId);
        
        redirectAttributes.addFlashAttribute("success", "Entity created successfully");
        return "redirect:/entities/" + entity.getId();
    }
    
    @GetMapping("/{id}")
    public String viewEntity(@PathVariable Long id, Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        Entity entity = entityService.getEntity(id, userId);
        
        model.addAttribute("entity", entity);
        return "entity/view";
    }
}
```

---

### JSP Template with JSTL

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageTitle} - Document Platform</title>
    <link rel="stylesheet" href="<c:url value='/resources/lib/bootstrap-5.3/css/bootstrap.min.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/static/css/main.css'/>">
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <main class="container mt-4">
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        
        <!-- Page Content -->
        
    </main>
    
    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
    
    <script src="<c:url value='/resources/lib/bootstrap-5.3/js/bootstrap.bundle.min.js'/>"></script>
    <script src="<c:url value='/resources/static/js/main.js'/>"></script>
</body>
</html>
```

---

### Permission Checking Pattern

```java
public boolean checkPermission(Long pageId, Long userId, RoleEnum requiredRole) {
    // 1. Check if user owns the page
    Page page = pageDAO.findById(pageId);
    if (page == null) {
        throw new PageNotFoundException("Page not found: " + pageId);
    }
    
    if (page.getUserId().equals(userId)) {
        return true; // Owner has all permissions
    }
    
    // 2. Check if page is shared with user
    PageShare share = pageShareDAO.findShare(pageId, userId);
    if (share == null) {
        throw new PermissionDeniedException("Access denied to page: " + pageId);
    }
    
    // 3. Check role hierarchy
    switch (requiredRole) {
        case VIEWER:
            return true; // All roles can view
        case EDITOR:
            return share.getRole() == RoleEnum.EDITOR || 
                   share.getRole() == RoleEnum.ADMIN;
        case ADMIN:
            return share.getRole() == RoleEnum.ADMIN;
        default:
            return false;
    }
}
```

---

### Version Creation Pattern

```java
private void createVersion(Page page, Long userId) {
    // Get next version number
    Integer currentVersion = pageVersionDAO.getMaxVersionNumber(page.getId());
    Integer nextVersion = (currentVersion == null) ? 1 : currentVersion + 1;
    
    // Create version snapshot
    PageVersion version = new PageVersion();
    version.setPageId(page.getId());
    version.setContent(page.getContent());
    version.setVersionNumber(nextVersion);
    version.setCreatedBy(userId);
    version.setCreatedAt(LocalDateTime.now());
    
    pageVersionDAO.save(version);
}
```

---

### Session Management Pattern

```java
// Login
@PostMapping("/login")
public String login(@ModelAttribute LoginDTO loginDTO, 
                   HttpSession session, 
                   RedirectAttributes redirectAttributes) {
    User user = userService.authenticate(loginDTO.getEmail(), loginDTO.getPassword());
    
    if (user != null) {
        session.setAttribute("userId", user.getId());
        session.setAttribute("userEmail", user.getEmail());
        session.setAttribute("userName", user.getFullName());
        return "redirect:/dashboard";
    } else {
        redirectAttributes.addFlashAttribute("error", "Invalid credentials");
        return "redirect:/users/login";
    }
}

// Logout
@PostMapping("/logout")
public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/users/login";
}

// Check Authentication
private void requireAuthentication(HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
        throw new UnauthorizedException("Please login first");
    }
}
```

---

### Transaction Management

**Service Layer:**
```java
@Service
@Transactional  // Class-level: all methods are transactional
public class PageServiceImpl implements PageService {
    
    @Override
    public void updatePage(Long pageId, PageDTO dto, Long userId) {
        // This entire method runs in one transaction
        // If any exception occurs, all changes are rolled back
        
        Page page = pageDAO.findById(pageId);
        checkPermission(page, userId, RoleEnum.EDITOR);
        
        page.setTitle(dto.getTitle());
        page.setContent(dto.getContent());
        pageDAO.update(page);
        
        // Create version in same transaction
        createVersion(page, userId);
        
        // Commit happens automatically at end of method
    }
    
    @Override
    @Transactional(readOnly = true)  // Method-level: read-only optimization
    public Page getPage(Long pageId, Long userId) {
        Page page = pageDAO.findById(pageId);
        checkPermission(page, userId, RoleEnum.VIEWER);
        return page;
    }
}
```

---

### Testing Recommendations

**Unit Test Example:**
```java
@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserDAOTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private UserDAO userDAO;
    
    @Test
    void testSaveUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPasswordHash("hash123");
        user.setFullName("Test User");
        
        userDAO.save(user);
        entityManager.flush();
        
        User found = userDAO.findByEmail("test@example.com");
        assertNotNull(found);
        assertEquals("Test User", found.getFullName());
    }
}
```

**Integration Test Example:**
```java
@SpringBootTest
@AutoConfigureMockMvc
class PageControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void testCreatePage() throws Exception {
        mockMvc.perform(post("/pages/create")
                .param("title", "Test Page")
                .param("content", "Test Content")
                .sessionAttr("userId", 1L))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlPattern("/pages/*"));
    }
}
```

---

## Development Workflow

### Phase-Based Implementation

1. **Setup Phase:** Database + Spring Boot configuration
2. **User Phase:** Authentication and profile management
3. **Page Phase:** CRUD operations and hierarchy
4. **Version Phase:** Version control implementation
5. **Share Phase:** Sharing and permissions
6. **Polish Phase:** Error handling, validation, UI improvements

### Git Workflow

```bash
# Feature branch naming
git checkout -b feature/user-authentication
git checkout -b feature/page-hierarchy
git checkout -b feature/version-control
git checkout -b feature/sharing-permissions

# Commit after each working phase
git add .
git commit -m "Phase 2: Complete page CRUD operations"
git push origin feature/page-crud
```

### Testing Strategy

- **Unit tests:** Test DAO and Service methods in isolation
- **Integration tests:** Test Controller → Service → DAO flow
- **Manual tests:** Use browser to verify UI flows
- **Git commit:** Commit working code after each phase

---

## Success Criteria

### Academic Requirements Met

✅ Spring Boot 3.5.7 with managed dependencies  
✅ Hibernate 6.6.x with annotated POJOs (NO XML)  
✅ DAO Pattern implementation (NO Spring Data JPA)  
✅ Service layer with dependency injection  
✅ MVC architecture with JSP views  
✅ Session management  
✅ CRUD operations for all entities  
✅ Jakarta EE specifications (jakarta.* namespaces)

### Functional Requirements Met

✅ User registration, login, logout  
✅ Create, read, update, delete pages  
✅ Hierarchical page structure (parent-child)  
✅ Version history with restore capability  
✅ Page sharing with role-based permissions  
✅ Search functionality  
✅ Permission enforcement  
✅ Error handling and validation

### Code Quality Standards

✅ Clean separation of concerns (MVC + Service + DAO)  
✅ Dependency injection throughout  
✅ Transaction management  
✅ Exception handling  
✅ Input validation  
✅ Secure password storage  
✅ Comprehensive comments  
✅ Unit and integration tests

---

**End of Document**