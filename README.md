# ✅ Java To-Do List (콘솔 미니 프로젝트)

## 📘 프로젝트 개요
이 프로젝트는 자바 기초 및 컬렉션, 파일 입출력 학습을 위한 **콘솔형 할 일 관리 프로그램**입니다.  
DB 없이 파일(`tasks.tsv`)로 데이터를 저장하고 불러오며,  
CRUD(등록/조회/수정/삭제)와 완료 상태 토글 기능을 지원합니다.

---

## ⚙ 개발 환경
| 항목 | 내용 |
|------|------|
| 개발 언어 | Java 17 |
| IDE | Eclipse |
| 실행 방식 | Console Application |
| 데이터 저장 | 파일 기반 (TSV 형식) |
| 주요 기술 | ArrayList, File I/O, Exception Handling |

---

## 📁 프로젝트 구조
```
todo-console/
├─ src/
│ ├─ todo/ → 메인 실행 (TodoApp)
│ ├─ todo/model/ → Task 클래스 (데이터 모델)
│ ├─ todo/service/ → TaskService (비즈니스 로직)
│ └─ todo/repo/ → TaskRepository (파일 저장/불러오기)
├─ data/
│ └─ tasks.tsv → 저장된 할 일 데이터
└─ README.md → 프로젝트 설명 파일
```

---

## 🎯 핵심 키워드
| 키워드 | 설명 |
|---------|------|
| **CRUD** | Create / Read / Update / Delete 기능 구현. 콘솔 메뉴와 1:1 대응. |
| **파일입출력(File I/O)** | BufferedReader / Writer, Files, Path 활용하여 `tasks.tsv`에 데이터 저장. |
| **구조분리** | model-repo-service-ui 계층 구조로 역할을 명확히 분리하여 유지보수성 확보. |
| **예외처리** | 사용자 입력·파일 오류 등 예외를 처리해 안정성 유지. |

---

## 🧩 구조별 역할
| 클래스 | 역할 |
|---------|------|
| **Task (Model)** | 할 일 데이터(id, title, dueDate, done)와 동작(toggleDone, toString) 정의 |
| **TaskRepository (Repo)** | ArrayList로 메모리 데이터 관리, 파일 저장/불러오기 기능 포함 |
| **TaskService (Service)** | 입력 검증, ID 자동 발급(nextId), CRUD 로직 수행 |
| **TodoApp (UI)** | 콘솔 메뉴/입력 처리 및 사용자 인터페이스 담당 |

---

## 💡 주요 기능
1️⃣ 할 일 등록 (Create)  
2️⃣ 목록 조회 (Read)  
3️⃣ 완료/취소 토글, 수정 (Update)  
4️⃣ 삭제 (Delete)  
5️⃣ 종료 시 자동 저장 (File I/O)

---

## 💾 파일 구조 (`data/tasks.tsv`)
- 구분자: 탭(`\t`)  
- 첫 행: 헤더  
- `done`: 완료 여부 (true/false)

---

## 🛡 예외 처리
- 제목 누락 → `IllegalArgumentException("제목은 필수입니다.")`  
- 잘못된 ID 입력 → `NumberFormatException` 처리 후 안내  
- 파일 저장 실패 → `IOException` 메시지 출력 (앱 중단 없음)  
- 존재하지 않는 ID → `null/false` 반환 후 안내문 표시  

---

## 🧠 학습 포인트
- **객체지향 구조 설계(OOP)** — 역할 분리 및 캡슐화  
- **컬렉션 활용** — ArrayList, Stream, removeIf 등  
- **파일 입출력(File I/O)** — 데이터 영속화 개념  
- **예외 처리 흐름** — 안정적 프로그램 제어  
- **CRUD 구조 실습** — 백엔드의 기본 로직 패턴 이해

---

## 🗣 발표용 요약 문장
> “이 프로젝트는 CRUD 구조를 기반으로 한 콘솔형 할 일 관리 프로그램입니다.  
> 사용자는 메뉴를 통해 데이터를 직접 생성·조회·수정·삭제할 수 있으며,  
> 모든 데이터는 파일로 저장되어 종료 후에도 유지됩니다.  
> model-repo-service-ui 구조 분리와 예외처리를 통해 유지보수성과 안정성을 높였습니다.”

---

## 👤 개발자
**최강민**  
Java 풀스택 개발자 과정 (신촌 하이미디어 아카데미)  
📅 프로젝트 기간: 2025.10.20 ~ 2025.10.22

---



