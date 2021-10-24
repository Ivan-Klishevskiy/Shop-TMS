package by.teachmeskills.service.impl;

import by.teachmeskills.repository.BaseRepository;
import by.teachmeskills.service.BaseService;
import by.teachmeskills.model.Product;

import java.util.List;

public class ProductsBaseServiceImpl implements BaseService<Product> {

    private BaseRepository<Product> repository;

    public ProductsBaseServiceImpl(BaseRepository<Product> repository) {
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
