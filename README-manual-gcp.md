# GCP VPC Peering Setup Between Two Projects

This document outlines the manual steps to create two separate Google Cloud Projects, set up VPC peering between them, and configure VM instances with firewall rules for SSH and ICMP communication.

---

## 🏗️ 1. Project Creation

### 📁 Project 1: `gke-application`
- Create a new GCP project named **`gke-application`**

### 📁 Project 2: `databases`
- Create another GCP project named **`databases`**

---

## 🌐 2. VPC & Subnet Setup

### In `gke-application` Project
1. Go to **VPC network > VPC networks**
2. Click **Create VPC Network**
3. Name: `gke-application`
4. Subnet mode: **Custom**
5. Add a subnet:
   - Name: `gke-subnet`
   - Region: Choose one (e.g., `us-east1`)
   - IP range: `10.10.0.0/16`
6. Click **Create**

### In `databases` Project
1. Go to **VPC network > VPC networks**
2. Click **Create VPC Network**
3. Name: `databases-application`
4. Subnet mode: **Custom**
5. Add a subnet:
   - Name: `db-subnet`
   - Region: Choose one (same or different)
   - IP range: `10.20.0.0/16`
6. Click **Create**

---

## 🔗 3. VPC Peering Setup

### From `gke-application` Project:
1. Go to **VPC network > VPC network peering**
2. Click **Create Connection**
3. Name: `peer-to-databases`
4. Your VPC: `gke-application`
5. Peered VPC:
   - Project ID: `<databases-project-id>`
   - VPC Network: `databases-application`
6. Check ✅ *Import and export custom routes*
7. Click **Create**

### From `databases` Project:
1. Repeat the steps above
2. Name: `peer-to-gke`
3. Your VPC: `databases-application`
4. Peered VPC:
   - Project ID: `<gke-application-project-id>`
   - VPC Network: `gke-application`
5. Check ✅ *Import and export custom routes*
6. Click **Create**

Once both sides are created, the status should show **ACTIVE**

---

## 💻 4. Create VM Instances

### VM in `gke-application`
1. Go to **Compute Engine > VM instances**
2. Click **Create Instance**
3. Name: `gke-vm`
4. Region/Zone: Same as subnet
5. Network: `gke-application`
6. Subnet: `gke-subnet`
7. External IP: Optional
8. Firewall: Check ✅ *Allow HTTP and HTTPS if needed*
9. Click **Create**

### VM in `databases`
1. Go to **Compute Engine > VM instances**
2. Click **Create Instance**
3. Name: `db-vm`
4. Region/Zone: Same as `db-subnet`
5. Network: `databases-application`
6. Subnet: `db-subnet`
7. External IP: Optional
8. Click **Create**

---

## 🔥 5. Firewall Rules

### In `databases-application` VPC (to allow ping and SSH from `gke-application`)

1. Go to **VPC network > Firewall rules**
2. Click **Create Firewall Rule**
3. Name: `allow-from-gke`
4. Network: `databases-application`
5. Targets: All instances (or tag specific VM)
6. Source IP ranges: `10.10.0.0/16`
7. Protocols and ports:
   - Check ✅ *Specified protocols and ports*
     - `tcp:22` (for SSH)
     - `icmp` (for ping)
8. Click **Create**

### In `gke-application` VPC (optional for bidirectional test)

1. Repeat the above steps:
2. Name: `allow-from-databases`
3. Network: `gke-application`
4. Source IP ranges: `10.20.0.0/16`

---

## 🧪 6. Testing

### ✅ Test SSH
```bash
ssh <internal-ip-of-other-vm>
