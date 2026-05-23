# ZGRZYT Mobile

Aplikacja mobilna Android dla systemu ZGRZYT — ekosystemu do obsługi zgłoszeń serwisowych i technicznych.

## Opis projektu

ZGRZYT Mobile umożliwia użytkownikom zgłaszanie problemów technicznych oraz pracownikom IT i administratorom zarządzanie zgłoszeniami z poziomu urządzenia mobilnego.

Aplikacja komunikuje się z centralnym backendem REST API:

Repozytorium API:

https://github.com/Gajda90/ZGRZYT-api
Aktualnie zaimplementowane funkcje
logowanie użytkownika,
automatyczne logowanie po ponownym uruchomieniu aplikacji,
wylogowanie,
pobieranie listy zgłoszeń,
wyszukiwarka zgłoszeń,
szczegóły zgłoszenia,
tworzenie nowego zgłoszenia,
wysyłanie wiadomości w zgłoszeniu,
automatyczne odświeżanie wiadomości,
zmiana statusu zgłoszenia dla ról admin i it,
zmiana priorytetu zgłoszenia dla ról admin i it,
obsługa ról użytkowników,
podstawowa obsługa błędów API,
pusty stan listy zgłoszeń,
prosty cache zgłoszeń w pamięci,
zabezpieczenie przed screenshotami,
obfuscation ProGuard/R8,
bezpieczne przechowywanie tokena z użyciem EncryptedSharedPreferences.

Technologie
Kotlin
Jetpack Compose
Compose Multiplatform
Retrofit
OkHttp
Gson
Navigation Compose
ViewModel
StateFlow
EncryptedSharedPreferences
Android Keystore
ProGuard / R8
Gradle Kotlin DSL
Architektura

Projekt wykorzystuje podejście zbliżone do MVVM.

Główne warstwy:

UI
 └── ViewModel
      └── Repository
           └── Retrofit API

Najważniejsze pakiety:

com.zgrzyt.mobile.data.api
com.zgrzyt.mobile.data.model
com.zgrzyt.mobile.data.repository
com.zgrzyt.mobile.ui.login
com.zgrzyt.mobile.ui.tickets

Bezpieczeństwo

W aplikacji zastosowano następujące mechanizmy bezpieczeństwa:

Blokada przechwytywania ekranu

Aplikacja używa:

WindowManager.LayoutParams.FLAG_SECURE

Dzięki temu system blokuje screenshoty oraz nagrywanie ekranu aplikacji.

Obfuscation

W wersji release włączono:

isMinifyEnabled = true
isShrinkResources = true

Kod aplikacji jest zaciemniany przy pomocy R8/ProGuard.

Bezpieczne przechowywanie tokena

Token sesji użytkownika jest przechowywany z użyciem:

EncryptedSharedPreferences
Android Keystore

Uruchomienie projektu
Wymagania
IntelliJ IDEA lub Android Studio
JDK 17
Android SDK
Gradle
Emulator Android lub fizyczne urządzenie
Budowanie aplikacji debug

./gradlew :composeApp:assembleDebug

Na Windows:

.\gradlew.bat :composeApp:assembleDebug

Plik APK będzie dostępny w:

composeApp/build/outputs/apk/debug/composeApp-debug.apk
Budowanie wersji release
.\gradlew.bat :composeApp:assembleRelease

Plik APK będzie dostępny w:

composeApp/build/outputs/apk/release/

Testy

Uruchomienie testów jednostkowych:

.\gradlew.bat :composeApp:testDebugUnitTest

Aktualnie testowane są między innymi:

modele danych,
stan sesji,
podstawowe stany ViewModeli,
cache zgłoszeń.
Endpointy używane przez aplikację
POST /api/login
GET /api/tickets
POST /api/tickets
GET /api/tickets/{id}
PUT /api/tickets/{id}
GET /api/tickets/{id}/messages
POST /api/tickets/{id}/messages
Role użytkowników

System obsługuje role:

user
it
admin

Uprawnienia:

user — tworzenie i przeglądanie zgłoszeń,
it — obsługa zgłoszeń, zmiana statusu i priorytetu,
admin — pełniejszy dostęp administracyjny.

Projekt jest w trakcie rozwoju. Aktualnie działa podstawowy przepływ aplikacji HelpDesk:

logowanie → lista zgłoszeń → szczegóły → wiadomości → tworzenie/edycja zgłoszeń
