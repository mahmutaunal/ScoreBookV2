# 🎯 ScoreBook

ScoreBook is a modern, minimalistic mobile application designed for tracking scores across popular Turkish Okey and card games. It supports rule-based score input, game-specific configurations, player management, and dynamic round-based scoring.

> Easily track your Okey or card game rounds, calculate scores by game type, and review past games with a smooth user experience.

---

## 🧩 Features

- 📋 **Multi-game support:** Okey (101, Klasik, Çift), Batak, Pişti, Uno, Poker, King, etc.
- 🧠 **Rule-based score input:** Configure rules per game subtype (e.g., Okey 101 vs Okey Çift).
- 🔄 **Round-based scoring:** Add and visualize scores dynamically per round.
- 📈 **Game summary view:** View player statistics, total scores, and winners.
- 🎲 **Dice Roller & Calculator:** Quick tools for game rounds.
- 📚 **Game history:** Track previous games, sort, favorite, and re-access.
- ❤️ **Favorites & Ended games:** Mark games as favorite or completed.

---

## 🏗️ Tech Stack

- **Language:** Kotlin
- **Architecture:** MVVM + Clean Architecture
- **Persistence:** Room Database
- **Dependency Injection:** Hilt
- **UI:** Material Design 3, Jetpack Components (Navigation, ViewModel, LiveData)
- **Utilities:** Gson (type converters), custom score calculators

---

## 📷 Screenshots

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Flamingo or later
- Android SDK 33+
- Kotlin 1.8+

### Clone & Run

```bash
git clone https://github.com/mahmutaunal/ScoreBook.git
cd ScoreBook

```

## 📚 Documentation

### Features

- **Game-specific rule configuration**
- **Round-based scoring**
- **Game summaries**
- **Favorites**
- **Game history**
- **Built-in calculator**
- **Analytics and crash reporting**

### Architecture

The app follows Clean Architecture principles with the following layers:

- **Presentation Layer**: Contains UI components (Activities, Fragments, ViewModels)
- **Domain Layer**: Contains business logic and use cases
- **Data Layer**: Contains repositories and data sources

### Technologies Used

- Kotlin
- MVVM Architecture
- Clean Architecture
- Coroutines & Flow
- Dagger Hilt
- Room Database
- Firebase Analytics
- Firebase Crashlytics
- Material Design Components
- Navigation Component
- ViewBinding

### Setup

1. Clone the repository
2. Open the project in Android Studio
3. Add your `google-services.json` file to the app directory
4. Build and run the project

### Testing

The project includes:

- Unit Tests
- UI Tests
- Performance Tests

Run tests using:

```bash
./gradlew test        # Run unit tests
./gradlew connectedAndroidTest  # Run UI tests
```

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.
