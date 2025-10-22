package todo.service;

import java.util.*;
import todo.model.Task;
import todo.repo.TaskRepository;

//★ Service: '규칙/검증/ID발급/정책'을 한 곳에 모아두는 계층
public class TaskService {
	private final TaskRepository repo;
	private int nextId = 1; // ★ 실행마다 이어지도록 load 후 갱신

	public TaskService(TaskRepository repo) {
		this.repo = repo;
		this.repo.load(); // ★ 시작 시 파일 로드
		for (Task t : this.repo.findAll()) { // ★ 기존 max ID 기준으로 nextId 갱신
			nextId = Math.max(nextId, t.getId() + 1);
		}
	}

	public Task create(String title, String dueDate) {
		// ★ 필수 입력 검증은 Service 책임 (UI에서 걸러도 '최종 방어' 필요)
		if (title == null || title.isBlank()) {
			throw new IllegalArgumentException("제목은 필수입니다.");
		}
		String due = (dueDate == null || dueDate.isBlank()) ? "-" : dueDate.trim(); // ★ 정책: 빈값은 '-' 처리
		Task t = new Task(nextId++, title.trim(), due);
		repo.add(t); // ★ 실제 저장은 Repository에 위임
		return t;
	}

	public List<Task> list() {
		// ★ Repository 결과 그대로 전달 (정렬/필터는 확장 시 여기에)
		return repo.findAll();
	}

	public boolean toggleDone(int id) {
		Task t = repo.findById(id); // ★ 존재 여부 체크
		if (t == null)
			return false;
		t.toggleDone(); // ★ 상태 변경은 엔티티 메서드로
		return true;
	}

	public boolean update(int id, String newTitle, String newDueOrKeep) {
		Task t = repo.findById(id);
		if (t == null)
			return false;

		// ★ Enter(빈문자)면 유지, 값이 있으면만 변경
		if (newTitle != null && !newTitle.isBlank()) {
			t.setTitle(newTitle.trim());
		}

		// ★ 마감 정책: Enter=유지, "-"=없음, 그 외=변경
		if (newDueOrKeep != null) {
			if (newDueOrKeep.isBlank()) {
				// 유지
			} else if (newDueOrKeep.trim().equals("-")) {
				t.setDueDate("-");
			} else {
				t.setDueDate(newDueOrKeep.trim());
			}
		}
		return true;
	}

	public boolean delete(int id) {
		return repo.remove(id); // ★ 위임 (성공/실패 boolean 그대로 반환)
	}

	public void save() {
		repo.save(); // ★ 저장 위치/포맷은 Repository가 관리
	}
}
