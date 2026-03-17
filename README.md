# Resume Matcher

A rule-based Resume Parsing and Job Matching System built with Spring Boot and React.
No LLMs used — only regex, keyword matching, and traditional NLP techniques.

## Tech Stack

- **Backend**: Java 21, Spring Boot 3.4, Spring Data JPA
- **Database**: PostgreSQL
- **PDF Parsing**: Apache PDFBox
- **Frontend**: React, Vite, Tailwind CSS
- **Containerization**: Docker, Docker Compose

## Features

- PDF resume parsing (name, salary, experience, skills)
- Job description parsing (required vs optional skills, salary, experience)
- Rule-based skill extraction from a 200+ skills dictionary
- Matching score calculation: `(Matched Skills / Total JD Skills) × 100`
- Skill-level analysis showing which JD skills are present/missing
- REST API with full CRUD for resumes and JDs
- React UI with drag-and-drop upload and visual match results

## Local Setup (Without Docker)

### Prerequisites
- Java 21
- Maven
- PostgreSQL running locally
- Node.js 20+

### Backend

```bash
cd backend
# Update src/main/resources/application.properties with your DB password
./mvnw spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

Open `http://localhost:5173`

## Docker Setup (Recommended)

```bash
# From project root
docker-compose up --build
```

Open `http://localhost:80`

Backend API available at `http://localhost:8080/api`

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/resume/upload` | Upload PDF resume |
| GET | `/api/resume/{id}` | Get parsed resume |
| POST | `/api/jd` | Add job description |
| GET | `/api/jd` | List all JDs |
| GET | `/api/jd/{id}` | Get single JD |
| POST | `/api/match/{resumeId}` | Run matching |

## Sample Output

See `sample_output.json` for the expected output format.
