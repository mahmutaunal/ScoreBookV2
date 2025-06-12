#!/bin/bash

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print colored messages
print_message() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check if version is provided
if [ -z "$1" ]; then
    print_error "Please provide a version number (e.g., 1.0.0)"
    exit 1
fi

VERSION=$1
CURRENT_BRANCH=$(git branch --show-current)

# Check if we're on main branch
if [ "$CURRENT_BRANCH" != "main" ]; then
    print_error "You must be on the main branch to create a release"
    exit 1
fi

# Check if there are uncommitted changes
if [ -n "$(git status --porcelain)" ]; then
    print_error "You have uncommitted changes. Please commit or stash them first."
    exit 1
fi

print_message "Starting release process for version $VERSION"

# Update version in build.gradle
print_message "Updating version in build.gradle..."
sed -i "s/versionName \".*\"/versionName \"$VERSION\"/" app/build.gradle
sed -i "s/versionCode .*/versionCode $(date +%Y%m%d)/" app/build.gradle

# Update CHANGELOG.md
print_message "Updating CHANGELOG.md..."
TODAY=$(date +%Y-%m-%d)
CHANGELOG_ENTRY="## [$VERSION] - $TODAY\n\n### Added\n- Initial release\n\n### Changed\n- None\n\n### Fixed\n- None\n\n"
sed -i "3i\\$CHANGELOG_ENTRY" CHANGELOG.md

# Update release notes
print_message "Updating release notes..."
echo "ScoreBook v$VERSION" > distribution/whatsnew/whatsnew-en-US
echo "ScoreBook v$VERSION" > distribution/whatsnew/whatsnew-tr-TR

# Commit changes
print_message "Committing changes..."
git add app/build.gradle CHANGELOG.md distribution/whatsnew/whatsnew-*
git commit -m "Release version $VERSION"

# Create tag
print_message "Creating tag v$VERSION..."
git tag -a "v$VERSION" -m "Release version $VERSION"

# Push changes
print_message "Pushing changes to remote..."
git push origin main
git push origin "v$VERSION"

print_message "Release process completed successfully!"
print_message "Next steps:"
print_message "1. Wait for GitHub Actions to complete"
print_message "2. Verify the release on GitHub"
print_message "3. Check the Play Store listing"
print_message "4. Monitor crash reports and analytics" 