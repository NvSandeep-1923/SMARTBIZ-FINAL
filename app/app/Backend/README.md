# SmartBiz Backend Setup

I have initialized the backend project for you. Since I cannot execute commands in this directory from my current environment, please follow these simple steps to start your server:

## Steps to Start

1. **Open your terminal** (Command Prompt or PowerShell).
2. **Navigate to this folder**:
   ```bash
   cd "C:\Users\sande\Desktop\app\app\Backend"
   ```
3. **Install dependencies**:
   ```bash
   npm install
   ```
4. **Start the server**:
   ```bash
   npm start
   ```

## API Endpoints Created
- **Auth**: `/api/auth/register`, `/api/auth/login`
- **Inventory**: `/api/inventory` (GET/POST)
- **Customers**: `/api/customers` (GET)
- **Expenses**: `/api/expenses` (GET)
- **Dashboard**: `/api/dashboard/stats` (GET)

The database (`smartbiz_master.db`) will be automatically created when you start the server for the first time.
