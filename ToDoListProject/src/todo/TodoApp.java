package todo;

import java.util.List;
import java.util.Scanner;

import todo.model.Task;
import todo.repo.TaskRepository;
import todo.service.TaskService;

public class TodoApp {
	// ★ 콘솔 입력 스캐너는 앱 종료 전까지 하나만 사용 (메모리 누수/잠김 방지)
	private static final Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
	    TaskRepository repo = new TaskRepository();   // ★ 데이터 I/O 담당 (메모리+파일)
	    TaskService service = new TaskService(repo);  // ★ 비즈니스 로직 (검증/ID발급/위임)

	    boolean run = true;
	    System.out.println("===== To-Do Console =====");
	    while (run) {
	        printMenu();                               // ★ UI는 '보여주기'만, 로직은 Service로 위임
	        String sel = prompt("메뉴 선택");
	        switch (sel) {
	            case "1" -> add(service);              // ★ 입력 → 검증/생성은 Service로
	            case "2" -> list(service);             // ★ 조회도 Service로부터 받아 출력만
	            case "3" -> toggle(service);
	            case "4" -> edit(service);
	            case "5" -> remove(service);
	            case "8" -> { service.save(); System.out.println("저장 완료"); }
	            case "0" -> {
	                service.save();                    // ★ 종료 직전 자동 저장 (데이터 유실 방지)
	                System.out.println("저장 후 종료합니다.");
	                run = false;
	            }
	            default -> System.out.println("잘못된 선택");
	        }
	    }
	}

	// ★ 메뉴 문구는 UI에서만 관리 (추후 메뉴추가/문구변경 용이)
	private static void printMenu() {
	    System.out.println("\n[1] 할 일 추가  [2] 목록 보기 [3] 완료/취소 [4] 수정 [5] 삭제 [8] 저장  [0] 종료");
	}

	private static void add(TaskService service) {
	    String title = prompt("제목");                 // ★ 공백/빈문자 검증은 Service에서 최종 수행
	    String due = prompt("마감일(예: 2025-10-22 / Enter=없음)");
	    try {
	        // ★ UI는 단순 가공만 (Enter → "-"), 검증은 Service가 책임
	        Task t = service.create(title, due.isBlank() ? "-" : due);
	        System.out.println("등록 완료 : " + t);
	    } catch (Exception e) {
	        System.out.println("등록 실패 :" + e.getMessage()); // ★ 예외 메시지는 사용자 친화적으로
	    }
	}

	private static void list(TaskService service) {
	    List<Task> list = service.list();             // ★ 읽기 전용 리스트(내부 변경 금지)
	    System.out.println("=== 목록 (" + list.size() + ") ===");
	    if (list.isEmpty()) System.out.println("(비어있음)");
	    for (Task t : list) System.out.println(t);    // ★ Task.toString() 포맷 통일
	}

	private static String prompt(String label) {
	    System.out.print(label + " > ");              // ★ println 아님 (입력줄 이어쓰기)
	    return sc.nextLine().trim();                  // ★ trim으로 앞뒤 공백 제거
	}

	private static void toggle(TaskService service) {
	    String s = prompt("토글할 ID");
	    try {
	        int id = Integer.parseInt(s);             // ★ 숫자 변환 실패에 대비
	        boolean ok = service.toggleDone(id);      // ★ 존재 확인/상태변경은 Service 책임
	        System.out.println(ok ? "상태 변경 완료" : "대상을 찾을 수 없습니다.");
	    } catch (NumberFormatException e) {
	        System.out.println("ID는 숫자여야 합니다.");
	    }
	}

	private static void edit(TaskService service) {
	    String s = prompt("수정할 ID");
	    try {
	        int id = Integer.parseInt(s);
	        var current = service.list().stream()
	            .filter(t -> t.getId() == id).findFirst().orElse(null);
	        if (current == null) {
	            System.out.println("대상을 찾을 수 없습니다."); // ★ 없는 ID 방어
	            return;
	        }
	        System.out.println("현재 : " + current);

	        // ★ 빈값(Enter)=유지, "-"=마감 없음으로 처리 (정책은 Service에서 최종 반영)
	        String newTitle = prompt("새 제목(Enter=유지)");
	        String newDue   = prompt("새 마감일(예: 2025-10-24 / Enter=유지 / '-'=없음)");

	        boolean ok = service.update(id, newTitle, newDue);
	        System.out.println(ok ? "수정 완료" : "수정 실패");
	    } catch (NumberFormatException e) {
	        System.out.println("ID는 숫자여야 합니다.");
	    }
	}

	private static void remove(TaskService service) {
	    String s = prompt("삭제할 ID");
	    try {
	        int id = Integer.parseInt(s);
	        boolean ok = service.delete(id);
	        System.out.println(ok ? "삭제 완료" : "대상을 찾을 수 없습니다.");
	    } catch (NumberFormatException e) {
	        System.out.println("ID는 숫자여야 합니다.");
	    }
	}
}