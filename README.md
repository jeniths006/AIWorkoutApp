# 🧠 AI Workout App

A **full-stack AI-powered fitness platform** that combines workout logging, progression analytics, nutrition tracking, and personalized AI coaching.

The goal of this project is to merge the best ideas from **Hevy** (workout tracking) and **MyFitnessPal** (nutrition tracking), enhanced with **AI-driven insights** and adaptive coaching.

This repository contains the **entire project**, including the backend API, future web frontend, and planned mobile applications.

---

## ✨ Core Features

### 🏋️ Workout Logging
- Create and manage workouts
- Add exercises to workouts
- Log sets (weight, reps, RPE)
- View complete workout history

### 📈 Progression & Analytics
- Volume tracking per workout and exercise
- Personal record (PR) detection
- Progression analysis over time
- Training load and fatigue insights *(planned)*

### 🤖 AI Coaching *(Planned)*
- Personalized training feedback
- AI-generated workout summaries
- Adaptive progression recommendations
- Natural language insights based on training history

### 🍎 Nutrition Tracking *(Planned)*
- Calorie and macro tracking
- Meal logging
- Nutrition insights alongside training data

---

## 🏗 Project Architecture

/backend   → Spring Boot backend API *(current focus)*  
/frontend  → Web client *(Next.js, planned)*  
/mobile    → Mobile application *(planned)*

The backend acts as the **single source of truth** and will be shared by both web and mobile clients.

---

## 🧰 Tech Stack

### 🖥 Backend *(Implemented)*
- Java 17+
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Flyway (database migrations)
- Docker (local development)
- Gradle
- Lombok

### 🌐 Frontend *(Planned)*
- Next.js
- Tailwind CSS
- TypeScript

### 🧠 AI & Analytics *(Planned)*
- OpenAI API
- Custom progression and volume logic
- Training data analysis

---

## 🗄 Database Design

Core entities:
- users
- workouts
- exercises
- workout_exercises
- sets

Each workout contains multiple exercises, and each exercise contains multiple sets.  
The schema is designed to support advanced analytics, PR detection, and AI-driven insights.

---

## 📡 API Overview *(Backend)*

### 🏋️ Workouts
- POST /api/workouts       → Create a workout
- GET  /api/workouts       → List workouts
- GET  /api/workouts/{id}  → View full workout details

### 🏃 Exercises
- POST /api/workouts/{workoutId}/exercises → Add exercise to workout

### 📊 Sets
- POST /api/workout-exercises/{id}/sets → Log a set

---

## 🚀 Getting Started *(Backend)*

### 1️⃣ Start PostgreSQL with Docker
docker compose up -d

### 2️⃣ Run the Spring Boot application
Run: AiWorkoutAppApplication.java

The backend API will be available at:  
http://localhost:8080

---

## 🔍 Database Access *(Local)*

You can inspect the database using **DataGrip** or any PostgreSQL client:

Host: localhost  
Port: 5432  
Database: aiworkout  
Username: aiworkout  
Password: aiworkout

---

## 🧪 Current Status

🟢 **Backend MVP Complete**
- Workout creation
- Exercise management
- Set logging
- Flyway-managed schema
- Dockerized local database

🟡 **Frontend, AI, and nutrition features are in planning**

---

## 🛣 Roadmap

- Authentication and user accounts
- Workout analytics (volume, PRs)
- AI coaching integration
- Nutrition tracking
- Web frontend (Next.js)
- Mobile application

---

## 🤝 Contributing

This project is being built collaboratively.  
Ideas, suggestions, and contributions are welcome.

---

## 📄 License

MIT *(to be added)*
