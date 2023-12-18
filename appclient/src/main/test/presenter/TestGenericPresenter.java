package presenter;

public class TestGenericPresenter<T> extends GenericPresenter<T> {
    private T returnValue;

    public void setReturnValue(T returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public T get(String apiUrl, String id, Class<T> type) {
        return returnValue;
    }

    // Implement other methods if needed...
}