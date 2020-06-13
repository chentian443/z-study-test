package org.avro.test;

import java.io.File;
import java.io.IOException;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.avro.test.example.Product;

public class Test_avro {
    public static void main(String[] args) throws IOException {

        //TODO 序列化操作
        Product pro = Product.newBuilder().build();
        pro.setProductId("1");
        pro.setCompanyName("这是一个测试");
        pro.setProductInfo("测试的详细说明");
        pro.setDirection("1");
        //将生成的数据保存到本地文件中
        File file = new File("D:/BaiduNetdiskDownload/mapreduce_test/avroTest/product.avro");
//        DatumWriter<Product> productDatumWriter = new SpecificDatumWriter<Product>(Product.class);
//        DataFileWriter<Product> dataFileWriter = new DataFileWriter<Product>(productDatumWriter);
//        // 这个Schema即用于编译Product类所用的schema
//        dataFileWriter.create(Product.getClassSchema() , file);
//        dataFileWriter.append(pro);
//        dataFileWriter.close();

        //TODO 反序列
        DatumReader<Product> productDatumReader = new SpecificDatumReader<Product>(Product.class);
        DataFileReader<Product> productDataFileReader = new DataFileReader<Product>(file , productDatumReader);
        System.out.println(productDataFileReader.getSchema());  // 获取该product.avro文件的schema
        Product pro_reader = null;
        while (productDataFileReader.hasNext()){
            pro_reader = productDataFileReader.next();
            System.out.println(pro_reader);
        }
    }
}