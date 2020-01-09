package com.atguigu;

import com.atguigu.pojo.User;
import com.atguigu.repository.UserRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tituo
 * @create 2020-01-08 20:16
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EsDemo {

    //elasticsearchTemplate是TransportClient客户端
    @Autowired
    ElasticsearchRestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @Test
    public void test(){
        //创建索引
        this.restTemplate.createIndex(User.class);
        //创建映射
        this.restTemplate.putMapping(User.class);
        //删除映射
        //this.restTemplate.deleteIndex("user");
    }

    @Test
    public void testDocument(){
        //this.userRepository.save(new User(1l,"zhang3",20,"123456"));
        //System.out.println(this.userRepository.findById(1l).get());

    }
    @Test
    public void testAddAll(){
        List<User> users = new ArrayList<>();
        users.add(new User(1l, "柳岩", 18, "123456"));
        users.add(new User(2l, "范冰冰", 19, "123456"));
        users.add(new User(3l, "李冰冰", 20, "123456"));
        users.add(new User(4l, "锋哥", 21, "123456"));
        users.add(new User(5l, "小鹿", 22, "123456"));
        users.add(new User(6l, "韩红", 23, "123456"));
        this.userRepository.saveAll(users);
    }

    @Test
    public void testFindByAgeBetween(){
        System.out.println(this.userRepository.findByAgeBetween(20, 30));
    }

    @Test
    public void testFindByQuery(){
        System.out.println(this.userRepository.findByQuery(20, 30));
    }

    @Test
    public void testNative(){
        //初始化自定义查询对象
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //构建查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("name","冰冰"));
        //排序
        queryBuilder.withSort(SortBuilders.fieldSort("age").order(SortOrder.ASC));
        //分页
        queryBuilder.withPageable(PageRequest.of(0,2));
        //高亮
        queryBuilder.withHighlightBuilder(new HighlightBuilder().field("name").preTags("<em>").postTags("</em>"));
        //执行查询，获取分页结果集
        Page<User> userPage = this.userRepository.search(queryBuilder.build());
        // 总页数
        System.out.println(userPage.getTotalPages());
        // 总记录数
        System.out.println(userPage.getTotalElements());
        // 当前页数据
        System.out.println(userPage.getContent());

    }

}
