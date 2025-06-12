@echo off
setlocal enabledelayedexpansion

:: Function to run tests
:run_tests
echo [INFO] Running tests...
call gradlew test
if errorlevel 1 (
    echo [ERROR] Tests failed!
    exit /b 1
)
echo [INFO] Tests completed successfully!
goto :eof

:: Function to run lint
:run_lint
echo [INFO] Running lint...
call gradlew lint
if errorlevel 1 (
    echo [WARNING] Lint found issues. Please check the report.
) else (
    echo [INFO] Lint completed successfully!
)
goto :eof

:: Function to run ktlint
:run_ktlint
echo [INFO] Running ktlint...
call gradlew ktlintCheck
if errorlevel 1 (
    echo [WARNING] ktlint found issues. Please check the report.
) else (
    echo [INFO] ktlint completed successfully!
)
goto :eof

:: Function to run detekt
:run_detekt
echo [INFO] Running detekt...
call gradlew detekt
if errorlevel 1 (
    echo [WARNING] detekt found issues. Please check the report.
) else (
    echo [INFO] detekt completed successfully!
)
goto :eof

:: Function to build debug APK
:build_debug
echo [INFO] Building debug APK...
call gradlew assembleDebug
if errorlevel 1 (
    echo [ERROR] Debug APK build failed!
    exit /b 1
)
echo [INFO] Debug APK built successfully!
echo [INFO] APK location: app\build\outputs\apk\debug\app-debug.apk
goto :eof

:: Function to build release APK
:build_release
echo [INFO] Building release APK...
call gradlew assembleRelease
if errorlevel 1 (
    echo [ERROR] Release APK build failed!
    exit /b 1
)
echo [INFO] Release APK built successfully!
echo [INFO] APK location: app\build\outputs\apk\release\app-release.apk
goto :eof

:: Function to clean project
:clean_project
echo [INFO] Cleaning project...
call gradlew clean
if errorlevel 1 (
    echo [ERROR] Project clean failed!
    exit /b 1
)
echo [INFO] Project cleaned successfully!
goto :eof

:: Function to update dependencies
:update_dependencies
echo [INFO] Checking for dependency updates...
call gradlew dependencyUpdates
if errorlevel 1 (
    echo [ERROR] Dependency check failed!
    exit /b 1
)
echo [INFO] Dependency check completed!
goto :eof

:: Function to show help
:show_help
echo Usage: develop.bat [command]
echo.
echo Commands:
echo   test              Run unit tests
echo   lint              Run Android lint
echo   ktlint            Run ktlint
echo   detekt            Run detekt
echo   build-debug       Build debug APK
echo   build-release     Build release APK
echo   clean             Clean project
echo   update-deps       Check for dependency updates
echo   all               Run all checks and build debug APK
echo   help              Show this help message
goto :eof

:: Main script
if "%~1"=="" goto show_help

if "%~1"=="test" goto run_tests
if "%~1"=="lint" goto run_lint
if "%~1"=="ktlint" goto run_ktlint
if "%~1"=="detekt" goto run_detekt
if "%~1"=="build-debug" goto build_debug
if "%~1"=="build-release" goto build_release
if "%~1"=="clean" goto clean_project
if "%~1"=="update-deps" goto update_dependencies
if "%~1"=="help" goto show_help

if "%~1"=="all" (
    call :run_tests
    call :run_lint
    call :run_ktlint
    call :run_detekt
    call :build_debug
    goto :eof
)

echo [ERROR] Unknown command: %~1
goto show_help 