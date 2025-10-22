# 🛍️ TAGGY - Smart Shopping Price Comparison

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![JavaFX](https://img.shields.io/badge/JavaFX-21.0.1-blue?style=for-the-badge&logo=java)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)
![Maven](https://img.shields.io/badge/Maven-3.8+-red?style=for-the-badge&logo=apache-maven)

**A modern, feature-rich desktop application for comparing product prices across multiple e-commerce platforms**

[Features](#-features) • [Installation](#-installation) • [Usage](#-usage) • [Tech Stack](#-tech-stack)

</div>

---

## 📖 About

TAGGY is an intelligent shopping companion that helps users find the best deals by comparing prices across Amazon, Flipkart, and Myntra. Built with JavaFX and MySQL, it offers a beautiful gradient pink-themed interface with smooth animations and comprehensive shopping features.

## ✨ Features

### 🛒 Shopping Experience
- **Multi-Platform Price Comparison** - Compare prices from Amazon, Flipkart, and Myntra
- **5 Product Categories** - Electronics, Fashion, Home, Sports, and Books
- **73+ Real Products** - Curated catalog with actual market pricing
- **Smart Category Filtering** - Browse products by specific categories
- **Detailed Product Views** - Side-by-side price comparison for each product
- **Shopping Cart** - Add items from preferred stores and manage quantities

### 💰 Financial Management
- **Digital Wallet** - Built-in wallet system for seamless transactions
- **Quick Fund Deposit** - Add money with preset amounts (₹500, ₹1000, ₹5000)
- **Real-time Balance Updates** - Track spending and wallet balance instantly
- **Secure Checkout** - Complete purchases with wallet balance

### 👤 User Profile
- **Personal Information Management** - Full name, phone, date of birth
- **Shipping Address** - Complete address with PIN code validation
- **Multiple Contact Numbers** - Primary and alternate phone numbers
- **Profile Editing** - Update information anytime from dashboard

### 🎨 User Interface
- **Modern Gradient Pink Theme** - Beautiful, eye-catching design
- **Smooth Animations** - Subtle hover effects and transitions
- **Responsive Layout** - Adapts to different screen sizes
- **Clean Typography** - Poppins font for excellent readability
- **Intuitive Navigation** - Easy-to-use sidebar and search functionality

## 🚀 Installation

### Prerequisites

- **Java JDK 17** or higher
- **Maven 3.8+**
- **MySQL 8.0** or higher
- **Git** (optional, for cloning)

### Setup Steps

1. **Clone or Download the Repository**
   ```bash
   git clone <repository-url>
   cd Price-comparison
   ```

2. **Configure MySQL Database**
   
   Start MySQL and run the schema:
   ```bash
   mysql -u root -p < database/schema.sql
   ```
   
   Import sample data:
   ```bash
   mysql -u root -p < database/sample_data.sql
   ```

3. **Update Database Credentials**
   
   Edit `src/main/java/com/taggy/util/DatabaseConnection.java`:
   ```java
   private static final String PASSWORD = "your_mysql_password";
   ```

4. **Build the Project**
   ```bash
   mvn clean install
   ```

5. **Run the Application**
   ```bash
   mvn javafx:run
   ```

## 🎯 Usage

### Login Credentials
- **Username:** `ridhi`
- **Password:** `ridhi123`
- **Starting Balance:** ₹75,000

### Navigation Guide
- **Home** - Browse featured products and categories
- **Compare Prices** - View comprehensive price comparison table
- **My Cart** - Manage shopping cart and checkout
- **Wallet** - Add funds and view balance
- **Profile** - Update personal information and shipping address

### Shopping Workflow
1. Browse products by category or use search
2. Click on a product to view detailed price comparison
3. Select store with best price and add to cart
4. Go to cart and proceed to checkout
5. Complete purchase using wallet balance

## 🛠️ Tech Stack

### Frontend
- **JavaFX 21.0.1** - Modern UI framework
- **JFoenix 9.0.10** - Material Design components
- **AnimateFX 1.2.3** - Smooth animations
- **FontAwesomeFX 4.7.0** - Icon library

### Backend
- **Java 17** - Core application logic
- **MySQL 8.0** - Database management
- **MySQL Connector 8.0.33** - JDBC driver

### Build & Deployment
- **Maven 3.8+** - Dependency management and build automation

## 📁 Project Structure

```
Price-comparison/
├── src/
│   ├── main/
│   │   ├── java/com/taggy/
│   │   │   ├── controller/      # UI Controllers
│   │   │   ├── model/           # Data Models
│   │   │   ├── util/            # Utility Classes
│   │   │   ├── Main.java        # Application Entry
│   │   │   └── Launcher.java    # JavaFX Launcher
│   │   └── resources/
│   │       ├── css/             # Stylesheets
│   │       └── fxml/            # UI Layouts
├── database/
│   ├── schema.sql               # Database Schema
│   └── sample_data.sql          # Sample Products & Users
├── pom.xml                      # Maven Configuration
└── README.md                    # Documentation
```

## 🎨 Design System

### Color Palette
- **Primary Pink:** #f78fb3
- **Secondary Pink:** #f8a5c2
- **Accent Pink:** #e77f67
- **Success Green:** #4CAF50
- **Error Red:** #F44336

### Typography
- **Font Family:** Poppins (Google Fonts)
- **Weights:** 300, 400, 500, 600, 700

## 🔒 Security Features

- Password validation for user authentication
- PIN code validation (6 digits)
- Phone number validation (10 digits)
- SQL injection prevention with prepared statements
- Session management for logged-in users

## 🌟 Key Highlights

- ✅ **Zero CSS Warnings** - Clean console output
- ✅ **Smooth Performance** - Optimized database queries
- ✅ **Modern Architecture** - MVC pattern implementation
- ✅ **Extensible Design** - Easy to add new features
- ✅ **Professional UI** - Inspired by modern web design
- ✅ **73 Real Products** - Authentic shopping experience

## 📊 Database Schema

### Tables
- **users** - User accounts with profile information (15 fields)
- **wallet** - Digital wallet balances
- **products** - Product catalog with multi-platform pricing
- **cart** - Shopping cart items
- **orders** - Purchase history

### Sample Data
- 73 products across 5 categories
- Pre-configured user account
- Realistic price variations across platforms

## 🚀 Quick Commands

```bash
mvn clean compile

mvn javafx:run

mvn clean install
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit pull requests or open issues for bugs and feature requests.

## 📝 License

This project is created for educational purposes.

## 👨‍💻 Developer

Built with ❤️ by Ridhi

## 🙏 Acknowledgments

- JavaFX community for excellent documentation
- JFoenix for Material Design components
- FontAwesome for beautiful icons
- Google Fonts for Poppins typography

---

<div align="center">

**Happy Shopping with TAGGY! 🛍️**

Made with JavaFX • Powered by MySQL • Styled with Love

</div>
