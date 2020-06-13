package org.elasticsearch.test.elast;

import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;


@RequestMapping("es")
@RestController
@Slf4j
public class DemoController {
    /**
     * @Fields elasticsearchTemplate : ES操作类
     */
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private ItemDocumentRepository repository;
    
    @GetMapping("/test")
    public String testStr(){
    	
    	return "test";
    }

    /**
     * @param
     * @return void
     * @throws
     * @Title: test
     * @Description: 使用elasticsearchTemplate手动创建索引
     */
    @GetMapping(value = "/create")
    public void test() {
        //简单创建索引，elasticsearchTemplate提供四种方式手动创建。
        elasticsearchTemplate.createIndex("JBY");
    }


    /**
     * @param id
     * @return ResponseEntity
     * @throws
     * @Title: getItem
     * @Description: 查询指定Id document
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<String> getItem(@PathVariable("id") String id) {
        ItemDocument com = repository.findById(id).get();
        return new ResponseEntity<String>(com.toString(), HttpStatus.OK);
    }

    /**
     * @param document
     * @return ResponseEntity
     * @throws
     * @Title: createItem
     * @Description: 插入document
     */
    @PostMapping
    public ResponseEntity createItem(@RequestBody ItemDocument document) {
    	log.info("createItem():" + document.toString());
        repository.save(document);
        return new ResponseEntity(document.toString(), HttpStatus.OK);
    }


    /**
     * @param word
     * @param pageable,主要参数page：当前页，size每页大小
     * @return Page<ItemDocument>
     * @throws
     * @Title: singleTitle
     * @Description: 根据输入串查询Document
     */
    @GetMapping(path = "/singleWord")
    public Page<ItemDocument> singleTitle(String word, @PageableDefault Pageable pageable) {
    	log.info("singleTitle():" + word);
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(word);
        return repository.search(builder, pageable);
    }

}
