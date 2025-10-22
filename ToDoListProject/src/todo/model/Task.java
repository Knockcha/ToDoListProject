package todo.model;

//★ 엔티티: UI/저장소와 분리된 '데이터 그 자체' 역할
public class Task {
	// ★ 필드는 private으로 캡슐화 (직접 접근 금지)
	private int id;
	private String title;
	private String dueDate; // ★ 단순 문자열로 보관 (나중에 LocalDate로 교체 가능)
	private boolean done;

	// ★ 파일 로드/특수 생성용: 모든 필드를 직접 지정
	public Task(int id, String title, String dueDate, boolean done) {
		this.id = id;
		this.title = title;
		this.dueDate = dueDate;
		this.done = done; // ★ 전달값 그대로 반영 (고정 false 금지)
	}

	// ★ 일반 신규 생성용: 기본 완료상태 false
	public Task(int id, String title, String dueDate) {
		this(id, title, dueDate, false); // ★ 생성자 체이닝으로 중복 제거
	}

	// ★ 읽기 전용 접근자 (외부에서 필드 직접 변경 금지)
	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDueDate() {
		return dueDate;
	}

	public boolean isDone() {
		return done;
	}

	// ★ 변경은 setter/명령 메서드를 통해서만 (규칙 한 곳에 모으기)
	public void setTitle(String title) {
		this.title = title;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	// ★ 상태 변경은 의미 있는 동사로 제공 (boolean 직접 set 지양)
	public void toggleDone() {
		this.done = !this.done;
	}

	// ★ 출력 포맷은 여기에서 통일 (UI와 모델의 경계 최소화)
	@Override
	public String toString() {
		String mark = done ? "✔" : " ";
		return String.format("[%s] #%d %s (마감: %s)", mark, id, title, dueDate);
	}
}
