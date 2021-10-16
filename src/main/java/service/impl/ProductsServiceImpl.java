package service.impl;

import myDataBase.repository.BaseRepository;
import myDataBase.repository.impl.ProductsRepositoryImpl;
import service.BaseService;
import shopUnit.Product;

import java.util.List;

public class ProductsServiceImpl implements BaseService<Product> {

    private BaseRepository<Product> repository;

    public ProductsServiceImpl(BaseRepository<Product> repository) {
        this.repository = repository;
    }

    @Override
    public Product find(int id) {
        return repository.find(id);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean deleteById(int id) {
        return repository.deleteById(id);
    }

    @Override
    public boolean update(Product entity) {
        return repository.update(entity);
    }

    @Override
    public boolean save(Product entity) {
        return repository.save(entity);
    }
}
