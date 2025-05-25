# 🏥 E-Hospital Management System

The E-Hospital System is a web-based Spring Boot application that allows medical personnel to manage patient care from admission to discharge, including real-time vitals monitoring, treatment planning, and role-based access for doctors, nurses, and administrators.

---

## ✨ Features

- 👩‍⚕️ Role-based login for **Guard Doctor**, **Doctor**, **Nurse**, and **Admin**
- 📝 Register new patients and create clinical files
- ♻️ Reactivate discharged patients (starts with a fresh vitals history)
- 💊 Assign treatments to active patients
- 🩺 Record patient vitals (temperature, blood pressure, heart rate)
- 🔁 Automatically generate sensor vitals every 30 seconds
- 📈 Evaluate patient status:
  - **Stable** = 3 consecutive healthy vitals → Discharge enabled
  - **Under Observation** = not ready for discharge
- 🧼 On discharge, all vitals are deleted and status is reset
- 📊 View vitals per patient or across all patients (filtered by active status)
- 🟢 Status badges and row highlights in vitals table

---

## 🧑‍⚕️ Roles & Permissions

| Role         | Permissions                                                                 |
|--------------|------------------------------------------------------------------------------|
| Admin        | Full access to all modules                                                  |
| Guard Doctor | Register patients, create/reactivate clinical files                         |
| Doctor       | Assign treatments, discharge patients, view vitals and all patients         |
| Nurse        | Record vitals, view vitals                                                  |

---

## ⚙️ Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security
- Thymeleaf
- H2 / MariaDB (configurable)
- Scheduled tasks with `@Scheduled`

---

## 🚀 Running the Project

1. **Clone the repository**
```bash
git clone https://github.com/adenis033/e-hospital.git
````

2. **Open in IntelliJ / VS Code**

3. **Configure DB** in `application.properties` (H2 or MariaDB)

4. **Run the project**

```bash
./mvnw spring-boot:run
```

5. **Access in browser**

```
http://localhost:8080
```

---

## 👥 Sample Users

| Username   | Password | Role          |
| ---------- | -------- | ------------- |
| [Admin]    | pass     | ADMIN         |
| [Doctor]   | pass     | DOCTOR        |
| [Nurse]    | pass     | NURSE         |
| [Guard]    | pass     | GUARD\_DOCTOR |

---

## 📌 Notes

* Only **Stable** patients can be discharged
* When reactivating, the previous vitals are not shown
* Sensor-generated vitals mix normal/abnormal data for realistic alerts


## 💡 Author

- **RÂPA Denis-Andrei** - [GitHub Profile](https://github.com/adenis033)
