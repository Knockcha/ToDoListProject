package todo.repo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

import todo.model.Task;

//★ Repository: 저장 기술(File, DB 등) 변경시에도 Service/UI 손대지 않도록 '경계' 역할
public class TaskRepository {
	private final List<Task> tasks = new ArrayList<>();

	// ★ data 디렉터리는 프로젝트 루트 기준. 없으면 생성.
	private final Path dataDir = Paths.get("data");
	private final Path file = dataDir.resolve("tasks.tsv");

	public TaskRepository() {
		try {
			Files.createDirectories(dataDir);
		} catch (IOException ignored) {
		}
	}

	// ===== In-memory API =====
	public void add(Task task) {
		tasks.add(task);
	} // ★ 순서 보장: ArrayList

	public List<Task> findAll() {
		// ★ 외부에서 리스트를 수정 못 하게 '읽기 전용'으로 내보내기
		return Collections.unmodifiableList(tasks);
	}

	public Task findById(int id) {
		// ★ 작은 컬렉션에서는 선형 탐색으로도 충분 (ID가 key면 Map으로 교체 가능)
		for (Task t : tasks)
			if (t.getId() == id)
				return t;
		return null;
	}

	public boolean remove(int id) {
		// ★ 조건 삭제에는 removeIf가 간단하고 안전
		return tasks.removeIf(t -> t.getId() == id);
	}

	// ===== File I/O =====
	public void save() {
		// ★ 헤더 포함, 탭( \t ) 구분, UTF-8로 저장
		try (BufferedWriter bw = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
			bw.write("id\ttitle\tdueDate\tdone\n");
			for (Task t : tasks) {
				// ★ 탭/개행은 escape로 정리 (파일 포맷 깨짐 방지)
				bw.write(t.getId() + "\t");
				bw.write(escape(t.getTitle()) + "\t");
				bw.write(escape(t.getDueDate()) + "\t");
				bw.write(Boolean.toString(t.isDone()));
				bw.write("\n");
			}
		} catch (IOException e) {
			System.out.println("파일 저장 실패: " + e.getMessage());
		}
	}

	public void load() {
		tasks.clear(); // ★ 중복 적재 방지: 로드 전 초기화
		if (!Files.exists(file))
			return; // ★ 최초 실행 등 파일 없을 수 있음 (정상)
		try (BufferedReader br = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
			String line = br.readLine(); // ★ 첫 줄 헤더 스킵
			while ((line = br.readLine()) != null) {
				String[] cols = line.split("\t", -1); // ★ 빈 칸 유지(-1)
				if (cols.length < 4)
					continue; // ★ 방어적 코딩: 손상 라인 스킵
				int id = Integer.parseInt(cols[0]);
				String title = unescape(cols[1]);
				String due = unescape(cols[2]);
				boolean done = Boolean.parseBoolean(cols[3]);
				tasks.add(new Task(id, title, due, done));
			}
		} catch (Exception e) {
			System.out.println("파일 로드 실패: " + e.getMessage());
		}
	}

	private static String escape(String s) {
		if (s == null)
			return "";
		// ★ 최소한의 이스케이프: 탭/개행만 제거 (CSV/JSON 도입 전 임시 대응)
		return s.replace("\t", "    ").replace("\n", " ");
	}

	private static String unescape(String s) {
		return s; // ★ 현재는 단순화 (추후 포맷 바꾸면 함께 수정)
	}
}
