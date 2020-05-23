package ua.training.model.dao;

public interface GenericDao<T> extends AutoCloseable {
    void close();
}
