# CO-OP Bank Loan Calculator & Amortization Schedule App

An Android application that allows users to view available loan options, apply for loans, and calculate repayments with a detailed amortization schedule.


## Architecture Overview
The app follows **Clean Architecture** principles combined with **MVVM (Model-View-ViewModel)**:
- **Presentation Layer**: Built with **Jetpack Compose**. ViewModels use `StateFlow` to expose UI state and interact with the data layer.
- **Domain Layer**: Contains data models like `Loan` and `AmortizationScheduleItem`, and business logic utilities like `LoanCalculator`.
- **Data Layer**: Handles data persistence using **Room Database**. Dependency injection is managed via **Hilt**.
- **Navigation**: Managed using **Jetpack Navigation Compose** with a centralized `AppNavGraph`.

## How to Run the App
1.  **Prerequisites**:
    - Android Studio Ladybug or newer.
    - JDK 11 or 17.
    - Android SDK 35.
2.  **Steps**:
    - Clone the repository.
    - Open the project in Android Studio.
    - Wait for Gradle sync to complete.
    - Connect an Android device or emulator (API 24+).
    - Click **Run** in Android Studio.

## Assumptions Made
- **Interest Calculation**: Used the standard Reducing Balance method for EMI calculation.
- **Tenure**: Assumed tenure is entered in months by default, with an option to switch to years.
- **Currency**: Fixed to KES (Kenyan Shillings) as per the target branding.
- **Persistence**: Local storage only (Room). No remote backend integration was required for this scope.

## Trade-offs and Limitations
- **Data Sync**: Currently, there is no cloud synchronization; data is lost if the app is uninstalled.
- **Validation**: Basic input validation is implemented (handling nulls/empty strings), but complex financial edge cases (like negative interest rates) are restricted via keyboard types and basic logic.
- **Theming**: While the app uses Material 3, custom "Dark Mode" specialized assets (like logos) might need further refinement for perfect contrast.
- **Destructive Migration**: Room is configured with `fallbackToDestructiveMigration()` for development speed, which clears the database on schema changes.

## Finally: I have complied to Non-Functional Expectations
- **Dark Mode**: Fully supports Dark/Light modes via Material 3 `ColorScheme`.
- **Robustness**: Error handling for invalid inputs in the calculator to prevent crashes.
- **Performance**: Efficient list rendering using `LazyColumn` and asynchronous database operations using Coroutines.
