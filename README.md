# 약약약 🩺 Android 프로젝트 세팅 가이드

## 📁 파일 구조

```
yakyakyak/
├── build.gradle                          ← 루트 Gradle (플러그인 버전 선언)
└── app/
    ├── build.gradle                      ← 앱 의존성 (Room, Navigation, Material...)
    └── src/main/
        ├── AndroidManifest.xml
        ├── java/com/example/yakyakyak/
        │   ├── data/
        │   │   ├── Medication.kt         ← 약 정보 Entity
        │   │   ├── MedicationLog.kt      ← 복용 기록 Entity
        │   │   ├── MedicationDao.kt      ← DB 쿼리 인터페이스
        │   │   └── AppDatabase.kt        ← Room DB 싱글톤
        │   ├── repository/
        │   │   └── MedicationRepository.kt
        │   ├── viewmodel/
        │   │   ├── HomeViewModel.kt
        │   │   └── MedicationViewModel.kt
        │   └── ui/
        │       ├── MainActivity.kt        ← 바텀 네비게이션 호스트
        │       ├── home/
        │       │   ├── HomeFragment.kt    ← 오늘 복용 스케줄
        │       │   └── TodayMedicationAdapter.kt
        │       └── medication/
        │           ├── MedicationListFragment.kt  ← 약 목록
        │           ├── MedicationAdapter.kt
        │           └── AddEditMedicationActivity.kt ← 약 등록/수정
        └── res/
            ├── layout/  (6개 레이아웃 XML)
            ├── navigation/nav_graph.xml
            ├── menu/bottom_nav_menu.xml
            ├── drawable/ (아이콘/배경 shape)
            └── values/ (colors, strings, themes)
```

---

## 🚀 Android Studio에 적용하는 방법

### 방법 1: 새 프로젝트에 파일 복사 (권장)

1. Android Studio → **New Project → Empty Views Activity**
2. 설정:
   - Name: `YakYakYak`
   - Package: `com.example.yakyakyak`
   - Language: `Kotlin`
   - Min SDK: `API 26`
3. 프로젝트 생성 후 이 폴더의 파일들을 해당 위치에 **덮어쓰기**

### 방법 2: 기존 파일 교체

각 파일을 Android Studio 프로젝트의 동일한 경로에 복사하면 됩니다.

---

## ⚙️ build.gradle 주의사항

루트 `build.gradle`의 플러그인 버전이 실제 Android Studio 버전과 맞아야 합니다.

| Android Studio 버전 | AGP 버전 |
|---|---|
| Hedgehog (2023.1) | 8.2.x |
| Iguana (2023.2) | 8.3.x |
| Jellyfish (2023.3) | 8.4.x |
| Ladybug (2024.2) | 8.6.x |

버전이 맞지 않으면 `build.gradle` 최상단의 버전 숫자를 AS가 제안하는 값으로 교체하세요.

---

## ✅ 2주차 구현 완료 기능

- [x] 홈 화면 - 오늘 복용 스케줄 + 진행 현황 표시
- [x] 복용 완료 체크 버튼 (체크 시 취소선 + 시간 기록)
- [x] 약 목록 화면 - 등록된 약 전체 조회
- [x] 약 추가/수정/삭제 기능
- [x] 복용 시간 설정 (TimePickerDialog, 여러 개 추가)
- [x] 복용 주기 설정 (매일/격일/특정 요일)
- [x] 복용 기간 설정 (DatePickerDialog)
- [x] Room DB 데이터 저장
- [x] MVVM 아키텍처

---

## 3주차 예정 기능

- [ ] 알림 (AlarmManager + NotificationChannel)
- [ ] 복용 기록 화면 (달력 뷰)
- [ ] 건강 메모 기능
