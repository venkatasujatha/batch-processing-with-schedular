package com.example.demo.Config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.example.demo.Listener.MyJobListener;
import com.example.demo.Model.Product;
import com.example.demo.Processor.ProductProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	//1.create reader class object
	@Bean
	public FlatFileItemReader<Product> fileItemReader()
	{
		FlatFileItemReader<Product> fileItemReader=new FlatFileItemReader<>();
		fileItemReader.setResource(new ClassPathResource("product.csv"));  //src/main/REsources
		
		fileItemReader.setLineMapper(new DefaultLineMapper<>() {{
			setLineTokenizer(new DelimitedLineTokenizer()
					{{
						setDelimiter(DELIMITER_COMMA);
						setNames("prodid","prodcode","prodcost");
					}});
			
			setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
				setTargetType(Product.class);
			}});
		}});
		
			
		
		return fileItemReader;
		
	}
	
	//2.processor class object
	@Bean
	public ItemProcessor<Product, Product> itemProcessor()
	{
		return new ProductProcessor();
		
	}
	
	@Autowired
	private DataSource dataSource;
	
	//3.create writer class object
	@Bean
	public JdbcBatchItemWriter<Product> batchItemWriter()
	{
		JdbcBatchItemWriter<Product> batchItemWriter=new JdbcBatchItemWriter<>();
		batchItemWriter.setDataSource(dataSource);
		batchItemWriter.setSql("Insert into Products(pid,pcode,pcost,pdisc,pgst)values(:prodid,:prodcode,:prodcost,:proddisc,:prodgst)");
		batchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		return batchItemWriter;
	}
	
	//4.listener class object
	@Bean
	public JobExecutionListener executionListener()
	{
		return new MyJobListener();
	}
	
	//5.autowire step builder factory
	
	@Autowired
	private StepBuilderFactory builderFactory;
	
	//6.step object
	@Bean
	public Step stepA()
	{
		return builderFactory.get("stepA") //step name
				.<Product,Product>chunk(3) //<I,O>chunk
				.reader(fileItemReader())  //Reader object
				.writer(batchItemWriter()) //writer object 
				.processor(itemProcessor()) //processor object
				.build();
	}
	
	//7.autowire job builder factory
	@Autowired
	private JobBuilderFactory jf;

	
	//8.job object
	@Bean
	public Job jobA()
	{
		return jf.get("jobA")
				.incrementer(new RunIdIncrementer())
				.listener(executionListener())
				.start(stepA())
				.build();
	}
	

}
