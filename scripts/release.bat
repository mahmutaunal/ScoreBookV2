@echo off
setlocal enabledelayedexpansion

:: Check if version is provided
if "%~1"=="" (
    echo [ERROR] Please provide a version number (e.g., 1.0.0)
    exit /b 1
)

set VERSION=%~1

:: Check if we're on main branch
for /f "tokens=*" %%a in ('git branch --show-current') do set CURRENT_BRANCH=%%a
if not "%CURRENT_BRANCH%"=="main" (
    echo [ERROR] You must be on the main branch to create a release
    exit /b 1
)

:: Check if there are uncommitted changes
git diff --quiet
if errorlevel 1 (
    echo [ERROR] You have uncommitted changes. Please commit or stash them first.
    exit /b 1
)

echo [INFO] Starting release process for version %VERSION%

:: Update version in build.gradle
echo [INFO] Updating version in build.gradle...
powershell -Command "(Get-Content app/build.gradle) -replace 'versionName \".*\"', 'versionName \"%VERSION%\"' | Set-Content app/build.gradle"
powershell -Command "(Get-Content app/build.gradle) -replace 'versionCode .*', 'versionCode %date:~6,4%%date:~3,2%%date:~0,2%' | Set-Content app/build.gradle"

:: Update CHANGELOG.md
echo [INFO] Updating CHANGELOG.md...
set TODAY=%date:~6,4%-%date:~3,2%-%date:~0,2%
set CHANGELOG_ENTRY=## [%VERSION%] - %TODAY%^^n^^n### Added^^n- Initial release^^n^^n### Changed^^n- None^^n^^n### Fixed^^n- None^^n^^n
powershell -Command "(Get-Content CHANGELOG.md) | Select-Object -First 2 | Set-Content CHANGELOG.md.tmp; Add-Content CHANGELOG.md.tmp '%CHANGELOG_ENTRY%'; (Get-Content CHANGELOG.md | Select-Object -Skip 2) | Add-Content CHANGELOG.md.tmp; Move-Item -Force CHANGELOG.md.tmp CHANGELOG.md"

:: Update release notes
echo [INFO] Updating release notes...
echo ScoreBook v%VERSION% > distribution\whatsnew\whatsnew-en-US
echo ScoreBook v%VERSION% > distribution\whatsnew\whatsnew-tr-TR

:: Commit changes
echo [INFO] Committing changes...
git add app/build.gradle CHANGELOG.md distribution/whatsnew/whatsnew-*
git commit -m "Release version %VERSION%"

:: Create tag
echo [INFO] Creating tag v%VERSION%...
git tag -a "v%VERSION%" -m "Release version %VERSION%"

:: Push changes
echo [INFO] Pushing changes to remote...
git push origin main
git push origin "v%VERSION%"

echo [INFO] Release process completed successfully!
echo [INFO] Next steps:
echo [INFO] 1. Wait for GitHub Actions to complete
echo [INFO] 2. Verify the release on GitHub
echo [INFO] 3. Check the Play Store listing
echo [INFO] 4. Monitor crash reports and analytics 