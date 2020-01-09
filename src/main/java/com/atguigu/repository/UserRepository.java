package com.atguigu.repository;

import com.atguigu.pojo.User;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Tituo
 * @create 2020-01-08 20:38
 */
public interface UserRepository extends ElasticsearchRepository<User,Long> {

    public List<User> findByAgeBetween(Integer age1,Integer age2);

    @Query("{\n" +
            "    \"range\": {\n" +
            "      \"age\": {\n" +
            "        \"gte\": \"?0\",\n" +
            "        \"lte\": \"?1\"\n" +
            "      }\n" +
            "    }\n" +
            "  }")
    List<User> findByQuery(Integer age1, Integer age2);
}
