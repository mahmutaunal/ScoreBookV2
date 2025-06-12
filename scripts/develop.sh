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

# Function to run tests
run_tests() {
    print_message "Running tests..."
    ./gradlew test
    if [ $? -eq 0 ]; then
        print_message "Tests completed successfully!"
    else
        print_error "Tests failed!"
        exit 1
    fi
}

# Function to run lint
run_lint() {
    print_message "Running lint..."
    ./gradlew lint
    if [ $? -eq 0 ]; then
        print_message "Lint completed successfully!"
    else
        print_warning "Lint found issues. Please check the report."
    fi
}

# Function to run ktlint
run_ktlint() {
    print_message "Running ktlint..."
    ./gradlew ktlintCheck
    if [ $? -eq 0 ]; then
        print_message "ktlint completed successfully!"
    else
        print_warning "ktlint found issues. Please check the report."
    fi
}

# Function to run detekt
run_detekt() {
    print_message "Running detekt..."
    ./gradlew detekt
    if [ $? -eq 0 ]; then
        print_message "detekt completed successfully!"
    else
        print_warning "detekt found issues. Please check the report."
    fi
}

# Function to build debug APK
build_debug() {
    print_message "Building debug APK..."
    ./gradlew assembleDebug
    if [ $? -eq 0 ]; then
        print_message "Debug APK built successfully!"
        print_message "APK location: app/build/outputs/apk/debug/app-debug.apk"
    else
        print_error "Debug APK build failed!"
        exit 1
    fi
}

# Function to build release APK
build_release() {
    print_message "Building release APK..."
    ./gradlew assembleRelease
    if [ $? -eq 0 ]; then
        print_message "Release APK built successfully!"
        print_message "APK location: app/build/outputs/apk/release/app-release.apk"
    else
        print_error "Release APK build failed!"
        exit 1
    fi
}

# Function to clean project
clean_project() {
    print_message "Cleaning project..."
    ./gradlew clean
    if [ $? -eq 0 ]; then
        print_message "Project cleaned successfully!"
    else
        print_error "Project clean failed!"
        exit 1
    fi
}

# Function to update dependencies
update_dependencies() {
    print_message "Checking for dependency updates..."
    ./gradlew dependencyUpdates
    if [ $? -eq 0 ]; then
        print_message "Dependency check completed!"
    else
        print_error "Dependency check failed!"
        exit 1
    fi
}

# Function to show help
show_help() {
    echo "Usage: ./develop.sh [command]"
    echo ""
    echo "Commands:"
    echo "  test              Run unit tests"
    echo "  lint              Run Android lint"
    echo "  ktlint            Run ktlint"
    echo "  detekt            Run detekt"
    echo "  build-debug       Build debug APK"
    echo "  build-release     Build release APK"
    echo "  clean             Clean project"
    echo "  update-deps       Check for dependency updates"
    echo "  all               Run all checks and build debug APK"
    echo "  help              Show this help message"
}

# Main script
case "$1" in
    "test")
        run_tests
        ;;
    "lint")
        run_lint
        ;;
    "ktlint")
        run_ktlint
        ;;
    "detekt")
        run_detekt
        ;;
    "build-debug")
        build_debug
        ;;
    "build-release")
        build_release
        ;;
    "clean")
        clean_project
        ;;
    "update-deps")
        update_dependencies
        ;;
    "all")
        run_tests
        run_lint
        run_ktlint
        run_detekt
        build_debug
        ;;
    "help"|"")
        show_help
        ;;
    *)
        print_error "Unknown command: $1"
        show_help
        exit 1
        ;;
esac 