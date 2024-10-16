
# **Device Matching Backend Service**

## **Overview**  
This project is a backend service for a **Device Matching API** built using **Java 21** and **Spring Boot**. The service matches incoming User-Agent strings to existing devices and maintains a hit count for each matched device. **Aerospike** is used as the database to store and retrieve device information.

## **Features**
- **Match Device**: Create or match an existing device based on the User-Agent string.
- **Get Device by ID**: Retrieve details of a specific device.
- **Get Devices by OS Name**: List all devices with a given operating system name.
- **Delete Device**: Delete a device profile by ID.
- **Dockerized**: The project includes Docker setup with Docker Compose to run Aerospike.

---

## **Prerequisites**
1. **Java 21** or higher  
2. **Maven** (for building the project)  
3. **Docker** and **Docker Compose** installed

---

## **Getting Started**

### **1. Clone the Repository**
```bash
git clone https://github.com/vinjamurimall_grmn/device-matching
cd device-matching-backend
```

### **2. Build the Project**
Make sure the project builds without any issues:
```bash
mvn clean package
```

The built JAR file will be located in the `target` folder:
```
target/device-matching-service.jar
```

Run JAR file located in the `target` folder:
```
java -jar target/device-matching-service.jar
Or
mvn spring-boot:run
```

---

## **Running the Application with Docker Compose**

### **1. Build Docker Images**
```bash
docker-compose build
```

### **2. Start the Containers**
```bash
docker-compose up
```

This will start:
- **Spring Boot Application** on port `8080`
- **Aerospike Database** on port `3000`

### **3. Test the API Endpoints**

Use **Postman** or **curl** to test the following endpoints:

1. **Match Device** (POST)  
   Create or match an existing device:
   ```bash
   curl -X POST -H "Content-Type: application/json" \
   -d '{"userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/89.0"}' \
   http://localhost:8080/api/devices/match
   ```

2. **Get Device by ID** (GET)  
   ```bash
   curl http://localhost:8080/api/devices/{deviceId}
   ```

3. **Get Devices by OS Name** (GET)  
   ```bash
   curl http://localhost:8080/api/devices/os/Windows
   ```

4. **Delete Device by ID** (DELETE)  
   ```bash
   curl -X DELETE http://localhost:8080/api/devices/{deviceId}
   ```

---

## **Configuration**

The application connects to Aerospike using the following properties in `application.properties`:

```properties
aerospike.host=${AEROSPIKE_HOST:localhost}
aerospike.port=3000
```


---

## **Testing the Application**

### **Unit Tests**
To run unit tests, execute:
```bash
mvn test
```

### **Run Specific Tests (Optional)**
Use the following command to run specific test classes:
```bash
mvn -Dtest=DeviceServiceTest test
```

---

## **Stopping the Containers**
To stop the running containers, press **CTRL+C** or run:
```bash
docker-compose down
```

---

## **Optional: Push Docker Image to Docker Hub**

1. **Tag the Docker Image**:
   ```bash
   docker tag device-matching-service:latest dockerhub-username/device-matching-service:latest
   ```

2. **Push the Image**:
   ```bash
   docker push dockerhub-username/device-matching-service:latest
   ```

---

## **Project Structure**

```
device-matching-backend/
│
├── src/main/java/com/example/device
│   ├── controller/       # REST Controllers
│   ├── model/            # Data Models (Device)
│   ├── repository/       # Aerospike Repository Layer
│   ├── service/          # Service Layer
│
├── src/test/java/com/example/device
│   ├── DeviceServiceTest.java  # Unit tests for DeviceService
│   ├── DeviceRepositoryTest.java  # Unit tests for Repository
│
├── Dockerfile            # Docker setup for Spring Boot app
├── docker-compose.yml    # Docker Compose to run the app & Aerospike
├── pom.xml               # Maven configuration
└── README.md             # Documentation
```

---

## **Troubleshooting**

1. **Issue:** `Error 16,1,0: Unsupported Server Feature`  
   - **Solution:** Make sure you are using the latest Aerospike Community Edition in the Docker image.

2. **Issue:** Application not connecting to Aerospike  
   - **Solution:** Check if Aerospike is running on port 3000. Verify connectivity using:
     ```bash
     nc -zv localhost 3000
     ```

3. **Issue:** `NullPointerException` in the Repository Layer  
   - **Solution:** Ensure that the Aerospike keys and records are correctly initialized.

---
