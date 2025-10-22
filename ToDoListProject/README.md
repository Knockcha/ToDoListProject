# ✅ Java To-Do List (콘솔 미니 프로젝트)

## 📘 프로젝트 개요
자바 기초 및 컬렉션, 파일 입출력 학습을 위한 **콘솔형 할 일 관리 프로그램**입니다.  
DB 없이 파일(`tasks.tsv`)로 데이터를 저장하고 불러오며,  
CRUD(등록/조회/수정/삭제)와 완료 상태 토글 기능을 지원합니다.

---

## 🧱 개발 환경
| 항목 | 내용 |
|------|------|
| 개발 언어 | Java 17 |
| IDE | Eclipse |
| 실행 방식 | Console Application |
| 데이터 저장 | 파일 기반 (TSV 형식) |
| 주요 기술 | ArrayList, File I/O, Exception Handling |

---

## 🗂 프로젝트 구조
todo-console/
├─ src/
│ ├─ todo/ → 메인 실행 (TodoApp)
│ ├─ todo/model/ → Task 클래스 (데이터 모델)
│ ├─ todo/service/ → TaskService (비즈니스 로직)
│ └─ todo/repo/ → TaskRepository (파일 저장/불러오기)
├─ data/
│ └─ tasks.tsv → 저장된 할 일 데이터
└─ README.md → 프로젝트 설명 파일

---

## ⚙ 주요 기능
| 기능 | 설명 |
|------|------|
| 할 일 등록 | 제목과 마감일을 입력하여 새로운 Task 생성 |
| 목록 보기 | 전체 Task 목록을 출력 |
| 완료/취소 | 선택한 ID의 완료 상태를 토글 |
| 수정 | 제목 또는 마감일 변경 가능 |
| 삭제 | 선택한 ID의 Task 삭제 |
| 저장/불러오기 | 프로그램 종료 시 자동 저장, 실행 시 자동 불러오기 |

---

## 💾 파일 구조 (tasks.tsv)
- 구분자: 탭(`\t`)
- 첫 행: 헤더
- `done`은 완료 여부 (true/false)

---

## ▶ 실행 방법
1️⃣ Eclipse에서 `TodoApp.java` 실행  
2️⃣ 콘솔 메뉴 조작  
3️⃣ 종료 시 자동으로 `data/tasks.tsv`에 저장  
4️⃣ 다음 실행 시 자동으로 이전 데이터 불러오기

---

## 🧠 학습 포인트
- 객체지향 구조 분리 (model/service/repo/ui)
- 컬렉션(ArrayList)과 스트림 활용
- 파일 입출력(BufferedReader / Writer)
- 예외 처리(try-catch, IllegalArgumentException)
- 캡슐화(getter/setter)와 메서드 위임 구조 설계

---

## 👤 개발자
**최강민**  
자바 풀스택 개발자 과정 (신촌 하이미디어 아카데미)  
프로젝트 기간: 2025.10.20 ~ 2025.10.22 (2일)

