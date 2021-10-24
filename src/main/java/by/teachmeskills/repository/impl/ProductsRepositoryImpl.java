package by.teachmeskills.repository.impl;

import by.teachmeskills.jdbc_connection.JdbcConnection;
import by.teachmeskills.repository.BaseRepository;
import by.teachmeskills.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductsRepositoryImpl implements BaseRepository<Product> {

    private static final String INSERT = "INSERT INTO products (id, name, price) VALUES (?, ?, ?)";

    private static final String DELETE = "DELETE FROM products WHERE Id=?";

    private static final String SELECT_ALL = "SELECT * FROM products";

    private static final String SELECT_BY_ID = "SELECT * FROM products WHERE id=?";


    private static final String UPDATE = "UPDATE products SET id=?, name=?, price=? WHERE id=?";

    private final JdbcConnection connection;

    public ProductsRepositoryImpl(JdbcConnection connection) {
        this.connection = connection;
    }


    @Override
    public Product find(int id) {
        Product product = new Product();
        try (Connection con = connection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setPrice(resultSet.getInt(3));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        try (Connection con = connection.getConnection();
             Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);

            while (resultSet.next()) {
                Product product= new Product();
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setPrice(resultSet.getInt(3));
                list.add(product);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection con = connection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate()!=0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Product entity) {
        try (Connection con = connection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(UPDATE)) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setInt(3, entity.getPrice());
            preparedStatement.setInt(4, entity.getId());
            return preparedStatement.executeUpdate()!=0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean save(Product entity) {
        try (Connection con = connection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(INSERT)) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setInt(3, entity.getPrice());
            return preparedStatement.executeUpdate()!=0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
