# HEPoC - Homomorphic Encryption Proof of Concept

Welcome to HEPoC, a proof-of-concept application designed to demonstrate the use of homomorphic encryption in online voting processes. Built as part of a master thesis, this application showcases how homomorphic encryption can enhance the security and privacy of online voting systems.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies](#technologies)
- [Installation](#installation)
- [Usage](#usage)

## Overview

HEPoC (Homomorphic Encryption Proof of Concept) is an application built using Spring Boot and Thymeleaf for the frontend, with PostgreSQL as the database. This project aims to illustrate the practical application of homomorphic encryption in securing online voting processes, ensuring that votes can be counted without exposing individual voter choices.

## Features

- **Homomorphic Encryption**: Demonstrates secure vote counting using homomorphic encryption techniques.
- **Online Voting Interface**: User-friendly interface built with Thymeleaf for managing votes.
- **PostgreSQL Integration**: Persistent storage for user and voting data.
- **Spring Boot Backend**: Robust server-side application built with Spring Boot.

## Technologies

- **Spring Boot**: Framework for building the backend services.
- **Thymeleaf**: Template engine for rendering the frontend.
- **PostgreSQL**: Relational database for storing application data.
- **Homomorphic Encryption Libraries**: Libraries integrated for encryption and decryption operations.

## Installation

To set up HEPoC on your local machine, follow these steps:

1. **Clone the Repository**

   ```bash
   git clone https://github.com/yourusername/HEPoC.git
   cd HEPoC

2. **Set Up PostgreSQL**
   ```bash
   createdb hepoc
   
3. **Configure Application Properties**
    ```bash
    spring.datasource.url=jdbc:postgresql://localhost:5432/hepoc
    spring.datasource.username=yourusername
    spring.datasource.password=yourpassword

4. **Build and Run the Application**
    ```bash
    ./mvnw clean install
    ./mvnw spring-boot:run
   
5. **Access the Application**
    ```bash
    http://localhost:8080

