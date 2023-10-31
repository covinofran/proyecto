import java.util.List;

public interface DbOperation<T> {
	public void create();

	public void update();

	public Object read();

	public void delete();

	List<T> getAll();

}
