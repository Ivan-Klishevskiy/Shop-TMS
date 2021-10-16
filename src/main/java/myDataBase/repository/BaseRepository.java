package myDataBase.repository;

import java.util.List;

public interface BaseRepository <T> {

    T find(int id);
    List<T> findAll();
    boolean deleteById(int Id);
    boolean update(T entity);
    boolean save(T entity);
}
