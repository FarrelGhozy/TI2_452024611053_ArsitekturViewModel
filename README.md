# Tugas 8 — Arsitektur Aplikasi Android (ViewModel & StateFlow)

| | |
|---|---|
| **Nama** | Farrel Ghozy |
| **NIM** | 452024611053 |
| **Kelas** | TI5A2 |

## Aplikasi Unscramble

Game tebak kata (acak huruf) berbasis **Jetpack Compose** dengan arsitektur **MVVM + UDF (Unidirectional Data Flow)**.

## Arsitektur

```
UI (Composable) ──event──> ViewModel ──state──> UI (Composable)
     │                        │
     │  collectAsState()      │  StateFlow<UiState>
     └────────────────────────┘
```

### Komponen

- **`GameViewModel`** — extends `ViewModel`, menyimpan semua logic bisnis (skor, kata acak, validasi tebakan)
- **`UiState`** — data class immutable yang diekspos via `StateFlow`
- **`StateFlow`** — `MutableStateFlow` diprivate, publik sebagai `StateFlow.asStateFlow()`
- **Composable UI** — pasif, hanya collect & render state, kirim event ke ViewModel

### Fitur

- Acak kata dari 20 kata Android/Kotlin
- Skor bertambah tiap jawaban benar
- 10 ronde per game
- Skip kata
- Game Over dialog dengan skor akhir
- **Data tetap aman saat rotasi layar** (ViewModel survive configuration changes)

## Screenshot

<!-- Taruh screenshot di folder assets/screenshots/ lalu referensikan di sini -->

| Game Play | Game Over |
|:---:|:---:|
| <img src="assets/screenshots/game_play.png" width="250" alt="Game Play"/> | <img src="assets/screenshots/game_over.png" width="250" alt="Game Over"/> |

> **Cara ambil screenshot:**
> 1. Jalankan `./gradlew installDebug`
> 2. Buka app di emulator/device
> 3. Tekan **Ctrl+F11** (rotate) — data game tetap utuh
> 4. Ambil screenshot lewat Android Studio (Logcat → Camera icon) atau `Ctrl+S`
> 5. Simpan sebagai `game_play.png` dan `game_over.png` ke `assets/screenshots/`

## Build & Run

```bash
./gradlew assembleDebug
./gradlew installDebug
```
