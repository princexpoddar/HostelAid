# How to Upload HostelAid to GitHub

Follow these step-by-step instructions to upload your HostelAid project to GitHub.

## Prerequisites

1. **Git installed** on your computer
   - Download from: https://git-scm.com/downloads
   - Verify installation: Open terminal/command prompt and type `git --version`

2. **GitHub account**
   - Create account at: https://github.com

## Method 1: Using GitHub Desktop (Easiest)

### Step 1: Install GitHub Desktop
- Download from: https://desktop.github.com/
- Install and sign in with your GitHub account

### Step 2: Create Repository on GitHub
1. Go to https://github.com
2. Click the **"+"** icon in top right → **"New repository"**
3. Repository name: `HostelAid`
4. Description: "A sustainable hostel management Android application"
5. Choose **Public** or **Private**
6. **DO NOT** initialize with README, .gitignore, or license (we already have these)
7. Click **"Create repository"**

### Step 3: Publish Repository
1. Open GitHub Desktop
2. Click **"File"** → **"Add Local Repository"**
3. Navigate to your HostelAid project folder
4. Click **"Add Repository"**
5. You'll see all your files listed
6. In the bottom left, write a commit message: `Initial commit: HostelAid Android app`
7. Click **"Commit to main"**
8. Click **"Publish repository"** button
9. Uncheck "Keep this code private" if you want it public
10. Click **"Publish repository"**

Done! Your code is now on GitHub.

## Method 2: Using Command Line (Terminal/CMD)

### Step 1: Initialize Git Repository
1. Open terminal/command prompt
2. Navigate to your project folder:
   ```bash
   cd "C:\Users\praba\Music\sd card\HostelAid\HostelAid"
   ```

### Step 2: Initialize Git
```bash
git init
```

### Step 3: Add All Files
```bash
git add .
```

### Step 4: Create Initial Commit
```bash
git commit -m "Initial commit: HostelAid Android app"
```

### Step 5: Create Repository on GitHub
1. Go to https://github.com
2. Click the **"+"** icon → **"New repository"**
3. Name: `HostelAid`
4. Description: "A sustainable hostel management Android application"
5. Choose **Public** or **Private**
6. **DO NOT** initialize with README, .gitignore, or license
7. Click **"Create repository"**

### Step 6: Connect and Push
GitHub will show you commands. Use these:

```bash
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/HostelAid.git
git push -u origin main
```

Replace `YOUR_USERNAME` with your actual GitHub username.

### Step 7: Authenticate
- If prompted, enter your GitHub username and password
- For password, you may need to use a Personal Access Token instead
  - Generate token: GitHub → Settings → Developer settings → Personal access tokens → Generate new token
  - Give it "repo" permissions

## Method 3: Using Android Studio (Built-in Git)

### Step 1: Enable Version Control
1. Open Android Studio
2. Go to **VCS** → **Enable Version Control Integration**
3. Select **Git** → **OK**

### Step 2: Create Repository on GitHub
1. Go to https://github.com
2. Create a new repository (same as Method 1, Step 2)

### Step 3: Commit Files
1. Right-click on project → **Git** → **Add**
2. Go to **VCS** → **Commit**
3. Write commit message: `Initial commit: HostelAid Android app`
4. Click **Commit**

### Step 4: Push to GitHub
1. Go to **VCS** → **Git** → **Push**
2. Click **"Define remote"**
3. Enter URL: `https://github.com/YOUR_USERNAME/HostelAid.git`
4. Click **Push**
5. Authenticate if prompted

## After Uploading

### Update README
1. Edit `README.md` in GitHub
2. Replace `yourusername` with your actual GitHub username
3. Update author information
4. Add screenshots if you have them

### Add Topics/Tags
1. On your repository page, click the gear icon next to "About"
2. Add topics: `android`, `java`, `sustainability`, `hostel-management`, `material-design`

### Create Releases (Optional)
1. Go to **Releases** → **Create a new release**
2. Tag: `v1.0.0`
3. Title: `HostelAid v1.0.0`
4. Description: Initial release
5. Click **Publish release**

## Troubleshooting

### "Repository not found" error
- Check your repository URL
- Verify you have access to the repository
- Make sure you're authenticated

### "Permission denied" error
- Generate a Personal Access Token
- Use token instead of password

### Large file upload issues
- Make sure `.gitignore` is working
- Don't commit `.apk` files or build folders

### Files not showing up
- Make sure you added files: `git add .`
- Check if files are in `.gitignore`

## Next Steps

1. **Share your repository**: Copy the repository URL and share it
2. **Clone on other devices**: Use `git clone https://github.com/YOUR_USERNAME/HostelAid.git`
3. **Collaborate**: Add collaborators in repository settings
4. **Create issues**: Track bugs and feature requests

## Useful Git Commands

```bash
# Check status
git status

# Add specific file
git add filename.java

# Commit changes
git commit -m "Your message"

# Push to GitHub
git push

# Pull latest changes
git pull

# View commit history
git log
```

---

**Need Help?** Create an issue on GitHub or check Git documentation: https://git-scm.com/doc

