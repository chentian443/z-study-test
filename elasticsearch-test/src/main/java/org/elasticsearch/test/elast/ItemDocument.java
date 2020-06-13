package org.elasticsearch.test.elast;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author JBY
 * @ClassName: ItemDocument
 * @Description: 索引实体类，在项目启动时，若ES中不存在该索引会自动创建。
 * @date 2018年10月18日 下午3:44:07
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = ItemDocument.INDEX, type = ItemDocument.TYPE)
public class ItemDocument {

    public static final String INDEX = "items";
    public static final String TYPE = "item";

    /**
     * 商品唯一标识
     */
    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    /**
     * 类目id
     */
    @Field(type = FieldType.Integer)
    private Integer catId;

    /**
     * 商品名称
     */
    @Field(type = FieldType.Text, index = false)
    private String name;


    /**
     * 商品价格
     */
    @Field(type = FieldType.Long)
    private Long price;


    /**
     * 商品的描述,标注使用ik分词
     */
    @Field(type = FieldType.Text, searchAnalyzer = "ik_smart", analyzer = "ik_smart")
    private String description;
}

