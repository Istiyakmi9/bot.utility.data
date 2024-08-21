package com.bhalf.utility.data.service;

import com.bhalf.utility.data.utils.Template;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DbManager {

    @Autowired
    DbUtils dbUtils;

    @Autowired
    ObjectMapper mapper;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    DbManager(Template template) {
        jdbcTemplate = template.getTemplate();
    }

    public <T> void save(T instance) throws Exception {
        String query = dbUtils.save(instance);
        jdbcTemplate.execute(query);
    }

    public <T> List<T> get(Class<T> type) throws Exception {
        String query = dbUtils.get(type);
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);

        return mapper.convertValue(result, new TypeReference<List<T>>() {});
    }

    public <T> T getById(T instance, Class<T> type) throws Exception {
        String query = dbUtils.getById(type);

        T emp = jdbcTemplate.queryForObject(query, type);

        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);

        return mapper.convertValue(result, new TypeReference<T>() {});
    }
}
